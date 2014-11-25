package eu.vdrm.miscellaneous;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import org.junit.Assert;
import org.junit.Test;
import eu.vdrm.miscellaneous.data.BreakPoint;

public class BreakPointsTest {
    
    private static final String path = "D:\\_ulrich\\workspaces\\dspraga\\restjes\\";
    
    @Test  // simple correct file
    public void testReadFile_1(){
        BreakPoints bps = new BreakPoints();
        bps.loadFromPhysicalFile(path + "src\\test\\resources\\breakpointfiles\\test_1.bkps");
        Assert.assertEquals(2L, bps.getCount());
    }    
    
    @Test // empty lines at end
    public void testReadFile_2(){
        String b = System.getProperty("testpath");
        BreakPoints bps = new BreakPoints();
        bps.loadFromPhysicalFile(b + "\\src\\test\\resources\\breakpointfiles\\test_2.bkps");
        Assert.assertEquals(2L, bps.getCount());
    }    
    
    @Test // not existing
    public void testNoneFile(){
        BreakPoints bps = new BreakPoints();
        boolean res = bps.loadFromPhysicalFile(path + "src\\test\\resources\\breakpointfiles\\xxx.bkps");
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals(path + "src\\test\\resources\\breakpointfiles\\xxx.bkps (Het systeem kan het opgegeven bestand niet vinden)", err.get(0));
    }    

    
    @Test // write and reread
    public void testWriteReadFile(){
        String b = System.getProperty("testpath");
        String filename = b+"\\src\\test\\resources\\breakpointfiles\\writeread.bkps";
        File file = new File(filename);
        if (file.exists()){
            file.delete();
        }
        BreakPoints bps_out = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        bps_out.set(testMap);
        bps_out.writeToFile(filename);
        BreakPoints bps_in = new BreakPoints();
        
        bps_in.loadFromPhysicalFile(filename);
        checkEqual(testMap, bps_in.getPoints());
    }    
    
    @Test (expected = IllegalArgumentException.class)
    public void testNoneBKSFile(){
        BreakPoints bps = new BreakPoints();
        boolean res = bps.loadFromPhysicalFile(path + "pom.xml");
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals("No breakpoints read from file", err.get(0));
    }    

    @Test 
    public void testIllegalFile(){
        BreakPoints bps = new BreakPoints();
        boolean res = bps.loadFromPhysicalFile(path + "src\\test\\resources\\breakpointfiles\\test_3.bkps");
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals("breackpoint goes back in time in line 6; skipped", err.get(0));
    }    

    @Test
    public void testSetNull(){
        BreakPoints bps = new BreakPoints();
        boolean res = bps.set(null);
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals("no data given", err.get(0));
    }

    @Test
    public void testSetEmpty(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = new HashMap<Double,Double>();
        boolean res = bps.set(testMap);
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals("no data given", err.get(0));
    }

