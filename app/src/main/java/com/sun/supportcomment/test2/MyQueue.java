package com.sun.supportcomment.test2;

import java.util.Stack;
/**
 * 题目2
 * 使用栈实现队列的下列操作：
 * push(x) -- 将一个元素放入队列的尾部。
 * pop() -- 从队列首部移除元素。
 * peek() -- 返回队列首部的元素。
 * empty() -- 返回队列是否为空。
 * 示例:
 * MyQueue queue = new MyQueue();
 * queue.push(1);
 * queue.push(2);
 * queue.peek();  // 返回 1
 * queue.pop();   // 返回 1
 * queue.empty(); // 返回 false
 * 说明:
 * 你只能使用标准的栈操作 -- 也就是只有 push to top, peek/pop from top, size, 和 is empty 操作是合法的。
 * 你所使用的语言也许不支持栈。你可以使用 list 或者 deque（双端队列）来模拟一个栈，只要是标准的栈操作即可。
 * 假设所有操作都是有效的 （例如，一个空的队列不会调用 pop 或者 peek 操作）。
 */
public class MyQueue {

    /**
     * 思路：2个栈是必须的，1个只用于输入，一个只用于输出，只要输出栈为空，就先将输入栈所有元素放入输出栈。
     *
     * 1、使用两个栈，一个栈（stackPush）用于元素进栈，一个栈（stackPop）用于元素出栈；
     *
     * 2、pop() 或者 peek() 的时候：
     *
     * （1）如果 stackPop 里面有元素，直接从 stackPop 里弹出或者 peek 元素；
     *
     * （2）如果 stackPop 里面没有元素，一次性将 stackPush 里面的所有元素倒入 stackPop。
     *
     * 为此，可以写一个 shift 辅助方法，一次性将 stackPush 里的元素倒入 stackPop。
     * @param args
     */
    public static void main(String[] args) {
        MyQueue queue = new MyQueue();
        queue.push(1);
        queue.push(2);
        System.out.println(" queue.peek()---"+ queue.peek());
        System.out.println(" queue.pop()---"+ queue.pop());
        System.out.println("queue.empty()---"+queue.empty());
    }

    private Stack<Integer> input = new Stack<>();
    private Stack<Integer> output = new Stack<>();

    public MyQueue() {
    }

    public void push(int x) {
        input.push(x);
    }

    public int pop() {
        if(output.empty()){
            while(!input.empty()){
                output.push(input.pop());
            }
        }
        return output.pop();
    }


    public int peek() {
        if(output.empty()){
            while(!input.empty()){
                output.push(input.pop());
            }
        }
        return output.peek();
    }

    public boolean empty() {
        return input.empty()&&output.empty();
    }


}
