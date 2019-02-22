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
        return 2 * (index + 1);
    }

    //返回完全二叉树中，index索引位置节点的右子节点的索引
    private int rightChild(int index){
        return 2 * (index + 2);
    }

}
```

### 如何从传入的数据数组构建线段树
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Segment%20tree/cj.png)<br>
一看上面的图片，很显然用递归可以非常轻易地解决该问题
```

```
