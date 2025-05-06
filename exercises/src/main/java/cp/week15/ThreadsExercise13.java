package cp.week15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise13
{
	/*
	Modify ThreadsExercise9 to use executors.
	Try different kinds of executor (cached thread pool or fixed thread pool) and different fixed pool sizes.
	Which executor runs faster?
	Can you explain why?

	Få Victor til at hjælpe dig med at forklare, hvilken en der er bedst. 

	Lige nu synes du ikke, at noget af det går særlig hurtigt. 
	Jeg antager, at den standard networkStealingThread er den bedste - da den selv finder det bedste antal threads. 
	
	fremgangsmåde: ændret ex.9 til at bruge executors. 

	Caveat: Der er to gange merges; først, når den tæller antal ord med bogstaver i computeOccurences, 
		herefter når den matcher de talte ord i hvert set med hinanden
		-> dette, som gør den langsom?
		-> Men den tager dog ca. ligeså længe, som Fabrizios implementering.


		Ofte afgørende hvor lang tid de enkelte jobs tager. 
		- NewWorkStealingPool egner sig bedre til "små" jobs. 
		*/

	/* Network stealing pool */

		public static void main() throws ExecutionException 
	{
		// word -> number of times that it appears over all files
		Map< Character, Set<String> > occurrences = new HashMap<>();
		ExecutorService executor = Executors.newWorkStealingPool();



		// ExecutorService executor = Executors.newCachedThreadPool(); // For testing another type. 
		// ExecutorService executor = Executors.newFixedThreadPool(4); // For testing another type. 


		ExecutorCompletionService< Map <Character, Set<String> >> completionService = 
			new ExecutorCompletionService<>( executor );
		
		// wokring on the same map, versus each thread getting their own.
		// If each thread gets their own, then we need to merge them as well. 

		try {
			long pendingTasks =
				Files.walk( Paths.get("/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week15"))
					.filter(Files::isRegularFile) // Man kunne tilføje yderligere kontrol, der tjekkede om det kun var normale txt. 
					.map(filepath -> 
						completionService.submit( () -> computeOccurrences(filepath) ) 
					).count();
		while (pendingTasks > 0) { // Merging in order to get the total amount across all files.
			Map<Character, Set<String>> fileOccurrences = completionService.take().get();
			fileOccurrences.forEach( (character, set) -> occurrences.merge( character, set, (ExistingSet, NewSet) -> 
				{
					ExistingSet.addAll(NewSet);
					return ExistingSet;
				} 
				));
			pendingTasks--;
		}
	} catch( InterruptedException | ExecutionException | IOException e ) {
		e.printStackTrace();
	}
		
	try {
		executor.shutdown();
		executor.awaitTermination( 1, TimeUnit.DAYS );
	} catch( InterruptedException e ) {
		e.printStackTrace();
	}


	// FOR SEEING THE ACTUAL RESULT. 
	// occurrences.forEach( (character, list) -> System.out.println( character + ": " + list ) );
	}
	
	// Ændret indholdet af parametrene; tilføjer ord til set hvis startbogstav.
	// går fra inplace, til at skabe hashmappet her. (nødvendigt at returnere noget ift. vores executor. )
	private static Map<Character, Set<String>> computeOccurrences(Path textFile)
	{
		Map<Character, Set<String>> occurrences = new HashMap<>();
		
		// Nuer det 
		try {
			Files.lines( textFile ) 
				.flatMap( Words::extractWords )
				.map( String::toLowerCase )
				.forEach( s -> 
				{
					Character c = s.charAt(0);
					occurrences.merge( c, new HashSet<>(Set.of(s)), (ExistingSet, NewSet) -> {
						ExistingSet.addAll(NewSet);
						return ExistingSet;
					} );
				} );
		} catch( IOException e ) {
			e.printStackTrace();
		}
		return occurrences;
	}

}
