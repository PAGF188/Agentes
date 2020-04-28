import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.domain.FIPAException;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jdk.nashorn.internal.ir.RuntimeNode;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * @autor Pablo García Fernández
 * Agente de comprador. El que participa en las subastas.
 */
public class CompradorAgent extends Agent{

    /*Libros sobre los que pujo*/
    HashMap<String, Integer> libros;


    /*Inicialización del agente*/
    @Override
    protected void setup() {

        libros = new HashMap<>();
        libros.put("libro1",25);

        //Registramos al comprador en el servcio de páginas amarillas.
        //Luego abrá que modificarlo para una subasta particular cuando muestre su interés en ella
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
        ServiceDescription sd = new ServiceDescription();
        sd.setType("subasta");
        sd.setName("libro1");
        dfd.addServices(sd);
        try {
            DFService.register(this, dfd);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }

        //Comportamiento pasa negociación
        addBehaviour(new Negociacion());
        //Comportamineto final subasta notificación
        addBehaviour(new FinalSubasta());
        //Comportamineto para compra de libro
        addBehaviour(new Venta());
    }

    /*Limpieza antes de eliminación*/
    @Override
    protected void takeDown() {
        //Nos des-registramos de las páginas amarillas. ¿Habrá muchas?
        try {
            DFService.deregister(this);
        }
        catch (FIPAException fe) {
            fe.printStackTrace();
        }
        // Printout a dismissal message
        System.out.println("CompradorAgente "+getAID().getName()+" terminando.");
    }

    /**
     * Clase interna
     * Usada por el comprador durante la fase de negociación
     * Recibirá un mensaje procediente del vendedor con la cantidad actual de la subasta
     * Responderá si puja o no.
     */
    private class Negociacion extends Behaviour {

        int fase = 0;

        @Override
        public void action() {
            /**
             * Definimos el formato del mensaje. En este caso uno de tipo CFP
             */
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);
            //Si hay mensaje
            if (msg != null) {
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPOSE);
                /**
                 * Si no supera el precio para la subasta del libro indicado por ConversationID
                 * IMPORTANTISIMO:: hacer que el getConversatioID sea el nombre del libro que se está subastanto
                 */
                int price = Integer.parseInt(msg.getContent());
                if (libros.get(msg.getConversationId()) >= price) {
                    reply.setContent("acepto");
                    System.out.println("Acepto " +price);
                } else {
                    //Si no estamos interesados
                    System.out.println("Deniego " +price);
                    reply.setContent("deniego " + price);
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }

        @Override
        public boolean done() {
            return fase == 3;
        }
    }

    /**
     * Clase interna.
     * Usada por el comprador para ser notificado del final de la subasta
     * ¿Y quién es el ganador?
     */
    private class FinalSubasta extends Behaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            //Si hay mensaje
            if(msg!=null){

            }
            else{
                block();
            }
        }

        @Override
        public boolean done() {
            return true;
        }
    }

    /**
     * Clase interna
     * Usada por el comprador para proceder a la compra del libro si es el ganador
     */
    private class Venta extends Behaviour {
        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REQUEST);
                ACLMessage msg = myAgent.receive(mt);
                //Si hay mensaje
                if(msg!=null){

                }
                else{
                    block();
                }
            }

            @Override
            public boolean done() {
                return true;
            }
        }

}
