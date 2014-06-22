package topic_not_durable;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import cfg.Utils;

/**
 * 非durable的topic发送和订阅:
 * 只有发送者和接收者同时在工作时才收到消息。
 * 不保存没接收到的消息。
 */
public class TopicPublisher {
	public static void main(String[] args) throws JMSException {

		Connection connection = Utils.getConnection();
		connection.start();

		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);
		Topic topic = session.createTopic("myTopic.messages_notdurable");

		MessageProducer producer = session.createProducer(topic);

		producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

		for (int i = 0; i < 1000; i++) {
			TextMessage message = session.createTextMessage();
			message.setText("message_" + i);
			producer.send(message);
			System.out.println("Sent message: " + message.getText());

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// session.close();
		// connection.stop();
		// connection.close();
	}
}