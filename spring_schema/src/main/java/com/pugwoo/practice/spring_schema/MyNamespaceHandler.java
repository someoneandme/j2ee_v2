package com.pugwoo.practice.spring_schema;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

public class MyNamespaceHandler extends NamespaceHandlerSupport {

	public void init() {
		/**
		 * 注册xml标签名的bean处理器，bean处理器就是怎样根据这个xml接口生成对象.
		 * 
		 * 就是用来把节点名和解析类联系起来
		 */
		registerBeanDefinitionParser("people", new PeopleBeanDefinitionParser());
	}

}
