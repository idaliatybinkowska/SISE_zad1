import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        Wezel korzen = new Wezel();
        korzen.setPoziomWDrzewie(0);
        korzen.setUklad(new Uklad(zczytaniePliku("uklad_poczatkowy.txt")));
        Uklad rozwiazanie = new Uklad(zczytaniePliku("rozwiazanie.txt"));
//        korzen.stworzDzieci("LURD");
        //List<Punkt> tablica = zczytaniePliku("uklad_poczatkowy.txt");
//        for (List l : tablica) {
//            for (Object k : l) {
//                System.out.print(k);
//            }
//        }
        Scanner odczyt = new Scanner(new File("uklad_poczatkowy.txt"));
        int wiersze, kolumny;
        wiersze = odczyt.nextInt();
        kolumny = odczyt.nextInt();

        //List<Punkt> rozwiazanie = zczytaniePliku("rozwiazanie.txt");
//        Punkt punktZero = getByWartosc(tablica, 0);
        BFS("LURD", korzen, rozwiazanie);


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
        List <Wezel> przetworzone = new ArrayList<Wezel>();
        przetworzone.add(korzen);
        Wezel obecnyWezel = new Wezel();
        if(korzen.getUklad().compareTo(ukladDocelowy)!=0)
        {
            obecnyWezel.setUklad(new Uklad(korzen.getUklad().getPunkty()));
            Queue <Wezel> kolejka = new LinkedList<Wezel>();
            char poprzedniRuch = ' ' ;
            do{
                obecnyWezel.stworzDzieci(porzadekPrzechodzenia, poprzedniRuch, kolejka);
                obecnyWezel = kolejka.remove();
                przetworzone.add(obecnyWezel);
                poprzedniRuch = obecnyWezel.getKierunek();
            }while(obecnyWezel.getUklad().compareTo(ukladDocelowy) != 0 && kolejka.size()!=0);
        }
        System.out.println("glebokosc rekursji "+obecnyWezel.getPoziomWDrzewie());

        String sciezka ="";
        while(obecnyWezel != null) {
            sciezka = obecnyWezel.getKierunek() + sciezka;
            obecnyWezel = obecnyWezel.getRodzic();
        }


        System.out.println("sciezka: "+sciezka);
        System.out.println("dlugosc sciezki "+(sciezka.length()-1));
        System.out.println("Stany przetworzone: "+przetworzone.size());
        System.out.println("Stany odwiedzone: "+sciezka.length());




        System.out.println(przetworzone.toString());
        System.out.println("BINGO");
    }
}
