public class LinkedList<T> {

    //只有在LinkedList内可以访问Node，对外不公开
    //用户不需要了解底层实现细节
    private class Node{
        public T t;
        public Node next;

        public Node(T t, Node next){
            this.t = t;
            this.next = next;
        }

        public Node(T t){
            this(t, null);
        }

        public Node(){
            this(null,null);
        }

        @Override
        public String toString() {
            return t.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedList(){
        dummyHead = new Node();
        size = 0;
    }

    //返回链表是否为空
    public boolean isEmpty(){
        return size == 0;
    }

    //获取链表中的元素个数
    public int getSize(){
        return size;
    }

    //在链表的index(从0开始)位置添加新的元素
    //在链表中不是一个常用的操作，练习用
    public void add(int index, T t){

        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed,Illegal index.");
        }

        Node prev = dummyHead;
        for(int i = 0 ; i < index ; i++){
            prev = prev.next;
        }

        prev.next = new Node(t, prev.next);
        size++;

    }

    //在链表头部添加新结点t
    public void addFirst(T t){
        add(0, t);
    }

    //在链表尾部添加新结点t
    public void addLast(T t){
        add(size, t);
    }

    //获得链表的第index(从0开始)个位置的元素
    //在链表中不是一个常用的操作，练习用
    public T get(int index){

        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Get failed,Illegal index.");
        }

        Node cur = dummyHead.next;
        for(int i = 0 ; i < index ; i++){
            cur = cur.next;
        }
        return cur.t;
    }

    //获得链表的第一个元素
    public T getFirst(){
        return get(0);
    }

    //获得链表的最后一个元素
    public T getLast(){
        return get(size - 1);
    }

    //修改链表的第index(从0开始)个位置的元素为t
    //在链表中不是一个常用的操作，练习用
    public void set(int index,T t){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException();
        }

        Node cur = dummyHead.next;
        for(int i = 0 ; i < index ; i++){
            cur = cur.next;
        }

        cur.t = t;
    }

    //查找链表中是否有元素t
    public boolean contains(T t){
        Node cur = dummyHead.next;
        while(cur != null){
            if(cur.t.equals(t)){
                return true;
            }
            cur = cur.next;
        }
        return false;
    }

    //从链表中删除index(从0开始)位置的元素，返回删除的元素
    //在链表中不是一个常用的操作，练习用
    public T remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Remove failed,Index is illegal.");
        }

        Node prev = dummyHead;
        for(int i = 0 ; i < index ; i++){
            prev = prev.next;
        }

        Node retNode = prev.next;
        prev.next = retNode.next;
        retNode.next = null;
        size--;

        return retNode.t;
    }

    //从链表中删除第一个元素，返回删除的元素
    public T removeFirst(){
        return remove(0);
    }

    //从链表中删除最后一个元素，返回删除的元素
    public T removeLast(){
        return remove(size - 1);
    }

    //从链表中删除元素t
    public void removeElement(T t){

        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.t.equals(t)){
                break;
            }
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
        }
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();

        /*
        Node cur = dummyHead.next;
        while(cur != null){
            res.append(cur + "->");
            cur = cur.next;
        }
        */

        for(Node cur = dummyHead.next ; cur != null ; cur = cur.next){
            res.append(cur + "->");
        }
        res.append("NULL");

        return res.toString();
    }
}
