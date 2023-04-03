package BPlusTree;

import java.util.ArrayList;
import java.util.List;

public class LeafNode<PermitHolder extends Comparable<PermitHolder>, V> implements Node<PermitHolder, V> {

    private int degree;
    private List<PermitHolder> keys;
    private List<V> values;
    private LeafNode<PermitHolder, V> next;

    public LeafNode(int degree) {
        this.degree = degree;
        this.keys = new ArrayList<>(degree - 1);
        this.values = new ArrayList<>(degree - 1);
    }

    @Override
    public V get(PermitHolder key) {
        int i = getKeyIndex(key);
        if (i >= 0 && i < keys.size() && key.compareTo( keys.get(i)) == 0) {
            return values.get(i);
        } else {
            return null;
        }
    }

    @Override
    public boolean isFull() {
        return keys.size() == 2 * degree - 1;
    }

    @Override
    public PermitHolder getFirstLeafKey() {
        return keys.get(0);
    }

    private void splitLeafNode() {
        int mid = keys.size() / 2;
        LeafNode<PermitHolder, V> newLeafNode = new LeafNode<PermitHolder, V>(degree);
        for (int i = mid; i < keys.size(); i++) {
            newLeafNode.keys.add(keys.get(i));
            newLeafNode.values.add(values.get(i));
        }
        keys.subList(mid, keys.size()).clear();
        values.subList(mid, values.size()).clear();
        newLeafNode.next = next;
        next = newLeafNode.getNext();
    }

    @Override
    public Node<PermitHolder, V> insert(PermitHolder key, V value) {
        int i = getKeyIndex(key);
        if (i >= 0 && i < keys.size() && key.compareTo(keys.get(i)) == 0) {
            values.set(i, value);
        } else {
            keys.add(i, key);
            values.add(i, value);
        }
        if (isFull()) {
            splitLeafNode();
        }
        return this.getNext();
    }

    @Override
    public V search(PermitHolder key) {
        int i = getKeyIndex(key);
        if (i >= 0 && i < keys.size() && key.compareTo(keys.get(i)) == 0) {
            return values.get(i);
        } else {
            return null;
        }
    }

    @Override
    public boolean delete(PermitHolder key, PermitHolder value) {
        int i = getKeyIndex(key);
        if (i >= 0 && i < keys.size() && key.compareTo(keys.get(i)) == 0 && values.get(i).equals(value)) {
            keys.remove(i);
            values.remove(i);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int getNumKeys() {
        return keys.size();
    }

    private int getKeyIndex(PermitHolder key) {
        int i = 0;
        while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
            i++;
        }
        return i;
    }

    public LeafNode<PermitHolder, V> getNext() {
        return next;
    }

    public Boolean isLeaf(){
        return true;
    }

    public void setNext(LeafNode<PermitHolder, V> next) {
        this.next = next;
    }

    public LeafNode<PermitHolder, V> findLeafNode(String key) {
        if (key.compareTo(String.valueOf(keys.get(0))) < 0) {
            return this;
        }
        if (key.compareTo(String.valueOf(keys.get(keys.size() - 1))) >= 0) {
            return next == null ? this.getNext() : next.findLeafNode(key);
        }
        for (int i = 0; i < keys.size() - 1; i++) {
            if (key.compareTo(String.valueOf(keys.get(i))) >= 0 && key.compareTo(String.valueOf(keys.get(i + 1))) < 0) {
                return this.getNext();
            }
        }
        return null;
    }

    public PermitHolder getCurrentKey(int i) {
        return keys.get(i);
    }

    public int getCurrentKeyIndex(PermitHolder key) {
        int i = getKeyIndex(key);
        if (i >= 0 && i < keys.size() && key.compareTo(keys.get(i)) == 0) {
            return i;
        } else {
            return -1;
        }
    }

}
