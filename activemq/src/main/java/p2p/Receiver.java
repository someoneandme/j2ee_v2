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
			// 没有必要设置clientID
			connection.start();
			
			/**
			 * 当发送者的session设置为true事务型时，接收者必须对消息确认，否则服务器会认为消息未传递到：
			 * 确认方式：
			 * 1) Session.AUTO_ACKNOWLEDGE 自动确认，接收成功就确认
			 * 2) Session.CLIENT_ACKNOWLEDGE 接收者手工确认，才算消息投递成功
			 * 
			 * 问题：
			 * 当客户端太久确认时，怎样保证服务器过久认为没有接收到而重复投递呢？
			 * 所以确实有超时机制。
			 * 这还是回到那个问题，无法保证发且只发一次。
			 * 
			 */
//			Session session = connection.createSession(false,
//					Session.AUTO_ACKNOWLEDGE);
			Session session = connection.createSession(false,
					Session.CLIENT_ACKNOWLEDGE);
			
            // 获取指定的队列FirstQueue
			Destination destination = session.createQueue("FirstQueue");
			MessageConsumer consumer = session.createConsumer(destination);
            
            while (true) {
                //设置接收者接收消息的时间，为了便于测试，这里设定为30s
            	// 30s意味着：consumer.receive最多会阻塞30秒
                TextMessage message = (TextMessage) consumer.receive(30000);
                
                if (null != message) {
                    System.out.println("收到消息:" + message.getText());
                    
                    // 处理完消息之后，手工确认
                    message.acknowledge();
                    
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
