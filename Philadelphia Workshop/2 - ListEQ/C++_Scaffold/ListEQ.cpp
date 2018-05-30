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
  /*TODO: set the properties on the element based on location, magnitude of the earthquake */
}

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(102, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("List EQ");

	//Get Earthquake data
	std::vector< EarthquakeUSGS > eqs = DataSource::getEarthquakeUSGSData(100);

	//Building linked list
	SLelement<EarthquakeUSGS>* head = nullptr;

	for (auto eq : eqs) {
	  SLelement<EarthquakeUSGS>* newone = new SLelement<EarthquakeUSGS> (eq,
									     eq.getTitle());
	  newone->setNext(head);
	  head = newone;
	}

	//Setting properties.
	{
	  SLelement<EarthquakeUSGS>* current = head;
	  while (current != nullptr) {
	    setProperties(current);
	    current = current->getNext();
	  }
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
