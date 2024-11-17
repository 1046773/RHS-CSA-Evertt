import java.util.Random;

public class AppMain {
    // The program should...
    //  Uses at least two methods.
    //  Picks a random die: 𝑑4, 𝑑6, 𝑑8, 𝑑10, 𝑑12, and 𝑑20.
    //  Then find the average of rolling that die rolled 𝟏𝟎𝟎 times.
    // 
    // Example output…
    //  Die: d8
    //  Average = 4.32

    private static final int ROLLCOUNT = 100;
    public static void main(String[] args) {
        int[] diceSides = {4, 6, 8, 10, 12, 20};
        int die = pickRandomDie(diceSides);
        double average = rollDieAndCalculateAverage(die);
        System.out.println("Die: d" + die);
        System.out.printf("Average = %.2f\n", average);
    }

    private static int pickRandomDie(int[] diceSides) {
        Random random = new Random();
        return diceSides[random.nextInt(diceSides.length)];
    }

    private static double rollDieAndCalculateAverage(int sides) {
        Random random = new Random();
        int total = 0;
        for (int i = 0; i < ROLLCOUNT; i++) {
            total += random.nextInt(sides) + 1;
        }
        return (double) total / ROLLCOUNT;
    }
}
