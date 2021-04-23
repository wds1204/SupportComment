package com.sun.supportcomment;

public class Solution {

    // 是否是回文字符串
    public static boolean isPalindrome(String text){

        int i=0,j = text.length()-1;
        while (i<j) {
            while ((i < j && !Character.isLetterOrDigit(text.charAt(i)))) i++;
            while (i < j && !Character.isLetterOrDigit(text.charAt(j))) j--;

            if (Character.toLowerCase(text.charAt(i)) != Character.toLowerCase(text.charAt(j))) {
                return false;
            }
            i++;
            j--;
        }
        return true;
    }


    public static void main(String []args){
        String text="A man, a plan, a canal: Panama";
        System.out.println(isPalindrome(text));
    }
}
