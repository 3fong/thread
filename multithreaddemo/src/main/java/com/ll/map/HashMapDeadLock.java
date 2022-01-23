package com.ll.map;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

public class HashMapDeadLock {

    public static void main(String[] args) {
        HashMap<String,String> map = new HashMap<>();
        Hashtable hashtable = new Hashtable();
    }

         public <K,V> void transfer(Map.Entry<K,V>[] newTable, boolean rehash) {
        int newCapacity = newTable.length;
        for (Map.Entry<K,V> e : table) {// 1,2,3
            while(null != e) {
                Map.Entry<K,V> next = e.next;//2 3
                if (rehash) {
                    e.hash = null == e.key ? 0 : hash(e.key);
                }
                int i = indexFor(e.hash, newCapacity);//7
                e.next = newTable[i];//null null
                newTable[i] = e;// 1 2
                e = next;// 2 3
            }
        }
    }
    }

}
