package com.nemosw.commons.collections;

import java.util.Collection;

public interface NodeCollection<E> extends Collection<E>
{

    Node<E> addNode(E e);

    Node<E> getNode(Object o);

}
