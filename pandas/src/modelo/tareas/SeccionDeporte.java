package modelo.tareas;

import Interfaces.ICalcularCalorias;
import org.json.JSONException;
import org.json.JSONObject;

public class SeccionDeporte extends Tarea implements Comparable, ICalcularCalorias {
    private String ejercicios;
    private double caloriasQuemadas; // 0 si no lo sabe
    private double duracion;
    private String intensidad;

    public SeccionDeporte(String titulo, String objetivo, String codigo, int temporizador, String fecha, String ejercicios, double duracion, String intensidad) {
        super(titulo,objetivo, codigo, temporizador, fecha, "SeccionDeporte");
        this.ejercicios = ejercicios;
        this.duracion=duracion;
        this.intensidad=intensidad;
        this.caloriasQuemadas = calcularCaloriasQuemadas(intensidad);
    }

    public SeccionDeporte()
    {
            super("SeccionDeporte");
            this.ejercicios = " ";
            this.caloriasQuemadas = -1;
            this.duracion=0;
            this.intensidad="";
    }

    public String getEjercicios() {
        return ejercicios;
    }

    public double getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public double getDuracion() {
        return duracion;
    }

    public String getIntensidad() {
        return intensidad;
    }

    public void setEjercicios(String ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void setCaloriasQuemadas(double caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
    }


    public void setDuracion(double duracion) {
        this.duracion = duracion;
    }

    public void setIntensidad(String intensidad) {
        this.intensidad = intensidad;
    }

    public int hashCode()
    {
        return 1;
    }

    public boolean equals(Object obj)
    {
        boolean res = false;
        if(obj != null)
        {
            if(obj instanceof SeccionDeporte deporteTmp)
            {
                if(deporteTmp.getTitulo().equals(this.getTitulo())
                && deporteTmp.getCaloriasQuemadas() == this.getCaloriasQuemadas())
                {
                    res = true;
                }
            }
        }
        return res;
    }

    public int compareTo(Object obj)
    {
        int res = -9;
        if(obj != null)
        {
            if(obj instanceof SeccionDeporte deporteTmp)
            {
                if(deporteTmp.getCaloriasQuemadas() < this.getCaloriasQuemadas())
                {
                    res = -1;
                }
                else if(deporteTmp.getCaloriasQuemadas() == this.getCaloriasQuemadas())
                {
                    res = 0;
                }
                else
                {
                    res = 1;
                }
            }
        }
        return res;
    }

    @Override
    public JSONObject toJson()
    {
        JSONObject res = super.toJson();
        try {
            res.put("ejercicios", ejercicios);
            res.put("caloriasQuemadas", caloriasQuemadas);
            res.put("duracion", duracion);
            res.put("intensidad", intensidad);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    @Override
    public String toString() {
        return "SeccionDeporte{" +
                "ejercicios='" + ejercicios + '\'' +
                ", caloriasQuemadas=" + caloriasQuemadas +
                ", duracion=" + duracion +
                ", intensidad='" + intensidad + '\'' +
                '}';
    }

    public double calcularCaloriasQuemadas(String intensidad)
    {
        double calorias=convertirIntensidadACalorias(intensidad);

        if(calorias==-1)
        {
            calorias=0;
        }

        return duracion*calorias;
    }

    public double convertirIntensidadACalorias(String intensidad)
    {
        double intensidadCalculada=0;
        switch (intensidad.toLowerCase())
        {
            case "baja":
                intensidadCalculada = 3.5;
                break;

            case "media":
                intensidadCalculada = 7.0;
                break;

            case "alta":
                intensidadCalculada= 12.0;
                break;

            default:
               intensidadCalculada=-1;

        }

        return intensidadCalculada;
    }



}
