import java.util.*;

public class BinaryHeap<AnyType extends Comparable<? super AnyType>> extends AbstractQueue<AnyType>
{
	private static final int DEFAULT_CAPACITY = 100;
	private int currentSize;      // Nombre d'elements
	private AnyType [ ] array;    // Tableau contenant les donnees (premier element a l'indice 1)
	private boolean min;
	private int modifications;    // Nombre de modifications apportees a ce monceau

	@SuppressWarnings("unchecked")
	public BinaryHeap( boolean min )
	{
		this.min = min;
		currentSize = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
	}

	@SuppressWarnings("unchecked")
	public BinaryHeap( AnyType[] items, boolean min )
	{
		this.min = min;
		currentSize = items.length;
		array = (AnyType[]) new Comparable[currentSize +1];
		for(int i = 1 ; i < array.length; i++)
		{
			array[i] = items[i-1];
		}

		// COMPLETEZ
		// invoquez buildMinHeap() ou buildMaxHeap() en fonction du parametre min;
		if(min)
		{
			buildMinHeap();
		}
		else 
		{
			buildMaxHeap();
		}
		
		
		
	}

	public boolean offer( AnyType x )
	{
		if (x == null)
			throw new NullPointerException("Cannot offer null in a BinaryHeap");

		if( currentSize + 1 == array.length )
			doubleArray();

		// COMPLETEZ
		int hole = ++currentSize;
		if(min)
		{
			for(; hole > 1 && x.compareTo(array[hole/2]) < 0 ; hole /= 2)
			{
				array[hole] = array[hole/2];
			}
		}
		else
		{
			for(; hole > 1 && x.compareTo(array[hole/2]) > 0 ; hole /= 2)
			{
				array[hole] = array[hole/2];
			}
		}
		array[hole] = x;
		modifications++;
		return true;
	}

	public AnyType peek()
	{
		if(!isEmpty())
			return array[1];

		return null;
	}

	
	public AnyType poll()
	{
		//COMPLETEZ
		if(array.length != 1)
		{
			AnyType head = array[1];
			array[1] = array[currentSize--];
			
			if(array.length > 1)
			{
				if(min)
				{
					percolateDownMinHeap(1, currentSize);
				}
				else 
				{
					percolateDownMaxHeap(1, currentSize);
				}
			}
			modifications++;
			return head;
		}
		return null;
	}
	

	public Iterator<AnyType> iterator()
	{
		return new HeapIterator();
	}

	private void buildMinHeap()
	{
		//COMPLETEZ
		for(int i = array.length/2 ; i > 0 ; i--)
		{
			percolateDownMinHeap(i, array.length);
		}
	}

	public void buildMaxHeap()
	{
		for(int i = array.length/2 ; i > 0 ; i--)
		{
			percolateDownMaxHeap(i, array.length);
		}
	}

	public boolean isEmpty()
	{
		return currentSize == 0;
	}

	public int size()
	{
		return currentSize;
	}

	public void clear()
	{
		currentSize = 0;
		modifications = 0;
		array = (AnyType[]) new Comparable[ DEFAULT_CAPACITY + 1];
	}

	
	private static int leftChild( int i, boolean heapIndexing )
	{
		//COMPLETEZ
		if(!heapIndexing)
		{
			return 2*i+1;
		}
		return 2*i;
	}
	

	private void swapReferences( int index1, int index2 )
	{
		swapReferences(array, index1, index2);
	}

	private static <AnyType extends Comparable<? super AnyType>>
	void swapReferences( AnyType[] array, int index1, int index2 )
	{
		AnyType tmp = array[ index1 ];
		array[ index1 ] = array[ index2 ];
		array[ index2 ] = tmp;
	}

	@SuppressWarnings("unchecked")
	private void doubleArray()
	{
		AnyType [ ] newArray;

		newArray = (AnyType []) new Comparable[ array.length * 2 ];
		for( int i = 0; i < array.length; i++ )
			newArray[ i ] = array[ i ];
		array = newArray;
	}


	/**
	 * @param hole    Position a percoler
	 * @param size    Indice max du tableau
	 */
	private void percolateDownMinHeap( int hole, int size )
	{
		percolateDownMinHeap(array, hole, size, true);
	}

	/**
	 * @param array   Tableau d'element
	 * @param hole    Position a percoler
	 * @param size    Indice max du tableau
	 * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
	 */
	private static <AnyType extends Comparable<? super AnyType>>
	void percolateDownMinHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
	{
		int child;
		// On initialise tmp au noeud qui a la position percoler
		AnyType tmp = array[hole];
		
		// Tant que la position a percoler a un fils
		while(leftChild(hole, heapIndexing) < size)
		{
			// On recupere la position du fils
			child = leftChild(hole, heapIndexing);
			// Si le noeud a percoler a 2 fils, on les compare
			// Et si le fils gauche est inferieur au droit, on place child sur le fils de droit
			if(child != size-1 && array[child].compareTo(array[child+1]) > 0)
			{
				child++;
			}
			// Si tmp est plus petit que le noeud fils, on remplace 
			if(tmp.compareTo(array[child]) > 0)
			{
				array[hole] = array[child];
			}
			else
			{
				break;
			}
			// On deplace le noeud a percoler sur son fils
			hole = child;
		}
		// On replace tmp
		array[hole] = tmp;
	}

