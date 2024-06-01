package excepciones.deLogin;

// Clase padre de login
public abstract class LoginIncorrectoException extends Exception{
    private String mensaje;

    public LoginIncorrectoException(String mensaje) {
        this.mensaje = mensaje;
    }

    public abstract String getMensaje();
}
