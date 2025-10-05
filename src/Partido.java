public class Partido {
    private Pareja pareja1;
    private Pareja pareja2;
    private String resultado;

    public Partido(Pareja pareja1, Pareja pareja2) {
        this.pareja1 = pareja1;
        this.pareja2 = pareja2;
        this.resultado = "Pendiente";
    }

    public Pareja getPareja1() {
        return pareja1;
    }

    public Pareja getPareja2() {
        return pareja2;
    }

    public String getResultado() {
        return resultado;
    }

    public void setResultado(String resultado) {
        this.resultado = resultado;
    }

    @Override
    public String toString() {
        return "Partido: " + pareja1.toString() + " vs " + pareja2.toString() +
                " - Resultado: " + resultado;
    }
}
