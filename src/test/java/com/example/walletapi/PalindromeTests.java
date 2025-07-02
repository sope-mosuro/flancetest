package com.example.walletapi;
import com.example.walletapi.util.PalindromeTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PalindromeTests {
    @Test
    void testIsPalindrome() {
        assertTrue(PalindromeTest.isPalindrome(121));
        assertFalse(PalindromeTest.isPalindrome(-121));
        assertFalse(PalindromeTest.isPalindrome(10));
        assertTrue(PalindromeTest.isPalindrome(0));
        assertTrue(PalindromeTest.isPalindrome(1));
        assertTrue(PalindromeTest.isPalindrome(1221));
    }



}
