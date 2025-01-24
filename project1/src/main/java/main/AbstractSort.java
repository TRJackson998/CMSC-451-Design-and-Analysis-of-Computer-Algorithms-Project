package main;
/*
 * Abstract Sort
 * Terrence Jackson
 * UMGC CMSC 451
 * Project 1
 * 11.9.24
 * 
 * An Abstract parent class for the two types of sorting algorithms
 */

public abstract class AbstractSort {
    private int count;
    private long time;
    private long startTime;

    public abstract void sort(int[] inout) throws UnsortedException;

    /*
     * called before the sort starts
     * initialize the counter and record the starting time of the sort
     */
    protected void startSort() {
        // initialize the counter
        count = 0;

        // record the starting time of the sort
        startTime = System.nanoTime();
    }

    /*
     * called after the sort ends
     * compute the elapsed time of the sort
     */
    protected void endSort() {
        // record the ending time of the sort
        long endTime = System.nanoTime();

        // compute the elapsed time of the sort
        time = (endTime - startTime);
    }

    /*
     * Check that the array is sorted
     * throw an Unsorted Exception if it is not
     */
    protected void checkSort(int[] sorted) throws UnsortedException {
        if (sorted.length > 1) {
            for (int i = 1; i < sorted.length; i++) {
                if (sorted[i - 1] > sorted[i]) {
                    throw new UnsortedException();
                }
            }
        }
    }

    /*
     * increment the critical operation counter
     */
    protected void incrementCount() {
        count += 1;
    }

    /*
     * return the final value of the counter
     */
    public int getCount() {
        return count;
    }

    /*
     * return the elapsed time
     */
    public long getTime() {
        return time;
    }
}
