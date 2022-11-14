/* import org.jgrapht.alg.cycle.HawickJamesSimpleCycles;
import org.jgrapht.alg.interfaces.CycleBasisAlgorithm;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class DeadlockDetector {

    DefaultDirectedGraph zuteilungsSituation;

    public static void main(String[] args) {
        DefaultDirectedGraph beispielGraph = erstelleBeispielGraph();
        DeadlockDetector dd = new DeadlockDetector(beispielGraph);
        System.out.println(
            String.format("Analysiere den folgenden Zustand ... \r\n%s\r\n", dd.getZuteilungsSituation().toString())
        );
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
        //TODO: Implementierung
        Set vertexes = this.zuteilungsSituation.vertexSet();

        for (Object knoten : vertexes) {
            List besuchteKnoten = new LinkedList();
            List besuchteKanten = new LinkedList();
            Object aktuellerKnoten = knoten;
            do {
                if (pruefeObKnotenInbesuchteKnoten(aktuellerKnoten, besuchteKnoten)) {
                    return true;
                }

                besuchteKnoten.add(aktuellerKnoten);
                for (Object kante : zuteilungsSituation.edgesOf(aktuellerKnoten)) {
                    if (besuchteKanten.contains(kante)) {
                        continue;
                    } else {
                        besuchteKanten.add(kante);
                        aktuellerKnoten = zuteilungsSituation.getEdgeTarget(kante);
                        break;
                    }
                }
            }  while (nichtSackgasse());
        }
        return false;
    }

    public boolean nichtSackgasse() {
        return true;
    };

    public boolean pruefeObKnotenInbesuchteKnoten(Object knoten,List besuchteKnoten) {
        if (besuchteKnoten.contains(knoten))
            return true;
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
*/