# 链表
* 前面所讲的动态数组、栈、队列底层其实都是依托的静态数组，靠resize解决固定容量问题
* 链表则是一种真正的动态数据结构

![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/List/lbb.png)<br>
数组链表<br>
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/List/szlb.png)<br>

* 每个元素包含两个结点，一个是存储元素的数据域 (内存空间)，另一个是指向下一个结点地址的指针域
* 链表是物理存储单元上非连续的、非顺序的存储结构，数据元素的逻辑顺序是通过链表的指针地址实现

链表的优点： 
* 链表是很常用的一种数据结构，不需要初始化容量，可以任意加减元素； 
* 添加或者删除元素时只需要改变前后两个元素结点的指针域指向地址即可，所以添加，删除很快；

缺点： 
* 因为含有大量的指针域，占用空间较大； 
* 查找元素需要遍历链表来查找，非常耗时。

适用场景： 
* 数据量较小，需要频繁增加，删除操作的场景

## 实现链表version1
version1
```
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

    private Node head;
    private int size;

    public LinkedList(){
        head = null;
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

    public void addFirst(T t){

        /*
        Node node = new Node(e);
        node.next = head;
        head = node;
        */
        //上面代码是细化版本，帮助理解
        head = new Node(t, head);
        size++;
    }

    //在链表的index(从0开始)位置添加新的元素
    //在链表中不是一个常用的操作，练习用
    public void add(int index, T t){

        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed,Illegal index.");
        }

        if(index == 0){
            addFirst(t);
        }else{
            Node prev = head;
            for(int i = 0 ; i < index - 1 ; i++){
                prev = prev.next;
            }

            /*
            Node node = new Node(e);
            node.next = prev.next;
            prev.next = node;
            */
            //上述注释代码是细化版本，帮助理解
            prev.next = new Node(e, prev.next);
            size++;

        }
    }

    public void addLast(T t){
        add(size, t);
    }
}
```

## 虚拟头结点
在执行添加操作时，要将之前的节点指针域指向新的结点，新的节点指针域指向下个结点或者NULL<br>
但是对于头结点，因为它并没有处于它前面的结点，所以总是需要特殊处理<br>
为了改善这个情况，出现了<strong>虚拟头结点</strong><br>

![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/List/xnt.png)<br>

设立一个数据域为NULL的结点放在Head结点之前，这个结点没有任何实际意义，仅仅只是为了逻辑处理方便<br>
对于用户来说，它是屏蔽的，用户并不需要知道它的存在<br>

## 链表实现version2
```
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

```

## 时间复杂度分析
添加操作    O(n)
```
addLast(t)      O(n)
addFirst(t)     O(1)
add(index, t)   O(n/2)=O(n)
```
删除操作    O(n)
```
removeLast(t)   O(n)
removeFirst(t)  O(1)
remove(index)   O(n/2)=O(n)
```
修改操作    O(n)
```
set(index, e)   O(n)
```
查找操作    O(n)
```
get(index)     O(n)
contains(t)    O(n)
```
故链表的增删改查操作全是O(n)的时间复杂度，感觉比数组糟糕多了，好像没什么用。<br>
但是我们仔细看看，<br>
```
addFirst(t) O(1)
removeFirst(t)  O(1)
```
从这两个点可以看出链表适合使用的方式<br>
* 增删查只对链表头进行操作
* 不进行或极少进行更改操作

此时链表的时间复杂度就是O(1)的,跟数组一样，但是比起数组，链表具有动态分配内存空间的优势，不会大量地浪费内存空间

## 使用链表实现栈
Stack接口
```
public interface Stack<T> {
    int getSize();
    boolean isEmpty();
    void push(T t);
    T pop();
    T peek();
}
```

