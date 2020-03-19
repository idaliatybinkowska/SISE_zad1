import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static java.lang.System.exit;

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
            dziecko.setPoziomWDrzewie(this.poziomWDrzewie + 1);
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

//    public Wezel znajdzNajlepszeDzieckoManhattan(Uklad ukladDocelowy) {
//        Wezel najlepszeDziecko = dzieci.get(0);
//        int najmniejszaWartosc = najlepszeDziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, najlepszeDziecko);
//        for (Wezel dziecko : dzieci) {
//            int obecnaWartosc = dziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, dziecko);
//            if (obecnaWartosc < najmniejszaWartosc) {
//                najmniejszaWartosc = obecnaWartosc;
//                najlepszeDziecko = dziecko;
//            }
//        }
//        return najlepszeDziecko;
//    }


    public Wezel znajdzNajlepszeDzieckoManhattan(Uklad ukladDocelowy,List<Wezel> dzieciaki) {
        Wezel najlepszeDziecko = dzieciaki.get(0);
        int najwiekszaWartosc = najlepszeDziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, najlepszeDziecko);
        //System.out.println(najlepszeDziecko.poziomWDrzewie);
        List<Integer> wartosciFunkcji = new ArrayList<Integer>();

        for (Wezel dziecko : dzieciaki) {

            int obecnaWartosc = dziecko.poziomWDrzewie + heurystykaManhattan(ukladDocelowy, dziecko);
            wartosciFunkcji.add(obecnaWartosc);
            if (obecnaWartosc < najwiekszaWartosc) {
                najwiekszaWartosc = obecnaWartosc;
                najlepszeDziecko = dziecko;
            }

        }

//        for (int i = 0; i < wartosciFunkcji.size() ; i++) {
//            for (int j = i+1; j <wartosciFunkcji.size()-1 ; j++) {
//                if(wartosciFunkcji.get(i)== wartosciFunkcji.get(j) && ) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
        List<Wezel> najlepszeDzieci = new ArrayList<Wezel>();
        for (int i = 0; i < wartosciFunkcji.size() ; i++) {
            if(wartosciFunkcji.get(i) == najwiekszaWartosc) {
                najlepszeDzieci.add(dzieciaki.get(i));
            }
        }
        if(najlepszeDzieci.size()==1) {
            return najlepszeDzieci.get(0);
        } else {
            List<Wezel> wnuczki = new ArrayList<Wezel>();

            for (int i = 0; i <najlepszeDzieci.size() ; i++) {
                najlepszeDzieci.get(i).stworzDzieci("LURD", najlepszeDzieci.get(i).getKierunek(), new ArrayList<Wezel>());
                wnuczki.addAll(najlepszeDzieci.get(i).getDzieci());
            }
            return znajdzNajlepszeDzieckoManhattan(ukladDocelowy,wnuczki);
        }

    }

    public Wezel znajdzNajlepszeDzieckoHamming(Uklad ukladDocelowy,List<Wezel> dzieciaki) {
        Wezel najlepszeDziecko = dzieciaki.get(0);
        int najwiekszaWartosc = najlepszeDziecko.poziomWDrzewie + heurystykaHamming(ukladDocelowy, najlepszeDziecko);
        //System.out.println(najlepszeDziecko.poziomWDrzewie);
        List<Integer> wartosciFunkcji = new ArrayList<Integer>();

        for (Wezel dziecko : dzieciaki) {

            int obecnaWartosc = dziecko.poziomWDrzewie + heurystykaHamming(ukladDocelowy, dziecko);
            wartosciFunkcji.add(obecnaWartosc);
            if (obecnaWartosc > najwiekszaWartosc) {
                najwiekszaWartosc = obecnaWartosc;
                najlepszeDziecko = dziecko;
            }

        }

//        for (int i = 0; i < wartosciFunkcji.size() ; i++) {
//            for (int j = i+1; j <wartosciFunkcji.size()-1 ; j++) {
//                if(wartosciFunkcji.get(i)== wartosciFunkcji.get(j) && ) {
//                    flag = true;
//                    break;
//                }
//            }
//        }
        List<Wezel> najlepszeDzieci = new ArrayList<Wezel>();
        for (int i = 0; i < wartosciFunkcji.size() ; i++) {
            if(wartosciFunkcji.get(i) == najwiekszaWartosc) {
                najlepszeDzieci.add(dzieciaki.get(i));
            }
        }
        if(najlepszeDzieci.size()==1) {
            return najlepszeDzieci.get(0);
        } else {
            List<Wezel> wnuczki = new ArrayList<Wezel>();

            for (int i = 0; i <najlepszeDzieci.size() ; i++) {
                najlepszeDzieci.get(i).stworzDzieci("LURD", najlepszeDzieci.get(i).getKierunek(), new ArrayList<Wezel>());
                wnuczki.addAll(najlepszeDzieci.get(i).getDzieci());
            }
            return znajdzNajlepszeDzieckoHamming(ukladDocelowy,wnuczki);
        }

    }


    public int heurystykaManhattan(Uklad ukladDocelowy, Wezel dziecko) {
        int suma = 0;
        int rozmiarUkladu = dziecko.getUklad().getPunkty().size();
        List<Punkt> punktyRozwiazanie = ukladDocelowy.getPunkty();
        List<Punkt> punktyDziecko = dziecko.getUklad().getPunkty();
        for (int i = 0; i < rozmiarUkladu; i++) {
            for (int j = 0; j < rozmiarUkladu; j++) {
                if (punktyDziecko.get(i).getWartosc() == punktyRozwiazanie.get(j).getWartosc()) {
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

