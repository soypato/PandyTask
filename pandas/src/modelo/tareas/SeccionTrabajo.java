package modelo.tareas;


public class SeccionTrabajo extends Tarea implements Comparable {
    private String sector;
    private String fechaLimite;

    // CONSTRUCTORES
    public SeccionTrabajo(String titulo, String objetivo, String sector, String fechaLimite, String codigo) {
        super(titulo, objetivo, codigo);
        this.sector = sector;
        this.fechaLimite = fechaLimite;
    }

    public SeccionTrabajo() {
        super();
        this.sector = " ";
        this.fechaLimite = " ";
    }

    // GETTERS

    public String getSector() {
        return sector;
    }

    public String getFechaLimite() {
        return fechaLimite;
    }

    // SETTERS

    public void setSector(String sector) {
        this.sector = sector;
    }

    public void setFechaLimite(String fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    public int hashCode() {
        return 1;
    }

    public boolean equals(Object obj) {
        boolean res = false;
        if (obj != null) {
            if (obj instanceof SeccionTrabajo trabajoTmp) {
                if (trabajoTmp.getTitulo().equals(this.getTitulo())
                        && trabajoTmp.getFechaLimite().equals(this.getFechaLimite())
                ) {
                    res = true;
                }
            }
        }
        return res;
    }

    public int compareTo(Object obj) {
        int res = -9;
        if (obj != null) {
            if (obj instanceof SeccionTrabajo trabajoTmp) {
                res = trabajoTmp.getTitulo().compareTo(this.getTitulo());
            }

        }
        return res;
    }

    @Override
    public String toString() {
        return "SeccionTrabajo{" +
                "sector='" + sector + '\'' +
                ", fechaLimite='" + fechaLimite + '\'' +
                '}';
    }
}
