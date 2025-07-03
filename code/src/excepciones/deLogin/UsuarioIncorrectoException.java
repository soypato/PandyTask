package excepciones.deLogin;

public class UsuarioIncorrectoException extends LoginIncorrectoException{
    private String mensaje;

    public UsuarioIncorrectoException(String mensaje) {
        super(mensaje);
    }

    @Override
    public String getMensaje() {
        return mensaje;
    }
}
