package p2p;

import javax.jms.Connection;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.Session;
import javax.jms.TextMessage;

import cfg.Utils;

/**
 * 2014年6月20日 18:00:49
 */
public class Receiver {

	public static void main(String[] args) {
		
		Connection connection = null;
		
		try {
			connection = Utils.getConnection();
			connection.start();
			
			Session session = connection.createSession(Boolean.FALSE,
					Session.AUTO_ACKNOWLEDGE);
			
            // 获取指定的队列FirstQueue
			Destination destination = session.createQueue("FirstQueue");
			MessageConsumer consumer = session.createConsumer(destination);
            
            while (true) {
                //设置接收者接收消息的时间，为了便于测试，这里设定为100s
                TextMessage message = (TextMessage) consumer.receive(100000);
                
                if (null != message) {
                    System.out.println("收到消息:" + message.getText());
                } else {
                	System.out.println("没有收到消息，退出本轮接收");
                    break;
                }
            }
			
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
