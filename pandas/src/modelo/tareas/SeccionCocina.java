package modelo.tareas;


import org.json.JSONException;
import org.json.JSONObject;

public class SeccionCocina extends Tarea{
    private String ingredientes;
    private String pasoAPaso;

    public SeccionCocina(String titulo, String objetivo, String codigo, int temporizador, int minutosTranscurridos, String fecha, String ingredientes, String pasoAPaso)
    {
        super(titulo, objetivo, codigo, temporizador, minutosTranscurridos, fecha, "SeccionCocina");
        this.ingredientes = ingredientes;
        this.pasoAPaso = pasoAPaso;
    }

    public SeccionCocina()
    {
        super("SeccionCocina");
        this.ingredientes = "";
        this.pasoAPaso = "";
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(String ingredientes) {
        this.ingredientes = ingredientes;
    }

    public String getPasoAPaso() {
        return pasoAPaso;
    }

    public void setPasoAPaso(String pasoAPaso) {
        this.pasoAPaso = pasoAPaso;
    }

    @Override
    public int hashCode() {
        return 1;
    }


    public JSONObject toJson()
    {
        JSONObject res = super.toJson();
        try {
            res.put("ingredientes", ingredientes);
            res.put("pasoAPaso", pasoAPaso);
        } catch (JSONException e) {
            throw new RuntimeException(e);
        }
        return res;
    }


    @Override
    public String toString() {
        return "SeccionCocina{\n" +
                super.toString() +
                "\n\tIngredientes: " + ingredientes +
                ",\n\tPaso a Paso: " + pasoAPaso +
                "\n}";
    }


}
