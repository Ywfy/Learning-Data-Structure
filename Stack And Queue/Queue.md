# 队列(Queue)
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/dl.gif)
* 队列与栈一样，也是一种线性表
* 只能从一端(队尾)添加元素，另一端(队首)取出元素,也就是先进先出,First In First Out(FIFO)
* 添加元素叫入队，取出元素叫出队

## 实现数组队列
这里我们借助之前的[Array](https://github.com/Ywfy/Learning-Data-Structure/tree/master/Arrays#java%E4%B8%8B%E5%AE%9A%E5%88%B6%E6%95%B0%E7%BB%84)类来实现Queue<br>

定义Queue接口
```
public interface Queue<T>{
    int getSize();
    boolean isEmpty();
    void enqueue(T t);
    T dequeue();
    T getFront();
}
```

实现Queue接口
```
public class ArrayQueue<T> implements Queue<T>{
    private Array<T> array;

    public ArrayQueue(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayQueue(){
        array = new Array<>();
    }

    @Override
    public int getSize() {
        return array.getSize();
    }

    @Override
    public boolean isEmpty() {
        return array.isEmpty();
    }

    @Override
    public void enqueue(T t) {
        array.addLast(t);
    }

    @Override
    public T dequeue() {
        return array.removeFirst();
    }

    @Override
    public T getFront() {
        return array.get(0);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Queue: ");
        res.append("front [");
        for(int i=0; i<array.getSize(); i++){
            res.append(array.get(i));
            if(i != array.getSize()-1){
                res.append(",");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args){
        ArrayQueue<Integer> queue = new ArrayQueue<>();
        for(int i=0; i<10; i++){
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

时间复杂度分析
```
void enqueue(T t) O(1)均摊
T dequeue()       O(n)
T front()         O(1)
int getSize()     O(1)
boolean isEmpty() O(1)
```
这里的dequeue操作每执行一次，都要将数组中的元素全部向左移一次，导致时间复杂度为O(n),这是数组队列的极大弊端<br>
接下来我们讲解一个可以规避这个弊端的新的队列结构<br>

## 循环队列
* 起始
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/xhf.png)<br><br>

* 数据入队，front不变，tail = (tail+1)%c (c为数组的长度)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/xh0.png)<br><br>

* 数据出队，front = (front+1)%c (c为数组的长度), tail不变<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/xhc.png)<br><br>

* 规定 (tail+1)%c = front则队列已满
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/xh.png)<br><br>

### 实现代码
```
import java.util.Arrays;

public class LoopQueue<T> implements Queue<T>{

    private T[] data;
    private int front,tail;
    private int size;

    public LoopQueue(int capacity){
        data = (T[])new Object[capacity + 1];
        front = 0;
        tail = 0;
        size = 0;
    }

    public LoopQueue(){
        this(10);
    }

    public int getCapacity(){
        return data.length - 1;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return front == tail;
    }

    @Override
    public void enqueue(T t) {

        if((tail + 1) % data.length == front){
            resize(getCapacity()* 2);
        }
        data[tail] = t;
        tail = (tail + 1) % data.length;
        size++;
    }

    @Override
    public T dequeue() {

        if(isEmpty()){
            throw new IllegalArgumentException("Cannot dequeue from  an empty queue.");
        }

        T ret = data[front];
        data[front] = null;
        front = (front + 1) % data.length;
        size--;
        if(size == getCapacity()/4 && getCapacity() / 2 != 0){
            resize(getCapacity() / 2);
        }
        return ret;
    }

    @Override
    public T getFront() {
        if(isEmpty()){
            throw new IllegalArgumentException("Queue is empty.");
        }
        return data[front];
    }

    private void resize(int newCapacity){

        T[] newData = (T[])new Object[newCapacity + 1];
        for(int i=0; i<size; i++){
            newData[i] = data[(front + i) % data.length];
        }
        data = newData;
        front = 0;
        tail = size;
    }

    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Queue: size = %d , capacity = %d\n", size, getCapacity()));
        res.append("front [");
        for(int i = front; i != tail; i = (i + 1) % data.length){
            res.append(data[i]);
            if((i + 1) % data.length != tail){
                res.append(",");
            }
        }
        res.append("] tail");
        return res.toString();
    }

    public static void main(String[] args){

        LoopQueue<Integer> queue = new LoopQueue<>();
        for(int i = 0 ; i < 10 ; i++ ){
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
循环队列的复杂度分析
```
void enqueue(T t)   O(1)均摊
T dequeue()         O(1)均摊
T getFront()        O(1)
int getSize()       O(1)
boolean isEmpty     O(1)
```

## 数组队列和循环队列的性能比较
我们通过以下测试代码
```
import java.util.Random;

public class Main {

    //测试使用q运行opCount个enqueue和dequeue操作所需要的时间，单位为秒
    private static double testQueue(Queue<Integer> q, int opCount){

        long startTime = System.nanoTime();

        Random random = new Random();
        for(int i = 0 ; i < opCount ; i++){
            q.enqueue(random.nextInt(Integer.MAX_VALUE));
        }
        for(int i = 0 ; i < opCount ; i++){
            q.dequeue();
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        int opCount = 100000;

        ArrayQueue<Integer> arrayQueue = new ArrayQueue<>();
        double time1 = testQueue(arrayQueue, opCount);
        System.out.println("ArrayQueue, time: " + time1 + "s");

        LoopQueue<Integer> loopQueue = new LoopQueue<>();
        double time2 = testQueue(loopQueue, opCount);
        System.out.println("LoopQueue, time: " + time2 + "s");
    }
}
```
我的运行结果是
```
ArrayQueue, time: 4.114253075s
LoopQueue, time: 0.012391489s
```
显然，循环队列在dequeue上的优化是巨大的
