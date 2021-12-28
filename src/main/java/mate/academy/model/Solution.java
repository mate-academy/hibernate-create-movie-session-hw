package mate.academy.model;


import mate.academy.lib.Inject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

//Definition for singly-linked list.
  public class ListNode {
     int val;
    ListNode next;
     ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

class Solution {
    public int getDecimalValue(ListNode head) {
        List<String> listNodeList = new ArrayList<>();
        Stream.of(head).map(y -> y.next.).forEach(x -> listNodeList.add(String.valueOf((x.val))));
        String y = Integer.toBinaryString(Integer.parseInt(listNodeList.toString()));
        return Integer.parseInt(y);
    }
}