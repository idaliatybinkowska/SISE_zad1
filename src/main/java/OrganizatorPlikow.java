import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrganizatorPlikow {


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

    public static void zapisDoPliku(String tekst, String sciezkaDoPliku) throws FileNotFoundException {
        FileWriter fileWriter = null;

        try {
            fileWriter = new FileWriter(sciezkaDoPliku);
            fileWriter.write(tekst);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileWriter != null) {
                try {
                    fileWriter.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
