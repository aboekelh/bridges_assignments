import bridges.connect.Bridges;
import bridges.base.Array;
import bridges.base.Element;

public class Setup {
	public static void main(String[] args) throws Exception {

    /* Initialize a Bridges connection with your credentials */
		Bridges bridges = new Bridges(0, "YOUR_API_KEY", "YOUR_USER_ID");

		/* Set an assignment title */
		bridges.setTitle("Array Example");

		/* Set up the array dimensions, allocate an Array of Elements */
    int arraySize = 10;
		int[] dims = { arraySize, 1, 1 };
		Array<Integer> arr = new Array<Integer> (1, dims);

		/* Populate the array with integers */
		for (int k = 0; k < arr.getSize(); k++) {
			arr.setValue(k, new Element<Integer>(String.valueOf(k),k*k));
		}

    /* Tell BRIDGES which data structure to visualize */
		bridges.setDataStructure(arr);

		/* Visualize the Array */
	  bridges.visualize();
  }
}
