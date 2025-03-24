package cp.week12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise7
{

	/*
	- Modify Threads/cp/threads/SynchronizedMap such that:
		* Each threads also counts the total number of times that any word
		  starting with the letter "L" appears.
		* Each thread should have its own total (no shared global counter).
		* The sum of all totals is printed at the end.
	*/

	/* I do it based on the file SynchronizedMap2TWords instead - this is what was shown in the video. */

public static void main(String[] args) {
// word -> number of times that it appears over all files

		/* Løsning: Brug af atomicInteger og lille ændring i computeOccurences
		 * 
		 * Spørg Victor om dette er korrekt og få uddybet atomicIntegers.
		 * 
		 * "++" not atomic on integers!.
		 */
		Map< String, Integer > occurrences = new HashMap<>();	
		
		// Is this a shared global counter?
		// Snak med Victor om dette, om implementer resten (opg 8) sammen med ham. 
		// Jeg kunne forestille mig, at de nedenstående atomiske variable blot skulle defineres og returneres indeni "ComputeOccurences" i stedet.
		// På den måde har jeg faktisk implementeret næste opgave her, og kan måske lave ovenstående ændring næste øhold. 

		// Forskellen kunne også bare være, om de har den samme counter -> eller om de har hver deres counter (opg 7 vs opg 8.)
		
		AtomicInteger countL_t1 = new AtomicInteger(0);
		AtomicInteger countL_t2 =  new AtomicInteger(0);;

		Thread t1 = new Thread( () -> computeOccurrences( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week12/text1.txt", occurrences, countL_t1) );
		Thread t2 = new Thread( () -> computeOccurrences( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week12/text2.txt", occurrences, countL_t2) );

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}

		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );
		System.out.println("Words that start with L in text 1: " + countL_t1);
		System.out.println("Words that start with L in text 2: " + countL_t2);
		int total = countL_t1.get()+countL_t2.get();
		System.out.println("Total words with L: " + total);
}		


	private static void computeOccurrences(String filename, Map<String, Integer> occurrences, AtomicInteger countL) {

		try {
			Files.lines( Paths.get( filename ) ).flatMap( Words::extractWords ).map( String::toLowerCase ).forEach( 
				s -> {
				synchronized( occurrences ) {
					occurrences.merge( s, 1, Integer::sum );

					if (s.charAt(0) == 'l') {
						countL.incrementAndGet();
					}
				}

			} );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}



}
