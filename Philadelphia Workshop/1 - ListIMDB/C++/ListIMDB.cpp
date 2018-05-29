#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"
#include "SLelement.h"


using namespace bridges;

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(1, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("List IMDB");


	std::vector< ActorMovieIMDB > ami = DataSource::getActorMovieIMDBData();


	//building linked list
	SLelement<ActorMovieIMDB>* head = nullptr;

	for (auto im : ami) {
	  SLelement<ActorMovieIMDB>* newone = new SLelement<ActorMovieIMDB> (im,
									     im.getActor() + " - " + im.getMovie());
	  newone->setNext(head);
	  head = newone;
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
