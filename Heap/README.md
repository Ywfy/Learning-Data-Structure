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
根据完全二叉树的定义，完全能够将每个元素标号放到数组中
且这个结构能够很容易地推出一个节点的父节点和左右子节点
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
* 但是此时破坏了完全二叉树(大顶堆)的结构，因为52比16大
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
