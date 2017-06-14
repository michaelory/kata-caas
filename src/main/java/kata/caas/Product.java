package kata.caas;

/**
 * Created by michael on 13/06/17.
 */
public class Product {

    private String label;

    private Double amountHT;

    private Double amountTTC;

    private boolean imported;

    private boolean tvaApplied;

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Double getAmountHT() {
        return amountHT;
    }

    public void setAmountHT(Double amountHT) {
        this.amountHT = amountHT;
    }

    public Double getAmountTTC() {
        return amountTTC;
    }

    public void setAmountTTC(Double amountTTC) {
        this.amountTTC = amountTTC;
    }

    public boolean isImported() {
        return imported;
    }

    public void setImported(boolean imported) {
        this.imported = imported;
    }

    public boolean isTvaApplied() {
        return tvaApplied;
    }

    public void setTvaApplied(boolean tvaApplied) {
        this.tvaApplied = tvaApplied;
    }
}
