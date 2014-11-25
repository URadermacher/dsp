package eu.vdrm.util.run;

import java.util.Set;
import org.junit.Assert;
import org.junit.Test;

public class ArgsTest {
    
    @Test
    public void testSingleParm(){
        String[] in = new String[1];
        in[0] = "singleVal";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("singleVal",result.getParms().get(0));
        Assert.assertNull(result.getKetValuePairs());
    }

    @Test
    public void testSeveralParms(){
        String[] in = new String[3];
        in[0] = "Val_0";
        in[1] = "Val_1";
        in[2] = "Val_2";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("Val_0",result.getParms().get(0));
        Assert.assertEquals("Val_1",result.getParms().get(1));
        Assert.assertEquals("Val_2",result.getParms().get(2));
        Assert.assertNull(result.getKetValuePairs());
    }

    @Test
    public void testNameVal(){
        String[] in = new String[2];
        in[0] = "-input";
        in[1] = "myfile.txt";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("myfile.txt",result.getKetValuePairs().get("input"));
        Assert.assertNull(result.getParms());
    }

    @Test
    public void test2NameVal(){
        String[] in = new String[4];
        in[0] = "-input";
        in[1] = "myfile.txt";
        in[2] = "-input";
        in[3] = "mynexfile.txt";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("mynexfile.txt",result.getKetValuePairs().get("input"));
        Assert.assertNull(result.getParms());
    }


    @Test
    public void test2NameValEmpty(){
        String[] in = new String[3];
        in[0] = "-input";
        in[1] = "myfile.txt";
        in[2] = "-input";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("myfile.txt",result.getKetValuePairs().get("input"));
        Assert.assertNull(result.getParms());
    }

    @Test
    public void testKeysOnly(){
        String[] in = new String[3];
        in[0] = "-a";
        in[1] = "-b";
        in[2] = "-c";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertNull(result.getKetValuePairs().get("a"));
        Assert.assertNull(result.getKetValuePairs().get("b"));
        Assert.assertNull(result.getKetValuePairs().get("c"));
        Assert.assertEquals(3, result.getKetValuePairs().keySet().size());
        Set<String> keys = result.getKetValuePairs().keySet();
        Assert.assertTrue( keys.contains("a"));
        Assert.assertTrue( keys.contains("b"));
        Assert.assertTrue( keys.contains("c"));
        Assert.assertNull(result.getParms());
    }

    @Test
    public void testMixed(){
        String[] in = new String[3];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("first",result.getKetValuePairs().get("a"));
        Assert.assertEquals(1, result.getKetValuePairs().keySet().size());
        Set<String> keys = result.getKetValuePairs().keySet();
        Assert.assertTrue( keys.contains("a"));
        Assert.assertEquals("loose",result.getParms().get(0));

    }

    @Test
    public void testMixedDouble(){
        String[] in = new String[5];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        in[3] = "-b";
        in[4] = "second";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        Assert.assertEquals("first",result.getKetValuePairs().get("a"));
        Assert.assertEquals("second",result.getKetValuePairs().get("b"));
        Assert.assertEquals(2, result.getKetValuePairs().keySet().size());
        Set<String> keys = result.getKetValuePairs().keySet();
        Assert.assertTrue( keys.contains("a"));
        Assert.assertTrue( keys.contains("b"));
        Assert.assertEquals("loose",result.getParms().get(0));

    }
    
    @Test
    public void testMixedSingle(){
        String[] in = new String[5];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        in[3] = "-b";
        in[4] = "second";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, false);
        Assert.assertNull(result.getKetValuePairs().get("a"));
        Assert.assertNull(result.getKetValuePairs().get("b"));
        Assert.assertEquals(2, result.getKetValuePairs().keySet().size());
        Set<String> keys = result.getKetValuePairs().keySet();
        Assert.assertTrue( keys.contains("a"));
        Assert.assertTrue( keys.contains("b"));
        Assert.assertEquals("first",result.getParms().get(0));
        Assert.assertEquals("loose",result.getParms().get(1));
        Assert.assertEquals("second",result.getParms().get(2));

    }
}
