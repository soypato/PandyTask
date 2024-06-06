package excepciones.deTarea;

public class TareaInvalidaExcpetion extends Exception{
    private String mensaje;

    public TareaInvalidaExcpetion(String mensaje) {
        this.mensaje = mensaje;
    }
    public String getMensaje() {
        return mensaje;
    }
}
