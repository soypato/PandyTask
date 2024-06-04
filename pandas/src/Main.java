import java.sql.SQLOutput;
import java.util.Scanner;

import excepciones.deLogin.LoginIncorrectoException;
import excepciones.dePanda.CantidadBambuesInsuficientesException;
import modelo.extra.Receta;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Panda;
import modelo.sistema.Usuario;
// import modelo.tareas.ManejoTarea;
import modelo.tareas.SeccionEstudio;
import modelo.tareas.SeccionTrabajo;
import modelo.tareas.Tarea;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ManejoUsuario manejoUsuario;
    // static ManejoTarea manejoTarea;

    public static void main(String[] args) throws Exception {

        //Inicializamos el hashSet
        manejoUsuario = new ManejoUsuario();
        // manejoTarea = new ManejoTarea();

        //Leer los datos que fueron cargados en el archivo
        try {
            manejoUsuario.entradaUsuarios();
            System.out.println("Programa iniciado correctamente");
        } catch (Exception e) {
            System.err.println("Se produjo un error al iniciar el programa, importar el archivo de inicio: si es el primer inicio, reinicia presionando 4.");
        }
        try {
           // manejoTarea.entradaTarea();
            System.out.println("Tareas iniciadas correctamente");
        } catch (Exception e) {
            System.err.println("Se produjo un error al cargar las tareas: si es el primer inicio del programa, reinicia presionando 4.");
            e.printStackTrace();
        }
      //  System.out.println(manejoTarea.mostrarTareas());
        System.out.println(manejoUsuario.mostrarTodosLosUsuarios());
        /* SeccionTrabajo tareaPrueba = new SeccionTrabajo(
                "Proyecto X",
                "Completar el desarrollo del módulo",
                "Tecnología",
                "2024-12-31",
                "T001"
        ); */
        /*tareaPrueba.setTiempoTranscurrido(120);
        tareaPrueba.setCalificacion(85);
        tareaPrueba.setRetroalimentacion("Buen trabajo, pero se puede mejorar.");
        tareaPrueba.setFecha("2024-06-02");*/


        // manejoTarea.altaTarea(tareaPrueba);
        Usuario usuario1 = new Usuario(322, "pato", "1234", "patriciotubio" ,0, new Panda("Pandita"), 10, 0, 2, 0, false);
        Usuario usuario2 = new Usuario(324, "nachito", "676", "mailNachito", 0, new Panda("Pandito"), 5, 10, 0, 0, true);

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
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico, 0, new Panda(nombrePanda), 0, 0, 0, 0, false);
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
            System.out.println("3. Menu de tienda");
            System.out.println("4. Menu de misiones");
            System.out.println("5. Configuraciones");
            System.out.println("6. Salir");
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
                    mostrarMenuTienda(usuarioActual);
                    break;
                case 4:
                    mostrarMenuMisiones(usuarioActual);
                    break;
                case 5:
                    mostrarMenuConfiguracion(usuarioActual);
                    break;
                case 6:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 6);
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
                        if(usuarioActual.getInstalacionesAdquiridas())
                        {
                            System.out.println("Ya a adquirido las instalaciones. No hay mas instalaciones que comprar. Gracias!");
                        } else
                        {
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
            System.out.println("1. Crear campania de concientizacion sobre pandas");
            System.out.println("Financia una campaña educativa para aumentar la conciencia sobre la importancia de proteger a los pandas.");
            System.out.println("2. Mejorar el hábitat de los pandas");
            System.out.println("Mejora el habitat natural de los pandas, anadiendo mas vegetacion y agua potable.");
            System.out.println("3. Organizar una excursion educativa sobre pandas");
            System.out.println("Organiza una excursion educativa para enseñar a los visitantes sobre la vida y la importancia de los pandas.");
            System.out.println("4. ");
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

    //SECTOR TIENDA

    public static void reducirBambues(Usuario usuarioActual, double bambuesARestar)
    {
        double bambuesActuales = usuarioActual.getBambuesActuales();
        bambuesActuales -= bambuesARestar;
        usuarioActual.setBambuesActuales(bambuesActuales);
    }

    public static void aumentarBambues(Usuario usuarioActual, double bambuesASumar)
    {
        double bambuesActuales = usuarioActual.getBambuesActuales();
        bambuesActuales += bambuesASumar;
        usuarioActual.setBambuesActuales(bambuesActuales);
    }

    public static void alimentarPanda(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 50)  {
        System.out.println("Alimentaste a tu panda con un bambu ...");
        usuarioActual.alimentarPandaUsuario();
        System.out.println(usuarioActual.getNombrePanda() + " ha comido " + usuarioActual.getCantBambuConsumidoPanda() + " bambues");
        reducirBambues(usuarioActual, 50);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void plantarArbol(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 150)  {
            System.out.println("Plantaste un arbol de bambu ...");
            usuarioActual.aumentarCantArbolesPlantados();
            System.out.println(usuarioActual.getNombreUsuario()+" haz plantado " + usuarioActual.getCantArbolesPlantados() + " arboles");
            reducirBambues(usuarioActual, 150);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void limpiarPanda(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 100)  {
            System.out.println("Lavaste a tu panda ...");
            usuarioActual.aumentarLavados();
            System.out.println(usuarioActual.getNombreUsuario()+" haz lavado " + usuarioActual.getCantLavados() + " veces a tu panda");
            reducirBambues(usuarioActual, 100);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void comprarJuguete(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 300)  {
            System.out.println("Compraste un nuevo juguete para tu panda ...");
            usuarioActual.aumentarCantJuguetes();
            System.out.println(usuarioActual.getNombreUsuario()+" haz comprado " + usuarioActual.getCantJuguetes() + " a tu panda");
            reducirBambues(usuarioActual, 300);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }

    public static void comprarVisitaVeterinario(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 1000)  {
            System.out.println("Adquiriste una visita al veterinario a tu panda ...");
            usuarioActual.aumentarVisitas();
            System.out.println(usuarioActual.getNombreUsuario()+" haz adquirido " + usuarioActual.getCantVisitasVeterinario() + " visitas a tu panda");
            reducirBambues(usuarioActual, 1000);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }
    public static void adquirirInstalaciones(Usuario usuarioActual) throws CantidadBambuesInsuficientesException
    {
        if(usuarioActual.getBambuesActuales() >= 10000 && !usuarioActual.getInstalacionesAdquiridas())  {
            System.out.println("Ayudaste al centro de refugios de pandas, adquiriendo nuevas instalaciones ...");
            usuarioActual.modificarInstalaciones();
            System.out.println("Felicidades " +usuarioActual.getNombreUsuario()+ ", acabas de ayudar a todos los pandas del refugio!");
            reducirBambues(usuarioActual, 10000);
        }else
        {
            throw new CantidadBambuesInsuficientesException("No tienes la suficiente cantidad de bambues ...");
        }
    }
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

