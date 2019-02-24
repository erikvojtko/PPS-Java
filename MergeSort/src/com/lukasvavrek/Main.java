package com.lukasvavrek;

public class Main {

    public static void main(String[] args) {
        int length = 5_000_000;

        for (int i = 1; i <= 10; i++) {
            float[] unsorted = new SortingPreparator().prepare(length);

            Stopwatch watch = new Stopwatch();
            new ParallelMergeSorter().sort(unsorted, i);

            double stop = watch.elapsedTime();

            System.out.format("%d - %2d threads: %f seconds.\n", length, i, stop);
        }
    }
}
