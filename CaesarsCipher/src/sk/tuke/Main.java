package sk.tuke;

import java.io.*;

public class Main {

    public static void main(String[] args) {
        Timer timer = new Timer();
        String filePath = "input2.txt";

        timer.start();
        System.out.println("File length: " + decipher(filePath, 2));
        System.out.println("Ciphering sequentially done in : " + timer.finish() + " ms.");

        timer.start();
        System.out.println("File length: " + decipherConcurrently(filePath, 2, 2));
        System.out.println("Ciphering concurrently done in : " + timer.finish() + " ms.");
    }

    public static int decipher(String path, int key) {
        StringBuilder builder = new StringBuilder();
        try (FileInputStream fis = new FileInputStream(new File(path))) {
            char current;
            while (fis.available() > 0) {
                current = (char) fis.read();
                builder.append(decipherCharacter(current, key));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.length();
    }

    public static int decipherConcurrently(String file, int key, int threadCount) {
        if(threadCount < 1){
            throw new IllegalArgumentException("At least one thread has to be used");
        }
        try {
            RandomAccessFile raf = new RandomAccessFile(file, "r");
            int marker = (int) (2 * (Math.floor(Math.abs((raf.length() / threadCount) / 2))));
            int length = (int) raf.length();

            StringConnector connector = new StringConnector(threadCount);

            StringBuilder[] results = connector.getPartialResults();

            Thread[] threads = new Thread[threadCount];

            for (int i = 0; i < threadCount; i++) {
                final int multiplier = i;
                final int start = marker * multiplier;
                final int end;
                if ((i + 1) == threadCount) {
                    end = length;
                } else {
                    end = marker * (multiplier + 1);
                }

                threads[i] = new Thread(() -> partialDecipher(key, start, end, results[multiplier], raf));

                threads[i].start();
            }

            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            StringBuilder result = new StringBuilder();
            for (StringBuilder partial : results) {
                result.append(partial);
            }

            return result.length();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return 0;
    }

    public static StringBuilder partialDecipher(int key, int start, int end, StringBuilder result, RandomAccessFile raf) {
        for (int i = start; i < end; i++) {
            try {
                result.append(decipherCharacter( (char) raf.readByte(), key));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
//        System.out.println(result);
        return result;
    }

    public static char decipherCharacter(char current, int key) {
        if (current < 91 && current > 64) {
            current -= key;
            if (current < 65) {
                current += 26;
            }
        } else if (current < 123 && current > 96) {
            current-= key;
            if (current < 97) {
                current += 26;
            }
        }
        return current;
    }
}
