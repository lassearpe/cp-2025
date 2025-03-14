package cp.week8;

/**
 * 
 * @author Fabrizio Montesi
 */
public class LambdaExercise1
{
	/*
	Create a class Box<T> with a single final field of type T called "content".
	Its constructor must take the content as parameter and set it.
	
	Add a public method called "content()" that returns the content of the box.

	*/


	class Box< T > {
		private T content;
		// private O output;
	
		public Box(T content) {
			this.content = content;
		}
	
		public T content() {
			return this.content;
		}
}

}
