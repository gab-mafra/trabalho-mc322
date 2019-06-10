package drauziumbi;
import java.util.ArrayList;

/*Classe: /Classes/protocoloUrgencia.java

Autores:
Gabriel de Souza Mafra - RA 197272
Manuela Rafael Onofre de Souza - RA 202534
Victor Toon de Araujo - RA 225231
Gabriel Batista Moura - RA 216101
Augusto Piato Amstalden - RA 213364
Daniel Cardoso Custodio de Oliveira - RA 169400

Objetivo:

Esse componente recebe o vetor de strings que representa os sintomas do Paciente
e retorna o nível de urgência de seu atendimento, variando entre "máxima", "alta", "média" ou "baixa",
priorizando zumbis idosos e crianças, de forma similar aos pronto-socorros do Brasil.

Metodos:

    - urgencia: classifica a urgencia de atendimento do paciente
        - retorno: inteiro que representa a urgencia do paciente
        - parametros: vetor de strings com o nome de cada sintoma do paciente
    - getListaSintomas: gera um vetor com o nome dos sintomas do paciente
        - retorno: vetor de strings que contem o nome dos sintomas do paciente
        - parametros: o paciente que sera a fonte do vetor
    - listaAtendimento: gera uma lista de atendimento ordenada em urgencia
        - retorno: vetor de pacientes ordenado em urgencia (desempate na idade, com idosos e criancas como prioridade nessa ordem)
        - parametros: vetor de pacientes a ser ordenado
*/
interface IProtocoloUrgencia{
    public int urgencia(String[] sintomas); //classifica as doenças em prioridade
    public Patient[] listaAtendimento(Patient[] pacientes); //cria uma lista de atendimento de acordo com a prioridade
}

public class protocoloUrgencia implements IProtocoloUrgencia{
    public int urgencia(String[] sintomas){
        int prioridade = 0;
        //os sintomas que não estão sendo checados tem prioridade 0
        for (int i = 0; i<sintomas.length; i++) {
            if (sintomas[i].equals("Paralisia")) {
                if (prioridade < 1) {
                    prioridade = 1;
                }
            } else if (sintomas[i].equals("Perda de Membro")) {
                if (prioridade < 3) {
                    prioridade = 3;
                }
            } else if (sintomas[i].equals("Dor no Peito")) {
                if (prioridade < 3) {
                    prioridade = 3;
                }
            } else if (sintomas[i].equals("Pele Azul")) {
                if (prioridade < 1) {
                    prioridade = 1;
                }
            } else if (sintomas[i].equals("Febre")) {
                if (prioridade < 2) {
                    prioridade = 2;
                }
            }
        }
        return prioridade;

    }

    public String getStringUrgencia(Patient paciente){
        switch (paciente.getUrgencia()){
            case 0:
                return "baixa";

            case 1:
                return "média";

            case 2:
                return "alta";

            case 3:
                return "MÁXIMA";
        }

        return "sei lá bixo";
    }

    public String[] getListaSintomas(Patient paciente){
        ArrayList<String> lista = new ArrayList<String>();
        String[] retorno = new String[0];

        int i = 0;
        for(String gab : paciente.getGabarito()){
            if(gab.equals("1"))
                lista.add(paciente.getAttributes()[i]);
            i++;
        }

        retorno = lista.toArray(retorno);
        return retorno;
    }

    public Patient[] listaAtendimento(Patient[] pacientes){

        MergeSort ms = new MergeSort();
        Patient[] lista = new Patient[pacientes.length];

        for(int i = 0; i < pacientes.length; i++)
            pacientes[i].setUrgencia(urgencia(getListaSintomas(pacientes[i])));

        MergeSort.mergeSort(pacientes, pacientes.length);

        return pacientes;
    }
}