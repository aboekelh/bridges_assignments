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
	    int[] dims = { arraySize, 1, 1 };
	    Array<Integer> arr = new Array<Integer> (1, dims);
	    
	    /* Populate the array with integers */
	    /* TODO: Make the array store square numbers*/
	    arr.setValue(0, new Element<Integer>("0",0));
	    arr.setValue(1, new Element<Integer>("1",1));
	    
	    /* Tell BRIDGES which data structure to visualize */
	    bridges.setDataStructure(arr);

	    /* Visualize the Array */
	    bridges.visualize();
	}
}
