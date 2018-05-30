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
  double R = 6371; // Radius of the earth in KM
  // Haversine formula to calculate a value between 0 and 1 between 2 points on a sphere, 1 being the
  // opposite side of the sphere
  double laDistance = degree_to_rad(la2 - la1);
  double loDistance = degree_to_rad(lo2 - lo1);
  double a = sin(laDistance / 2) * sin(laDistance / 2)
    + cos(degree_to_rad(la1)) * cos(degree_to_rad(la2))
    * sin(loDistance / 2) * sin(loDistance / 2);

  double c = 2. * atan2(sqrt(a), sqrt(1 - a));

  
  double distance = R * c;    //convert to KM
  return distance;
}


int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(104, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("Graph EQ");

	//Get most recent 10000 earthquakes
	std::vector< EarthquakeUSGS > eqs = DataSource::getEarthquakeUSGSData(10000);

	//sort earthquake by decreasing magnitude
	std::sort(eqs.begin(), eqs.end(),
		  [](const EarthquakeUSGS& a, const EarthquakeUSGS& b )->bool {
		  
		    return a.getMagnitude()>b.getMagnitude();
		  });

	//only retain the first 100 quakes
	eqs.resize(100);
	
	for (auto eq: eqs) {
	  std::cout<<eq.getTitle()<<std::endl;
	  std::cout<<"LONG: "<<eq.getLongit()<<std::endl;
	  std::cout<<"LAT: "<<eq.getLatit()<<std::endl;
	}

	//Builds a graph with nodes at correct location
	GraphAdjList<int, EarthquakeUSGS> graph;
	
	for (int i=0; i<eqs.size(); ++i) {
	  auto eq = eqs[i];
	  graph.addVertex(i, eq);
	  
	  Element<EarthquakeUSGS> * el = graph.getVertex(i);
	  el->setLabel(eq.getTitle());
	  
	  ElementVisualizer* elv = graph.getVisualizer(i);
	  elv->setLocation(eq.getLongit(), eq.getLatit());
	  elv->setSize (eq.getMagnitude());

	  int red = (int) ((eq.getLongit() / 180.0) * 255);
	  red = red > 0 ? red : 0;
	  
	  int blue = (int) ((eq.getLongit() / 180.0) * 255);
	  blue = blue < 0 ? blue * -1 : 0;
	  
	  int green = (int) ((eq.getLatit() / 90.0) * 255);
	  green = green < 0 ? green * -1: green;

	  int alpha = 255;
	  
	  elv->setColor(Color(red, green, blue, alpha)); 
	}	
	
	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(&graph);

	Bridges::setCoordSystemType("equirectangular");
	Bridges::setMapOverlay(true);

	// visualize the list
	Bridges::visualize();
	
	//add edges between
	for (int i=0; i<eqs.size(); ++i) {
	    EarthquakeUSGS eqi = eqs[i];
	    for (int j=0; j<eqs.size(); ++j) {
	      if (i == j) continue;
	      
	      EarthquakeUSGS eqj = eqs[j];

	      double distance = calcDistance(eqi.getLatit(), eqi.getLongit(),
					     eqj.getLatit(), eqj.getLongit());

	      if (distance < 500) {
		int weight = 1;
		graph.addEdge(i, j, weight);
		auto linkvis = graph.getLinkVisualizer(i, j);
		std::stringstream ss;
		ss<<distance<<" km";
		linkvis->setLabel(ss.str());
	      }
	  }
	}
	
	// visualize the list
	Bridges::visualize();

	return 0;
}
