package cp.week9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamExercise3
{
	/*
	- Create a stream of lines for the file created in StreamExercise1.
	- Use Stream::filter and Stream::count to count how many lines
	  contain the letter "L".
	*/

	private static void printCount(Path path) {
	try (Stream < String > lines = Files.lines(path) ) {
		System.out.println(lines
		.filter(line -> line.contains("L"))
		.count());

	} catch( IOException e ) {
		e.printStackTrace();
	} 
}

public static void main(String[] args) {
	Path file = Paths.get("exercises/src/main/java/cp/week9/text_101.txt"); 
	printCount(file);
}	


}
