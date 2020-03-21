import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Stack;

public class Strategia {

    public static void BFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        Wynik.przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue<Wezel> kolejka = new LinkedList<Wezel>();
            do {
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia,Wynik.przetworzone);
                kolejka.addAll(obecnyWezel.getDzieci());
                obecnyWezel = kolejka.remove();
                Wynik.obecnyWezel = obecnyWezel;
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && kolejka.size() != 0);
        }
    }

    public static void DFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        String temp="";
        for (int i = porzadekPrzechodzenia.length()-1; i >= 0; i--) {
            temp=temp.concat(String.valueOf(porzadekPrzechodzenia.charAt(i)));
        }
        Wynik.przetworzone.add(korzen);
        porzadekPrzechodzenia = temp;
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Stack<Wezel> stos = new Stack<Wezel>();
            do {
                if(obecnyWezel.getPoziomWDrzewie() < 25) {
                    obecnyWezel.stworzDzieci(porzadekPrzechodzenia,Wynik.przetworzone);
                    stos.addAll(obecnyWezel.getDzieci());
                }
                obecnyWezel = stos.pop();
                Wynik.obecnyWezel = obecnyWezel;
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && stos.size() != 0);
        }
    }

    public static void najpierwNajlepszy(String metryka, Wezel korzen, Uklad ukladDocelowy) {
        Wynik.przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue<Wezel> kolejka = new PriorityQueue<Wezel>(new WezelComparator());
            do {
                obecnyWezel.stworzDzieci("LURD",Wynik.przetworzone);
                if (metryka.equals("manh"))
                    obecnyWezel.astar(ukladDocelowy, "manh");
//                    obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoManhattan(ukladDocelowy,obecnyWezel.getDzieci(), Wynik.przetworzone);
                else if (metryka.equals("hamm"))
                    //obecnyWezel = obecnyWezel.znajdzNajlepszeDzieckoHamming(ukladDocelowy,obecnyWezel.getDzieci(), Wynik.przetworzone);
                    obecnyWezel.astar(ukladDocelowy, "hamm");
                kolejka.addAll(obecnyWezel.getDzieci());
                obecnyWezel = kolejka.remove();
                Wynik.obecnyWezel = obecnyWezel;
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0);
        }
    }
}
