package eu.vdmr.util.run;

import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class ArgsTest {
    
    @Test
    void testSingleParm(){
        String[] in = new String[1];
        in[0] = "singleVal";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getParms().get(0)).isEqualTo("singleVal");
        assertThat(result.getKetValuePairs()).isNull();
    }

    @Test
    void testSeveralParms(){
        String[] in = new String[3];
        in[0] = "Val_0";
        in[1] = "Val_1";
        in[2] = "Val_2";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getParms().get(0)).isEqualTo("Val_0");
        assertThat(result.getParms().get(1)).isEqualTo("Val_1");
        assertThat(result.getParms().get(2)).isEqualTo("Val_2");
        assertThat(result.getKetValuePairs()).isNull();
    }

    @Test
    void testNameVal(){
        String[] in = new String[2];
        in[0] = "-input";
        in[1] = "myfile.txt";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("input")).isEqualTo("myfile.txt");
        assertThat(result.getParms()).isNull();
    }

    @Test
    void test2NameVal(){
        String[] in = new String[4];
        in[0] = "-input";
        in[1] = "myfile.txt";
        in[2] = "-input";
        in[3] = "mynextfile.txt";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("input")).isEqualTo("mynextfile.txt");
        assertThat(result.getParms()).isNull();
    }


    @Test
    void test2NameValEmpty(){
        String[] in = new String[3];
        in[0] = "-input";
        in[1] = "myfile.txt";
        in[2] = "-input";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("input")).isEqualTo("myfile.txt");
        assertThat(result.getParms()).isNull();
    }

    @Test
    void testKeysOnly(){
        String[] in = new String[3];
        in[0] = "-a";
        in[1] = "-b";
        in[2] = "-c";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("a")).isNull();
        assertThat(result.getKetValuePairs().get("b")).isNull();
        assertThat(result.getKetValuePairs().get("c")).isNull();
        assertThat(result.getKetValuePairs().keySet()).hasSize(3);
        Set<String> keys = result.getKetValuePairs().keySet();
        assertThat(keys.contains("a")).isTrue();
        assertThat(keys.contains("b")).isTrue();
        assertThat(keys.contains("c")).isTrue();
        assertThat(result.getParms()).isNull();
    }

    @Test
    void testMixed(){
        String[] in = new String[3];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("a")).isEqualTo("first");
        assertThat(result.getKetValuePairs().keySet()).hasSize(1);
        Set<String> keys = result.getKetValuePairs().keySet();
        assertThat(keys.contains("a")).isTrue();
        assertThat(result.getParms().get(0)).isEqualTo("loose");
    }

    @Test
    void testMixedDouble(){
        String[] in = new String[5];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        in[3] = "-b";
        in[4] = "second";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, true);
        assertThat(result.getKetValuePairs().get("a")).isEqualTo("first");
        assertThat(result.getKetValuePairs().get("b")).isEqualTo("second");
        assertThat(result.getKetValuePairs().keySet()).hasSize(2);
        Set<String> keys = result.getKetValuePairs().keySet();
        assertThat(keys.contains("a")).isTrue();
        assertThat(keys.contains("b")).isTrue();
        assertThat(result.getParms().get(0)).isEqualTo("loose");

    }
    
    @Test
    void testMixedSingle(){
        String[] in = new String[5];
        in[0] = "-a";
        in[1] = "first";
        in[2] = "loose";
        in[3] = "-b";
        in[4] = "second";
        Args args = new Args();
        Args.ArgVal result = args.getArgs(in, false);
        assertThat(result.getKetValuePairs().get("a")).isNull();
        assertThat(result.getKetValuePairs().get("b")).isNull();
        assertThat(result.getKetValuePairs().keySet()).hasSize(2);
        Set<String> keys = result.getKetValuePairs().keySet();
        assertThat(keys.contains("a")).isTrue();
        assertThat(keys.contains("b")).isTrue();
        assertThat(result.getParms().get(0)).isEqualTo("first");
        assertThat(result.getParms().get(1)).isEqualTo("loose");
        assertThat(result.getParms().get(2)).isEqualTo("second");

    }
}
