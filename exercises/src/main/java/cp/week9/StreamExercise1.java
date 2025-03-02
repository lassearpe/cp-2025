package cp.week9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class StreamExercise1
{
	/*
	- Create a file with many (>100) lines of text.
	  For example, you can use this website: loremipsum.io
	- Use Files.lines to get a stream of the lines contained within the file.
	- Use Stream::filter and Stream::forEach to print on screen each line that ends with a dot.
	*/


// Should they have their own line? Right now, it just removes the middle spacing. 
private static void printLinesWithDot(Path path) {
	try (Stream < String > lines = Files.lines(path) ) {
		lines
			.filter(line -> line.contains(". "))
			.forEach(System.out::println );
	} catch( IOException e ) {
		e.printStackTrace();
	} 
}

public static void main(String[] args) {
	Path file = Paths.get("exercises/src/main/java/cp/week9/text_101.txt"); 
	printLinesWithDot(file);
}

}
