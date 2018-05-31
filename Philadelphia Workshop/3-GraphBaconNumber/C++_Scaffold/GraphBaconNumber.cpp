
#include <iostream>
#include <string>
#include <vector>

using namespace std;

#include "Bridges.h"
#include "GraphAdjList.h"
#include "DataSource.h"
#include "data_src/ActorMovieIMDB.h"

using namespace bridges;

int getBaconNumber (GraphAdjList<string, string> *gr, string src_actor, 	
		string dest_actor);


void buildActorMovieGraph(GraphAdjList<string, string> *gr) {
	// get the actor movie IMDB data through the BRIDGES API
	vector<ActorMovieIMDB> actor_list = DataSource::getActorMovieIMDBData(1814);

	for (int k = 0; k < actor_list.size(); k++) {
	  string actor, movie;

	  // get the actor and movie names
		actor = actor_list[k].getActor();
		movie = actor_list[k].getMovie();

		// our graph needs to have a unique set of actors and movies;
		// so create the actor and movie vertices only if they dont already
		// exit; use an STL map to check for that

		// first get the graph's vertex list
		unordered_map<string, Element<string>*> *vertices = gr->getVertices();

		// add actor if it does not exist
		if (vertices->find(actor) == vertices->end()) 
			gr->addVertex(actor);

		// add movie if it does not exist
		if (vertices->find(movie) == vertices->end()) 
			gr->addVertex(movie);

					// create the edge -- assumes no duplicate edges 
					// undirected graph, edges go both ways
		gr->addEdge(actor, movie, 1);
		gr->addEdge(movie, actor, 1);

		// TODO : Highlight "Cate_Blanchett" node and the movie nodes she is 
		// connected to  in "red" and do the same for "Kevin_Bacon_(I)" in "green"
		// specify colors by Color("red"), for example
		//
		// You can get a LinkVisualizer with gr->getLinkVisualizer(src, dest).
		// LinkVisualizer have functions such as setColor().
		
	}

  
}

int main() {
	string hilite_color = "orange", 
			def_color = "green",
			end_color = "red";
		
	// Initialize BRIDGES with your credentials
	Bridges::initialize(103, "bridges_workshop", "1298385986627");

	// set title for visualization
	Bridges::setTitle("Bacon Number: IMDB Actor-Movie Data");

	// use an adjacency list based graph
	GraphAdjList<string> gr;

	buildActorMovieGraph(&gr);

	//set the data structure handle, and visualize the input graph
	Bridges::setDataStructure(&gr);
	Bridges::visualize();


	getBaconNumber(&gr, "Kevin_Bacon_(I)", "Cate_Blanchett");
	Bridges::setDataStructure(&gr);
	Bridges::visualize();

	return 0;
}

//
// Computes the Bacon Number of a an actor (#links that takes you from the 
// source actor to the destination actor. 
//
int getBaconNumber (GraphAdjList<string> *gr,  // input graph 
		    string src_actor,          // source actor 
		    string dest_actor          // destination actor
		    ){
  //TODO: Perform a BFS traversal of the graph from src_actor.
  //Keep parent information.



  //TODO: Highlight all edges and vertices on the shortest path
  //between src_actor and dest_actor
  
}
