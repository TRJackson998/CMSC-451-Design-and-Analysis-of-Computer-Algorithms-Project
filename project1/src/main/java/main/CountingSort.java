package main;
/*
 * Counting Sort
 * Terrence Jackson
 * UMGC CMSC 451
 * Project 1
 * 11.9.24
 * 
 * The bucket sorting algorithm
 * Reference: https://www.geeksforgeeks.org/counting-sort/
 */

public class CountingSort extends AbstractSort {

    @Override
    public void sort(int[] inout) throws UnsortedException {
        startSort();
        inout = countSort(inout);
        endSort();
        checkSort(inout);
    }

    /*
     * Function to sort inputArray[] of size n using count sort
     */
    public int[] countSort(int[] inputArray) {
        int n = inputArray.length;
        int max = 0;

        // find the maximum value in the array
        for (int i = 0; i < n; i++) {
            max = Math.max(max, inputArray[i]);
        }

        // create a new array of size max + 1
        int[] countArray = new int[max + 1];

        // at countArray index that equals input array value, count
        for (int i = 0; i < n; i++) {
            countArray[inputArray[i]]++;
        }

        // add in 'prefix sum' to help place elements in output
        for (int i = 1; i <= max; i++) {
            countArray[i] += countArray[i - 1];
        }

        // init output array
        int[] outputArray = new int[n];

        // fill in output array, starting at the end of the input array and using the
        // counting array
        for (int i = n - 1; i >= 0; i--) {
            outputArray[countArray[inputArray[i]] - 1] = inputArray[i];
            countArray[inputArray[i]]--;
            incrementCount();
        }

        return outputArray;
    }
}