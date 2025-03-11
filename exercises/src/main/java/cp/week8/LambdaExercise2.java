package cp.week8;

import java.util.ArrayList;

/**
 * 
 * @author Fabrizio Montesi
 */
public class LambdaExercise2
{
	/*
	Let's make a more advanced box.
	
	- Create a new interface BoxFunction<I,O> with a method "apply" that
		takes something of type I (for input) as parameter and has O (for output)
	    as return type.
		
	- Modify the Box class by adding a new method called "apply" that:
		* Takes as parameter a BoxFunction<I,O> that requires as input something
		  of the same type of the content of the box.
		* Has the output type of the BoxFunction parameter as return type.
		* Its implementation applies the BoxFunction to the content of the box
		  and returns the result.

	- Modify the Box class constructor such that it throws an IllegalArgumentException
	  if the passed content is null.
	*/


// Husk Lasse -> du skal ikke implementere metoden til "apply" endnu -> bare skabe muligheden for dens anvendelse. 
// så kan man senere hen udnytte vores interface til at implementere forskellige former for "apply".

    public static void main( String[] args ) {
		Box<String, String> box = new Box<>("Hej");
		System.out.println(box.content());

	}
}

interface BoxFunction<I,O> {
	O apply(I input);
}

class Box< I, O > {
	private I content;
	// private O output;

	public Box(I content) {
		this.content = content;
	}

	public I content() {
		return this.content;
	}

	O apply(BoxFunction<I,O> function) {
		return function.apply(this.content);
	}

}

