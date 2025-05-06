package cp.week15;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise16
{
	/*
	Adapt your program from ThreadsExercise15 to use CompletableFuture, as in Threads/cp/WalkCompletableFuture.
	*/

	public static void main()
	{

		Map< Path, FileInfo > occurrences = new ConcurrentHashMap<>();

		try {
			CompletableFuture < Void > [] futures = 
				Files.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week15" ) )
					.filter( Files::isRegularFile )
					.map( filepath ->
						CompletableFuture.supplyAsync( () -> computeFileInfo( filepath ) )  // SupplyAsync: Kører i baggrunden på en anden thread. 
						.thenAccept( fileOccurrences -> occurrences.putAll(fileOccurrences)) // Monad, hvor vi behandler færdig vores fremsatte futures. 
					)
					.collect( Collectors.toList() ).toArray( new CompletableFuture[0] );
			CompletableFuture
				.allOf( futures )
				.join();

		} catch( IOException e ) {
			e.printStackTrace();
		}
		
	occurrences.forEach( (file, info) -> System.out.println( file + ": " + info.lines) );
	
	}
	

	private static Map<Path, FileInfo> computeFileInfo( Path textFile )
	{
		Map< Path, FileInfo > occurrences = new HashMap<>();
		FileInfo info = new FileInfo(textFile);
		// info.setFile(textFile);
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
		long lineCountL=0;

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
