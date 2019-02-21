import javafx.scene.DepthTest;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;

public class BST<T extends Comparable<T>> {

    private class Node{
        public T t;
        public Node left, right;

        public Node(T t){
            this.t = t;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //向二分搜索树中添加新的元素t
    public void add(T t){
        root = add(root, t);
    }

    //向以node为根的二分搜索树中插入元素t，递归算法
    private Node add(Node node, T t){
        if(node == null){
            size++;
            return new Node(t);
        }

        if(t.compareTo(node.t) < 0)
           node.left = add(node.left, t);
        else if(t.compareTo(node.t) > 0)
           node.right = add(node.right, t);

        return node;
    }

    //看二分搜索树中是否包含元素t
    public boolean contains(T t){
        return contains(root ,t);
    }

    //看以node为根的二分搜索树中是否包含元素t，递归算法
    private boolean contains(Node node, T t){

        if(node == null)
            return false;

        if(t.compareTo(node.t) == 0)
            return true;
        else if(t.compareTo(node.t) < 0)
            return contains(node.left, t);
        else
            return contains(node.right, t);

    }

    //二分搜索树的前序遍历
    public void preOrder(){
        preOrder(root);
    }

    //前序遍历以node为根的二分搜索树，递归算法
    private void preOrder(Node node){
        if(node == null)
            return;

        System.out.println(node.t);
        preOrder(node.left);
        preOrder(node.right);
    }

    // 二分搜索树的非递归前序遍历
    public void preOrderNR(){

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.t);

            if(cur.right != null)
                stack.push(cur.right);
            if(cur.left != null)
                stack.push(cur.left);
        }
    }

    //二分搜索树的中序遍历
    public void inOrder(){
        inOrder(root);
    }

    //中序遍历以node为根的二分搜索树，递归算法
    private void inOrder(Node node){
        if(node == null)
            return;

        inOrder(node.left);
        System.out.println(node.t);
        inOrder(node.right);
    }

    //二分搜索树的后序遍历
    public void postOrder(){
        postOrder(root);
    }

    //后序遍历以node为根的二分搜索树，递归算法
    private void postOrder(Node node){
        if(node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.t);
    }

    //二分搜索树的层序遍历
    public void levelOrder(){
        Queue<Node> q = new LinkedList<>();
        q.add(root);
        while(!q.isEmpty()){
            Node cur = q.remove();
            System.out.println(cur.t);

            if(cur.left != null)
                q.add(cur.left);
            if(cur.right != null)
                q.add(cur.right);
        }
    }

    //返回二分搜索树的最小值
    public T minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return minimum(root).t;
    }

    //返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    //查找二分搜索树的最大元素
    public T maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return maximum(root).t;
    }

    //返回以node为根的二分搜索树的最大值所在的节点
    private Node maximum(Node node){
        if(node.right == null)
            return node;

        return maximum(node.right);
    }

    //从二分搜索树中删除最小值所在的节点，返回最小值
    public T removeMin(){
        T ret = minimum();
        root = removeMin(root);
        return ret;
    }

    //删除掉以node为根的二分搜索树中的最小节点
    //返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){
        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    //从二分搜索树中删除最大值所在的节点
    public T removeMax(){
        T ret = maximum();
        root = removeMax(root);
        return ret;
    }

    //删除掉以node为根的二分搜索树中的最大节点
    //返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node){

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }

    //从二分搜索树中删除元素为t的节点
    public void remove(T t){
        root = remove(root, t);
    }

    //删除以node为根的二分搜索树中值为t的节点，递归算法
    //返回删除节点后新的二分搜索树的根
    private Node remove(Node node, T t){

        if(node == null)
            return null;

        if(t.compareTo(node.t) < 0){
            node.left = remove(node.left, t);
            return node;
        }
        else if(t.compareTo(node.t) > 0){
            node.right = remove(node.right, t);
            return node;
        }
        else{ //t.equals(node.t)
            //待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //待删除节点左右子树均不为空的情况
            //找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
            //用这个最小节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    //生成以node为根节点，深度为depth的描述二叉树的字符串
    public void generateBSTString(Node node, int depth, StringBuilder res){

        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.t + "\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth; i++){
            res.append("--");
        }
        return res.toString();
    }
}
