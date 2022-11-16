import java.util.Objects;
public class Knoten {
    protected enum Knotentypen {PROZESS , RESSOURCE};
    protected Knotentypen knotentyp;
    protected String name;

    public Knoten(String name, Knotentypen typ) {
        this.name = name;
        this.knotentyp = typ;
    }

    public String getName() {
        return name;
    }

    public Knotentypen getTyp() {
        return knotentyp;
    }

    public String toString() {
        return String.format("%s %s",knotentyp,name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Knoten knoten = (Knoten) o;
        return knotentyp == knoten.knotentyp && name.equals(knoten.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(knotentyp, name);
    }
}
