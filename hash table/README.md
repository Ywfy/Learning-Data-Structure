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
···


## 哈希冲突的处理——链地址法(Seperate Chaining)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/hash%20table/Image/10.png)<br>
