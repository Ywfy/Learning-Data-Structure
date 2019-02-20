![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/BinaryTree.png)<br>
* 上最层的节点，也就是28，叫根节点,二叉树的根节点是唯一的
* 二叉树每个节点的下方最多有左右两个子节点
* 相对应的，二叉树每个节点最多有一个父节点
* 若节点没有子节点，则可以称为叶子节点
* 二叉树具有天然递归结构
  * 每个节点的左子树也是二叉树
  * 每个节点的右子树也是二叉树
* 二叉树并不一定是“满”的
  * 满二叉树：除了叶子节点外，其他所有节点都拥有两个子节点
  
<br><br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/bst.png)<br>
* 二分搜索树存储的元素必须有可比较性
* 判断并不包含“=”情况，所以该种情况的二分搜索树是没有重复元素的
  * 想要包含重复情况，只需要将“=”情况加到判断条件中就行了，比如每个节点<strong>大于等于</strong>左子树所有节点的值
  

# 实现二分搜索树
## 编写Node节点，完成添加功能
```
public class BST<T extends Comparable<T>> {

    private class Node{
        public T t;
        public Node left, right;

        public Node(T t){
            this.t = t;
            left = null;
            right = null;
        }
    }

    private Node root;
    private int size;

    public BST(){
        root = null;
        size = 0;
    }

    public int size(){
        return size;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    //向二分搜索树中添加新的元素t
    public void add(T t){
        if(root == null){
            root = new Node(t);
            size++;
        }else{
            add(root, t);
        }
    }

    //向以node为根的二分搜索树中插入元素t，递归算法
    private void add(Node node, T t){

        if(t.equals(node.t))
            return;
        else if(t.compareTo(node.t) < 0 && node.left == null){
            node.left = new Node(t);
            size++;
            return;
        }
        else if(t.compareTo(node.t) > 0 && node.right == null){
            node.right = new Node(t);
            size++;
            return;
        }

        if(t.compareTo(node.t) < 0)
            add(node.left, t);
        else
            add(node.right, t);
    }
}

```
上面add方法可以进行简化
```
 //向二分搜索树中添加新的元素t
    public void add(T t){
        root = add(root, t);
    }

    //向以node为根的二分搜索树中插入元素t，递归算法
    private Node add(Node node, T t){
        if(node == null){
            size++;
            return new Node(t);
        }

        if(t.compareTo(node.t) < 0)
           node.left = add(node.left, t);
        else if(t.compartTo(node.t) > 0)
           node.right = add(node.right, t);

        return node;
    }
```
新的add方法的思想是
* 新的元素的添加必然是发生在NULL节点的,NULL节点必然处于父节点的左子树或者右子树
* 当node不等于null，就确定值是要插入到左子树或者右子树             <---同一问题的更小情况
* 直到出现node==null，也就是到达了NULL节点，此时创建Node对象并返回 <---最基本情况 
* 返回的Node对象被父节点所挂载

## 添加“查询是否包含元素”功能
```
 //看二分搜索树中是否包含元素t
    public boolean contains(T t){
        return contains(root ,t);
    }

    //看以node为根的二分搜索树中是否包含元素t，递归算法
    private boolean contains(Node node, T t){

        if(node == null)
            return false;

        if(t.compareTo(node.t) == 0)
            return true;
        else if(t.compareTo(node.t) < 0)
            return contains(node.left, t);
        else
            return contains(node.right, t);
    }
```

## 添加“遍历”功能
遍历有三种方法：前序遍历、中序遍历、后序遍历
### 前序遍历
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/qx.png)<br>
* 前序遍历：也就是先访问父节点，然后访问左子树和右子树
* 前序遍历是最自然和最常用的遍历方式
```
//二分搜索树的前序遍历
    public void preOrder(){
        preOrder(root);
    }

    //前序遍历以node为根的二分搜索树，递归算法
    private void preOrder(Node node){
        if(node == null)
            return;

        System.out.println(node.t);
        preOrder(node.left);
        preOrder(node.right);
    }
```

* 用前序遍历实现toString()方法
```
@Override
    public String toString() {
        StringBuilder res = new StringBuilder();
        generateBSTString(root, 0, res);
        return res.toString();
    }

    //生成以node为根节点，深度为depth的描述二叉树的字符串
    public void generateBSTString(Node node, int depth, StringBuilder res){

        if(node == null){
            res.append(generateDepthString(depth) + "null\n");
            return;
        }

        res.append(generateDepthString(depth) + node.t + "\n");
        generateBSTString(node.left, depth + 1, res);
        generateBSTString(node.right, depth + 1, res);
    }

    private String generateDepthString(int depth){
        StringBuilder res = new StringBuilder();
        for(int i = 0 ; i < depth; i++){
            res.append("--");
        }
        return res.toString();
    }
```

### 中序遍历
<img src="https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/zx.png" width = "300" height = "300" div align=left /><br>
区别很简单，先访问左子树，然后访问父节点，最后访问右子树<br>
```
 //二分搜索树的中序遍历
    public void inOrder(){
        inOrder(root);
    }

    //中序遍历以node为根的二分搜索树，递归算法
    private void inOrder(Node node){
        if(node == null)
            return;

        inOrder(node.left);
        System.out.println(node.t);
        inOrder(node.right);
    }
```
* 二分搜索树的中序遍历结果是顺序的

