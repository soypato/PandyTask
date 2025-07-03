package modelo.tareas;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;

public abstract class Tarea implements Serializable
{
    private String titulo;
    private String objetivo;
    private String codigo;
    private int temporizador;
    private int minutosTranscurridos;
    private String fecha;//  Viene del main
    private String tipoTarea;// esto es para el manejo de archivos

    private int calificacion; // SET (1 a 10)
    private String retroalimentacion; // SET Mensaje a vos mismo, como te sentistes

    //Constructores
    public Tarea(String tipoTarea) // siempre vamos a tener que tener este tipo, por mas que sea vacio
    {
        this.titulo="";
        this.objetivo="";
        this.codigo= "Tarea Invalida";
        this.temporizador=0;
        this.minutosTranscurridos=0;
        this.calificacion=0;
        this.retroalimentacion="";
        this.fecha="";
        this.tipoTarea = tipoTarea;

    }

    public Tarea(String titulo, String objetivo, String codigo, int temporizador, int minutosTrancurrido, String fecha, String tipoTarea) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.codigo=codigo;
        this.temporizador=temporizador;
        this.minutosTranscurridos=minutosTrancurrido;
        this.calificacion=0; // 0 para detectar cuando todavia no coloco nada, ira del 1 al 10.
        this.retroalimentacion="";
        this.fecha=fecha;
        this.tipoTarea = tipoTarea;
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

    public int getTemporizador() {
        return temporizador;
    }
    public int getMinutosTrancurridos() {return minutosTranscurridos;}

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

    public void setMinutosTrancurridos(int minutosTrancurridos) {this.minutosTranscurridos=minutosTrancurridos;}

    public void setObjetivo(String objetivo)
    {
        this.objetivo = objetivo;
    }
    public void settemporizador(int i) {
    }

    public void setFecha(String date) {
    }
    @Override
    public abstract int hashCode();


    @Override
    public String toString() {
        return "Tarea{" +
                "\n\tTítulo: '" + titulo + '\'' +
                ",\n\tObjetivo: '" + objetivo + '\'' +
                ",\n\tCódigo: '" + codigo + '\'' +
                ",\n\tDuración: " + temporizador + " minutos" +
                ",\n\tMinutos transcurridos: " + minutosTranscurridos +
                ",\n\tCalificación: " + calificacion +
                ",\n\tRetroalimentación: '" + retroalimentacion + '\'' +
                ",\n\tFecha: '" + fecha + '\'' +
                "\n}";
    }


    public JSONObject toJson()
    {
        JSONObject res = new JSONObject();
        try
        {
            res.put("titulo", titulo);
            res.put("objetivo", objetivo);
            res.put("codigo", codigo);
            res.put("duracion", temporizador);
            res.put("fecha", fecha);
            res.put("tipoTarea", tipoTarea);
            res.put("transcurridos", minutosTranscurridos);
            res.put("calificacion", calificacion);
            res.put("retroalimentacion", retroalimentacion);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return res;
    }



}
