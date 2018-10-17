package com.nemosw.commons.collections;

import java.util.List;

public interface NodeList<E> extends NodeCollection<E>, List<E>
{

    Node<E> addNode(int index, E e);

    Node<E> getNode(int index);

}