package julie.study;

import junit.framework.Assert;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

public class MoneyTest {

    Money m1;
    Money m2;
    Money expected;
    Money result;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }


    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testAdd() {
        m1 = new Money(12, "USD");
        m2 = new Money(14, "USD");
        expected = new Money(26, "USD");
        result = m1.add(m2);
        Assert.assertEquals("Error", expected.getValue(), m1.getValue());
        Assert.assertNotNull(new Money(10, "USD"));
    }
}