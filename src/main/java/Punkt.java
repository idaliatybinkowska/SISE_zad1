public class Punkt {
    private int wartosc;
    private int x;
    private int y;

    public Punkt() {
    }

    public Punkt(int wartosc, int x, int y) {
        this.wartosc = wartosc;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWartosc() {
        return wartosc;
    }

    public void setWartosc(int wartosc) {
        this.wartosc = wartosc;
    }


}
