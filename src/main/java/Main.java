

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        List<Punkt> tablica = zczytaniePliku("uklad_poczatkowy.txt");
//        for (List l : tablica) {
//            for (Object k : l) {
//                System.out.print(k);
//            }
//        }
        Scanner odczyt = new Scanner(new File("uklad_poczatkowy.txt"));
        int wiersze, kolumny;
        wiersze = odczyt.nextInt();
        kolumny = odczyt.nextInt();

        List<Punkt> rozwiazanie = zczytaniePliku("rozwiazanie.txt");
        Punkt punktZero = getByWartosc(tablica, 0);
        DFS("LURD", tablica, rozwiazanie, punktZero, wiersze, kolumny);



    }


    public static void znajdzZero(Punkt punktZero, List<Punkt> tablica) {
        for (Punkt k : tablica) {
            if (k.getWartosc() == 0) {
                punktZero.setX(k.getX());
                punktZero.setY(k.getY());
            }
        }
    }


    public static List<Punkt> zczytaniePliku(String sciezkaDoPliku) throws FileNotFoundException {
        List<Punkt> uklad = new ArrayList<Punkt>();
        Scanner odczyt = new Scanner(new File(sciezkaDoPliku));
        int wiersze = odczyt.nextInt(); int kolumny = odczyt.nextInt();
        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                uklad.add(new Punkt(odczyt.nextInt(),w,k));
            }
        }
//
//        int i = 0;
//        while (odczyt.hasNextLine()) {
//
//            uklad.add(new Punkt());
//            for (int j = 0; j < kolumny; j++) {
//                uklad.get(i).add(odczyt.nextInt());
//            }
//            i++;
//
//        }

        return uklad;
    }

    public static void DFS(String porzadekPrzechodzenia, List<Punkt> ukladPoczatkowy, List<Punkt> ukladDocelowy, Punkt punktZero, int wiersze, int kolumny) {
        String porzadek;
        while (!porownajUklady(ukladPoczatkowy,ukladDocelowy)) {
            porzadek = porzadekPrzechodzenia;
            if (punktZero.getX() == 0) {
                porzadek = porzadek.replace("L", "");
            } else if (punktZero.getX() == kolumny - 1) {
                porzadek = porzadek.replace("R", "");
            }
            if (punktZero.getY() == 0) {
                porzadek = porzadek.replace("U", "");
            } else if (punktZero.getY() == wiersze - 1) {
                porzadek = porzadek.replace("D", "");
            }
            wykonajRuch(porzadek.charAt(0),ukladPoczatkowy);
        }
    }
    public static void wykonajRuch(char kierunek, List<Punkt> obecnyUklad)
    {
        Punkt zero = getByWartosc(obecnyUklad,0);
        switch (kierunek)
        {
            case 'L':
                getByXZ(obecnyUklad,zero.getX()-1,zero.getY()).setX(zero.getX());
                System.out.println("X: "+zero.getX()+" "+"Y: "+zero.getY());
                zero.setX(zero.getX()-1);

                System.out.println("X: "+zero.getX()+" "+"Y: "+zero.getY());
                break;
            case 'R':
                getByXZ(obecnyUklad,zero.getX()+1,zero.getY()).setX(zero.getX());
                zero.setX(zero.getX()+1);
                break;
            case 'U':
                getByXZ(obecnyUklad,zero.getX(),zero.getY()-1).setY(zero.getY());
                zero.setY(zero.getY()-1);
                break;
            case 'D':
                getByXZ(obecnyUklad,zero.getX(),zero.getY()+1).setY(zero.getY());
                zero.setY(zero.getY()+1);
                break;
        }
        System.out.println(obecnyUklad.get(0).getWartosc()+ " "+ obecnyUklad.get(1).getWartosc());
        System.out.println(obecnyUklad.get(2).getWartosc()+ " "+ obecnyUklad.get(3).getWartosc());

    }
    //TODO: zamiast main nowa klasa 'uklad' z lista punktów
    public static Punkt getByWartosc(List<Punkt> punkty, int wartosc)
    {
        for (Punkt p:punkty) {
            if(p.getWartosc()==wartosc)
                return p;
        }
        return  null;
    }
    public static Punkt getByXZ(List<Punkt> punkty, int x, int y)
    {
        for (Punkt p:punkty) {
            if(p.getX()==x && p.getY()==y)
                return p;
        }
        return  null;
    }

    public static boolean porownajUklady(List<Punkt> p1, List<Punkt> p2)
    {
        for (int i = 0; i < p1.size(); i++) {
            if(!porownajPunkty(p1.get(i),p2.get(i)))
                return false;
        }
        return true;
    }

    public static boolean porownajPunkty(Punkt p1, Punkt p2)
    {
        return p1.getWartosc() == p2.getWartosc() && p1.getX() == p2.getX() && p1.getY() == p2.getY();
    }
}
