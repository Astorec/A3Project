package BPlusTree;

import Classes.PermitHolder;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.*;

public class BPlusTree<K extends Comparable<K>, V> {

    private static final int DEFAULT_DEGREE = 2;

    private static int degree;
    private LeafNode root;
    private PermitHolder mapper;

    public BPlusTree() {
        this(DEFAULT_DEGREE);
    }

    public BPlusTree(int degree) {
        this.degree = degree;
        this.root = new LeafNode(degree);
        this.mapper = new PermitHolder();
    }

    public void insert(PermitHolder key, PermitHolder value) throws JsonProcessingException {
        this.root.insert(key, value);
    }


    public boolean delete(PermitHolder key, PermitHolder value) {
        if (root == null) {
            return false;
        }
        boolean deleted = root.delete(key, value);
        if (root.getNumKeys() == 0) {
            if (root.isLeaf()) {
                root = null;
            } else {
                root = root.getNext();
            }
        }
        return deleted;
    }


    public List<PermitHolder> rangeSearch(PermitHolder startKey, PermitHolder endKey) {
        List<PermitHolder> result = new ArrayList<>();

        LeafNode<PermitHolder, V> leaf = root.findLeafNode(startKey.getId());
        int i = leaf.getCurrentKeyIndex(startKey);

        while (leaf != null) {
            for (; i < leaf.getNumKeys(); i++) {
                PermitHolder  currentKey = leaf.getCurrentKey(i);
                if (currentKey.compareTo(endKey) > 0) {
                    return result;
                }
                result.add(currentKey);
            }
            i = 0;
            leaf = leaf.getNext();
        }
        return result;
    }


    public Set<Map.Entry<PermitHolder , V>> entrySet() {
        Set<Map.Entry<PermitHolder , V>> entries = new HashSet<>();
        Queue<Node<PermitHolder , V>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<PermitHolder , V> node = queue.poll();
            if (node instanceof InnerNode) {
                InnerNode<PermitHolder, V> innerNode = (InnerNode<PermitHolder , V>) node;
                for (int i = 0; i < innerNode.getNumKeys(); i++) {
                    queue.add(innerNode.getChild(i));
                }
            } else {
                LeafNode<PermitHolder , V> leafNode = (LeafNode<PermitHolder , V>) node;
                for (int i = 0; i < leafNode.getNumKeys(); i++) {
                    PermitHolder  key = leafNode.getCurrentKey(i);
                    V value = leafNode.get(key);
                    entries.add(new AbstractMap.SimpleEntry<>(key, value));
                }
            }
        }

        return entries;
    }


}
