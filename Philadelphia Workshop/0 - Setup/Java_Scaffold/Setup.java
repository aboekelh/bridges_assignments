import bridges.connect.Bridges;
import bridges.base.Array;
import bridges.base.Element;

public class Setup {
	public static void main(String[] args) throws Exception {

	    /* Initialize a Bridges connection with your credentials */
	    /* TODO: plug your own BRIDGES credentials */
	    Bridges bridges = new Bridges(0, "YOUR_API_KEY", "YOUR_USER_ID");

	    /* Set an assignment title */
	    bridges.setTitle("Array Example");

	    /* Set up the array dimensions, allocate an Array of Elements */
	    /* TODO: Make an array of size 10 */
	    int arraySize = 2;
	    Array<Integer> arr = new Array<Integer> (2);
	    
	    /* Populate the array with integers */
	    /* TODO: Make the array store square numbers*/
	    arr.getElement(0).setValue (0);
	    /* set the value as a Label */
	    arr.getElement(0).setLabel(String.valueOf(0));
	    
	    /* Tell BRIDGES which data structure to visualize */
	    bridges.setDataStructure(arr);

	    /* Visualize the Array */
	    bridges.visualize();
	}
}
