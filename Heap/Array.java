import java.util.Arrays;

public class Array<T> {
    private T[] data;
    private int size;

    //构造器，传入数组的容量capacity构造Array
    public Array(int capacity){
        //泛型数组是无法被直接实例化的，这是java的历史遗留问题
        //data = new T[capacity];
        data = (T[])new Object[capacity];
        size = 0;
    }
    //无参构造器,默认数组容量为10
    public Array(){
        this(10);
    }
    
    public Array(T[] arr){
        data = (T[])new Object[arr.length];
        for(int i = 0 ; i < arr.length ; i++){
            data[i] = arr[i];
        }
        size = arr.length;
    }

    //获取数组中的元素个数
    public int getSize(){
        return size;
    }

    //获取数组的容量
    public int getCapacity(){
        return data.length;
    }

    //返回数组是否为空
    public boolean isEmpty(){
        return size  == 0;
    }

    //往数组末尾添加一个新元素
    public void addLast(T e){

        /*
        if(size == data.length){
            throw new IllegalArgumentException("AddLast failed,Array is full");
        }
        data[size] = e;
        size++;
        */
        add(size, e);

    }

    public void addFirst(T e){
        add(0, e);
    }

    //在index索引的位置插入一个新元素e
    //以下方法只能插入数组的有存储数据的连续区间段内，
    //比如，0，1，2，3，位置是有元素的，那我们此时可以插入的位置只有0,1,2,3,4
    //而不能插入包括5的以后的位置
    public void add(int index,T e){
        if(index < 0 || index > size){
            throw new IllegalArgumentException("Add failed,Required index >= 0 and index <= size");
        }
        if(size == data.length){
            resize(data.length * 2);
        }
        for(int i=size-1; i >=index; i--){
            data[i+1] = data[i];
        }

        data[index] = e;
        size++;
    }

    //获取index索引位置的元素
    public T get(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Get failed,Index is illegal");
        }
        return data[index];
    }

    //修改index索引位置的元素为e
    public void set(int index, T e){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Set failed,Index is illegal");
        }
        data[index] = e;
    }

    //查找数组中是否有元素e
    public boolean contains(T e){
        for(int i=0; i<size; i++){
            if(data[i].equals(e)){
                return true;
            }
        }

        return false;
    }

    //查找数组中元素e所在的索引，如果不存在元素e，则返回-1
    public int find(T e){
        for(int i=0; i<size; i++){
            if(data[i].equals(e)){
                return i;
            }
        }

        return -1;
    }

    //从数组中删除index位置的元素，返回删除的元素
    public T remove(int index){
        if(index < 0 || index >= size){
            throw new IllegalArgumentException("Remove failed,Index is illegal");
        }

        T ret = data[index];
        for(int i=index+1; i<size; i++){
            data[i-1] = data[i];
        }

        //使用泛型时，此处虽然size-1了，但是
        //data[size]实际上还是保留着对对象的引用
        //这不便于垃圾回收机制的执行
        //所以将data[size] = null，使得不再使用的对象能迅速被垃圾回收机制所回收
        size--;
        data[size] = null;

        //此处设置为size缩小到1/4，才缩小length为原来的1/2
        //可以有效的防止复杂度的震荡
        //若是设置为size缩小到1/2,length就缩小到1/2，则可能出现
        //容量满后，add扩容，remove马上缩容，add又扩容,remove又缩容这种极端情况
        if(size == data.length/4 && data.length / 2 != 0){
            resize(data.length/2);
        }
        return ret;
    }

    //从数组中删除第一个元素，返回删除的元素
    public T removeFirst(){
        return remove(0);
    }

    //从数组中删除最后一个元素，返回删除的元素
    public T removeLast(){
        return remove(size-1);
    }

    //从数组中删除元素e
    public void removeElement(T e){
        int index = find(e);
        if(index != -1){
            remove(index);
        }
    }

    //交换索引i和j位置的两个元素
    public void swap(int i, int j){

        if(i < 0 || i >= size || j < 0 || j>= size)
            throw new IllegalArgumentException("Index is illegal.");

        T t = data[i];
        data[i] = data[j];
        data[j] = t;
    }
    @Override
    public String toString() {

        StringBuilder res = new StringBuilder();
        res.append(String.format("Array: size=%d, capacity=%d\n", size, data.length));
        res.append("[");
        for(int i=0; i<size; i++){
            res.append(data[i]);
            if(i != size-1)
                res.append(",");
        }
        res.append("]");
        return res.toString();
    }

    //此处应该用private修饰，因为数组扩容或者缩容应该是在需要时自动发生
    //而用户并不需要去关心
    //此处原理很简单，new一个新的数组，然后将数据复制过去，最后指向新的数组
    private void resize(int newCapacity){
        T[] newData = (T[])new Object[newCapacity];
        for(int i=0; i<size; i++){
            newData[i] = data[i];
        }
        data = newData;
    }
}
