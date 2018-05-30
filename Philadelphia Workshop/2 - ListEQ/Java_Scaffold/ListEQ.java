import java.util.List;

/* Import Bridges and relevant data source */
import bridges.connect.Bridges;
import bridges.base.SLelement;
import bridges.data_src_dependent.EarthquakeUSGS;

public class ListEQ {

	/* Given an SLelement with an EarthquakeUSGS value, set various visual properties */
	public static void setProperties(SLelement<EarthquakeUSGS> el) {
	    /*TODO: set the properties on the element based on location, magnitude of the earthquake */
	}

	public static void main(String[] args) throws Exception {

		/* Initialize a Bridges connection with your credentials */
		Bridges bridges = new Bridges(2, "bridges_workshop", "1298385986627");

    /* Set an assignment title */
    bridges.setTitle("ListEQ Example");

		/* Get a List of USGS Earthquake Tweet objects from Bridges */
		List<EarthquakeUSGS> mylist = bridges.getEarthquakeUSGSData(100);

		/* Set up a prev and head element */
		SLelement<EarthquakeUSGS> prev = new SLelement<EarthquakeUSGS>();
		SLelement<EarthquakeUSGS> head = new SLelement<EarthquakeUSGS>();

		/* Read each actor movie pair and set up a new SLelement for each */
		for(int i = 0; i < mylist.size(); i++) {

			/* Create each new SLelement */
			SLelement<EarthquakeUSGS> element = new SLelement<EarthquakeUSGS>(mylist.get(i));

			/* Set the element label equal to Title (print it out for sanity check) */
			element.setLabel(mylist.get(i).getTitle());
			System.out.println(element.getLabel());

			/* Pass the element to a function to set its visual properties */
			setProperties(element);

			/* Add 'next' pointer where appropriate */
			if(i > 0) {
				prev.setNext(element);
			} else {
				/* Set the head pointer */
				head = element;
			}
			/* Update the prev pointer */
			prev = element;
		}

		/* Pass the head of the list to Bridges */
		bridges.setDataStructure(head);

		/* Visualize the list */
		bridges.visualize();
	}
}
