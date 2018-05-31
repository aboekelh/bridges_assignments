package book;

import java.util.Scanner;
import java.util.Scanner;
import bridges.connect.Bridges;
import bridges.base.BSTElement;
import bridges.data_src_dependent.GutenbergBook;
import java.util.ArrayList;
import bridges.base.Array;
import bridges.base.Element;

public class BookPQDriver {
	static Task[] theHeap;
	public static void main(String[] args)throws Exception {
				
		Bridges bridges = new Bridges(11, "806007046050", "quai");
	    bridges.setTitle("GutenBerg Book Collection BST");
	            
        ArrayList<GutenbergBook> book_list = 
                (ArrayList<GutenbergBook>) Bridges.getGutenbergBookMetaData();
		
        Scanner scan = new Scanner(System.in);
        System.out.println("Priority Queue Test\n");   
 
        System.out.println("Enter size of priority queue ");
        BookPQ pq = new BookPQ(scan.nextInt() );
        

        char ch;
        int count = 0;
        /*  Perform Priority Queue operations */
        do    
        {
            System.out.println("\nPriority Queue Operations\n");
            System.out.println("1. insert");
            System.out.println("2. remove");
            System.out.println("3. check empty");
            System.out.println("4. check full");
            System.out.println("5. clear");
            System.out.println("6. size");
 
            int choice = scan.nextInt();            
            switch (choice) 
            {
            case 1 : 
            	for (int i = 0; i < 10; i++) {
            		System.out.println("Book inerting.."); 
            		theHeap = pq.insert(book_list.get(count).getTitle(), book_list.get(count).getNumWords());
                	System.out.println(book_list.get(count).getTitle() + " was added to the heap");
                	count++;
            	}
                break;                          
            case 2 : 
                System.out.println("\nJob removed \n\n"+ pq.remove()); 
                break;        
            case 3 : 
                System.out.println("\nEmpty Status : "+ pq.isEmpty() );
                break; 
            case 4 : 
                System.out.println("\nFull Status : "+ pq.isFull() );
                break; 
            case 5 : 
                System.out.println("\nPriority Queue Cleared");
                pq.clear();
                break;    
            case 6 : 
                System.out.println("\nSize = "+ pq.size() );
                break;         
            default : 
                System.out.println("Wrong Entry \n ");
                break;   
            }    
 
            System.out.println("\nDo you want to continue (Type y or n) \n");
            ch = scan.next().charAt(0);                        
        } while (ch == 'Y'|| ch == 'y');  
        
        int[] dims = {10, 1, 1};
        Array<Integer> my_array = new Array<Integer> (1, dims);
        for (int k = 0; k < pq.size(); k++){
           // my_array.setValue(k, new Element<Integer>(Integer.toString(theHeap[k+1].priority), theHeap[k+1].priority));
            my_array.setValue(k, new Element<Integer>(theHeap[k+1].job + " (Priority = " + theHeap[k+1].priority + ")", theHeap[k+1].priority));
            my_array.getValue(k).getVisualizer().setColor("red");
        }
        bridges.setDataStructure(my_array);
        bridges.visualize();
    }
}
