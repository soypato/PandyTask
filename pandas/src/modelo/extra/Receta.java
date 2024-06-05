package modelo.extra;

import org.json.JSONException;
import org.json.JSONObject;

public class Receta {
    private String ingredientes;
    private String pasoAPaso;

    public Receta(String ingredientes, String pasoAPaso) {
        this.ingredientes = ingredientes;
        this.pasoAPaso = pasoAPaso;
    }

    public Receta() {
        this.ingredientes = "(no especificado)";
        this.pasoAPaso = "(no especificado)";
    }

    public String getIngredientes() {
        return ingredientes;
    }

    public String getPasoAPaso() {
        return pasoAPaso;
    }

    public JSONObject toJson()
    {
        JSONObject res = new JSONObject();
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
        return "Receta{" +
                "ingredientes='" + ingredientes + '\'' +
                ", pasoAPaso='" + pasoAPaso + '\'' +
                '}';
    }
}
