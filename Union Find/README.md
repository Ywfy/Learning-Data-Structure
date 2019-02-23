# 并查集 Union Find
在开始时让每个元素构成一个单元素的集合，然后按一定顺序将属于同一组的元素所在的集合合并，其间要反复查找一个元素在哪个集合中。<br>
并查集的结构非常简单，就两个--元素和集合<br>
并查集只关心元素是否连接、即是否同属于一个集合<br>

## 主要操作
* 初始化
把每个点所在集合初始化为其自身<br>
通常来说，这个步骤在每次使用该数据结构时只需要执行一次，无论何种实现方式，时间复杂度均为O(N)<br>
* 查找
查找元素所在的集合，即根节点<br>
* 合并
将两个元素所在的集合合并为一个集合<br>
通常来说，合并之前，应先判断两个元素是否属于同一集合，这可用上面的“查找”操作实现<br><br>

定义并查集接口
```
public interface UnionFind {
    int getSize();
    boolean isConnected(int i, int j);
    void unionElements(int p, int q);
}
```

## 用数组实现并查集

### Quick Find
```
ID(数组索引)   0  1  2  3  4  5  6  7  8  9
-----------------------------------------------------------
所属集合(值)   0  1  0  1  0  1  0  1  0  1
```
用数组可以非常简单地实现并查集
```
public class UnionFind1 implements UnionFind{

    private int[] id;

    public UnionFind1(int size){
        id = new int[size];
        //刚开始所有元素都是独立的，都是各自一个集合
        for(int i = 0 ; i < id.length ; i++){
            id[i] = i;
        }
    }

    @Override
    public int getSize() {
        return id.length;
    }

    //查询元素p所对应的集合编号
    private int find(int p){
        if(p < 0 || p >= id.length)
            throw new IllegalArgumentException("p is out of bound");
        return id[p];
    }

    //用于查询元素i和j是否所属同一个集合
    @Override
    public boolean isConnected(int i, int j) {
        return find(i) == find(j);
    }

    //合并元素p和q所属的集合
    @Override
    public void unionElements(int p, int q) {

        int pID = find(p);
        int qID = find(q);
        if(pID == qID)
            return;

        for(int i = 0 ; i < id.length ; i++){
            if(id[i] == qID) {
                id[i] = pID;
            }
        }
    }
}
```
时间复杂度
```
unionElements(p, q)     O(n)
isConnected(i, j)       O(1)
```

### Quick Union
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/bcj.png)<br>
将元素视为一个一个的节点，它们各自有一个指针，当union时，<strong>一个元素的根节点就指向另外一个元素的根节点</strong><br>
显然，这种形式下union操作就非常便利了，当然查询操作会有些性能上的牺牲。两者的性能看树的深度，一般来说是OK的<br>
```
public class UnionFind2 implements UnionFind{

    private int[] parent;

    public UnionFind2(int size){
        parent = new int[size];

        //每个节点起始的根节点都是自己，即各自是一个个独立的集合
        for(int i = 0 ; i < size ; i++){
            parent[i] = i;
        }
    }

    @Override
    public int getSize(){
        return parent.length;
    }

    //查找过程，查找元素p所对应的集合编号
    //O(h)复杂度，h为树的高度
    public int find(int p){

        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        while(p != parent[p]){
            p = parent[p];
        }
        return p;
    }

    //查看元素p和q是否同属一个集合
    @Override
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    //合并元素p和元素q所属的集合
    //O(h)复杂度，h为树的高度
    @Override
    public void unionElements(int p, int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot)
            return;

        parent[pRoot] = parent[qRoot];
    }
}
```
#### 基于size的优化
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/un0.png)<br>
Quick Union有可能发生上述情况，造成树太深甚至退化成链表，使得在节点非常多时性能会受到很大的影响<br>
一个非常简单的解决方法就是，在union时判断树的大小，令较小的树的根节点指向较大的树的根节点
```
public class UnionFind3 implements UnionFind {

    private int[] parent;
    private int[] sz; //sz[i]表示以i为根的集合中元素个数

    public UnionFind3(int size){
        parent = new int[size];
        sz = new int[size];

        //每个节点起始的根节点都是自己，即各自是一个个独立的集合
        for(int i = 0 ; i < size ; i++){
            parent[i] = i;
            sz[i] = 1;
        }
    }

    @Override
    public int getSize(){
        return parent.length;
    }

    //查找过程，查找元素p所对应的集合编号
    //O(h)复杂度，h为树的高度
    public int find(int p){

        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        while(p != parent[p]){
            p = parent[p];
        }
        return p;
    }

    //查看元素p和q是否同属一个集合
    @Override
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    //合并元素p和元素q所属的集合
    //O(h)复杂度，h为树的高度
    @Override
    public void unionElements(int p, int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot)
            return;

        //根据两个元素所在树的元素个数不同判断合并方向
        //将元素个数少的集合合并到元素个数多的集合上
        if(sz[pRoot] < sz[qRoot]){
            parent[pRoot] = parent[qRoot];
            sz[qRoot] += sz[pRoot];
        }else{
            parent[qRoot] = parent[pRoot];
            sz[pRoot] += sz[qRoot];
        }
    }
}
```

