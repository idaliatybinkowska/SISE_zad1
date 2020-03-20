import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Uklad ukladPoczatkowy = new Uklad(OrganizatorPlikow.zczytaniePliku(args[2]));

//        if (!ukladPoczatkowy.sprawdzCzyMoznaRozwiazac())
//        {
//            OrganizatorPlikow.zapisDoPliku("-1",args[3]);
//            OrganizatorPlikow.zapisDoPliku("-1",args[4]);
//        }
//        else
//        {
            Uklad ukladWzorcowy = znajdzUkladWzorcowy();
            Wezel korzen = new Wezel(ukladPoczatkowy, 0);
//                List<Wezel> stanyPrzetworzone = new ArrayList<Wezel>();
            Wynik wynik = new Wynik();
            double czasWykonywania=0;
            boolean czyRozwiazane = true;
            long start = System.nanoTime(), koniec;
            try{
                //START ALGORYTMU
                if(args[0].equals("bfs"))
                    wynik = BFS(args[1], korzen, ukladWzorcowy);                            //BFS
                else if(args[0].equals("dfs"))
                    wynik = DFS(args[1], korzen, ukladWzorcowy);                            //DFS
                else if(args[0].equals("astr") && (args[1].equals("manh") || args[1].equals("hamm")))
                    wynik = najpierwNajlepszy(args[1], korzen, ukladWzorcowy);              //ASTR
                else System.out.println("Niepoprawne parametry wywolania");
                //KONIEC ALGORYTMU
                koniec = System.nanoTime();
                czasWykonywania = (koniec - start) / 1000000.0;
                //ZAPIS DO PLIKOW
                if(wynik.getObecnyWezel().getUklad().compareTo(ukladWzorcowy)!=0)
                    czyRozwiazane=false;
            }
            catch (Exception e)
            {
                e.getStackTrace();
                czyRozwiazane=false;
                koniec = System.nanoTime();
                czasWykonywania = (koniec - start) / 1000000.0;
                OrganizatorPlikow.zapisDoPliku("-1",args[3]);
                OrganizatorPlikow.zapisDoPliku("-1",args[4]);
            }
            finally {
                OrganizatorPlikow.zapisDoPliku(stworzRozwiazanie(czyRozwiazane, wynik.getObecnyWezel()), args[3]);
                OrganizatorPlikow.zapisDoPliku(
                        stworzInformacjeDodatkowe(czyRozwiazane,
                                wynik.getObecnyWezel(),
                                wynik.getPrzetworzone()) + String.format("\n%.3f",
                                czasWykonywania),
                        args[4]);
            }
//        }
    }

    public static Uklad znajdzUkladWzorcowy()
    {
        List<Punkt> punkty = new ArrayList<Punkt>();
        int wartosc = 1;
        for (int i = 0; i < Uklad.liczbaWierszy; i++) {
            for (int j = 0; j < Uklad.liczbaKolumn; j++) {
                if(wartosc == Uklad.liczbaKolumn*Uklad.liczbaWierszy)
                    wartosc = 0;
                punkty.add(new Punkt(wartosc, j , i));
                wartosc++;
            }
        }
        return new Uklad(punkty);
    }

    public static Wynik BFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        Wynik wynik = new Wynik();
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue<Wezel> kolejka = new LinkedList<Wezel>();
            do {
//                obecnyWezel.stworzDzieci(porzadekPrzechodzenia, kolejka);
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia,przetworzone);
                kolejka.addAll(obecnyWezel.getDzieci());
                obecnyWezel = kolejka.remove();
//                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && kolejka.size() != 0);
        }
        wynik.setObecnyWezel(obecnyWezel);
        wynik.setPrzetworzone(przetworzone);
        return wynik;
    }


    public static Wynik DFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        Wynik wynik = new Wynik();
        String temp="";
        for (int i = porzadekPrzechodzenia.length()-1; i >= 0; i--) {
            temp=temp.concat(String.valueOf(porzadekPrzechodzenia.charAt(i)));
        }
        porzadekPrzechodzenia = temp;
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            //Queue<Wezel> kolejka = new LinkedList<Wezel>();
            Stack<Wezel> stos = new Stack<Wezel>();
            do {
                if(obecnyWezel.getPoziomWDrzewie() < 25) {
//                    obecnyWezel.stworzDzieci(porzadekPrzechodzenia, stos);
                    obecnyWezel.stworzDzieci(porzadekPrzechodzenia,przetworzone);
                    stos.addAll(obecnyWezel.getDzieci());
                }
                obecnyWezel = stos.pop();
                //obecnyWezel = kolejka.remove();
//                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && stos.size() != 0);
        }
        wynik.setObecnyWezel(obecnyWezel);
        wynik.setPrzetworzone(przetworzone);
        return wynik;
    }

    public static Wynik najpierwNajlepszy(String metryka, Wezel korzen, Uklad ukladDocelowy) {
        Wynik wynik = new Wynik();
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            do {
                obecnyWezel.stworzDzieci("LURD",przetworzone);
                if (metryka.equals("manh"))
                    obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoManhattan(ukladDocelowy,obecnyWezel.getDzieci(), przetworzone);
                else if (metryka.equals("hamm"))
                    obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoHamming(ukladDocelowy,obecnyWezel.getDzieci(), przetworzone);
//                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0);
        }
        wynik.setObecnyWezel(obecnyWezel);
        wynik.setPrzetworzone(przetworzone);
        return wynik;
    }

    public static String stworzInformacjeDodatkowe(boolean czyRozwiazane, Wezel wezel,List<Wezel> przetworzone)
    {
        StringBuilder stringBuilder = new StringBuilder();
        if (przetworzone==null)
            System.out.println("cos nie tak");
        String temp ="\n"+przetworzone.get(przetworzone.size()-1).getPoziomWDrzewie();
        if(czyRozwiazane)
        {
            temp ="\n"+wezel.getPoziomWDrzewie();
            StringBuilder sciezka = new StringBuilder();
            while (wezel != null) {
                sciezka.insert(0, wezel.getKierunek());
                wezel = wezel.getRodzic();
            }
            stringBuilder.append(sciezka.length() - 1);
            stringBuilder.append("\n").append(sciezka.length());
        } else
        {
            stringBuilder.append("-1");
            stringBuilder.append("\n0");
        }
        stringBuilder.append("\n").append(przetworzone.size());
        stringBuilder.append(temp);

        return stringBuilder.toString();
    }

    public static String stworzRozwiazanie(boolean czyRozwiazane, Wezel wezel)
    {
        StringBuilder stringBuilder = new StringBuilder();

        StringBuilder sciezka = new StringBuilder();
        while (wezel != null) {
            sciezka.insert(0, wezel.getKierunek());
            wezel = wezel.getRodzic();
        }

        if(czyRozwiazane)
            stringBuilder.append(sciezka.length() - 1);
        else
        {
            stringBuilder.append("-1");
            stringBuilder.append("\n").append(sciezka.substring(1));
        }

        return stringBuilder.toString();
    }

}
