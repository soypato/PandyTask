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
        this.calificacion=0;
        this.retroalimentacion="";
        this.fecha="";
        this.tipoTarea = tipoTarea;

    }

    public Tarea(String titulo, String objetivo, String codigo, int temporizador, String fecha, String tipoTarea) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.codigo=codigo;
        this.temporizador=temporizador;
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

    public int gettemporizador() {
        return temporizador;
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
                ", temporizador=" + temporizador +
                ", calificacion=" + calificacion +
                ", retroalimentacion='" + retroalimentacion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }

    public JSONObject toJson()
    {
        JSONObject res = new JSONObject();
        try
        {
            res.put("titulo", titulo);
            res.put("objetivo", objetivo);
            res.put("codigo", codigo);
            res.put("temporizador", temporizador);
            res.put("fecha", fecha);
            res.put("tipoTarea", tipoTarea);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    public void settemporizador(int i) {
    }

    public void setFecha(String date) {
    }
}
