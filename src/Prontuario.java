import java.io.*;
import java.util.*;

public class Prontuario {

    private static class Doenca {

        private String nome;
        private int recorrencia;

        private Doenca(String nome){
            this.nome = nome;
            this.recorrencia = 0;
        }
        /**
         * @return the nome
         */
        private String getNome() {
            return nome;
        }
        /**
         * @return the recorrencia
         */
        private int getRecorrencia() {
            return recorrencia;
        }
        private void aumentar(int mais ) {
            this.recorrencia = mais;
        }
        @Override
        public String toString() {
            return this.nome + " " + Integer.toString(this.recorrencia);
        }
    }

    public void serializar(Patient z, String doenca) {
        //Bloco try ira verificar se ja existe uma ficha para o paciente
        //Caso sim, ele ira adicionar a nova doença
        try {
            FileReader arquivo1 = new FileReader(z.getNome() + "_" + z.getCPF() + ".txt");
            BufferedReader formato1 = new BufferedReader(arquivo1);
            String linha = formato1.readLine();     //Ignora a primeira linha (onde as informaçoes do paciente se encontram)
            int maior = 1;
            linha = formato1.readLine();
            while(linha != null) {      //O bloco while ira encontrar o numero de casos ja ocorridos da doença que se deseja adicionar
                String[] texto = linha.split(",");
                if(texto[0].equals(doenca)) {
                    maior = Integer.parseInt(texto[1]) + 1;
                }
                linha = formato1.readLine();
            }
            arquivo1.close();
            FileWriter arquivo2 = new FileWriter(z.getNome() + "_" + z.getCPF() + ".txt", true);
            PrintWriter formato2 = new PrintWriter(arquivo2);
            formato2.println(doenca + "," + maior);     //Insere a doença nova com o numero de ocorrencias dela
            arquivo2.close();
        }
        //O bloco catch sera acionado caso aida nao exista um arquivo para o paciente
        catch(IOException e1) {
            try {
                FileWriter arquivo = new FileWriter(z.getNome() + "_" + z.getCPF() + ".txt");      //Um novo arquivo sera criado
                PrintWriter formato = new PrintWriter(arquivo);
                formato.println("Nome:" + z.getNome() + ",Idade:" + z.getIdade() + ",CPF:" + z.getCPF());
                formato.println(doenca + "," + 1);
                arquivo.close();
            }
            catch(IOException e2) {
                System.err.println("Erro ao inicializar um registro medico para o paciente " + z.getNome() + " CPF: " + z.getCPF() + "\nSem memoria");
                e1.printStackTrace();
            }
            catch(Exception er) {
                System.err.println("Erro desconhecido");
                er.printStackTrace();
                er.getMessage();
            }
        }
    }

    public void maisRecorrente(Patient z) {
        //Bloco try tentara ler o arquivo do paciente, caso ele exista
        try {
            FileReader arquivo = new FileReader(z.getNome() + "_" + z.getCPF() + ".txt");
            BufferedReader formato = new BufferedReader(arquivo);
            String linha = formato.readLine();
            int  maior = 0;
            String d = "a";
            linha = formato.readLine();
            while(linha != null) {      //O bloco while ira encontrar a doença mais recorrente, e o seu numero de incidencia
                String[] texto = linha.split(",");
                if(Integer.parseInt(texto[1]) >= maior) {
                    d = texto[0];
                    maior = Integer.parseInt(texto[1]);
                }
                linha = formato.readLine();
            }
            System.out.println("||Ficha medica||\n" +
                    "#Nome: " + z.getNome() + ";\n" +
                    "#Idade :" + z.getIdade() + "\n" +
                    "#CPF: " + z.getCPF() + ";\n" +
                    "A doença mais recorrente no paciente é " + d + ", com um numero de casos igual a " + maior + "\n" +
                    "Obs.: O paciente pode ter tido outras doenças tao recorrentes, \n" +
                    "porem essa foi a mais recente.");
        }
        //Esse catch ira rodar apenas se o paciente ainda nao tiver um arquivo (ficha medica)
        catch(IOException e) {
            System.err.println("||Erro||\n" +
                    "#Nome: " + z.getNome() + ";\n" +
                    "#Idade: " + z.getIdade() + "\n" +
                    "#CPF: " + z.getCPF() + ";\n" +
                    "O paciente em questao ainda nao possui uma ficha medica.");
        }
        catch(Exception err) {
            System.err.println("Erro desconhecido.");
            err.printStackTrace();
        }
    }

    public void print(Patient z) {
        try {
            // Abre o arquivo txt
            String nomeZumbi = z.getNome();
            FileReader arquivo = new FileReader(nomeZumbi + "_" + z.getCPF() + ".txt");
            BufferedReader formato = new BufferedReader(arquivo);

            // Lista com as doencas que o paciente ja contrariu
            ArrayList<Doenca> doencas = new ArrayList<Doenca>();
            // Despreza a primeira linha com os dados dos pacientes
            formato.readLine();
            while (formato.ready()) {
                String[] linha = formato.readLine().split(",");
                int recorrencia =  Integer.parseInt(linha[1]);
                Doenca doenca = new Doenca(linha[0]);
                doenca.aumentar(recorrencia);
                boolean pertence = false;
                for (Doenca d : doencas) {
                    if (doenca.getNome().equalsIgnoreCase(d.getNome())){
                        pertence =  true;
                        d.aumentar(recorrencia);
                    }
                }
                if(!pertence){
                    doencas.add(doenca);
                    pertence = false;
                }
            }
            System.out.println("||Ficha medica||");
            System.out.println("#Nome: " + nomeZumbi);
            System.out.println("#Idade: " + z.getIdade());
            System.out.println("#CPF: " + z.getCPF());
            System.out.println("Doenças contraidas pelo paciente: ");
            for (Doenca d : doencas) {
                System.out.println(d.toString());
            }
            arquivo.close();
        } catch (IOException erro) {
            System.err.println("||Erro||\n" +
                    "#Nome: " + z.getNome() + ";\n" +
                    "#Idade :" + z.getIdade() + "\n" +
                    "#CPF: " + z.getCPF() + ";\n" +
                    "O paciente em questao ainda nao possui uma ficha medica.");
        }
    }
}
