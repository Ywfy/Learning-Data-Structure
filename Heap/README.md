# 优先队列
* 动态选择优先级最高的任务执行
* 在优先队列中，元素被赋予优先级。当访问元素时，具有最高优先级的元素最先删除。优先队列具有最高级先出 （first in, largest out）的行为特征
```
              入队     出队
普通线性结构    O(1)     O(n)
顺序线性结构    O(n)     O(1)
    堆         O(log(n)) O(log(n))
```
普通线性结构和顺序线性结构实现优先队列的复杂度很容易弄懂，一个出队时要扫描，一个入队时要扫描
重点是堆为什么能够只有O(log(n))的复杂度呢？

# 堆
堆其实也是树结构，并且最主流的方式就是用二叉树来表示堆，即二叉堆
* 二叉堆是一颗完全二叉树
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/wqe.png)<br>
完全二叉树，简单的说，就是将所有元素按从左到右、层层放置
* 最大堆(大顶堆)：根节点是最大的元素，每个节点都要大于等于它的子节点的值
* 最小堆(小顶堆)：根节点是最小的元素，每个节点都要小于等于它的子节点的值

## 用数组实现堆
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/sze.png)<br>
根据完全二叉树的定义，完全能够将每个元素顺序放到数组中<br>
且这个结构能够很容易地推出一个节点的父节点和左右子节点<br>
  * 父节点：parent(i) = i/2
  * 左子节点：left child(i) = 2*i
  * 右子节点：right child(i) = 2*i + 1
  
当然，若是标号是从0开始的话，数组空间就不会浪费一个，不过公式要变一下
  * 父节点: parent(i) = (i-1)/2
  * 左子节点：left child(i) = 2*i
  * 右子节点：right child(i) = 2*i + 1
  
### 堆的数组基本表示
需要用到我们之前的[Array](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/Array.java)类(注，新添加了swap()方法，用于交换两个位置的元素)
```
public class MaxHeap<T extends Comparable<T>> {

    private Array<T> data;

    public MaxHeap(int capacity){
        data = new Array<>(capacity);
    }

    public MaxHeap(){
        data = new Array<>();
    }

    //返回堆中的元素个数
    public int size(){
        return data.getSize();
    }

    //堆是否为空
    public boolean isEmpty(){
        return data.isEmpty();
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的父亲节点的索引
    private int parent(int index){
        if(index == 0)
            throw new IllegalArgumentException("index-0 doesn't have parent.");
        return (index - 1) / 2;
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的左子节点的索引
    private int leftChild(int index){
        return index * 2 + 1;
    }

    //返回完全二叉树的数组表示中，一个索引所表示的元素的右子节点的索引
    private int rightChild(int index){
        return index * 2 + 2;
    }
 
}
```

### 向堆中添加元素和Sift Up
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/su.png)<br>
元素添加流程：
* 没满的底层从左向右补，故此时52补在16的下方，作为16的右子节点
* 但是此时破坏了大顶堆的结构，因为52比16大
* 解决方法是：将52跟16换位，但是换完后52还是比父节点41大，故再继续换，52跟41换位
* 换完后，52比父节点62小，故此时结构稳定，添加完毕

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/su2.png)<br>
这个流程很像一个气泡上浮的过程，故叫Sift Up(上浮)<br>

```
//向堆中添加元素
    public void add(T t){
        data.addLast(t);
        siftUp(data.getSize() - 1);
    }

    private void siftUp(int k){
        while(k > 0 && data.get(parent(k)).compareTo(data.get(k)) < 0){
            data.swap(k, parent(k));
            k = parent(k); //聚焦同一元素
        }
    }
```

### 从堆中取出元素和Sift Down
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/sd.png)<br>

