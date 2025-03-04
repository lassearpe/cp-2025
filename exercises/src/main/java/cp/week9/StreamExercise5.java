package cp.week9;

import java.nio.file.Files;
import java.util.stream.Stream;

public class StreamExercise5
{
	/* ! (Exercises marked with ! are more difficult.)
	
	- Create a stream of lines for the file created in StreamExercise1.
	- Use Stream::map to map each line to a HashMap<String, Integer> that
	  stores how many times each character appears in the line.
	  For example, for the line "abbc" you would produce a map with entries:
	    a -> 1
	    b -> 2
	    c -> 1
	- Use Stream::reduce(T identity, BinaryOperator<T> accumulator)
	  to produce a single HashMap<String, Integer> that stores
	  the results for the entire file.
	*/

private static void countCharacters (Path path) {
	try ( Stream <String> lines = Files.lines(path) ) {
		lines
			.flatMap( line -> Stream.of( line.split(" ")))
			.map( word -> 
			Map<String, Integer> m = new HashMap<>();
			m.put(word,1);
			return m;
			) 
	}
}

}
