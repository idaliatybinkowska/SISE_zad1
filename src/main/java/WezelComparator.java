import java.util.Comparator;

public class WezelComparator implements Comparator<Wezel> {
    public int compare(Wezel o1, Wezel o2) {
        if(o1.getWartoscFunkcji() > o2.getWartoscFunkcji()) {
            return 1;
        }
        else if(o1.getWartoscFunkcji() < o2.getWartoscFunkcji()) {
            return -1;
        } else
            return 0;
    }
}
