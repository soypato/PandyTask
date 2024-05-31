package excepciones;

public class ContrasenaIncorrectaException extends LoginIncorrectoException{
    private String mensaje;

    public ContrasenaIncorrectaException(String mensaje) {
        super(mensaje);
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
