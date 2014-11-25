package eu.vdrm.util.run;

import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class Args {
    
    public class ArgVal {
        private Map<String,String> ketValuePairs;
        private Vector<String> parms;
    
        public Map<String, String> getKetValuePairs() {
            return ketValuePairs;
        }
        public void setKetValuePairs(Map<String, String> ketValuePairs) {
            this.ketValuePairs = ketValuePairs;
        }
        public Vector<String> getParms() {
            return parms;
        }
        public void setParms(Vector<String> parms) {
            this.parms = parms;
        }
    }
    
    
    /**
     * 
     * @param args the command line arguments
     * @return an ArgVal object:
     * A) if parameter isKeyValue == true
     *      a) map of args in format -<key>  <value>
     *      b) a vector of other parameters
     *      will never return null, but the Map or Vector may be null
     * 
     *      if a -<key> is not unique, the last occurrence of the value will be put into the map
     *          -keya  aaa -keya bbb : will result in keya=bbb
     *          -keya  aaa -keya     : will result on keya=aaa
     *          
     *          
     *  B) if parameter isKeyValue == false
     *      a) a map with key-values only (for all parms with '-' )
     *      b) a vector with other parms
     */
    public ArgVal getArgs(String[] args, boolean isKeyValue){
        boolean valuecomes = false;
        String currKey = null;
        ArgVal ret = new ArgVal();
        for (String arg : args) {
            if (arg.startsWith("-")){
                if (isKeyValue){
                    valuecomes = true;
                }
                if (ret.getKetValuePairs() == null){
                    ret.setKetValuePairs(new HashMap<String,String>());
                }
                currKey = arg.substring(1);
                if (ret.getKetValuePairs().get(currKey) == null ){
                    ret.getKetValuePairs().put(currKey, null);
                } // else keep value..
            } else {
                if (valuecomes){
                    ret.getKetValuePairs().put(currKey, arg);
                    valuecomes = false; // got it
                    
                } else { // not a key-value pair
                    if (ret.getParms() == null){
                        ret.setParms(new Vector<String>());
                    }
                    ret.getParms().add(arg);
                }
            }
        }
        return ret;
    }
}
