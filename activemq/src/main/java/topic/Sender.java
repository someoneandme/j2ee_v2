package topic;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.Topic;

import cfg.Utils;

public class Sender {

	public static void main(String[] args) {
		Connection connection = null;

		try {
			connection = Utils.getConnection();
			connection.start();

			/**
			 * 参数说明相见p2p.Sender.java
			 */
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);

			// 指定主题myTopic.messages
			Topic topic = session.createTopic("myTopic.messages");  
			MessageProducer producer = session.createProducer(topic);

			/**
			 * 参数说明相见p2p.Sender.java
			 */
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);

			for (int i = 0; i < 20; i++) {
				Utils.sendMessage(session, producer, "你好" + i);
			    Thread.sleep(1000);
			    session.commit(); // 提交，没有提交的话发送的信息不会到activeMQ服务器
			}

			// 提交，没有提交的话发送的信息不会到activeMQ服务器
			session.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
