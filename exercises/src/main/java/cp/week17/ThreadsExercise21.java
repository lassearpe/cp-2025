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
public class ThreadsExercise21
{
	/*
	Modify Threads/cp/WalkParallelStreamFindAny such that it returns a boolean
	telling whether there exists at least one file with more than 10 lines.
	*/

	public static void main()
	{
		try {
			boolean found =
				Files
					.walk( Paths.get( "/home/lassearpekristensen/Datalogi/4. semester/Concurrent Programming/cp-2025/exercises/src/main/java/cp/week17" ) )
					.filter( Files::isRegularFile )
					.collect( Collectors.toList() )
					.parallelStream()
					.flatMap( textFile -> {
						return computeFileInfo(textFile).entrySet().stream();
					} )
					.anyMatch(info -> info.getValue().lines >= 10);
			System.out.println( found );
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


// Brug anymatch i computeOccurences. 
// - så snart stream finder resultat, så er den lazy og terminerer. 
// try Files.lines( textfile -> )
// anymatch(line -Z line.length( > 10))

// import java.util.concurrent.CompletableFuture;
// import java.util.stream.Collectors;
// import java.util.stream.Stream;