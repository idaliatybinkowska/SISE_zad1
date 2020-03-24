import java.util.List;

public class Wynik {
    public static List<Wezel> przetworzone;
    public static Wezel obecnyWezel;

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
            stringBuilder.append("\n").append(Strategia.liczbaStanowOdwiedzonych);
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