	/**
	 * @param hole    Position a percoler
	 * @param size    Indice max du tableau
	 */
	private void percolateDownMaxHeap( int hole, int size )
	{
		//if(!min) 
		{
			percolateDownMaxHeap(array, hole, size, true);
		}
		/*
		else
		{
			percolateDownMinHeap(array, hole, size, true);
		}
		*/
	}

	/**
	 * @param array         Tableau d'element
	 * @param hole          Position a percoler
	 * @param size          Indice max du tableau
	 * @param heapIndexing  True si les elements commencent a l'index 1, false sinon
	 */
	private static <AnyType extends Comparable<? super AnyType>> 
	void percolateDownMaxHeap( AnyType[] array, int hole, int size, boolean heapIndexing )
	{
		int child;
		// On initialise tmp au noeud qui a la position percoler
		AnyType tmp = array[hole];
		
		// Tant que la position a percoler a un fils
		while(leftChild(hole, heapIndexing) < size)
		{
			// On recupere la position du fils
			child = leftChild(hole, heapIndexing);
			// Si le noeud a percoler a 2 fils, on les compare
			// Et si le fils gauche est inferieur au droit, on place child sur le fils de droit
			if(child != size-1 && array[child].compareTo(array[child+1]) < 0)
			{
				child++;
			}
			// Si tmp est plus petit que le noeud fils, on remplace 
			if(tmp.compareTo(array[child]) < 0)
			{
				array[hole] = array[child];
			}
			else
			{
				break;
			}
			// On deplace le noeud a percoler sur son fils
			hole = child;
		}
		// On replace tmp
		array[hole] = tmp;
	}

	public static <AnyType extends Comparable<? super AnyType>>
	void heapSort( AnyType[] a )
	{
		//COMPLETEZ
		for( int i = a.length / 2; i >= 0; i--)
		{
			// Construction du monceau
			BinaryHeap.percolateDownMaxHeap(a, i, a.length, false);
		}
		for( int i = a.length - 1; i > 0; i-- ) 
		{
			// Permutation du maximum (racine) avec le dernier élément du monceau
			BinaryHeap.swapReferences(a, 0, i); 
			BinaryHeap.percolateDownMaxHeap(a, 0, i, false);
		}
	}

	public static <AnyType extends Comparable<? super AnyType>>
	void heapSortReverse( AnyType[] a )
	{
		//COMPLETEZ
		for( int i = a.length / 2; i >= 0; i--)
		{
			// Construction du monceau
			BinaryHeap.percolateDownMinHeap(a, i, a.length, false);
		}
		for( int i = a.length - 1; i > 0; i-- ) 
		{
			// Permutation du minimum (racine) avec le dernier élément du monceau
			BinaryHeap.swapReferences(a, 0, i); 
			BinaryHeap.percolateDownMinHeap(a, 0, i, false);
		}
	}

	public String nonRecursivePrintFancyTree()
	{
		//COMPLETEZ
		String outputString = "";
		
		outputString += array[1].toString() + "\n";
		int i = 2;
		while(i < currentSize)
		{
			for(int j = i*2 ; i < currentSize; i*=2)
			{
				outputString += array[j].toString() + "\n";
			}
			if(leftChild(i, true) < currentSize)
			{
				i = 2 * i;
			}
			else if(i+1 < currentSize)
			{
				i++;
			}
			else
			{
				i = (i+1)/2;
			}

		}
		/*
		i = currentSize;
		while(i > 2)
		{
			outputString += array[i].toString() + "\n";
			i -= 2;
		}
		*/
		return outputString;
	}

	public String printFancyTree()
	{
		return printFancyTree(1, "");
	}

	private String printFancyTree( int index, String prefix)
	{
		String outputString = "";

		outputString = prefix + "|__";

		if( index <= currentSize )
		{
			boolean isLeaf = index > currentSize/2;

			outputString += array[ index ] + "\n";

			String _prefix = prefix;

			if( index%2 == 0 )
				_prefix += "|  "; // un | et trois espace
			else
				_prefix += "   " ; // quatre espaces

			if( !isLeaf ) {
				outputString += printFancyTree( 2*index, _prefix);
				outputString += printFancyTree( 2*index + 1, _prefix);
			}
		}
		else
			outputString += "null\n";

		return outputString;
	}
	
	public class HeapIterator implements Iterator<AnyType> { 
	    private int index;
	    private int modificationsIterator;

	    public HeapIterator() {
	        index = 0;
	        modificationsIterator = modifications;
	        
	    }

	    @Override
	    public AnyType next() throws NoSuchElementException, 
		ConcurrentModificationException, 
		UnsupportedOperationException {
	        if(hasNext() && modificationsIterator == modifications) {
	            return (AnyType) array[++index];
	        } 
	        else if(hasNext() == false)
	        {
	        	throw new NoSuchElementException();
	        }
	        else {
	            throw new ConcurrentModificationException();
	        }
	    }

	    @Override
	    public boolean hasNext()
	    {
	        return (index < currentSize);
	    }

	    @Override
	    public void remove() {
			throw new UnsupportedOperationException();
		}  
	}
}
