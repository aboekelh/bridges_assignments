#include "Bridges.h"
#include "Array.h"

using namespace bridges;

int main() {

	//create the Bridges object, set credentials
	Bridges::initialize(0, "bridges_workshop", "1298385986627");
	
	Bridges::setTitle("Array Example");

	//  allocate array of 10elements
	Array<int> *arr = new Array<int>(10);

	// populate the array, with squares of indices
	for (int j = 0; j < 10; j++) {
		arr->getElement(j).setValue(j*j);
		arr->getValue(j).setLabel(to_string(arr->getElement(j).getValue()));
	}

	// color the array elements
	arr->getElement(0).getVisualizer()->setColor(Color("red"));
	arr->getElement(1).getVisualizer()->setColor(Color("green"));
	arr->getElement(2).getVisualizer()->setColor(Color("blue"));
	arr->getElement(3).getVisualizer()->setColor(Color("cyan"));
	arr->getElement(4).getVisualizer()->setColor(Color("magenta"));
	arr->getElement(5).getVisualizer()->setColor(Color("yellow"));
	arr->getElement(6).getVisualizer()->setColor(Color("red"));
	arr->getElement(7).getVisualizer()->setColor(Color("green"));
	arr->getElement(8).getVisualizer()->setColor(Color("blue"));
	arr->getElement(9).getVisualizer()->setColor(Color("black"));

	// tell Bridges what data structure to visualize
	Bridges::setDataStructure(arr);

	// visualize the list
	Bridges::visualize();

	return 0;
}
