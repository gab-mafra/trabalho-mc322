import java.io.*;
import java.util.*;
/**
 * Componente
 */
public class Componente {

    public void  print(Zumbi zumbi) {
        try {
            // Abre o arquivo txt 
            String nomeZumbi = zumbi.getNome();
            String idadeZumbi = Integer.toString(zumbi.getIdade());
            String CPFZumbi = Integer.toString(zumbi.getCPF());
            FileReader arquivo = new FileReader(nomeZumbi + "_" + CPFZumbi + ".txt");
            BufferedReader formato = new BufferedReader(arquivo);

            // Lista com as doencas que o paciente ja contrariu
            ArrayList<Doenca> doencas = new ArrayList<Doenca>(); 
            // Despreza a primeira linha com os dados dos pacientes
            formato.readLine();
            while (formato.ready()) {
                String[] linha = formato.readLine().split(",");
                int recorrencia =  Integer.parseInt(linha[0]);
                Doenca doenca = new Doenca(linha[1]);
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
            System.out.println("Nome do Paciente " + nomeZumbi);
            System.out.println("Idade do Paciente " + idadeZumbi);
            System.out.println("Identificador do Paciente " + CPFZumbi);
            System.out.println("Doenças que o paciente já contraiu:");
            for (Doenca d : doencas) {
                System.out.println(d.toString());
            }
            arquivo.close();
        } catch (IOException erro) {
            System.err.println("O pacinete em questão não tem ficha médica.");
            //erro.printStackTrace();
        }
    }

}