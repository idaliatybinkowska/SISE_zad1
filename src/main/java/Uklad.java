import java.util.ArrayList;
import java.util.List;

public class Uklad implements Comparable<Uklad> {
    public static int liczbaWierszy;
    public static int liczbaKolumn;
    private List<Punkt> punkty;

    public Uklad(Uklad u) {
        this.punkty = new ArrayList<Punkt>();
        for (int i = 0; i < u.getPunkty().size() ; i++) {
           this.punkty.add(new Punkt( u.punkty.get(i)));
        }

    }

    public Uklad(List<Punkt> punkty) {
        this.punkty = punkty;
    }

    public List<Punkt> getPunkty() {
        return punkty;
    }

    public void setPunkty(List<Punkt> punkty) {
        this.punkty = punkty;
    }

    public Punkt getPunktByWartosc(int wartosc) {
        for (Punkt p : punkty) {
            if (p.getWartosc() == wartosc)
                return p;
        }
        return null;
    }

    public Punkt getPunktByXZ(int x, int y) {
        for (Punkt p : punkty) {
            if (p.getX() == x && p.getY() == y)
                return p;
        }
        return null;
    }

    public void idzWPrawo() {
        Punkt zero = getPunktByWartosc(0);
        getPunktByXZ(zero.getX() + 1, zero.getY()).setX(zero.getX());
        zero.setX(zero.getX() + 1);
    }
    public void idzWLewo() {
        Punkt zero = getPunktByWartosc(0);
        getPunktByXZ(zero.getX() - 1, zero.getY()).setX(zero.getX());
        zero.setX(zero.getX() - 1);
    }
    public void idzWGore() {
        Punkt zero = getPunktByWartosc(0);
        getPunktByXZ(zero.getX(), zero.getY() - 1).setY(zero.getY());
        zero.setY(zero.getY() - 1);
    }
    public void idzWDol() {
        Punkt zero = getPunktByWartosc(0);
        getPunktByXZ(zero.getX(), zero.getY() + 1).setY(zero.getY());
        zero.setY(zero.getY() + 1);
    }


    public int compareTo(Uklad o) {
        for (int i = 0; i < punkty.size(); i++) {
            for (int j = 0; j < o.getPunkty().size(); j++) {
                if (punkty.get(i).getWartosc() == o.getPunkty().get(j).getWartosc()) {
                    if (punkty.get(i).compareTo(o.getPunkty().get(j)) != 0) {
                        return -1;
                    } else {
                        break;
                    }
                }
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        return "Uklad{" +
                "punkty=" + punkty +
                '}';
    }
}