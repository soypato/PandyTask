import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;

import excepciones.deLogin.LoginIncorrectoException;
import excepciones.dePanda.CantidadBambuesInsuficientesException;
import excepciones.deTarea.TareaInvalidaExcpetion;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Panda;
import modelo.sistema.Usuario;
import modelo.tareas.*;
import org.json.JSONException;

import static java.lang.Thread.sleep;
import static utiles.JsonUtiles.grabar;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
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

        //Primero leemos en el archivo para verificar que no haya datos, luego "hardcodeo" un usuario y lo agrego
        //con el altaUsuario. Una vez hecho to-do esto, entrará al método de salidaUsuario, por lo tanto, el usuario estará
        //cargado en el archivo, luego en otra instancia, leeremos el archivo y cuando se cierre mostramos la colección
        // para verificar que el dato se haya cargado correctamente.

        // Durante to-do el sistema tenemos que trabajar sobre las colecciones, no el archivo
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
        limpiarBuffer();
        String usuario = "";
        String contrasena = "";
        Usuario usuarioActual = null;
        if (manejoUsuario.numeroDeUsuarios() == 0) {
            System.out.println("No hay usuarios creados. Por favor, cree una cuenta primero.");
            System.out.println("Ingrese los datos para crear su nueva cuenta. Apreta ENTER para continuar");
            registrarUsuario();
        } else {
            do {
                System.out.println("Quien esta mirando?");
                System.out.println(manejoUsuario.mostrarTodosLosNombreUsuarios());

                System.out.println("Ingrese el nombre del usuario");
                usuario = scanner.nextLine();
                System.out.println("Ingrese la contrasena del usuario");
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
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }


            }
            while (usuarioActual == null);
        }


    }


    // OP2 REGISTRAR
    public static void registrarUsuario() {
        limpiarBuffer();
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
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico, 0, new Panda(nombrePanda), 0, 0, 0, 0, false);
        respuesta = manejoUsuario.altaUsuario(usuario);
        if (respuesta) {
            System.out.println("¡Usuario registrado correctamente!");
        } else {
            System.out.println("El usuario ya esta registrado, intentelo nuevamente");
        }
    }

    //OP3 RESTABLECER CONTRASEÑA
    public static void restablecerContrasena() {
        limpiarBuffer();
        String usuario;
        String nuevaContrasena;
        Usuario usuarioActual = null;

        do {
            System.out.println("-------------------------");
            System.out.println("Introduzca el usuario");
            usuario = scanner.nextLine();
            usuarioActual = manejoUsuario.buscarUsuario(usuario);
                if (usuarioActual != null) {
                    System.out.println("Ingrese la nueva contrasena");
                    nuevaContrasena = scanner.nextLine();
                    usuarioActual.setContrasena(nuevaContrasena);
                    System.out.println("La contraseña ha sido restablecida con éxito.");
                }
        } while (usuarioActual == null);
    }
    /////////////////// LOGIN EXITOSO

    public static void mostrarMenuInicio(Usuario usuarioActual) throws JSONException {
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
            System.out.println("1. Menu de tareas");
            System.out.println("2. Menu de estadisticas");
            System.out.println("3. Menu de tienda");
            System.out.println("4. Menu de misiones");
            System.out.println("5. Configuracion de cuenta");
            System.out.println("6. Exportar tareas");
            System.out.println("7. Volver al menu principal");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    mostrarMenuTareas(usuarioActual);
                    break;
                case 2:
                    mostrarMenuEstadisticas(usuarioActual);
                    break;
                case 3:
                    mostrarMenuTienda(usuarioActual);
                    break;
                case 4:
                    mostrarMenuMisiones(usuarioActual);
                    break;
                case 5:
                    mostrarMenuConfiguracion(usuarioActual);
                    break;
                case 6:
                    archivoJSON(usuarioActual);
                    break;
                case 7:
                    System.out.println("Volviendo al menu principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 7);
    }

    //OP 1.1.1 MENU TAREAS (tendriamos que recuperar las tareas por puntero)

    public static void mostrarMenuTareas(Usuario usuarioActual) {
        int opcion;
        int eleccionTareaInt = 0;
        String eleccionTarea = "";
        String idTarea = "";
        Tarea tareaTmp = null;
        do {
            System.out.println("Menu Tareas");
            System.out.println("1. Ver y comenzar/reanudar");
            System.out.println("2. Crear nueva tarea");
            System.out.println("3. Ver historial de tareas");
            System.out.println("4. Modificar tareas");
            System.out.println("5. Volver al menu de usuario");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    int tiempoTotal = 0;
                    if (!usuarioActual.hayTareasCreadas()) {
                        System.out.println("Menu de arranque tareas");
                        System.out.println(usuarioActual.listarTareas());
                        System.out.println("Ingrese a que seccion desea acceder: ");
                        System.out.println("1. SeccionTrabajo");
                        System.out.println("2. SeccionEstudio");
                        System.out.println("3. SeccionDeporte");
                        System.out.println("4. SeccionCocina");
                        eleccionTareaInt = scanner.nextInt();
                        if (eleccionTareaInt == 1) {
                            eleccionTarea = "SeccionTrabajo";
                        } else if (eleccionTareaInt == 2) {
                            eleccionTarea = "SeccionEstudio";
                        } else if (eleccionTareaInt == 3) {
                            eleccionTarea = "SeccionDeporte";
                        } else if (eleccionTareaInt == 4) {
                            eleccionTarea = "SeccionCocina";
                        } else {
                            System.out.println("Opcion incorrecta");
                        }
                        System.out.println("Ingrese el ID de la tarea a comenzar: ");
                        scanner.nextLine();
                        idTarea = scanner.nextLine();
                        tareaTmp = manejoUsuario.buscarEntreTareas(idTarea, eleccionTarea, usuarioActual);

                        if (tareaTmp == null) {
                            System.out.println("Vuelve a intentarlo");
                        } else {

                            int minutosRestantes = tareaTmp.getTemporizador() - tareaTmp.getMinutosTrancurridos();
                            System.out.println("Llevas trabajados en la tarea: " + tareaTmp.getMinutosTrancurridos() + " minutos, de los: " + tareaTmp.getTemporizador() + " minutos totales.");
                            System.out.println("Desea comenzar la tarea? (s/n)");
                            char decision = scanner.next().charAt(0);
                            if (decision == 's') {
                                System.out.println("Presiona 'Enter' para comenzar la tarea...");
                                scanner.nextLine();
                                scanner.nextLine();
                                int minutosCumplidos = iniciarTemporizador(tareaTmp.getTemporizador(), tareaTmp.getMinutosTrancurridos());
                                System.out.println("Minutos cumplidos: " + minutosCumplidos);
                                usuarioActual.setBambuesActuales(usuarioActual.getBambuesActuales() + (minutosCumplidos * 30));
                                System.out.println("Has sumado " + minutosCumplidos * 30 + " bambues");
                                tiempoTotal = tareaTmp.getMinutosTrancurridos() + minutosCumplidos;
                                System.out.println("Llevas trabajando en la tarea: " + tiempoTotal);
                                tareaTmp.setMinutosTrancurridos(tareaTmp.getMinutosTrancurridos() + minutosCumplidos);
                            } else {
                                System.out.println("Volviendo ...");
                            }
                        }
                    } else {
                        System.out.println("No hay tareas creadas, cree una tarea antes de arrancarla");
                    }
                    break;
                case 2:
                    // Crear una nueva tarea
                    nuevaTarea(usuarioActual);
                    // Ver historial
                    System.out.println("Este es el historial de todas las tareas del usuario ...");
                    System.out.println(usuarioActual.listarTareas());
                    break;
                case 3:
                    // Ver historial
                    System.out.println("Este es el historial de todas las tareas del usuario ...");
                    System.out.println(usuarioActual.listarTareas());
                    break;
                case 4:
                    // Modificar tareas
                    System.out.println("Menu de modificacion");
                    System.out.println(usuarioActual.listarTareas());
                    System.out.println("Ingrese a que seccion desea acceder: ");
                    System.out.println("1. SeccionTrabajo");
                    System.out.println("2. SeccionEstudio");
                    System.out.println("3. SeccionDeporte");
                    System.out.println("4. SeccionCocina");
                    scanner.nextLine();
                    eleccionTareaInt = scanner.nextInt();
                    if (eleccionTareaInt == 1) {
                        eleccionTarea = "SeccionTrabajo";
                    } else if (eleccionTareaInt == 2) {
                        eleccionTarea = "SeccionEstudio";
                    } else if (eleccionTareaInt == 3) {
                        eleccionTarea = "SeccionDeporte";
                    } else if (eleccionTareaInt == 4) {
                        eleccionTarea = "SeccionCocina";
                    } else {
                        System.out.println("Opcion incorrecta");
                    }
                    System.out.println("Ingrese el ID de la tarea a modificar: ");
                    scanner.nextLine();
                    idTarea = scanner.nextLine();

                    tareaTmp = manejoUsuario.buscarEntreTareas(idTarea, eleccionTarea, usuarioActual);
                    if (tareaTmp != null) {
                        modificarTarea(tareaTmp, eleccionTarea);
                    } else {
                        System.out.println("Vuelve a intentarlo");
                    }
                    System.out.println("Tarea modificada con exito");
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

    public static void mostrarMenuEstadisticas(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Estadisticas");
            System.out.println("1. Ver bambueas actuales");
            System.out.println("2. Ver tareas");
            System.out.println("3. Ver cantidad de veces que alimentaste a " + usuarioActual.getNombrePanda());
            System.out.println("4. Ver cantidad de veces que plantaste un arbol");
            System.out.println("5. Ver cantidad de baños que le diste a " + usuarioActual.getNombrePanda());
            System.out.println("6. Ver cantidad de juguetes que le compraste a " + usuarioActual.getNombrePanda());
            System.out.println("7. Volver al menu de inicio");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    System.out.println("Bambueas actuales: " + usuarioActual.getBambuesActuales());
                    break;
                case 2:
                    System.out.println("Tareas: " + usuarioActual.listarTareas());
                    break;
                case 3:
                    System.out.println("Veces que has alimentado a " + usuarioActual.getNombrePanda() + ": " + usuarioActual.getCantBambuConsumidoPanda());
                    break;
                case 4:
                    System.out.println("Veces que has plantado un árbol: " + usuarioActual.getCantArbolesPlantados());
                    break;
                case 5:
                    System.out.println("Veces que has dado baño a " + usuarioActual.getNombrePanda() + ": " + usuarioActual.getCantLavados());
                    break;
                case 6:
                    System.out.println("Cantidad de juguetes que has comprado para " + usuarioActual.getNombrePanda() + ": " + usuarioActual.getCantJuguetes());
                    break;
                case 7:
                    System.out.println("Volviendo al menu de inicio ...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 7);
    }
    //OP 1.3.0

    public static void mostrarMenuTienda(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Tienda");
            System.out.println("Tu cantidad de bambues actual es de: " + usuarioActual.getBambuesActuales() + " bambues");
            System.out.println("1. Alimentar a tu panda con un bambu | (50 bambues)");
            System.out.println("2. Plantar un arbol de bambu | (150 bambues)");
            System.out.println("3. Limpiar al panda | (100 bambues)");
            System.out.println("4. Comprar un juguete para el panda | (300 bambues)");
            System.out.println("5. Contratar un veterinario para cuidar la salud del panda | (1000 bambues)");
            System.out.println("6. Adquirir instalaciones y habitats para el centro de pandas | (10000 bambues)");
            System.out.println("7. Salir");
            System.out.println("8. cheat");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    try {
                        alimentarPanda(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        String mensaje = e.getMensaje();
                        System.out.println(mensaje);
                    }
                    break;
                case 2:
                    try {
                        plantarArbol(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        String mensaje = e.getMensaje();
                        System.out.println(mensaje);
                    }
                    break;
                case 3:
                    try {
                        limpiarPanda(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        String mensaje = e.getMensaje();
                        System.out.println(mensaje);
                    }
                    break;
                case 4:
                    try {
                        comprarJuguete(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        String mensaje = e.getMensaje();
                        System.out.println(mensaje);
                    }
                    break;
                case 5:
                    try {
                        comprarVisitaVeterinario(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        String mensaje = e.getMensaje();
                        System.out.println(mensaje);
                    }
                    break;
                case 6:
                    try {
                        adquirirInstalaciones(usuarioActual);
                    } catch (CantidadBambuesInsuficientesException e) {
                        if (usuarioActual.getInstalacionesAdquiridas()) {
                            System.out.println("Ya a adquirido las instalaciones. No hay mas instalaciones que comprar. Gracias!");
                        } else {
                            String mensaje = e.getMensaje();
                            System.out.println(mensaje);
                        }

                    }
                    break;
                case 7:
                    System.out.println("Volviendo al menú principal...");
                    break;
                case 8:
                    aumentarBambues(usuarioActual, 30000);
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 7);
    }

    public static void mostrarMenuMisiones(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Menu Misiones");
            System.out.println("1. Crear campania de concientizacion sobre pandas | (+300 bambues)");
            System.out.println("2. Mejorar el hábitat de los pandas | (+1000 bambues)");
            System.out.println("3. Organizar una excursion educativa sobre pandas | (+1500 bambues)");
            System.out.println("4. Volver al menu de usuario");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    if (misiónExitosa(50)) {
                        System.out.println("Financia una campaña educativa para aumentar la conciencia sobre la importancia de proteger a los pandas.");
                        System.out.println("Has conseguido 300 bambues!");
                        aumentarBambues(usuarioActual, 300);
                    } else {
                        System.out.println("No se cumplió el objetivo de la campaña educativa.");
                    }
                    break;
                case 2:
                    if (misiónExitosa(33)) {
                        System.out.println("Mejora el habitat natural de los pandas, anadiendo mas vegetacion y agua potable.");
                        System.out.println("Has conseguido 1000 bambues!");
                        aumentarBambues(usuarioActual, 1000);
                    } else {
                        System.out.println("No se cumplió el objetivo de mejorar el hábitat.");
                    }
                    break;
                case 3:
                    if (misiónExitosa(20)) {
                        System.out.println("Organiza una excursion educativa para enseñar a los visitantes sobre la vida y la importancia de los pandas.");
                        System.out.println("Has conseguido 1500 bambues!");
                        aumentarBambues(usuarioActual, 1500);
                    } else {
                        System.out.println("No se cumplió el objetivo de la excursión educativa.");
                    }
                    break;
                case 4:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
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
                    break;
            }
        } while (opcion != 3);


    }
    //OP 1.4.1

    public static void cambiarNombre(Usuario usuarioActual) {
        limpiarBuffer();
        String nuevoNombre;
        if (usuarioActual != null) {
            System.out.println("Introduzca su nuevo nombre");
            nuevoNombre = scanner.nextLine();
            usuarioActual.setNombreUsuario(nuevoNombre);
        }
    }
    //OP 1.4.2

    public static void cambiarContrasena(Usuario usuarioActual) {
        limpiarBuffer();
        String nuevaContrasena;

        if (usuarioActual != null) {
            System.out.println("Ingrese la nueva contrasena");
            nuevaContrasena = scanner.nextLine();
            usuarioActual.setContrasena(nuevaContrasena);
        }
    }

    // SECTOR MISIONES

    private static boolean misiónExitosa(int porcentajeExito) {
        return random.nextInt(100) < porcentajeExito;
    }

    //**--------------------------------------------------------------------------------------------------------------**

    //SECTOR TIENDA
    public static void reducirBambues(Usuario usuarioActual, double bambuesARestar) {
        double bambuesActuales = usuarioActual.getBambuesActuales();
        bambuesActuales -= bambuesARestar;
        usuarioActual.setBambuesActuales(bambuesActuales);
    }

    public static void aumentarBambues(Usuario usuarioActual, double bambuesASumar) {
        double bambuesActuales = usuarioActual.getBambuesActuales();
        bambuesActuales += bambuesASumar;
        usuarioActual.setBambuesActuales(bambuesActuales);
    }

    public static void alimentarPanda(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 50) {
            System.out.println("Alimentaste a tu panda con un bambu ...");
            usuarioActual.alimentarPandaUsuario();
            System.out.println(usuarioActual.getNombrePanda() + " ha comido " + usuarioActual.getCantBambuConsumidoPanda() + " bambues");
            reducirBambues(usuarioActual, 50);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void plantarArbol(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 150) {
            System.out.println("Plantaste un arbol de bambu ...");
            usuarioActual.aumentarCantArbolesPlantados();
            System.out.println(usuarioActual.getNombreUsuario() + " haz plantado " + usuarioActual.getCantArbolesPlantados() + " arboles");
            reducirBambues(usuarioActual, 150);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void limpiarPanda(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 100) {
            System.out.println("Lavaste a tu panda ...");
            usuarioActual.aumentarLavados();
            System.out.println(usuarioActual.getNombreUsuario() + " haz lavado " + usuarioActual.getCantLavados() + " veces a tu panda");
            reducirBambues(usuarioActual, 100);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void comprarJuguete(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 300) {
            System.out.println("Compraste un nuevo juguete para tu panda ...");
            usuarioActual.aumentarCantJuguetes();
            System.out.println(usuarioActual.getNombreUsuario() + " haz comprado " + usuarioActual.getCantJuguetes() + " a tu panda");
            reducirBambues(usuarioActual, 300);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void comprarVisitaVeterinario(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 1000) {
            System.out.println("Adquiriste una visita al veterinario a tu panda ...");
            usuarioActual.aumentarVisitas();
            System.out.println(usuarioActual.getNombreUsuario() + " haz adquirido " + usuarioActual.getCantVisitasVeterinario() + " visitas a tu panda");
            reducirBambues(usuarioActual, 1000);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void adquirirInstalaciones(Usuario usuarioActual) throws CantidadBambuesInsuficientesException {
        limpiarBuffer();
        if (usuarioActual.getBambuesActuales() >= 10000 && !usuarioActual.getInstalacionesAdquiridas()) {
            System.out.println("Ayudaste al centro de refugios de pandas, adquiriendo nuevas instalaciones ...");
            usuarioActual.modificarInstalaciones();
            System.out.println("Felicidades " + usuarioActual.getNombreUsuario() + ", acabas de ayudar a todos los pandas del refugio!");
            reducirBambues(usuarioActual, 10000);
        } else {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    //SECTOR TAREAS
    public static void nuevaTarea(Usuario usuarioActual) {
        limpiarBuffer();
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
        System.out.print("Fecha de la tarea (YYYY/MM/DD): (presiona enter y luego ingresa)");
        scanner.nextLine();
        String fecha = scanner.nextLine();

        int minutos;
        do {
            System.out.println("-------------------------");
            System.out.print("Cantidad de minutos que durara la tarea (Mayor a 1 minuto): ");
            minutos = scanner.nextInt();
            System.out.println("-------------------------");
            if (minutos <= 1) {
                System.out.println("Has introducido una cantidad invalida de minutos. Intentalo de nuevo.");
            }

        } while (minutos <= 1);

        switch (opcion) {
            case 1:
                SeccionEstudio estudioTmp = generarEstudio(usuarioActual, titulo, objetivo, fecha, minutos);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if (calificTemp <= 0 || calificTemp > 10) {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while (calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                estudioTmp.setCalificacion(calificTemp);
                estudioTmp.setRetroalimentacion(retroalimentacion);

                usuarioActual.nuevaTareaALaColeccion(estudioTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                break;
            case 2:
                SeccionTrabajo trabajoTmp = generarTrabajo(usuarioActual, titulo, objetivo, fecha, minutos);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if (calificTemp <= 0 || calificTemp > 10) {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while (calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                trabajoTmp.setCalificacion(calificTemp);
                trabajoTmp.setRetroalimentacion(retroalimentacion);

                usuarioActual.nuevaTareaALaColeccion(trabajoTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                break;
            case 3:
                SeccionDeporte deporteTmp = generarDeporte(usuarioActual, titulo, objetivo, fecha, minutos);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if (calificTemp <= 0 || calificTemp > 10) {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while (calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea. Antes presiona enter.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                deporteTmp.setCalificacion(calificTemp);
                deporteTmp.setRetroalimentacion(retroalimentacion);
                usuarioActual.nuevaTareaALaColeccion(deporteTmp);
                System.out.println("La tarea ha sido agregada exitosamente");
                break;
            case 4:
                SeccionCocina cocinaTmp = generarCocina(usuarioActual, titulo, objetivo, fecha, minutos);
                usuarioActual.nuevaTareaALaColeccion(cocinaTmp);

                System.out.println("Como calificarias esta tarea del 1 al 10?");
                do {
                    calificTemp = scanner.nextInt();
                    if (calificTemp <= 0 || calificTemp > 10) {
                        System.out.println("Incorrecto. Introduzca del 1 al 10.");
                    }
                } while (calificTemp <= 0 || calificTemp > 10);

                System.out.println("Comenta brevemente como te sentiste con esta tarea.");
                scanner.nextLine();
                retroalimentacion = scanner.nextLine();

                cocinaTmp.setCalificacion(calificTemp);
                cocinaTmp.setRetroalimentacion(retroalimentacion);

                System.out.println("La tarea ha sido agregada exitosamente");
                break;
            default:
                System.out.println("Opción no válida");
        }

    }

    ///////////////////// GENERADORES DE TAREA
    public static SeccionTrabajo generarTrabajo(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        limpiarBuffer();
        SeccionTrabajo res = null;

        System.out.print("Sector de la tarea: ");
        scanner.nextLine();
        String sector = scanner.nextLine();

        System.out.print("Fecha límite (YYYY/MM/DD)-- deadline de la tarea: (presiona enter y luego ingresa)");
        scanner.nextLine();
        String fechaLimite = scanner.nextLine();

        String idNuevo = manejoUsuario.incrementarID("SeccionTrabajo", usuarioActual);

        return new SeccionTrabajo(titulo, objetivo, idNuevo, minutos, 0, fecha, sector, fechaLimite);
    }

    public static SeccionEstudio generarEstudio(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        limpiarBuffer();
        System.out.println("-------------------------");
        System.out.print("Categoría: (presiona enter y luego ingresa)");
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

        String idNuevo = manejoUsuario.incrementarID("SeccionEstudio", usuarioActual);
        System.out.println(idNuevo);
        return new SeccionEstudio(titulo, objetivo, idNuevo, minutos, 0, fecha, categoria, materia, unidad);


    }

    public static SeccionDeporte generarDeporte(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        limpiarBuffer();
        System.out.println("-------------------------");
        System.out.print("Ejercicios: ");
        scanner.nextLine();
        String ejercicios = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Duracion: ");
        double duracion = scanner.nextDouble();
        System.out.println("-------------------------");
        System.out.println("Intensidad: ");
        scanner.nextLine();
        String intensidad = scanner.nextLine();
        String idNuevo = manejoUsuario.incrementarID("SeccionDeporte", usuarioActual);

        return new SeccionDeporte(titulo, objetivo, idNuevo, minutos, 0, fecha, ejercicios, duracion, intensidad);

    }

    public static SeccionCocina generarCocina(Usuario usuarioActual, String titulo, String objetivo, String fecha, int minutos) {
        limpiarBuffer();
        System.out.println("-------------------------");
        System.out.print("Ingredientes (separados por coma)(presiona enter antes): ");
        scanner.nextLine();
        String ingredientes = scanner.nextLine();
        System.out.println("-------------------------");
        System.out.print("Paso a paso: (presiona enter antes)");
        scanner.nextLine();
        String pasoAPaso = scanner.nextLine();
        System.out.println("-------------------------");


        String idNuevo = manejoUsuario.incrementarID("SeccionCocina", usuarioActual);

        return new SeccionCocina(titulo, objetivo, idNuevo, minutos, 0, fecha, ingredientes, pasoAPaso);
    }

    ///////////  FUNCION PROPIA DE JAVA PARA MANEJO DE TEMPORIZADOR

    private static int iniciarTemporizador(int minutos, int minutosTrabajados) {
        final AtomicBoolean finalizado = new AtomicBoolean(false);
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        final long totalSegundos = minutos * 60;
        final long[] tiempoTotal = {minutosTrabajados * 60}; // Inicializar con el tiempo trabajado
        final long tiempoInicio = System.currentTimeMillis() / 1000; // Tiempo de inicio en segundos

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
                scanner.nextLine();
                return;
            }

            // Limpiar la consola
            System.out.print("\033[H\033[2J");
            System.out.flush();

            int minutosTranscurridos = (int) ((tiempoTotal[0] / 60) % 60);
            int segundosTranscurridos = (int) (tiempoTotal[0] % 60);
            double porcentaje = ((double) tiempoTotal[0] / totalSegundos) * 100;

            String barraProgreso = generarBarraProgreso(porcentaje);

            System.out.printf("Llevas %d minutos y %d segundos. [%s] %.2f%%\n",
                    minutosTranscurridos, segundosTranscurridos, barraProgreso, porcentaje);

            tiempoTotal[0] += 60; // Incrementar el tiempo total después de imprimir

            if (tiempoTotal[0] >= totalSegundos) {
                System.out.println("El temporizador ha finalizado después de " + minutos + " minutos.");
                System.out.println("Presione ENTER para continuar");
                finalizado.set(true);
                scheduler.shutdownNow();
            }
        }, 0, 60, TimeUnit.SECONDS); // Primera ejecución inmediatamente, luego cada 60 segundos

        try {
            hiloFinalizar.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long tiempoFin = System.currentTimeMillis() / 1000; // Tiempo de finalización en segundos
        long tiempoEjecutado = tiempoFin - tiempoInicio; // Calcular la diferencia en segundos

        return (int) (tiempoEjecutado / 60); // Retornar el tiempo ejecutado en minutos
    }


    private static String generarBarraProgreso(double porcentaje) {
        int totalCaracteres = 20; // longitud de la barra de progreso
        int caracteresLlenos = (int) (porcentaje / 100 * totalCaracteres);
        StringBuilder barra = new StringBuilder();
        for (int i = 0; i < caracteresLlenos; i++) {
            barra.append("=");
        }
        for (int i = caracteresLlenos; i < totalCaracteres; i++) {
            barra.append(" ");
        }
        return barra.toString();
    }

    // FRONT DE JSON
    public static void archivoJSON(Usuario usuarioActual) {
        String rutaArchivo = "tareas" + usuarioActual.getId();
        try {
            grabar(usuarioActual.toJSONTareas(), rutaArchivo);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        System.out.println("-------------------------");
        System.out.println("Chequea el archivo " + rutaArchivo + ".json por favor");
        System.out.println("-------------------------");

    }

    public static void limpiarBuffer() {
        if (scanner.hasNextLine()) {
            scanner.nextLine(); // Limpiar el buffer
        }
    }
    // MODIFICACION DE TAREAS

    public void modificarTareaSeleccionada(Usuario usuario, ManejoUsuario manejoUsuario, Scanner scanner) {
        System.out.println("Ingrese el ID de la tarea que desea modificar:");
        String idTarea = scanner.nextLine();
        System.out.println("Ingrese el tipo de tarea que desea modificar:");
        String tipoTarea = scanner.nextLine();
        Tarea tarea = null;
        tarea = manejoUsuario.buscarEntreTareas(idTarea, tipoTarea, usuario);
    }

    private static void modificarTarea(Tarea tarea, String tipoTarea) {
        boolean continuar = true;
        while (continuar) {
            System.out.println("Seleccione el atributo que desea modificar:");
            System.out.println("1. Título");
            System.out.println("2. Objetivo");
            System.out.println("3. Fecha");
            System.out.println("4. Calificación");
            System.out.println("5. Retroalimentación");
            System.out.println("6. Atributos específicos de " + tipoTarea);
            System.out.println("7. Salir");
            System.out.print("Opción: ");
            int opcion = Integer.parseInt(scanner.nextLine());

            switch (opcion) {
                case 1:
                    System.out.println("Ingrese el nuevo título:");
                    tarea.setTitulo(scanner.nextLine());
                    break;
                case 2:
                    System.out.println("Ingrese el nuevo objetivo:");
                    tarea.setObjetivo(scanner.nextLine());
                    break;
                case 3:
                    System.out.println("Ingrese la nueva fecha (YYYY-MM-DD):");
                    tarea.setFecha(scanner.nextLine());
                    break;
                case 4:
                    System.out.println("Ingrese la nueva calificación (1 a 10):");
                    tarea.setCalificacion(Integer.parseInt(scanner.nextLine()));
                    break;
                case 5:
                    System.out.println("Ingrese la nueva retroalimentación:");
                    tarea.setRetroalimentacion(scanner.nextLine());
                    break;
                case 6:
                    modificarAtributosEspecificos(tarea, tipoTarea, scanner);
                    break;
                case 7:
                    continuar = false;
                    break;
                default:
                    System.out.println("Opción no válida.");
            }
        }
    }

    private static void modificarAtributosEspecificos(Tarea tarea, String tipoTarea, Scanner scanner) {
        boolean continuar = true;
        while (continuar) {
            switch (tipoTarea) {
                case "SeccionTrabajo":
                    continuar = modificarSeccionTrabajo((SeccionTrabajo) tarea, scanner);
                    break;
                case "SeccionEstudio":
                    continuar = modificarSeccionEstudio((SeccionEstudio) tarea, scanner);
                    break;
                case "SeccionDeporte":
                    continuar = modificarSeccionDeporte((SeccionDeporte) tarea, scanner);
                    break;
                default:
                    System.out.println("Tipo de tarea no válido.");
                    continuar = false;
            }
        }
    }

    private static boolean modificarSeccionTrabajo(SeccionTrabajo tarea, Scanner scanner) {
        System.out.println("Seleccione el atributo que desea modificar:");
        System.out.println("1. Sector de trabajo");
        System.out.println("2. Fecha límite");
        System.out.println("3. Salir");
        System.out.print("Opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese el nuevo sector de trabajo:");
                tarea.setSector(scanner.nextLine());
                break;
            case 2:
                System.out.println("Ingrese la nueva fecha límite:");
                tarea.setFechaLimite(scanner.nextLine());
                break;
            case 3:
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true;
    }

    private static boolean modificarSeccionEstudio(SeccionEstudio tarea, Scanner scanner) {
        System.out.println("Seleccione el atributo que desea modificar:");
        System.out.println("1. Categoría");
        System.out.println("2. Materia");
        System.out.println("3. Unidad");
        System.out.println("4. Salir");
        System.out.print("Opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese la nueva categoría:");
                tarea.setCategoria(scanner.nextLine());
                break;
            case 2:
                System.out.println("Ingrese la nueva materia:");
                tarea.setMateria(scanner.nextLine());
                break;
            case 3:
                System.out.println("Ingrese la nueva unidad:");
                tarea.setUnidad(scanner.nextLine());
                break;
            case 4:
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true;
    }

    private static boolean modificarSeccionDeporte(SeccionDeporte tarea, Scanner scanner) {
        System.out.println("Seleccione el atributo que desea modificar:");
        System.out.println("1. Duración");
        System.out.println("2. Intensidad");
        System.out.println("3. Salir");
        System.out.print("Opción: ");
        int opcion = Integer.parseInt(scanner.nextLine());

        switch (opcion) {
            case 1:
                System.out.println("Ingrese la nueva duración:");
                tarea.setDuracion(Double.parseDouble(scanner.nextLine()));
                break;
            case 2:
                System.out.println("Ingrese la nueva intensidad:");
                tarea.setIntensidad(scanner.nextLine());
                break;
            case 3:
                return false;
            default:
                System.out.println("Opción no válida.");
        }
        return true;
    }
}

