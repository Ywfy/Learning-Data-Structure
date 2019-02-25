# 红黑树

## 红黑树与2-3树的等价性
红黑树是在2-3树的基础上演变来的,希望整棵树只有2节点,以下是演变流程<br><br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/1.png)<br>
节点的对应：2节点就不用说了，3节点的话只能采用两个节点的方式来对应<br><br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/2.png)<br>
然而b是作为c的左子节点，这并不能表示出b和c关系的特殊性,这时我们考虑将b和c的连接线染成红色,以表示b和c对应2-3树的3节点<br><br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/3.png)<br>
不过由于节点只能有一个父节点，所以我们可以将连接线的信息转嫁到子节点上，线仍然是黑色，而b染成红色<br><br>
在这里，我们规定：<strong>所有的红色节点都是左倾斜的</strong><br><br>

以下是一个对应的案例：<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/4.png)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/5.png)<br>

## 红黑树的基本性质
<<算法导论>>给出了红黑树的五大基本性质：
* 每个节点或者是红色的，或者是黑色的
* 根节点是黑色的
* 每一个叶子节点(最后的空节点)是黑色的
* 如果一个节点是红色的，那么他的孩子节点都是黑色的
* 从任意一个节点到叶子节点，经过的黑色节点数是一样的
