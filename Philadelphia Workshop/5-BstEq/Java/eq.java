
//
// SimpleDriver.java : Driver to illustrate importing USGS earthquake data
// 
import bridges.connect.Bridges;
import bridges.base.BSTElement;
import bridges.data_src_dependent.EarthquakeUSGS;
import bridges.data_src_dependent.Tweet;
import bridges.data_src_dependent.USGSaccount;
import java.util.List;

public class eq {
	public static final int maxElements = 1000; //number of tweets

	public static void main(String[] args) throws Exception{
	
    							// Instantiate a Bridges object */
//		Bridges bridges = new Bridges (5, "bridges_workshop", "1298385986627");
		Bridges bridges = new Bridges (5, "kalpathi60", "486749122386");


		bridges.setTitle("Recent Earthquakes(USGIS Data)");

		
								// Retrieve a list of (maxElements) Tweets */	
		List<EarthquakeUSGS> eqlist = Bridges.getEarthquakeUSGSData(maxElements );
		BST<Double, EarthquakeUSGS>  bst = new BST<Double, EarthquakeUSGS>();
		for ( int i = 0; i < eqlist.size(); i++ ){
			if (eqlist.get(i).getMagnitude() > 3.)
				bst.insert(eqlist.get(i).getMagnitude(), eqlist.get(i));

		}
		// To do: use the list of earthquake objects and insert them into
		// a binary search tree (use the BST API); use the magnitude as
		// search key

		// Set each node's element with the various attributes of the 
		// earthquake; in the visualization the mousing over the element will
		// reveal this label

		bst.findAndHighlightQuakeRange(bst.getTreeRoot(), 0.0f, 8.0f);
		
		bridges.setDataStructure(bst.getTreeRoot());
//		bridges.getVisualizer().setVisualizeJSON(true);
		//* Read each Tweet returned from Bridges
//		System.out.println("\n\n\n------Printing Earthquakes-------\n");
//		for ( int i = 0; i < eqlist.size(); i++ ){
//			System.out.println(eqlist.get(i));
//		}
		bridges.visualize();
    }
}
