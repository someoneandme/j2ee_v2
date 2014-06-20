package p2p;

import javax.jms.Connection;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;

import cfg.Utils;

/**
 * 2014年6月20日 17:45:15
 */
public class Sender {

	public static void main(String[] args) {
		
		Connection connection = null;
		
		try {
			connection = Utils.getConnection();
			connection.start();
			
			Session session = connection.createSession(Boolean.TRUE,
					Session.AUTO_ACKNOWLEDGE);
			
			// 指定队列FirstQueue
			Destination destination = session.createQueue("FirstQueue");
			MessageProducer producer = session.createProducer(destination);
			
			// 设置不持久化，此处学习，实际根据项目决定
			producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
			
			for(int i = 0; i < 5; i++) {
				Utils.sendMessage(session, producer, "你好" + i);
			}
			
			// 提交，没有提交的话发送的信息不会到activeMQ服务器
			session.commit();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(connection != null) {
				try {
					connection.close();
				} catch (JMSException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
}
