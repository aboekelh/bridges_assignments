
import bridges.base.*;
import bridges.connect.Bridges;
import bridges.data_src_dependent.EarthquakeUSGS;
import bridges.data_src_dependent.Tweet;
import bridges.data_src_dependent.USGSaccount;

import java.util.List;
import java.math.*;

/*
 *	sample output at: http://bridges-clone.herokuapp.com/assignments/75/agoncharow
 */


public class EQGraph
{
    public static double calcDistance(double lo1, double lo2, double la1, double la2)
    {
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

    public static void main(String[] args) throws Exception
    {
        Bridges bridges = new Bridges(75, "API_KEY", "USER_ID");

        GraphAdjListSimple<String> graph  = new GraphAdjListSimple<>();

        USGSaccount name = new USGSaccount( "earthquake" );
        List<EarthquakeUSGS> eqlist = Bridges.getEarthquakeUSGSData(name, 99);


        for (EarthquakeUSGS eq : eqlist)    // builds Earthquake map
        {
            graph.addVertex(eq.getTitle(), eq.getTitle());
            ElementVisualizer vis = graph.getVertex(eq.getTitle()).getVisualizer();
            vis.setLocation(eq.getLongit(), eq.getLatit());     // set vertex to its Long-Lat point
            vis.setSize(eq.getMagnitude());                     // scale vertex based on magnitude
        }

        bridges.setCoordSystemType("equirectangular");
        bridges.setDataStructure(graph);
        bridges.setMapOverlay(true);
        bridges.setServer("clone");
        bridges.setTitle("Earthquake Map");
        bridges.visualize();


        for (EarthquakeUSGS eq : eqlist)    // builds Earthquake graph
        {
            ElementVisualizer vis = graph.getVertex(eq.getTitle()).getVisualizer();
            vis.setSize(eq.getMagnitude()*5);
            vis.setLocation(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);    // reset vertex location
            for (EarthquakeUSGS ua : eqlist)
            {
                if (eq.getTitle().equals(ua.getTitle()))
                    continue;

                double distance = calcDistance(eq.getLongit(), ua.getLongit(),
                                                eq.getLatit(), ua.getLatit());

                if (distance <= 25) // draw edge between vertices 25 KM or less apart
                {
                    graph.addEdge(eq.getTitle(), ua.getTitle());
                    graph.getLinkVisualizer(eq.getTitle(), ua.getTitle()).setLabel(String.format("%.2f KM", distance));
                }
            }
        }

        bridges.setMapOverlay(false);
        bridges.visualize();
    }
}
