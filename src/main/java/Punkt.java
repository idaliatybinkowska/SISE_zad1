public class Punkt implements Comparable<Punkt> {
    private int wartosc;
    private int x;
    private int y;

    public Punkt(Punkt p){
        this.wartosc=p.wartosc;
        this.x=p.x;
        this.y=p.y;
    }

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

    public int compareTo(Punkt o) {

        if (this.getWartosc() == o.getWartosc() && this.getX() == o.getX() && this.getY() == o.getY()) {
            return 0;
        } else {
            return -1;
        }
    }
}