用我们的LinkedList来实现Stack，叫做LinkedListStack
```
public class LinkedListStack<T> implements Stack<T> {

    private LinkedList<T> list;

    public LinkedListStack(){
        list = new LinkedList<>();
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    @Override
    public void push(T t) {
        list.addFirst(t);
    }

    @Override
    public T pop() {
        return list.removeFirst();
    }

    @Override
    public T peek() {
        return list.getFirst();
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: top ");
        res.append(list);
        return res.toString();
    }

    public static void main(String[] args){

        LinkedListStack<Integer> stack = new LinkedListStack<>();

        for(int i = 0 ; i < 6 ; i++){
            stack.push(i);
            System.out.println(stack);
        }

        stack.pop();
        System.out.println(stack);
    }
}
```

## Array实现的栈和LinkedList实现的栈性能比较
我们之前也用[Array](https://github.com/Ywfy/Learning-Data-Structure/tree/master/Arrays#java%E4%B8%8B%E5%AE%9A%E5%88%B6%E6%95%B0%E7%BB%84)类来实现过栈，那么哪个的性能更好呢？<br>
我们通过以下测试代码
```
import java.util.Random;

public class Test {

    //测试使用stack运行opCount个push和pop操作所需要的时间，单位：秒
    private static double testStack(Stack<Integer> stack, int opCount){

        long startTime = System.nanoTime();

        Random rand = new Random();
        for(int i = 0 ; i < opCount ; i++){
            stack.push(rand.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0 ; i < opCount ; i++){
            stack.pop();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args){

        int opCount = 100000;

        ArrayStack<Integer> arrayStack = new ArrayStack<>();
        double time1 = testStack(arrayStack, opCount);
        System.out.println("ArrayStack, Time: " + time1 + " s");

        LinkedListStack<Integer> linkedListStack = new LinkedListStack<>();
        double time2 = testStack(linkedListStack, opCount);
        System.out.println("LinkedListStack, Time: " + time2 + " s");
    }
}

```
我的测试结果
```
ArrayStack, Time: 0.025484838 s
LinkedListStack, Time: 0.018814016 s
```
将opCount的值调大为1000万
```
ArrayStack, Time: 3.428777656 s
LinkedListStack, Time: 4.985829783 s
```
显然，这二者在性能上的差距并不明显，而LinkedListStack在opCount值调大到1000万甚至更大后，出现的些许落后跟内存的分配有关，毕竟LinkedListStack在运行中要不断地new结点，寻找可用内存空间

## 使用链表来实现队列
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/List/lbdl.png)<br>
Head端不管是新增还是删除都很容易<br>
新增一个指针来指向链表尾端，即Tail端。Tail端新增很容易，但是删除很难<br>
结合这种特点，我们使用Head作为队首，Tail作为队尾，可以实现较为高效的队列结构<br>

<strong>我们使用Java自带的LinkedList来实现</strong><br>
注：Java自带的LinkedList底层是一个循环双向链表
```
public class LinkedListQueue<T> implements Queue<T> {

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

    private Node head, tail;
    private int size;

    public LinkedListQueue(){
        head = null;
        tail = null;
        size = 0;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void enqueue(T t) {
        if(tail == null){
            tail = new Node(t);
            head = tail;
        }else{
            tail.next = new Node(t);
            tail =  tail.next;
        }
        size++;
    }

    @Override
    public T dequeue() {
        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from an empty queue.");
        }

        Node retNode = head;
        head = head.next;
        retNode.next = null;
        if(head == null){
            tail = null;
        }
        size--;
        return retNode.t;
    }

    @Override
    public T getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty.");
        }
        return head.t;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: front ");

        Node cur = head;
        while(cur != null){
            res.append(cur + "->");
            cur = cur.next;
        }
        res.append("NULL tail");
        return res.toString();
    }

    public static void main(String[] args){

        LinkedListQueue<Integer> queue = new LinkedListQueue<>();
        for(int i = 0 ; i < 10 ; i++){
            queue.enqueue(i);
            System.out.println(queue);

            if(i % 3 == 2){
                queue.dequeue();
                System.out.println(queue);
            }
        }

    }
}
```
