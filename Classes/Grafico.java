/*Classe: /Classes/Grafico.java
Autores:
Gabriel de Souza Mafra - RA 197272
Manuela Rafael Onofre de Souza - RA 202534
Victor Toon de Araujo - RA 225231
Gabriel Batista Moura - RA 216101
Augusto Piato Amstalden - RA 213364
Daniel Cardoso Custodio de Oliveira - RA 169400
Objetivo:
O objetivo do componente é criar um gráfico com a frequência de doenças diagnosticadas e outro com a frequencia de sintomas identificados a fim de guiar a anamnsese médica para doenças mais comuns para facilitar o diagnóstico.
Interface:
*/


//importando a biblioteca de numeros aleatorios
import java.awt.desktop.SystemSleepEvent;
import java.util.Random;
//importando as bibliotecas usadas pelo DataSet
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//------------------------------------------------------------------------------------------------//
import java.util.ArrayList;
import java.lang.String;
import java.util.Arrays;

//importando a biblioteca de gráfico
import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
//-------------------------------------------------------------------------------------------------//
interface IDataSource {
    public String getDataSource();
    public void setDataSource(String dataSource);
}

//Essa interface define os metodos que geram a tabela de atributos e instancias do Data Set
interface ITableProducer {
    String[] requestAttributes();
    String[][] requestInstances();
}

//Essa interface faz a conexao com a interface ITableProducer
interface ITableProducerReceptacle {
    public void connect(ITableProducer producer);
}

//Combinando as interfaces do DataSet em IDataSet
interface IDataSet extends IDataSource, ITableProducer {
}

class DataSetComponent implements IDataSet {
    private String dataSource = null;
    private String[] attributes = null;
    private String[][] instances = null;

    public DataSetComponent() {
        /* nothing */
    }

    public String getDataSource() {
        return dataSource;
    }

    public void setDataSource(String dataSource) {
        this.dataSource = dataSource;
        if (dataSource == null) {
            attributes = null;
            instances = null;
        } else
            readDS();
    }

    public String[] requestAttributes() {
        return attributes;
    }

    public String[][] requestInstances() {
        return instances;
    }

    private void readDS() {
        ArrayList<String[]> instArray = new ArrayList<String[]>();
        try {
            BufferedReader file = new BufferedReader(new FileReader(dataSource));

            String line = file.readLine();
            if (line != null) {
                attributes = line.split(",");
                line = file.readLine();
                while (line != null) {
                    String[] instLine = line.split(",");
                    instArray.add(instLine);
                    line = file.readLine();
                }
                instances = instArray.toArray(new String[0][]);
            }

            file.close();
        } catch (IOException erro) {
            erro.printStackTrace();
        }
    }

}

interface IResponder {
    public String ask(String question);
    public boolean finalAnswer(String answer);
}

//Essa interface faz a conexao com a interface IResponder
interface IResponderReceptacle {
    public void connect(IResponder responder);
}

//Combinando as interfaces do Patient em IPatient
interface IPatient extends IResponder, ITableProducerReceptacle {
}

class Patient implements IPatient{
    private String[]   gabarito;
    private String[]   attributes;
    private ITableProducer ds;
    private int doenca;
    private Random rand;

    //funcao nova

    //-------------------------------------------------------------------------------------------------//
    public String[] getGabarito() {
        return gabarito;
    }

    public String[] getAttributes(){
        return attributes;
    }
    //-------------------------------------------------------------------------------------------------//
    public Patient(ITableProducer producer){
        connect(producer);
        rand = new Random();
        attributes = ds.requestAttributes();
        doenca = rand.nextInt(producer.requestInstances().length);
        gabarito = ds.requestInstances()[doenca];
    }

    public String ask(String question){
        for(int i = 0; i < attributes.length; i++)
            if(question == attributes[i])
                return gabarito[i];
        return "unknown";
    }

    public boolean finalAnswer(String answer){
        if(gabarito[gabarito.length-1].equals(answer))
            return true;
        else
            return false;
    }

    public void connect(ITableProducer producer){
        ds = producer;
    }
}

//-------------------------------------------------------------------------------------------------//
class Doencas{
    private String nome;
    private int frequencia;

    public Doencas(String nomeNovo){
        this.nome = nomeNovo;
        this.frequencia = 1;
    }

    public void aumentaFrequencia(){
        this.frequencia++;
    }

    public int getFrequencia(){
        return this.frequencia;
    }

