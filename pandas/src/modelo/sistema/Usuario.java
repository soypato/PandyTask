package modelo.sistema;
import modelo.tareas.*;

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
    private HashMap<String, HashSet<Tarea>> tareasPersonales;

    /*
    
    String y de valor: un HashSet de Tarea. Tarea tiene un atributo que es tipoTarea, en donde guarda qué tarea es, 
    son las claves que tendrá el HashMap, siempre serán: instanceof "SeccionTrabajo", "SeccionEstudio", 
    "SeccionDeporte" y "SeccionCocina". SeccionCocina a su vez usa una clase que es "Cocina" 
    que es para guardar los datos de la misma, y es pedida en el constructor de SeccionCocina.

    Es decir, el mapa se compone por:
    
    - "SeccionEstudio", {HashSet de todas las tareas que tengan SeccionEstudio en el atributo "tipoTarea"}
    
     */

    // CONSTRUCTORES

    public Usuario(double id, String nombreUsuario, String contrasena, String correoElectronico, double bambuesActuales, Panda pandaDelUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.bambuesActuales = bambuesActuales;
        this.pandaDelUsuario = pandaDelUsuario;

        // Manejo del map con el set y sus respectivas clases
        this.tareasPersonales = new HashMap<>();
        this.tareasPersonales.put("SeccionTrabajo", new HashSet<>());
        this.tareasPersonales.put("SeccionEstudio", new HashSet<>());
        this.tareasPersonales.put("SeccionDeporte", new HashSet<>());
        this.tareasPersonales.put("SeccionCocina", new HashSet<>());

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

    public HashMap<String, HashSet<Tarea>> getTareasPersonales() {
        return tareasPersonales;
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

    public void setTareasPersonales(HashMap<String, HashSet<Tarea>> tareasPersonales) {
        this.tareasPersonales = tareasPersonales;
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

    // MANEJO TAREA ////////////////////////////////////////////////////////////////////////////////////////

    public void nuevaTareaALaColeccion(Tarea nuevaTarea) {
        if (nuevaTarea instanceof SeccionEstudio) {
            tareasPersonales.get("SeccionEstudio").add(nuevaTarea);
        } else if (nuevaTarea instanceof SeccionDeporte) {
            tareasPersonales.get("SeccionDeporte").add(nuevaTarea);
        } else if (nuevaTarea instanceof SeccionTrabajo) {
            tareasPersonales.get("SeccionTrabajo").add(nuevaTarea);
        } else if (nuevaTarea instanceof SeccionCocina) {
            tareasPersonales.get("SeccionCocina").add(nuevaTarea);
        }
    }

    public String buscarTareaPorId(String codigo) {
        for (String tipoTarea : tareasPersonales.keySet()) {
            for (Tarea tarea : tareasPersonales.get(tipoTarea)) {
                if (tarea.getCodigo().equals(codigo)) {
                    return tarea.toString();
                }
            }
        }
        return "Tarea con ID " + codigo + " no encontrada.";
    }

    public String listarTareas() {
        StringBuilder sb = new StringBuilder();
        sb.append("Listado de todas las tareas:\n");
        sb.append("--------------------------------------------------\n");
        for (String tipoTarea : tareasPersonales.keySet()) {
            sb.append("Tipo de Tarea: ").append(tipoTarea).append("\n");
            for (Tarea tarea : tareasPersonales.get(tipoTarea)) {
                sb.append(tarea.toString() + "\n");
            }
        }
        return sb.toString();
    }

    public String bajaTareaDeLaColeccion(String codigo) {
        for (String tipoTarea : tareasPersonales.keySet()) {
            for (Tarea tarea : tareasPersonales.get(tipoTarea)) {
                if (tarea.getCodigo().equals(codigo)) {
                    tareasPersonales.get(tipoTarea).remove(tarea);
                    return "La tarea con código " + codigo + " fue eliminada exitosamente.";
                }
            }
        }
        return "La tarea con código " + codigo + " no fue encontrada en la colección.";
    }


}
