package modelo.sistema;

import excepciones.deLogin.ContrasenaIncorrectaException;
import excepciones.deLogin.LoginIncorrectoException;
import excepciones.deLogin.UsuarioIncorrectoException;
import modelo.tareas.Tarea;

import java.io.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

public class ManejoUsuario {
    // ATRIBUTOS
    private HashSet<Usuario> listaUsuarios;
    private final String archivoUsuarios = "usuarios.dat";

    // CONSTRUCTORES
    public ManejoUsuario() {
        listaUsuarios = new HashSet<>();
    }

    /// METODOS DE ARCHIVO ///////////////////////////////////////////////////////////////////////////////////

    // ENTRADA DE NUESTRO SISTEMA DEL ARCHIVO
    public void entradaUsuarios() throws Exception {
        // Hacemos try con recursos
        try (FileInputStream fileInputStream = new FileInputStream(archivoUsuarios);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (true) {
                Usuario usuarioTmp = (Usuario) objectInputStream.readObject();
                cargarTareas(usuarioTmp);
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
    public void salidaUsuarios() {
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(archivoUsuarios);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);

            for (Usuario usuarioTmp : listaUsuarios) {
                objectOutputStream.writeObject(usuarioTmp);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (dataOutputStream != null) {
                    dataOutputStream.close();
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
    private void cargarTareas(Usuario usuario) {
        String filename = usuario.getId() + ".dat";
        File file = new File(filename);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                HashMap<String, HashSet<Tarea>> tareas = (HashMap<String, HashSet<Tarea>>) ois.readObject(); // casteo
                usuario.setTareasPersonales(tareas);
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        } else {
            guardarTareas(usuario); // Crea el archivo si no existe
        }
    }

    public void guardarTareas(Usuario usuario) {
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
        String respuesta = " ";
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext()) {
            Usuario usuarioTmp = iterator.next();
            respuesta += usuarioTmp.toString(); // IMPORTANTE: EL TOSTRING NO MUESTRA LA CONTRASENA
        }
        return respuesta;
    }
}
