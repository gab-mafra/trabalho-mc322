import java.util.Random;
import java.lang.Math;

/**
 * Zumbi
 */
public class Zumbi {

    private String nome;
    private int idade;
    private int CPF;

    public Zumbi(String nome,int idade){
        this.nome = nome;
        this.idade = idade;
        this.CPF = 44;//new Random().nextInt();
    }
    /**
     * @return the cPF
     */
    public int getCPF() {
        return CPF;
    }
    /**
     * @return the idade
     */
    public int getIdade() {
        return idade;
    }
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
}