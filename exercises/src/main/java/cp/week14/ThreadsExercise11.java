package cp.week14;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise11
{
	/*
	 * Modify Threads/NotifyWait such that it has a (new) third thread printing on screen,
	
	 * and such that the third thread waits for Thread t1 to be finished printing on screen before doing anything.
	
	NOTE: Du kan meget nemt gøre det, således det eneste krav er at få T1 før T3. 
	Hvis det forrige krav om T2 før T3 dog skal holde, er det væsentligt sværere. 
	
	Spørg victor: I din nuværende metode, hvordan kan T1 nogengange komme før T2?
	 
	// latch implementert via notify wait-primitiven. 
	*/



	public static void main(String[] args) {
		ThreadsExercise11.ThreeThreads();
	}

	private static final Object monitor = new Object();
	private static final Object monitor2 = new Object();
	
	private static boolean t1Done = false;
	private static boolean t2Done = false;
	
	public static void ThreeThreads()
	{
		Thread t1 = new Thread( () -> {
			synchronized( monitor ) {

				if (!t2Done) {
					try {
						monitor.wait();
					} catch( InterruptedException e ) {
						e.printStackTrace();
					}
				}

				System.out.println( "Hello from t1" ); // wait pauser del af program, giver andre lov til at komme ind i sync block -> notify, "når jeg er kaldes, slip én løs"
			}
			
			synchronized (monitor2) {
				monitor2.notify();
				t1Done = true;
			}


		} );
		
		Thread t2 = new Thread( () -> {

			synchronized( monitor ) {
				monitor.notify();
				t2Done = true;
				System.out.println( "Hello from t2" );
			}
		} );

		Thread t3 = new Thread( () -> {

			synchronized( monitor2 ) {

				if ( !t1Done ) {
					try {
						monitor2.wait();
					} catch( InterruptedException e ) {
						e.printStackTrace();
					}
				}
				System.out.println( "Hello from t3" );
				// monitor.notify();
			}

		} );
		
		t1.start();
		t2.start();
		t3.start();

		try {
			t1.join();
			t2.join();
			t3.join();
		} catch( InterruptedException e ) {
			e.printStackTrace();
		}
	}
}
