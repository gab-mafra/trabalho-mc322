/**
 * Doenca
 */
public class Doenca {

    private String nome;
    private int recorrencia;

    public Doenca(String nome){
        this.nome = nome;
        this.recorrencia = 0;
    }
    /**
     * @return the nome
     */
    public String getNome() {
        return nome;
    }
    /**
     * @return the recorrencia
     */
    public int getRecorrencia() {
        return recorrencia;
    }
    public void aumentar(int mais ){
        this.recorrencia = mais;
    }
    @Override
    public String toString() {
        return this.nome + " " + Integer.toString(this.recorrencia);
    }

}