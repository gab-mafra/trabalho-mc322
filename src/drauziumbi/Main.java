package drauziumbi;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static Patient[] vetorPacientes = new Patient[0];

    public static void gerarPacientes(int numeroPacientes, ITableProducer dataset){
        Random rand = new Random();

        for (int k = 0; k < numeroPacientes; k++){
            ArrayList<Patient> myList = new ArrayList<>(Arrays.asList(vetorPacientes));
            Patient pacienteAdicionado = new Patient(dataset);
            pacienteAdicionado.crescer(rand.nextInt(4));
            myList.add(pacienteAdicionado);
            vetorPacientes = myList.toArray(vetorPacientes);
        }
    }

    public static void main(String[] args) {
        IDataSet dataset = new DataSetComponent();
        dataset.setDataSource("roteiros-auxiliares/db/zombie-health-new-cases500.csv");

        gerarPacientes(1000, dataset);

        protocoloUrgencia urg = new protocoloUrgencia();
        vetorPacientes = urg.listaAtendimento(vetorPacientes);

        int i = 0;
        for (Patient pac : vetorPacientes){
            System.out.println("Paciente " + i + ": Urgencia = " + pac.getUrgencia() +
                                ", Idade = " + pac.getIdade() + ", Doenca = " +
                                pac.getGabarito()[pac.getGabarito().length-1]);
            i++;
        }

        Patient a = new Patient(dataset);
        a.setNome("asdrubal");
        a.setCPF(111000);
        Prontuario prontuario = new Prontuario();
        prontuario.serializar(a, "virose");

        Grafico grafico1 =  new Grafico(vetorPacientes);
        Grafico grafico2 =  new Grafico(vetorPacientes);
        //Controi o grafico que vc quiser
        grafico1.contrutorGraficoDoenca();
        grafico2.contrutorGraficoSintoma();

    }

}
