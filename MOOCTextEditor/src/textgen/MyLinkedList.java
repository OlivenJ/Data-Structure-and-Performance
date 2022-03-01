package textgen;

import java.util.*;


/** A class that implements a doubly linked list
 * 
 * @author UC San Diego Intermediate Programming MOOC team
 *
 * @param <E> The type of the elements stored in the list
 */
public class MyLinkedList<E> extends AbstractList<E> {
	LLNode<E> head;
	LLNode<E> tail;
	int size;

	/** Create a new empty LinkedList */
	public MyLinkedList() {
		// TODO: Implement this method
		
		size = 0;
		head = new LLNode<E>(null);
		tail = new LLNode<E>(null);
		
		head.next = tail;
		tail.prev = head;
		tail.next = null;
		 
	}

	/**
	 * Appends an element to the end of the list
	 * @param element The element to add
	 */
	public boolean add(E element ) throws NullPointerException
	{
		if(element == null) {
			
			throw new NullPointerException("Null is not acceptable.");
			
		}
		
		LLNode<E> newNode = new LLNode<E>(element);
		
		
		if(size == 0) {
			
			newNode.next = tail;
			//tail.prev = null;
			tail.prev = newNode;
			
			newNode.prev = head;
			//head.next = null;
			head.next = newNode;
			
			size ++;
			
			
		}else {
		newNode.next = tail;
		tail.prev.next = newNode;
		newNode.prev = tail.prev;
		tail.prev = newNode;
		
		size ++;
		
		}
		
		// TODO: Implement this method
		
		return false;
	}

	/** Get the element at position index 
	 * @throws IndexOutOfBoundsException if the index is out of bounds. */
	public E get(int index) throws IndexOutOfBoundsException
	{
		if(index < 0 || index >= size) {
			
			throw new IndexOutOfBoundsException("Shall have positive index less than the list length.");
		}
		// TODO: Implement this method.
		
		LLNode<E> current = head.next;
		int count = 0;
		
		
		
		while(current != null) {
			
			if(count == index) {
				return current.getData();
			}
			
			count ++;
			current = current.next;
			
		}
		
		
		return null;
		
		
	}

	/**
	 * Add an element to the list at the specified index
	 * @param The index where the element should be added
	 * @param element The element to add
	 */
	public void add(int index, E element ) throws IndexOutOfBoundsException, NullPointerException
	{
		if(element == null) {
			throw new NullPointerException("Can not add null");
			
		}
		
		if(index >= size&&size !=0) {
			throw new IndexOutOfBoundsException("The List has no place for the new element, smaller the index.");
		}if(index < 0) {
			
			throw new IndexOutOfBoundsException("The index value shall not be negative");
		}
		

		
		
		LLNode<E> newNode = new LLNode<E>(element);
		
		
		if(size == 0 && index == 0) {
			this.add(element);
		}else if(size > 0 && index < size && index >=0) {
			
			LLNode<E> oldPos = head.next;
			int counter = 0;
			while(counter < index) {
				counter ++;
				oldPos = oldPos.next;	
			}
			newNode.next = oldPos;
			oldPos.prev.next = newNode;
			newNode.prev = oldPos.prev;
			oldPos.prev = newNode;
			
			size ++;
		}
		
		// TODO: Implement this method
	}


	/** Return the size of the list */
	public int size() 
	{
		// TODO: Implement this method
		return size;
	}

	/** Remove a node at the specified index and return its data element.
	 * @param index The index of the element to remove
	 * @return The data element removed
	 * @throws IndexOutOfBoundsException If index is outside the bounds of the list
	 * 
	 */
	public E remove(int index) throws IndexOutOfBoundsException 
	{
		if(index >= size) {
			throw new IndexOutOfBoundsException("There are less number of elements than the index you put.");
		}else if(index <0) {
			throw new IndexOutOfBoundsException("Negative value is not acceptable");
			
		}
		
		// TODO: Implement this method
		
		LLNode<E> current = head.next;
		if(index == 0) {
	        
	        head.next = current.next;
	        current.next.prev = head;
	        current.next = null;
	        current.prev = null;
	        size --;
			
		}else if(index == size - 1) {
			
			current = tail.prev;
			current.prev.next = tail;
			tail.prev = current.prev;
			current.prev = null;
			current.next = null;
			size --;
			
		}else {
		int counter = 0;
		while(counter < index) {
			current = current.next;
			counter ++;
		}
		
		current.next.prev = current.prev;
		current.prev.next = current.next;
		current.next = null;
		current.prev = null;
		size --;
		
		
		}
		return current.getData();
	}

	/**
	 * Set an index position in the list to a new element
	 * @param index The index of the element to change
	 * @param element The new element
	 * @return The element that was replaced
	 * @throws IndexOutOfBoundsException if the index is out of bounds.
	 */
	public E set(int index, E element) throws IndexOutOfBoundsException, NullPointerException
	{
		if(element == null) {
			
			throw new NullPointerException("Null is not accepted");
		}
		
		if(index >= size) {
			throw new IndexOutOfBoundsException("Can not find corresponding element, try smaller index");
		}else if(index < 0) {
			
			throw new IndexOutOfBoundsException("Negative value is illegal");
		}
		
		
		E result;
		//LLNode<E> newNode = LLNode<E>(element);
		
		if(index == 0) {
			result = this.remove(index);
			this.add(index, element);
		}else if(index == size - 1) {
			result = this.remove(index);
			this.add(element);
			
		}else {
			result = this.remove(index);
			this.add(index, element);
			
			
		}
		
		// TODO: Implement this method
		return result;
	}
	
	
	
}

class LLNode<E> 
{
	LLNode<E> prev;
	LLNode<E> next;
	E data;

	// TODO: Add any other methods you think are useful here
	// E.g. you might want to add another constructor

	public LLNode(E e) 
	{
		this.data = e;
		this.prev = null;
		this.next = null;
	}
	
	public E getData() {
		
		return data;
		
	}
	
	public LLNode<E> getPrev(){
		return prev;
	}

	public LLNode<E> getNext(){
		return next;
	}
	
}
