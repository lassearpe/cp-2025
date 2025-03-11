package cp.week8;

import java.util.ArrayList;
import java.util.List;

// import cp.week8.LambdaExercise2;

/**
 * 
 * @author Fabrizio Montesi
 */
public class LambdaExercise4
{
	/*
	- Create a list of type ArrayList<String> with some elements of your preference.
	- Create a Box that contains the list.
	- Now compute the sum of the lengths of all strings in the list inside of the box,
	  by invoking Box::apply with a lambda expression.
	*/


// STREAM() to process content of list sequentially, like reduce, map etc. 






public static void main(String[] args) {
	ArrayList<String> list = new ArrayList<>();
	list.add("John");
	list.add("Mogensen");
	list.add("Jam");

	Box<ArrayList<String>, Integer> box = new Box<>(list);

	Integer sum = box.apply(input -> input.stream().mapToInt(String::length).sum());
	System.out.println(sum);

	}

}

