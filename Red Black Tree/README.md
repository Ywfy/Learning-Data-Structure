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
  * 分析：根节点要么是2节点，要么是3节点，而两种情况的根节点都是黑色的
* 每一个叶子节点(最后的空节点)是黑色的
  * 注意，这里说的是<strong>最后的空节点</strong>，将他定义为黑色的
  * 而且这里跟第二条性质相吻合，对于一颗空红黑树，他的根节点，亦即最后的空节点是黑色的
* 如果一个节点是红色的，那么他的孩子节点都是黑色的
  * 实际上红色节点的子节点是对应2-3树3节点的左孩子和中间孩子的，只能再次连接一个2节点或者3节点
  * 同样不管是2节点还是3节点，子树的根节点一定是黑色的
  * 此处有一个推论：黑色节点的右孩子一定是黑色的，分析逻辑是一致的
* 任意选定一个节点，从该节点到其每个叶子的所有路径都包含相同数目的黑色节点
  * 分析：2-3树是绝对平衡的，所以有任意选定一个节点，从该节点到其每个叶子的所有路径都包含相同数目的节点
  * 而2-3树的节点到红黑树，不管是2节点还是3节点都只有一个黑色节点，这就形成了一一对应的关系
  * 所以就有了红黑树的这条性质
  
以上性质可以推出：红黑树是保持“黑平衡”的二叉树，即从根节点出发，搜索任意元素直到叶子节点，所经过的黑色节点数是一样的<br>
最终的结果就是红黑树的时间复杂度<strong>最差</strong>为O(2logn)=O(logn)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Red%20Black%20Tree/Image/6.png)<br>
从上图就可以看出，最差就每层都遍历一个黑色节点和红色节点<br>

红黑树比起平衡二叉树：虽然时间复杂度一样，但是红黑树比起AVLTree在添加和删除操作上是要快的，不过相应的红黑树的查询性能要差一些(高度较大),所以若是经常发生添加删除操作就使用红黑树，若是不怎么增删而基本只使用查询操作，则AVLTree要快一些<br>

## 红黑树的添加元素逻辑
