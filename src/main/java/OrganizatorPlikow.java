import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class OrganizatorPlikow {

    public List<Punkt> zczytaniePliku(String sciezkaDoPliku) throws FileNotFoundException {
        List<Punkt> uklad = new ArrayList<Punkt>();
        Scanner odczyt = new Scanner(new File(sciezkaDoPliku));
        int wiersze = odczyt.nextInt();
        int kolumny = odczyt.nextInt();
        for (int w = 0; w < wiersze; w++) {
            for (int k = 0; k < kolumny; k++) {
                uklad.add(new Punkt(odczyt.nextInt(), k, w));
            }
        }
        return uklad;
    }
}
