import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.OneShotBehaviour;
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
    private HashMap<String, Integer> libros;

    /*Interfaz gráfica*/
    private CompradorAgentGui myGui;


    /*Inicialización del agente*/
    @Override
    protected void setup() {

        libros = new HashMap<>();

        // Create and show the GUI
        myGui = new CompradorAgentGui(this);
        myGui.setVisible(true);

        /**
         * Nos registramos en las páginas amarillas para que el vendedor nos pueda encontrar.
         */
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

        /**
         * Comportamento para saber si estoy interesado en la subasta de un libro particular
         */
        addBehaviour(new CyclicBehaviour() {
            @Override
            public void action() {
                MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.SUBSCRIBE);
                ACLMessage msg = myAgent.receive(mt);
                if(msg!=null){
                    ACLMessage reply = msg.createReply();
                    reply.setPerformative(ACLMessage.SUBSCRIBE);
                    /**
                     * Si si estoy interesado:
                     */
                    if(libros.containsKey(msg.getContent())){
                        reply.setContent("interesado");
                    }
                    else{
                        reply.setContent("noInteresado");
                    }
                }
                else{
                    block();
                }
            }
        });
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
     * Para apuntarse a la subasta de este tipo.
     * @param title
     * @param price
     */
    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {
                libros.put(title, new Integer(price));
                System.out.println(title+" inserted into catalogue. Price = "+price);
            }
        } );
    }

    /**
     * Uno muy parecido al anterior para desapuntarse
     */

    /**
     * Clase interna
     * Usada por el comprador durante la fase de negociación
     * Recibirá un mensaje procediente del vendedor con la cantidad actual de la subasta
     * Responderá si puja o no.
     */
    private class Negociacion extends Behaviour {


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
                 * Si no supera el precio para la subasta del libro
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
            return false;
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
