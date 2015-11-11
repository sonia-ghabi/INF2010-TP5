import java.util.*;

public class Main 
{
   /**
     * Fonction principale
     */
   public static void main(String[] args) 
   {
      // creer un monceau avec 22 elements et un tableau equivalent
      int numItems = 22;
      BinaryHeap<Integer> heap = new BinaryHeap<Integer>(true);
      
      Integer [ ] items = new Integer[ numItems ];

      int i;
      int j;

      // en inserant les elements un a un
      for( i = 11, j = 0; j != numItems; i = ( i + 37 ), j++ )
      {
    	  heap.offer( i );
    	  items[ j ] = i;

    	  i %=  numItems; 
      }

      // en construisant le monceau depuis le depart
      System.out.println("Monceau min contruit element par element:");
      System.out.println( heap.printFancyTree() );

      heap = new BinaryHeap<Integer>(false);
      // en inserant les elements un a un
      for( Integer item : items)
    	  heap.offer( item );

      // en construisant le monceau depuis le depart
      System.out.println("Monceau max contruit element par element:");
      System.out.println( heap.printFancyTree() );

      
      heap = new BinaryHeap<Integer>(items,false);
      System.out.println("Monceau max contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );
		
      
      heap = new BinaryHeap<Integer>(items,true);
      System.out.println("Monceau min contruit a partir d'un tableau:");
      System.out.println( heap.printFancyTree() );

      
      System.out.println();
      System.out.println("Affichage recursif:");
      System.out.println( heap.printFancyTree() );

      System.out.println("Affichage non recursif:");
      System.out.println( heap.nonRecursivePrintFancyTree() );

      System.out.println();
      System.out.println("Tableau d'origine:");
      System.out.println( printArray( items ) );
      
      BinaryHeap.heapSort( items );
      System.out.println("Tableau ordonne:");
      System.out.println( printArray( items ) );

      BinaryHeap.heapSortReverse( items );
      System.out.println("Tableau inversement ordonne:");
      System.out.println( printArray( items ) );
      System.out.print("\n");


      /*
       * Ajouter appels pour repondre a la question
       **/
      
      PriorityQueue<Integer> minQueue = new PriorityQueue<Integer>();
      PriorityQueue<Integer> maxQueue = new PriorityQueue<Integer>(20, Collections.reverseOrder());
      
      BinaryHeap<Integer> minHeap = new BinaryHeap<Integer>(true);
      BinaryHeap<Integer> maxHeap = new BinaryHeap<Integer>(false);
      
      
      //rempli tous les container avec 20 chiffres de 1 a 20
      for(int k = 1; k <= 20; k++)
      {
    	  maxHeap.offer(k);
    	  minHeap.offer(k);
    	  minQueue.offer(k);
    	  maxQueue.offer(k);
      }
      
      System.out.print("\n");
      
      //verifie que poll retire toujours le plus grand element dans un max heap
      //les element devraient etre imprimer en ordre decroissant
      System.out.println("Retirer tous les elements d'un max heap:");
      for(int k = 1; k <= 20; k++)
       	  System.out.print(maxHeap.poll()+", ");
      System.out.print("\n");
      //fait la meme chose avec un max queue pour voir si l'affichage concorde
      System.out.println("Retirer tous les elements d'un max queue:");
      for(int k = 1; k <= 20; k++)
       	  System.out.print(maxQueue.poll()+", ");
      System.out.print("\n\n");
      
      
      //verifie que poll retire toujours le plus petit element dans un min heap
      //les element devraient etre imprimer en ordre croissant
      System.out.println("Retirer tous les elements d'un min heap:");
      for(int k = 1; k <= 20; k++)
       	  System.out.print(minHeap.poll()+", ");
      System.out.print("\n");
      //fait la meme chose avec un min queue pour voir si l'affichage concorde
      System.out.println("Retirer tous les elements d'un min queue:");
      for(int k = 1; k <= 20; k++)
       	  System.out.print(minQueue.poll()+", ");
      System.out.print("\n\n");
      
      
      //test si l'iterateur detecte une modification si on ajoute un element
      System.out.println("Aouter un element au monceau pendant une iteration avec l'iterateur:");
      try
      {
      	for(int element: heap)
      	{
      		heap.offer(element);
      	}
      }
      catch(ConcurrentModificationException e)
      {
    	  System.out.println("ERREUR: aucun element ne peut etre ajoute au monceau pendant une iteration avec son iterateur\n");
      }
      
      //test si l'iterateur detecte une modification si on retire un element
      System.out.println("Retirer un element au monceau pendant une iteration avec l'iterateur:");
      try
      {
      	for(int element: heap)
      	{
      		heap.poll();
      	}
      }
      catch(ConcurrentModificationException e)
      {
    	  System.out.println("ERREUR: aucun element ne peut etre retire du monceau pendant une iteration avec son iterateur\n");
      }
      
      
      //verifie que hasNext detecte bien la fin du tableau
      Iterator<Integer> it = heap.iterator();
      System.out.println("Parcourir le monceau avec un iterateur jusqu'a depasser la fin:");
      try
      {
      	for(int k = 0; k < heap.size()+10; k++)
      	{
      		it.next();
      	}
      }
      catch(NoSuchElementException e)
      {
    	  System.out.println("ERREUR: on est arriver a la fin du monceau");
      }
      
   }
   

   private static <AnyType> 
   String printArray(AnyType[] a)
   {
      String outputString = "";

      for(AnyType item : a)
      {
         outputString += item;
         outputString += ", ";
      }

      return outputString.substring(0,outputString.length()-2);
   }
}
