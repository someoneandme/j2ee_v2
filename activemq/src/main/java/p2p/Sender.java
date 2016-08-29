package p2p;

import java.util.Date;

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
			
			/**
			 * 参数设定参考：http://riddickbryant.iteye.com/blog/441890
			 * 第一个参数指定session是否事务，当true时第二参数无用，true时要求消息就通过确认和校正，以保证消息一定到达
			 * 当第一个参数是false时，第二个参数有如下选择：
			 * 1) AUTO_ACKNOWLEDGE 接收方session将自动地确认收到的一则消息
			 * 2) CLIENT_ACKNOWLEDGE 客户端程序将确认收到的一则消息，调用这则消息的确认方法
			 * 3) DUPS_OK_ACKNOWLEDGE 自动批量确认。当JMS出问题时，客户端可能收到重复的消息，这就要求客户端能容忍重复消息
			 * 
			 * 保证有保障的消息：http://www2.sys-con.com/itsg/virtualcd/java/archives/0604/chappell/index.html
			 * http://shift-alt-ctrl.iteye.com/blog/2020182
			 */
			Session session = connection.createSession(true,
					Session.AUTO_ACKNOWLEDGE);
			
			// 指定队列FirstQueue
			Destination destination = session.createQueue("FirstQueue");
			MessageProducer producer = session.createProducer(destination);
			
			/**
			 * 设置持久化
			 * 
			 * DeliveryMode.NON_PERSISTENT
			 * 没有持久化，意味着当JMS重启时，数据将丢失，而【不是】
			 * http://riddickbryant.iteye.com/blog/441890
			 * 中说的标记为NON_PERSISTENT的消息最多传递一次
			 * 
			 * DeliveryMode.PERSISTENT
			 * 意味着JMS重启后，数据还存在。
			 */
			producer.setDeliveryMode(DeliveryMode.PERSISTENT);
			
			for(int i = 0; i < 10; i++) {
				Utils.sendMessage(session, producer, "你好" + i + "" + new Date());
				Thread.sleep(1000);
				session.commit(); // 一个session可以多次commit，commit才回发送消息
			}
			
			// 提交，没有提交的话发送的信息不会到activeMQ服务器
			// 也就是说，发送时一批同一时刻发送过去的
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
