package ness.producer;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.jms.*;
import java.util.logging.Logger;

@Component
public class MQProducer implements Producer{

    @Value("${activemq.address}")
    private String activeMQAddress;

    @Value("${activemq.topic}")
    private String topicName;

    private Logger logger = Logger.getLogger(getClass().getName());

    @Override
    public int produce(String message) {
    logger.info("Sending message: ");
        try {
            // Create a ConnectionFactory
            ActiveMQConnectionFactory connectionFactory = new ActiveMQConnectionFactory(activeMQAddress);

            // Create a Connection
            Connection connection = connectionFactory.createConnection();
            connection.start();

            // Create a Session
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);

            // Create the destination (Topic or Queue)
            Destination destination = session.createTopic(topicName);

            // Create a MessageProducer from the Session to the Topic or Queue
            MessageProducer producer = session.createProducer(destination);
            producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

            // Create a messages
            TextMessage textMessage = session.createTextMessage(message);

            // Tell the producer to send the message
            producer.send(textMessage);

            // Clean up
            session.close();
            connection.close();
            return 1;
        } catch (Exception e) {
            logger.warning("Caught: " + e);
            return 0;
        }
    }
}
