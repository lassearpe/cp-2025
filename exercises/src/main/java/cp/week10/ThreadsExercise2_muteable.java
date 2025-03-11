package cp.week10;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise2_muteable
{
	/*
	- Read and replicate the examples in: https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html
	*/


    public static void main(String[] args) {
        SynchronizedRGB rgb = new SynchronizedRGB(20, 30, 100, "Lasses farve");
        
        
        synchronized (rgb) {
            int myColorInt = rgb.getRGB();      //Statement 1
            String myColorName = rgb.getName(); 
            System.out.println(myColorInt);
            System.out.println(myColorName);
        }


        rgb.set(100, 100, 100, "lasses nye farve");
        String myColorName2 = rgb.getName(); //Statement 2

        int myColorInt2 = rgb.getRGB();      //Statement 1

        System.out.println(myColorInt2);
        System.out.println(myColorName2);
    }

}


class SynchronizedRGB {

    // Values must be between 0 and 255.
    private int red;
    private int green;
    private int blue;
    private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
            || green < 0 || green > 255
            || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public SynchronizedRGB(int red,
                           int green,
                           int blue,
                           String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    public void set(int red,
                    int green,
                    int blue,
                    String name) {
        check(red, green, blue);

        synchronized (this) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.name = name;
        }
    }

    public synchronized int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

    public synchronized String getName() {
        return name;
    }

    public synchronized void invert() {
        red = 255 - red;
        green = 255 - green;
        blue = 255 - blue;
        name = "Inverse of " + name;
    }
}