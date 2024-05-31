package modelo.tareas;

import java.util.Random;

public abstract class Tarea
{
    private String titulo;
    private String objetivo;
    private int codigo;
    private int tiempoTranscurrido; //Será automático
    private int calificacion; // (1 a 10)
    private String retroalimentacion; //Mensaje a vos mismo, como te sentistes
    private String fecha; //Con instant (API)


    //Constructores
    public Tarea()
    {
        titulo="";
        objetivo="";
        codigo=generarCodigo();
        tiempoTranscurrido=0;
        calificacion=0;
        retroalimentacion="";
        fecha="";
    }

    public Tarea(String titulo, String objetivo) {
        this.titulo = titulo;
        this.objetivo = objetivo;
        this.codigo=generarCodigo();
        tiempoTranscurrido=0;
        calificacion=0;
        retroalimentacion="";
        fecha="";

    }

    //Getters

    public String getTitulo() {
        return titulo;
    }

    public String getObjetivo() {
        return objetivo;
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

    //Métodos
    public int generarCodigo()
    {
        Random randomCodigo = new Random();

        int min = 10000000;
        int max = 99999999;
        int codigoRandom = randomCodigo.nextInt(max-min); //Generamos un número random en este rango especifico

        return codigoRandom;
    }

    @Override
    public int hashCode() {
        return 1;
    }


    @Override
    public boolean equals(Object obj) //Para que no haya tareas repetidas
    {
        boolean sonIguales=false;
        if(obj!=null)
        {
            if(obj instanceof Tarea tareaTmp)
            {
                if (this.titulo.equals(tareaTmp.titulo) && this.objetivo.equals(tareaTmp.objetivo))
                {
                    sonIguales=true;
                }
            }
        }
        return sonIguales;
    }

}
