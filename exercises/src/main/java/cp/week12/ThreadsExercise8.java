package cp.week12;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise8
{
	/*
	- As ThreadExercise7, but now use a global counter among all threads instead.
	- Reason about the pros and cons of the two concurrency strategies
	  (write them down).

	  	- Which one is faster? Can you think and implement a test routine that 
	  dependably measures and compares the execution time of the two 
	  implementations?

	Pros ved flere reference-get-counters:
		- Hver thread kan counte på sin egen -> summer op til sidst (som tager konstant tid!)
	Pros ved atomisk global:
		- Her skal de vente på hinanden. 

	Han har skrevet noget på seneste forelæsning (20 eller 25 marts) omkring atomic global vs local counter.


	*/
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
		
		// Her er det rent faktisk nødvendigt at bruge en atomisk integer, da der er to tråde til én counter.
		AtomicInteger countL = new AtomicInteger(0);

		Thread t1 = new Thread( () -> computeOccurrences( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week12/text1.txt", occurrences, countL) );
		Thread t2 = new Thread( () -> computeOccurrences( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week12/text2.txt", occurrences, countL) );

		t1.start();
		t2.start();
		try {
			t1.join();
			t2.join();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}

		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );
		// System.out.println("Words that start with L in texts: " + countL);
		// int total = countL.get()+countL.get();
		System.out.println("Total words with L: " + countL.get());
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
