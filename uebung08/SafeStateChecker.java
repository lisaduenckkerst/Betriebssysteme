import java.util.List;

public interface SafeStateChecker {
    boolean isSafe(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen);
}
