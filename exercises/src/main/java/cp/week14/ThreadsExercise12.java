package cp.week14;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.stream.IntStream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>,
 */
public class ThreadsExercise12
{
	/*
	* Modify the code in Threads/WalkBlockingDeque according to the following:
	* 1. Modify it such that only files ending with a ".txt" suffix are put in the tasks deque.
	* 2. Use PriorityBlockingQueue instead of BlockingQueue, making sure to prioritize files with the least size.
	
	Spørg Victor -> Hvordan kan jeg tjekke, om den virkeligt prioritere dette?

	// Tjekke det ved at kommentere worker ("IntStream.range") ud, og debugge med break på linje 92 ca. 
	*/


	public static void main(String[] args) {
		ThreadsExercise12.main();
	}

	public static void main()
	{
		// word -> number of times it appears over all files
		Map< String, Integer > occurrences = new ConcurrentHashMap<>();
		
		int maxThreads = Runtime.getRuntime().availableProcessors();
		CountDownLatch latch = new CountDownLatch( maxThreads );

		// Slå op hvad 10 betyder her. 
		final PriorityBlockingQueue< Optional< Path > > tasks = new PriorityBlockingQueue<>(10, 
		
		new Comparator<Optional<Path>>() {
			@Override
			public int compare(Optional<Path> o1, Optional<Path> o2) {
				// Is present tjekker for optional ikke er .empty(). 
				if (o1.isPresent() && o2.isPresent()) {
					try {
						long size1 = Files.size(o1.get()); // Get size of the first file
						long size2 = Files.size(o2.get()); // Get size of the second file
						return Long.compare(size1, size2); // Compare by file size
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				// If only one of the Optionals is present, it should have higher priority
				return Boolean.compare(o2.isPresent(), o1.isPresent());
			}
		});
		
		// "Worker"
		IntStream.range( 0, maxThreads ).forEach( i -> {
			new Thread( () -> {
				try {
					Optional< Path > task;
					do {
						task = tasks.take();
						task.ifPresent( filepath -> computeOccurrences( filepath, occurrences ) );
					} while( task.isPresent() );
					tasks.add( task ); //Når den rammer .empty(), lukker vi ned. 

				} catch( InterruptedException e ) {}
				latch.countDown();
			} ).start();
		} );
				
		try {
			Files.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week14" ) )
				.filter( Files::isRegularFile )
				.filter( path -> path.toString().endsWith(".txt"))
				.forEach( path -> tasks.add( Optional.of( path ) ) );
				// .forEach(Optional::add);
		} catch( IOException e ) {
			e.printStackTrace();
		}
		
		tasks.add( Optional.empty() );
		
		try {
			latch.await();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
		
		occurrences.forEach( (word, n) -> System.out.println( word + ": " + n ) );

	}
	
	private static void computeOccurrences( Path textFile, Map< String, Integer > occurrences )
	{
		try {
			Files.lines( textFile )
				.flatMap( Words::extractWords )
				.map( String::toLowerCase )
				.forEach( s -> occurrences.merge( s, 1, Integer::sum ) );
		} catch( IOException e ) {
			e.printStackTrace();
		}
	}
}




