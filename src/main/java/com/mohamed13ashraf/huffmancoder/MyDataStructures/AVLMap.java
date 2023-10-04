package com.mohamed13ashraf.huffmancoder.MyDataStructures;

/**
 * AVL Tree Implementation (self-balanced BST) AS a Key-Value Pair Data Structure.
 * NOTE: This data structure does NOT allow duplicate Keys.
 **/

public class AVLMap<K extends Comparable<K>, V> {
    private AVLNode<K, V> map;
    private int size;


    public void put(K key, V value) {
        if (key == null)
            throw new NullPointerException();

        this.map = insert(this.map, new AVLNode<>(key, value));
    }

    private AVLNode<K, V> insert(AVLNode<K, V> root, AVLNode<K, V> nodeToInsert) {
        if (root == null) {
            this.size++;
            return nodeToInsert;
        }

        if (root.key == nodeToInsert.key) {
            nodeToInsert.left = root.left;
            nodeToInsert.right = root.right;
            return nodeToInsert;
        }

        if (root.key.compareTo(nodeToInsert.key) > 0)
        {
            AVLNode<K, V> node = insert(root.left, nodeToInsert);
            node.parent = root;
            root.left = node;
        } else if (root.key.compareTo(nodeToInsert.key) < 0)
        {
            AVLNode<K, V> node = insert(root.right, nodeToInsert);
            node.parent = root;
            root.right = node;
        }

        root.updateHeight();

        return balance(root);
    }

    public boolean containsKey(K key) {
        return find(this.map, key) != null;
    }

    public V remove(K key) {
        AVLNode<K, V> nodeToRemove = find(this.map, key);
        if (nodeToRemove != null)
        {
            this.size--;

            if (nodeToRemove.isLeaf())
                removeCaseOne(map, key);
            else if (nodeToRemove.hasOneChild())
                removeCaseTwo(map, key);
            else
                removeCaseThere(nodeToRemove);

            return nodeToRemove.value;
        }

        return null;
    }


    private AVLNode<K, V> removeCaseOne(AVLNode<K, V> root, K key) {
        if (root != null) {
            if (root.key.equals(key)) {
                if (parentOf(root) == null) {
                    this.map = null;
                    return root;
                }

                if (leftOf(root.parent) == root)
                    root.parent.left = null;
                else
                    root.parent.right = null;

                return root;
            }

            if (root.key.compareTo(key) > 0) {
                if (leftOf(root) != null) {
                    AVLNode<K, V> left = removeCaseOne(root.left, key).parent;
                    left.updateHeight();
                    if (parentOf(root) == null)
                        this.map = balance(left);
                    else if (leftOf(root.parent) == root)
                        root.parent.left = balance(left);
                    else if (rightOf(root.parent) == root)
                        root.parent.right = balance(left);

                    return left;
                }
            } else {
                if (rightOf(root) != null) {

                    AVLNode<K, V> right = removeCaseOne(root.right, key).parent;
                    right.updateHeight();

                    if (parentOf(root) == null)
                        this.map = balance(right);
                    else if (leftOf(root.parent) == root)
                        root.parent.left = balance(right);
                    else if (rightOf(root.parent) == root)
                        root.parent.right = balance(right);

                    return right;
                }
            }

        }

        return null;
    }

    private AVLNode<K, V> removeCaseTwo(AVLNode<K, V> root, K key) {
        if (root != null) {
            if (root.key.equals(key)) {
                if (leftOf(root.parent) == root)
                {
                    if (leftOf(root) != null) {
                        root.left.parent = root.parent;
                        if (parentOf(root) == null)
                            this.map = root.left;
                        else
                            root.parent.left = root.left;
                    } else {
                        root.right.parent = root.parent;
                        if (parentOf(root) == null)
                            this.map = root.right;
                        else
                            root.parent.left = root.right;
                    }
                } else {
                    if (rightOf(root) != null) {
                        root.right.parent = root.parent;
                        if (parentOf(root) == null)
                            this.map = root.right;
                        else
                            root.parent.right = root.right;
                    } else {
                        root.left.parent = root.parent;
                        if (parentOf(root) == null)
                            this.map = root.left;
                        else
                            root.parent.right = root.left;
                    }
                }
                return root;
            }

            if (root.key.compareTo(key) > 0) {
                if (leftOf(root) != null) {
                    AVLNode<K, V> left = removeCaseTwo(root.left, key).parent;
                    left.updateHeight();
                    if (parentOf(root) == null)
                        this.map = balance(left);
                    else if (leftOf(root.parent) == root)
                        root.parent.left = balance(left);
                    else if (rightOf(root.parent) == root)
                        root.parent.right = balance(left);

                    return left;
                }
            } else {
                if (rightOf(root) != null) {
                    AVLNode<K, V> right = removeCaseTwo(root.right, key).parent;
                    right.updateHeight();
                    if (parentOf(root) == null)
                        this.map = balance(right);
                    else if (leftOf(root.parent) == root)
                        root.parent.left = balance(right);
                    else if (rightOf(root.parent) == root)
                        root.parent.right = balance(right);

                    return right;
                }
            }
        }

        return null;
    }

    private void removeCaseThere(AVLNode<K, V> node) {

        AVLNode<K, V> successor = getInOrderSuccessor(node);

        if (successor.hasOneChild())
            removeCaseTwo(this.map, successor.key);
        else
            removeCaseOne(this.map, successor.key);

        set(map, node.key, successor);
    }

    private void set(AVLNode<K, V> root, K key, AVLNode<K,V> successor) {
        if (root != null)
        {
            if (root.key.equals(key)) {
                root.key = successor.key;
                root.value = successor.value;
                return;
            }

            if (root.key.compareTo(key) > 0) {
                if (leftOf(root) != null)
                    set(root.left, key, successor);
            } else if (root.key.compareTo(key) < 0) {
                if (rightOf(root) != null)
                    set(root.right, key, successor);
            }
        }

    }

