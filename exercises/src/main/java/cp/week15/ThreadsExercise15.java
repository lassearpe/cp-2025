package cp.week15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise15
{
	/*
	Adapt your program from ThreadsExercise14 to use an ExecutorCompletionService, as in Threads/cp/WalkCompletionService.
	
	CompletionFuture cs CompletionService

	CompletionFuture forgår i submission order, ikke completion order. Mere kontrol.
	Det gør, at der kommer mere ventetid. (Blocking)

	Futures minder om latch - vi bestemmer, hvornår vi afslutter den. 
		Derfor kan completionFutur bruges til mere kontrol over udførslen.  

	CompletionService er non-blocking - går igang med ting, så snart det er muligt. 
	CF -> Mere kontrol.
	CS -> Simplere. 

	Hvis vi ikke har brug for kontrol, så er Executors bedre, da det er mere simpelt. 

	*/

public static void main()
	{
		// word -> number of times it appears over all files
		Map< Path, FileInfo > fileOccurrences = new HashMap<>();
		ExecutorService executor = Executors.newWorkStealingPool();
		ExecutorCompletionService< Map< Path, FileInfo > > completionService =
		new ExecutorCompletionService<>( executor );

		try {
			long pendingTasks = 
				Files.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week15" ) )
					.filter( Files::isRegularFile )
					.map( filepath ->
						completionService.submit( () -> computeFileInfo( filepath ) )
					)
					.count();
			while( pendingTasks > 0 ) {
				Map<Path, FileInfo> result = completionService.take().get();
				// fileOccurrences.forEach( (word, n) -> occurrences.merge( word, n, Integer::sum ) );
				fileOccurrences.putAll(result);
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
		
	fileOccurrences.forEach( (file, info) -> System.out.println( file + ": " + info.linesL) );
	
	// System.out.println(fileOccurrences);
	}
	
	private static Map<Path, FileInfo> computeFileInfo( Path textFile )
	{
		Map< Path, FileInfo > occurrences = new HashMap<>();
		FileInfo info = new FileInfo(textFile);
		occurrences.put(textFile, info);
		
		return occurrences;
	}

}

class FileInfo {

	final double size;
	final long lines;
	final long linesL;
	final Path path;

	// Constructor. 
	public FileInfo(Path filePath) {
		File file = filePath.toFile();
		this.path = filePath;
		this.size = file.length();
		this.lines = getLines();
		this.linesL = getLinesUpperCaseL();
	
	}

	public long getLines() {
		long lineCount = 0;
		try (Stream<String> stream = Files.lines(path)) {
            lineCount = stream.count();  // Count the number of lines in the stream
            // System.out.println("Number of lines: " + lineCount);
			// return lineCount;
        } catch (IOException e) {
            e.printStackTrace();
        }
		return lineCount;
		
	}

	public long getLinesUpperCaseL() {
		long lineCountL=2;

		try (Stream<String> stream = Files.lines(path)) {
			lineCountL = stream.filter(line-> !line.isEmpty() && line.charAt(0)=='L')
			.count();
            // lineCountL = stream.count();  // Count the number of lines in the stream
            // System.out.println("Number of lines: " + lineCount);
			return lineCountL;
        } catch (IOException e) {
            e.printStackTrace();
        }
		
		return lineCountL;
	}

}
