import java.sql.SQLOutput;
import java.util.Scanner;

import excepciones.deLogin.LoginIncorrectoException;
import modelo.extra.Receta;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Panda;
import modelo.sistema.Usuario;
import modelo.tareas.ManejoTarea;
import modelo.tareas.SeccionEstudio;
import modelo.tareas.SeccionTrabajo;
import modelo.tareas.Tarea;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ManejoUsuario manejoUsuario;
    static ManejoTarea manejoTarea;

    public static void main(String[] args) throws Exception {

        //Inicializamos el hashSet
        manejoUsuario = new ManejoUsuario();
        manejoTarea = new ManejoTarea();

        //Leer los datos que fueron cargados en el archivo
        try {
            manejoUsuario.entradaUsuarios();
            System.out.println("Programa iniciado correctamente");
        } catch (Exception e) {
            System.err.println("Se produjo un error al iniciar el programa:");
            e.printStackTrace();
        }
        try {
            manejoTarea.entradaTarea();
            System.out.println("Tareas iniciadas correctamente");
        } catch (Exception e) {
            System.err.println("Se produjo un error al cargar las tareas:");
            e.printStackTrace();
        }
        System.out.println(manejoTarea.mostrarTareas());
        System.out.println(manejoUsuario.mostrarTodosLosUsuarios());
        SeccionTrabajo tareaPrueba = new SeccionTrabajo(
                "Proyecto X",
                "Completar el desarrollo del módulo",
                "Tecnología",
                "2024-12-31",
                "T001"
        );
        tareaPrueba.setTiempoTranscurrido(120);
        tareaPrueba.setCalificacion(85);
        tareaPrueba.setRetroalimentacion("Buen trabajo, pero se puede mejorar.");
        tareaPrueba.setFecha("2024-06-02");


        manejoTarea.altaTarea(tareaPrueba);
        Panda panda = new Panda("Pandita");
        Usuario usuario1 = new Usuario(322, "pato", "1234", "patriciotubio" ,0, panda);
        Usuario usuario2 = new Usuario(324, "nachito", "676", "mailNachito", 0, new Panda("Pandito"));

        //Primero leemos en el archivo para verificar que no haya datos, luego "hardcodeo" un usuario y lo agrego
        //con el altaUsuario. Una vez hecho todo esto, entrará al método de salidaUsuario, por lo tanto, el usuario estará
        //cargado en el archivo, luego en otra instancia, leeremos el archivo y cuando se cierre mostramos la colección
        // para verificar que el dato se haya cargado correctamente.


        manejoUsuario.altaUsuario(usuario1);
        manejoUsuario.altaUsuario(usuario2);


        // Durante todo el sistema tenemos que trabajar sobre las colecciones, no el archivo
        // El archivo se actualiza a lo ultimo

        int opcion;
        do {
            mostrarMenuPrincipal();
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    iniciarSesion();
                    break;
                case 2:
                    registrarUsuario();
                    break;
                case 3:
                     restablecerContrasena();   // Restablecer contraseña
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    try {
                        manejoUsuario.salidaUsuarios();  //Carga datos
                    } catch (Exception e) {
                        e.printStackTrace();  //Verificar esto
                    }
                    try {
                        manejoTarea.salidaTareas();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }


    // INICIO DEL PROGRAMA
    public static void mostrarMenuPrincipal() {
        System.out.println("¿En qué sector desea entrar?");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Restablecer contraseña");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

    // OP1 INICIAR SESION
    public static void iniciarSesion()
    {
        String usuario;
        String contrasena;
        Usuario usuarioActual = null;

        do {
            System.out.println("Introduzca el usuario");
            scanner.nextLine();
            usuario = scanner.nextLine();

            System.out.println("Vamos con la contrasena");
            contrasena = scanner.nextLine();

            try {
                usuarioActual = manejoUsuario.comprobarLogin(usuario, contrasena);
                if(usuarioActual != null)
                {
                    System.out.println("Usuario y contrasena correcta!");
                    mostrarMenuInicio(usuarioActual);
                }
            } catch (LoginIncorrectoException e) { // funciona porque la excepcion se tira cuando en el back rebota el usuario
                System.out.println("Usuario y contrasena incorrecto");
            }


        }
        while (usuarioActual == null);



    }


    // OP2 REGISTRAR
    public static void registrarUsuario() {
        boolean respuesta = false;
        Double id;
        String nombreUsuario;
        String contrasena;
        String correoElectronico;
        String nombrePanda;

        id = manejoUsuario.buscarUltimoID()+1;
        System.out.print("Nombre de usuario: ");
        nombreUsuario = scanner.next();
        System.out.print("Contraseña: ");
        contrasena = scanner.next();
        System.out.print("Correo electrónico: ");
        correoElectronico = scanner.next();
        System.out.println("Nombre del panda: ");
        nombrePanda = scanner.next();
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico, 0, new Panda(nombrePanda));
        respuesta = manejoUsuario.altaUsuario(usuario);
        if(respuesta) {
            System.out.println("¡Usuario registrado correctamente!");
        }else
        {
            System.out.println("El usuario ya esta registrado, intentelo nuevamente");
        }
    }

    //OP3 RESTABLECER CONTRASEÑA
    public static void restablecerContrasena()
    {
        String usuario;
        String contrasena;
        String nuevaContrasena;
        Usuario usuarioActual = null;

        do {
            System.out.println("Introduzca el usuario");
            scanner.nextLine();
            usuario = scanner.nextLine();

            System.out.println("Introduzca su contrasena actual");
            contrasena = scanner.nextLine();

            try {
                usuarioActual = manejoUsuario.comprobarLogin(usuario, contrasena);
                if(usuarioActual != null)
                {
                    System.out.println("Ingrese la nueva contrasena");
                    nuevaContrasena = scanner.nextLine();
                    usuarioActual.setContrasena(nuevaContrasena);
                    mostrarMenuInicio(usuarioActual);
                }
            } catch (LoginIncorrectoException e) { // funciona porque la excepcion se tira cuando en el back rebota el usuario
                System.out.println("Usuario y contrasena incorrecto");
            }


        }
        while (usuarioActual == null);



    }

    // OP1.1 LOGIN EXITOSO
    public static void mostrarMenuInicio(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("---------------------------------");
            System.out.println("Bienvenido " + usuarioActual.getNombreUsuario() + " " + usuarioActual.getId());
            System.out.println("---------------------------------");
            System.out.println(usuarioActual.getPandaAscii());
            System.out.println("Tu cantidad de bambues actual es de: " + usuarioActual.getBambuesActuales() + " bambues");
            System.out.println("Tu panda es: " + usuarioActual.getNombrePanda());
            System.out.println(usuarioActual.getNombrePanda() + " comio " + usuarioActual.getCantBambuConsumidoPanda() + " bambues historicamente");
            System.out.println("---------------------------------");
            System.out.println("Menu inicio");
            System.out.println("---------------------------------");
            System.out.println("1. Menu de tareas ");
            System.out.println("2. Menu de recompensas ");
            System.out.println("3. Menu de misiones");
            System.out.println("4. Configuraciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    mostrarMenuTareas(usuarioActual);
                    break;
                case 2:
                    mostrarMenuRecompensas(usuarioActual);
                    break;
                case 3:
                    mostrarMenuMisiones(usuarioActual);
                    break;
                case 4:
                    mostrarMenuConfiguracion(usuarioActual);
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    //OP 1.1.1 MENU TAREAS (tendriamos que recuperar las tareas por puntero)
    public static void mostrarMenuTareas(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Tareas");
            System.out.println("1. Ver y reanudar tareas");
            System.out.println("2. Ver historial");
            System.out.println("3. Crear nueva tarea");
            System.out.println("4. Modificar tareas");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // Ver y reanudar tareas
                    break;
                case 2:
                    // Ver historial
                    break;
                case 3:
                    // Crear nueva tarea
                    break;
                case 4:
                    // Modificar tareas
                    break;
                case 5:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    //OP 1.2.0
    public static void mostrarMenuRecompensas(Usuario usuarioActual){
        int opcion;
        do {
            System.out.println("Menu Recompensas");
            System.out.println("1. Ver y reanudar tareas");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // Ver y reanudar tareas
                    break;
                case 2:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 2);
    }

    //OP 1.3.0
    public static void mostrarMenuMisiones(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Misiones");
            System.out.println("1. Ver misiones");
            System.out.println("2. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // Ver misiones
                    break;
                case 2:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 2);
    }

    //OP 1.4.0
    public static void mostrarMenuConfiguracion(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Configuración");
            System.out.println("1. Cambiar nombre");
            System.out.println("2. Cambiar contraseña");
            System.out.println("3. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                     cambiarNombre(usuarioActual); // Cambiar nombre
                    break;
                case 2:
                     cambiarContrasena(usuarioActual);  // Cambiar contraseña| Es lo mismo que restablecer contrasena
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 3);




    }

    //OP 1.4.1
    public static void cambiarNombre(Usuario usuarioActual)
    {
        String nuevoNombre;
            if(usuarioActual != null)
            {
                System.out.println("Introduzca su nuevo nombre");
                scanner.nextLine();
                nuevoNombre = scanner.nextLine();
                usuarioActual.setNombreUsuario(nuevoNombre);
                mostrarMenuInicio(usuarioActual);
            }
    }

    //OP 1.4.2
    public static void cambiarContrasena(Usuario usuarioActual)
    {
        String nuevaContrasena;

        if(usuarioActual != null)
        {
            System.out.println("Ingrese la nueva contrasena");
            nuevaContrasena = scanner.nextLine();
            usuarioActual.setContrasena(nuevaContrasena);
            mostrarMenuInicio(usuarioActual);
        }
    }

    //**--------------------------------------------------------------------------------------------------------------**
    //SECTOR TAREAS

    /*public static void registrarTarea() {
        boolean respuesta = false;
        String titulo;
        String objetivo;
        String codigo;
        int tiempoTranscurrido; //Será automático
        int calificacion; // (1 a 10)
        String retroalimentacion; //Mensaje a vos mismo, como te sentistes
        String fecha;

        id = manejoUsuario.buscarUltimoID()+1;
        System.out.print("Nombre de usuario: ");
        nombreUsuario = scanner.next();
        System.out.print("Contraseña: ");
        contrasena = scanner.next();
        System.out.print("Correo electrónico: ");
        correoElectronico = scanner.next();
        System.out.println("Nombre del panda: ");
        nombrePanda = scanner.next();
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico, 0, new Panda(nombrePanda));
        respuesta = manejoUsuario.altaUsuario(usuario);
        if(respuesta) {
            System.out.println("¡Usuario registrado correctamente!");
        }else
        {
            System.out.println("El usuario ya esta registrado, intentelo nuevamente");
        }
    }*/


}

