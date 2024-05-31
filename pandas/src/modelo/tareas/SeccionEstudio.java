package modelo.tareas;


public class SeccionEstudio extends Tarea
{
    private String categoria;
    private String materia;
    private String unidad;

    // CONSTRUCTORES
    public SeccionEstudio(String titulo, String objetivo, String categoria, String materia, String unidad) {
        super(titulo, objetivo);
        this.categoria = categoria;
        this.materia = materia;
        this.unidad = unidad;
    }

    public SeccionEstudio()
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

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        boolean res = false;
        if (obj != null) {
            if (obj instanceof SeccionEstudio estudioTmp) {
                if (estudioTmp.getTitulo().equals(this.getTitulo())
                        && estudioTmp.getObjetivo().equals(this.getObjetivo())
                ) {
                    res = true;
                }
            }
        }
        return res;
    }

    public int compareTo(Object obj) {
        int res = -9;
        if (obj != null) {
            if (obj instanceof SeccionEstudio estudioTmp) {
                res = estudioTmp.getObjetivo().compareTo(this.getObjetivo());
            }

        }
        return res;
    }

    @Override
    public String toString() {
        return "SeccionEstudio{" +
                "categoria='" + categoria + '\'' +
                ", materia='" + materia + '\'' +
                ", unidad='" + unidad + '\'' +
                '}';
    }
}
