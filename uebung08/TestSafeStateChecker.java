import java.util.LinkedList;
import java.util.List;
import java.util.ArrayList;

/**
 * BS Übung 8
 * Testklasse, die das Interface SafeStateChecker implementiert
 */
public class TestSafeStateChecker implements SafeStateChecker {

    public static void main(String[] args) {

        TestSafeStateChecker t = new TestSafeStateChecker();

        ProzessZuteilung a = new ProzessZuteilung("A",1, 6);
        ProzessZuteilung b = new ProzessZuteilung("B", 1, 5);
        ProzessZuteilung c = new ProzessZuteilung("C", 2, 4);
        ProzessZuteilung d = new ProzessZuteilung("D", 4, 7);
        int freieRessourcen = 2;

        List<ProzessZuteilung> zuteilungsSituation = new LinkedList<>();

        zuteilungsSituation.add(a);
        zuteilungsSituation.add(b);
        zuteilungsSituation.add(c);
        zuteilungsSituation.add(d);

        t.printProcessTable(zuteilungsSituation, freieRessourcen);
        System.out.println("State is safe: " + t.isSafe(zuteilungsSituation, freieRessourcen));
        // System.out.println("State is safe: " + t.isSafeRec(zuteilungsSituation, freieRessourcen));

    } // Ende main

    /**
     * Wählt den nächsten gültigen Prozess, der in einen
     * Endzustand gebracht werden kann
     * @param zuteilungsSituation Liste mit der aktuellen Zuteilung aller Prozesse
     * @param anzahlFreieRessourcen Anzahl der aktuell freien Ressourcen
     */
    public ProzessZuteilung nextProcess(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen) {

        for(ProzessZuteilung p : zuteilungsSituation) {

            // wenn der Prozess noch nicht beendet ist
            if (p.anzahlAktuellerRessourcen != 0 && p.maximaleAnzahlRessourcen != 0) {

                // wenn die Anzahl aktuellerRessourcen addiert mit der Anzahl
                // freier Ressourcen größer oder gleich der maximalen Anzahl Ressourcen ist,
                // kann der Prozess beendet werden
                if (p.anzahlAktuellerRessourcen + anzahlFreieRessourcen >= p.maximaleAnzahlRessourcen) {
                    return p; // dann wähle diesen Prozess

                }
            }
        }
        return null; // Ansonsten gibt es keinen laufenden Prozess mehr
    }

    /**
     * Prüft für die Liste der Prozesszuteilung,
     * ob alle Prozesse beendet wurden.
     * @param zuteilungsSituation Liste mit der aktuellen Zuteilung aller Prozesse
     * @return true, wenn alle Prozesse beendet werden konnten, sonst false
     */
    public boolean allProcessesAreTerminated(List<ProzessZuteilung> zuteilungsSituation) {
        for (ProzessZuteilung p : zuteilungsSituation) {
            if (p.anzahlAktuellerRessourcen != 0 && p.maximaleAnzahlRessourcen != 0)
                return false; // es gibt mind. 1 Prozess, der nicht beendet ist
        }
        return true; // alle Prozesse konnten beendet werden
    }

    /**
     * Prüft die Liste der Prozesszuteilung auf einen Safe-State
     * @param zuteilungsSituation Liste mit der aktuellen Zuteilung aller Prozesse
     * @param anzahlFreieRessourcen Anzahl der aktuell freien Ressourcen
     * @return true, wenn es eine Folge von Schritten gibt,
     * die alle Prozesse in einen Endzustand bringt (safe state), sonst false
     */
    @Override
    public boolean isSafe(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen) {

        while (true) {

            // wähle den aktuellen Prozess
            ProzessZuteilung p = nextProcess(zuteilungsSituation, anzahlFreieRessourcen);

            // wenn es noch gültige laufende Prozesse gibt
            if (p != null) {

                // neue Anzahl freier Ressourcen berechnen
                anzahlFreieRessourcen = p.anzahlAktuellerRessourcen + anzahlFreieRessourcen;

                // Prozess aktualisieren, da fertig
                p.anzahlAktuellerRessourcen = 0;
                p.maximaleAnzahlRessourcen = 0;

                printProcessTable(zuteilungsSituation, anzahlFreieRessourcen); // gebe Tabelle aus
            }

            // wenn es keine gültigen laufenden Prozesse mehr gibt
            else if (allProcessesAreTerminated(zuteilungsSituation))
                return true;
            else
                return false;

        }
    }

    /**
     * Prüft die Liste der Prozesszuteilung auf einen Safe-State mittels Rekursion
     * @param zuteilungsSituation Liste mit der aktuellen Zuteilung aller Prozesse
     * @param anzahlFreieRessourcen Anzahl der aktuell freien Ressourcen
     * @return true, wenn es eine Folge von Schritten gibt,
     * die alle Prozesse in einen Endzustand bringt (safe state), sonst false
     */
    public boolean isSafeRec(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen){
        if (zuteilungsSituation.isEmpty())
            return true;

        for (ProzessZuteilung p : zuteilungsSituation) {

            // wähle den nächsten Prozess, der beendet werden kann
            if (p.anzahlAktuellerRessourcen + anzahlFreieRessourcen >= p.maximaleAnzahlRessourcen) {

                // neue Anzahl freier Ressourcen berechnen
                int neueAnzahlFreieRessourcen = p.anzahlAktuellerRessourcen + anzahlFreieRessourcen;

                // Prozess entfernen da fertig
                zuteilungsSituation.remove(p);

                printProcessTable(zuteilungsSituation, anzahlFreieRessourcen); // gebe Tabelle aus

                // alle Prozesse konnten bearbeitet werden
                if (zuteilungsSituation.isEmpty()) {
                    return true;
                } else {
                    // es gibt noch unbeendete Prozesse
                    return isSafe(zuteilungsSituation, neueAnzahlFreieRessourcen);
                }

            }
        }
        return false;
    }


    /**
     * Gibt die Liste der Prozesse als Tabelle aus
     * @param zuteilungsSituation Liste mit der aktuellen Zuteilung aller Prozesse
     * @param anzahlFreieRessourcen Anzahl der aktuell freien Ressourcen
     */
    public void printProcessTable(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen) {
        System.out.println("P" + "\tHas" + "\tNeed");
        for (ProzessZuteilung p : zuteilungsSituation)
            System.out.println(p.prozessName + "\t" + p.anzahlAktuellerRessourcen + "\t" + p.maximaleAnzahlRessourcen);
        System.out.println("Frei: " + anzahlFreieRessourcen + "\n");
    }
}
