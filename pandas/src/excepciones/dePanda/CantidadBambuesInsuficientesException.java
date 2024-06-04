package excepciones.dePanda;

public class CantidadBambuesInsuficientesException extends Exception{
    private String mensaje;
    public CantidadBambuesInsuficientesException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
