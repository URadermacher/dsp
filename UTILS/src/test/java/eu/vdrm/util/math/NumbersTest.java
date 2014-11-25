package eu.vdrm.util.math;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import org.junit.Assert;
import org.junit.Test;

public class NumbersTest {
    
    @Test (expected = IllegalArgumentException.class)  
    public void testMaxNull(){
       Numbers.getMax(null);
    }
    
    @Test (expected = IllegalArgumentException.class)  
    public void testMaxEmpty(){
        List<Integer> l = new ArrayList<Integer>(); 
        Numbers.getMax(l);
    }

    @Test
    public void testMaxInteger(){
        List<Integer> l = new ArrayList<Integer>();
        l.add(Integer.valueOf(1));
        l.add(Integer.valueOf(5));
        l.add(Integer.valueOf(3));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof Integer);
        Assert.assertEquals(5,((Integer)o).intValue());
    }

    @Test
    public void testMaxByte(){
        List<Byte> l = new ArrayList<Byte>();
        l.add(Byte.valueOf("1"));
        l.add(Byte.valueOf("5"));
        l.add(Byte.valueOf("3"));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof Byte);
        Assert.assertEquals(5,((Byte)o).intValue());
    }

    @Test
    public void testMaxDouble(){
        List<Double> l = new ArrayList<Double>();
        l.add(Double.valueOf("1.3"));
        l.add(Double.valueOf("5.88989"));
        l.add(Double.valueOf("3.7"));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof Double);
        Assert.assertEquals(5.88989,((Double)o).doubleValue(),0.0);
    }

    @Test
    public void testMaxBigDecimal(){
        List<BigDecimal> l = new ArrayList<BigDecimal>();
        BigDecimal res = BigDecimal.valueOf(5.88989);
        l.add(BigDecimal.valueOf(1.3));
        l.add(res);
        l.add(BigDecimal.valueOf(3.7));
        Object o = Numbers.getMax(l);
        Assert.assertTrue(o instanceof BigDecimal);
        Assert.assertTrue(res.equals(o));
    }
}
