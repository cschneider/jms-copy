package org.talend.jms.copy;

import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.advisory.DestinationSource;
import org.apache.activemq.command.ActiveMQQueue;

public class Copy {
    private ActiveMQConnection sconn;
    private ActiveMQConnection dconn;
    private AtomicInteger counter;

    public Copy(ActiveMQConnectionFactory sourceCF, ActiveMQConnectionFactory destCF) throws JMSException {
        this.counter = new AtomicInteger();
        sconn = (ActiveMQConnection)sourceCF.createConnection();
        dconn = (ActiveMQConnection)destCF.createConnection();
        sconn.start();
        dconn.start();
    }

    public void copyQueues(String filter) throws JMSException, InterruptedException {
        DestinationSource ds = sconn.getDestinationSource();
        Set<ActiveMQQueue> queues = ds.getQueues();
        
        for (ActiveMQQueue queue : queues) {
            System.out.println(queue.getQueueName());
            counter.set(0);
            copyQueue(queue);
        }
        dconn.close();
        sconn.close();

    }

    private void copyQueue(ActiveMQQueue queue) throws JMSException {
        Session ssess = sconn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Session dsess = dconn.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageConsumer consumer = ssess.createConsumer(queue);
        Queue destQueue = dsess.createQueue(queue.getQueueName());
        MessageProducer producer = dsess.createProducer(destQueue);
        Message message;
        while ((message = consumer.receive(100)) != null) {
            int c = counter.getAndIncrement();
            if (c % 10 == 0) {
                System.out.println("Queue: " + queue.getQueueName() + ". Move message " + c);
            }
            producer.send(destQueue, message);
        }
        producer.close();
        consumer.close();
        dsess.close();
        ssess.close();
    }

}
