package modelo.tareas;


import java.util.HashSet;

public class SeccionEstudio extends Tarea
{
    private HashSet<Tarea> tareasEstudio;

    private String categoria;
    private String materia;
    private String unidad;

    //Constructores
    public SeccionEstudio()
    {
        tareasEstudio= new HashSet<>();
        categoria="";
        materia="";
        unidad="";
    }

    public SeccionEstudio(String titulo, String objetivo, String categoria, String materia, String unidad) {
        super(titulo, objetivo);
        this.categoria = categoria;
        this.materia = materia;
        this.unidad = unidad;
    }

    //Getters

    public String getCategoria() {
        return categoria;
    }

    public String getMateria() {
        return materia;
    }

    public String getUnidad() {
        return unidad;
    }

    //Setter
    public void setMateria(String materia) {
        this.materia = materia;
    }

    //Métodos
    public boolean altaEstudio(Tarea tarea)
    {
        boolean seAgrego=false;

        if(!tareasEstudio.contains(tarea))
        {
            tareasEstudio.add(tarea);
            seAgrego=true;
        }

        return seAgrego;
    }

    public Tarea obtenerTarea(String titulo)
    {
        Tarea tarea=null;

        for(Tarea tarea1 : tareasEstudio)
        {
            if(tarea1.getTitulo().equals(titulo))
            {
                tarea = tarea1;
            }
        }
        return tarea;
    }

    public boolean eliminarTarea(String titulo)
    {
        boolean seElimino=false;

        Tarea tarea1 = obtenerTarea(titulo);

        if(tarea1!=null)
        {
            tareasEstudio.remove(tarea1);

            seElimino=true;
        }

         return seElimino;
    }



}
