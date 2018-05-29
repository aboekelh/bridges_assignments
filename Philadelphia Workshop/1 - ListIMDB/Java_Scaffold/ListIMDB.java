import java.util.List;

/* Import Bridges and relevant data source */
import bridges.connect.Bridges;
import bridges.base.SLelement;
import bridges.data_src_dependent.ActorMovieIMDB;

public class ListIMDB {

	public static void main(String[] args) throws Exception {

		/* Initialize a Bridges connection with your credentials */
		Bridges bridges = new Bridges(1, "bridges_workshop", "1298385986627");

		/* Set an assignment title */
		bridges.setTitle("ListIMDB Example");

		/* Get a List of ActorMovieIMDB objects from Bridges */
		List<ActorMovieIMDB> mylist = bridges.getActorMovieIMDBData("imdb", Integer.MAX_VALUE);

		/* printing all actor movie pair for debuggging purposes*/
		for(int i = 0; i < mylist.size(); i++) {
		    ActorMovieIMDB im = mylist.get(i);
		    System.out.println(im.getActor()+ " - "+ im.getMovie());
		}

		
		
		/* Set up a prev and head element */
		/*TODO: Build a list of all actor movie pair*/
		SLelement<Integer> head = new SLelement<Integer>();
		SLelement<Integer> sec = new SLelement<Integer>();
		head.setValue(0);
		head.setLabel("First");
		sec.setValue(1);
		sec.setLabel("Second");
		head.setNext(sec);

		/* Pass the head of the list to Bridges */
		bridges.setDataStructure(head);

		/* Visualize the list */
		bridges.visualize();
	}
}
