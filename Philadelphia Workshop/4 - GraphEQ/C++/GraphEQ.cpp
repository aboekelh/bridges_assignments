#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"
#include "SLelement.h"
#include "GraphAdjList.h"

using namespace bridges;

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
	GraphAdjList<std::string, int, int> graph;
	
	for (auto eq : eqs) {
	  graph.addVertex(eq.getTitle());

	  ElementVisualizer* elv = graph.getVisualizer(eq.getTitle());
	  elv->setLocation(eq.getLongit(), eq.getLatit());
	  elv->setSize (eq.getMagnitude()*4);
	}	
	
	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(&graph);

	Bridges::setCoordSystemType("equirectangular");
	Bridges::setMapOverlay(true);
	
	// visualize the list
	Bridges::visualize();

	return 0;
}
