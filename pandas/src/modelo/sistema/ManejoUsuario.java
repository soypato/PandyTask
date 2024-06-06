package modelo.sistema;

import excepciones.deLogin.ContrasenaIncorrectaException;
import excepciones.deLogin.LoginIncorrectoException;
import excepciones.deLogin.UsuarioIncorrectoException;
import modelo.tareas.Tarea;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ManejoUsuario {
    // ATRIBUTOS
    private HashSet<Usuario> listaUsuarios;
    private final String archivoUsuarios = "usuarios.dat";

    // CONSTRUCTORES
    public ManejoUsuario() {
        listaUsuarios = new HashSet<>();
    }

    public int numeroDeUsuarios() {
        return listaUsuarios.size();
    }

    /// METODOS DE ARCHIVO ///////////////////////////////////////////////////////////////////////////////////

    // ENTRADA DE NUESTRO SISTEMA DEL ARCHIVO
    public void entradaUsuarios() throws Exception {
        // Hacemos try con recursos
        try (FileInputStream fileInputStream = new FileInputStream(archivoUsuarios);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (true) {
                Usuario usuarioTmp = (Usuario) objectInputStream.readObject();
                entradaTareas(usuarioTmp);
                listaUsuarios.add(usuarioTmp);
            }
        } catch (EOFException e) {
            //  Esta excepcion la tenemos que tener para que el while se pegue contra ella, pero sin hacer nada
        } catch (FileNotFoundException e) {
            File archivo = new File(archivoUsuarios); // Si no existe, lo crea
            archivo.createNewFile();
        }
    }

    // SALIDA DE NUESTRO SISTEMA HACIA EL ARCHIVO
    public void salidaUsuarios() throws Exception {
        FileOutputStream fileOutputStream = null;
        ObjectOutputStream objectOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(archivoUsuarios);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Usuario usuarioTmp : listaUsuarios) {
                objectOutputStream.writeObject(usuarioTmp); // esto guarda en el archivo la info de todos los usuarios
                salidaTareas(usuarioTmp); // esto guarda en el archivos tareas, un archivo x user
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (objectOutputStream != null) {
                    objectOutputStream.close();
                }
                if (fileOutputStream != null) {
                    fileOutputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // ID AUTOITERABLE
    public double buscarUltimoID() {
        Iterator<Usuario> iterator = listaUsuarios.iterator(); // iterator de la lista de usuarios
        double id = 0; // id con 0 temporalmente para darle un valor
        Usuario usuarioTmp = null; // usuario de iteracion

        if (!listaUsuarios.isEmpty()) { // si esta vacio
            while (iterator.hasNext()) {
                usuarioTmp = iterator.next(); // sigue hasta el final
            }
            if (usuarioTmp != null) { // si lo que hizo != null , retornamos el id
                id = usuarioTmp.getId();
            }
        }
        return id;
    }


    /// METODOS DE TAREAS /////////////////////////////////////////////////////////////////////////////////////

    // CARGA Y DESCARGA: FUNCIONES AUXILIARES QUE PERMITEN CARGAR Y GUARDAR EN LOS ARCHIVOS DE LOS METODOS ANTERIORES

    /*
    * Quizá no quedó muy clara la idea detrás de esto: como Usuario tiene un atributo que es un HashMap, dentro
    * un HashSet de Tarea, al momento de serializarlo le paso el Usuario, quien es portador esa información,
    * <b>Toda la información que tiene el usuario respecto a las colecciones las devuelve un getter</b>
    * Es ese getter quien después, se escribe en (id).dat, donde nosotros estamos escribiendo to_do.
    *
    * Al momento de deserializar hago lo contario = como es un paquete, y en el sólo se encuentra un HashMap y Set<Tarea>,
    si yo convierto la información a un HashMap, Set<Tarea>, voy a obtenerla nuevamente, y la agrego con el setter del usuario.
    *
    *  Como Java trabaja por puntero, queda afectado el usuario y sus colección.
    *
    * */


    // Leo en (id).bat sus tareas.
    private void entradaTareas(Usuario usuario) {
        String filename = usuario.getId() + ".dat"; // concateno para el archivo
        File file = new File(filename); // creo un nuevo archivo
        if (file.exists()) {  // si el archivo existe previamente
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) { // toda la logica de stream
                HashMap<String, HashSet<Tarea>> tareas = (HashMap<String, HashSet<Tarea>>) objectInputStream.readObject(); // casteo y leo la coleccion particular del usuario
                usuario.setTareasPersonales(tareas); // se la mando a tareas
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace(); // excepciones
            }
        } else {
            salidaTareas(usuario); // Crea el archivo si no existe
        }
    }

    // Mando al archi (id).bat las tareas del usuario
    public void salidaTareas(Usuario usuario) {
        String filename = usuario.getId() + ".dat";
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(usuario.getTareasPersonales());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // METODOS AUXILIARES /////////////////////////////////////////////////////////////////////////////////////

    public Usuario buscarUsuario(String nombreUsuario) {
        Usuario encontrado = null;
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext() && encontrado == null) {
            Usuario usuarioTmp = iterator.next();
            if (nombreUsuario.equals(usuarioTmp.getNombreUsuario())) {
                encontrado = usuarioTmp;
            }
        }
        return encontrado;
    }

    public boolean altaUsuario(Usuario usuarioNuevo) {
        // DEVUELVE TRUE EN CASO DE QUE SEA CORRECTO, FALSE QUE SEA INCORREC.
        return listaUsuarios.add(usuarioNuevo);
    }

    public Usuario comprobarLogin(String nombre, String contrasena) throws LoginIncorrectoException {
        Usuario encontrado = null;
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext() && encontrado == null) {
            Usuario usuarioTmp = iterator.next();
            if (nombre.equals(usuarioTmp.getNombreUsuario())) {
                if (contrasena.equals(usuarioTmp.getContrasena())) {
                    encontrado = usuarioTmp;
                } else {
                    throw new ContrasenaIncorrectaException("La constrasena es incorrecta");
                }
            }

        }

        if (encontrado == null) {
            throw new UsuarioIncorrectoException("No existe el usuario");
        }
        return encontrado;
    }

    public String mostrarTodosLosUsuarios() {
        String respuesta = "";
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        respuesta += "-----------------------------------\n";

        while (iterator.hasNext()) {
            Usuario usuarioTmp = iterator.next();
            respuesta += usuarioTmp.toString() + "\n"; // IMPORTANTE: EL TOSTRING NO MUESTRA LA CONTRASENA
        }
        respuesta += "-------------------------------------";

        return respuesta;
    }

    public String mostrarTodosLosNombreUsuarios() {
        String respuesta = "";
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        respuesta += "-----------------------------------\n";

        while (iterator.hasNext()) {
            Usuario usuarioTmp = iterator.next();
            respuesta += usuarioTmp.getNombreUsuario() + "\n"; // Muestro el nombre nothing more
        }
        respuesta += "-------------------------------------";

        return respuesta;
    }

    public String incrementarID(String seccionAIncrementar, Usuario usuarioActual) {
        Tarea ultima = null;
        String res = "";
        HashMap<String, HashSet<Tarea>> coleccionDelUsuario = usuarioActual.getTareasPersonales(); // recupero la coleccion entera del usuario
        HashSet<Tarea> tareasDeLaSeccion = coleccionDelUsuario.get(seccionAIncrementar); // desde la coleccion, le paso la key que el usuario me pasa: su valor sera el hashset de esa seccionx
        if (!(tareasDeLaSeccion.isEmpty())) { // si tiene ya datos: significa que hay que incrementar en uno
            Iterator iterator = tareasDeLaSeccion.iterator();

            while (iterator.hasNext()) {
                ultima = (Tarea) iterator.next();
            }

            res = idAutoincrementable(ultima.getCodigo());
        } else { // si no tiene ya sabemos que tiene que ser la primera
            res = (usuarioActual.getId() + "_" + seccionAIncrementar + "_" + "00");
        }
        return res;
    }

    protected String idAutoincrementable(String input) {
        String resultado = "";

        // Definir una expresión regular para encontrar el número al final de la cadena
        Pattern pattern = Pattern.compile("(\\d+)$");
        Matcher matcher = pattern.matcher(input);

        // Verificar si la cadena coincide con el patrón
        if (matcher.find()) {
            String numberStr = matcher.group(1);  // Parte numérica

            // Convertir la parte numérica a un número entero, incrementarlo en 1 y formatearlo nuevamente
            int number = Integer.parseInt(numberStr);
            number += 1;

            // Formatear el número con ceros a la izquierda según la longitud original
            String newNumberStr = String.format("%02d", number);

            // Ensamblar la cadena con el número incrementado
            resultado = input.replaceAll(numberStr + "$", newNumberStr);
        } else {
            throw new IllegalArgumentException("No se encontró un número al final de la cadena.");
        }

        return resultado;
    }


}
