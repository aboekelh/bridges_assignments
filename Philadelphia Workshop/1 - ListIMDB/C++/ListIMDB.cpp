#include "Bridges.h"
#include "DataSource.h"
#include "Array.h"

using namespace bridges;

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(1, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("List IMDB");


	std::vector< ActorMovieIMDB > ami = DataSource::getActorMovieIMDBData();
	
	for (int i=0 ; i< 10; ++i) {
	  std::cout<<ami[i].getActor()<<" "<<ami[i].getMovie()<<std::endl;
	}
	
	// tell Bridges what data structure to visualize
	//Bridges::setDataStructure(arr);

	// visualize the list
	Bridges::visualize();

	return 0;
}
