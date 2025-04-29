package cp;
import java.util.concurrent.ExecutionException;

import cp.week15.ThreadsExercise13;
import cp.week15.ThreadsExercise14;


/**
 * Main class (entry point) of the Java Application.
 */
public final class Testing {
	/**
	 * Entry point method of the Java application.
	 * 
	 * @param args The arguments of the program.
	 */
	public static void main( String[] args ) {
		// VirtualThreads1.main();
		// VirtualThreads2.main();
		// Utils.doAndMeasure(() -> {
        //     try {
        //         ThreadsExercise13.main();
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // });

		ThreadsExercise14.main();

		
	}
}
