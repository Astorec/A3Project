package BPlusTree;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class InnerNode<PermitHolder extends Comparable<PermitHolder>, V> implements Node<PermitHolder,V> {

    private int degree;
    private List<PermitHolder> keys;
    private List<Node<PermitHolder, V>> children;

    public InnerNode(int degree) {
        this.degree = degree;
        this.keys = new ArrayList<>(degree -1);
        this.children = new ArrayList<>(degree);
    }

    @Override
    public V get(PermitHolder key) {
        int i = getKeyIndex(key);
        return getChild(i).get(key);
    }


    @Override
    public boolean isFull() {
        return keys.size() == 2 * degree -1;
    }

    @Override
    public PermitHolder getFirstLeafKey() {
        return getChild(0).getFirstLeafKey();
    }

    private Node<PermitHolder, V> splitInnerNode() {
        int mid = keys.size() / 2;
        InnerNode<PermitHolder,V> newInnerNode = new InnerNode<PermitHolder, V>(degree);
        for (int i = mid + 1; i < keys.size(); i++) {
            newInnerNode.keys.add(keys.get(i));
            newInnerNode.children.add(children.get(i));
        }
        newInnerNode.children.add(children.get(keys.size()));
        keys.subList(mid, keys.size()).clear();
        children.subList(mid + 1, children.size()).clear();
        return newInnerNode;
    }

    @Override
    public Node<PermitHolder, V> insert(PermitHolder key, V value) {
        int i = getKeyIndex(key);
        Node<PermitHolder, V> child = getChild(i);
        Node<PermitHolder, V> newChild = child.insert(key, value);
        if (newChild != child) {
            insertChild(newChild, i + 1);
            if (isFull()) {
                return splitInnerNode();
            }
        }
        return splitInnerNode();
    }

    @Override
    public V search(PermitHolder key) {
        int i = 0;
        while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
            i++;
        }
        return children.get(i).get(key);
    }

    @Override
    public boolean delete(PermitHolder key, PermitHolder value) {
        int i = 0;
        while (i < keys.size() && key.compareTo(keys.get(i)) > 0) {
            i++;
        }
        return children.get(i).delete(key, value);
    }

    private int getKeyIndex(PermitHolder key) {
        int i = Collections.binarySearch(keys, key);
        return i < 0 ? -i - 1 : i;
    }

    public Node<PermitHolder, V> getChild(int i) {
        return children.get(i);
    }

    private void insertChild(Node<PermitHolder, V> child, int i) {
        children.add(i, child);
        keys.add(i - 1, child.getFirstLeafKey());
    }

    @Override
    public int getNumKeys() {
        return keys.size();
    }

}