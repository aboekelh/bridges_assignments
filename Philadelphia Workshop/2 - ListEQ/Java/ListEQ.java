import java.util.List;

/* Import Bridges and relevant data source */
import bridges.connect.Bridges;
import bridges.base.SLelement;
import bridges.data_src_dependent.EarthquakeUSGS;

public class ListEQ {

	/* Given an SLelement with an EarthquakeUSGS value, set various visual properties */
	public static void setProperties(SLelement<EarthquakeUSGS> el) {

		/* Get the magnitude of the earthquake from the SLelement
		 *   (the EarthquakeUSGS object is stored as the value inside the SLelement)
		 */
		double magnitude = el.getValue().getMagnitude();

		/* Prepare color and size variables */
		String color = "";
		Float size = 0.0f;

		/* Pick the color depending on the magnitude */
		if(magnitude < 1.0) {
			color = "blue";
		} else if(magnitude < 2.0) {
			color = "green";
		} else if(magnitude < 3.0) {
			color = "yellow";
		} else if(magnitude < 4.0) {
			color = "orange";
		} else if(magnitude < 5.0) {
			color = "red";
		} else if(magnitude < 6.0) {
			color = "purple";
		} else {
			color = "black";
		}
		/* Set the color of the Element */
		el.getVisualizer().setColor(color);


		/* Set the size of the Element based on the magnitude */
		el.getVisualizer().setSize(magnitude*5);

		/* Set the shape of the Element based on the location */
		if(el.getValue().getLocation().contains("Alaska")) {
			double maybe = Math.random();
			if(maybe < 0.5)
				el.getVisualizer().setShape("star");
			else
				el.getVisualizer().setShape("wye");
		}
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
