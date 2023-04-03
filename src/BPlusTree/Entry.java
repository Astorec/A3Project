package BPlusTree;

import Classes.PermitHolder;

public class Entry<K extends Comparable<K>, V> implements Comparable<Entry<K, V>> {
    private K key;
    private PermitHolder value;

    public Entry(K key, PermitHolder value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public PermitHolder getValue() {
        return value;
    }

    public void setValue(PermitHolder value) {
        this.value = value;
    }

    public int compareTo(Entry<K, V> other) {
        return this.key.compareTo(other.key);
    }

}