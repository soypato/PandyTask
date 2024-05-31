package modelo.tareas;

public class SeccionDeporte extends Tarea implements Comparable{
    private String ejercicios;
    private double caloriasQuemadas; // 0 si no lo sabe

    public SeccionDeporte(String titulo, String objetivo, String ejercicios, double caloriasQuemadas, String codigo) {
        super(titulo, objetivo, codigo);
        this.ejercicios = ejercicios;
        this.caloriasQuemadas = caloriasQuemadas;
    }

    public SeccionDeporte()
    {
        super();
            this.ejercicios = " ";
            this.caloriasQuemadas = 0;
    }

    public String getEjercicios() {
        return ejercicios;
    }

    public double getCaloriasQuemadas() {
        return caloriasQuemadas;
    }

    public void setEjercicios(String ejercicios) {
        this.ejercicios = ejercicios;
    }

    public void setCaloriasQuemadas(double caloriasQuemadas) {
        this.caloriasQuemadas = caloriasQuemadas;
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
    public String toString() {
        return "SeccionDeporte{\n" + super.toString() + " " +
                "ejercicios='" + ejercicios + '\'' +
                ", caloriasQuemadas=" + caloriasQuemadas +
                '}';
    }
}
