package main;
/*
 * Bucket Sort
 * Terrence Jackson
 * UMGC CMSC 451
 * Project 1
 * 11.9.24
 * 
 * The bucket sorting algorithm
 * Reference: https://www.geeksforgeeks.org/bucket-sort-2/
 */

import java.util.ArrayList;
import java.util.List;

public class BucketSort extends AbstractSort {
    @Override
    public void sort(int[] inout) throws UnsortedException {
        startSort();
        inout = bucketSort(inout);
        endSort();
        checkSort(inout);
    }

    /*
     * Insertion sort function to sort individual buckets
     */
    public void insertionSort(List<Integer> bucket) {
        for (int i = 1; i < bucket.size(); ++i) {
            int key = bucket.get(i);
            int j = i - 1;
            while (j >= 0 && bucket.get(j) > key) {
                incrementCount();
                bucket.set(j + 1, bucket.get(j));
                j--;
            }
            bucket.set(j + 1, key);
        }
    }

    /*
     * Function to sort arr[] of size n using bucket sort
     */
    public int[] bucketSort(int[] arr) {
        int n = arr.length;

        // 1) Create n empty buckets
        List<Integer>[] buckets = new ArrayList[n];
        for (int i = 0; i < n; i++) {
            buckets[i] = new ArrayList<>();
        }

        // 2) Put array elements in different buckets
        for (int i = 0; i < n; i++) {
            int bi = (arr[i] / n); // changed from source to avoid overflowing array
            buckets[bi].add(arr[i]);
        }

        // 3) Sort individual buckets using insertion sort
        for (int i = 0; i < n; i++) {
            insertionSort(buckets[i]);
        }

        // 4) Concatenate all buckets into arr[]
        int index = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < buckets[i].size(); j++) {
                arr[index++] = buckets[i].get(j);
            }
        }

        return arr;
    }
}
