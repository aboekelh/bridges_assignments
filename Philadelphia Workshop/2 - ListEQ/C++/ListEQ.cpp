#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"
#include "SLelement.h"


using namespace bridges;

/*
 * Case Sensitive Implementation of endsWith()
 * It checks if the string 'mainStr' ends with given string 'toMatch'
 *
 * From: http://thispointer.com/c-how-to-check-if-a-string-ends-with-an-another-given-string/
 */
bool endsWith(const std::string &mainStr, const std::string &toMatch)
{
	if(mainStr.size() >= toMatch.size() &&
			mainStr.compare(mainStr.size() - toMatch.size(), toMatch.size(), toMatch) == 0)
			return true;
		else
			return false;
}

void setProperties(SLelement<EarthquakeUSGS>* el) {
  /* Get the magnitude of the earthquake from the SLelement
   *   (the EarthquakeUSGS object is stored as the value inside the SLelement)
   */
  double magnitude = el->getValue().getMagnitude();
  
  /* Prepare color and size variables */
  std::string color = "";
  float size = 0.0f;
  
  /* Pick the color depending on the magnitude */
  if(magnitude < 1.0) {
    color = "blue";
  } else if(magnitude < 2.0) {
    color = "green";
  } else if(magnitude < 3.0) {
    color = "yellow";
  } else if(magnitude < 4.0) {
    color = "orange";
  } else if(magnitude < 5.0) {
    color = "red";
  } else if(magnitude < 6.0) {
    color = "purple";
  } else {
    color = "black";
  }

  auto vizp = el->getVisualizer();
  
  /* Set the color of the Element */
  vizp->setColor(color);
    
  /* Set the size of the Element based on the magnitude */
  vizp->setSize(magnitude*5);
  
  /* Set the shape of the Element based on the location */
  if(endsWith ( el->getValue().getLocation(), "Alaska")) {
    vizp->setShape(STAR);
  }
  if(endsWith (el->getValue().getLocation(), "Hawaii")) {
    vizp->setShape(WYE);
  }
  
}

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(102, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("List EQ");

	std::vector< EarthquakeUSGS > eqs = DataSource::getEarthquakeUSGSData(100);

	//building linked list
	SLelement<EarthquakeUSGS>* head = nullptr;

	for (auto eq : eqs) {
	  SLelement<EarthquakeUSGS>* newone = new SLelement<EarthquakeUSGS> (eq,
									     eq.getTitle());
	  newone->setNext(head);
	  head = newone;

	  setProperties(newone);
	}

	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(head);

	// visualize the list
	Bridges::visualize();

	//free memory
	while (head != nullptr) {
	  auto next = head->getNext();
	  delete head;
	  head = next;
	}
	
	return 0;
}
