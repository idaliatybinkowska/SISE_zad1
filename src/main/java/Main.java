import java.util.ArrayList;

public class Main {
    public static void main(String[] args) throws Exception {

        Uklad ukladPoczatkowy = new Uklad(OrganizatorPlikow.zczytaniePliku(args[2]));

        Uklad ukladWzorcowy = Uklad.znajdzUkladWzorcowy();
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
            OrganizatorPlikow.zapisDoPliku(Wynik.stworzRozwiazanie(czyRozwiazane, Wynik.obecnyWezel), args[3]);
            OrganizatorPlikow.zapisDoPliku(
                    Wynik.stworzInformacjeDodatkowe(czyRozwiazane,
                            Wynik.obecnyWezel,
                            Wynik.przetworzone) + String.format("\n%.3f",
                            czasWykonywania),
                    args[4]);
        }
    }
}