    public AVLNode<K, V> getInOrderSuccessor(AVLNode<K, V> node) {

        if (rightOf(node) != null)
        {
            AVLNode<K, V> currNode = node.right;
            while (leftOf(currNode) != null)
                currNode = currNode.left;

            return currNode;
        }
        else
        {
            AVLNode<K, V> curNode = node;

            while (parentOf(curNode) != null && leftOf(curNode.parent) != curNode)
                curNode = curNode.parent;

            return curNode.parent;
        }
    }

    public AVLNode<K, V> getInOrderPredecessor(AVLNode<K, V> node) {

        if (leftOf(node) != null)
        {
            AVLNode<K, V> currNode = node.left;
            while (rightOf(currNode) != null)
                currNode = currNode.right;

            return currNode;
        }
        else
        {
            AVLNode<K, V> curNode = node;

            while (parentOf(curNode) != null && rightOf(curNode.parent) != curNode)
                curNode = curNode.parent;

            return curNode.parent;
        }
    }

    public AVLNode<K, V> lowerEntry(K key) {
        AVLNode<K, V> entry = find(this.map, key);
        return getInOrderPredecessor(entry);
    }

    public K lowerKey(K key) {
        AVLNode<K, V> predecessor = getInOrderPredecessor(find(this.map, key));
        return predecessor == null ? null : predecessor.key ;
    }

    public K higherKey(K key) {
        AVLNode<K, V> successor = getInOrderSuccessor(find(this.map, key));
        return successor == null ? null : successor.key ;
    }

    public AVLNode<K, V> higherEntry(K key) {
        AVLNode<K, V> entry = find(this.map, key);
        return getInOrderSuccessor(entry);
    }

    public SinglyLinkedList<AVLNode<K, V>> entrySet()
    {
        SinglyLinkedList<AVLNode<K, V>> entrySet = new SinglyLinkedList<>();
        return inOrderTraversal(this.map, entrySet);
    }

    private SinglyLinkedList<AVLNode<K,V>> inOrderTraversal(AVLNode<K,V> root, SinglyLinkedList<AVLNode<K,V>> entrySet) {

        if (root != null)
        {
            if (root.left != null)
                inOrderTraversal(root.left, entrySet);

            entrySet.add(root);

            if (root.right != null)
                inOrderTraversal(root.right, entrySet);
        }

        return entrySet;
    }

    public V get(K key) {
        if (key == null)
            throw new NullPointerException();

        AVLNode<K, V> node = find(this.map, key);
        return node != null ? node.value : null;
    }

    private AVLNode<K, V> find(AVLNode<K, V> root, K key) {
        
        if (root != null)
        {
            if (root.key.equals(key))
                return root;

            if (root.key.compareTo(key) > 0) {
                if (leftOf(root) != null)
                    return find(root.left, key);
            } else if (root.key.compareTo(key) < 0) {
                if (rightOf(root) != null)
                    return find(root.right, key);
            }
        }
        
        return null;
    }

    private AVLNode<K, V> balance(AVLNode<K, V> root) {

        if (balanceFactor(root) == 2)
        {
            if (balanceFactor(root.left) == -1)
                root.left = leftRotation(root.left);

            return rightRotation(root);
        } else if (balanceFactor(root) == -2) {
            if (balanceFactor(root.right) == 1)
                root.right = rightRotation(root.right);

            return leftRotation(root);
        }

        return root;
    }

    public int balanceFactor(AVLNode<K, V> root) {
        if (root.isLeaf())
            return 0;

        if (root.hasTwoChildren())
            return root.left.height - root.right.height;
        else if (leftOf(root) == null)
            return -root.right.height;

        return root.left.height;
    }

    private AVLNode<K, V> leftRotation(AVLNode<K, V> root) {
        AVLNode<K, V> q = root.right;
        root.right = q.left;
        if (leftOf(q) != null)
            q.left.parent = q.parent;
        q.parent = root.parent;
        root.parent = q;
        q.left = root;
        root.updateHeight();
        q.updateHeight();
        return q;
    }

    private AVLNode<K, V> rightRotation(AVLNode<K, V> root) {
        AVLNode<K, V> p = root.left;
        root.left = p.right;
        if (rightOf(p) != null)
            p.right.parent = p.parent;
        p.parent = root.parent;
        root.parent = p;
        p.right = root;
        root.updateHeight();
        p.updateHeight();
        return p;
    }

    public boolean isEmpty() {
        return this.size == 0;
    }

    public int size() {
        return this.size;
    }

    private AVLNode<K, V> parentOf(AVLNode<K, V> node) {
        return node == null ? null : node.parent;
    }

    private AVLNode<K, V> leftOf(AVLNode<K, V> node) {
        return node == null ? null : node.left;
    }

    private AVLNode<K, V> rightOf(AVLNode<K, V> node) {
        return node == null ? null : node.right;
    }

    public static class AVLNode<K, V> {
        K key;
        V value;

        AVLNode<K, V> left,
                right,
                parent;
        int height;

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        @Override
        public String toString() {
            return "<" + key + ", " + value + ">";
        }

        public AVLNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }

        private void updateHeight() {
            if (isLeaf())
                height = 1;
            else if (hasTwoChildren())
                height = 1 + Math.max(left.height, right.height);
            else
                height = (left == null) ? 1 + right.height : 1 + left.height;
        }

        private boolean isLeaf() {
            return left == null && right == null;
        }

        private boolean hasOneChild() {
            return  (left != null && right == null) ||
                    (right != null && left == null);
        }

        private boolean hasTwoChildren() {
            return left != null && right != null;
        }
    }
}