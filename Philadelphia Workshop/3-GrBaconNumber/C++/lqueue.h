// From the software distribution accompanying the textbook
// "A Practical Introduction to Data Structures and Algorithm Analysis,
// Third Edition (C++)" by Clifford A. Shaffer.
// Source code Copyright (C) 2007-2011 by Clifford A. Shaffer.

// This is the file to include in your code if you want access to the
// complete LQueue template class

// Modified to use Bridges elements

#include "SLelement.h"

// First, get the declaration for the base stack class
#include "queue.h"

using namespace bridges;
// Implementations for linked queue function members
// Linked queue implementation
template <typename E> class LQueue: public Queue<E> {
private:
  SLelement<E>* front;      // Pointer to front queue node
  SLelement<E>* rear;       // Pointer to rear queue node
  int size;					// Number of elements in queue


  // Assert: If "val" is false, print a message and terminate
  // the program
  static void Assert(bool val, string s) {
    if (!val) { // Assertion failed -- close the program
      cout << "Assertion Failed: " << s << endl;
      exit(-1);
    }
  }

public:
  LQueue(int sz = 10) // Constructor 
    { front = rear = new SLelement<E>(); size = 0; }

  ~LQueue() { clear(); delete front; }      // Destructor

  void clear() {           // Clear queue
    while(front->getNext() != NULL) { // Delete each link node
      rear = front;
      delete rear;
    }
    rear = front;
    size = 0;
  }

  void enqueue(const E& it) { // Put element on rear
    rear->setNext(new SLelement<E>(NULL, it) );
    rear = rear->getNext();
    size++;
  }

  E dequeue() {              // Remove element from front
    Assert(size != 0, "Queue is empty");
    E it = front->getNext()->getValue();  // Store dequeued value
    SLelement<E>* ltemp = front->getNext(); // Hold dequeued link
    front->setNext(ltemp->getNext());       // Advance front
    if (rear == ltemp) rear = front; // Dequeue last element
    delete ltemp;                    // Delete link
    size --;
    return it;                       // Return element value
  }

  const E frontValue() const { // Get front element
    Assert(size != 0, "Queue is empty");
    return front->getNext()->getValue();
  }

  virtual int length() const { return size; }
};
