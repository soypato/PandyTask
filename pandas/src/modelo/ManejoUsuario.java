package modelo;

import excepciones.ContrasenaIncorrectaException;
import excepciones.LoginIncorrectoException;
import excepciones.UsuarioIncorrectoException;
import modelo.sistema.Usuario;
import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

public class ManejoUsuario {

    private HashSet<Usuario> listaUsuarios;

    public ManejoUsuario() {
        listaUsuarios = new HashSet<>();
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

    public boolean iniciarSesion(String nombre, String contrasena) throws LoginIncorrectoException
    {
        boolean encontrado = false;
        Iterator<Usuario> iterator = listaUsuarios.iterator();
        while(iterator.hasNext() && !encontrado)
        {
            Usuario usuarioTmp = iterator.next();
            if(nombre.equals(usuarioTmp.getNombreUsuario()))
            {
                if(contrasena.equals(usuarioTmp.getContrasena()))
                {
                    encontrado = true;
                }
                else
                {
                    throw new ContrasenaIncorrectaException("La constrasena es incorrecta");
                }
            }

        }

        if(!encontrado)
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