    public String getNome(){
        return this.nome;
    }
}

class Sintomas{
    private String nome;
    private int frequencia;

    public Sintomas(String nomeNovo){
        this.nome = nomeNovo;
        this.frequencia = 0;
    }

    public void aumentaFrequencia(){
        this.frequencia++;
    }

    public int getFrequencia(){
        return this.frequencia;
    }

    public String getNome(){
        return this.nome;
    }
}

public class Grafico extends JFrame{

    private Doencas vetorDoencas[] = new Doencas[0];
    private Sintomas vetorSintomas[] = new Sintomas[0];

    //Verifica e adiciona se tem a doenca no vetor, se nao tiver, instancia uma nova doenca
    private void verificaAdicionaDoenca(Doencas[] vetorDoenca, String doencaProcura){
        int contador = 0;
        for (int a = 0; a < vetorDoenca.length; a++){
            if (vetorDoenca[a].getNome().equals(doencaProcura)) {
                vetorDoenca[a].aumentaFrequencia();
                contador = 13;
                break;
            }
        }
        if (contador == 0){
            ArrayList<Doencas> myList = new ArrayList<>(Arrays.asList(vetorDoenca));
            Doencas doencaAdicionada = new Doencas(doencaProcura);
            myList.add(doencaAdicionada);
            vetorDoencas = myList.toArray(vetorDoenca);
        }
    }

    private void verificaAdicionaSintoma(Sintomas[] vetorSintomas, Patient paciente){
        //System.out.println(paciente.getGabarito()[0]);
        for (int c = 0; c < paciente.getGabarito().length; c++){
            if (paciente.getGabarito()[c].equals("1")){
                vetorSintomas[c].aumentaFrequencia();
            }
        }
    }

    public Grafico(Patient[] pacientes){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Gráfico Barra");
        setSize(950,700);
        setLocationRelativeTo(null);

        for (int b = 0; b < pacientes.length; b++) {
            verificaAdicionaDoenca(vetorDoencas, pacientes[b].getGabarito()[pacientes[b].getGabarito().length-1]);
        }

        //instanciando o vetor de sintomas
        for (int z = 0; z < pacientes[1].getAttributes().length; z++){
            ArrayList<Sintomas> myList = new ArrayList<>(Arrays.asList(vetorSintomas));
            Sintomas sintomaAdicionado = new Sintomas(pacientes[1].getAttributes()[z]);
            myList.add(sintomaAdicionado);
            vetorSintomas = myList.toArray(vetorSintomas);
        }

        for (int b = 0; b < pacientes.length; b++) {
            verificaAdicionaSintoma(vetorSintomas, pacientes[b]);
        }
        //so nao sei se isso cria dois graficos ou um sobrescreve o outro

    }

    public void contrutorGraficoDoenca(){
        criarGraficoDoencas(vetorDoencas);
        setVisible(true);
    }

    public void contrutorGraficoSintoma(){
        criarGraficoSintomas(vetorSintomas);
        setVisible(true);
    }

    //cria um grafico com as frequencias das doenças
    //lembrar de por Patient[] pacientes como parametro depois
    private void criarGraficoDoencas(Doencas[] doencas){
       DefaultCategoryDataset barra = new DefaultCategoryDataset();
       for (int j = 0; j < doencas.length; j++){
           barra.setValue(doencas[j].getFrequencia(), doencas[j].getNome(), "");
        }
       //tem que mudar os valores de A,B e C pra deixar melhor o grafico depois
       JFreeChart grafico = ChartFactory.createBarChart("A", "B", "C", barra, PlotOrientation.VERTICAL, true, true, false);
       ChartPanel painel = new ChartPanel(grafico);
       add(painel);
    }

    //cria um grafico com as frequencias das sintomas
    private void criarGraficoSintomas(Sintomas[] sintomas){
        DefaultCategoryDataset barraSintoma = new DefaultCategoryDataset();
        for (int j = 0; j < sintomas.length -1; j++){
            barraSintoma.setValue(sintomas[j].getFrequencia(), sintomas[j].getNome(), "");
        }
        //tem que mudar os valores de A,B e C pra deixar melhor o grafico depois
        JFreeChart grafico = ChartFactory.createBarChart("A", "B", "C", barraSintoma, PlotOrientation.VERTICAL, true, true, false);
        ChartPanel painelSintoma = new ChartPanel(grafico);
        add(painelSintoma);
    };
}
//-------------------------------------------------------------------------------------------------//