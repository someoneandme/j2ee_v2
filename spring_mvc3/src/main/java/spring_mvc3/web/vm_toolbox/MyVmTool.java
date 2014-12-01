package spring_mvc3.web.vm_toolbox;

public class MyVmTool {

	public static Object ifnull(Object obj, Object defaultValue) {
		if(obj == null) {
			return defaultValue;
		}
		return obj;
	}
	
}
