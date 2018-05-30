/** Source code example for "A Practical Introduction to Data
    Structures and Algorithm Analysis, 3rd Edition (Java)" 
    by Clifford A. Shaffer
    Copyright 2008-2011 by Clifford A. Shaffer
*/

import java.lang.Comparable;

import bridges.base.BSTElement;
import bridges.data_src_dependent.EarthquakeUSGS;

		// Binary Search Tree implementation for Dictionary ADT 
class BST<Key extends Comparable<? super Key>, E>
         implements Dictionary<Key, E> {
  private BSTElement<Key, E> root; // Root of the BST
  int nodecount;             // Number of nodes in the BST

		// Constructor
  BST() { 
	root = null; nodecount = 0; 
  }

  public BSTElement<Key, E> getTreeRoot() {
	return root;
  }
  		// Reinitialize tree 
  public void clear() { root = null; nodecount = 0; }

		// Insert a record into the tree.
		// @param k Key value of the record.
		//@param e The record to insert.
  public void insert(Key k, E e) {
    root = inserthelp(root, k, e);
    nodecount++;
  }

  		/** Remove a record from the tree.
      		@param k Key value of record to remove.
      		@return The record removed, or null if there is none. */
  public E remove(Key k) {
    E temp = findhelp(root, k);   // First find it
    if (temp != null) {
      root = removehelp(root, k); // Now remove it
      nodecount--;
    }
    return temp;
  }

  private E findhelp(BSTElement<Key,E> rt, Key k) {
  	if (rt == null) return null;
  	if (rt.getKey().compareTo(k) > 0)
    	return findhelp(rt.getLeft(), k);
  	else if (rt.getKey().compareTo(k) == 0) return rt.getValue();
  	else return findhelp(rt.getRight(), k);
  }
  private BSTElement<Key,E> inserthelp(BSTElement<Key,E> rt, Key k, E e) {
    if (rt == null) {
		BSTElement<Key, E> n = new BSTElement<Key, E>(k, e);
		EarthquakeUSGS eq = (EarthquakeUSGS) n.getValue();
		n.setLabel (eq.getTitle() + "\n\n" + eq.getTime());
		
	  return n;
    }
    if (rt.getKey().compareTo(k) > 0)
      rt.setLeft(inserthelp(rt.getLeft(), k, e));
    else
      rt.setRight(inserthelp(rt.getRight(), k, e));
    return rt;
  }
  /** Remove a node with key value k
      @return The tree with the node removed */
  private BSTElement<Key,E> removehelp(BSTElement<Key,E> rt, Key k) {
    if (rt == null) return null;
    if (rt.getKey().compareTo(k) > 0)
      rt.setLeft(removehelp(rt.getLeft(), k));
    else if (rt.getKey().compareTo(k) < 0)
      rt.setRight(removehelp(rt.getRight(), k));
    else { // Found it
      if (rt.getLeft() == null)
        return rt.getRight();
      else if (rt.getRight() == null)
        return rt.getLeft();
      else { // Two children
        BSTElement<Key,E> temp = getmin(rt.getRight());
        rt.setValue(temp.getValue());
	    rt.setLabel(String.valueOf(temp.getKey()));
        rt.setKey(temp.getKey());
        rt.setRight(deletemin(rt.getRight()));
      }
    }
    return rt;
  }
  private BSTElement<Key,E> getmin(BSTElement<Key,E> rt) {
    if (rt.getLeft() == null)
      return rt;
    else return getmin(rt.getLeft());
  }
  private BSTElement<Key,E> deletemin(BSTElement<Key,E> rt) {
    if (rt.getLeft() == null)
      return rt.getRight();
    else {
      rt.setLeft(deletemin(rt.getLeft()));
      return rt;
    }
  }
  private void printhelp(BSTElement<Key,E> rt) {
    if (rt == null) return;
    printVisit(rt.getValue(), rt.getKey());
    printhelp(rt.getLeft());
    printhelp(rt.getRight());
  }

  private StringBuffer out;

  public String toString() {
    out = new StringBuffer(100);
    printhelp(root);
    return out.toString();
  }
  private void printVisit(E it, Key k) {
    out.append("[" + k + "," + it + "]");
  }


  /** Remove and return the root node from the dictionary.
      @return The record removed, null if tree is empty. */
  public E removeAny() {
    if (root != null) {
      E temp = root.getValue();
      root = removehelp(root, root.getKey());
      nodecount--;
      return temp;
    }
    else return null;
  }

  /** @return Record with key value k, null if none exist.
      @param k The key value to find. */
  public E find(Key k) { return findhelp(root, k); }

  /** @return The number of records in the dictionary. */
  public int size() { return nodecount; }

  public void updateLabels(BSTElement<Key, E>  rt) {
	if (rt == null)
		return;
	EarthquakeUSGS eq = (EarthquakeUSGS) rt.getValue();
	rt.setLabel(eq.getTitle() + eq.getDate());
				// recurse
	updateLabels(rt.getLeft());
	updateLabels(rt.getRight());
  }
	
					// find largest
	public BSTElement<Key, E>  findLargest(BSTElement<Key,E>  root) {
		if (root == null)
			return null;
		else if (root.getRight() == null)
			return root;
		else  {
			return findLargest(root.getRight());
		}
    }
	public BSTElement<Key, E>  findSmallest(BSTElement<Key,E>  root) {
		if (root == null)
			return null;
		else if (root.getLeft() == null)
			return root;
		else  {
			return findSmallest(root.getLeft());
		}
    }
	public void resetToDefaultColors(BSTElement<Key,E>  root) {
		if (root != null) {
			root.getVisualizer().setColor("blue");
			resetToDefaultColors(root.getLeft());
			resetToDefaultColors(root.getRight());
		}
	}
	public void findAndHighlightByLocation(BSTElement<Key, E> root, String location){
		if (root != null) {
					// compare location of quake
			String loc = ((EarthquakeUSGS) root.getValue()).getLocation();
			if (loc.toLowerCase().contains(location.toLowerCase()) )// matches
				root.getVisualizer().setColor("red");
			findAndHighlightByLocation(root.getLeft(), location);
			findAndHighlightByLocation(root.getRight(), location);
		}
	}

	public void scaleByMagnitude (BSTElement<Key, E> root) {
							// size mapping: from quake range 0-10 to 0-30
		if (root != null) {
			float magn = (float) ((EarthquakeUSGS)root.getValue()).getMagnitude();
			root.getVisualizer().setSize((int) (magn*5));

			scaleByMagnitude (root.getLeft());
			scaleByMagnitude (root.getRight());
		}
	}
	public void findAndHighlightQuakeRange(BSTElement<Key, E> root, 
								float min_r, float max_r) {
		if (root != null) {
			float magn = (float) ((EarthquakeUSGS)root.getValue()).getMagnitude();
		//	if (min_r <= magn && magn <= max_r)	 // in range
		//		root.getVisualizer().setColor("magenta");
			if (magn >= 3.0 && magn < 4.0)
				root.getVisualizer().setColor("green");
			else if (magn >= 4.0 && magn < 5.0)
				root.getVisualizer().setColor("orange");
			else if (magn >= 5.0 && magn < 6.0)
				root.getVisualizer().setColor("magenta");
			else 
				root.getVisualizer().setColor("red");
			
			findAndHighlightQuakeRange(root.getLeft(), min_r, max_r);
			findAndHighlightQuakeRange(root.getRight(), min_r, max_r);
		}
	}

/*
	public void findAndHighlightByDate(BSTElement<Key, E> root, String month, int year){
		if (root != null) {
					// compare location of quake
			String mon_tree = ((EarthquakeUSGS) root.getValue()).getMonth();
			Integer y_tree = ((EarthquakeUSGS) root.getValue()).getYear();
			if (mon_tree.toLowerCase().contains(month.toLowerCase()) 
								&& (year == y_tree))// matches
				root.getVisualizer().setColor("red");
			findAndHighlightByDate(root.getLeft(), month, year);
			findAndHighlightByDate(root.getRight(), month, year);
		}
	}
*/
}
