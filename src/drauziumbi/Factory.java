package drauziumbi;
public class Factory {
    public static protocoloUrgencia criarUrgencia(){
        return new protocoloUrgencia();
    }

    public static Grafico criarGrafico(Patient[] pacientes){
        return new Grafico(pacientes);
    }

    public static Prontuario criairProntuario(){
        return new Prontuario();
    }

}
