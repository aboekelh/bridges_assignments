
import bridges.base.*;
import bridges.connect.Bridges;
import bridges.data_src_dependent.EarthquakeUSGS;

import java.util.Comparator;
import java.util.List;

public class GraphEQ {

    public static double calcDistance(double la1, double lo1, double la2, double lo2) {
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

		/*	TODO:
		 *	Initialize a Graph of your choice
		 *	Grab Earthquake data and store it in a List
		 *	Sort that list in decending order
		 */

        for (int i = 0; i < 100; ++i) {
		/* TODO:
		 * Add the top 99 Earthquakes to the graph
		 * Set each earthquake's location based on its latitude and longitude
		 * ex: graph.getVisualizer(key).setLocation(earthquake.getLongit(), earthquake.getLatit());
		 * Tweak the colors or other visual elements if you wish
		 */
        }

        bridges.setCoordSystemType("equirectangular");
        bridges.setDataStructure(graph);
        bridges.setMapOverlay(true);
        bridges.setServer("clone");
        bridges.setTitle("Earthquake Map");
        bridges.visualize();

        for (int i = 0; i < 100; ++i) {
		/* TODO:
		 * Compare the distances between all vertexes in the graph, drawing an edge 
		 *	if they are within some threshold. A method is provided to give a rough
		 *	estimate between 2 lat,long points.
		 *
		 * example usage: calcDistance(eq1.getLatit(), eq1.getLongit(),
		 *							   eq2.getLatit(), eq2.getLongit());
		 * which returns a double representing the distance of two points in KM
		 */
        }

        bridges.visualize();

        for (int i = 0; i < 100; ++i) {
		/* TODO:
		 * Reset the locations of the vertices by setting their location to
		 *	Double.POSITIVE_INFINITY
		 *
		 *	ex: graph.getVisualizer(key).setLocation(Double.POSITIVE_INFINITY, Double.POSITIVE_INFINITY) 
		 */
        }

        bridges.setMapOverlay(false);
        bridges.visualize();
    }
}
