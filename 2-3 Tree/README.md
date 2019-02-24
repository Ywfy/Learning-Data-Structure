# 2-3树
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/2-3%E6%A0%91.png)<br>
* 2-3树满足二分搜索树的性质，但它并不是一颗二叉树
* 2-3有两种节点，如上图所示，一种存放一个元素，一种存放两个元素
  * 存放一个元素的节点(叫“2节点”)：左孩子小于a，右孩子大于a
  * 存放两个元素的节点(叫“3节点”): 左孩子小于b，中间孩子处于b、c之间，右孩子大于c
* 每个节点有2个或者3个孩子，这也就是2-3树命名的由来

2-3树示例：<br>
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/2-3%E6%A0%912.png)<br>

## 2-3树是一颗绝对平衡的树
2-3是如何维持绝对平衡的？我们以元素添加流程为例<br><br>
* 往空2-3树添加42，42作为根节点
* 添加元素37

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/42%2B37.png)<br>
2-3树添加节点是不会添加到空位置的，他在逐级比较到叶子节点后，会跟叶子节点发生融合<br>
融合的位置看相对大小，比原来的叶子节点值大就右边，小就左边<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B37two.png)<br>
* 添加元素12

照样比较到叶子节点后，跟叶子节点发生融合<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B12.png)<br>
添加后，此时是一个4节点，这是不符合2-3树的定义的，进行拆分<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B12two.png)<br>
* 添加元素18

18跟37比较，来到左边，跟12比较，要去右边，发现12是个叶子节点，于是跟12发生融合<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B18.png)<br>
* 添加元素6

同样，逐级比较来到(12,18)节点，比它小，要去左边，发现是个叶子节点，发生融合<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B6.png)<br>
出现了4节点，要进行拆分<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B6two.png)<br>
<strong>注意此时没完，由于12不是根节点，所以12要进行向上融合</strong><br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B6three.png)<br>
* 添加元素11

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B11.png)<br>
* 添加元素5

![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B5.png)<br>
出现4节点，要进行拆分<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B5two.png)<br>
6不是根节点，要进行向上融合<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B5three.png)<br>
出现了4节点，要进行拆分<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/2-3%20Tree/Image/%2B5four.png)<br>
