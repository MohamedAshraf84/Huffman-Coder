package com.mohamed13ashraf.huffmancoder.MyDataStructures;

import java.util.Iterator;

public class SinglyLinkedList<T> implements Iterable<T> {
    private int size;
    private SLLNode<T> head;
    private SLLNode<T> tail;

    @Override
    public Iterator<T> iterator() {
        return new SLLIterator(this);
    }

    public SinglyLinkedList() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }


    public void add(T e) {
        SLLNode<T> newNode = new SLLNode<>(e, null);

        if (this.size != 0)
            tail.next = newNode;
        else
            head = newNode;

        tail = newNode;
        this.size++;
    }

    public void add(int index, T e) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        SLLNode<T> newNode = new SLLNode<>(e, null);

        SLLNode<T> current = head;
        for (int i = 0; i < index - 1; ++i)
            current = current.next;

        newNode.next = current.next;
        current.next = newNode;

        this.size++;
    }
    private SLLNode<T> getEntry(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        SLLNode<T> current = head;
        for (int i = 0; i < index; ++i)
            current = current.next;

        return current;
    }

    public T get(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        return getEntry(index).data;
    }

    public int size() {
        return this.size;
    }

    public void clear() {
        this.size = 0;
        this.head = null;
        this.tail = null;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public T remove(int index) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        T value;
        if (index == 0) {
            value = head.data;
            head = head.next;
        } else {
            SLLNode<T> current = head, previous = null;
            for (int i = 0; i < index; ++i) {
                previous = current;
                current = current.next;
            }
            previous.next = current.next;
            value = current.data;
        }

        this.size--;
        return value;
    }

    public void set(int index, T e) {
        if (index < 0 || index >= size)
            throw new IndexOutOfBoundsException();

        SLLNode<T> current = getEntry(index);
        current.data = e;
    }


    private static class SLLNode<T> {
        T data;
        SLLNode<T> next;
        public SLLNode(T data, SLLNode<T> next) {
            this.data = data;
            this.next = next;
        }
    }

    private class SLLIterator implements Iterator<T> {
        private SLLNode<T> next;
        SLLIterator(SinglyLinkedList<T> list) {
            this.next = list.head;
        }

        public boolean hasNext() {
            return this.next != null;
        }

        public T next() {
            T ret = next.data;
            next = next.next;
            return ret;
        }
    }
}