#### 基于Rank的优化
基于size的优化有时候仍然会出现一些问题<br>
![无法解决问题](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/u3.png)<br>
size大的并不一定就是深度/高度大的<br>,所以我们将比较size大小换成比较rank(高度)大小
```
public class UnionFind4 implements UnionFind{
    private int[] parent;
    private int[] rank; //rank[i]表示以i为根的集合所表示的树的层数

    public UnionFind4(int size){
        parent = new int[size];
        rank = new int[size];

        //每个节点起始的根节点都是自己，即各自是一个个独立的集合
        for(int i = 0 ; i < size ; i++){
            parent[i] = i;
            rank[i] = 1;
        }
    }

    @Override
    public int getSize(){
        return parent.length;
    }

    //查找过程，查找元素p所对应的集合编号
    //O(h)复杂度，h为树的高度
    public int find(int p){

        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        while(p != parent[p]){
            p = parent[p];
        }
        return p;
    }

    //查看元素p和q是否同属一个集合
    @Override
    public boolean isConnected(int p, int q){
        return find(p) == find(q);
    }

    //合并元素p和元素q所属的集合
    //O(h)复杂度，h为树的高度
    @Override
    public void unionElements(int p, int q){

        int pRoot = find(p);
        int qRoot = find(q);

        if(pRoot == qRoot)
            return;

        //根据两个元素所在树rank(高度)判断合并方向
        //将rank低的集合合并到rank高的集合上
        if(rank[pRoot] < rank[qRoot]){
            parent[pRoot] = parent[qRoot];
        }else if(rank[qRoot] < rank[pRoot]){
            parent[qRoot] = parent[pRoot];
        }else{ //rank[qRoot] == rank[pRoot]
            parent[qRoot] = parent[pRoot];
            rank[pRoot]++;
        }
    }
}
```

#### 路径压缩
哪怕是基于Rank优化，免不了树还是会越来越高，此时我们可以通过路径压缩的方法<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/ysa.png)<br>
我们可以在进行find操作的时候顺便进行路径压缩就行了,实际就一行代码
```
    //查找过程，查找元素p所对应的集合编号
    //O(h)复杂度，h为树的高度
    public int find(int p){

        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        while(p != parent[p]){
            parent[p] = parent[parent[p]];
            p = parent[p];
        }
        return p;
    }
```
此时会有一个问题，就是路径压缩后，rank值就不准确了。不过这也是它叫rank的原因<br>
rank并不需要很准确，它实际表示的是排名，根据排名来粗略地进行比较就行了<br>
<br>

上面方式的路径压缩感觉不够彻底，我们希望做到图片中下方的方式那样，所有子节点全部指向一个根节点<br>
可以通过递归实现
```
    //查找过程，查找元素p所对应的集合编号
    //O(h)复杂度，h为树的高度
    public int find(int p){

        if(p < 0 || p >= parent.length){
            throw new IllegalArgumentException("p is out of bound.");
        }

        if(p != parent[p]){
            parent[p] = find(parent[p]);
        }
        return parent[p];
    }
```

我们通过以下测试代码来测试性能
```
import java.util.Random;

public class Main {

    private static double testUF(UnionFind uf, int m){

        int size = uf.getSize();
        Random random = new Random();

        long startTime = System.nanoTime();

        for(int i = 0 ; i < m ; i++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.unionElements(a, b);
        }

        for(int i = 0 ; i < m ; i++){
            int a = random.nextInt(size);
            int b = random.nextInt(size);
            uf.isConnected(a, b);
        }

        long endTime = System.nanoTime();

        return (endTime - startTime) / 1000000000.0;
    }
    public static void main(String[] args) {

        int size = 10000000;
        int m = 10000000;

//        UnionFind1 uf1 = new UnionFind1(size);
//        System.out.println("UnionFind1 : " + testUF(uf1, m) + " s");
//
//        UnionFind2 uf2 = new UnionFind2(size);
//        System.out.println("UnionFind2 : " + testUF(uf2, m) + " s");

        UnionFind3 uf3 = new UnionFind3(size);
        System.out.println("UnionFind3 : " + testUF(uf3, m) + " s");

        UnionFind4 uf4 = new UnionFind4(size);
        System.out.println("UnionFind4 : " + testUF(uf4, m) + " s");

        UnionFind5 uf5 = new UnionFind5(size);
        System.out.println("UnionFind5 : " + testUF(uf5, m) + " s");

        UnionFind6 uf6 = new UnionFind6(size);
        System.out.println("UnionFind6 : " + testUF(uf6, m) + " s");
    }
}
```
运行结果
```
UnionFind3 : 4.997861255 s
UnionFind4 : 4.747258178 s
UnionFind5 : 3.737494873 s
UnionFind6 : 4.418862881 s
```
递归实现全部指向根节点好像性能反而更差一点了，这是为什么呢？
* 递归的开销在数量级很大时是很恐怖的
* 非递归的路径压缩方法其实在最后也会变成所有子节点指向根节点

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/u5.png)<br>
之后若再次调用find(3),实际就变成所有子节点指向根节点了<br>

## 并查集的时间复杂度
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Union%20Find/fz.png)<br>
