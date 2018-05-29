#include "Bridges.h"
#include "Array.h"

using namespace bridges;

int main() {

  //create the Bridges object, set credentials
  /* TODO: Use your own credentials */
  Bridges::initialize(100, "bridges_workshop", "1298385986627");
  
  Bridges::setTitle("Array Example");
  
  //  set array dimensions, allocate array of elements
  /* TODO: set  an array of size 10 */
  int dims[3] = {2, 1, 1}
  Array<int> *arr = new Array<int>(1, dims);
  
  // populate the array, with squares of indices
  /* TODO: make  the array show the first 10 square numbers */
  arr->getValue(0).setValue(0);
  arr->getValue(0).setLabel("0");
  arr->getValue(1).setValue(1);
  arr->getValue(1).setLabel("1");
     
  // tell Bridges what data structure to visualize
  Bridges::setDataStructure(arr);
  
  // visualize the list
  Bridges::visualize();
  
  return 0;
}
