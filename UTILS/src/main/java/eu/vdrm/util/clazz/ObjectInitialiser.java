package eu.vdrm.util.clazz;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

import org.apache.log4j.Logger;


/**
 * some useful(?) stuff with classes/objects
 * @author ura03640
 *
 */
public class ObjectInitialiser {
	private static Logger LOG = Logger.getLogger(ObjectInitialiser.class);

	/**
	 * creates an Object from type classname. Assuming a zero parameter constructor
	 * @param classname
	 * @return a new Object (or null in case of any error)
	 */
	public static Object getObject(String classname){
		LOG.debug("Trying to create: " + classname);
        Class<?> clasz = null;
        Object rdspc = null;
        Constructor<?> nullArgCtor = null;
		try {
			clasz = (Class<?>) Class.forName(classname);
		} catch (ClassNotFoundException cnf){
			LOG.error("ClassNotFoundException " + classname ,cnf);
		}
		if (clasz != null) {
    		Constructor<?>[] ctors = (Constructor<?>[]) clasz.getConstructors();
			int idx = 0;
			for (Constructor<?> ctor :ctors) {
				idx ++;
				Type[] types = ctor.getGenericParameterTypes();
				if (types.length == 0){
					LOG.debug("no argument ctor found");
					nullArgCtor = ctor;
				} else {
				    if (LOG.isDebugEnabled()){
    					for (Type type : types){
    						LOG.debug("ctor " + idx + "has type " + type.getClass().getName());
    					}
				    }
				}
			}
		}
		if (nullArgCtor != null){
			try {
				rdspc = nullArgCtor.newInstance(new Object[0]);
			} catch (IllegalArgumentException e) {
				LOG.error("IllegalArgumentException in ctor " + classname ,e);
			} catch (InstantiationException e) {
				LOG.error("InstantiationException in ctor " + classname ,e);
			} catch (IllegalAccessException e) {
				LOG.error("IllegalAccessException in ctor " + classname ,e);
			} catch (InvocationTargetException e) {
				LOG.error("InvocationTargetException in ctor " + classname ,e);
			}
		}
		return rdspc;
	}
}