* 从堆中取出元素只能取出最大的元素，在上图中也就是62
* 62被取出后，需要有新的元素来填坑，我们用编号最末的元素来填坑，在上图中也就是16，故将16赋给位置0，并删除末端的16
* 16填坑后，不满足大顶堆的结构，因为16比两个子节点小
* 解决方法：将16与其两个子节点进行比较，若16小于两个节点中的最大者，则将16与最大者交换位置，也就是和52交换位置
* 交换后，仍不满足大顶堆的结构，重复上一步骤，与41交换位置
* 交换后，16大于左子节点15，右子节点为空，成功满足大顶堆的结构,完毕

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Heap/sd2.png)<br>
这个流程是不断下沉的，故叫Sift Down(下沉)<br>

```
//查看堆中的最大元素
    public T findMax(){
        if(data.isEmpty())
            throw new IllegalArgumentException("Can not findMax when heap is empty");
        return data.get(0);
    }

    //取出堆中的最大元素
    public T extractMax(){

        T ret = findMax();

        data.swap(0, data.getSize() - 1);
        data.removeLast();
        siftDown(0);

        return ret;
    }

    private void siftDown(int k){

        while(leftChild(k) < data.getSize()){

            int j = leftChild(k);
            if(j + 1 < data.getSize() &&
                    data.get(j + 1).compareTo(data.get(j)) > 0){
                j++;
            }
            //data[j]是 leftChild 和 rightChild 中的最大值
            if(data.get(k).compareTo(data.get(j)) >= 0)
                break;
            else {
                data.swap(k, j);
                k = j;
            }
        }
    }
```

### 时间复杂度分析
add和extractMax实际上主要流程还是上浮和下沉的过程，所以复杂度显然都是O(log(n)),而且堆是完全二叉树，所以堆是永远不可能退化成链表的

### 添加“replace”功能:取出最大元素后，放入一个新元素
实现:
* 第一种：可以先extractMax，再add，两次O(log(n))的操作
* 第二种：可以直接将堆顶元素替换以后Sift Down，一次O(log(n))的操作

```
//取出堆中的最大元素，并且替换成元素t
    public T replace(T t){

        T ret = findMax();
        data.set(0, t);
        siftDown(0);
        return ret;
    }
```

### 添加“heapify”功能：将任意数组整理成堆的形状
实现：
* 第一种：直接不断add，就OK了，算法复杂度为O(nlog(n))
* 第二种：
    * 直接将数组排列成完全二叉树的形状
    * 定位倒数第一个非叶子节点，也就是最后一个叶子节点的父节点，进行Sift Down操作
    * 然后倒数第二个非叶子节点，进行Sift Down
    * 第三个
    * ...
    * 直到根节点，此时就是大顶堆了
    * 第二种的算法复杂度为O(n)
 
```
    public MaxHeap(T[] arr){

        data = new Array<>(arr);
        for(int i = parent(arr.length - 1) ; i >= 0 ; i--){
            siftDown(i);
        }
    }
```

#### 测试两种实现的性能差别
通过以下测试代码
```
import java.util.Random;

public class Main {

    private static double testHeap(Integer[] testData, boolean isHeapify){
        long startTime = System.nanoTime();

        MaxHeap<Integer> maxHeap;
        if(isHeapify)
            maxHeap = new MaxHeap<>(testData);
        else{
            maxHeap = new MaxHeap<>();
            for(int num: testData)
                maxHeap.add(num);
        }

        int[] arr = new int[testData.length];
        for(int i = 0 ; i < testData.length ; i ++)
            arr[i] = maxHeap.extractMax();

        for(int i = 1 ; i < testData.length ; i ++)
            if(arr[i-1] < arr[i])
                throw new IllegalArgumentException("Error");
        System.out.println("Test MaxHeap completed.");

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        int n = 1000000;

        Random random = new Random();
        Integer[] testData = new Integer[n];
        for(int i = 0 ; i < n ; i++){
            testData[i] = random.nextInt(Integer.MAX_VALUE);
        }

        double time1 = testHeap(testData, false);
        System.out.println("Without heapify: " + time1 + " s");

        double time2 = testHeap(testData, true);
        System.out.println("With heapify: " + time2 + " s");
    }
}
```

运行结果是
```
Test MaxHeap completed.
Without heapify: 1.32640666 s
Test MaxHeap completed.
With heapify: 0.922797623 s
```
