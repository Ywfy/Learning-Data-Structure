# 平衡二叉树(AVL树)
前面学习二分搜索树的时候，发现二分搜索树存在一个隐患，就是二分搜索树有可能退化成一个链表<br>
例如，若我们顺序添加1,2,3,4,5,6,7,...那结果显然就变成一个链表了<br>
为了解决这个情况，出现了平衡二叉树，也叫AVL树<br>

## 什么是平衡二叉树
平衡二叉树,全名平衡二叉搜索树，定义条件：对于任意一个节点，左子树和右子树的高度差不能超过1<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/ph.png)<br>
对于这个定义，满二叉树，完全二叉树，线段树都能够满足，所以他们是平衡二叉树<br>

若我们对上图中的平衡二叉树添加2和7，结果如下<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/bph.png)<br>

计算每个节点高度和平衡因子(左子树和右子树高度差)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/yz.png)<br>
此时8和12的平衡因子为2，显然不满足平衡二叉树的定义了,所以此时我们需要某些措施<br>

<br>
平衡二叉树其实就是在二分搜索树的基础上添加上平衡机制，所以我们在之前实现的BST类进行修改就行了<br>

[BST类代码直通车](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/BST.java)<br>

## 添加元素平衡
由于我们只有新添加了一个节点，才有可能导致之前的平衡二叉树变得不平衡，而不平衡节点的出现只可能出现在插入节点(叶子节点)的父辈节点上。所以我们维护平衡的时机应该是我们加入节点后，沿着这个节点向上回溯来维持树的平衡性。由于add操作本来就是递归实现的，所以逻辑非常简单
```
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        //更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1){
            //在此处进行节点平衡操作，使得平衡因子<=1
            System.out.println("unbalance : " + balanceFactor);
        }
        return node;
    }
```

维护时的情况有四种，LL,RR,LR,RL

### LL(插入的元素在不平衡节点的左侧的左侧)----右旋转

看个例子：<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/LL1.png)<br>
添加一个2<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/LL2.png)<br>
解决方法:<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/LL3.png)<br>
验证流程：
* 满足二分搜索树的性质：左子树<父节点<右子树，看图显然满足
* 是否满足平衡二叉树定义
  * 设T1、T2的最大高度为h，z的高度为h+1
  * 由于以x为根的树是平衡的，又由于这是插入左侧的左侧导致不平衡，所以必然是z>=T3,最终T3只能是h+1，h
  * 则x的高度为h+2
  * 由于这是左侧添加一次后导致不平衡，T4的高度必然比x小2，即h
  * 将高度套到新构成的树后验证，确定平衡
  
### RR(插入的元素在不平衡节点的右侧的右侧)----左旋转
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/RR1.png)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/RR2.png)<br>


### LL和RR的代码
```
    private Node add(Node node, K key, V value){

        if(node == null){
            size ++;
            return new Node(key, value);
        }

        if(key.compareTo(node.key) < 0)
            node.left = add(node.left, key, value);
        else if(key.compareTo(node.key) > 0)
            node.right = add(node.right, key, value);
        else // key.compareTo(node.key) == 0
            node.value = value;

        //更新height
        node.height = 1 + Math.max(getHeight(node.left), getHeight(node.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(node);
        if(Math.abs(balanceFactor) > 1){
            System.out.println("unbalance : " + balanceFactor);
        }

        //平衡维护
        if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);
        if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);
        
        return node;
    }
    
    // 对节点y进行向右旋转操作，返回旋转后新的根节点x
    //        y                              x
    //       / \                           /   \
    //      x   T4     向右旋转 (y)        z     y
    //     / \       - - - - - - - ->    / \   / \
    //    z   T3                       T1  T2 T3 T4
    //   / \
    // T1   T2
    private Node rightRotate(Node y){
        Node x = y.left;
        Node T3 = x.right;
        //向右旋转过程
        x.right = y;
        y.left = T3;

        //更新Height值
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }

    // 对节点y进行向左旋转操作，返回旋转后新的根节点x
    //    y                             x
    //  /  \                          /   \
    // T1   x      向左旋转 (y)       y     z
    //     / \   - - - - - - - ->   / \   / \
    //   T2  z                     T1 T2 T3 T4
    //      / \
    //     T3 T4
    private Node leftRotate(Node y){
        Node x = y.right;
        Node T2 = x.left;

        //向左旋转的过程
        x.left = y;
        y.right = T2;

        //更新height值
        y.height = Math.max(getHeight(y.left), getHeight(y.right)) + 1;
        x.height = Math.max(getHeight(x.left), getHeight(x.right)) + 1;

        return x;
    }
```

### LR(插入的元素在不平衡节点的左侧的右侧)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/LR2.png)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/LR3.png)<br>

### RL(插入的元素在不平衡节点的右侧的左侧)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/RL1.png)<br>
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/AVL/RL2.png)<br>

### 代码
```
        //平衡维护
        //LL
        if(balanceFactor > 1 && getBalanceFactor(node.left) >= 0)
            return rightRotate(node);

        //RR
        if(balanceFactor < -1 && getBalanceFactor(node.right) <= 0)
            return leftRotate(node);

        //LR
        if(balanceFactor > 1 && getBalanceFactor(node.left) < 0){
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }

        //RL
        if(balanceFactor < -1 && getBalanceFactor(node.right) > 0){
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }
```

## 删除元素平衡
删除元素后，会受到影响的只有删除元素对应位置的树和它的父辈节点，所以思路其实是一样
```
     private Node remove(Node node, K key){

        if( node == null )
            return null;

        Node retNode;
        if( key.compareTo(node.key) < 0 ){
            node.left = remove(node.left , key);
            retNode = node;
        }
        else if(key.compareTo(node.key) > 0 ){
            node.right = remove(node.right, key);
            retNode = node;
        }
        else{   // key.compareTo(node.key) == 0

            // 待删除节点左子树为空的情况
            if(node.left == null){
                Node rightNode = node.right;
                node.right = null;
                size --;
                retNode = rightNode;
            }
            else if(node.right == null){
                // 待删除节点右子树为空的情况
                Node leftNode = node.left;
                node.left = null;
                size --;
                retNode = leftNode;
            }
            else {
                // 待删除节点左右子树均不为空的情况
                // 找到比待删除节点大的最小节点, 即待删除节点右子树的最小节点
                // 用这个节点顶替待删除节点的位置
                Node successor = minimum(node.right);
                successor.right = remove(node.right, successor.key);
                successor.left = node.left;

                node.left = node.right = null;

                retNode = successor;
            }
        }

        if(retNode == null)
            return null;

        //更新height
        retNode.height = 1 + Math.max(getHeight(retNode.left), getHeight(retNode.right));
        //计算平衡因子
        int balanceFactor = getBalanceFactor(retNode);

        //平衡维护
        //LL
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) >= 0)
            return rightRotate(retNode);

        //RR
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) <= 0)
            return leftRotate(retNode);

        //LR
        if(balanceFactor > 1 && getBalanceFactor(retNode.left) < 0){
            retNode.left = leftRotate(retNode.left);
            return rightRotate(retNode);
        }

        //RL
        if(balanceFactor < -1 && getBalanceFactor(retNode.right) > 0){
            retNode.right = rightRotate(retNode.right);
            return leftRotate(retNode);
        }
        return retNode;
    }
```
