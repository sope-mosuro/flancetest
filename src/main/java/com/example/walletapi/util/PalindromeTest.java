package com.example.walletapi.util;

public class PalindromeTest {
    /**
     * Checks if an integer is a palindrome using string reversal.
     *
     * @param x the integer to check
     * @return true if x is a palindrome, false otherwise
     */
    public static boolean isPalindrome(int x) {
        String str = String.valueOf(x);
        String reversed = new StringBuilder(str).reverse().toString();
        return str.equals(reversed);
    }
}