    @Test
    public void testSetOK(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = new HashMap<Double,Double>();
        testMap.put(1.5D, 2.5D);
        boolean res = bps.set(testMap);
        Assert.assertTrue(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals(0, err.size());
    }

    @Test
    public void testSetDouble(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = new HashMap<Double,Double>();
        testMap.put(1.5D, 2.5D);
        boolean res = bps.set(testMap);
        Assert.assertTrue(res);
        res = bps.set(testMap);
        Assert.assertFalse(res);
        List<String> err = bps.getErrors();
        Assert.assertEquals("data already exist", err.get(0));
    }

    @Test
    public void testResetDouble(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = new HashMap<Double,Double>();
        testMap.put(1.5D, 2.5D);
        boolean res = bps.set(testMap);
        Assert.assertTrue(res);
        res = bps.reset(testMap);
        Assert.assertTrue(res);
    }
    
    @Test
    public void testNormalizeSmaller(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        boolean res = bps.set(testMap);
        Assert.assertTrue(res);
        bps.normalize(100D);
        List<BreakPoint> lst = bps.getPoints();
        BreakPoint bp = lst.get(0);
        Assert.assertEquals(bp.getTime(), 1D, 0D);
        Assert.assertEquals(bp.getValue(), 50D, 0D);
        bp = lst.get(1);
        Assert.assertEquals(bp.getTime(), 2D, 0D);
        Assert.assertEquals(bp.getValue(), 75D, 0D);
        bp = lst.get(2);
        Assert.assertEquals(bp.getTime(), 3D, 0D);
        Assert.assertEquals(bp.getValue(), 100D, 0D);
        bp = lst.get(3);
        Assert.assertEquals(bp.getTime(), 4D, 0D);
        Assert.assertEquals(bp.getValue(), 30D, 0D);
        bp = lst.get(4);
        Assert.assertEquals(bp.getTime(), 5D, 0D);
        Assert.assertEquals(bp.getValue(), 8D, 0D);
        bp = lst.get(5);
        Assert.assertEquals(bp.getTime(), 6D, 0D);
        Assert.assertEquals(bp.getValue(), 91D, 0D);
        bp = lst.get(6);
        Assert.assertEquals(bp.getTime(), 7D, 0D);
        Assert.assertEquals(bp.getValue(), 1D, 0D);
        bp = lst.get(7);
        Assert.assertEquals(bp.getTime(), 8D, 0D);
        Assert.assertEquals(bp.getValue(), 65D, 0D);
        
    }
    
    
    @Test
    public void testNormalizeBigger(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        boolean res = bps.set(testMap);
        Assert.assertTrue(res);
        bps.normalize(4000D);
        List<BreakPoint> lst = bps.getPoints();
        BreakPoint bp = lst.get(0);
        Assert.assertEquals(bp.getTime(), 1D, 0D);
        Assert.assertEquals(bp.getValue(), 2000D, 0D);
        bp = lst.get(1);
        Assert.assertEquals(bp.getTime(), 2D, 0D);
        Assert.assertEquals(bp.getValue(), 3000D, 0D);
        bp = lst.get(2);
        Assert.assertEquals(bp.getTime(), 3D, 0D);
        Assert.assertEquals(bp.getValue(), 4000D, 0D);
        bp = lst.get(3);
        Assert.assertEquals(bp.getTime(), 4D, 0D);
        Assert.assertEquals(bp.getValue(), 1200D, 0D);
        bp = lst.get(4);
        Assert.assertEquals(bp.getTime(), 5D, 0D);
        Assert.assertEquals(bp.getValue(), 320D, 0D);
        bp = lst.get(5);
        Assert.assertEquals(bp.getTime(), 6D, 0D);
        Assert.assertEquals(bp.getValue(), 3640D, 0D);
        bp = lst.get(6);
        Assert.assertEquals(bp.getTime(), 7D, 0D);
        Assert.assertEquals(bp.getValue(), 40D, 0D);
        bp = lst.get(7);
        Assert.assertEquals(bp.getTime(), 8D, 0D);
        Assert.assertEquals(bp.getValue(), 2600D, 0D);
    }

    @Test 
    public void testShift(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        bps.set(testMap);
        bps.shift(50D);
        List<BreakPoint> result = bps.getPoints();
        Assert.assertEquals(testMap.size(), result.size());
        int i = 0;
        for (BreakPoint point : result){
            System.out.println("line " + ++i );
            Assert.assertNotNull(testMap.get(point.getTime()));
            Assert.assertEquals(point.getValue(), testMap.get(point.getTime())+50D,0D);
        }
    }
        

    @Test 
    public void testFactor(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        bps.set(testMap);
        bps.scale(2D);
        List<BreakPoint> result = bps.getPoints();
        Assert.assertEquals(testMap.size(), result.size());
        int i = 0;
        for (BreakPoint point : result){
            System.out.println("line " + ++i );
            Assert.assertNotNull(testMap.get(point.getTime()));
            Assert.assertEquals(point.getValue(), testMap.get(point.getTime())*2D,0D);
        }
    }

    @Test (expected = UnsupportedOperationException.class)
    public void testAdd(){
        BreakPoints bps = new BreakPoints();
        Map<Double,Double> testMap = getMap1();
        bps.add(testMap, true);
        Assert.fail("add should not be implemented (yet)");
    }
    
    private Map<Double,Double> getMap1(){
        Map<Double,Double> testMap = new TreeMap<Double,Double>();
        testMap.put(1D, 200D);
        testMap.put(2D, 300D);
        testMap.put(3D, 400D);
        testMap.put(4D, 120D);
        testMap.put(5D, 32D);
        testMap.put(6D, 364D);
        testMap.put(7D, 4D);
        testMap.put(8D, 260D);
        return testMap;
    }

    private void checkEqual(Map<Double,Double> testMap, List<BreakPoint> inList){
        Assert.assertEquals(testMap.size(), inList.size());
        for (BreakPoint point : inList){
            Assert.assertNotNull(testMap.get(point.getTime()));
            Assert.assertEquals(point.getValue(), testMap.get(point.getTime()),0D);
        }
    }

}
