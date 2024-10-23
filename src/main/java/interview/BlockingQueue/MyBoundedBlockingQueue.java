package interview.BlockingQueue;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * <pre>
 * <B>Copyright:</B>   Izik Golan
 * <B>Owner:</B>       <a href="mailto:golan2@hotmail.com">Izik Golan</a>
 * <B>Creation:</B>    16/07/14 08:07
 * <B>Since:</B>       BSM 9.21
 * <B>Description:</B>
 *
 * </pre>
 */
public class MyBoundedBlockingQueue<E> implements BlockingQueue<E> {
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();
    private final int bound;
    private final Queue<E> queue;

    public MyBoundedBlockingQueue(int bound) {
        this.bound = bound;
        queue = new ArrayDeque<>(bound);
    }

    @Override
    public boolean add(E e) {
        lock.writeLock().lock();
        try {
            return queue.add(e);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public boolean offer(E e) {
        lock.writeLock().lock();
        try {
            return queue.offer(e);
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public E remove() {
        lock.writeLock().lock();
        try {
            return queue.remove();
        }
        finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public E poll() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public E element() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public E peek() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void put(E e) throws InterruptedException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit) throws InterruptedException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public E take() throws InterruptedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public E poll(long timeout, TimeUnit unit) throws InterruptedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int remainingCapacity() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean remove(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void clear() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int size() {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isEmpty() {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean contains(Object o) {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Iterator<E> iterator() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Object[] toArray() {
        return new Object[0];  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int drainTo(Collection<? super E> c) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int drainTo(Collection<? super E> c, int maxElements) {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
