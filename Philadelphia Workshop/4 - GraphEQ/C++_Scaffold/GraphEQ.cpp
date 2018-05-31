#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"
#include "SLelement.h"
#include "GraphAdjList.h"

using namespace bridges;

double degree_to_rad(double d) {
  return d*M_PI/180;
}

double calcDistance(double la1, double lo1, double la2, double lo2) {
  double R = 6371; // Radius of the earth in km
  // Haversine formula to calculate a value between 0 and 1 between 2 points on a sphere, 1 being the
  // opposite side of the sphere
  double laDistance = degree_to_rad(la2 - la1);
  double loDistance = degree_to_rad(lo2 - lo1);
  double a = sin(laDistance / 2) * sin(laDistance / 2)
    + cos(degree_to_rad(la1)) * cos(degree_to_rad(la2))
    * sin(loDistance / 2) * sin(loDistance / 2);

  double c = 2. * atan2(sqrt(a), sqrt(1 - a));

  
  double distance = R * c;    //convert to km
  return distance;
}


int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(104, "bridges_workshop", "1298385986627");
	
	/* TODO:
	 * Grab 10000 earthquakes.
	 * Retain only the 100 earthquakes of highest magnitude.
	 */
	
	/* TODO:
	 * Initialize a Graph of your choice (recommend a GraphAdjList <int, EarthquakeUSGS>) 
	 * Tell Bridges to visualize that graph.
	 * Add the Earthquakes to the graph as individual vertices.
	 * Set each earthquake's location based on its longitude and latitude (Use ElementVisualizer::setLocation() ).
	 * Tweak the colors or other visual elements if you wish.
	 */
	
	
	Bridges::setCoordSystemType("equirectangular");
	Bridges::setMapOverlay(true);

	// visualize the list
	Bridges::visualize();
	
	/* TODO: 
	 * 
	 * Compare the distances between all vertexes in the graph,
	 * drawing an edge if they are within 500km.
	 *
	 * Use calcDistance to compute the distance between two
	 * earthquakes.
	 */
	
	
	// visualize the list
	Bridges::visualize();

	// Visualize the graph just by itself
	
	/* TODO:
	 *
	 * Reset the locations of the vertices by setting their location to INFINITY
	 */


	Bridges::setMapOverlay(false);

	
	// visualize the list
	Bridges::visualize();

	
	return 0;
}
