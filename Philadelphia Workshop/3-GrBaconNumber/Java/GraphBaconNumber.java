
import java.util.HashMap;
import java.util.Map.Entry;
import java.lang.String;
import java.util.List;

import bridges.connect.Bridges;
import bridges.base.SLelement;
import bridges.base.Element;
import bridges.base.Edge;
import bridges.base.GraphAdjList;
import bridges.base.GraphAdjListSimple;
import bridges.data_src_dependent.ActorMovieIMDB;



public class GraphBaconNumber {
	public static void main(String[] args) throws Exception {
		
		// Initialize BRIDGES with your credentials
//		Bridges bridges = new Bridges(13, "kalpathi60", "486749122386");
        Bridges bridges = new Bridges(13, "486749122386", "kalpathi60");

		
		// set title for visualization
		bridges.setTitle("Bacon Number: IMDB Actor-Movie Data");

		// use an adjacency list based graph
		GraphAdjListSimple<String> gr = new GraphAdjListSimple<>();

		// get the actor movie IMDB data through the BRIDGES API
		List<ActorMovieIMDB> actor_list = bridges.getActorMovieIMDBData ("earthquake", 1814);

		String actor, movie;
		for (int k = 0; k < actor_list.size(); k++) {

						// get the actor and movie names
			actor = actor_list.get(k).getActor();
			movie = actor_list.get(k).getMovie();

			// our graph needs to have a unique set of actors and movies;
			// so reate the actor and movie vertices only if they dont already
			// exit; use an STL map to check for that

			// first get the graph's vertex list
			HashMap<String, Element<String>> vertices = gr.getVertices();

			// add actor if it does not exist
			if (!vertices.containsKey(actor))
				gr.addVertex(actor, actor);

			// add movie if it does not exist
			if (!vertices.containsKey(movie))
				gr.addVertex(movie, movie);

						// create the edge -- assumes no duplicate edges 
						// undirected graph, edges go both ways
			gr.addEdge(actor, movie, 1);
			gr.addEdge(movie, actor, 1);

			// TO DO : Highlight "Cate_Blanchett" node and the movie nodes she is 
			// 	connected to  in "red" and do the same for 
			//	"Kevin_Bacon_(I)" in "green"
						// specify colors by their names, "red", for example
			if (actor.equals("Cate_Blanchett")) {
				gr.getLinkVisualizer (actor, movie).setColor ("orange");
				gr.getVisualizer (actor).setColor ("orange");
				gr.getVisualizer (movie).setColor ("orange");
			}
			if (actor.equals("Kevin_Bacon_(I)")) {
				gr.getLinkVisualizer (actor, movie).setColor ("green");
				gr.getVisualizer (actor).setColor ("green");
				gr.getVisualizer (movie).setColor ("green");
			}
		}

		//set the data structure handle, and visualize the input graph
		bridges.setDataStructure(gr);
		bridges.visualize();

		HashMap<String, String> mark = new HashMap<>(); 		//keeps track of visited nodes
		HashMap<String, String> parent = new HashMap<>(); 	//keeps track of parent nodes
		HashMap<String, Integer> dist = new HashMap<>(); 		// keeps track of distances

							// initialize maps for Bacon number algorithm
		for (Entry<String, Element<String> > v : gr.getVertices().entrySet() ) {
			mark.put(v.getKey(), "unvisited"); 
			parent.put(v.getKey(), "none");
			dist.put(v.getKey(), 0);
		}

		int d = getBaconNumber(gr, "Kevin_Bacon_(I)", "Cate_Blanchett", 
										mark, dist, parent);
		System.out.println ("Bacon Number[Kevin Bacon to Cate Blanchett: " + d);

		bridges.setDataStructure(gr);
		bridges.visualize();
	}

//
// Computes the Bacon Number of a an actor (#links that takes you from the 
// source actor to the destination actor. 
//
	public static int getBaconNumber (GraphAdjListSimple<String> 
					gr,   		// input graph 
					String src_actor, 						// source actor 
					String dest_actor,						// destination actor
					HashMap<String, String> mark, 	// mark array -- keep track of visited nodes
					HashMap<String, Integer> dist, 		// distances to src node 
					HashMap<String, String> parent) { 	// parent of node - for finding path 
															// back to source actor
	try {
		// Need to use a queue, as we are doing a BFS search
		LQueue<String> lq = new LQueue<String>();

		// add the source actor to the queue
		lq.enqueue(src_actor);
		// initializations
		mark.put(src_actor, "visited");
		dist.put(src_actor,  0);
		parent.put(src_actor, "none");

		// BFS traversal
		while (lq.length() > 0) {  // non empty queue
			String vertex = (String) lq.dequeue();

			// get adjacency list of vertex
			SLelement<Edge<String, String>> sl_list = gr.getAdjacencyList(vertex);
			for (SLelement<Edge<String, String>> sle = sl_list; sle != null; sle = 
												sle.getNext()){
				// get destination vertex
				String w = ((Edge<String, String>)sle.getValue()).getVertex();

				// if unvisited, mark it as visited and add to queue,
				// increment distance and point point parent back to vertex name
				if (mark.get(w).equals("unvisited")){
					mark.put(w, "visited");
					lq.enqueue(w);
									// update the distance and parent values
					dist.put(w, dist.get(vertex)+1);
					parent.put(w, vertex);

					// if we found the destination actor, then we are done
					// we want to highlight the actor and the path to the source
					// actor; we use the parent links to retrace (note source actor's
					// parent is "none" for us to stop)
					if (w.equals(dest_actor)) {  // found it
											// highlight actor and path using
											// the parent values and the hashmap
											// to trace back the sequence of nodes
											// to the source node
							
						// optional, we are deemphasizing the graph nodes by adjusting opacity	
	/*
						Element<String> *el, *el2;
						for (auto& v : *gr.getVertices()) {
							el = (Element<String>*) v.second;
							el.getVisualizer().setOpacity(0.5f);
						}
	*/
							
						// highlight the nodes and the links in the path from the source
						// to the destination actor
						String p = dest_actor;
						while (!p.equals("none")) {

							//  color the nodes in the path
							//  example: gr.getVisualizer(key-val).setColor("red")
							gr.getVisualizer(p).setColor("red");
							gr.getVisualizer(p).setSize(50);
							gr.getVisualizer(p).setOpacity(1.0f);

							// next, color the link, but check the parent is not "none", else you will
							// get an exception
							//  example: gr.getLinkVisualizer(src-key, dest-key).setColor("red")
							String par = parent.get(p);
							if (!par.equals("none")) {
								gr.getLinkVisualizer(p, par).setColor("red");
								gr.getLinkVisualizer(p, par).setThickness(10.);
							}	
							p = par;
						}
						// return the bacon number of this pair of actors
						return dist.get(dest_actor);
					}
				}
			}
		}
	}
	catch (Exception exc) {
		// TODO
	}
		return -1;
	} // getBaconNumber
} // class BaconNumber
