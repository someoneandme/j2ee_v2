package com.pugwoo.practice.spring_schema;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.xml.AbstractSingleBeanDefinitionParser;
import org.springframework.util.StringUtils;
import org.w3c.dom.Element;

/**
 * 对标签bean的处理。
 * 注意，如果该xml结点的子结点也是bean的话，子结点的bean parser要单独定义。
 * 
 */
public class PeopleBeanDefinitionParser extends
		AbstractSingleBeanDefinitionParser {

	/**
	 * 返回生成的bean类型
	 */
	@Override
	protected Class<?> getBeanClass(Element element) {
		return People.class;
	}

	/**
	 * 这里就是关键的代码：怎样生成这个对象。
	 * 
	 * @param element 这个就包含了当前xml节点的所有标签，包括属性、子node等。
	 * @param bean 通过这个告诉spring需要为对象加入哪些属性，相当于property注入
	 */
	protected void doParse(Element element, BeanDefinitionBuilder bean) {
		String name = element.getAttribute("name");
		String age = element.getAttribute("age");
		String id = element.getAttribute("id");
		if (StringUtils.hasText(id)) {
			bean.addPropertyValue("id", id);
		}
		if (StringUtils.hasText(name)) {
			bean.addPropertyValue("name", name);
		}
		if (StringUtils.hasText(age)) {
			bean.addPropertyValue("age", Integer.valueOf(age));
		}
	}

}
