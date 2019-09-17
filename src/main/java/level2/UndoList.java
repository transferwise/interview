package level2;

import java.util.*;

interface Undoable {
    void undo();
}

interface UndoAction {
    void execute();
}

public class UndoList<T> implements List<T>, Undoable {

    class UndoInsertAction<T> implements UndoAction {

        private T item;

        public UndoInsertAction(T item) {
            this.item = item;
        }

        @Override
        public void execute() {
            list.remove(item);
        }
    }

    class UndoDeleteAction implements UndoAction {

        private Object element;

        public UndoDeleteAction(Object element) {
            this.element = element;
        }

        @Override
        public void execute() {
            list.add((T) element);
        }
    }

    private final List<T> list;
    private final Stack<UndoAction> undoActions = new Stack<>();

    public UndoList(List<T> list) {
        this.list = list;
    }

    public void undo() {
        undoActions.pop().execute();
    }

    @Override
    public int size() {
        return list.size();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return list.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return list.toArray();
    }

    @Override
    public <T1> T1[] toArray(T1[] a) {
        return list.toArray(a);
    }

    @Override
    public boolean add(T t) {
        undoActions.add(new UndoInsertAction(t));
        return list.add(t);
    }

    @Override
    public boolean remove(Object o) {
        undoActions.add(new UndoDeleteAction(o));
        return list.remove(o);
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return list.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        return list.addAll(c);
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return list.removeAll(c);
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return list.retainAll(c);
    }

    @Override
    public void clear() {
        list.clear();
    }

    @Override
    public T get(int index) {
        return list.get(0);
    }

    @Override
    public T set(int index, T element) {
        return list.set(index, element);
    }

    @Override
    public void add(int index, T element) {
        list.add(index, element);
    }

    @Override
    public T remove(int index) {
        return list.remove(index);
    }

    @Override
    public int indexOf(Object o) {
        return list.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return list.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return list.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return list.listIterator(index);
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return list.subList(fromIndex, toIndex);
    }
}
