package interview.BlockingQueue;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@SuppressWarnings("NullableProblems")
public class FixedSizeBlockingQ<E> implements BlockingQueue<E> {

	private final ReentrantLock lock = new ReentrantLock();
	private final Condition emptyCondition = lock.newCondition();		//when empty we will wait on this object
	private final Condition fullCondition = lock.newCondition();		//when full we will wait on this object
	private final int maxSize;
	private final Queue<E> queue = new LinkedList<E>();
	
	
	public FixedSizeBlockingQ(int size) {
		this.maxSize = size;
	}

	@Override
	public E poll(long timeout, TimeUnit unit) throws InterruptedException {
		lock.lock();
		try {
			long timeToWait = unit.toNanos(timeout);
			//wait in a loop because awaitNanos may return but some other thread will get the item and size will be zero again
			while (queue.isEmpty()){
				timeToWait = emptyCondition.awaitNanos(timeToWait);
				if (timeToWait<=0) {
					//the specified waiting time elapses
					return null;
				}
			}

			@SuppressWarnings("unchecked")
			E result = queue.poll();
			fullCondition.signalAll();
			System.out.println("["+Thread.currentThread().getId()+"] poll - end - value=["+result+"] queue => " + queue.stream().map(Object::toString).collect(Collectors.joining(",")));
			return result;
		}
		finally {
			lock.unlock();
		}
	}

	@Override
	public boolean offer(E value, long timeout, TimeUnit unit) throws InterruptedException {
		lock.lock();
		try {
			long timeToWait = unit.toNanos(timeout);
			//wait in a loop because awaitNanos may return but some other thread will add the item and the queue will be full again
			while (queue.size()>=maxSize) {
				timeToWait = fullCondition.awaitNanos(timeToWait);
				if (timeToWait<=0) {
					//the specified waiting time elapses
					return false;
				}
			}
			queue.offer(value);
			emptyCondition.signalAll();
			return true;
		}
		finally {
			System.out.println("["+Thread.currentThread().getId()+"] offer - end - value=["+value+"] queue => " + queue.stream().map(e->e.toString()).collect(Collectors.joining(",")));
			lock.unlock();
		}
		
	}

	@Override
	public E remove() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public E poll() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public E element() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public E peek() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public int size() {
		return queue.size();
	}

	@Override
	public boolean isEmpty() {
		return queue.isEmpty();
	}

	@Override
	public Iterator<E> iterator() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public Object[] toArray() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public <T> T[] toArray(T[] a) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean addAll(Collection<? extends E> c) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public void clear() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean add(E e) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean offer(E e) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public void put(E e) throws InterruptedException {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public E take() throws InterruptedException {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public int remainingCapacity() {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean remove(Object o) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public boolean contains(Object o) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public int drainTo(Collection<? super E> c) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

	@Override
	public int drainTo(Collection<? super E> c, int maxElements) {
		throw new RuntimeException("UNIMPLEMENTED");
	}

}
