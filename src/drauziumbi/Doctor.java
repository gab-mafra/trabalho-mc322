package drauziumbi;

interface IEnquirer {
    public String startInterview();
}

// combinando duas interfaces para IPatient
interface IDoctor extends IEnquirer, IResponderReceptacle, ITableProducerReceptacle {
}

public class Doctor implements IDoctor {

    private ITableProducer producer;
    private IResponder responder;

    public void connect(ITableProducer producer) {
        this.producer = producer;
    }

    public void connect(IResponder responder) {
        this.responder = responder;
    }

    public String startInterview() {
        String[] attributes = producer.requestAttributes();
        String[][] instances = producer.requestInstances();

        String[] sintomas = new String[attributes.length];

        for (int a = 0; a < attributes.length - 1; a++){
            sintomas[a] = responder.ask(attributes[a]);
        }
        int i;
        for (i=0; i < instances.length; i++){
            for (int a=0; a < attributes.length-1; a++){
                if (!sintomas[a].equals(instances[i][a]))
                    break;

                else{
                    if (a == attributes.length-2){
                        System.out.println("Disease guess: " + instances[i][attributes.length - 1]);
                        boolean result = responder.finalAnswer(instances[i][attributes.length - 1]);
                        System.out.println("Result: " + (result ? "I am right =)" : "I am wrong =("));
                        return instances[i][attributes.length - 1];
                    }
                }
            }
        }
        return instances[i][attributes.length - 1];
    }

}