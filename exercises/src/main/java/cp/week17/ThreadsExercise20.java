package cp.week17;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise20
{
	/*
	Modify ThreadsExercise19 such that the computed map contains only
	entries for files that have at least 10 lines.
	*/

	public static void main()
	{
		try {
			Map< Path, FileInfo > occurrences =
				Files
					.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week17" ) )
					.filter( Files::isRegularFile )
					.collect( Collectors.toList() )
					.parallelStream()
					.flatMap( textFile -> {
						return computeFileInfo(textFile).entrySet().stream();

					})
					.filter(info -> info.getValue().lines >= 10) // Tilføjelse af endnu et filter, på vores nye flatMap stream. 
					.collect( Collectors.toMap(
						Map.Entry::getKey,
						Map.Entry::getValue
					) );
			occurrences.forEach( (file, info) -> System.out.println( file + ": " + info.lines ) );
		} catch( IOException e ) {
			e.printStackTrace();
		}
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





// Tilføje enkelt filter i bunden;import java.util.concurrent.CompletableFuture;

// .filter(info.lines >= 10)


// Alternativ:
// - map om til flatmap. 



