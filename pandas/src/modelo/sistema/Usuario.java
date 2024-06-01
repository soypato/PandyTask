package modelo.sistema;

public class Usuario implements Comparable {
    private double id;
    private String nombreUsuario;
    private String contrasena;
    private String correoElectronico;
    private double bambuesActuales;
    private Panda pandaDelUsuario;

    // CONSTRUCTORES

    public Usuario(double id, String nombreUsuario, String contrasena, String correoElectronico, double bambuesActuales, Panda pandaDelUsuario) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.contrasena = contrasena;
        this.correoElectronico = correoElectronico;
        this.bambuesActuales = bambuesActuales;
        this.pandaDelUsuario = pandaDelUsuario;
    }

    public Usuario()
    {
        this.id = -9; // -9 por convecion es invalido
        this.nombreUsuario = " ";
        this.contrasena = " ";
        this.correoElectronico = " ";
        this.bambuesActuales = 0;
        this.pandaDelUsuario = new Panda();
    }

    // GETTERS

    public double getId()
    {
        return id;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public String getContrasena() { // la contrasena ya viaja encriptada, por lo que no hay forma de ver la real
        return contrasena;
    }

    public String getCorreoElectronico() {
        return correoElectronico;
    }

    public double getBambuesActuales() {
        return bambuesActuales;
    }

    // Esto para el archivo
    public String getNombrePanda()
    {
        return pandaDelUsuario.getNombrePanda();
    }
    public double getCantBambuConsumidoPanda()
    {
        return pandaDelUsuario.getCantBambuConsumido();
    }

    public String getPandaAscii()
    {
        return pandaDelUsuario.getPandaAscii();
    }

    // SETTERS

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public void setCorreoElectronico(String correoElectronico) {
        this.correoElectronico = correoElectronico;
    }

    public void setBambuesActuales(double bambuesActuales) {
        this.bambuesActuales = bambuesActuales;
    }

    @Override
    public boolean equals(Object obj)
    {
        boolean res = false;
        if(obj != null)
        {
            if(obj instanceof Usuario usuarioValidado)
            {
                if(this.getId() == (usuarioValidado.getId()))
                {
                    res = true;
                }
            }
        }
        return res;
    }

    @Override
    public int compareTo(Object obj)
    {
        int resultado = -99;

        if(obj != null)
        {
            if(obj instanceof Usuario usuarioValidado)
            {
                if(this.getId() > usuarioValidado.getId())
                    resultado = -1;
                else if(this.getId() == usuarioValidado.getId())
                    resultado = 0;
                else
                    resultado = 1;

            }
        }
        return resultado;
    }


    @Override
    public int hashCode() {
        return 1;
    }

    // Omitimos la de contrasena
    @Override
    public String toString() {
        return "Usuario{" +
                "id='" + id + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", correoElectronico='" + correoElectronico + '\'' +
                '}';
    }  // IMPORTANTE: EL TOSTRING NO MUESTRA LA CONTRASENA
}
