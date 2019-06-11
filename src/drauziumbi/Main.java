package drauziumbi;
import pt.clubedohardware.userinterface.AnimationC;
import pt.clubedohardware.userinterface.IAnimationC;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Main {

    private static Patient[] vetorPacientes = new Patient[0]; //instancia o array nulo para depois preenchê-lo

    private static Patient[] gerarPacientes(int numeroPacientes, int numeroZumbis, ITableProducer dataset){
        Random rand = new Random();

        //Setando os zumbis modelos
        Patient[] zumbis = new Patient[numeroZumbis]; //cria um vetor de pacientes chamado "zumbis"
        //setando a doença do paciente
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
            ArrayList<Patient> myList = new ArrayList<>(Arrays.asList(vetorPacientes)); //transformando o vetorPacientes num arraylist
            Patient pacienteAdicionado = new Patient(dataset);
            Patient modelo = zumbis[rand.nextInt(10)]; //os zumbis criados acima serão "clonados" aleatóriamente num arraylist
            pacienteAdicionado.setNome(modelo.getNome());
            pacienteAdicionado.setCPF(modelo.getCPF());
            pacienteAdicionado.crescer(modelo.getIdade()-1);
            myList.add(pacienteAdicionado);
            vetorPacientes = myList.toArray(vetorPacientes);
        }

        return zumbis; //retorna um vetor de pacientes zumbis
    }

    public static void main(String[] args) {
        //setando o dataset
        IDataSet dataset = new DataSetComponent();
        dataset.setDataSource("roteiros-auxiliares/db/zombie-health-new-cases500.csv"); //puxa o arquivo csv que usaremos para realizar as consultas

        Patient[] zumbis = gerarPacientes(700, 10, dataset); //numeroZumbis é a quantidade de modelos de pacientes que teremos, numeroPacientes serão o total de pacientes

        protocoloUrgencia urg = Factory.criarUrgencia(); //chama um protocolo de urgencia que ajudará a classificar a urgencia de atendimento dos pacientes

        //ordenando o vetor de pacientes por urgencia dos sintomas apresentados
        vetorPacientes = urg.listaAtendimento(vetorPacientes);
        for (Patient pac : vetorPacientes){
            //imprime no console o nome do paciente, seguido da sua urgencia de atendimento, idade e doença apresentada
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
        for (Patient a: vetorPacientes){
            doctor.connect(a);
            resposta = doctor.startInterview(); //começa as perguntas (anamnese)
            prontuario.serializar(a, resposta); //arquiva os dados do paciente - nome,idade e cpf seguidos das doenças apresentadas e sua recorrência
        }

        //simula um atendimento com o texto
        //seleciona um paciente para a simulacao
        Random rand = new Random();
        Patient a = vetorPacientes[rand.nextInt(vetorPacientes.length)];

        //organiza os sintomas para uma string para o atendimento
        String sintomas = new String("");
        for(String sintoma: urg.getListaSintomas(a)){
            sintomas += sintoma + ", ";
        }

        //simula o atendimento em uma animação
        IAnimationC animacao = new AnimationC();
        animacao.setDocName("DrauZiumbi Varella");
        animacao.setWindowName("Atendimento");
        animacao.setPacientName(a.getNome());
        doctor.connect(a);
        String[] falas = new String[]{"", "Oi Doutor DrauZiumbi!", "Como você vai " + a.getNome() + "?", "Vou meio mal, tenho muitos sintomas: *" + sintomas.substring(0, sintomas.length() - 2) + "*", "Tudo bem, a prioridade do seu atendimento será: *" + urg.getStringUrgencia(a) + "*", "No atendimento...", "Então doutor, o que eu tenho?", "Bom " + a.getNome() + " você está com *" + doctor.startInterview() + "*"};
        String[] personagens = new String[]{"", "pacient", "doctor", "pacient", "doctor", "", "pacient", "doctor"};
        animacao.story(falas, personagens);


        //Instancia os gráficos a partir do vetor de pacientes
        Grafico grafico1 =  Factory.criarGrafico(vetorPacientes);
        Grafico grafico2 =  Factory.criarGrafico(vetorPacientes);
        //Controi os gráficos de recorrência de doenças e sintomas
        grafico1.contrutorGraficoDoenca();
        grafico2.contrutorGraficoSintoma();

        for(Patient paciente: zumbis){
            System.out.println("");
            prontuario.maisRecorrente(paciente); //a partir do prontuário do seu paciente é encontrada a doença mais recorrente ao mesmo
        }

    }

}
