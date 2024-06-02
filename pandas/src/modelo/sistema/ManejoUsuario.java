package modelo.sistema;

import excepciones.deLogin.ContrasenaIncorrectaException;
import excepciones.deLogin.LoginIncorrectoException;
import excepciones.deLogin.UsuarioIncorrectoException;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

public class ManejoUsuario {

    private HashSet<Usuario> listaUsuarios;
    private final String archivoUsuarios = "usuarios.dat";

    public ManejoUsuario() {
        listaUsuarios = new HashSet<>();
    }

    // ENTRADA DE NUESTRO SISTEMA DEL ARCHIVO
    public void entradaUsuarios() throws Exception {

        // Hacemos try con recursos
        try (FileInputStream fileInputStream = new FileInputStream(archivoUsuarios);
             ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (true) {
                Usuario usuarioTmp = (Usuario) objectInputStream.readObject();
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
        return listaUsuarios.add(usuarioNuevo);
    }

// DEVUELVE TRUE EN CASO DE QUE SEA CORRECTO, FALSE QUE SEA INCORREC.

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


    // ID AUTOITERABLE
    public double buscarUltimoID() {
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        double id = 0;
        Usuario usuarioTmp = null;

        if (!listaUsuarios.isEmpty()) {
            while (iterator.hasNext()) {
                usuarioTmp = iterator.next();
            }
            if (usuarioTmp != null) {
                id = usuarioTmp.getId();
            }
        }

        return id;
    }


}
