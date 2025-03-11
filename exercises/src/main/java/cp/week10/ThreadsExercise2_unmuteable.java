package cp.week10;

/**
 *
 * @author Fabrizio Montesi <fmontesi@imada.sdu.dk>
 */
public class ThreadsExercise2_unmuteable
{
	/*
	- Read and replicate the examples in: https://docs.oracle.com/javase/tutorial/essential/concurrency/immutable.html
	*/


    public static void main(String[] args) {
        ImmutabledRGB rgb = new ImmutabledRGB(20, 30, 100, "Lasses farve");
        

        int myColorInt = rgb.getRGB();      //Statement 1
        String myColorName = rgb.getName(); 
        System.out.println(myColorInt);
        System.out.println(myColorName);
    


        ImmutabledRGB rgb_inverted = rgb.invert();
        String myColorName2 = rgb_inverted.getName(); //Statement 2

        int myColorInt2 = rgb_inverted.getRGB();      //Statement 1

        System.out.println(myColorInt2);
        System.out.println(myColorName2);
    }

}


final class ImmutabledRGB {

    // Values must be between 0 and 255.
    final private int red;
    final private int green;
    final private int blue;
    final private String name;

    private void check(int red,
                       int green,
                       int blue) {
        if (red < 0 || red > 255
            || green < 0 || green > 255
            || blue < 0 || blue > 255) {
            throw new IllegalArgumentException();
        }
    }

    public ImmutabledRGB(int red,
                           int green,
                           int blue,
                           String name) {
        check(red, green, blue);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.name = name;
    }

    /* No place in immuteable object for second setter.*/

    // public void set(int red,
    //                 int green,
    //                 int blue,
    //                 String name) {
    //     check(red, green, blue);

    //     synchronized (this) {
    //         this.red = red;
    //         this.green = green;
    //         this.blue = blue;
    //         this.name = name;
    //     }
    // }

     public int getRGB() {
        return ((red << 16) | (green << 8) | blue);
    }

     public String getName() {
        return name;
    }

     public ImmutabledRGB invert() {
        return new ImmutabledRGB(255 - red, 255 - green, 255 - blue, "Inverse of " + name);
    }
}