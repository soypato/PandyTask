package modelo.tareas;

import modelo.extra.Receta;

public class SeccionCocina extends Tarea{
    private Receta receta;

    public SeccionCocina(Receta receta)
    {
        this.receta = receta;
    }

    public SeccionCocina()
    {
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
}
