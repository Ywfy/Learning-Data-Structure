# 哈希表(hash table)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/2.jpg)<br>

* 散列表（Hash table，也叫哈希表），是根据关键码值(Key value)而直接进行访问的数据结构。也就是说，它通过把关键码值映射到表中一个位置来访问记录，以加快查找的速度。这个映射函数叫做散列函数，存放记录的数组叫做散列表。

* 给定表M，存在函数f(key)，对任意给定的关键字值key，代入函数后若能得到包含该关键字的记录在表中的地址，则称表M为哈希(Hash）表，函数f(key)为哈希(Hash) 函数。<br>

## 哈希函数的设计
* “键”通过哈希函数得到的“索引”分布越均匀越好

哈希函数的设计还是要具体问题具体分析，下面简单介绍一些方法<br>
### 整型
```
小范围正整数直接使用
小范围负整数进行偏移 ----- -100~100 -> 0~200 

大整数
例如身份证，可以采用模一个素数
```
素数选择参考<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/4.png)<br>
上图来自https://planetmath.org/goodhashtableprimes<br>

### 浮点型
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/5.png)<br>
然后采用对大整数进行素数取模就行了

### 字符串
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/7.png)<br>
B的大小自己选，M是取的素数

### 复合类型
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/9.png)<br><br>

哈希函数设计的原则:
* 一致性：如果a==b，则hash(a) == hash(b)
* 高效性：计算高效简便
* 均匀性：哈希值均匀分布

## Java中的hashCode
java的包装类和String都已经自带实现了hashCode
```
        int a = 42;
        System.out.println(((Integer)a).hashCode());

        //java的hashCode返回的是int值，所以可以是负的
        int b = -42;
        System.out.println(((Integer)b).hashCode());

        double c = 3.1415926;
        System.out.println(((Double)c).hashCode());

        String d = "imooc";
        System.out.println(d.hashCode());
```

创建一个自己的类
```
public class Student {

    int grade;
    int cls;
    String firstName;
    String lastName;

    public Student(int grade, int cls, String firstName, String lastName) {
        this.grade = grade;
        this.cls = cls;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public int hashCode(){

        int B = 31;
        int hash = 0;
        hash = hash * B + grade;
        hash = hash * B + cls;
        hash = hash * B + firstName.toLowerCase().hashCode();
        hash = hash * B + lastName.toLowerCase().hashCode();

        return hash;
    }

    //注意，这是在发生哈希冲突时辨别对象的方法，若是想要将对象放入HashMap、HashSet等就必须和hashCode方法一起覆盖
    @Override
    public boolean equals(Object o){

        if(this == o)
            return true;

        if(o == null)
            return false;

        if(getClass() != o.getClass())
            return false;

        Student another = (Student)o;
        return this.grade == another.grade &&
                this.cls == another.cls &&
                this.firstName.toLowerCase().equals(another.firstName.toLowerCase()) &&
                this.lastName.toLowerCase().equals(another.lastName.toLowerCase());
    }
}
```

测试代码
```
        Student stu = new Student(3, 3, "yw", "Lg");
        System.out.println(stu.hashCode());

        HashSet<Student> set = new HashSet<>();
        set.add(stu);

        HashMap<Student, Integer> scores = new HashMap<>();
        scores.put(stu, 99);

        //java的Object类自带有hashCode的实现，是根据对象地址计算的
        Student stu2 = new Student(3, 3, "yw", "Lg");
        System.out.println(stu2.hashCode());
```

## 哈希冲突的处理——链地址法(Seperate Chaining)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/10.png)<br>

## 代码实现HashTable
```
import java.util.TreeMap;

public class HashTable<K, V> {

    private TreeMap<K, V>[] hashtable;
    private int M;
    private int size;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashtable = new TreeMap[M];
        for(int i = 0 ; i < M ; i++){
            hashtable[i] = new TreeMap<>();
        }
    }

    public HashTable(){
        this(97);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    public void add(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(map.containsKey(key))
            map.put(key, value);
        else{
            map.put(key, value);
            size++;
        }
    }

    public V remove(K key){
        TreeMap<K, V> map = hashtable[hash(key)];
        V retValue = null;
        if(map.containsKey(key)){
            retValue = map.remove(key);
            size--;
        }
        return retValue;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(!map.containsKey(key))
            throw new IllegalArgumentException("key doesn't exist!");
        map.put(key, value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }
}
```

## 哈希表的动态空间处理
数组的大小M固定是不合理，在随着N不断扩大的过程中，M的过小将极大地增加碰撞的几率，使哈希表的性能下降<br>
所以我们需要使得M能够进行动态地调整<br>
N / M >= upperTol<br>
* 平均每个地址承载的元素多过一定程序，即扩容
* 平均每个地址承载的元素少过一定程度，则缩容
N / M < lowerTol<br>

