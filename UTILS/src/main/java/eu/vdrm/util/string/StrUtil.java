package eu.vdrm.util.string;

public class StrUtil {
	
	public static final String SEP = ";";

	public static String IntArr2String(int[] in){
		return IntArr2String(in, SEP);
	}
	
	public static String IntArr2String(int[] in, String sep){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < in.length; i++){
			sb.append(in[i]);
			if (i > 0){
				sb.append(sep);
			}
		}
		return sb.toString();
	}

	
	
	
	public static int[] String2IntArr(String in){
		return  String2IntArr(in, SEP);
	}

	public static int[] String2IntArr(String in, String sep){
		if (in == null) return null;
		if (in.length() == 0) return new int[0];
		int[] res = new int[count(in,sep)+1];   //we have one more entries than separators
		int nxt = in.indexOf(sep);
		int idx = 0;
		try {
			while (nxt > -1){
				res[idx++] = Integer.parseInt(in.substring(0,nxt));
				in = in.substring(nxt+1);
				nxt = in.indexOf(sep);
			}
			// last piece
			res[idx] = Integer.parseInt(in);
		}catch (NumberFormatException nfe){
			System.err.println("illegal string for number (idx = " + idx + "): "+ in);
			return null;
		}
		return res;
	}
	
	
	public static int count(String haystack, String needle){
		int cnt = 0;
		int nxt = haystack.indexOf(needle);
		while (nxt > -1){
			cnt++;
			nxt = haystack.indexOf(needle, nxt+1);
		}
		return cnt;
	}
	
	public static void main(String[] args) {
		int[] r = String2IntArr("99;837364;88");
		for (int i = 0; i < r.length; i++){
			System.out.println("" + r[i]);
		}

	}
}
