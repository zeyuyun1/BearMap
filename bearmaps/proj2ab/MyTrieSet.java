package bearmaps.proj2ab;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MyTrieSet {

    private Node root;

    private static class Node{
        private boolean isKey;
        private HashMap<Character,Node> next;

        private Node(boolean isKey) {
            this.isKey =isKey;
            next = new HashMap<>();
        }
    }
    public MyTrieSet() {
        root = new Node(false);
    }
    /** Clears all items out of Trie */
    public void clear(){
        root = new Node(false);
    }

    /** Returns true if the Trie contains KEY, false otherwise */
    public boolean contains(String key){
        if (key == null) {
            throw new IllegalArgumentException();
        }
        Node curr = root;
        for (int i =0; i<key.length(); i++) {
            Character a = key.charAt(i);
            if (curr.next == null || !curr.next.containsKey(a)) {
                return false;
            }
            curr = curr.next.get(a);
        }
        return curr.isKey;
    }

    /** Inserts string KEY into Trie */
    public void add(String key){
        if (key == null){
            return;
        }
        Node curr = root;
        for (int i =0; i<key.length(); i++) {
            Character a = key.charAt(i);
            if (!curr.next.containsKey(a)) {
                curr.next.put(a,new Node(false));
            }
            curr = curr.next.get(a);
        }
        curr.isKey = true;


    }

    /** Returns a list of all words that start with PREFIX */

    public List<String> keysWithPrefix(String prefix){
        ArrayList<String> x = new ArrayList<>();
        colHelp("",x,root,prefix,0,false);
        System.out.println(x);
        return x;
    }


    private void colHelp(String s, List<String> x, Node n, String prefix,int index,boolean flag) {
        if(n == null) {
            return;
        }
        if (n.isKey && index >=prefix.length()) {
            x.add(s);
        }
        for (Character c: n.next.keySet()){
            if (index<prefix.length() && prefix.charAt(index)!=c){
                continue;
            }
            colHelp(s+c,x,n.next.get(c),prefix,index+1,true);
        }

    }


    public String longestPrefixOf(String key){
        throw new UnsupportedOperationException();
    }
}
