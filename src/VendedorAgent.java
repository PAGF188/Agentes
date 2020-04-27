import jade.core.AID;
import jade.core.Agent;
import jade.core.behaviours.Behaviour;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import jade.lang.acl.ACLMessage;

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

        @Override
        public void action() {
            //Para cada subasta
            for (Map.Entry<String, Integer> entry : libros.entrySet()) {
                //Consultamos páginas amarillas de la subasta i
                DFAgentDescription template = new DFAgentDescription();
                ServiceDescription sd = new ServiceDescription();
                sd.setType("subasta");
                sd.setName(entry.getKey());
                template.addServices(sd);
                template.addServices(sd);
                try {
                    DFAgentDescription[] result = DFService.search(myAgent, template);
                    System.out.println("Found the following seller agents:");
                    ArrayList<AID> participantes = new ArrayList<>();
                    for (int i = 0; i < result.length; ++i) {
                        participantes.add(result[i].getName());
                        System.out.println(participantes.get(i).getName());
                    }

                    //Enviamos cfp a todos los participantes
                    ACLMessage cfp = new ACLMessage(ACLMessage.CFP);
                    for (int i = 0; i < participantes.size(); ++i) {
                        cfp.addReceiver(participantes.get(i));
                    }
                    cfp.setContent(Integer.toString(entry.getValue()));
                    cfp.setConversationId(entry.getKey());
                    cfp.setReplyWith("cfp"+System.currentTimeMillis()); // Unique value
                    myAgent.send(cfp);

                }
                catch (Exception fe) {
                    fe.printStackTrace();
                }

            }
            fase++;
        }

        @Override
        public boolean done() {
            return fase==3;
        }
    }
}
