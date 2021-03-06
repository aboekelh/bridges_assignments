import bridges.connect.Bridges;
import bridges.base.Array;
import bridges.base.Element;

public class Setup {
	public static void main(String[] args) throws Exception {

		/* Initialize a Bridges connection with your credentials */
		Bridges bridges = new Bridges(0, "bridges_workshop", "1298385986627");

		/* Set an assignment title */
		bridges.setTitle("Array Example");

		/* Set up the array dimensions, allocate an Array of Elements */
		int arraySize = 10;
		Array<Integer> arr = new Array<Integer> (10);

		/* Populate the array with integers */
		for (int k = 0; k < arr.getSize(); k++) {
			arr.getElement(k).setValue(k*k);
			arr.getElement(k).setLabel(String.valueOf(k*k));
		}

		/* Tell BRIDGES which data structure to visualize */
		bridges.setDataStructure(arr);

		/* Visualize the Array */
		bridges.visualize();
	}
}
