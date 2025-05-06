package cp;
import java.util.concurrent.ExecutionException;

import cp.week15.ThreadsExercise13;
import cp.week15.ThreadsExercise14;
import cp.week15.ThreadsExercise16;
import cp.week15.ThreadsExercise17;
import cp.week15.ThreadsExercise18;
import cp.week17.ThreadsExercise19;
import cp.week17.ThreadsExercise20;
import cp.week17.ThreadsExercise21;


/**
 * Main class (entry point) of the Java Application.
 */
public final class Testing {
	/**
	 * Entry point method of the Java application.
	 * 
	 * @param args The arguments of the program.
	 * @throws ExecutionException 
	 * @throws InterruptedException 
	 */
	public static void main( String[] args ) throws InterruptedException, ExecutionException {
		// VirtualThreads1.main();
		// VirtualThreads2.main();
		// Utils.doAndMeasure(() -> {
        //     try {
        //         ThreadsExercise13.main();
        //     } catch (ExecutionException e) {
        //         e.printStackTrace();
        //     }
        // });

		// ThreadsExercise14.main();

		// ThreadsExercise17.main();
		// ThreadsExercise18.main();
		// ThreadsExercise19.main();
		// ThreadsExercise20.main();
		ThreadsExercise21.main();

		
	}
}
