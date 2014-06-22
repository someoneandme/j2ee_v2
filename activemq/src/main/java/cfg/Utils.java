package cfg;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;

import org.apache.activemq.ActiveMQConnectionFactory;

/**
 * 2014年6月20日 17:48:36
 */
public class Utils {

	private static ConnectionFactory connectionFactory;

	static {
		connectionFactory = new ActiveMQConnectionFactory(Config.user,
				Config.password, Config.url);
	}

	public static ConnectionFactory getConnectionFactory() {
		return connectionFactory;
	}

	public static Connection getConnection() throws JMSException {
		return connectionFactory.createConnection();
	}

	public static void sendMessage(Session session, MessageProducer producer,
			String content) throws Exception {
		// 发送消息到目的地方
		TextMessage message = session.createTextMessage(content);
		System.out.println("发送消息：" + content);
		producer.send(message);
	}

}
