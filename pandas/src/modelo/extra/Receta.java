package modelo.extra;

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
}
