import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Uklad ukladPoczatkowy = new Uklad(OrganizatorPlikow.zczytaniePliku(args[2]));

        if (!ukladPoczatkowy.sprawdzCzyMoznaRozwiazac())
        {
            OrganizatorPlikow.zapisDoPliku("-1",args[3]);
            OrganizatorPlikow.zapisDoPliku("-1",args[4]);
        }
        else
        {
            Uklad ukladWzorcowy = znajdzUkladWzorcowy();
            Wezel korzen = new Wezel(ukladPoczatkowy, 0);
            List<Wezel> stanyPrzetworzone = new ArrayList<Wezel>();
            //START ALGORYTMU
            long start = System.nanoTime();
            if(args[0].equals("bfs"))
                stanyPrzetworzone = BFS(args[1], korzen, ukladWzorcowy);                            //BFS
            else if(args[0].equals("dfs"))
                stanyPrzetworzone = DFS(args[1], korzen, ukladWzorcowy);                            //DFS
            else if(args[0].equals("astr") && (args[1].equals("manh") || args[1].equals("hamm")))
                stanyPrzetworzone = najpierwNajlepszy(args[1], korzen, ukladWzorcowy);              //ASTR
            else System.out.println("Niepoprawne parametry wywolania");
            //KONIEC ALGORYTMU
            long koniec = System.nanoTime();
            double czasWykonywania = (koniec - start) / 1000000.0;
            OrganizatorPlikow.zapisDoPliku(
                    stworzInformacjeDodatkowe(
                            stanyPrzetworzone.get(stanyPrzetworzone.size() - 1),
                            stanyPrzetworzone) + String.format("\nczas wykonywania: %.3f",
                            czasWykonywania),
                    args[3]);
            OrganizatorPlikow.zapisDoPliku(stworzRozwiazanie(stanyPrzetworzone.get(stanyPrzetworzone.size() - 1)), args[4]);
        }
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

    public static List<Wezel> BFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue<Wezel> kolejka = new LinkedList<Wezel>();
            do {
//                obecnyWezel.stworzDzieci(porzadekPrzechodzenia, kolejka);
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia);
                kolejka.addAll(obecnyWezel.getDzieci());
                obecnyWezel = kolejka.remove();
                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && kolejka.size() != 0);
        }
        return przetworzone;
    }


    public static List<Wezel> DFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        String temp="";
        for (int i = porzadekPrzechodzenia.length()-1; i >= 0; i--) {
            temp=temp.concat(String.valueOf(porzadekPrzechodzenia.charAt(i)));
        }
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
                    obecnyWezel.stworzDzieci(porzadekPrzechodzenia);
                    stos.addAll(obecnyWezel.getDzieci());
                }
                obecnyWezel = stos.pop();
                //obecnyWezel = kolejka.remove();
                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && stos.size() != 0);
        }
        return przetworzone;
    }

    public static List<Wezel> najpierwNajlepszy(String metryka, Wezel korzen, Uklad ukladDocelowy) {
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            do {
                obecnyWezel.stworzDzieci("LURD");
                if (metryka.equals("manh"))
                    obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoManhattan(ukladDocelowy,obecnyWezel.getDzieci());
                else if (metryka.equals("hamm"))
                    obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoHamming(ukladDocelowy,obecnyWezel.getDzieci());
                przetworzone.add(obecnyWezel);
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0);
        }
        return przetworzone;
    }

    public static String stworzInformacjeDodatkowe(Wezel wezel,List<Wezel> przetworzone)
    {
        StringBuilder stringBuilder = new StringBuilder();
        String temp ="\nglebokosc rekursji: "+wezel.getPoziomWDrzewie();

        StringBuilder sciezka = new StringBuilder();
        while (wezel != null) {
            sciezka.insert(0, wezel.getKierunek());
            wezel = wezel.getRodzic();
        }
        stringBuilder.append("dlugosc sciezki: ").append(sciezka.length() - 1);
        stringBuilder.append("\nstany odwiedzone: ").append(sciezka.length());
        stringBuilder.append("\nstany przetworzone: ").append(przetworzone.size());
        stringBuilder.append(temp);
        //stringBuilder.append("\nsciezka: ").append(sciezka);

        return stringBuilder.toString();
    }

    public static String stworzRozwiazanie(Wezel wezel)
    {
        StringBuilder stringBuilder = new StringBuilder();

        StringBuilder sciezka = new StringBuilder();
        while (wezel != null) {
            sciezka.insert(0, wezel.getKierunek());
            wezel = wezel.getRodzic();
        }
        stringBuilder.append("dlugosc sciezki: ").append(sciezka.length() - 1);
        stringBuilder.append("\nsciezka: ").append(sciezka);

        return stringBuilder.toString();
    }

}
