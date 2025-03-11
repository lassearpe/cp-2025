package cp.week8;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import cp.week8.LambdaExercise2;

/**
 * 
 * @author Fabrizio Montesi
 */
public class LambdaExercise3
{
	/*
	NOTE: When I write Class::methodName, I don't mean to use a method reference (lambda expression), I'm simply
	talking about a particular method.
	*/
	
	/*
	- Create a Box that contains an ArrayList<String> with some elements of your preference.
	- Now compute a sorted version of your list by invoking Box::apply, passing a lambda expression that uses List::sort.
	*/

	// Husk at læse hans freaking tekst. Du skal blot bruge en lambda expression ffs. 

	public static void main( String[] args ) {
		ArrayList<String> list = new ArrayList<>();
		list.add("Kaj");
		list.add("Haj");
		list.add("Aaj");

		Box<ArrayList<String>, ArrayList<String>> box = new Box<>(list);
		
		List<String> sortedList = box.apply(input -> {
            input.sort(String::compareTo);
            return list;  
        });

		System.out.println(sortedList);
		
	}

}
