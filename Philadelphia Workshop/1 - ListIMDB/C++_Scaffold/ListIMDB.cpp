#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"
#include "SLelement.h"


using namespace bridges;

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(1, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("List IMDB");

	std::vector< ActorMovieIMDB > ami = DataSource::getActorMovieIMDBData(10);

	// Printing actor movie pairs for debugging purposes
	for (auto im : ami) {
	  std::cout<<im.getActor()<< " - "<< im.getMovie()<<std::endl;
	}
	
	//building linked list
	/*TODO: make a linked list with all movie actor pair */
	SLelement<int>* head = new SLelement<int> (0, "First");
	SLelement<int>* sec = new SLelement<int> (1, "Second");

	head->setNext(sec);
	
	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(head);

	// visualize the list
	Bridges::visualize();

	//free memory
	delete head;
	delete sec;
	
	return 0;
}
