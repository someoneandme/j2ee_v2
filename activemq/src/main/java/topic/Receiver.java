package topic;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import cfg.Utils;

public class Receiver {

	public static void main(String[] args) {

		Connection connection = null;

		try {
			connection = Utils.getConnection();
			/**
			 * 【重要】对于持久消息订阅者，需要标识客户端，全局唯一
			 */
			connection.setClientID("ABC_Receiver");

			/**
			 * 参考p2p.Receiver.java
			 */
			Session session = connection.createSession(false,
					Session.AUTO_ACKNOWLEDGE);

			// 获取指定的主题myTopic.messages
			Topic topic = session.createTopic("myTopic.messages");

			// 基于Topic创建持久的消息订阅者
			// durable TopicSubscriber会保证receiver挂掉一段时间后重启仍能收到完整的消息(除非消息过期)
			/**
			 * 在同一个clientID内，name也是唯一的，the name used to identify this
			 * subscription Only one session at a time can have a
			 * TopicSubscriber for a particular durable subscription. 订阅名
			 */
			MessageConsumer consumer = session.createDurableSubscriber(topic,
					"MySub");

			consumer.setMessageListener(new MessageListener() {
				public void onMessage(Message message) {
					TextMessage tm = (TextMessage) message;
					try {
						System.out.println("Received message: " + tm.getText());
					} catch (JMSException e) {
						e.printStackTrace();
					}
				}
			});

			connection.start();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			// connection.close(); // 这里执行这个会结束整个程序
		}
	}
}
