package it.frascu.adaventcode;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Day05 {

    private static final String INPUT_FILE_PATH = "input/day05.txt";

    public static void main(String[] args) {
        System.out.println("------ day 05 ------");
        try (Scanner scanner = new Scanner(new File(INPUT_FILE_PATH))) {
            int rows = 128;
            int columns = 8;
            Map<Integer, Set<Integer>> seats = new HashMap<>();
            int highestSeatId = Integer.MIN_VALUE;
            while (scanner.hasNext()) {
                char[] seatPosition = scanner.next().toCharArray();

                int minRow = 0;
                int maxRow = rows - 1;
                for (int i = 0; i < 7; i++) {
                    char direction = seatPosition[i];
                    int calcHalf = minRow + (maxRow - minRow + 1) / 2;
                    if (direction == 'F') {
                        maxRow = calcHalf - 1;
                    } else if (direction == 'B') {
                        minRow = calcHalf;
                    }
                }

                int minCol = 0;
                int maxCol = columns - 1;
                for (int i = 7; i < seatPosition.length; i++) {
                    char direction = seatPosition[i];
                    int calcHalf = minCol + (maxCol - minCol + 1) / 2;
                    if (direction == 'L') {
                        maxCol = calcHalf - 1;
                    } else if (direction == 'R') {
                        minCol = calcHalf;
                    }
                }

                // part one
                int seatId = minRow * columns + minCol;
                if (highestSeatId < seatId) {
                    highestSeatId = seatId;
                }

                // part two
                Set<Integer> cols = seats.computeIfAbsent(minRow, k -> new HashSet<>());
                cols.add(minCol);
            }

            System.out.println("----- part one -----");
            System.out.println(highestSeatId);

            // part two
            for (int i = 0; i < rows; i++) {
                Set<Integer> cols = seats.get(i);
                if (cols != null) {
                    for (int j = 0; j < columns; j++) {
                        if (!cols.contains(j)
                                // check that -1 exists
                                && seats.containsKey(i - 1)
                                && seats.get(i - 1).contains(j)
                                // check that +1 exists
                                && seats.containsKey(i + 1)
                                && seats.get(i + 1).contains(j)
                        ) {
                            System.out.println("----- part two -----");
                            System.out.println(i * columns + j);
                        }
                    }
                }

            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

}
