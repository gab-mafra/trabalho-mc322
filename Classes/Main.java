import java.util.ArrayList;
import java.util.Arrays;

public class Main {

    private static Patient vetorPacientes[] = new Patient[0];

    public static void gerarPacientes(int numeroPacientes, ITableProducer dataset){
        for (int k = 0; k < numeroPacientes; k++){
            ArrayList<Patient> myList = new ArrayList<>(Arrays.asList(vetorPacientes));
            Patient pacienteAdicionado = new Patient(dataset);
            myList.add(pacienteAdicionado);
            vetorPacientes = myList.toArray(vetorPacientes);
        }
    }

    public static void main(String[] args) {
        IDataSet dataset = new DataSetComponent();
        dataset.setDataSource("zombie-health-new-cases500.csv");

        gerarPacientes(1000, dataset);

        Grafico grafico =  new Grafico(vetorPacientes);
        //Controi o grafico que vc quiser
        grafico.contrutorGraficoDoenca();
        grafico.contrutorGraficoSintoma();

    }

}
