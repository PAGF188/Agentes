import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @autor Pablo García Fernández
 * Agente vendedor. Quien inicia y controla la subasta.
 */

public class VendedorAgent extends Agent{

    /**
     * ArrayList de subastas
     */
    ArrayList<Subasta> subastas;

    @Override
    protected void setup(){
        subastas = new ArrayList<>();
        subastas.add(new Subasta("libro1",10,5));
        addBehaviour(new VendedorAgent.Comportamiento());
    }

    @Override
    protected void takeDown() {
        System.out.println("Agente vendedor"+getAID().getName()+" terminando.");
    }

    private class Comportamiento extends Behaviour {

        /**
         * Lista de eliminar subastas si terminaron
         */
        private ArrayList<Subasta> eliminar;


        @Override
        public void action() {

            eliminar = new ArrayList<>();

            /**
             * Recorremos todas las subastas activas.
             */
            for (Subasta aux : subastas) {

                /**
                 * Consultamos en las páginas amarillas, los clientes interesados en el libro de la subasta
                 */
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("subasta");
                sd.setName(aux.getLibro());
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    aux.reiniciar();

                    /**
                     * Añadimos los agentes a participantes, previamente reseteado
                     */
                    for (int i = 0; i < result.length; ++i) {
                        aux.add(result[i].getName());
                    }

                    /**
                     * Enviamos CFP a todos los activos, con contenido de mensaje precio y COnversatioID libro
                     */
                    int respuestas=0;
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < aux.getParticipantes().size(); ++i) {
                        cfp.addReceiver(aux.getParticipantes().get(i));
                        respuestas++;
                    }
                    cfp.setContent(String.valueOf(aux.getPrecio()));
                    cfp.setConversationId(aux.getLibro());
                    cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);

                    /**
                     * Esperamos a obtener las respuestas de todos
                     */
                    MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.PROPOSE);
                    while(respuestas!=0){
                        ACLMessage msg = myAgent.receive(mt);
                        if (msg != null) {
                            if(msg.getContent().equals("acepto")){

                            }
                            else{
                                aux.getParticipantes().remove(msg.getSender());
                            }
                        }
                        respuestas--;
                    }

                    /*Al acabar la ronda damos ganador a 1 participante que este activo*/
                    if(aux.getParticipantes().size()!=0)
                     aux.setGanador(aux.getParticipantes().get(0));
                     ACLMessage a = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                     a.addReceiver(aux.getGanador());
                     a.setContent("Aceptado " + aux.getLibro());
                     a.setConversationId(aux.getLibro());
                     a.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                     myAgent.send(a);


                    /**
                     * mensajes de rechazo al resto de participantes activos
                     */

                    ACLMessage d = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    for (int i = 0; i < aux.getParticipantes().size(); ++i) {
                        if(!aux.participantes.get(i).equals(aux.getGanador()))
                            d.addReceiver(aux.getParticipantes().get(i));
                    }
                    d.setContent("Rechazado " + aux.getPrecio());
                    d.setConversationId(aux.getLibro());
                    d.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(d);

                    /**
                     * criterio de parada
                     * 1 participante, 0 participantes
                     */
                    if(aux.getParticipantes().size()==1 || aux.getParticipantes().size()==0){
                        aux.setFase(-1);
                    }

                    /**
                     * Paso final. Al acabar subasta
                     */
                    if(aux.getFase()==-1){
                        //añadir a la lista de eliminar subastas
                        eliminar.add(aux);
                    }

                }
                catch (Exception fe) {
                    fe.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                }catch(Exception e){
                    e.printStackTrace();
                }

                //Incrementamos el precio
                aux.incrementar();
            }

            /**
             * Al acabar de recorrer todas las subastas, eliminamos aquellas que terminaron
             */
            subastas.removeAll(eliminar);
        }

        @Override
        public boolean done() {
            if(subastas.size()==0){
                return true;
            }
            else{
                return false;
            }
        }
    }


    /**
     * Clase interna auxiliar subasta
     */

    private class Subasta{

        private String libro;
        private int precio;
        private int incremento;
        private AID ganador;
        private ArrayList<AID> participantes;
        /**
         * 0 inicial
         * -1 termino
         */
        private int fase;

        public Subasta(String libro, int precio, int incremento){
            this.libro=libro;
            this.precio=precio;
            this.incremento=incremento;
            participantes = new ArrayList<>();
            fase=0;
        }

        public String getLibro(){
            return this.libro;
        }

        public ArrayList<AID> getParticipantes() {
            return participantes;
        }

        public int getPrecio() {
            return precio;
        }

        public AID getGanador() {
            return ganador;
        }

        public void add(AID a){
            participantes.add(a);
        }

        public void setFase(int fase) {
            this.fase = fase;
        }

        public int getFase() {
            return fase;
        }

        public void incrementar(){
            this.precio+=this.incremento;
        }

        public void setGanador(AID ganador) {
            this.ganador = ganador;
        }

        public void reiniciar(){
            participantes=null;
            participantes = new ArrayList<>();
        }
    }
}
