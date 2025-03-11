package cp.week8;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Fabrizio Montesi
 * @param <I>
 * @param <O>
 */
public class LambdaExercise5<I, O>
{
	/*
	- Write a static method Box::applyToAll that, given
	  a list of Box(es) with the same type and a BoxFunction with compatible type,
	  applies the BoxFunction to all the boxes and returns a list
	  that contains the result of each BoxFunction invocation.
	*/



	static <O, I> ArrayList<O> applyToAll(ArrayList<Box<I,O>> list, BoxFunction<I,O> function) {
			ArrayList<O> resultList = new ArrayList<>();
			
			for (int i = 0; i < list.size(); i++) {
				O result = list.get(i).apply(function);
				resultList.add(result);
			}

			return resultList;
		
	};

	// A BoxFunction that converts a List<String> to a List<String> of uppercase strings
public static class ToUpperCaseBoxFunction implements BoxFunction<String, String> {
    @Override
    public String apply(String input) {
		return input.toUpperCase();
        
    }

// Kunne måske være mere interessant at implementere, således hver box indeholer en liste med strings i stedet for. 

}


	public static void main(String[] args) {
		Box<String,String> box1 = new Box<>("Hej");
		Box<String,String> box2 = new Box<>("Johnny");

		ArrayList<Box<String, String>> list2 = new ArrayList();
		list2.add(box1);
		list2.add(box2);
		
		BoxFunction<String,String> upper_func = new ToUpperCaseBoxFunction();

		// BoxFunction<List<String>, List<String>> toUpperCaseFunction = new ToUpperCaseBoxFunction();

		ArrayList<String> results = applyToAll(list2, upper_func);

		for (String result: results ) {
			System.out.println(result);
		}

		
	}






}
