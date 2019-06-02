/*Classe: /Classes/protocoloUrgencia.java

Autores:
Gabriel de Souza Mafra - RA 197272
Manuela Rafael Onofre de Souza - RA 202534
Victor Toon de Araujo - RA 225231
Gabriel Batista Moura - RA 216101
Augusto Piato Amstalden - RA 213364
Daniel Cardoso Custodio de Oliveira - RA 169400

Objetivo:

Esse componente recebe o vetor de strings que representa os sintomas do Paciente e retorna o nível de urgência de seu atendimento, variando entre "máxima", "alta", "média" ou "baixa", priorizando zumbis idosos e crianças, de forma similar aos pronto-socorros do Brasil.

Interface:
*/
public interface IProtocoloUrgencia extends Grafico {
    public int urgencia(String[] sintomas); //classifica as doenças em prioridade
    public Patient[] listaAtendimento(Patient[] pacientes) //cria uma lista de atendimento de acordo com a prioridade
}

public class protocoloUrgencia implements IProtocoloUrgencia{
    public int urgencia(String[] sintomas){
        int prioridade = 0;
        //os sintomas que não estão sendo checados tem prioridade 0
        for (int i =0; i++; i<sintomas.length ){
            if (sintomas[i].equals("paralysis"){
                if (prioridade < 1){
                    prioridade = 1;
                }
            }

            else if (sintomas[i].equals("member_loss")){
                if (prioridade < 3){
                    prioridade = 3;
                }
            }
            else if (sintomas[i].equals("chest_pain")){
                if (prioridade < 3){
                    prioridade = 3;
                }
            }


            else if (sintomas[i].equals("history_bacteria")){
                if (prioridade < 1){
                    prioridade = 1;
                }
            }

            else if (sintomas[i].equals("fever")){
                if (prioridade < 2){
                    prioridade = 2;
                }
            }
        }
        return prioridade;

    }

    public Patient[] listaAtendimento(Patient[] pacientes){

        MergeSort ms = new MergeSort;
        Patient[] lista = new Patient[pacientes.length];

        for(int i = 0; i < pacientes.length; i++)
            pacientes[i].urgencia = urgencia(pacientes.sintomas);

        ms.mergeSort(pacientes, pacientes.length);

        return pacientes;
    }
}