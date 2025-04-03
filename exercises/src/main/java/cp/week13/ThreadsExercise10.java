package cp.week13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise10
{
	/*
	Modify ThreadsExercise9 to use Files.walk over the data directory in the Threads project, such
	that you recursively visit all files in that directory instead of using a fixed list of filenames.
	
	-- lav på øvelsesholdet; evt. se billedet på den telefon.


	// Latch er et alternativ til at joine. Den bruges også blot til at vente. 
	
	// Hvorfor try/except

	*/

		public static void main(String[] args) 
	{
		// word -> number of times that it appears over all files
		// Concurrency sikker datastruktur. 
		Map< Character, Set<String> > occurrences = new ConcurrentHashMap<>();
		
		// List< String > filenames = List.of(
		// 	"/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week13/text1.txt",
		// 	"/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week13/text2.txt"
		// );
		
		Path start = Paths.get("/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week13");
		
		// CountDownLatch latch = new CountDownLatch( filenames.size() );

		try {
			Files.walk(start)
			.filter(path -> path.toString().endsWith(".txt"))
			.map(Path::toString)
			.map( filename -> new Thread( () -> {
				computeOccurrences( filename, occurrences );
				// latch.countDown();
			} ) )
			.map( thread -> {
				 thread.start();
				 return thread;
			})
			.forEach( thread -> {
				try {
					thread.join();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		

		// filenames.stream()
		// 	.map( filename -> new Thread( () -> {
		// 		computeOccurrences( filename, occurrences );
		// 		latch.countDown();
		// 	} ) )
		// 	.forEach( Thread::start );

		// try {
		// 	latch.await();
		// } catch( InterruptedException e ) {
		// 	e.printStackTrace();
		// }
		
		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );
	}
	
	// Ændret indholdet af parametrene; tilføjer ord til set hvis startbogstav.
	private static void computeOccurrences( String filename, Map< Character, Set<String>> occurrences )
	{
		try {
			Files.lines( Paths.get( filename ) )
				.flatMap( Words::extractWords )
				.map( String::toLowerCase )
				.forEach( s -> 
				{
					// System.out.println("We here");
					Character c = s.charAt(0);
					occurrences.merge( c, new HashSet<>(Set.of(s)), (ExistingSet, NewSet) -> {
						ExistingSet.addAll(NewSet);
						return ExistingSet;
					} );
				} );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}

}
