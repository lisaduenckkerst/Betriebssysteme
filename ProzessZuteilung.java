public class ProzessZuteilung {
    String prozessName;
    int anzahlAktuellerRessourcen;
    int maximaleAnzahlRessourcen;


    public ProzessZuteilung(String prozessName, int anzahlAktuellerRessourcen,int maximaleAnzahlRessourcen) {
        this.prozessName = prozessName;
        this.anzahlAktuellerRessourcen = anzahlAktuellerRessourcen;
        this.maximaleAnzahlRessourcen = maximaleAnzahlRessourcen;
    }
}