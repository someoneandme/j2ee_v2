package spring_mvc3.test.vm;

import java.io.StringWriter;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.RuntimeConstants;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;

/**
 * 直接就渲染velocity出来，使用class路径
 * 2015年1月23日 17:32:05
 */
public class TestVelocityMain {

	public static void main(String[] args) {
        VelocityEngine ve = new VelocityEngine();
        
        ve.setProperty(RuntimeConstants.RESOURCE_LOADER, "classpath"); 
        ve.setProperty("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
        
        ve.init();
        
        Template t = ve.getTemplate("/spring_mvc3/hello.vm");
        
        VelocityContext context = new VelocityContext();
        context.put("name", "World");
        
        StringWriter writer = new StringWriter();
        t.merge(context, writer);
        
        System.out.println(writer.toString());
	}
	
}
