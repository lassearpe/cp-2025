package cp.week15;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.io.File;
import java.nio.file.attribute.BasicFileAttributes;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise14
{
	/*
	Modify Threads/cp/WalkExecutorFuture such that, instead of word occurrences,
	it computes a map of type Map< Path, FileInfo >, which maps the Path of each file found in "data"
	to an object of type FileInfo that contains:
		- the size of the file;
		- the number of lines contained in the file;
		- the number of lines starting with the uppercase letter "L".
			SPØRG VICTOR, HVORFOR DET IKKE VIRKER. 


	Denne her skal du bruge hjælp fra Victor til at løse. 

	ComputefileInfo -> finder fileinfo, sætter det sammen med path (som en indlejret path - dumt?)
	*/

	public static void main()
	{
		// word -> number of times it appears over all files
		Map< Path, FileInfo > fileOccurrences = new HashMap<>();
		ExecutorService executor = Executors.newWorkStealingPool();

		try {
			List< Future< Map< Path, FileInfo > > > futures =
				Files.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week15" ) )
					.filter( Files::isRegularFile )
					.map( filepath ->
						executor.submit( () -> computeFileInfo( filepath ) )
					)
					.collect( Collectors.toList() );

			for( Future< Map< Path, FileInfo > > future : futures ) {

				Map<Path, FileInfo> result = future.get();
				fileOccurrences.putAll(result);
				// fileOccurrences.forEach( (file, info) -> occurrences.put(file,info));

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
	
// Det er nok ikke meningen, at jeg skal lave endnu en Map i denne funktion?
// Hør lige, hvad Victor siger til det. 
	private static Map<Path, FileInfo> computeFileInfo( Path textFile )
	{
		Map< Path, FileInfo > occurrences = new HashMap<>();
		FileInfo info = new FileInfo();
		info.setFile(textFile);
		occurrences.put(textFile, info);
		
		return occurrences;
	}

}

class FileInfo {

	double size;
	long lines;
	long linesL;
	Path path;

	public FileInfo setFile(Path filePath) {
		File file = filePath.toFile();
		this.path = filePath;
		this.size = file.length();
		this.lines = getLines();
		this.linesL = getLinesUpperCaseL();

		return this;	
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
