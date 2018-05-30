
import bridges.base.*;
import bridges.connect.Bridges;
import bridges.data_src_dependent.EarthquakeUSGS;

import java.util.Comparator;
import java.util.List;

public class GraphEQ {

    public static double calcDistance(double lo1, double lo2, double la1, double la2) {
        final int R = 6371; // Radius of the earth in KM
        // Haversine formula to calculate a value between 0 and 1 between 2 points on a sphere, 1 being the
        // opposite side of the sphere
        double laDistance = Math.toRadians(la2 - la1);
        double loDistance = Math.toRadians(lo2 - lo1);
        double a = Math.sin(laDistance / 2) * Math.sin(laDistance / 2)
                + Math.cos(Math.toRadians(la1)) * Math.cos(Math.toRadians(la2))
                * Math.sin(loDistance / 2) * Math.sin(loDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        double distance = R * c;    //convert to KM
        return distance;
    }

    public static void main(String[] args) throws Exception {
        Bridges bridges = new Bridges(4, "bridges_workshop", "1298385986627");

        GraphAdjListSimple<String> graph = new GraphAdjListSimple<>();

        // grabs most recent 10000 earthquakes
        List<EarthquakeUSGS> eqList = Bridges.getEarthquakeUSGSData(10000);

        // sorts list by magnitude in descending order
        eqList.sort(Comparator.comparingDouble(EarthquakeUSGS::getMagnitude).reversed());

        for (EarthquakeUSGS eq : eqList) {
            if (graph.getAdjacencyList().size() > 99)
                break;

            graph.addVertex(eq.getTitle(), eq.getTitle());
            ElementVisualizer vis = graph.getVisualizer(eq.getTitle());

            vis.setLocation(eq.getLongit(), eq.getLatit());     // set vertex to its Long-Lat point
            vis.setSize(eq.getMagnitude());                     // scale vertex based on magnitude

            int red = (int) ((eq.getLongit() / 90.0) * 255);
            red = red > 0 ? red : 0;

            int blue = (int) ((eq.getLongit() / 90.0) * 255);
            blue = blue < 0 ? blue * -1 : 0;

            int green = (int) ((eq.getLatit() / 180.0) * 255);
            green = green < 0 ? green * -1: green;

            vis.setColor(red, green, blue, 1.0f);           // set color relative to earthquakes lat long position
        }


        bridges.setCoordSystemType("equirectangular");
        bridges.setDataStructure(graph);
        bridges.setMapOverlay(true);
        bridges.setServer("clone");
        bridges.setTitle("Earthquake Map");
        bridges.visualize();

        for (int i = 0; i < 99; ++i) {
            EarthquakeUSGS eq = eqList.get(i);

            for (int j = 0; j < 99; ++j) {
                if (i == j)
                    continue;

                EarthquakeUSGS ua = eqList.get(j);
                double distance = calcDistance(eq.getLongit(), ua.getLongit(),  // returns distance in km
                                                eq.getLatit(), ua.getLatit());

                if (distance < 500) {
                    graph.addEdge(eq.getTitle(), ua.getTitle());
                    graph.setEdgeData(eq.getTitle(), ua.getTitle(), String.valueOf(distance));
                }
            }
        }

        bridges.visualize();

        for (int i = 0; i < 99; ++i) {
            EarthquakeUSGS eq = eqList.get(i);
            ElementVisualizer vis = graph.getVisualizer(eq.getTitle());

            vis.setLocation(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);  // resets vertices' locations
            vis.setSize(eq.getMagnitude() * 5);  // increases size of vertex
        }

        bridges.setMapOverlay(false);
        bridges.visualize();
    }
}
