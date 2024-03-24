package Hw;

public class CQueue {
	
	private Object[] elements;
	private int front;//elements will be taken from front
	private int back;//new elements will added from back
	private int capacity;
	
	
	public CQueue(int capacity) 
	{
		this.capacity=capacity;
		elements=new Object[capacity];
		front=0;
		back=0;
		
	}
	public boolean Qadd(Object input) 
	{
		if(!isfull()) 
		{
			elements[back%capacity]=input;
			back++;
			return true;
		}
		return false;
		
	}
	public Object Qtake() 
	{
		Object trns=elements[front%capacity];
		elements[front%capacity]=null;
		front++;
		return trns;
	}
	public Object Peek() 
	{
		Object trns=elements[front%capacity];
		return trns;
	}
	public int capacity() 
	{
		return capacity;
	}
	public int size() 
	{
		if(elements[front%capacity]==null) 
		{
			return 0;	
		}
		if(elements[back%capacity]!=null) 
		{
			return capacity;
		}
		
		return (capacity+(back%capacity)-(front%capacity))%capacity;
	}
	public boolean isempty() 
	{
		return elements[front%capacity]==null;
	}
	public boolean isfull() 
	{
		return elements[back%capacity]!=null;
	}
}
