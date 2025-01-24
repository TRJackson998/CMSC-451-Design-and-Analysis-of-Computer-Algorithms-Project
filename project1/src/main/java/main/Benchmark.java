package main;
/*
 * Benchmark Sorts
 * Terrence Jackson
 * UMGC CMSC 451
 * Project 1
 * 11.11.24
 * 
 * The driver for benchmarking the two sorting algorithms
 * Generates two text files full of data on the sorts
 */

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Benchmark {
    // seed the random number generator
    private static Random rand = new Random();

    public static void main(String[] args) throws UnsortedException, IOException {
        FileWriter bucketWriter = new FileWriter("bucket.txt");
        FileWriter countingWriter = new FileWriter("counting.txt");
        BucketSort bucket = new BucketSort();
        CountingSort counting = new CountingSort();

        // run 10 warmup sorts
        for (int i = 0; i < 10; i++) {
            int[] inout = generateData(100);
            bucket.sort(inout);
            counting.sort(inout);
        }

        // run 12 different input sizes
        for (int i = 1; i <= 12; i++) {
            int n = i * 100; // generate input size

            // init strings to print a line to the to txt
            StringBuilder bucketString = new StringBuilder();
            StringBuilder countingString = new StringBuilder();

            // add the input size to the string
            bucketString.append(String.format("%d ", n));
            countingString.append(String.format("%d ", n));

            // run 40 sorts for this input size
            for (int j = 0; j < 40; j++) {
                // get new random data
                int[] inout = generateData(n);

                // run both sorts
                bucket.sort(inout);
                counting.sort(inout);

                // save the output to the string for this line
                bucketString.append(String.format("%d %d ", bucket.getCount(), bucket.getTime()));
                countingString.append(String.format("%d %d ", counting.getCount(), bucket.getTime()));
            }

            // add a newline after the 40 runs of this input size
            bucketString.append("\n");
            countingString.append("\n");

            // write this line to the txt file
            bucketWriter.write(bucketString.toString());
            countingWriter.write(countingString.toString());
        }

        // all 12 lines have been written, close the writers
        bucketWriter.close();
        countingWriter.close();
    }

    /*
     * Given an integer for the size of the array,
     * generate and return an array of random data
     */
    private static int[] generateData(int size) {
        int[] output = new int[size];
        for (int i = 0; i < size; i++) {
            // get the next random int and mod it to reduce the size
            output[i] = rand.nextInt() % 10000;

            // make sure it isn't negative
            if (output[i] < 0)
                output[i] *= -1;
        }
        return output;
    }
}
