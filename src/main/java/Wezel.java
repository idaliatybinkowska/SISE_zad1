import java.util.ArrayList;
import java.util.List;

public class Wezel {
    private Uklad uklad;
    private List<Wezel> dzieci = new ArrayList<Wezel>();
    private Wezel rodzic;
    private char kierunek;
    private int poziomWDrzewie;

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

    public List<Wezel> znajdzNajlepszeDzieci(Uklad ukladDocelowy, List<Wezel> dzieciaki, String metryka){
        int najwiekszaWartosc;
        if(metryka.equals("hamm"))
            najwiekszaWartosc = dzieciaki.get(0).poziomWDrzewie + heurystykaHamming(ukladDocelowy, dzieciaki.get(0));
        else
            najwiekszaWartosc = dzieciaki.get(0).poziomWDrzewie + heurystykaManhattan(ukladDocelowy, dzieciaki.get(0));

        List<Integer> wartosciFunkcji = new ArrayList<Integer>();
        int obecnaWartosc;
        for (Wezel dziecko : dzieciaki) {
            if(metryka.equals("hamm"))
            {
                obecnaWartosc = dziecko.poziomWDrzewie + heurystykaHamming(ukladDocelowy, dziecko);
                if (obecnaWartosc > najwiekszaWartosc) {
                    najwiekszaWartosc = obecnaWartosc;
                }
            }
            else
            {
                obecnaWartosc = dziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, dziecko);
                if (obecnaWartosc < najwiekszaWartosc) {
                    najwiekszaWartosc = obecnaWartosc;
                }
            }
            wartosciFunkcji.add(obecnaWartosc);
        }
        List<Wezel> najlepszeDzieci = new ArrayList<Wezel>();
        for (int i = 0; i < wartosciFunkcji.size() ; i++) {
            if(wartosciFunkcji.get(i) == najwiekszaWartosc) {
                najlepszeDzieci.add(dzieciaki.get(i));
            }
        }
        return najlepszeDzieci;
    }

    public Wezel znajdzNajlepszeDzieckoManhattan(Uklad ukladDocelowy,List<Wezel> dzieciaki, List<Wezel> przetworzone) {
        List<Wezel> najlepszeDzieci = znajdzNajlepszeDzieci(ukladDocelowy, dzieciaki,"manh");
        if(najlepszeDzieci.size()==1 || najlepszeDzieci.get(0).getUklad().compareTo(ukladDocelowy)==0) {
            return najlepszeDzieci.get(0);
        } else {
            List<Wezel> wnuczki = new ArrayList<Wezel>();

            for (int i = 0; i <najlepszeDzieci.size() ; i++) {
                najlepszeDzieci.get(i).stworzDzieci("LURD",przetworzone);
                wnuczki.addAll(najlepszeDzieci.get(i).getDzieci());
            }
            return znajdzNajlepszeDzieckoManhattan(ukladDocelowy,wnuczki,przetworzone);
        }
    }

    public Wezel znajdzNajlepszeDzieckoHamming(Uklad ukladDocelowy,List<Wezel> dzieciaki, List<Wezel> przetworzone) {
        List<Wezel> najlepszeDzieci = znajdzNajlepszeDzieci(ukladDocelowy, dzieciaki,"hamm");
        if(najlepszeDzieci.size()==1 || najlepszeDzieci.get(0).getUklad().compareTo(ukladDocelowy)==0) {
            return najlepszeDzieci.get(0);
        } else {
            List<Wezel> wnuczki = new ArrayList<Wezel>();

            for (int i = 0; i <najlepszeDzieci.size() ; i++) {
                najlepszeDzieci.get(i).stworzDzieci("LURD",przetworzone);
                wnuczki.addAll(najlepszeDzieci.get(i).getDzieci());
            }
            return znajdzNajlepszeDzieckoHamming(ukladDocelowy,wnuczki,przetworzone);
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
                if (punktyDziecko.get(i).getWartosc() == punktyRozwiazanie.get(j).getWartosc() &&
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

