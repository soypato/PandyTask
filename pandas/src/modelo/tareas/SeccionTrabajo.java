package modelo.tareas;


public class SeccionTrabajo extends Tarea
{
    private String categoria;
    private String materia;
    private String unidad;

    // CONSTRUCTORES
    public SeccionTrabajo(String titulo, String objetivo, String categoria, String materia, String unidad) {
        super(titulo, objetivo);
        this.categoria = categoria;
        this.materia = materia;
        this.unidad = unidad;
    }

    public SeccionTrabajo()
    {
        categoria="";
        materia="";
        unidad="";
    }

    // GETTERS

    public String getCategoria() {
        return categoria;
    }

    public String getMateria() {
        return materia;
    }

    public String getUnidad() {
        return unidad;
    }

    // SETTERS

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public void setMateria(String materia) {
        this.materia = materia;
    }

    public void setUnidad(String unidad) {
        this.unidad = unidad;
    }
}
