package modelo.sistema;

import java.io.Serializable;

public class Panda implements Serializable {
    private String nombrePanda;
    private double cantBambuConsumido;

    // Representación del panda en arte ASCII
    private final String pandaAscii =
            "⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⢀⡤⠊⠉⠀⠀⠉⠑⢄⠀⣀⣠⠤⠴⠒⠒⠒⠦⠤⠤⣀⡀⣠⠴⠚⠉⠉⠈⠉⠓⢦⡀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⡞⠀⢀⡴⠚⠉⢁⡶⠚⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠉⠓⢤⡒⠉⠓⢄⠀⠀⠀⢳⠀⠀⠀\n" +
                    "⠀⠀⠀⢸⠀⠀⡌⠀⠀⣠⠊⠀⠀⠀⣀⡤⠤⣄⠀⠀⠀⠀⠀⡴⠊⠉⠒⢤⡑⣄⠀⢸⠀⠀⠀⢸⠀⠀⠀\n" +
                    "⠀⠀⠀⢰⠀⠀⠸⢄⣰⠁⠀⠀⡴⠋⠀⠀⠀⠈⢧⠀⠀⠀⣸⠀⠀⣀⠀⠀⠱⡌⢦⡼⠀⠀⢠⠟⠀⠀⠀\n" +
                    "⠀⠀⠀⠈⠳⣄⣀⣰⠃⠀⢀⡞⠀⠀⢠⣾⣿⡆⣼⠀⠀⠀⣿⠀⣾⣿⣿⡄⠀⠹⡀⢣⣀⠴⠋⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⢀⠇⠀⠀⡾⠀⠀⠀⠸⣿⣿⣷⠃⠀⠀⠀⠈⢧⠻⠿⠿⠇⠀⠀⡇⠈⡆⠀⠀⠀⠀⠀⠀\n" +
                    "⣤⢤⠀⠀⠀⠀⡜⠀⠀⠀⠹⡄⠀⠀⠀⣠⠞⠁⣠⠔⠒⠒⠦⡌⠓⢤⣀⣀⣀⡠⠇⠀⢳⠀⠀⠀⠀⠀⠀\n" +
                    "⢧⡀⠣⡀⠀⢀⡇⠀⠀⠀⠀⠉⠓⠋⠉⠀⠀⠀⠧⣀⠀⠀⣠⠇⠀⠀⠀⠀⠀⠀⠀⠀⢸⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠙⢄⠘⣄⠈⡇⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⠀⠀⠀⢩⠏⠀⠀⠀⠀⠀⠀⠀⠀⠀⢠⡟⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠹⣍⠣⡘⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠑⠢⠤⠾⠔⠂⠀⠀⠀⠀⠀⠀⢀⣠⡋⠀⠀⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠈⢢⡙⢞⠉⠓⠦⢄⣀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣀⡤⠔⠚⠉⠀⠈⠳⣄⠀⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⢠⠞⠙⢄⠱⣄⠀⡴⠈⠉⠙⠒⠒⠒⠒⠒⠒⠒⠋⠉⠁⠀⠀⢢⠀⠀⠀⠀⠀⠈⢆⠀⠀⠀⠀\n" +
                    "⠀⠀⠀⢠⠃⠀⠀⠐⠓⠋⠙⠛⠲⣄⢀⡠⠔⠚⠋⠉⠉⠉⠉⠓⠲⢤⣀⠈⣧⠤⠒⠒⠀⠀⠘⡇⠀⠀⠀\n" +
                    "⠀⠀⠀⢈⠀⠀⠀⠀⠀⠀⠀⠀⣛⠛⢯⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⣿⣷⠄⠀⠀⠀⠀⠀⡇⠀⠀⠀\n" +
                    "⠀⠀⠀⠸⣆⠀⠀⠀⠀⠀⠀⢈⡛⢁⡟⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢸⠃⣶⡆⠀⠀⠀⠀⢠⡇⠀⠀⠀\n" +
                    "⠀⠀⠀⠀⠈⢹⡶⠤⣤⣠⣤⡬⠷⢯⡜⢦⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⢧⣘⣛⣀⣀⣠⡴⠯⠤⢄⡀⠀\n" +
                    "⠀⠀⠀⢀⠖⠉⢀⣀⣀⣀⠉⠳⣄⠀⠘⢆⠙⢄⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⣨⠟⠋⣁⣀⣀⡀⠀⠙⣆\n" +
                    "⠀⠀⢠⡏⢠⠚⡿⢧⣏⣻⠉⠢⡌⢣⡀⠀⠑⣄⠇⠀⠀⠀⠀⠀⠀⠀⠀⢀⡜⡡⠒⢻⣉⣷⡤⡉⢢⠀⢸\n" +
                    "⠀⠀⠘⡇⠇⣠⣷⠜⢀⠤⠒⠢⡈⢦⣱⡀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⢀⣾⠟⢠⠖⠒⠯⡘⢶⠧⣀⣀⠏\n" +
                    "⠀⠀⠀⠹⣾⡷⠼⠀⡏⠀⠀⢠⠃⠀⠹⡗⠲⠤⠤⠤⠤⠤⠤⠴⠖⠒⢹⡏⠀⢹⡀⠀⢀⡇⠐⣶⡿⠋⠀\n" +
                    "⠀⠀⠀⠀⠈⠻⣦⡀⠙⠒⠊⠁⠀⠀⣠⡧⠤⠔⠒⠒⠒⠒⠒⠋⠁⠒⠺⣧⡀⠀⠈⢉⣉⡤⠖⠋⠁⠀⠀\n" +
                    "⠀⠀⠀⠀⠀⠀⠀⠉⠉⠒⠒⠒⠒⠉⠁⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠀⠈⠉⠉⠉⠀⠀⠀⠀⠀⠀⠀";



    public Panda(String nombrePanda, double cantBambuConsumido) {
        this.nombrePanda = nombrePanda;
        this.cantBambuConsumido = cantBambuConsumido;
    }

    public Panda(String nombrePanda) {
        this.nombrePanda = nombrePanda;
        this.cantBambuConsumido = 0;
    }

    public Panda()
    {
        this.nombrePanda = " ";
        this.cantBambuConsumido = 0;
    }

    public String getPandaAscii()
    {
       return pandaAscii;
    }

    public String getNombrePanda() {
        return nombrePanda;
    }

    public double getCantBambuConsumido() {
        return cantBambuConsumido;
    }

    public void setNombrePanda(String nombrePanda) {
        this.nombrePanda = nombrePanda;
    }

    public void setCantBambuConsumido(double cantBambuConsumido) {
        this.cantBambuConsumido = cantBambuConsumido;
    }

}
