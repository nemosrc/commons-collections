package com.nemosw.commons.collections;

import java.util.Queue;

public interface NodeQueue<E> extends NodeCollection<E>, Queue<E>
{

    Node<E> offerNode(E e);

    Node<E> peekNode();

}
