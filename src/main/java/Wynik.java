import java.util.List;

public class Wynik {
    private List<Wezel> przetworzone;
    private Wezel obecnyWezel;

    public List<Wezel> getPrzetworzone() {
        return przetworzone;
    }

    public void setPrzetworzone(List<Wezel> przetworzone) {
        this.przetworzone = przetworzone;
    }

    public Wezel getObecnyWezel() {
        return obecnyWezel;
    }

    public void setObecnyWezel(Wezel obecnyWezel) {
        this.obecnyWezel = obecnyWezel;
    }
}
