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

    /*Libros sobre los que pujo. Y están activos*/
    private HashMap<String, Integer> libros;

    /* Libros totales incluso en los que estás fuera*/
    private HashMap<String, Integer> todos;

    /*Interfaz gráfica*/
    private CompradorAgentGui myGui;

    public HashMap<String, Integer> getLibros(){
        return(this.libros);
    }

    public HashMap<String, Integer> getTodos(){
        return(this.todos);
    }

    /*Inicialización del agente*/
    @Override
    protected void setup() {

        libros = new HashMap<>();
        todos = new HashMap<>();

        // Create and show the GUI
        myGui = new CompradorAgentGui(this);
        myGui.setVisible(true);

        /**
         * Nos registramos en las páginas amarillas para que el vendedor nos pueda encontrar.
         * Pero todavía sin indicar ningún servicio
         */
        DFAgentDescription dfd = new DFAgentDescription();
        dfd.setName(getAID());
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
        //Actualizar perdedor ronda
        addBehaviour(new Perdiendo());
        //Actualizar ganador ronda
        addBehaviour(new Ganando());
    }

    /**
     * Limpieza antes de desregistro
     */
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
     * Añadir una nueva entrada en libros y modificar registro añadiendo tantos servicios como entradas
     * @param title
     * @param price
     */
    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {

                todos.put(title, new Integer(price));
                libros.put(title, new Integer(price));
                System.out.println(title+" añadido. Price = "+price +  "Entrando subasta.");

                /**
                 * Modificamos la descripción del agente
                 */
                DFAgentDescription dfd = new DFAgentDescription();
                dfd.setName(getAID());

                /**
                 * Añadimos un servicio libro, por cada libro
                 */
                for (String key : libros.keySet()) {
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("subasta");
                    sd.setName(key);
                    dfd.addServices(sd);
                }
                /**
                 * Grabamos los datos
                 */
                try {
                    DFService.modify(myAgent,dfd);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Uno muy parecido al anterior para desapuntarse
     */
    public void salirSubasta(final String title) {
        addBehaviour(new OneShotBehaviour() {
            public void action() {

                libros.remove(title);
                System.out.println(title+" eliminado. Saliendo de su subasta");

                /**
                 * Modificamos la descripción del agente
                 */
                DFAgentDescription dfd = new DFAgentDescription();
                dfd.setName(getAID());

                /**
                 * Añadimos un servicio libro, por cada libro
                 */
                for (String key : libros.keySet()) {
                    ServiceDescription sd = new ServiceDescription();
                    sd.setType("subasta");
                    sd.setName(key);
                    dfd.addServices(sd);
                }
                /**
                 * Grabamos los datos
                 */
                try {
                    DFService.modify(myAgent,dfd);
                } catch (FIPAException e) {
                    e.printStackTrace();
                }
            }
        } );
    }

    /**
     * Clase interna.
     * Usada por el comprador durante la fase de negociación.
     * Recibirá un mensaje procediente del vendedor con la cantidad de dinero de la ronda actual
     * El id de la conversación será el libro sobre el que se está pujando
     * Responderá si puja o no.
     */
    private class Negociacion extends Behaviour {

        @Override
        public void action() {
            /**
             * Definimos el formato del mensaje de entrada. En este caso uno de tipo CFP
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
                String libro = msg.getConversationId();
                int precio = Integer.parseInt(msg.getContent());

                if (libros.get(libro) >= precio) {
                    reply.setContent("acepto");
                    System.out.println(myAgent.getName() + " acepto " + libro +" "+precio);
                    /**
                     * Si acepto, muestro en la interfaz, que acepte y por cuanto va
                     */
                    myGui.actualizarEstado(libro,"Aceptado " + precio);

                } else {
                    //Si no estamos interesados
                    reply.setContent("deniego " + precio);
                    System.out.println(myAgent.getName() + " deniego " + libro +" "+precio);
                    /**
                     *  No acepto, me quedo en x
                     */
                    myGui.actualizarEstado(libro,"Me quedo en:  " + libros.get(libro));
                }
                myAgent.send(reply);
            }
            else {
                block();
            }
        }

        /**
         * Cambiar por return libros.size()==0
         */
        @Override
        public boolean done() {
            return false;
        }
    }

    /**
     * Clase interna para informar del final de ronda, RejectProposal
     */
    private class Perdiendo extends Behaviour{

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.REJECT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            //Si hay mensaje
            if(msg!=null){
                myGui.actualizarEstadoPerdedor(msg.getConversationId());
            }
            else{
                block();
            }
        }

        @Override
        public boolean done() {
            return false;
        }
    }

    /**
     * Clase interna para informar del final de ronda, AceptProposal
     */
    private class Ganando extends Behaviour{

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);
            //Si hay mensaje
            if(msg!=null){
                myGui.actualizarEstadoGanador(msg.getConversationId());
            }
            else{
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
     */
    private class FinalSubasta extends Behaviour {

        @Override
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.INFORM);
            ACLMessage msg = myAgent.receive(mt);
            //Si hay mensaje
            if(msg!=null){
                System.out.println("Finalizada ganador: " + msg.getContent());
                myGui.actualizarEstado(msg.getConversationId(),"Finalizada. Ganador " + msg.getContent());
                myGui.terminar(msg.getConversationId());
                /**
                 * Eliminamos el libro de los hashmap
                 */
                libros.remove(msg.getConversationId());
                //todos.remove(msg.getConversationId());
            }
            else{
                block();
            }
        }

        @Override
        public boolean done() {
            return false;
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
                    System.out.println(myAgent.getName() + " dice: Has ganado, a comprar el libro. Precio: " + Integer.parseInt(msg.getContent()));
                    myGui.actualizarEstado(msg.getConversationId(), "Ganaste. Compra el libro por: " + Integer.parseInt(msg.getContent()));
                }
                else{
                    block();
                }
            }

            @Override
            public boolean done() {
                return false;
            }
        }
}
