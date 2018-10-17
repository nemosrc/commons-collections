package com.nemosw.commons.collections;

public interface Node<E>
{

    E getItem();

    E setItem(E item);

    Node<E> previous();

    Node<E> next();

    Node<E> linkBefore(E item);

    Node<E> linkAfter(E item);

    boolean isLinked();

    boolean unlink();

    void clear();

}
