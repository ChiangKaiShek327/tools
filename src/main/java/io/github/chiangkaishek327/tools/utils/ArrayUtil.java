package io.github.chiangkaishek327.tools.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ArrayUtil {
    public static <T> T getlast(T[] input) {
        return input[input.length - 1];
    }

    @SuppressWarnings("unchecked")
    public static <T> List<T> cloneList(List<T> arg0) {
        return (List<T>) Arrays.asList(arg0.toArray());
    }

    public static <K, T> Map<K, T> cloneMap(Map<K, T> arg0) {
        Set<K> ks = arg0.keySet();
        Map<K, T> nm = new HashMap<>();
        for (K k : ks) {
            nm.put(k, arg0.get(k));
        }
        return nm;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removelast(E[] input) {
        E[] result = (E[]) new Object[input.length - 1];
        for (int i = 0; i < result.length - 2; i++) {
            result[i] = input[i];
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    public static <E> E[] removefirst(E[] input) {
        E[] result = (E[]) new Object[input.length - 1];
        for (int i = 1; i < result.length - 1; i++) {
            result[i] = input[i];
        }
        return result;
    }

    public static <E> List<E> iteratorToList(Iterator<E> iterator) {
        ArrayList<E> al = new ArrayList<>();
        for (E ele = iterator.next(); iterator.hasNext(); ele = iterator.next()) {
            al.add(ele);
        }
        return al;
    }

    public static <E> List<E> enumerationToList(Enumeration<E> enumeration) {
        ArrayList<E> al = new ArrayList<>();
        for (E ele = enumeration.nextElement(); enumeration.hasMoreElements(); ele = enumeration.nextElement()) {
            al.add(ele);
        }
        return al;
    }

    public static class MapIterator<Type> implements Iterator<Type> {
        private final Type[] elements;
        private int index = 0;

        @SuppressWarnings("unchecked")
        public MapIterator(Map<?, Type> map) {
            elements = (Type[]) map.values().toArray();
        }

        @Override
        public boolean hasNext() {
            return index == elements.length - 1;
        }

        @Override
        public Type next() {
            Type element = elements[index];
            index++;
            return element;
        }
    }

}
