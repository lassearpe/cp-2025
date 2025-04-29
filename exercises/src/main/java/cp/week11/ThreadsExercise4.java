package cp.week11;

import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise4
{
	/*
	- Write the example from Listing 4.2 in the book.
	- Add a method that returns a reference to the internal field mySet.
	- Use the new method from concurrent threads to create unsafe access to mySet.
	*/

	// Own notes; //
	// Encapsulation simplifies making classes thread-safe by promoting instance-confinement.


	// Find ud af helt præcist: Hvad indebærer det, at skulle "return a reference" internal field?
	// Sikkert at den "escaper" på denne måde -> læs op på. 


}


// @ThreadSafe
class PersonSet {
	// @GuardedBy("this") -> Spørg victor omkring meningen af denne. 
	private final Set<Person> mySet = new HashSet<Person>();

	public synchronized void addPerson(Person p) {
		mySet.add(p);
	}
	
	public synchronized boolean containsPerson(Person p) {
		return mySet.contains(p);
	}

	// .. er det dette her, som menes?
	public synchronized Set<Person> getMySet() {
		return mySet;

	}

}