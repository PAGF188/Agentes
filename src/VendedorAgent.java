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

    /*Pareja libro-incremento*/
    HashMap<String, Integer> libros;

    @Override
    protected void setup(){
        libros = new HashMap<>();
        libros.put("libro1",10);
        addBehaviour(new VendedorAgent.Comportamiento());
    }

    @Override
    protected void takeDown() {
        System.out.println("Agente vendedor"+getAID().getName()+" terminando.");
    }

    private class Comportamiento extends Behaviour {

        int fase=0;
        boolean paramos=false;
        //Esto sirve para 1, luego habrá que crear una clase subasta con todos los datos
        private AID ganador;
        private ArrayList<AID> participantes;

        @Override
        public void action() {
            if(fase==0) {
                participantes = new ArrayList<>();
            }
            if(fase==1){
                System.out.println("1");
            }
            //Para cada subasta
            for (Map.Entry<String, Integer> entry : libros.entrySet()) {

                /**
                 * criterio de parada
                 * 1 participante, 0 participantes
                 */
                if(participantes.size()==0 && fase!=0){
                    paramos=true;
                    return;
                }
                if(participantes.size()==1){
                    paramos=true;
                    ganador=participantes.get(0);
                    return;
                }
                /**
                 * Paso final. Al acabar subasta
                 */
                if(paramos==true){

                }

                //Consultamos páginas amarillas de la subasta i
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("subasta");
                sd.setName(entry.getKey());
                template.addServices(sd);
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    ArrayList<AID> activos = new ArrayList<>();

                    for (int i = 0; i < result.length; ++i) {
                        activos.add(result[i].getName());
                        if(!participantes.contains(result[i].getName()))
                            participantes.add(result[i].getName());
                    }

                    /**
                     * Enviamos CFP a todos los activos que esten en participantes
                     */
                    int respuestas=0;
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < activos.size(); ++i) {
                        if(participantes.contains(activos.get(i))) {
                            cfp.addReceiver(activos.get(i));
                            respuestas++;
                        }
                    }
                    cfp.setContent(Integer.toString(entry.getValue()));
                    cfp.setConversationId(entry.getKey());
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
                                participantes.remove(msg.getSender());
                            }
                        }
                        respuestas--;
                    }

                    /*Al acabar la ronda damos ganador a 1 participante que este activo*/
                    for(int i=0; i<participantes.size();i++){
                        if(activos.contains(participantes.get(i))){
                            ganador=participantes.get(i);
                            ACLMessage a = new ACLMessage(ACLMessage.ACCEPT_PROPOSAL);
                            a.addReceiver(ganador);
                            a.setContent("Aceptado " + entry.getValue());
                            a.setConversationId(entry.getKey());
                            a.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                            myAgent.send(a);
                            break;
                        }
                    }

                    /**
                     * mensajes de rechazo al resto de participantes activos
                     */

                    ACLMessage d = new ACLMessage(ACLMessage.REJECT_PROPOSAL);
                    for (int i = 0; i < activos.size(); ++i) {
                        if(!activos.get(i).equals(ganador) && participantes.contains(activos.get(i)))
                            d.addReceiver(activos.get(i));
                    }
                    d.setContent("Rechazado " + entry.getValue());
                    d.setConversationId(entry.getKey());
                    d.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(d);
                }
                catch (Exception fe) {
                    fe.printStackTrace();
                }
                try {
                    Thread.sleep(10000);
                }catch(Exception e){
                    e.printStackTrace();
                }

            }
            fase++;
            //incrementamos precio:
            libros.put("libro1",libros.get("libro1")+5);

        }

        @Override
        public boolean done() {
            return paramos;
        }
    }
}
