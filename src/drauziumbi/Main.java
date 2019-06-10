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

        Patient[] zumbis = new Patient[numeroZumbis];
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


        for (int k = 0; k < numeroPacientes; k++){
            ArrayList<Patient> myList = new ArrayList<>(Arrays.asList(vetorPacientes));
            Patient pacienteAdicionado = zumbis[rand.nextInt(10)];
            pacienteAdicionado.crescer(rand.nextInt(4));
            myList.add(pacienteAdicionado);
            vetorPacientes = myList.toArray(vetorPacientes);
        }
    }

    public static void main(String[] args) {
        IDataSet dataset = new DataSetComponent();
        dataset.setDataSource("roteiros-auxiliares/db/zombie-health-new-cases500.csv");

        gerarPacientes(50, 10, dataset);

        protocoloUrgencia urg = Factory.criarUrgencia();
        vetorPacientes = urg.listaAtendimento(vetorPacientes);

        int i = 0;
        for (Patient pac : vetorPacientes){
            System.out.println("Paciente " + i + ": Urgencia = " + pac.getUrgencia() +
                                ", Idade = " + pac.getIdade() + ", Doenca = " +
                                pac.getGabarito()[pac.getGabarito().length-1]);
            i++;
        }

        IDoctor doctor = new Doctor();
        doctor.connect(dataset);
        Prontuario prontuario = Factory.criairProntuario();

        for (Patient a: vetorPacientes){
            prontuario.serializar(a, a.getGabarito()[a.getGabarito().length-1]);
            doctor.connect(a);

            a.setUrgencia(urg.urgencia(urg.getListaSintomas(a)));
        }


        prontuario.serializar(a, a.getGabarito()[a.getGabarito().length-1]);
        doctor.connect(a);

        a.setUrgencia(urg.urgencia(urg.getListaSintomas(a)));
        String sintomas = new String();
        for(String sintoma: urg.getListaSintomas(a)){
            sintomas += sintoma + ", ";
        }
        String nomeDoenca = doctor.startInterview();

        IAnimationC animacao = new AnimationC();
        animacao.setDocName("DrauZiumbi Varella");
        animacao.setWindowName("Olha que BUNITU!");
        animacao.setPacientName(a.getNome());
        String[] falas = new String[]{"", "Oi Doutor DrauZiumbi!", "Como você vai " + a.getNome() + "?", "Vou meio mal, tenho muitos sintomas: *" + sintomas.substring(0, sintomas.length() - 2) + "*", "Tudo bem, a prioridade do seu atendimento será: *" + urg.getStringUrgencia(a) + "*", "No atendimento...", "Então doutor, o que eu tenho?", "Bom " + a.getNome() + " você está com *" + nomeDoenca + "*"};
        String[] personagens = new String[]{"", "pacient", "doctor", "pacient", "doctor", "", "pacient", "doctor"};
        animacao.story(falas, personagens);

        Grafico grafico1 =  Factory.criarGrafico(vetorPacientes);
        Grafico grafico2 =  Factory.criarGrafico(vetorPacientes);
        //Controi o grafico que vc quiser
        grafico1.contrutorGraficoDoenca();
        grafico2.contrutorGraficoSintoma();

    }

}
