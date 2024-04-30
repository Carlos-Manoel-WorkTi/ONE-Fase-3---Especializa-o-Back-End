import java.text.DecimalFormat;

public class Moeda {
    private String moeda;
    private String moeda_base;
    private String tipo;
    private double value_base_code;
    private double value_converted;
    private double valor;
    DecimalFormat formato = new DecimalFormat("#0.00");

    public String getMoeda_base() {
        return moeda_base;
    }

    public void setValue_base_code(double value_base_code) {
        this.value_base_code = value_base_code;
        this.value_converted = this.valor * this.value_base_code;
    }

    @Override
    public String toString() {
        String valorFormatado = formato.format(this.value_converted);

        return "Moeda base: " + this.moeda_base +
                "\nValor base: " + this.valor + " " + this.moeda_base +
                "\nMoeda alvo: " + this.moeda + this.tipo +
                "\nValor convertido: " + valorFormatado + " " + this.tipo;

    }

    public String getTipo() {
        return tipo;
    }

    public Moeda(int tipoMoeda, int tipoMoedaBase) {
        switch (tipoMoeda) {
            case 1:
                this.moeda = "Real brasileiro";
                this.tipo = "BRL";
                break;
            case 2:
                this.moeda = "Dólar americano";
                this.tipo = "USD";
                break;
            case 3:
                this.moeda = "Euro";
                this.tipo = "EUR";
                break;
            case 4:
                this.moeda = "Russian Ruble";
                this.tipo = "RUB";
                break;
            case 5:
                this.moeda = "Iene japonês";
                this.tipo = "JPY";
                break;
            case 6:
                this.moeda = "Argentine Peso";
                this.tipo = "ARS";
                break;
            default:
                this.moeda = "Moeda desconhecida; (BRL) por padrão";
                this.tipo = "BRL";
                break;
        }
        switch (tipoMoedaBase) {
            case 1:
                this.moeda_base = "BRL";
                break;
            case 2:
                this.moeda_base = "USD";
                break;
            case 3:
                this.moeda_base = "EUR";
                break;
            case 4:
                this.moeda_base = "RUB";
                break;
            case 5:
                this.moeda_base = "JPY";
                break;
            case 6:
                this.moeda_base = "ARS";
                break;
            default:
                this.moeda_base = "BRL";
                break;
        }

    }

    public void setValor(double valor) {
        this.valor = valor;
    }
}

