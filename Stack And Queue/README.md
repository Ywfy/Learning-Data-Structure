# 栈和队列

## 栈
原理图<br>
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/z.jpg)<br>

* 栈是一种特殊的线性表，仅能在线性表的一端操作，栈顶允许操作，栈底不允许操作
* 从栈顶放入元素的操作叫入栈，取出元素叫出栈。 
* 栈是一种后进先出的数据结构
* Last In First Out(LIFO)

### 栈的运用
* 撤销
* 程序调用的系统栈

![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Stack%20And%20Queue/dy.png)<br>
每次触发调用的时候，都会将触发调用的点入栈。函数执行完了，查看系统栈有没有元素，若有则出栈跳转执行，若没有则程序执行完毕

### 栈的实现
我们利用之前实现的[Array](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Arrays/README.md#java%E4%B8%8B%E5%AE%9A%E5%88%B6%E6%95%B0%E7%BB%84)类来实现栈<br>
定义Stack接口
```
public interface Stack<T> {
    int getSize();
    boolean isEmpty();
    void push(T t);
    T pop();
    T peek();
}
```

实现Stack接口
```
public class ArrayStack<T> implements Stack<T>{
    private Array<T> array;

    public ArrayStack(int capacity){
        array = new Array<>(capacity);
    }

    public ArrayStack(){
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

    //这里主要是为了扩容的需要
    public int getCapacity(){
        return array.getCapacity();
    }

    @Override
    public void push(T t) {
        array.addLast(t);
    }

    @Override
    public T pop() {
        return array.removeLast();
    }

    @Override
    public T peek() {
        return array.get(getSize()-1);
    }

    @Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        res.append("Stack: ");
        res.append("[");
        for(int i=0; i<array.getSize(); i++){
            res.append(array.get(i));
            if(i != array.getSize()-1){
                res.append(",");
            }
        }
        res.append("] top");
        return res.toString();
    }
}
```

时间复杂度分析
```
void push(T t)     O(1)均摊
T pop()            O(1)均摊
T peek()           O(1)
int getSize()      O(1)
boolean isEmpty()  O(1)
```

### 使用Java自带的栈实现括号匹配功能
```
public static boolean isValid(String s){
        Stack<Character> stack = new Stack<>();
        for(int i=0; i<s.length(); i++){
            char c = s.charAt(i);
            if(c == '(' || c == '['|| c == '{' ) {
                stack.push(c);
            }else{
                if(stack.isEmpty())
                    return false;

                char top = stack.pop();
                if(top == '(' && c == ')')
                    continue;
                if(top == '[' && c == ']')
                    continue;
                if(top == '{' && c == '}')
                    continue;
                //前面三个都不匹配，证明括号配对失败
                return false;
            }
        }

        return stack.isEmpty();
    }
```