```
import java.util.TreeMap;

public class HashTable<K, V> {

    private static final int UPPER_TOL = 10;
    private static final int LOWER_TOL = 2;
    private static final int initCapacity = 7;

    private TreeMap<K, V>[] hashtable;
    private int M;
    private int size;

    public HashTable(int M){
        this.M = M;
        size = 0;
        hashtable = new TreeMap[M];
        for(int i = 0 ; i < M ; i++){
            hashtable[i] = new TreeMap<>();
        }
    }

    public HashTable(){
        this(initCapacity);
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    public void add(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(map.containsKey(key))
            map.put(key, value);
        else{
            map.put(key, value);
            size++;

            if(size >= UPPER_TOL * M)
                resize(2 * M);
        }
    }

    public V remove(K key){
        TreeMap<K, V> map = hashtable[hash(key)];
        V retValue = null;
        if(map.containsKey(key)){
            retValue = map.remove(key);
            size--;

            if(size < LOWER_TOL * M && M / 2 >= initCapacity)
                resize(M / 2);
        }
        return retValue;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(!map.containsKey(key))
            throw new IllegalArgumentException("key doesn't exist!");
        map.put(key, value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }

    private void resize(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for(int i = 0 ; i < newM; i++){
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;
        for(int i = 0 ; i < M ; i++){
            TreeMap<K, V> map = hashtable[i];
            for(K key : map.keySet()){
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }
        this.hashtable = newHashTable;
    }
}
```

## 哈希表的复杂度分析
跟动态数组同理，均摊复杂度为O(1)

## 更复杂的动态空间处理方法
简单地将数组容量M\*2，会导致M不再是素数，我们在一开始就讲了，对于大整数，用素数来取模能够达到比较好的均匀分布效果<br>
那怎么办呢？
很简单，我们就根据那张表，不够就扩容到下一个容量就行了，而不采用简单\*2的方式<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/4.png)<br>

```
import java.util.TreeMap;

public class HashTable<K, V> {

    private final int[] capacity = {
            53, 97, 193, 389, 769, 1543, 3079, 6151, 12289, 24593,
            49157, 98317, 196613, 393241, 786433, 1572869, 3145739, 6291469,
            12582917, 25165843, 50331653, 100663319, 201326611, 402653189, 805306457, 1610612741};

    private static final int UPPER_TOL = 10;
    private static final int LOWER_TOL = 2;
    private int capacityIndex = 0;

    private TreeMap<K, V>[] hashtable;
    private int M;
    private int size;

    public HashTable(){
        this.M = capacity[capacityIndex];
        size = 0;
        hashtable = new TreeMap[M];
        for(int i = 0 ; i < M ; i++){
            hashtable[i] = new TreeMap<>();
        }
    }

    private int hash(K key){
        return (key.hashCode() & 0x7fffffff) % M;
    }

    public int getSize(){
        return size;
    }

    public void add(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(map.containsKey(key))
            map.put(key, value);
        else{
            map.put(key, value);
            size++;

            if(size >= UPPER_TOL * M && capacityIndex + 1 < capacity.length)
                capacityIndex++;
                resize(capacity[capacityIndex]);
        }
    }

    public V remove(K key){
        TreeMap<K, V> map = hashtable[hash(key)];
        V retValue = null;
        if(map.containsKey(key)){
            retValue = map.remove(key);
            size--;

            if(size < LOWER_TOL * M && capacityIndex >= 1)
                capacityIndex--;
                resize(capacity[capacityIndex]);
        }
        return retValue;
    }

    public void set(K key, V value){
        TreeMap<K, V> map = hashtable[hash(key)];
        if(!map.containsKey(key))
            throw new IllegalArgumentException("key doesn't exist!");
        map.put(key, value);
    }

    public boolean contains(K key){
        return hashtable[hash(key)].containsKey(key);
    }

    public V get(K key){
        return hashtable[hash(key)].get(key);
    }

    private void resize(int newM){
        TreeMap<K, V>[] newHashTable = new TreeMap[newM];
        for(int i = 0 ; i < newM; i++){
            newHashTable[i] = new TreeMap<>();
        }

        int oldM = M;
        this.M = newM;
        for(int i = 0 ; i < M ; i++){
            TreeMap<K, V> map = hashtable[i];
            for(K key : map.keySet()){
                newHashTable[hash(key)].put(key, map.get(key));
            }
        }
        this.hashtable = newHashTable;
    }
}
```

## 哈希表的弊端
哈希表能够做到均摊时间复杂度为O(1),它必然牺牲了某些方面的性能，那么他牺牲了什么呢？<br>
答案是：顺序性<br>
前面的二分搜索树包括AVL树，红黑树，它们的时间复杂度都是O(logn)的，它们都具有顺序性
* 中序遍历能够顺序输出整棵树
* 能够快速找到某个节点的前驱和后继

## 集合、映射分类
加入哈希表后，集合映射就分成两类了
* 有序集合、有序映射，底层使用平衡树结构实现，典型就是java标准库的TreeMap、TreeSet类
* 无序集合、无序映射，底层使用哈希表实现，典型就是java标准库的HashMap、HashSet类

## 目前我们实现的HashTable类的一个bug
哈希表正常是不要求元素具有可比较性的，但是我们实现HashTable类底层是用TreeMap实现的，并且TreeMap是要求Key可比较，即实现Comparable接口，所以实际上哈希表的K也应该设置为可比较的，这里顺便扯一扯java标准库，其实前面也说过了
* jdk1.8之前哈希表数组节点是统一使用链表的
* jdk1.8及以后哈希表在哈希冲突达到一定程度后，每一个位置从链表转为红黑树

既然都使用红黑树了，那哈希表的K显然也是要具有可比较性的<br>
