package com.example.demo;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

@DisplayName("Testing Calculator op")
@TestInstance(Lifecycle.PER_CLASS)
public class CalculatorTest {
    static Calculator c1;
    boolean flag;
    @Test
    @Disabled
    @Tag("other")
    void test(){
        assertTrue(true);
    }
    @BeforeAll //@BeforeClass
    public static void createObject()
    {
        c1=new Calculator();
        System.out.println("******Started*****");
    }
    @AfterAll // @AfterClass
    public static void removeObject()
    {
        c1=null;
        System.out.println(" ********Finished******");
    }
    
    @Test 
    @Tag("math")
    public void testAdd()
    {
        Calculator c1=new Calculator();
        int actual =c1.add(10,20);
        int expected =30;
        
        assertEquals(expected,actual);
    }
    @Test
    @Tag("math")
    public void testSub(){
        System.out.println("testSub()");
        assertEquals(-10,c1.sub(10,20));
        assertEquals(20,c1.sub(40,20));
        assertEquals(30,c1.sub(50,20));
        assertEquals(10,c1.sub(30,20));

    }
    
    @Test
    @DisplayName("Testing Modulus op")
    @Tag("math")
    public void testMod()
    {
       System.out.println("testMod()");
       assertEquals(1,c1.mod(3,2));
    }

    @Test
    @DisplayName("Multiply Test Cases")
    void testMul() {

       assertAll("Multiplication scenarios",

        // ✅ Normal positive numbers
        () -> assertEquals(4, c1.mul(2,2)),
        () -> assertEquals(10, c1.mul(5,2)),

        // ✅ Zero cases
        () -> assertEquals(0, c1.mul(0,5)),
        () -> assertEquals(0, c1.mul(10,0)),

        // ✅ Negative numbers
        () -> assertEquals(-10, c1.mul(-5,2)),
        () -> assertEquals(10, c1.mul(-5,-2)),

        // ✅ Large numbers
        () -> assertEquals(10000, c1.mul(100,100))

      );
    }

    @Test // @Test(expected=ArithmeticException.class)
    @Tag("math")
     public void testDiv() {
        // assertAll();
        assertEquals((int) 5, c1.div(10, 2));
 
        assertThrows(ArithmeticException.class, () -> c1.div(30, 0));
        assertEquals((int) 5, c1.div(10, 2));
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    @Tag("os")
    public void testDll()
    {
        System.out.println("testDll()");
    }

    @Test
    @EnabledOnOs(value={OS.LINUX,OS.MAC})
    @Tag("os")
    public void testsh()
    {
        assumeTrue(flag);
        System.out.println("testsh()");
    }

    @Test
    // @EnabledForJre(JRE.JAVA_10)
    @EnabledForJreRange(min = JRE.JAVA_8, max = JRE.JAVA_17)
    @Tag("other")
    public void testLambda() {
       
    }

    
}

