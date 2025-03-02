package cp.week9;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamExercise2
{
	/*
	- Create a stream of lines for the file created in StreamExercise1.
	- Use Stream::filter and Stream::collector (the one with three parameters)
	  to create an ArrayList of all lines that start with a "C".
	- Suggestion: look at https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html#collect-java.util.function.Supplier-java.util.function.BiConsumer-java.util.function.BiConsumer-
	*/

private static void lineStartWithC(Path path) {
	try (Stream < String > lines = Files.lines(path) ) {
		ArrayList<String> filteredLines = lines
			.filter(line -> line.startsWith("C"))
			.collect(() -> new ArrayList<String>(), 
			(list,line) -> list.add(line),
			(list1, list2) -> list1.addAll(list2));

	System.out.println(filteredLines);

	} catch( IOException e ) {
		e.printStackTrace();
	} 

}


public static void main(String[] args) {
	Path file = Paths.get("exercises/src/main/java/cp/week9/text_101.txt");
	lineStartWithC(file);
}


}
