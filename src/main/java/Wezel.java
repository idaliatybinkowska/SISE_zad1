import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Queue;

public class Wezel {
    private Uklad uklad;
    private List<Wezel> dzieci = new ArrayList<Wezel>();
    private Wezel rodzic;
    private char kierunek;
    private int poziomWDrzewie;

    public Uklad getUklad() {
        return uklad;
    }

    public void setUklad(Uklad uklad) {
        this.uklad = uklad;
    }

    public List<Wezel> getDzieci() {
        return dzieci;
    }

    public void setDzieci(List<Wezel> dzieci) {
        this.dzieci = dzieci;
    }

    public Wezel getRodzic() {
        return rodzic;
    }

    public void setRodzic(Wezel rodzic) {
        this.rodzic = rodzic;
    }

    public char getKierunek() {
        return kierunek;
    }

    public void setKierunek(char kierunek) {
        this.kierunek = kierunek;
    }

    public int getPoziomWDrzewie() {
        return poziomWDrzewie;
    }

    public void setPoziomWDrzewie(int poziomWDrzewie) {
        this.poziomWDrzewie = poziomWDrzewie;
    }

    public void stworzDzieci(String porzadekPrzechodzenia, char poprzedniRuch, Collection<Wezel> kolekcja) {
        Punkt punktZero = this.uklad.getPunktByWartosc(0);
        String porzadek = porzadekPrzechodzenia;
        if (punktZero.getX() == 0 || poprzedniRuch == 'R') {
            porzadek = porzadek.replace("L", "");
        }
        if (punktZero.getX() == Uklad.liczbaKolumn - 1 || poprzedniRuch == 'L') {
            porzadek = porzadek.replace("R", "");
        }
        if (punktZero.getY() == 0 || poprzedniRuch == 'D') {
            porzadek = porzadek.replace("U", "");
        }
        if (punktZero.getY() == Uklad.liczbaWierszy - 1 || poprzedniRuch == 'U') {
            porzadek = porzadek.replace("D", "");
        }

        //wykonajRuch(porzadek.charAt(0), ukladPoczatkowy);
        while (!porzadek.equals("")) {
            Wezel dziecko = new Wezel();
            dziecko.rodzic = this;
            dziecko.setPoziomWDrzewie(this.poziomWDrzewie+1);
            dziecko.setUklad(new Uklad(this.getUklad()));
            this.dzieci.add(dziecko);
            if (porzadek.charAt(0) == 'L') {
                dziecko.getUklad().idzWLewo();
                dziecko.setKierunek('L');
                porzadek = porzadek.replace("L", "");
                kolekcja.add(dziecko);
            } else if (porzadek.charAt(0) == 'R') {
                dziecko.getUklad().idzWPrawo();
                dziecko.setKierunek('R');
                porzadek = porzadek.replace("R", "");
                kolekcja.add(dziecko);
            } else if (porzadek.charAt(0) == 'U') {
                dziecko.getUklad().idzWGore();
                dziecko.setKierunek('U');
                porzadek = porzadek.replace("U", "");
                kolekcja.add(dziecko);
            } else if (porzadek.charAt(0) == 'D') {
                dziecko.getUklad().idzWDol();
                dziecko.setKierunek('D');
                porzadek = porzadek.replace("D", "");
                kolekcja.add(dziecko);
            }
        }
    }

    @Override
    public String toString() {
        return "\nWezel{" +
                "uklad=" + uklad +
                ", kierunek=" + kierunek +
                '}';
    }
}

