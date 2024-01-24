package io.github.chiangkaishek327.ithinkiuf.observable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import io.github.chiangkaishek327.ithinkiuf.observable.Observable.Listener.Event;
import io.github.chiangkaishek327.ithinkiuf.utils.ArrayUtil;

public class ObservableList<T> implements List<T>, Observable<List<T>> {
    List<T> con = new ArrayList<>();
    Listener<List<T>> listener = (e, o, n) -> {
    };

    @Override
    public Listener<List<T>> getChangeListener() {
        return listener;
    }

    @Override
    public void setChangeListener(Listener<List<T>> listener) {
        this.listener = listener;
    }

    @Override
    public int size() {
        return con.size();
    }

    @Override
    public boolean isEmpty() {
        return con.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        return con.contains(o);
    }

    @Override
    public Iterator<T> iterator() {
        return con.iterator();
    }

    @Override
    public Object[] toArray() {
        return con.toArray();
    }

    @SuppressWarnings("all")
    @Override
    public <T> T[] toArray(T[] a) {
        return con.toArray(a);
    }

    @Override
    public boolean add(T e) {
        List<T> old = ArrayUtil.cloneList(con);
        boolean v = con.add(e);
        listener.changed(Event.ArrayEvent.EVENT_ADD, old, old);
        return v;
    }

    @Override
    public boolean remove(Object o) {
        List<T> old = ArrayUtil.cloneList(con);
        boolean v = con.remove(o);
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, old);
        return v;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return con.containsAll(c);
    }

    @Override
    public boolean addAll(Collection<? extends T> c) {
        return addAll(con.size(), c);
    }

    @Override
    public boolean addAll(int index, Collection<? extends T> c) {
        List<T> old = ArrayUtil.cloneList(con);
        boolean v = con.addAll(index, c);
        listener.changed(Event.ArrayEvent.EVENT_ADD, old, old);
        return v;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        List<T> old = ArrayUtil.cloneList(con);
        boolean v = con.removeAll(c);
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, old);
        return v;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        List<T> old = ArrayUtil.cloneList(con);
        boolean v = con.retainAll(c);
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, old);
        return v;
    }

    @Override
    public void clear() {
        List<T> old = ArrayUtil.cloneList(con);
        con.clear();
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, old);
    }

    @Override
    public T get(int index) {
        return con.get(index);
    }

    @Override
    public T set(int index, T element) {
        List<T> old = ArrayUtil.cloneList(con);
        T v = con.set(index, element);
        listener.changed(Event.ArrayEvent.EVENT_SET, old, con);
        return v;
    }

    @Override
    public void add(int index, T element) {
        List<T> old = ArrayUtil.cloneList(con);
        con.add(index, element);
        listener.changed(Event.ArrayEvent.EVENT_ADD, old, con);

    }

    @Override
    public T remove(int index) {
        List<T> old = ArrayUtil.cloneList(con);
        T v = con.remove(index);
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, old);
        return v;
    }

    @Override
    public int indexOf(Object o) {
        return con.indexOf(o);
    }

    @Override
    public int lastIndexOf(Object o) {
        return con.lastIndexOf(o);
    }

    @Override
    public ListIterator<T> listIterator() {
        return con.listIterator();
    }

    @Override
    public ListIterator<T> listIterator(int index) {
        return con.listIterator();
    }

    @Override
    public List<T> subList(int fromIndex, int toIndex) {
        return con.subList(fromIndex, toIndex);
    }

}
