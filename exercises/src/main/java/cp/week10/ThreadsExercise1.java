package cp.week10;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise1
{
	/*
	- Create a Counter class storing an integer (a field called i), with an increment and decrement method.
	- Make Counter thread-safe (see Chapter 2 in the book)
	- Does it make a different to declare i private or public?
	*/


	public static void main(String[] args) throws InterruptedException {
		Counter test = new Counter();
		Thread t1 = new Thread( () -> {
			test.increment();
			test.increment();
			test.increment();
		}		
		); 
		Thread t2 = new Thread( () -> {
			test.decrement();
			test.decrement();
		}
		);
		
		t1.start();
		t2.start();
		t1.join();
		t2.join();

		System.out.println(test.x);
	}
}

class Counter {
	public int x = 0;

	public synchronized void increment() {
			x=x+1;
	}
		
	

	public synchronized void decrement() {
		x--;
	}
}
