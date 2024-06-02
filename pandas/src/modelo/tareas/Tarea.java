package modelo.tareas;

import java.util.Random;

public abstract class Tarea
{
    private String titulo;
    private String objetivo;
    private String codigo;
    private int tiempoTranscurrido; //Será automático
    private int calificacion; // (1 a 10)
    private String retroalimentacion; //Mensaje a vos mismo, como te sentistes
    private String fecha; //Con instant (API)


    //Constructores
    public Tarea()
    {
        this.titulo="";
        this.objetivo="";
        this.codigo= "Tarea Invalida";
        this.tiempoTranscurrido=0;
        this.calificacion=0;
        this.retroalimentacion="";
        this.fecha="";
    }

    public Tarea(String titulo, String objetivo, String codigo) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.codigo=codigo;
        this.tiempoTranscurrido=0;
        this.calificacion=0;
        this.retroalimentacion="";
        this.fecha="";

    }

    //Getters

    public String getTitulo() {
        return titulo;
    }

    public String getObjetivo() {
        return objetivo;
    }

    public String getCodigo() {
        return codigo;
    }

    public int getTiempoTranscurrido() {
        return tiempoTranscurrido;
    }

    public int getCalificacion() {
        return calificacion;
    }

    public String getRetroalimentacion() {
        return retroalimentacion;
    }

    public String getFecha() {
        return fecha;
    }

    //Setters
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public void setCalificacion(int calificacion) {
        this.calificacion = calificacion;
    }

    public void setRetroalimentacion(String retroalimentacion) {
        this.retroalimentacion = retroalimentacion;
    }

    @Override
    public abstract int hashCode();

    @Override
    public String toString() {
        return "Tarea{" +
                "titulo='" + titulo + '\'' +
                ", objetivo='" + objetivo + '\'' +
                ", codigo='" + codigo + '\'' +
                ", tiempoTranscurrido=" + tiempoTranscurrido +
                ", calificacion=" + calificacion +
                ", retroalimentacion='" + retroalimentacion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
