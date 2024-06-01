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

    public void entradaUsuarios() throws Exception {
        FileInputStream fileInputStream = null;
        DataInputStream dataInputStream = null;
        try {
            fileInputStream = new FileInputStream(archivoUsuarios);
            dataInputStream = new DataInputStream(fileInputStream);

            while (true) {
                try {
                    String id = dataInputStream.readUTF();
                    String nombreUsuario = dataInputStream.readUTF();
                    String contrasena = dataInputStream.readUTF();
                    String correoElectronico = dataInputStream.readUTF();
                    Double bambuesActuales = dataInputStream.readDouble();

                    // PANDA:
                    String pandaNombre = dataInputStream.readUTF();
                    Double pandaBambu = dataInputStream.readDouble();

                    Usuario usuarioTmp = new Usuario(id, nombreUsuario, contrasena, correoElectronico, bambuesActuales, new Panda(pandaNombre, pandaBambu));
                    listaUsuarios.add(usuarioTmp);
                } catch (EOFException e) {
                    throw new EOFException("No se pudieron cargar los datos del archivo al set");
                }
            }
        } catch (FileNotFoundException e) {
            throw new FileNotFoundException("No encontrado");
        } catch (IOException e) {
            throw new IOException("Error en la lectura");
        } finally { // VER SI PUEDO MODIFICARLO CON UN ASSERT
            try {
                if (dataInputStream != null) {
                    dataInputStream.close();
                }
                if (fileInputStream != null) {
                    fileInputStream.close();
                }
            } catch (IOException e) {
                throw new IOException("Problema en la apertura");
            }
        }
    }
    public void salidaUsuarios() {
        FileOutputStream fileOutputStream = null;
        DataOutputStream dataOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream(archivoUsuarios);
            dataOutputStream = new DataOutputStream(fileOutputStream);

            for (Usuario usuarioTmp : listaUsuarios) {
                dataOutputStream.writeUTF(usuarioTmp.getId());
                dataOutputStream.writeUTF(usuarioTmp.getNombreUsuario());
                dataOutputStream.writeUTF(usuarioTmp.getContrasena());
                dataOutputStream.writeUTF(usuarioTmp.getCorreoElectronico());
                dataOutputStream.writeDouble(usuarioTmp.getBambuesActuales());

                // PANDA: tengo que "sobreescribir" (no es eso pero funciona igual) los metodos de panda en Usuario,
                // y retorna los de panda, para poder obterner mas facil
                // otra opcion podria haber sido hacer un pandaTmp pero pierde la persistencia tan facil
                dataOutputStream.writeUTF(usuarioTmp.getNombrePanda());
                dataOutputStream.writeDouble(usuarioTmp.getCantBambuConsumidoPanda());


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


    public Usuario buscarUsuario(String nombreUsuario)
    {
        Usuario encontrado = null;
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext() && encontrado == null)
        {
           Usuario usuarioTmp = iterator.next();
           if(nombreUsuario.equals(usuarioTmp.getNombreUsuario()))
           {
               encontrado = usuarioTmp;
           }
        }
        return encontrado;
    }

    public boolean altaUsuario(Usuario usuarioNuevo)
    {
        return listaUsuarios.add(usuarioNuevo);
    }

    // DEVUELVE TRUE EN CASO DE QUE SEA CORRECTO, FALSE QUE SEA INCORREC.

    public Usuario comprobarLogin(String nombre, String contrasena) throws LoginIncorrectoException
    {
        Usuario encontrado = null;
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while(iterator.hasNext() && encontrado == null)
        {
            Usuario usuarioTmp = iterator.next();
            if(nombre.equals(usuarioTmp.getNombreUsuario()))
            {
                if(contrasena.equals(usuarioTmp.getContrasena()))
                {
                    encontrado = usuarioTmp;
                }
                else
                {
                    throw new ContrasenaIncorrectaException("La constrasena es incorrecta");
                }
            }

        }

        if(encontrado == null)
        {
            throw new UsuarioIncorrectoException("No existe el usuario");
        }
        return encontrado;
    }

    public String mostrarTodosLosUsuarios()
    {
        String respuesta = " ";
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while (iterator.hasNext())
        {
            Usuario usuarioTmp = iterator.next();
            respuesta += usuarioTmp.toString(); // IMPORTANTE: EL TOSTRING NO MUESTRA LA CONTRASENA
        }
        return respuesta;
    }



}
