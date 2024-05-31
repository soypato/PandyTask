import java.util.Scanner;
import modelo.ManejoUsuario;
import modelo.sistema.Usuario;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static ManejoUsuario manejoUsuario;

    public static void main(String[] args) throws Exception {

        manejoUsuario = new ManejoUsuario();
        try {
            manejoUsuario.salidaUsuarios();
        } catch (Exception e) {
            throw new Exception(e);
        }

        try {
            manejoUsuario.entradaUsuarios();
        }
        catch (Exception e)
        {
            System.out.println("ERROR " + e);
        }
/*
        Usuario usuario1 = new Usuario("324", "pato", "1234", "patriciotubio");
        manejoUsuario.altaUsuario(usuario1);

        // Durante todo el sistema tenemos que trabajar sobre las colecciones, no el archivo
        // El archivo se actualiza a lo ultimo

        try {
            manejoUsuario.salidaUsuarios();
        } catch (Exception e) {
            throw new Exception(e);
        }

        */


        /*int opcion;
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

    public static void iniciarSesion() {
        String nombreUsuario;
        String contrasena;
        do {
            System.out.print("Nombre de usuario: ");
            nombreUsuario = scanner.next();
            System.out.print("Contraseña: ");
            contrasena = scanner.next();
            if (!manejoUsuario.comprobarLogin(nombreUsuario, contrasena)) {
                System.out.println("Usuario o contraseña incorrectos. Intente nuevamente.");
            }
        } while (!manejoUsuario.comprobarLogin(nombreUsuario, contrasena));
        System.out.println("¡Inicio de sesión exitoso!");
        mostrarMenuInicio();
    }

    public static void registrarUsuario() {
        String id;
        String nombreUsuario;
        String contrasena;
        String correoElectronico;
        System.out.print("ID: ");
        id = scanner.next();
        System.out.print("Nombre de usuario: ");
        nombreUsuario = scanner.next();
        System.out.print("Contraseña: ");
        contrasena = scanner.next();
        System.out.print("Correo electrónico: ");
        correoElectronico = scanner.next();
        Usuario usuario = new Usuario(id, nombreUsuario, contrasena, correoElectronico);
        manejoUsuario.agregarUsuario(usuario);
        System.out.println("¡Usuario registrado correctamente!");
    }

    public static void mostrarMenuInicio() {
        int opcion;
        do {
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
        } while (opcion != 3);*/
    }
}
