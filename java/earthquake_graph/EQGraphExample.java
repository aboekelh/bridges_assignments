
import bridges.base.*;
import bridges.connect.Bridges;
import bridges.data_src_dependent.EarthquakeUSGS;
import bridges.data_src_dependent.Tweet;
import bridges.data_src_dependent.USGSaccount;

import java.util.List;
import java.math.*;

/*
 * Output at: http://bridges-clone.herokuapp.com/assignments/75/agoncharow
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
        Bridges bridges = new Bridges(75, "API_KEY", "USER_NAME");

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

        graph = new GraphAdjListSimple<>();
        eqlist = Bridges.getEarthquakeUSGSData(name, 10000);
        for (EarthquakeUSGS eq : eqlist)    // builds Earthquake map
        {
            if (eq.getMagnitude() < 4)
                continue;

            String bin = null;
            try
            {
               bin = eq.getLocation();
               if (bin.contains("of "))
               {
                   bin = bin.split("of ")[1];
               }
               if (bin.contains(","))
               {
                   bin = bin.split(",")[1];
               }
               ElementVisualizer vis = graph.getVertex(bin).getVisualizer();
               vis.setSize(vis.getSize() == 50 ? 50 : vis.getSize() + 1);
               Element ver = graph.getVertex(bin);
               int amount = Integer.valueOf(ver.getLabel().split(":")[1].trim());
               ver.setLabel(ver.getLabel().replace(String.valueOf(amount), String.valueOf(++amount)));
            }
            catch (NullPointerException e)
            {
                graph.addVertex(bin, bin);
                graph.getVertex(bin).setLabel(bin + ": 1");
                graph.getVisualizer(bin).setLocation(eq.getLongit(), eq.getLatit());
                graph.getVisualizer(bin).setSize(1);
            }
            catch (ArrayIndexOutOfBoundsException e)
            {
                System.out.printf("OOB: %s\n", eq.getLocation());
                graph.addVertex(eq.getLocation(),eq.getLocation());
                graph.getVisualizer(eq.getLocation()).setLocation(eq.getLongit(), eq.getLatit());
            }
            //System.out.println(eq.getLocation());
        }

        bridges.setCoordSystemType("equirectangular");
        bridges.setDataStructure(graph);
        bridges.setMapOverlay(true);
        bridges.setServer("clone");
        bridges.setTitle("Earthquake Map");
        bridges.visualize();

        for (EarthquakeUSGS eq : eqlist)    // builds Earthquake graph
        {
            if (eq.getMagnitude() < 4)
                continue;

            graph.addVertex(eq.getTitle(), eq.getTitle());
            ElementVisualizer vis = graph.getVertex(eq.getTitle()).getVisualizer();
            //vis.setSize(eq.getMagnitude()*5);
            //vis.setLocation(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);    // reset vertex location
            String bin = eq.getLocation();
            if (bin.contains("of "))
            {
                bin = bin.split("of ")[1];
            }
            if (bin.contains(","))
            {
                bin = bin.split(",")[1];
            }

            graph.addEdge(bin, eq.getTitle());
            graph.getVisualizer(bin).setLocation(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY);
            graph.getVisualizer(bin).setSize(50);
            graph.getVisualizer(bin).setColor("green");


            for (EarthquakeUSGS ua : eqlist)
            {
                if (graph.getVertex(ua.getTitle()) == null)
                    continue;

                if (eq.getTitle().equals(ua.getTitle()))
                    continue;

                double distance = calcDistance(eq.getLongit(), ua.getLongit(), eq.getLatit(), ua.getLatit());


                if (distance <= 500)
                    try
                    {
                        graph.getEdgeData(bin, eq.getTitle());
                        graph.getEdgeData(bin, ua.getTitle());
                    }
                    catch (Exception e)
                    {
                        graph.addEdge(ua.getTitle(), bin);
                    }
            }

        }

        bridges.setMapOverlay(false);
        bridges.visualize();
    }
}
