package netty;

import java.util.HashMap;
import java.util.Map;

/**
 * @author jiangwenjie
 * @date 2021/6/8
 * <p>
 * 设计和构建一个“最近最久未使用”缓存（LRU），该缓存会删除最近最久未使用的项目。
 * 缓存应该从键映射到值(允许你插入和检索特定键对应的值)，并在初始化时指定最大容量。
 * 当缓存被填满时，它应该删除最近最久未使用的项目。
 * <p>
 * 它应该支持以下操作： 获取数据 get 和 写入数据 put 。
 * <p>
 * 获取数据 get(key) - 如果密钥 (key) 存在于缓存中，则获取密钥的值（总是正数），否则返回 -1。
 * 写入数据 put(key, value) - 如果密钥不存在，则写入其数据值。当缓存容量达到上限时，它应该在写入新数据之前删除最近最久未使用的数据值，从而为新的数据值留出空间。
 */
public class LRUCache {
    private Integer cap;
    private volatile Integer size;
    private Map<Object, Node> cache = new HashMap<>();

    private Node head;
    private Node tail;

    public LRUCache(Integer cap) {
        this.cap = cap;
        head = new Node(0, 0);
        tail = new Node(1, 1);
        head.next = tail;
        tail.pre = head;
    }

    public Object get(Object key) {
        // 1.参数校验

        // 2.缓存查找
        Node node = cache.get(key);
        if (node != null) {
            moveToHead(node);
            return node.value;
        }

        // 找不到
        return -1;
    }

    public void put(Object key, Object value) {
        // 1.参数校验

        // 2.从缓存获取Node
        Node node = cache.get(key);

        // 2.1 在缓存，更新值
        if (null != node) {
            node.value = value;
        } else {
            // 2.2 不在缓存，直接插入到表头
            node = new Node(key, value);
            cache.put(key, node);
            size++;
        }

        // 3. 移动到表头
        moveToHead(node);
        // 4. size达到阈值,删除表尾
        if (size >= cap + 1) {
            cache.remove(tail.pre.key);
            Node pre = tail.pre.pre;
            pre.next = tail;
            tail.pre = pre;
        }
    }

    private void moveToHead(Node node) {
        // 先断掉
        Node next = node.next;
        Node pre = node.pre;
        if (next != null)
            next.pre = pre;
        if (pre != null)
            pre.next = next;

        // 在插入到表头
        Node headNext = head.next;
        head.next = node;
        node.pre = head;
        headNext.pre = node;
        node.next = headNext;
    }

    class Node {
        private Object key;
        private Object value;
        Node pre;
        Node next;

        public Node(Object key, Object value) {
            this.key = key;
            this.value = value;
        }
    }
}
