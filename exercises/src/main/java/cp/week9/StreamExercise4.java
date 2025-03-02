package cp.week9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamExercise4
{
	/*
	- Create a stream of lines for the file created in StreamExercise1.
	- Use Stream::mapToInt and IntStream::sum to count how many times
	  the letter "C" occurs in the entire file.
	*/

private static void countCinFile(Path path) {
	try (Stream < String > lines = Files.lines(path) ) {
		long lines2 = lines
			.map(String::toUpperCase)
			// .flatMap
			.filter(c -> c.equals("C"))
			.count();
			// .mapToInt(String::length)
			// .sum();
			// .mapToInt(IntStream::sum);

	System.out.println(lines2);


	} catch( IOException e ) {
		e.printStackTrace();
	} 
}

public static void main(String[] args) {
	Path file = Paths.get("exercises/src/main/java/cp/week9/text_101.txt"); 
	countCinFile(file);
}

}
