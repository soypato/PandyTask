import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import excepciones.deLogin.LoginIncorrectoException;
import modelo.extra.Receta;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Panda;
import modelo.sistema.Usuario;
import modelo.tareas.*;

import static java.lang.Thread.sleep;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ManejoUsuario manejoUsuario;
    // static ManejoTarea manejoTarea;

    public static void main(String[] args) throws Exception {

        // ManejoUsuario administra to-do lo que tiene que ver con el sistema. Es como una instancia de el.
        manejoUsuario = new ManejoUsuario();

        //Leemos los datos que fueron cargados en el archivo

        try {
            manejoUsuario.entradaUsuarios();
            System.out.println("Bienvenido a PandyTask.");
        } catch (Exception e) {
            System.err.println("Se produjo un error al iniciar el programa, importar el archivo de inicio: si es el primer inicio, reinicia presionando 4.");
        }
        try {
            // manejoTarea.entradaTarea();
            System.out.println("Tu sistema de tareas."); // mas friendly que "tareas cargadas correctamente"...
        } catch (Exception e) {
            System.err.println("Se produjo un error al cargar las tareas: si es el primer inicio del programa, reinicia presionando 4.");
            e.printStackTrace();
        }

        // manejoTarea.altaTarea(tareaPrueba);
        Usuario usuario1 = new Usuario(001, "usuarioPrueba", "1234", "prueba", 0, new Panda("Panda de prueba"));

        //Primero leemos en el archivo para verificar que no haya datos, luego "hardcodeo" un usuario y lo agrego
        //con el altaUsuario. Una vez hecho to-do esto, entrará al método de salidaUsuario, por lo tanto, el usuario estará
        //cargado en el archivo, luego en otra instancia, leeremos el archivo y cuando se cierre mostramos la colección
        // para verificar que el dato se haya cargado correctamente.

        // Durante to-do el sistema tenemos que trabajar sobre las colecciones, no el archivo
        // El archivo se actualiza a lo ultimo


        manejoUsuario.altaUsuario(usuario1);

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
                        // manejoTarea.salidaTareas();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    /////////////////// INICIO DEL PROGRAMA

    public static void mostrarMenuPrincipal() {
        System.out.println("-------------------------");
        System.out.println("¿En qué sector desea entrar?");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Restablecer contraseña");
        System.out.println("4. Salir y guardar cambios");
        System.out.println("-------------------------");
        System.out.print("Seleccione una opción: ");
    }

    // OP1 INICIAR SESION
    public static void iniciarSesion() {
        String usuario;
        String contrasena;
        Usuario usuarioActual = null;
        do {
            System.out.println("-------------------------");
            System.out.println("Introduzca el usuario");
            scanner.nextLine();
            usuario = scanner.nextLine();

            System.out.println("Vamos con la contrasena");
            contrasena = scanner.nextLine();

            try {
                usuarioActual = manejoUsuario.comprobarLogin(usuario, contrasena);
                if (usuarioActual != null) {
                    System.out.println("Usuario y contrasena correcta!");
                    mostrarMenuInicio(usuarioActual);
                }
            } catch (
                    LoginIncorrectoException e) { // funciona porque la excepcion se tira cuando en el back rebota el usuario
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

        id = manejoUsuario.buscarUltimoID() + 1;
        System.out.println("-------------------------");
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
        if (respuesta) {
            System.out.println("¡Usuario registrado correctamente!");
        } else {
            System.out.println("El usuario ya esta registrado, intentelo nuevamente");
        }
    }

    //OP3 RESTABLECER CONTRASEÑA
    public static void restablecerContrasena() {
        String usuario;
        String contrasena;
        String nuevaContrasena;
        Usuario usuarioActual = null;

        do {
            System.out.println("-------------------------");
            System.out.println("Introduzca el usuario");
            scanner.nextLine();
            usuario = scanner.nextLine();

            System.out.println("Introduzca su contrasena actual");
            contrasena = scanner.nextLine();

            try {
                usuarioActual = manejoUsuario.comprobarLogin(usuario, contrasena);
                if (usuarioActual != null) {
                    System.out.println("Ingrese la nueva contrasena");
                    nuevaContrasena = scanner.nextLine();
                    usuarioActual.setContrasena(nuevaContrasena);
                    mostrarMenuInicio(usuarioActual);
                }
            } catch (
                    LoginIncorrectoException e) { // funciona porque la excepcion se tira cuando en el back rebota el usuario
                System.out.println("Usuario y contrasena incorrecto");
            }


        }
        while (usuarioActual == null);
    }
    /////////////////// LOGIN EXITOSO

    public static void mostrarMenuInicio(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("---------------------------------");
            System.out.println("Bienvenido " + usuarioActual.getNombreUsuario());
            System.out.println("---------------------------------");
            System.out.println(usuarioActual.getPandaAscii());
            System.out.println("Tu cantidad de bambues actual es de: " + usuarioActual.getBambuesActuales() + " bambues");
            System.out.println("Tu panda es: " + usuarioActual.getNombrePanda());
            System.out.println(usuarioActual.getNombrePanda() + " comio " + usuarioActual.getCantBambuConsumidoPanda() + " bambues historicamente");
            System.out.println("---------------------------------");
            System.out.println("Menu de usuario");
            System.out.println("---------------------------------");
            System.out.println("1. Menu de tareas ");
            System.out.println("2. Menu de recompensas ");
            System.out.println("3. Menu de misiones");
            System.out.println("4. Configuraciones");
            System.out.println("5. Cerrar sesion y volver al login");
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
            System.out.println("5. Volver al menu de usuario");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    // Ver/reanudar tareas
                    String listaTareas = usuarioActual.listarTareas();
                    System.out.println(listaTareas);
                    break;
                case 2:
                    // Ver historial
                case 3:
                    nuevaTarea(usuarioActual);
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
    public static void mostrarMenuRecompensas(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Recompensas");
            System.out.println("1. Ver y reanudar tareas");
            System.out.println("2. Volver al menu de usuario");
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
            System.out.println("2. Volver al menu de usuario");
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
            System.out.println("3. Volver al menu de usuario");
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
    public static void cambiarNombre(Usuario usuarioActual) {
        String nuevoNombre;
        if (usuarioActual != null) {
            System.out.println("Introduzca su nuevo nombre");
            scanner.nextLine();
            nuevoNombre = scanner.nextLine();
            usuarioActual.setNombreUsuario(nuevoNombre);
            mostrarMenuInicio(usuarioActual);
        }
    }

    //OP 1.4.2
    public static void cambiarContrasena(Usuario usuarioActual) {
        String nuevaContrasena;

        if (usuarioActual != null) {
            System.out.println("Ingrese la nueva contrasena");
            nuevaContrasena = scanner.nextLine();
            usuarioActual.setContrasena(nuevaContrasena);
            mostrarMenuInicio(usuarioActual);
        }
    }

    public static void nuevaTarea(Usuario usuarioActual) {

        System.out.println("Este es el asistente para crear una nueva tarea.");
        try {
            sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int opcion;
        int calificTemp;
        String retroalimentacion;

        System.out.println("-------------------------");
        System.out.println("Que tipo de tarea quiere iniciar?");
        System.out.println("1. Estudio");
        System.out.println("2. Trabajo");
        System.out.println("3. Deporte");
        System.out.println("4. Cocina");
        System.out.println("-------------------------");
        System.out.print("Su decision: ");
        opcion = scanner.nextInt();

        // GENERICAS PARA TODAS LAS CLASES: LAS DEL SUPER TAREA
        System.out.println("-------------------------");
        System.out.println("Por favor, introduce la información de la tarea:");
        System.out.println("-------------------------");
        System.out.print("Título de la tarea: ");
        scanner.nextLine();
        String titulo = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Objetivo de la tarea: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String objetivo = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Fecha de la tarea: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String fecha = scanner.nextLine();

        int minutos;
        do {
            System.out.println("-------------------------");
            System.out.print("Cantidad de minutos que durara la tarea: ");
            minutos = scanner.nextInt();
            System.out.println("-------------------------");
            if (minutos <= 0) {
                System.out.println("Has introducido una cantidad invalida de minutos. Intentalo de nuevo.");
            }

        } while (minutos <= 0);

        switch (opcion) {
            case 1:
                SeccionEstudio estudioTmp = generarEstudio(usuarioActual, titulo, objetivo, fecha, minutos);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if(calificTemp <= 0 || calificTemp > 10)
                    {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while(calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                estudioTmp.setCalificacion(calificTemp);
                estudioTmp.setRetroalimentacion(retroalimentacion);

                usuarioActual.nuevaTareaALaColeccion(estudioTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                System.out.println(estudioTmp.toString());
                break;
            case 2:
                SeccionTrabajo trabajoTmp = generarTrabajo(usuarioActual, titulo, objetivo, fecha, minutos);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if(calificTemp <= 0 || calificTemp > 10)
                    {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while(calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                trabajoTmp.setCalificacion(calificTemp);
                trabajoTmp.setRetroalimentacion(retroalimentacion);

                usuarioActual.nuevaTareaALaColeccion(trabajoTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                System.out.println(trabajoTmp.toString());
                break;
            case 3:
                SeccionDeporte deporteTmp = generarDeporte(usuarioActual, titulo, objetivo, fecha, minutos);
                // NECESITAMOS QUE LUEGO NOS DIGA LAS CALORIAS CON FINES ESTADISTICOS
                System.out.println("Cuantas calorias quemo?");
                deporteTmp.setCaloriasQuemadas(scanner.nextInt());

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if(calificTemp <= 0 || calificTemp > 10)
                    {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while(calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                deporteTmp.setCalificacion(calificTemp);
                deporteTmp.setRetroalimentacion(retroalimentacion);



                usuarioActual.nuevaTareaALaColeccion(deporteTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                System.out.println(deporteTmp.toString());
                break;
            case 4:
                SeccionCocina cocinaTmp = generarCocina(usuarioActual, titulo, objetivo, fecha, minutos);
                usuarioActual.nuevaTareaALaColeccion(cocinaTmp);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if(calificTemp <= 0 || calificTemp > 10)
                    {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while(calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                cocinaTmp.setCalificacion(calificTemp);
                cocinaTmp.setRetroalimentacion(retroalimentacion);

                System.out.println("La tarea ha sido agregada exitosamente");
                System.out.println(cocinaTmp.toString());
                break;
            default:
                System.out.println("Opción no válida");
        }

    }

    ///////////////////// GENERADORES DE TAREA

    public static SeccionTrabajo generarTrabajo(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {

        SeccionTrabajo res = null;

        System.out.print("Sector de la tarea: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String sector = scanner.nextLine();

        System.out.print("Fecha límite -- deadline de la tarea: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String fechaLimite = scanner.nextLine();

        int minutosCumplidos = iniciarTemporizador(minutos);

        res = new SeccionTrabajo(titulo, objetivo, "000", minutosCumplidos, fecha, sector, fechaLimite);
        usuarioActual.sumarBambues(minutosCumplidos * 10);

        System.out.println("Has sumado " + minutos * 10 + "bambues");


        return res;
    }

    public static SeccionEstudio generarEstudio(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        System.out.println("-------------------------");
        System.out.print("Categoría: ");
        scanner.nextLine();
        String categoria = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Materia: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String materia = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Unidad/capitulo bibliografico (presiona enter y luego ingresa): ");
        scanner.nextLine();
        String unidad = scanner.nextLine();

        int minutosCumplidos = iniciarTemporizador(minutos);

        usuarioActual.sumarBambues(minutosCumplidos * 10);
        return new SeccionEstudio(titulo, objetivo, "000", minutosCumplidos, fecha, categoria, materia, unidad);
    }

    public static SeccionDeporte generarDeporte(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        System.out.println("-------------------------");
        System.out.print("Ejercicios: ");
        scanner.nextLine();
        String ejercicios = scanner.nextLine();
        System.out.println("-------------------------");
        int minutosCumplidos = iniciarTemporizador(minutos);

        usuarioActual.sumarBambues(minutosCumplidos * 10);
        return new SeccionDeporte(titulo, objetivo, "000", minutosCumplidos, fecha, ejercicios);
    }

    public static SeccionCocina generarCocina(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        System.out.println("-------------------------");
        System.out.print("Ingredientes (separados por coma): ");
        scanner.nextLine();
        String ingredientes = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Paso a paso: (presiona enter antes)");
        scanner.nextLine();
        String pasoAPaso = scanner.nextLine();
        System.out.println("-------------------------");
        Receta receta = new Receta(ingredientes, pasoAPaso);

        int minutosCumplidos = iniciarTemporizador(minutos);

        usuarioActual.sumarBambues(minutosCumplidos * 10);
        return new SeccionCocina(titulo, objetivo, "000", minutosCumplidos, fecha, receta);
    }

    ///////////  FUNCION PROPIA DE JAVA PARA MANEJO DE TEMPORIZADOR
    private static int iniciarTemporizador(int minutos) {
        final AtomicBoolean finalizado = new AtomicBoolean(false);
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final long[] minutosCumplidos = {0};

        System.out.println("Presiona 'Enter' para finalizar el temporizador antes de tiempo.");

        Thread hiloFinalizar = new Thread(() -> {
            scanner.nextLine();
            finalizado.set(true);
            scheduler.shutdownNow();
        });

        hiloFinalizar.start();

        scheduler.scheduleAtFixedRate(() -> {
            if (finalizado.get()) {
                System.out.println("El temporizador ha sido finalizado antes de tiempo.");
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Presione una tecla para continuar...");
                return;
            }

            minutosCumplidos[0]++;
            System.out.println("Llevas " + minutosCumplidos[0] + " / " + minutos + " minutos.");

            if (minutosCumplidos[0] >= minutos) {
                System.out.println("El temporizador ha finalizado después de " + minutos + " minutos.");
                finalizado.set(true);
                scheduler.shutdownNow();
            }

        }, 1, 1, TimeUnit.MINUTES);

        try {
            hiloFinalizar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return (int) minutosCumplidos[0];
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

