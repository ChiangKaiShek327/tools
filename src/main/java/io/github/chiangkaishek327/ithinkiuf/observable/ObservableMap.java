package io.github.chiangkaishek327.ithinkiuf.observable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import io.github.chiangkaishek327.ithinkiuf.observable.Observable.Listener.Event;
import io.github.chiangkaishek327.ithinkiuf.utils.ArrayUtil;

public class ObservableMap<K, V> implements Map<K, V>, Observable<Map<K, V>> {
    Map<K, V> con = new HashMap<>();
    Listener<Map<K, V>> listener = (e, o, n) -> {
    };

    @Override
    public int size() {
        return con.size();
    }

    @Override
    public boolean isEmpty() {
        return con.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return con.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return con.containsValue(value);
    }

    @Override
    public V get(Object key) {
        return con.get(key);
    }

    @Override
    public V put(K key, V value) {
        Map<K, V> old = ArrayUtil.cloneMap(con);
        V v = con.put(key, value);
        listener.changed(Event.ArrayEvent.EVENT_ADD, old, con);
        return v;
    }

    @Override
    public V remove(Object key) {
        Map<K, V> old = ArrayUtil.cloneMap(con);
        V v = con.remove(key);
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, con);
        return v;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        Map<K, V> old = ArrayUtil.cloneMap(con);
        con.putAll(m);
        listener.changed(Event.ArrayEvent.EVENT_ADD, old, con);

    }

    @Override
    public void clear() {
        Map<K, V> old = ArrayUtil.cloneMap(con);
        con.clear();
        listener.changed(Event.ArrayEvent.EVENT_REMOVE, old, con);
    }

    @Override
    public Set<K> keySet() {
        return con.keySet();
    }

    @Override
    public Collection<V> values() {
        return con.values();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return con.entrySet();
    }

    @Override
    public Listener<Map<K, V>> getChangeListener() {
        return listener;
    }

    @Override
    public void setChangeListener(Listener<Map<K, V>> listener) {
        this.listener = listener;
    }

}
