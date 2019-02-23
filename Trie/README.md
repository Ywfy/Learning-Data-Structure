# Trie 字典树(前缀树)
![无法加载图片](https://github.com/Ywfy/Learning-Data-Structure/blob/master/Trie/what.png)<br>
字典树是专门针对字符串设计的，查询每个条目的时间复杂度和字典中一共有多少条目无关！只和查询的字符串长度有关，若字符串长度为w，则时间复杂度为O(w)
```
import java.util.TreeMap;

public class Trie {

    private class Node{

        public boolean isword;
        public TreeMap<Character, Node> next;

        public Node(boolean isword){
            this.isword = isword;
            next = new TreeMap<>();
        }

        public Node(){
            this(false);
        }
    }

    private Node root;
    private int size;

    public Trie(){
        root = new Node();
        size = 0;
    }

    //获取Trie中储存的单词数量
    public int getSize(){
        return size;
    }

    //向Trie中添加一个新的单词word
    public void add(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i++){
            char c = word.charAt(i);
            if(cur.next.get(c) == null)
                cur.next.put(c, new Node());
            cur = cur.next.get(c);
        }
        if(cur.isword == false){
            cur.isword = true;
            size++;
        }

    }

    //查询单词word是否在Trie中
    //这个跟add的逻辑基本一致
    public boolean contains(String word){

        Node cur = root;
        for(int i = 0 ; i < word.length() ; i++) {
            char c = word.charAt(i);
            //只是在此处若是为空，则可证明不包含，直接返回
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        return cur.isword;
    }

    //查询是否在Trie中有单词以prefix为前缀
    //本函数视单词本身为自己的前缀
    public boolean isPrefix(String prefix){

        Node cur = root;
        for(int i = 0 ; i < prefix.length(); i++){
            char c = prefix.charAt(i);
            if(cur.next.get(c) == null)
                return false;
            cur = cur.next.get(c);
        }
        //只要循环能遍历完，就证明有以prefix为前缀的单词
        return true;
    }
}
```

## 实现模糊匹配(.能够表示任意字符)
d..r能够作为d[a-z][a-z]r
```
    public boolean search(String word) {
        return match(root, word, 0);
    }

    private boolean match(Node node, String word, int index){

        if(index == word.length())
            return node.isWord;

        char c = word.charAt(index);

        if(c != '.'){
            if(node.next.get(c) == null)
                return false;
            return match(node.next.get(c), word, index + 1);
        }
        else{
            for(char nextChar: node.next.keySet())
                if(match(node.next.get(nextChar), word, index + 1))
                    return true;
            return false;
        }
    }
```

