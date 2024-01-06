package agh.ics.oop.utils;

import agh.ics.oop.model.classes.Boundary;
import agh.ics.oop.model.classes.Vector2D;

import java.util.*;

public class NumbersRange {
    private final int countOfNumbers;
    private final int maxNumber;
    List<Integer> numbers;

    public NumbersRange(int countOfNumbers, int maxNumber) {
        this.countOfNumbers = countOfNumbers;
        this.maxNumber = maxNumber;
        this.numbers = generateAllPositions();
    }

    private List<Integer> generateAllPositions() {
        List<Integer> positions = new LinkedList<>();
        for (int i = 0; i < this.maxNumber; i++) {
            positions.add(i);
        }
        Collections.shuffle(positions);
        return positions.subList(0, Math.min(countOfNumbers, positions.size()));
    }

    public List<Integer> getNumbers() {
        return numbers;
    }
}
