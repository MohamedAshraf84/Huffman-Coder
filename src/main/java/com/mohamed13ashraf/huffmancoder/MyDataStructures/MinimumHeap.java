package com.mohamed13ashraf.huffmancoder.MyDataStructures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MinimumHeap<T extends Comparable<T>> {


    /**
        Minimum Heap Array-Based Implementation AS a Priority Queue Data Structure.
     **/

    private int size;
    private final List<T> heap;

    public MinimumHeap()
    {
        this.size = 0;
        this.heap = new ArrayList<>();
    }

    public boolean isEmpty()
    {
        return this.size == 0;
    }

    public void add(T node)
    {
        heap.add(size, node);
        if(size > 0) {
            heapifyUp(size);
        }
        size++;
    }

    public <T> T poll()
    {
        T min = (T) heap.get(0);
        heap.set(0, heap.get(size - 1));
        heapifyDown(0);
        size--;

        return min;
    }

    private void heapifyUp(int size)
    {
        int p = parent(size);
        if(p == -1)
           return;

        if (heap.get(size).compareTo(heap.get(p)) > 0)
            return;

        Collections.swap(heap, size, p);
        heapifyUp(p);
    }

    private void heapifyDown(int node)
    {
        int l = left(node);
        int r = right(node);

        if(l == -1 || r == -1)
            return;

        int diff = heap.get(l).compareTo(heap.get(r));

        if(diff <= 0 && heap.get(node).compareTo(heap.get(l)) >= 0 )
        {
            Collections.swap(heap, node, l);
            heapifyDown(l);
        }

        if(diff > 0 && heap.get(node).compareTo(heap.get(r)) >= 0 )
        {
            Collections.swap(heap, node, r);
            heapifyDown(r);
        }
    }

    private int parent(int child)
    {
        return  child == 0 ? -1 : (child - 1) / 2;
    }
    private int left(int parent)
    {
        int p = parent * 2 + 1;
        return p >= size ? -1 : p;
    }

    private int right(int parent)
    {
        int p = parent * 2 + 2;
        return p >= size ? -1 : p;
    }
    public int size()
    {
        return this.size;
    }

}