### 后序遍历
![图片无法加载](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/hx.png)<br>
先访问左子树，后访问右子树，最后访问节点内容
```
 //二分搜索树的后序遍历
    public void postOrder(){
        postOrder(root);
    }

    //后序遍历以node为根的二分搜索树，递归算法
    private void postOrder(Node node){
        if(node == null)
            return;

        postOrder(node.left);
        postOrder(node.right);
        System.out.println(node.t);
    }
```

### 补充：二分搜索树的前序遍历的非递归实现
```
// 二分搜索树的非递归前序遍历
    public void preOrderNR(){

        Stack<Node> stack = new Stack<>();
        stack.push(root);
        while(!stack.isEmpty()){
            Node cur = stack.pop();
            System.out.println(cur.t);

            if(cur.right != null)
                stack.push(cur.right);
            if(cur.left != null)
                stack.push(cur.left);
        }
    }
```
中序遍历、后序遍历的非递归实现较复杂，实际应用不广，先不做讨论

### 层序遍历
* 之前的前序遍历、中序遍历、后序遍历在执行时都会首先层层调用达到叶子节点，也就是最左边的一条。这叫做深度优先遍历
* 相对的，还有另一种遍历方法：广度优先遍历,也叫层序遍历
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/cx.png)<br>

#### 借助队列完成二分搜索树的层序遍历
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Binary%20Search%20Tree/cxyl.png)<br>
* 根节点入队，第0层入队完毕，将根节点28出队，然后根节点28的两个子节点16、30入队(注意先入队左子节点)
* 16出队，将16的子节点13、22入队；30出队，将30的子节点29、42入队。第二层入队完毕
* 13出队，13是叶子节点故没有新元素入队，后面的22、29、42同理出队
* 最终队列为空，层序遍历完毕

<br>
广度优先遍历的意义：
* 更快的找到问题的解
* 常用于算法设计中--无权图的最短路径

## 添加“删除”功能
### 删除二分搜索树的最小值和最大值
* 由二分搜索树的定义和图，可以很明显的看出
  * 二分搜索树的最小值也就是最左边的值
  * 二分搜索树的最大值也就是最右边的值

```
 //返回二分搜索树的最小值
    public T minimum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return minimum(root).t;
    }

    //返回以node为根的二分搜索树的最小值所在的节点
    private Node minimum(Node node){
        if(node.left == null)
            return node;
        return minimum(node.left);
    }

    //查找二分搜索树的最大元素
    public T maximum(){
        if(size == 0)
            throw new IllegalArgumentException("BST is empty.");

        return maximum(root).t;
    }

    //返回以node为根的二分搜索树的最大值所在的节点
    private Node maximum(Node node){
        if(node.right == null)
            return node;

        return maximum(node.right);
    }
```

删除最大值、最小值分两种情况：
* 最大值、最小值处于叶子节点，这个时候很简单
* 最大值、最小值所在节点不是叶子节点
  * 对于最小值，会存在右子树，删除最小值所在节点后，需要将右子树上移挂载
  * 对于最大值，会存在左子树，删除最大值所在节点后，需要将左子树上移挂载
  
```
 //删除掉以node为根的二分搜索树中的最小节点
    //返回删除节点后新的二分搜索树的根
    private Node removeMin(Node node){
        if(node.left == null){
            Node rightNode = node.right;
            node.right = null;
            size--;
            return rightNode;
        }

        node.left = removeMin(node.left);
        return node;
    }

    //从二分搜索树中删除最大值所在的节点
    public T removeMax(){
        T ret = maximum();
        root = removeMax(root);
        return ret;
    }

    //删除掉以node为根的二分搜索树中的最大节点
    //返回删除节点后新的二分搜索树的根
    private Node removeMax(Node node){

        if(node.right == null){
            Node leftNode = node.left;
            node.left = null;
            size--;
            return leftNode;
        }

        node.right = removeMax(node.right);
        return node;
    }
```

### 删除二分搜索树的任意元素
* 只有左子树的节点，将节点删除，左子树上移挂载
* 只有右子树的节点，将节点删除，右子树上移挂载
* 同时有左子树和右子树，采用Hibbard Deletion
  * 找比被删节点值大的最小的元素，也就是右子树的最小值
  * 它仍然能满足二分搜索树的定义，而不致破坏结构
  * 将这个右子树的最小值脱离，替换到被删节点的位置,也就是要和被删节点的左子节点、右子节点和父节点建立连接

```
 //从二分搜索树中删除元素为t的节点
    public void remove(T t){
        root = remove(root, t);
    }

    //删除以node为根的二分搜索树中值为t的节点，递归算法
    //返回删除节点后新的二分搜索树的根
    private Node remove(Node node, T t){

        if(node == null)
            return null;

        if(t.compareTo(node.t) < 0){
            node.left = remove(node.left, t);
            return node;
        }
        else if(t.compareTo(node.t) > 0){
            node.right = remove(node.right, t);
            return node;
        }
        else{ //t.equals(node.t)
            //待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size--;
                return rightNode;
            }
            //待删除节点右子树为空的情况
            if(node.right == null){
                Node leftNode = node.left;
                node.left = null;
                size--;
                return leftNode;
            }
            //待删除节点左右子树均不为空的情况
            //找到比待删除节点大的最小节点，即待删除节点右子树的最小节点
            //用这个最小节点顶替待删除节点的位置
            Node successor = minimum(node.right);
            successor.right = removeMin(node.right);
            successor.left = node.left;

            node.left = node.right = null;

            return successor;
        }
    }
```
