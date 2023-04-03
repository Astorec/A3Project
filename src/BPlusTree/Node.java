package BPlusTree;

// Node interface
public interface Node<PermitHolder extends Comparable<PermitHolder>, V> {
    V get(PermitHolder key);

    Node<PermitHolder, V> insert(PermitHolder key, V value);

    boolean delete(PermitHolder key, PermitHolder value);

    int getNumKeys();

    boolean isFull();

    PermitHolder getFirstLeafKey();

    V search(PermitHolder key);
    //List<V> rangeSearch(K startKey, K endKey);
}