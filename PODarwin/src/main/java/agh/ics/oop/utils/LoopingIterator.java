package agh.ics.oop.utils;

import java.util.Iterator;
import java.util.List;

public class LoopingIterator<T> implements Iterator<T> {
    private final List<T> elements;
    private int ptr;

    public LoopingIterator(List<T> l, int startOffset) {
        if (l.isEmpty()) {
            throw new IllegalArgumentException("List to loop through can't be empty");
        }
        elements = l;
        ptr = startOffset;
    }

    public LoopingIterator(List<T> l) {
        this(l, 0);
    }

    @Override
    public boolean hasNext() {
        return true;
    }

    @Override
    public T next() {
        var elem = elements.get(ptr);
        ptr = (ptr + 1) % elements.size();
        return elem;
    }
}
