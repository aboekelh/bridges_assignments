package assignment_test;

import java.util.List;
import java.util.Random;

/* Import Bridges and relevant data source */
import bridges.connect.Bridges;
import bridges.base.SLelement;
import bridges.data_src_dependent.ActorMovieIMDB;

public class ListIMDB {

	public static void main(String[] args) throws Exception {
		
		/* Initialize a Bridges connection with your credentials */
		Bridges bridges = new Bridges(87, "873711046824", "testy");	
		bridges.setServer("local");
		
		/* Get a List of ActorMovieIMDB objects from Bridges */
		List<ActorMovieIMDB> mylist = bridges.getActorMovieIMDBData("imdb", Integer.MAX_VALUE);

		SLelement<ActorMovieIMDB> prev = new SLelement<ActorMovieIMDB>();
		SLelement<ActorMovieIMDB> head = new SLelement<ActorMovieIMDB>();
		
		for(int i = 0; i < mylist.size(); i++) {
			
			SLelement<ActorMovieIMDB> element = new SLelement<ActorMovieIMDB>();
			element.setLabel(mylist.get(i).getActor() + " - " + mylist.get(i).getMovie());
			
			System.out.println(element.getLabel());
			
			if(i > 0) {
				prev.setNext(element);
			} else {
				head = element;
			}
			
			prev = element;
		}	
		
		bridges.setDataStructure(head);
		bridges.visualize();
	}
}