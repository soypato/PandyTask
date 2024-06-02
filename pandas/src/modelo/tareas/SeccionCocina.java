package modelo.tareas;

import modelo.extra.Receta;

public class SeccionCocina extends Tarea{
    private Receta receta;

    public SeccionCocina(String titulo, String objetivo, String codigo, int temporizador, String fecha, Receta receta)
    {
        super(titulo, objetivo, codigo, temporizador, fecha, "SeccionCocina");
        this.receta = receta;
    }

    public SeccionCocina()
    {
        super("SeccionCocina");
        this.receta = new Receta();
    }

    @Override
    public int hashCode() {
        return 1;
    }

    @Override
    public String toString() {
        return "SeccionCocina{\n" + super.toString() + " " +
                "receta=" + receta +
                '}';
    }

    public Receta getReceta()
    {
        return  receta;
    }
}
