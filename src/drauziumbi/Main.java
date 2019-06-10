package drauziumbi;
import pt.clubedohardware.userinterface.AnimationC;
import pt.clubedohardware.userinterface.IAnimationC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static Patient[] vetorPacientes = new Patient[0];

    public static void gerarPacientes(int numeroPacientes, int numeroZumbis, ITableProducer dataset){
        Random rand = new Random();

        //Setando os zumbis modelos
        Patient[] zumbis = new Patient[numeroZumbis];
        zumbis[0] = new Patient(dataset);
        zumbis[1] = new Patient(dataset);
        zumbis[2] = new Patient(dataset);
        zumbis[3] = new Patient(dataset);
        zumbis[4] = new Patient(dataset);
        zumbis[5] = new Patient(dataset);
        zumbis[6] = new Patient(dataset);
        zumbis[7] = new Patient(dataset);
        zumbis[8] = new Patient(dataset);
        zumbis[9] = new Patient(dataset);
        zumbis[0].setNome("Kina");
        zumbis[1].setNome("Genial");
        zumbis[2].setNome("Manu");
        zumbis[3].setNome("Du");
        zumbis[4].setNome("Boleto");
        zumbis[5].setNome("Mafia");
        zumbis[6].setNome("Norbit");
        zumbis[7].setNome("Walker");
        zumbis[8].setNome("Rafa");
        zumbis[9].setNome("Chico");
        zumbis[0].setCPF(11);
        zumbis[1].setCPF(132);
        zumbis[2].setCPF(1326);
        zumbis[3].setCPF(489);
        zumbis[4].setCPF(7777);
        zumbis[5].setCPF(704);
        zumbis[6].setCPF(54765);
        zumbis[7].setCPF(76521);
        zumbis[8].setCPF(7832);
        zumbis[9].setCPF(333);

        //crescendo eles aleatoriamente
        for(Patient pacienteAdicionado: zumbis)
            pacienteAdicionado.crescer(rand.nextInt(3));

        //criando o vetor com varios pacientes
        for (int k = 0; k < numeroPacientes; k++){
            ArrayList<Patient> myList = new ArrayList<>(Arrays.asList(vetorPacientes));
            Patient pacienteAdicionado = new Patient(dataset);
            Patient modelo = zumbis[rand.nextInt(10)];
            pacienteAdicionado.setNome(modelo.getNome());
            pacienteAdicionado.setCPF(modelo.getCPF());
            pacienteAdicionado.crescer(modelo.getIdade()-1);
            myList.add(pacienteAdicionado);
            vetorPacientes = myList.toArray(vetorPacientes);
        }
    }

    public static void main(String[] args) {
        //setando o dataset
        IDataSet dataset = new DataSetComponent();
        dataset.setDataSource("roteiros-auxiliares/db/zombie-health-new-cases500.csv");

        gerarPacientes(50, 10, dataset);

        protocoloUrgencia urg = Factory.criarUrgencia();

        //ordenando o vetor por urgencia
        vetorPacientes = urg.listaAtendimento(vetorPacientes);
        for (Patient pac : vetorPacientes){
            System.out.println("Paciente " + pac.getNome() + ": Urgencia = " + pac.getUrgencia() +
                                ", Idade = " + pac.getIdade() + ", Doenca = " +
                                pac.getGabarito()[pac.getGabarito().length-1]);
        }

        //setando o doutor
        IDoctor doctor = new Doctor();
        doctor.connect(dataset);
        Prontuario prontuario = Factory.criairProntuario();


        //atendimento e serializacao dos pacientes
        String resposta;
        int i = 0;
        for (Patient a: vetorPacientes){
            doctor.connect(a);
            resposta = doctor.startInterview();
            prontuario.serializar(a, resposta);
        }

        //simula um atendimento com o texto
        Random rand = new Random();
        Patient a = vetorPacientes[rand.nextInt(vetorPacientes.length)];

        String sintomas = new String("");
        for(String sintoma: urg.getListaSintomas(a)){
            sintomas += sintoma + ", ";
        }

        IAnimationC animacao = new AnimationC();
        animacao.setDocName("DrauZiumbi Varella");
        animacao.setWindowName("Atendimento");
        animacao.setPacientName(a.getNome());
        doctor.connect(a);
        String[] falas = new String[]{"", "Oi Doutor DrauZiumbi!", "Como você vai " + a.getNome() + "?", "Vou meio mal, tenho muitos sintomas: *" + sintomas.substring(0, sintomas.length() - 2) + "*", "Tudo bem, a prioridade do seu atendimento será: *" + urg.getStringUrgencia(a) + "*", "No atendimento...", "Então doutor, o que eu tenho?", "Bom " + a.getNome() + " você está com *" + doctor.startInterview() + "*"};
        String[] personagens = new String[]{"", "pacient", "doctor", "pacient", "doctor", "", "pacient", "doctor"};
        animacao.story(falas, personagens);


        //Gera os graficos do nosso vetor
        Grafico grafico1 =  Factory.criarGrafico(vetorPacientes);
        Grafico grafico2 =  Factory.criarGrafico(vetorPacientes);
        //Controi o grafico que vc quiser
        grafico1.contrutorGraficoDoenca();
        grafico2.contrutorGraficoSintoma();

    }

}
