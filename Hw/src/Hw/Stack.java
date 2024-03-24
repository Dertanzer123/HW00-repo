package Hw;

public class Stack {

	private Object[] elements;
	
	private int top;
	
	public Stack(int capacity) 
	{
		elements=new Object[capacity];
		top=0;
		
	}
	public boolean Push(Object input) 
	{
		if(!isfull()) 
		{
			elements[top]=input;
			top++;
			return true;
		}
		return false;
		
	}
	public Object Peek()
	{
		top--;
		Object trns=elements[top];
		top++;
		return trns;
	}
	public Object Pop() 
	{
		top--;
		Object trns=elements[top];
		return trns;
		
			
		
	}
	
	public boolean isempty() 
	{
		return  top==0;
	}
	public boolean isfull() 
	{
		return  top==elements.length;
	}
	public int size() 
	{
		return top;
	}
	public int capacity() 
	{
		return elements.length;
	}
}
