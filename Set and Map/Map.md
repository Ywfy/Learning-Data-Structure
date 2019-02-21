# 映射(Map)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Set%20and%20Map/m.png)<br>
* 存储(键,值)数据对的数据结构(key-value)
* 根据键(key),寻找值(value)
* 非常容易使用链表或者二分搜索树实现

```
class Node{
  K key;
  V value;
  Node next;
}
```
```
class Node{
  K key;
  V value;
  Node left;
  Node right;
}
```

## 定义Map的接口
```
public interface Map<K,V> {

    void add(K key, V value);
    V remove(K key);
    boolean contains(K key);
    V get(K key);
    void set(K key, V newValue);
    int getSize();
    boolean isEmpty();
}
```

## 基于链表实现Map
```
import java.util.ArrayList;

public class LinkedListMap<K, V> implements Map<K,V> {

    private class Node{
        public K key;
        public V value;
        public Node next;

        public Node(K key, V value, Node next){
            this.key = key;
            this.value = value;
            this.next = next;
        }

        public Node(K key, V value){
            this(key, value, null);
        }

        public Node(){
            this(null, null, null);
        }

        @Override
        public String toString(){
            return key.toString() + " : " + value.toString();
        }
    }

    private Node dummyHead;
    private int size;

    public LinkedListMap(){
        dummyHead = new Node();
        size = 0;
    }

    private Node getNode(K key){
        Node cur = dummyHead.next;
        while(cur != null){
            if(cur.key.equals(key))
                return cur;

            cur = cur.next;
        }
        return null;
    }

    @Override
    public void add(K key, V value) {
        Node node = getNode(key);
        if(node == null){
            dummyHead.next = new Node(key, value, dummyHead.next);
            size++;
        }else{
            //若key已存在，则更新对应value的值
            node.value = value;
        }
    }

    @Override
    public V remove(K key) {
        Node prev = dummyHead;
        while(prev.next != null){
            if(prev.next.key.equals(key))
                break;
            prev = prev.next;
        }

        if(prev.next != null){
            Node delNode = prev.next;
            prev.next = delNode.next;
            delNode.next = null;
            size--;
            return delNode.value;
        }

        return null;
    }

    @Override
    public boolean contains(K key) {
        return getNode(key) != null;
    }

    @Override
    public V get(K key) {
        Node node = getNode(key);
        return node == null ? null : node.value;
    }

    @Override
    public void set(K key, V newValue) {
        Node node = getNode(key);
        if(node == null)
            throw new IllegalArgumentException("Key doesn't exist!");

        node.value = newValue;
    }

    @Override
    public int getSize() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    public static void main(String[] args){

        System.out.println("pride-and-prejudice");

        ArrayList<String> words = new ArrayList<>();
        if(FileOperation.readFile("pride-and-prejudice.txt", words)){
            System.out.println("Total words: " + words.size());

            LinkedListMap<String,Integer> map = new LinkedListMap<>();
            for(String word : words){
                if(map.contains(word)){
                    map.set(word, map.get(word) + 1);
                }else{
                    map.add(word, 1);
                }
            }

            System.out.println("Total different words: " + map.getSize());
            System.out.println("Frequency of PRIDE: " + map.get("pride"));
            System.out.println("Frequency of PREJUDICE: " + map.get("prejudice"));
        }
        System.out.println();
    }
}
```
运行结果
```
pride-and-prejudice
Total words: 125901
Total different words: 6530
Frequency of PRIDE: 53
Frequency of PREJUDICE: 11
```
运行起来很慢，我的电脑出结果差不多用了七八秒钟

## 基于二分搜索树实现Map
```

```
