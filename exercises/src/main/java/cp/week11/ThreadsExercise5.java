package cp.week11;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise5 {
	/*
	 * Apply the technique for fixing Listing 4.14 to Listing 4.15 in the book, but
	 * to the following:
	 * - Create a thread-safe Counter class that stores an int and supports
	 * increment and decrement.
	 * - Create a new thread-safe class Point, which stores two Counter objects.
	 * - The two counter objects should be public.
	 * - Implement the method boolean areEqual() in Point, which returns true if the
	 * two counters store the same value.
	 * 
	 * Question: Is the code you obtained robust with respect to client-side locking
	 * (see book)?
	 * Would it help if the counters were private?
	 * 
	 * 
	 * Client side locking;
	 * - Client-side locking entails guarding client code that uses some object X
	 * with the lock X uses to guard its own state.
	 * 
	 * -
	 * 
	 * VIGTIGT -> In order to use client-side locking, you must know what lock X uses.
	 * 
	 * 
	 * DENNE HER VIL DU GERNE HAVE HJÆLP TIL AT FORSTÅ...
	 * 
	 * 
	 * 
	 */
	public static void main(String[] args) {
		Point point = new Point();

		point.counter2.increment();

		System.out.println(point.counter2.getX());
		System.out.println(point.counter1.getX());

		System.out.println(point.areEqual());
	}
}

class Counter {
	private int x = 0;

	public int getX() {
		return x;
	}

	public synchronized void increment() {
		x = x + 1;
	}

	public synchronized void decrement() {
		x = x + 1;
	}

}

class Point {
	// Er de stadigvæk thread safe, når vi har lavet dem public?
	// De er implementeret som private nemlig.

	// I dette tilfælde er det faktisk ikke okay. -> Se din forkerte løsning. 


	// Synkroniser på sig selv
	public Counter counter1 = new Counter();
	public Counter counter2 = new Counter();

	public boolean areEqual() {
		synchronized (counter1) {
			synchronized (counter2) {
				return counter1.getX() == counter2.getX();
			}
		}
	}

	// FORKERT LØSNING NEDENUNDER:

	// // Synkronierer på point (this)
	// // Nogle kan godt ændre på counter2, imens du laver dette tjek.

	// public synchronized boolean areEqual() {

	// return counter1.getX() == counter2.getX();
	// }

	// Forskel på, hvad og hvor vi synkronierer ->
}

/* Eksempel:
 * Synchronized MyMethod { }
 * 
 * 
 * oversættes til, af compiler
 *  
 * myMessage( {
 * Synchronized(this)})
 * 
 * 
 * -> this refererer til ens egen klasse -> SOM KAN HAVE ATTRIBUTER INDENI SIG!
 * NÅR VI LÅSER EN KLASSE, LÅSER VI IKKE ALLE ATTRIBUTER!
 * Synchronized på equal låser ikke counter objekterne.
 * 
 * Så længe én mutater et sted, kan der opstå et problem.
 * 
 * -> Client side locking:
 * 	Brugeren af attributer skal have en sikkerhed; hvis de enkelte attributer bruges i forskellige metoder. 
 *  -> Det er ikke nok at synkronisere på hele klassen, men der skal synkroniseres på attributerne. 
 * 
 */
