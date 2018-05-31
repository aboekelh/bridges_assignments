package book;

import java.util.Scanner;
import bridges.connect.Bridges;
import bridges.base.BSTElement;
import bridges.data_src_dependent.GutenbergBook;
import java.util.ArrayList;

public class BookDriver {

	public static void main(String[] args)throws Exception {
		
		Bridges bridges = new Bridges(10, "806007046050", "quai");
	    bridges.setTitle("GutenBerg Book Collection BST");
	    
        BST<String, GutenbergBook> bst = new BST<String, GutenbergBook>();
        
        ArrayList<GutenbergBook> book_list = 
                (ArrayList<GutenbergBook>) Bridges.getGutenbergBookMetaData();
        
        for ( int i = 0; i < 400; i++ ){
            bst.insert(Integer.toString(book_list.get(i).getNumWords()), book_list.get(i));
        }
        
        bridges.setDataStructure(bst.getTreeRoot());
                            // visualize the tree
        bridges.visualize();

	}

}
