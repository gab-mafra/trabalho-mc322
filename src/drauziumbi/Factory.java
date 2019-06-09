package drauziumbi;
public class Factory {
    public protocoloUrgencia criarUrgencia(){
        return new protocoloUrgencia();
    }

    public Grafico criarGrafico(Patient[] pacientes){
        return new Grafico(pacientes);
    }

    public Prontuario criairProntuario(){
        return new Prontuario();
    }

    public MergeSort criarMergeSort(){
        return new MergeSort();
    }

}
