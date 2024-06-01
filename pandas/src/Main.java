import java.sql.SQLOutput;
import java.util.Scanner;

import excepciones.deLogin.LoginIncorrectoException;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Usuario;


public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ManejoUsuario manejoUsuario;

    public static void main(String[] args) throws Exception {

        //Inicializamos el hashSet
        manejoUsuario = new ManejoUsuario();

        //Leer los datos que fueron cargados en el archivo
        try {
            manejoUsuario.entradaUsuarios();
        }
        catch (Exception e)
        {
            System.out.println("Programa iniciado correctamente");
        }

        System.out.println(manejoUsuario.mostrarTodosLosUsuarios());
        Usuario usuario1 = new Usuario("324", "pato", "1234", "patriciotubio", 0, "Pandita");
        Usuario usuario2 = new Usuario("555", "nachito", "676", "nachitoManu.com.es",0, "Pandita");


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
                    // Restablecer contraseña
                    break;
                case 4:
                    System.out.println("Saliendo del programa...");
                    try {
                        manejoUsuario.salidaUsuarios();  //Carga datos
                    } catch (Exception e) {
                        e.printStackTrace();  //Verificar esto
                    }
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 4);
    }

    public static void mostrarMenuPrincipal() {
        System.out.println("¿En qué sector desea entrar?");
        System.out.println("1. Iniciar sesión");
        System.out.println("2. Registrar nuevo usuario");
        System.out.println("3. Restablecer contraseña");
        System.out.println("4. Salir");
        System.out.print("Seleccione una opción: ");
    }

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



    public static void registrarUsuario() {
        boolean respuesta = false;
        String id;
        String nombreUsuario;
        String contrasena;
        String correoElectronico;
        String nombrePanda;
        System.out.print("ID: ");
        id = scanner.next();
        System.out.print("Nombre de usuario: ");
        nombreUsuario = scanner.next();
        System.out.print("Contraseña: ");
        contrasena = scanner.next();
        System.out.print("Correo electrónico: ");
        correoElectronico = scanner.next();
        System.out.println("Nombre del panda: ");
        nombrePanda = scanner.next();
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico, 0, nombrePanda);
        respuesta = manejoUsuario.altaUsuario(usuario);
        if(respuesta) {
            System.out.println("¡Usuario registrado correctamente!");
        }else
        {
            System.out.println("El usuario ya esta registrado, intentelo nuevamente");
        }
    }

    public static void mostrarMenuInicio(Usuario usuarioActual) {
        int opcion;
        do {
            System.out.println("Bienvenido " + usuarioActual.getNombreUsuario());
            System.out.println("Tu cantidad de bambues actual es de: " + usuarioActual.getBambuesActuales() + " bambues");
            System.out.println("Tu panda es: " + usuarioActual.getNombrePanda());
            System.out.println("Menu inicio");
            System.out.println("1. Menu de tareas ");
            System.out.println("2. Menu de recompensas ");
            System.out.println("3. Menu de misiones");
            System.out.println("4. Configuraciones");
            System.out.println("5. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            switch (opcion) {
                case 1:
                    mostrarMenuTareas();
                    break;
                case 2:
                    mostrarMenuRecompensas();
                    break;
                case 3:
                    mostrarMenuMisiones();
                    break;
                case 4:
                    mostrarMenuConfiguracion();
                    break;
                case 5:
                    System.out.println("Saliendo del programa...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 5);
    }

    public static void mostrarMenuTareas() {
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

    public static void mostrarMenuRecompensas() {
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

    public static void mostrarMenuMisiones() {
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

    public static void mostrarMenuConfiguracion() {
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
                    // Cambiar nombre
                    break;
                case 2:
                    // Cambiar contraseña
                    break;
                case 3:
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        } while (opcion != 3);




    }        

}

