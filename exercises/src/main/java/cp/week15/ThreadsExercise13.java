package cp.week15;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise13
{
	/*
	Modify ThreadsExercise9 to use executors.
	Try different kinds of executor (cached thread pool or fixed thread pool) and different fixed pool sizes.
	Which executor runs faster?
	Can you explain why?
	*/


	Fjern latch, brug executer. Ikke thread, submit til executor. 
	executor.newWorkingStealingPoll();
	threads kan stjæle jobs fra hinanden. 
}
