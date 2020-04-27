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
        libros.put("libro1",50);

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

        addBehaviour(new RequestPerformer());
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

    private class RequestPerformer extends Behaviour {

        int fase=0;

        @Override
        //Va tener que controlar a que subasta pertenece cada mensaje
        public void action() {
            //Recibimos el mensaje y según su tipo hacemos diferentes cosas:
            ACLMessage msg = myAgent.receive();
            //Si es de tipo CFP estamos en la fase de subasta
            if(msg!=null && msg.getPerformative()==ACLMessage.CFP){
                System.out.println("Entrando en fase subasta");
                ACLMessage reply = msg.createReply();
                reply.setPerformative(ACLMessage.PROPOSE);
                /**
                 * Si no supera el precio en cualqueira de nuestros libors -> propose
                 * IMPORTANTISIMO:: hacer que el getConversatioID sea el nombre del libro que se está subastanto
                 */
                int price = Integer.parseInt(msg.getContent());
                if(libros.get(msg.getConversationId())>=price){
                    reply.setContent("acepto");
                    System.out.println("Acepto");
                } else {
                  //Si no estamos interesados
                    System.out.println("Deniego");
                    reply.setContent("deniego");
                }
                myAgent.send(reply);
            }
            //Me informan de que ha terminado la subasta y ¿de quién es el ganador?
            else if(msg!=null && msg.getPerformative()==ACLMessage.INFORM){

            }
            //Gane la subasta
            else if(msg!=null && msg.getPerformative()==ACLMessage.REQUEST){

            }
            else{
                System.out.println("Bloqueado");
                block();
            }
            fase++;
        }

        @Override
        public boolean done() {
            return fase==3;
        }
    }



}
