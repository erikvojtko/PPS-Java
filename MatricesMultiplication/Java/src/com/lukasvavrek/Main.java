package com.lukasvavrek;

public class Main {

    public static void main(String[] args) {
        int m = 2000;
        int n = 2000;

        Matrix m1 = new Matrix(m, n, true);
        Matrix m2 = new Matrix(n, m, true);

        m1.print();
        m2.print();

        for (int i = 1; i <= 5; i++) {
            Stopwatch watch = new Stopwatch();
            MatrixMultiplicator multiplicator = new MatrixMultiplicator(i);
            Matrix result = multiplicator.multiply(m1, m2);
            double stop = watch.elapsedTime();
            System.out.format("%dx%d - %d threads: %f seconds.\n", m, n, i, stop);
            result.print();
        }
    }
}
