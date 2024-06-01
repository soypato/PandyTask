package excepciones.deTarea;
// Sirve para controlar que el usuario ponga un numero del 1 al 10 la calific. de la tarea.
public class CalificacionException extends Exception{
    String mensaje;

    public CalificacionException(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getMensaje() {
        return mensaje;
    }
}
