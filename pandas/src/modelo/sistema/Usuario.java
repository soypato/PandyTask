package modelo.sistema;
import modelo.tareas.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
    private double cantArbolesPlantados;
    private double cantLavados;
    private double cantJuguetes;
    private double cantVisitasVeterinario;
    public boolean instalacionesAdquiridas;
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

    public Usuario(double id, String nombreUsuario, String contrasena, String correoElectronico, double bambuesActuales, Panda pandaDelUsuario, double cantArbolesPlantados, double cantLavados, double cantJuguetes, double cantVisitasVeterinario, boolean instalacionesAdquiridas) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.bambuesActuales = bambuesActuales;
        this.cantArbolesPlantados = cantArbolesPlantados;
        this.cantLavados = cantLavados;
        this.cantJuguetes = cantJuguetes;
        this.cantVisitasVeterinario = cantVisitasVeterinario;
        this.instalacionesAdquiridas = instalacionesAdquiridas;
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
        this.cantArbolesPlantados = 0;
        this.cantLavados = 0;
        this.cantJuguetes = 0;
        this.cantVisitasVeterinario = 0;
        this.instalacionesAdquiridas = false;
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

    public double getCantArbolesPlantados() {return cantArbolesPlantados;}
    public double getCantLavados() {return cantLavados;}
    public double getCantJuguetes() {return cantJuguetes;}
    public double getCantVisitasVeterinario() { return cantVisitasVeterinario; }
    public boolean getInstalacionesAdquiridas() {return instalacionesAdquiridas;}
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

    public void setCantArbolesPlantados(double cantArbolesPlantados) {this.cantArbolesPlantados = cantArbolesPlantados;}
    public void setCantLavados(double cantLavados) {this.cantLavados = cantLavados;}
    public void setCantJuguetes(double cantJuguetes) {this.cantJuguetes = cantJuguetes;}
    public void setCantVisitasVeterinario(double cantVisitasVeterinario) {this.cantVisitasVeterinario = cantVisitasVeterinario;}
    public void setInstalacionesAdquiridas(boolean instalacionesAdquiridas) {this.instalacionesAdquiridas = instalacionesAdquiridas;}
    public void setTareasPersonales(HashMap<String, HashSet<Tarea>> tareasPersonales) {
        this.tareasPersonales = tareasPersonales;
    }

    public void alimentarPandaUsuario() //aumentando en uno el atributo bambuconsumido
    {
        double suma = pandaDelUsuario.getCantBambuConsumido();
        suma++;
        pandaDelUsuario.setCantBambuConsumido(suma);
    }
    public void aumentarCantArbolesPlantados() //aumentamos en uno el valor de cantArbolesPlantados
    {
        double suma = getCantArbolesPlantados();
        suma++;
        setCantArbolesPlantados(suma);
    }
    public void aumentarLavados()
    {
        double suma = getCantLavados();
        suma++;
        setCantLavados(suma);
    }
    public void aumentarCantJuguetes()
    {
        double suma = getCantJuguetes();
        suma++;
        setCantJuguetes(suma);
    }
    public void aumentarVisitas()
    {
        double suma = getCantVisitasVeterinario();
        suma++;
        setCantVisitasVeterinario(suma);
    }
    public void modificarInstalaciones()
    {
        setInstalacionesAdquiridas(true);
    }

    public boolean hayTareasCreadas() {
        boolean rta = true;
        for (HashSet<Tarea> tareas : tareasPersonales.values()) {
            if (!tareas.isEmpty()) {
                rta = false; // Si alguna colección de tareas no está vacía, hay tareas creadas
            }
        }
        return rta; // Si ninguna colección tiene tareas, entonces no hay tareas creadas
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



    public JSONObject toJSONTareas() throws JSONException {
        JSONObject res = new JSONObject();
        for(String tipoTarea : tareasPersonales.keySet()) // solo con el valor puedo recorrer
        {
            JSONArray tareasArray = new JSONArray(); // COMO DENTRO DEL VALUE TENGO UNA COLEC. HAGO UN ARREGLO
            for(Tarea tarea : tareasPersonales.get(tipoTarea))
            {
                tareasArray.put(tarea.toJson()); // MANDO TODOS LOS ELEMENTOS DEL SET AL ARREGLO
            }
            res.put(tipoTarea, tareasArray); // AGREGO EL ARR A LA OBJ
        }
        return res;
    }

}
