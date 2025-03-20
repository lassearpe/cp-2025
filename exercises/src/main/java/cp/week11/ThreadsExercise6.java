package cp.week11;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.stream.Stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;


/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise6
{
	/*
	This exercise generalises Threads/cp/SynchronizedMap2TWords.
	Feel free to borrow the appropriate pieces of code from that example.
	
	Implement a method 
		public static Map< String, Integer > computeOccurrences( Stream< String > filenames )
	that returns a map of how many times each word occurs (as in SynchronizedMap2TWords) in the files named
	in the stream parameter.
	
	Try first to implement the method sequentially (no threads), then try
	to implement it such that each file is processed by a dedicated thread (all threads
	should run concurrently and be waited for).
	*/

	//map -> one to one, flatmap -> many to many?

	public static Map< String, Integer > computeOccurrences2( Stream< String > filenames ) {

	}	

	// Sequential implementation
	public static Map< String, Integer > computeOccurrences( Stream< String > filenames ) {
		Map <String, Integer> occurences = new HashMap<>();

		filenames
		
		// .map(Files::lines)
		.map(Paths::get)
		.flatMap(path -> {
		try {
			return Files.lines(path).flatMap(line -> Words.extractWords(line));
		} catch (IOException e) {
			e.printStackTrace();
			return Stream.empty(); // If error occurs, return an empty stream
		}
		})
		.map(String::toLowerCase)
		.forEach(s-> {	
			synchronized(occurences) {
			occurences.merge(s ,1 , Integer::sum);
		}
		} );

		return occurences;
	}
	

	public static void main(String[] args) {

		List<String> filenames = Arrays.asList("exercises/src/main/java/cp/week11/test.txt", "exercises/src/main/java/cp/week11/test2.txt");

        Stream<String> filenamesStream = filenames.stream();

		System.out.println(computeOccurrences(filenamesStream));
	}
}



// private static Map< String, Integer >  computeOccurrences(String filename, Map<String, Integer> occurrences) {
// 	try {
// 		Files.lines( Paths.get( filename ) ).flatMap( Words::extractWords ).map( String::toLowerCase ).forEach( s -> {
// 			synchronized( occurrences ) {
// 				occurrences.merge( s, 1, Integer::sum );
// 			}
// 		} );
// 	} catch( IOException e ) {
// 		e.printStackTrace();
// 	}
// }

