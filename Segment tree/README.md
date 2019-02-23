# 线段树(Segment tree)
经典问题：区间查询<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Segment%20tree/qj.png)<br>
实质上，就是基于区间的统计查询
```
        使用数组实现    使用线段树
更新       O(n)           O(logn)
查询       O(n)           O(logn)
```
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Segment%20tree/xd.png)<br>
以求和为例，想要得到[4,7]的和，直接访问A[4,7]区间，想要[2,5]的和，访问A[2,3]和A[4,5]然后加起来<br>
当数据量特别大时，这种计算模式会非常便利<br>

## 线段树的性质
* 线段树不一定是完全二叉树，同样不一定是满二叉树
  * 因为若是10个元素-->55分-->必须23分或者32分，若是23分则显然不符合完全二叉树的定义
* 线段树是平衡二叉树
  * 平衡二叉树，即最大深度和最小深度相差<=1,另外堆作为一个完全二叉树也是满足平衡二叉树的定义的
* 线段树不考虑添加元素，即区间固定
  
## 用数组实现线段树
首要问题：如果区间有n个元素，数组表示需要有多少个节点？
* 如果n=2^k,则数组需要有2n的空间--->根据等比数列，末层要储存所有的元素，大小为n，则其上层所有空间的和大约也为n(实际是n-1)
* 一般情况，n=2^k + m,则数组需要有4n的空间

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Segment%20tree/4n.png)<br>
叶子节点为NULL，在数组表示的时候同样也要占空间，使其成为一个满二叉树，这是为了确保A[3]、A[4]的位置索引同样能够满足索引公式
```
(数组从0位置开始存放元素的公式)
父节点 parent = (i-1) / 2
左子节点 left Child = i * 2 + 1
右子节点 right Child = i * 2 + 2
```

### 基本实现
```
public class SegmentTree<T>  {

    private T[] tree;
    private T[] data;

    public SegmentTree(T[] arr){
        data = (T[])new Object[arr.length];
        for(int i = 0 ; i < arr.length ; i++){
            data[i] = arr[i];
        }

        tree = (T[])new Object[4 * arr.length];
    }

    public int getSize(){
        return data.length;
    }

    public T get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal.");
        return data[i];
    }

    //返回完全二叉树中，index索引位置节点的左子节点的索引
    private int leftChild(int index){
        return 2 * index + 1;
    }

    //返回完全二叉树中，index索引位置节点的右子节点的索引
    private int rightChild(int index){
        return 2 * index + 2;
    }

}
```

### 如何从传入的数据数组构建线段树
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Segment%20tree/cj.png)<br>
一看上面的图片，很显然用递归可以非常轻易地解决该问题
```
import java.util.Arrays;

public class SegmentTree<T>  {

    private T[] tree;
    private T[] data;
    private Merger<T> merger;

    public SegmentTree(T[] arr, Merger<T> merger){

        this.merger = merger;

        data = (T[])new Object[arr.length];
        for(int i = 0 ; i < arr.length ; i++){
            data[i] = arr[i];
        }

        tree = (T[])new Object[4 * arr.length];
        buildSegmentTree(0, 0, arr.length - 1);
    }

    //在treeIndex的位置创建表示区间[l...r]的线段树
    private void buildSegmentTree(int treeIndex, int l, int r){

        if(l == r) {
            tree[treeIndex] = data[l];
            return;
        }

        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        //int mid = (l + r) / 2;
        //防止int类型数值溢出
        int mid = l + (r - l) / 2;
        buildSegmentTree(leftTreeIndex, l, mid);
        buildSegmentTree(rightTreeIndex, mid + 1, r);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

    public int getSize(){
        return data.length;
    }

    public T get(int index){
        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal.");
        return data[index];
    }

    //返回完全二叉树中，index索引位置节点的左子节点的索引
    private int leftChild(int index){
        return 2 * index + 1;
    }

    //返回完全二叉树中，index索引位置节点的右子节点的索引
    private int rightChild(int index){
        return 2 * index + 2;
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append('[');
        for(int i = 0 ; i < tree.length ; i++){
            if(tree[i] != null)
                res.append(tree[i]);
            else
                res.append("null");

            if(i != tree.length - 1)
                res.append(",");
        }
        res.append(']');
        return res.toString();
    }
}

```
这里我们往构造器中新传入了一个Merger类，这个类是负责处理元素合并逻辑的，因为我们若是将底层就设置为求和的话，这个线段树就只有求和的功能了，这实在是太局限<br>
以下是使用代码
```
public class Main {

    public static void main(String[] args) {

        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                return a + b;
            }
        });
        //使用lambda表达式，等价于
        //SegmentTree<Integer> segTree = new SegmentTree<>(nums,
        //                (a, b) -> a + b);

        System.out.println(segTree);
    }
}
```

### 区间查询
```
//返回区间[queryL, queryR]的值
    public T query(int queryL, int queryR){

        if(queryL < 0 || queryL >= data.length
                || queryR < 0 || queryR >= data.length || queryL > queryR){
            throw new IllegalArgumentException("Index is illegal.");
        }

        return query(0, 0, data.length - 1, queryL, queryR);
    }

    //在以treeIndex为根的线段树中[l, r]的范围里,查询区间[queryL, queryR]的值
    private T query(int treeIndex, int l, int r, int queryL, int queryR){

        if(l == queryL && r == queryR){
            return tree[treeIndex];
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);

        if(queryL >= mid + 1)
            return query(rightTreeIndex, mid + 1, r, queryL, queryR);
        else if (queryR <= mid)
            return query(leftTreeIndex, l, mid, queryL, queryR);
        else {
            T leftResult = query(leftTreeIndex, l, mid, queryL, mid);
            T rightResult = query(rightTreeIndex, mid + 1, r, mid + 1, queryR);
            return merger.merge(leftResult, rightResult);
        }
    }
```
测试用例
```
public class Main {

    public static void main(String[] args) {

        Integer[] nums = {-2, 0, 3, -5, 2, -1};
        SegmentTree<Integer> segTree = new SegmentTree<>(nums, new Merger<Integer>() {
            @Override
            public Integer merge(Integer a, Integer b) {
                return a + b;
            }
        });
       
        System.out.println(segTree.query(0, 2));
        System.out.println(segTree.query(2, 5));
        System.out.println(segTree.query(0, 5));
    }
}
```

### 更新操作
```
    //将index位置的值，更新为t
    public void set(int index, T t){

        if(index < 0 || index >= data.length)
            throw new IllegalArgumentException("Index is illegal");

        data[index] = t;
        set(0, 0, data.length - 1, index, t);
    }

    //在以treeIndex为根的线段树中更新index的值为e
    private void set(int treeIndex, int l, int r, int index, T t){

        if(l == r) {
            tree[treeIndex] = t;
            return;
        }

        int mid = l + (r - l) / 2;
        int leftTreeIndex = leftChild(treeIndex);
        int rightTreeIndex = rightChild(treeIndex);
        if(index >= mid + 1)
            set(rightTreeIndex, mid + 1, r, index, t);
        if(index <= mid)
            set(leftTreeIndex, l, mid, index, t);

        tree[treeIndex] = merger.merge(tree[leftTreeIndex], tree[rightTreeIndex]);
    }

```
