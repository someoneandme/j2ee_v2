package topic_not_durable;

import javax.jms.Connection;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import cfg.Utils;

/**
 * 非durable的topic发送和订阅:
 * 只有发送者和接收者同时在工作时才收到消息。
 * 不保存没接收到的消息。
 */
public class TopicSubscriber {
	public static void main(String[] args) throws JMSException {
		
		Connection connection = Utils.getConnection();
		connection.start();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("myTopic.messages_notdurable");

		MessageConsumer consumer = session.createConsumer(topic);
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
		
		// session.close();
		// connection.stop();
		// connection.close(); // 这个一执行程序就结束了
	}
}
