package spring_mvc3.test.vm;

import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;
import org.junit.Test;

/**
 * 2014年4月22日 12:19:38
 * 
 * 关于路径的问题：http://panyongzheng.iteye.com/blog/1882559
 * 
 * 这里涉及到velocity路径的问题，但是还是没有解决。
 * http://stackoverflow.com/questions/5342704/how-to-set-properly-the-loader-path-of-velocity
 */
public class TestVelocityMain {
	
	private VelocityEngine engine;
	
	public TestVelocityMain() {
		
		// 这个根据实际情况修改,相对路径弄不好，所以这里改成强制获得绝对路径
		String absolutePath = new File(Thread.currentThread()
				.getContextClassLoader().getResource("").getFile())
				.getParentFile().getParentFile().getPath();
		String vmPath = absolutePath + "/src/main/webapp/WEB-INF/velocity";
		
		Properties p = new Properties();
		p.setProperty(Velocity.FILE_RESOURCE_LOADER_PATH, vmPath);
		p.setProperty(Velocity.ENCODING_DEFAULT, "UTF-8");
		p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
		p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
		
		engine = new VelocityEngine(p);
		
	}

	@Test
	public void testRender() throws IOException {
		
		String tempName = "velocity_index.vm";
		Template template = engine.getTemplate(tempName, "UTF-8");

		// 渲染填值
		VelocityContext context = new VelocityContext();
		context.put("userName", "nick");

		Writer writer = new StringWriter();
		template.merge(context, writer);
		writer.flush();

		System.out.println(writer.toString());
		
	}
}
