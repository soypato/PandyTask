import java.util.Scanner;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.Random;

import excepciones.deLogin.LoginIncorrectoException;
import excepciones.dePanda.CantidadBambuesInsuficientesException;
import modelo.sistema.ManejoUsuario;
import modelo.sistema.Panda;
import modelo.sistema.Usuario;
import modelo.tareas.*;
import org.json.JSONException;

import static java.lang.Thread.sleep;
import static utiles.JsonUtiles.grabar;
public class Main {
    public static void main(String[] args) throws Exception {
        try {
            ManejoMain.menu();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
