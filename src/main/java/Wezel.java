import java.util.ArrayList;
import java.util.List;

public class Wezel {
    private Uklad uklad;
    private List<Wezel> dzieci = new ArrayList<Wezel>();
    private Wezel rodzic;
    private char kierunek;
    private int poziomWDrzewie;
    private int wartoscFunkcji;

    public Wezel() {
    }

    public Wezel(Uklad uklad, int poziomWDrzewie) {
        this.uklad = uklad;
        this.poziomWDrzewie = poziomWDrzewie;
    }

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

    public int getWartoscFunkcji() {
        return wartoscFunkcji;
    }

    public void setWartoscFunkcji(int wartoscFunkcji) {
        this.wartoscFunkcji = wartoscFunkcji;
    }

    public String znajdzMozliweRuchy(String porzadekPrzechodzenia)
    {
        char poprzedniRuch = kierunek;
        Punkt punktZero = this.uklad.getPunktByWartosc(0);
        if (punktZero.getX() == 0 || poprzedniRuch == 'R') {
            porzadekPrzechodzenia = porzadekPrzechodzenia.replace("L", "");
        }
        if (punktZero.getX() == Uklad.liczbaKolumn - 1 || poprzedniRuch == 'L') {
            porzadekPrzechodzenia = porzadekPrzechodzenia.replace("R", "");
        }
        if (punktZero.getY() == 0 || poprzedniRuch == 'D') {
            porzadekPrzechodzenia = porzadekPrzechodzenia.replace("U", "");
        }
        if (punktZero.getY() == Uklad.liczbaWierszy - 1 || poprzedniRuch == 'U') {
            porzadekPrzechodzenia = porzadekPrzechodzenia.replace("D", "");
        }
        return porzadekPrzechodzenia;
    }

    public void stworzDzieci(String porzadekPrzechodzenia, List<Wezel> przetworzone) {
        String porzadek = znajdzMozliweRuchy(porzadekPrzechodzenia);

        while (!porzadek.equals("")) {
            Wezel dziecko = new Wezel();
            dziecko.rodzic = this;
            dziecko.setPoziomWDrzewie(this.poziomWDrzewie + 1);
            dziecko.setUklad(new Uklad(this.getUklad()));
            this.dzieci.add(dziecko);
            if (porzadek.charAt(0) == 'L') {
                dziecko.getUklad().idzWLewo();
                dziecko.setKierunek('L');
                porzadek = porzadek.replace("L", "");
                przetworzone.add(dziecko);
            } else if (porzadek.charAt(0) == 'R') {
                dziecko.getUklad().idzWPrawo();
                dziecko.setKierunek('R');
                porzadek = porzadek.replace("R", "");
                przetworzone.add(dziecko);
            } else if (porzadek.charAt(0) == 'U') {
                dziecko.getUklad().idzWGore();
                dziecko.setKierunek('U');
                porzadek = porzadek.replace("U", "");
                przetworzone.add(dziecko);
            } else if (porzadek.charAt(0) == 'D') {
                dziecko.getUklad().idzWDol();
                dziecko.setKierunek('D');
                porzadek = porzadek.replace("D", "");
                przetworzone.add(dziecko);

            }
        }
    }

    public void astar(Uklad ukladDocelowy, String metryka){
        for (Wezel dziecko: dzieci) {
            if(metryka.equals("hamm"))
                dziecko.wartoscFunkcji=dziecko.poziomWDrzewie + heurystykaHamming(ukladDocelowy, dziecko);
            else
                dziecko.wartoscFunkcji=dziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, dziecko);
        }
    }

    public int heurystykaManhattan(Uklad ukladDocelowy, Wezel dziecko) {
        int suma = 0;
        int rozmiarUkladu = dziecko.getUklad().getPunkty().size();
        List<Punkt> punktyRozwiazanie = ukladDocelowy.getPunkty();
        List<Punkt> punktyDziecko = dziecko.getUklad().getPunkty();
        for (int i = 0; i < rozmiarUkladu; i++) {
            for (int j = 0; j < rozmiarUkladu; j++) {
                if (punktyDziecko.get(i).getWartosc() == punktyRozwiazanie.get(j).getWartosc() && punktyDziecko.get(i).getWartosc()!=0) {
                    suma += (Math.abs(punktyDziecko.get(i).getX() - punktyRozwiazanie.get(j).getX())) +
                            (Math.abs(punktyDziecko.get(i).getY() - punktyRozwiazanie.get(j).getY()));
                    break;
                }
            }
        }
        return suma;
    }

    public int heurystykaHamming(Uklad ukladDocelowy, Wezel dziecko) {
        int licznik = 0;
        int rozmiarUkladu = dziecko.getUklad().getPunkty().size();
        List<Punkt> punktyRozwiazanie = ukladDocelowy.getPunkty();
        List<Punkt> punktyDziecko = dziecko.getUklad().getPunkty();
        for (int i = 0; i < rozmiarUkladu; i++) {
            for (int j = 0; j < rozmiarUkladu; j++) {
                if (punktyDziecko.get(i).getWartosc() != punktyRozwiazanie.get(j).getWartosc() &&
                        punktyDziecko.get(i).getWartosc()!=0 &&
                        punktyDziecko.get(i).getX() == punktyRozwiazanie.get(j).getX() &&
                        punktyDziecko.get(i).getY() == punktyRozwiazanie.get(j).getY()) {
                        licznik++;
                }
            }
        }
        return licznik;
    }


    @Override
    public String toString() {
        return "\nWezel{" +
                "uklad=" + uklad +
                ", kierunek=" + kierunek +
                '}';
    }
}

