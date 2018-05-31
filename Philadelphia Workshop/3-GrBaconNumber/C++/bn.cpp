
#include <iostream>
#include <string>
#include <vector>

using namespace std;

//#include "book.h"
#include "lqueue.h"
#include "Bridges.h"
#include "GraphAdjList.h"
#include "DataSource.h"
#include "data_src/ActorMovieIMDB.h"

using namespace bridges;

int getBaconNumber (GraphAdjList<string, string> *gr, string src_actor, 	
		string dest_actor,	unordered_map<string, string> mark, 
		unordered_map<string, int> dist, unordered_map<string, string> parent);

int main() {
	string hilite_color = "orange", 
			def_color = "green",
			end_color = "red";
		
	// Initialize BRIDGES with your credentials
	Bridges::initialize(13, "kalpathi60", "486749122386");

	// set title for visualization
	Bridges::setTitle("Bacon Number: IMDB Actor-Movie Data");

	// use an adjacency list based graph
	GraphAdjList<string> gr;

	// get the actor movie IMDB data through the BRIDGES API
	vector<ActorMovieIMDB> actor_list = DataSource::getActorMovieIMDBData(1814);

	string actor, movie;
	for (int k = 0; k < actor_list.size(); k++) {

					// get the actor and movie names
		actor = actor_list[k].getActor();
		movie = actor_list[k].getMovie();

		// our graph needs to have a unique set of actors and movies;
		// so reate the actor and movie vertices only if they dont already
		// exit; use an STL map to check for that

		// first get the graph's vertex list
		unordered_map<string, Element<string>*> *vertices = gr.getVertices();

		// add actor if it does not exist
		if (vertices->find(actor) == vertices->end()) 
			gr.addVertex(actor);

		// add movie if it does not exist
		if (vertices->find(movie) == vertices->end()) 
			gr.addVertex(movie);

					// create the edge -- assumes no duplicate edges 
					// undirected graph, edges go both ways
		gr.addEdge(actor, movie, 1);
		gr.addEdge(movie, actor, 1);

					// TO DO : Highlight "Cate_Blanchett" node and the movie nodes she is 
					// connected to  in "red" and do the same for "Kevin_Bacon_(I)" in "green"
					// specify colors by Color("red"), for example
		if (actor == "Cate_Blanchett") {
			gr.getLinkVisualizer (actor, movie)->setColor (Color("yellow"));
			gr.getVisualizer (actor)->setColor (Color("yellow"));
			gr.getVisualizer (movie)->setColor (Color("yellow"));
		}
		if (actor == "Kevin_Bacon_(I)") {
			gr.getLinkVisualizer (actor, movie)->setColor (Color("green"));
			gr.getVisualizer (actor)->setColor (Color("green"));
			gr.getVisualizer (movie)->setColor (Color("green"));
		}

	}

	//set the data structure handle, and visualize the input graph
	Bridges::setDataStructure(&gr);
	Bridges::visualize();

	unordered_map<string, string> mark; 		//keeps track of visited nodes
	unordered_map<string, string> parent; 	//keeps track of parent nodes
	unordered_map<string, int> dist; 		// keeps track of distances

						// initialize maps for Bacon number algorithm
	for (auto& v : *gr.getVertices() ) {
		mark[v.first] = "unvisited"; 
		parent[v.first] = "none";
		dist[v.first] = 0;
	}

	int d = getBaconNumber(&gr, "Kevin_Bacon_(I)", "Cate_Blanchett", 
									mark, dist, parent);
	Bridges::setDataStructure(&gr);
	Bridges::visualize();

	return 0;
}

//
// Computes the Bacon Number of a an actor (#links that takes you from the 
// source actor to the destination actor. 
//
int getBaconNumber (GraphAdjList<string> *gr,   		// input graph 
				string src_actor, 						// source actor 
				string dest_actor,						// destination actor
				unordered_map<string, string> mark, 	// mark array -- keep track of visited nodes
				unordered_map<string, int> dist, 		// distances to src node 
				unordered_map<string, string> parent){ 	// parent of node - for finding path 
														// back to source actor
													
	// Need to use a queue, as we are doing a BFS search
	LQueue<string> *lq = new LQueue<string>();

	// add the source actor to the queue
	lq->enqueue(src_actor);
	// initializations
	mark[src_actor] = "visited";
	dist[src_actor] =  0;
	parent[src_actor]  = "none";

	// BFS traversal
	while (lq->length() > 0) {  // non empty queue
		string vertex = (string) lq->dequeue();

		// get adjacency list of vertex
		const SLelement<Edge<string>> *sl_list = gr->getAdjacencyList(vertex);
		for (const SLelement<Edge<string>> *sle = sl_list; sle != NULL; sle = 
											sle->getNext()){
			// get destination vertex
			string w = ((Edge<string>)sle->getValue()).getVertex();

			// if unvisited, mark it as visited and add to queue,
			// increment distance and point point parent back to vertex name
			if (mark[w] == "unvisited"){
				mark[w] =  "visited";
				lq->enqueue(w);
								// update the distance and parent values
				dist[w] =  dist[vertex]+1;
				parent[w] = vertex;

				// if we found the destination actor, then we are done
				// we want to highlight the actor and the path to the source
				// actor; we use the parent links to retrace (note source actor's
				// parent is "none" for us to stop)
				if (w == dest_actor){  // found it
										// highlight actor and path using
										// the parent values and the hashmap
										// to trace back the sequence of nodes
										// to the source node
						
					// optional, we are deemphasizing the graph nodes by adjusting opacity	
					Element<string> *el, *el2;
					for (auto& v : *gr->getVertices()) {
						el = (Element<string>*) v.second;
						el->getVisualizer()->setOpacity(0.5);
					}
						
					// highlight the nodes and the links in the path from the source
					// to the destination actor
					string p = dest_actor;
					while (p != "none") {

						//  color the nodes in the path
						//  example: gr->getVisualizer(key-val)->setColor(Color("red"))
						gr->getVisualizer(p)->setColor(Color("red"));
						gr->getVisualizer(p)->setSize(50);
						gr->getVisualizer(p)->setOpacity(1.);

						// next, color the link, but check the parent is not "none", else you will
						// get an exception
						//  example: gr->getLinkVisualizer(src-key, dest-key)->setColor(Color("red"))
						string par = parent[p];
						if (par != "none") {
							gr->getLinkVisualizer(p, par)->setColor(Color("red"));
							gr->getLinkVisualizer(p, par)->setThickness(10.);
						}	
						p = par;
					}
					// return the bacon number of this pair of actors
					return dist[dest_actor];
				}
			}
		}
	}
	return -1;
}
