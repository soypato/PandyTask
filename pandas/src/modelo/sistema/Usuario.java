package modelo.sistema;
import modelo.tareas.Tarea;
import java.io.Serializable;
import java.util.HashMap;
import java.util.HashSet;

public class Usuario implements Comparable, Serializable {
    private double id;
    private String nombreUsuario;
    private String contrasena;
    private String correoElectronico;
    private double bambuesActuales;
    private Panda pandaDelUsuario;
    private HashMap<String, HashSet<Tarea>> notasPersonales;

    // CONSTRUCTORES

    public Usuario(double id, String nombreUsuario, String contrasena, String correoElectronico, double bambuesActuales, Panda pandaDelUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.bambuesActuales = bambuesActuales;
        this.pandaDelUsuario = pandaDelUsuario;

        // Manejo del map con el set y sus respectivas clases
        this.notasPersonales = new HashMap<>();
        this.notasPersonales.put("SeccionTrabajo", new HashSet<>());
        this.notasPersonales.put("SeccionEstudio", new HashSet<>());
        this.notasPersonales.put("SeccionDeporte", new HashSet<>());
        this.notasPersonales.put("SeccionCocina", new HashSet<>());

    }

    public Usuario()
    {
        this.id = -9; // -9 por convecion es invalido
        this.nombreUsuario = " ";
        this.contrasena = " ";
        this.correoElectronico = " ";
        this.bambuesActuales = 0;
        this.pandaDelUsuario = new Panda();
    }

    // GETTERS

    public double getId()
    {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() { // la contrasena ya viaja encriptada, por lo que no hay forma de ver la real
        return contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public double getBambuesActuales() {
        return bambuesActuales;
    }

    // Esto para el archivo
    public String getNombrePanda()
    {
        return pandaDelUsuario.getNombrePanda();
    }
    public double getCantBambuConsumidoPanda()
    {
        return pandaDelUsuario.getCantBambuConsumido();
    }

    public String getPandaAscii()
    {
        return pandaDelUsuario.getPandaAscii();
    }

    public HashMap<String, HashSet<Tarea>> getNotasPersonales() {
        return notasPersonales;
    }


    // SETTERS

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setBambuesActuales(double bambuesActuales) {
        this.bambuesActuales = bambuesActuales;
    }

    public void setNotasPersonales(HashMap<String, HashSet<Tarea>> notasPersonales) {
        this.notasPersonales = notasPersonales;
    }


    @Override
    public boolean equals(Object obj)
    {
        boolean res = false;
        if(obj != null)
        {
            if(obj instanceof Usuario usuarioValidado)
            {
                if(this.getId() == (usuarioValidado.getId()))
                {
                    res = true;
                }
            }
        }
        return res;
    }

    @Override
    public int compareTo(Object obj)
    {
        int resultado = -99;

        if(obj != null)
        {
            if(obj instanceof Usuario usuarioValidado)
            {
                if(this.getId() > usuarioValidado.getId())
                    resultado = -1;
                else if(this.getId() == usuarioValidado.getId())
                    resultado = 0;
                else
                    resultado = 1;

            }
        }
        return resultado;
    }


    @Override
    public int hashCode() {
        return 1;
    }

    // Omitimos la de contrasena
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }  // IMPORTANTE: EL TOSTRING NO MUESTRA LA CONTRASENA
}
