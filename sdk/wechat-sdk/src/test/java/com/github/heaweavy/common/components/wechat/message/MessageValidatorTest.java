package com.github.heaweavy.common.components.wechat.message;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;


/**
 * @author caimb
 * @date Created at 2018/3/16
 * @modefier
 */
public class MessageValidatorTest {
    
    public MessageValidatorTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of calculateSignature method, of class MessageValidator.
     */
    @Test
    public void testCalculateSignature() {
        System.out.println("calculateSignature");
        MessageValidator instance = new MessageValidator("xmosh2014", "1419406002", "1190832825", 
            "911d33e34bf167ed4fb0331aa23d275726b9279e");
        String expResult = "";
        String result = instance.calculateSignature();
//        assertEquals(expResult, result);
        System.out.println(result);
    }

    /**
     * Test of validate method, of class MessageValidator.
     */
    @Test
    public void testValidate() {
        System.out.println("validate");
        MessageValidator instance =  new MessageValidator("xmosh2014", "1419406002", "1190832825", 
            "911d33e34bf167ed4fb0331aa23d275726b9279e");
        boolean expResult = true;
        boolean result = instance.validate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
