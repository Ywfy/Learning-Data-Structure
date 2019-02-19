# 递归
* 本质上，将原来的问题，转化为更小的同一问题
* 举例：数组求和
  * Sum(arr[0...n-1]) = arr[0] + Sum(arr[1...n-1])   <--更小的同一问题
  * Sum(arr[1...n-1]) = arr[1] + Sum(arr[2...n-1])   <--更小的同一问题
  * ......
  * Sum(arr[n-1...n-1] = arr[n-1] + Sum([])          <--最基本的问题

```
public class ArraySum {
    public static int sum(int[] arr){
        return sum(arr, 0);
    }

    //计算arr[l...n]这个区间内所有数字的和
    private static int sum(int[] arr, int l){
        if( l == arr.length ){
            return 0;
        }
        return arr[l] + sum(arr, l+1);
    }

    public static void main(String[] args){
        int[] nums = {1,2,3,4,5,6,7,8};
        System.out.println(sum(nums));
    }
}
```

## 递归的调用流程
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/List/dg.jpg)<br>
递归的原理很简单，它实际上跟普通的函数调用没有区别<br>
A(n) --> A(n-1) --> A(n-2) ----- A(0)<br>
* A(n)在执行过程中调用A(n-1),A(n-1)在执行过程中执行A(n-2),A(n-2).....直至调用A(0)<br>
* A(0)是最基本情况，可以直接返回结果给A(1)，A(1)继续执行，最后返回结果给A(2)...最终返回结果给A(n),A(n)顺序执行，得出最终的结果<br>

实际上上述调用完全等价于<br>
A(n) --> B(n-1) --> C(n-2) ------X(0)<br>
只不过是每次调用都是同一个函数而已。<br>
