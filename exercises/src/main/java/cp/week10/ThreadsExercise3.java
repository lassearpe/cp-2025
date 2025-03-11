package cp.week10;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise3
{
	/*
	Modify the threads/cp/SynchronizedMap2T example such that:
	- There are now two maps (instead of just one) for accumulating results, one for each thread.
	- Each thread uses only its own map, without synchronizing on it.
	- After the threads terminate, create a new map where you merge the results of the two dedicated maps.
	
	Questions:
	- Does the resulting code work? Can you explain why?
	- Does the resulting code perform better or worse than the original example SynchronizedMap2T?
	- Can you hypothesise why?
	*/

	public static void main(String[] args) {
		Map< Character, Long > occurrences_t1 = new HashMap<>();
		Map< Character, Long > occurrences_t2 = new HashMap<>();
	
			Thread t1 = new Thread( () ->
			countLetters( Paths.get( "exercises/src/main/java/cp/week10/text1.txt" ), occurrences_t1 ) );
			Thread t2 = new Thread( () ->
			countLetters( Paths.get( "exercises/src/main/java/cp/week10/text2.txt" ), occurrences_t2 ) );
			Thread t3 = new Thread( () ->
			mergeLetters(occurrences_t1, occurrences_t2) ); 

			t1.start(); 
			t2.start();
			
			try {
				t1.join();
				t2.join();
			} catch( InterruptedException e ) {
				e.printStackTrace();
			}
			System.out.println( "e -> " + occurrences_t2.get( 'e' ) );

			t3.start();
			try {
				t3.join();
			} catch( InterruptedException e ) {
				e.printStackTrace();
			}
			
			System.out.println( "e -> " + occurrences_t2.get( 'e' ) );
	}

	private static void mergeLetters(Map<Character, Long > occ_1, Map<Character, Long > occ_2) {
		occ_1.forEach((key,value) -> occ_2.merge( key,value, Long::sum));
	}
	
	private static void countLetters( Path textPath, Map< Character, Long > occurrences ) {
		try( Stream< String > lines = Files.lines( textPath ) ) {
			lines.forEach( line -> {
				for( int i = 0; i < line.length(); i++ ) {
					final char c = line.charAt( i );
					synchronized( occurrences ) {
						occurrences.merge( c, 1L, Long::sum );
					}
				}
			} );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}


}
