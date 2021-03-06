
#include <string>
#include <iostream>
#include <fstream>

using namespace std;

#include "Bridges.h"
#include "DataSource.h"
#include "data_src/EarthquakeUSGS.h"
#include "book.h"
#include "BSTElement.h"
#include "bst.h"


using namespace bridges;

int max_quakes = 1000;

int main() {
	string hilite_color = "orange", 
			def_color = "green",
			end_color = "red";
		
	Bridges::initialize(103, "bridges_workshop", "1298385986627");
								// read the earth quake  data and build the BST
	Bridges::setTitle("Recent Earthquakes (USGIS Data)");

    vector<EarthquakeUSGS> eq_list = DataSource::getEarthquakeUSGSData(max_quakes);

	BST<float, EarthquakeUSGS> *bst = new BST<float, EarthquakeUSGS> ();

	for (int k = 0; k < max_quakes; k++) {
		EarthquakeUSGS eq = eq_list[k];
		bst->insert(eq.getMagnitude(), eq);
	}

					// visualize the binary search tree
//	bst->setProperties(bst->getRoot());
	Bridges::setDataStructure(bst->getRoot());
	Bridges::visualize();

	return 0;
}
