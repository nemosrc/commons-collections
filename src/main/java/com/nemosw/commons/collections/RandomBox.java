package com.nemosw.commons.collections;

import java.util.ArrayList;
import java.util.Random;

/**
 * @author Nemo
 */
public class RandomBox<E>
{

    private final ArrayList<Item<E>> items;

    private int bandwidth;

    private final Random random;

    public RandomBox()
    {
        this.items = new ArrayList<>();
        this.random = new Random();
    }

    public RandomBox(Random random)
    {
        if (random == null)
            throw new NullPointerException("Random cannot be null");

        this.items = new ArrayList<>();
        this.random = random;
    }

    public RandomBox(int initialCapacity)
    {
        this.items = new ArrayList<>(initialCapacity);
        this.random = new Random();
    }

    public RandomBox(int initialCapacity, Random random)
    {
        if (random == null)
            throw new NullPointerException("Random cannot be null");

        this.items = new ArrayList<>(initialCapacity);
        this.random = random;
    }

    public void trim()
    {
        this.items.trimToSize();
    }

    public void add(E e, int frequency)
    {
        if (e == null)
            throw new NullPointerException();
        if (frequency <= 0)
            throw new IllegalArgumentException("Illegal frequency " + frequency);
        int bandwidth = this.bandwidth + frequency;
        if (bandwidth < 0) // check overflow
            throw new IllegalStateException("overflowed bandwidth");

        Item item = new Item(e, frequency);
        this.items.add(item);
        this.bandwidth = item.bandwidth = bandwidth;
    }

    public int getBandwidth()
    {
        return bandwidth;
    }

    private int toIndex(int target)
    {
        ArrayList<Item<E>> items = this.items;
        int low = 0;
        int high = items.size() - 1;

        while (low <= high)
        {
            int mid = (low + high) >>> 1;

            if (target >= items.get(mid).bandwidth)
                low = mid + 1;
            else if (mid > 0 && target <= items.get(mid - 1).bandwidth)
                high = mid - 1;
            else
                return mid;
        }

        return low;
    }

    private int nextIndex()
    {
        return toIndex(this.random.nextInt(this.bandwidth));
    }

    public E draw()
    {
        if (this.bandwidth > 0)
            return remove(nextIndex());

        return null;
    }

    public E draw(int frequency)
    {
        if (this.bandwidth > 0)
            return remove(toIndex(frequency));

        return null;
    }

    public E remove(int index)
    {
        E item = this.items.remove(index).item;
        recalculateBandwidth(index);

        return item;
    }

    public E peek()
    {
        return this.bandwidth > 0 ? this.items.get(nextIndex()).item : null;
    }

    public E peek(int frequency)
    {
        return this.bandwidth > 0 ? this.items.get(toIndex(frequency)).item : null;
    }

    public boolean remove(Object o)
    {
        if (o == null)
            throw new NullPointerException();

        for (int i = 0, size = items.size(); i < size; i++)
        {
            Item<E> item = items.get(i);

            if (o.equals(item.item))
            {
                items.remove(i);
                recalculateBandwidth(i);

                return true;
            }
        }

        return false;
    }

    public void clear()
    {
       this.items.clear();
       this.bandwidth = 0;
    }

    private void recalculateBandwidth(int index)
    {
        int bandwidth = index > 0 ? this.items.get(index - 1).bandwidth : 0;

        for (int i = index, size = this.items.size(); i < size; i++)
        {
            Item item = this.items.get(i);
            item.bandwidth = bandwidth += item.frequency;
        }

        this.bandwidth = bandwidth;
    }

    static class Item<E>
    {
        final E item;

        final int frequency;

        int bandwidth;

        Item(E item, int frequency)
        {
            this.item = item;
            this.frequency = frequency;
        }
    }
}
