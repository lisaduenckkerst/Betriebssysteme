# Übung 08 - Betriebssysteme - WS 21/22

## Deadlock Modellierung
Modellieren Sie folgende Ressourcen-Nutzungs-Situation nach Holt für die Prozesse A,B,C,D,E und die Ressourcen R,S,T,U:
- Prozess A hält R und fordert U
- Prozess B hält S und fordert R, T und U
- Prozess E hält T und fordert S
- Prozess D fordert T
- Prozess C fordert T

Existiert ein Deadlock? Begründen Sie warum die Antwort ja oder nein heißt.

## Deadlock Detection Algorithmus
Implementieren Sie den in der Vorlesung vorgestellten Deadlock Detection Algorithmus in der Programmiersprache Java.
Als Input erhält der Algorithmus einen gerichteten Graphen, welcher den Zustand beschreibt. Prozesse und Ressourcen bilden die Knoten des Graphen, Halte- und Fordert-Beziehungen sind durch die Knotentypen und die Richtung der Kante abgebildet.
Für die Implementierung eines solchen Graphen können Sie auf folgendem Code-Gerüst aufbauen. Sie verwendet die
Graph-Bibliothek [jGraphT](https://jgrapht.org) und die anschließend dargestellte Knoten-Implementierung.
Der folgende Java-Code bildet den Zustand der vorhergehenden Aufgabe ab:
```java
package de.hsd.medien.bs;

import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class DeadlockDetector {

    DefaultDirectedGraph zuteilungsSituation;

    public static void main(String[] args) {
        DefaultDirectedGraph beispielGraph = erstelleBeispielGraph();
        DeadlockDetector dd = new DeadlockDetector(beispielGraph);
        System.out.println(String.format("Analysiere den folgenden Zustand ... \r\n%s\r\n",dd.getZuteilungsSituation().toString()));
        if (dd.liegtDeadlockVor()) {
            System.out.println("Der aktuelle Zustand enthält ein Deadlock!");
        } else {
            System.out.println("Der aktuelle Zustand ist Deadlock-frei!");
        }

    }

    public DeadlockDetector(DefaultDirectedGraph g) {
        this.zuteilungsSituation = g;
    }

    boolean liegtDeadlockVor() {
        //TODO: hier fehlt leider noch eine Implementierung
        return false;
    }

    public DefaultDirectedGraph getZuteilungsSituation() {
        return this.zuteilungsSituation;
    }

    static DefaultDirectedGraph erstelleBeispielGraph() {
        DefaultDirectedGraph<Knoten, DefaultEdge> zustand = new DefaultDirectedGraph(DefaultEdge.class);
        Knoten a = new Knoten("A", Knoten.Knotentypen.PROZESS);
        Knoten b = new Knoten("B", Knoten.Knotentypen.PROZESS);
        Knoten c = new Knoten("C", Knoten.Knotentypen.PROZESS);
        Knoten d = new Knoten("D", Knoten.Knotentypen.PROZESS);
        Knoten e = new Knoten("E", Knoten.Knotentypen.PROZESS);

        Knoten r = new Knoten("R", Knoten.Knotentypen.RESSOURCE);
        Knoten s = new Knoten("S", Knoten.Knotentypen.RESSOURCE);
        Knoten t = new Knoten("T", Knoten.Knotentypen.RESSOURCE);
        Knoten u = new Knoten("U", Knoten.Knotentypen.RESSOURCE);

        zustand.addVertex(a);
        zustand.addVertex(b);
        zustand.addVertex(c);
        zustand.addVertex(d);
        zustand.addVertex(e);
        zustand.addVertex(r);
        zustand.addVertex(s);
        zustand.addVertex(t);
        zustand.addVertex(u);


        // Prozess A hält R und fordert U
        zustand.addEdge(r,a);
        zustand.addEdge(a,u);

        // Prozess B hält S und fordert R, T und U
        zustand.addEdge(s,b);
        zustand.addEdge(b,r);
        zustand.addEdge(b,t);
        zustand.addEdge(b,u);

        //Prozess E hält T und fordert S
        zustand.addEdge(t,e);
        zustand.addEdge(e,s);

        //Prozess D fordert T
        zustand.addEdge(d,t);

        // Prozess C hält T
        zustand.addEdge(t,c);

        return zustand;
    }
}
```
Knoten-Implementierung:
```java
package de.hsd.medien.bs;

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
```

## Safe-State Bewertung
Bewerten Sie die folgenden Ressourcen-Zuteilungs-Situationen nach den Kategorien *safe* und *unsafe*.

Begründen Sie jeweils:
- Für die Bewertung *unsafe*, wieso ein Deadlock zwangsläufig entsteht
- Zeigen Sie für die Bewertung *safe*, wie die *sichere* Fortführung sämtlicher Zuteilungen erfolgen kann, so dass kein Deadlock entsteht.

| Prozess | Aktuell | Max | 
| --- | --- | --- | 
| A | 1 | 2 |  
| B | 4 | 5 |  
| C | 1 | 9 |  
| D | 3 | 13 | 
Frei: 1

| Prozess | Aktuell | Max | 
| --- | --- | --- | 
| A | 2 | 6 |  
| B | 1 | 3 |  
| C | 1 | 7 |  
| D | 3 | 8 | 
Frei: 2

| Prozess | Aktuell | Max | 
| --- | --- | --- | 
| A | 1 | 5 |  
| B | 1 | 3 |  
| C | 1 | 7 |  
| D | 3 | 8 | 
Frei: 3

## Safe/Unsafe-State Checker
Abstrahieren Sie von der Übung der Safe-State-Bewertung.

1. Formulieren Sie, unter welchen Bedingungen ein *sicherer* (safe) Zustand für eine Konstellation von Prozessen und Ressourcen-Zuteilungen vorliegt.
2. Implementieren Sie folgendes Interface, welches für einen gegebenen Zustand die Bewertung liefert.

```java
package de.hsd.medien.bs;

import java.util.List;

public interface SafeStateChecker {
    boolean isSafe(List<ProzessZuteilung> zuteilungsSituation, int anzahlFreieRessourcen);
}
```

Verwenden Sie folgende Implementierung einer Prozesszuteilung:
```java
package de.hsd.medien.bs;

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
```
   

 
