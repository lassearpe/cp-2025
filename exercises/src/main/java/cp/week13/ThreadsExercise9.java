package cp.week13;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import com.liferay.portal.kernel.util.ConcurrentHashSet;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise9
{
	/*
	Modify Threads/cp/ConcurrentMap to compute a map of type Map<Character, Set<String>>.
	The map should map a character to the set of words that start with that character (case sensitive).

Inddele ord efter deres begyndelsesbogstav.
	
Gå den alligevel igennem med Victor for at sikre dig, at du har forstået det ordenligt.
 
	*/

	public static void main(String[] args) 
	{
		// word -> number of times that it appears over all files
		Map< Character, Set<String> > occurrences = new ConcurrentHashMap<>();



		
		List< String > filenames = List.of(
			"/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week13/text1.txt",
			"/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week13/text1.txt"
		);
		
		CountDownLatch latch = new CountDownLatch( filenames.size() );
		
		filenames.stream()
			.map( filename -> new Thread( () -> {
				computeOccurrences( filename, occurrences );
				latch.countDown();
			} ) )
			.forEach( Thread::start );

		try {
			latch.await();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
		
		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );
	}
	
	private static void computeOccurrences( String filename, Map< Character, Set<String>> occurrences )
	{
		try {
			Files.lines( Paths.get( filename ) )
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
	}

}
