package modelo;

public class Prioridad {

    private int idPrioridad;
    private String tipo;
    private int horasSLA;

    public Prioridad() {
    }

    public Prioridad(String tipo, int horasSLA) {
        this.tipo = tipo;
        this.horasSLA = horasSLA;
    }

    public int getIdPrioridad() {
        return idPrioridad;
    }

    public void setIdPrioridad(int idPrioridad) {
        this.idPrioridad = idPrioridad;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public int getHorasSLA() {
        return horasSLA;
    }

    public void setHorasSLA(int horasSLA) {
        this.horasSLA = horasSLA;
    }
}
