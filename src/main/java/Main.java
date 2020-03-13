import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        List<List<Integer>> tablica = zczytaniePliku("uklad_poczatkowy.txt");
//        for (List l : tablica) {
//            for (Object k : l) {
//                System.out.print(k);
//            }
//        }
        List<List<Integer>> rozwiazanie = zczytaniePliku("rozwiazanie.txt");
        BFS("", tablica, rozwiazanie);



    }

    public static List<List<Integer>> zczytaniePliku(String sciezkaDoPliku) throws FileNotFoundException {
        Scanner odczyt = new Scanner(new File(sciezkaDoPliku));

        List<List<Integer>> tablica = new ArrayList<List<Integer>>();
        int wiersze, kolumny;
        wiersze = odczyt.nextInt();
        kolumny = odczyt.nextInt();


        int i = 0;
        while (odczyt.hasNextLine()) {

            tablica.add(new ArrayList<Integer>());
            for (int j = 0; j < kolumny; j++) {
                tablica.get(i).add(odczyt.nextInt());
            }
            i++;

        }

        return tablica;
    }

    public static void BFS(String porzadekPrzechodzenia,  List<List<Integer>> ukladPoczatkowy ,  List<List<Integer>> ukladDocelowy) {


        while(!ukladPoczatkowy.equals(ukladDocelowy) ) {


        }

    }
}
