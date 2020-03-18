import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Wezel korzen = new Wezel();
        korzen.setPoziomWDrzewie(0);
        korzen.setUklad(new Uklad(zczytaniePliku("uklad_poczatkowy.txt")));
        Uklad rozwiazanie = new Uklad(zczytaniePliku("rozwiazanie.txt"));


        if (sprawdzenieCzyMoznaRozwiazac(korzen)) {
            long start = System.nanoTime();
            DFS("LURD", korzen, rozwiazanie);
            long koniec = System.nanoTime();
            long czasWykonywania = (koniec - start) / 1000000;
            System.out.println("czas wykonywania: "+ czasWykonywania);
        } else
            System.out.println("Nie ma rozwiazania");


    }

    public static int[] wartosciUkladu(Wezel korzen) {
        int rozmiarTablicy = korzen.getUklad().getPunkty().size();
        int[] tablica = new int[rozmiarTablicy];
        for (int i = 0; i < rozmiarTablicy; i++) {
            tablica[i] = korzen.getUklad().getPunkty().get(i).getWartosc();
        }
        return tablica;
    }

    public static boolean sprawdzenieCzyMoznaRozwiazac(Wezel wezel) {
        int licznik = 0, wynik = 0;
        if(Uklad.liczbaKolumn%2==0)
        {
            licznik = wezel.getUklad().getPunktByWartosc(0).getX();
            if(Uklad.liczbaWierszy%2!=0)
                wynik = 1;
        }
        int [] wartosci = wartosciUkladu(wezel);
        System.out.println(Arrays.toString(wartosci));
//        int temp;
        for (int i = 0; i < wartosci.length-1; i++) {
            for (int j = i+1; j < wartosci.length; j++) {
                if (wartosci[i] > wartosci[j] && wartosci[i] > 0 && wartosci[j] > 0) {
//                    temp = wartosci[i];
//                    wartosci[i] = wartosci[j];
//                    wartosci[j] = temp;
                    licznik++;
                    System.out.println(Arrays.toString(wartosci));
                }
            }
        }
        System.out.println("licznik:"+licznik);
        return licznik % 2 == wynik;
    }

    public static List<Punkt> zczytaniePliku(String sciezkaDoPliku) throws FileNotFoundException {
        List<Punkt> uklad = new ArrayList<Punkt>();
        Scanner odczyt = new Scanner(new File(sciezkaDoPliku));
        Uklad.liczbaWierszy = odczyt.nextInt();
        Uklad.liczbaKolumn = odczyt.nextInt();

        for (int w = 0; w < Uklad.liczbaWierszy; w++) {
            for (int k = 0; k < Uklad.liczbaKolumn; k++) {
                uklad.add(new Punkt(odczyt.nextInt(), k, w));
            }
        }
        return uklad;
    }

    public static void BFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue<Wezel> kolejka = new LinkedList<Wezel>();
            char poprzedniRuch = ' ';
            do {
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia, poprzedniRuch, kolejka);
                obecnyWezel = kolejka.remove();
                przetworzone.add(obecnyWezel);
                poprzedniRuch = obecnyWezel.getKierunek();
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && kolejka.size() != 0);
        }
        System.out.println("glebokosc rekursji " + obecnyWezel.getPoziomWDrzewie());

        String sciezka = "";
        while (obecnyWezel != null) {
            sciezka = obecnyWezel.getKierunek() + sciezka;
            obecnyWezel = obecnyWezel.getRodzic();
        }


        System.out.println("sciezka: " + sciezka);
        System.out.println("dlugosc sciezki " + (sciezka.length() - 1));
        System.out.println("Stany przetworzone: " + przetworzone.size());
        System.out.println("Stany odwiedzone: " + sciezka.length());
    }


    public static void DFS(String porzadekPrzechodzenia, Wezel korzen, Uklad ukladDocelowy) {
        List<Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if (korzen.getUklad().compareTo(ukladDocelowy) != 0) {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            //Queue<Wezel> kolejka = new LinkedList<Wezel>();
            Stack<Wezel> stos = new Stack<Wezel>();
            char poprzedniRuch = ' ';
            do {
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia, poprzedniRuch, stos);
                obecnyWezel = stos.pop();
                //obecnyWezel = kolejka.remove();
                przetworzone.add(obecnyWezel);
                poprzedniRuch = obecnyWezel.getKierunek();
            } while (obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && stos.size() != 0 && obecnyWezel.getPoziomWDrzewie() < 20);
        }
        System.out.println("glebokosc rekursji " + obecnyWezel.getPoziomWDrzewie());

        String sciezka = "";
        while (obecnyWezel != null) {
            sciezka = obecnyWezel.getKierunek() + sciezka;
            obecnyWezel = obecnyWezel.getRodzic();
        }


        System.out.println("sciezka: " + sciezka);
        System.out.println("dlugosc sciezki " + (sciezka.length() - 1));
        System.out.println("Stany przetworzone: " + przetworzone.size());
        System.out.println("Stany odwiedzone: " + sciezka.length());
    }
}
