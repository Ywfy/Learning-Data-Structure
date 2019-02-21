# 集合
定义集合的接口(注意，要求集合中的元素不重复)
```
public interface Set<T> {
    void add(T t);
    void remove(T t);
    boolean contains(T t);
    int getSize();
    boolean isEmpty();
}
```

## 基于二分搜索树的集合实现
[BST类的代码](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/BST.java)
```
public class BSTSet<T extends Comparable<T>> implements Set<T> {

    private BST<T> bst;

    public BSTSet(){
        bst = new BST<>();
    }

    @Override
    public void add(T t) {
        bst.add(t);
    }

    @Override
    public void remove(T t) {
        bst.remove(t);
    }

    @Override
    public boolean contains(T t) {
        return bst.contains(t);
    }

    @Override
    public int getSize() {
        return bst.size();
    }

    @Override
    public boolean isEmpty() {
        return bst.isEmpty();
    }
}
```

## 基于链表的集合实现
[LinkedList类的代码](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/LinkedList.java)
```
import java.util.ArrayList;

public class LinkedListSet<T> implements Set<T> {

    private LinkedList<T> list;

    public LinkedListSet(){
        list = new LinkedList<>();
    }

    @Override
    public void add(T t) {
        if(!list.contains(t))
            list.addFirst(t);
    }

    @Override
    public void remove(T t) {
        list.removeElement(t);
    }

    @Override
    public boolean contains(T t) {
        return list.contains(t);
    }

    @Override
    public int getSize() {
        return list.getSize();
    }

    @Override
    public boolean isEmpty() {
        return list.isEmpty();
    }

    public static void main(String[] args){
        System.out.println("Pride and Prejudice");

        ArrayList<String> words1 = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words1)){
            System.out.println("Total words: " + words1.size());

            LinkedListSet<String> set1 = new LinkedListSet<>();
            for (String word : words1)
                set1.add(word);
            System.out.println("Total different words: " + set1.getSize());
        }

    }
}
```

## 基于二分搜索树和基于链表的性能比较
通过以下测试代码
```
import java.util.ArrayList;

public class Main {

    private static double testSet(Set<String> set, String filename){
        long startTime = System.nanoTime();

        System.out.println(filename);
        ArrayList<String> words1 = new ArrayList<>();
        if(FileOperation.readFile(filename, words1)){
            System.out.println("Total words: " + words1.size());

            for (String word : words1)
                set.add(word);
            System.out.println("Total different words: " + set.getSize());
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }

    public static void main(String[] args) {

        String filename = "pride-and-prejudice.txt";

        BSTSet<String> bstSet = new BSTSet<>();
        double time1 = testSet(bstSet, filename);
        System.out.println("BST Set: " + time1 + " s");

        LinkedListSet<String> linkedListSet = new LinkedListSet<>();
        double time2 = testSet(linkedListSet, filename);
        System.out.println("LinkedList Set: " + time2 + " s");

    }
}
```
运行结果如下
```
pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
BST Set: 0.273353094 s
pride-and-prejudice.txt
Total words: 125901
Total different words: 6530
LinkedList Set: 3.558534863 s
```
显然，基于二分搜索树的集合性能要高许多

## 二者的复杂度分析
| |LinkedListSet|BSTSet|
|:---|:---|:---| 
|查 contains| O(n) | O(二分搜索树的深度=depth)
|增 add| O(n) | O(depth)
|删 remove| O(n) | O(depth)

所以这里我们需要分析计算，节点数和二分搜索树的深度depth有什么关系？<br>
最优情况<br>

* 对于满二叉树，0层-1个节点 ， 1层-2个节点， 2层-4个节点 ， 3层-8个节点，当到达第h-1层时，节点有2^(h-1)
* 利用等比数列求和，可知h层，一共有2^h-1个节点，令节点数为n，可得h=log(n+1),2为底
* 所以h=O(log(n),2为底)=O(log(n))

最差情况
* 二叉树退化成为了链表

所以可以得出

| |LinkedListSet|BSTSet|
|:---|:---|:---|      
|查 contains| O(n) | 平均O(log(n)) 最差O(n) |
|增 add| O(n) | 平均O(log(n))  最差O(n) |
|删 remove| O(n) | 平均O(log(n))  最差O(n) |

从上表可以看出，BSTSet的性能在一般情况下有着极其巨大的优势，但是BSTSet有退化成链表的可能，为了防止这种情况，出现了平衡二叉树


## 扩展
### 有序集合与无序集合
* 有序集合中的元素具有顺序性 <-- 基于搜索树的实现
    * java标准库的集合是基于红黑树实现的，同样是有序的
* 无序集合中的元素没有顺序性 <--基于哈希表的实现较为常用

### 多重集合
* 集合中的元素可以重复

