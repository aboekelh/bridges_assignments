#include "Bridges.h"
#include "Array.h"

using namespace bridges;

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(0, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("Array Example");

	//  set array dimensions, allocate array of elements
	int dims[3] = {10, 1, 1};
	Array<int> *arr = new Array<int>(1, dims);

	// populate the array, with squares of indices
	for (int j = 0; j < dims[0]; j++) {
		arr->getValue(j).setValue(j*j);
		arr->getValue(j).setLabel(to_string(arr->getValue(j).getValue()));
	}

	// color the array elements
	arr->getValue(0).getVisualizer()->setColor(Color("red"));
	arr->getValue(1).getVisualizer()->setColor(Color("green"));
	arr->getValue(2).getVisualizer()->setColor(Color("blue"));
	arr->getValue(3).getVisualizer()->setColor(Color("cyan"));
	arr->getValue(4).getVisualizer()->setColor(Color("magenta"));
	arr->getValue(5).getVisualizer()->setColor(Color("yellow"));
	arr->getValue(6).getVisualizer()->setColor(Color("red"));
	arr->getValue(7).getVisualizer()->setColor(Color("green"));
	arr->getValue(8).getVisualizer()->setColor(Color("blue"));
	arr->getValue(9).getVisualizer()->setColor(Color("black"));

	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(arr);

	// visualize the list
	Bridges::visualize();

	return 0;
}
