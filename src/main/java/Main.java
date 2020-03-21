import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {

        Uklad ukladPoczatkowy = new Uklad(OrganizatorPlikow.zczytaniePliku(args[2]));

        Uklad ukladWzorcowy = znajdzUkladWzorcowy();
        Wezel korzen = new Wezel(ukladPoczatkowy, 0);

        Wynik.przetworzone = new ArrayList<Wezel>();
        double czasWykonywania=0;
        boolean czyRozwiazane = true;
        long start = System.nanoTime(), koniec;
        try {
            //START ALGORYTMU
            if (args[0].equals("bfs"))
                Strategia.BFS(args[1], korzen, ukladWzorcowy);                            //BFS
            else if (args[0].equals("dfs"))
                Strategia.DFS(args[1], korzen, ukladWzorcowy);                            //DFS
            else if (args[0].equals("astr") && (args[1].equals("manh") || args[1].equals("hamm")))
                Strategia.najpierwNajlepszy(args[1], korzen, ukladWzorcowy);              //ASTR
            else System.out.println("Niepoprawne parametry wywolania");
            //KONIEC ALGORYTMU
            koniec = System.nanoTime();
            czasWykonywania = (koniec - start) / 1000000.0;
            //ZAPIS DO PLIKOW
            if (Wynik.obecnyWezel.getUklad().compareTo(ukladWzorcowy) != 0)
                czyRozwiazane = false;
        }
        catch (OutOfMemoryError e)
        {
            e.getStackTrace();
            czyRozwiazane=false;
            koniec = System.nanoTime();
            czasWykonywania = (koniec - start) / 1000000.0;
        }
        finally {
            OrganizatorPlikow.zapisDoPliku(stworzRozwiazanie(czyRozwiazane, Wynik.obecnyWezel), args[3]);
            OrganizatorPlikow.zapisDoPliku(
                    stworzInformacjeDodatkowe(czyRozwiazane,
                            Wynik.obecnyWezel,
                            Wynik.przetworzone) + String.format("\n%.3f",
                            czasWykonywania),
                    args[4]);
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

    public static String stworzInformacjeDodatkowe(boolean czyRozwiazane, Wezel wezel,List<Wezel> przetworzone)
    {
        StringBuilder stringBuilder = new StringBuilder();
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
        {
            stringBuilder.append(sciezka.length() - 1);
            stringBuilder.append("\n").append(sciezka.substring(1));
        }
        else stringBuilder.append("-1");
        return stringBuilder.toString();
    }
}
