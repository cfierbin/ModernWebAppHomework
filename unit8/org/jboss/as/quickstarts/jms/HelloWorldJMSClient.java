/*
 * JBoss, Home of Professional Open Source
 * Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
 * contributors by the @authors tag. See the copyright.txt in the
 * distribution for a full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.as.quickstarts.jms;

import javax.jms.*;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.math.BigDecimal;
import java.util.Properties;
import java.util.Random;
import java.util.logging.Logger;

public class HelloWorldJMSClient {
    private static final Logger log = Logger.getLogger(HelloWorldJMSClient.class.getName());

    // Set up all the default values
    private static final String DEFAULT_MESSAGE = "Hello, Eclipse IDE!";
    private static final String DEFAULT_CONNECTION_FACTORY = "jms/RemoteConnectionFactory";
    private static final String DEFAULT_DESTINATION = "jms/queue/test";
    private static final String DEFAULT_MESSAGE_COUNT = "1";
    private static final String DEFAULT_USERNAME = "quickstartUser";
    private static final String DEFAULT_PASSWORD = "quickstartPwd1!";
    private static final String INITIAL_CONTEXT_FACTORY = "org.jboss.naming.remote.client.InitialContextFactory";
    private static final String PROVIDER_URL = "http-remoting://127.0.0.1:9080";

    public static void main(String[] args) {

        Context namingContext = null;

        try {
            String userName = System.getProperty("username", DEFAULT_USERNAME);
            String password = System.getProperty("password", DEFAULT_PASSWORD);

            // Set up the namingContext for the JNDI lookup
            final Properties env = new Properties();
            env.put(Context.INITIAL_CONTEXT_FACTORY, INITIAL_CONTEXT_FACTORY);
            env.put(Context.PROVIDER_URL, System.getProperty(Context.PROVIDER_URL, PROVIDER_URL));
            env.put(Context.SECURITY_PRINCIPAL, userName);
            env.put(Context.SECURITY_CREDENTIALS, password);
            namingContext = new InitialContext(env);

            // Perform the JNDI lookups
            String connectionFactoryString = System.getProperty("connection.factory", DEFAULT_CONNECTION_FACTORY);
            log.info("Attempting to acquire connection factory \"" + connectionFactoryString + "\"");
            ConnectionFactory connectionFactory = (ConnectionFactory) namingContext.lookup(connectionFactoryString);
            log.info("Found connection factory \"" + connectionFactoryString + "\" in JNDI");

            String destinationString = System.getProperty("destination", DEFAULT_DESTINATION);
            log.info("Attempting to acquire destination \"" + destinationString + "\"");
            Destination destination = (Destination) namingContext.lookup(destinationString);
            log.info("Found destination \"" + destinationString + "\" in JNDI");

            int count = Integer.parseInt(System.getProperty("message.count", DEFAULT_MESSAGE_COUNT));
 //           String content = System.getProperty("message.content", DEFAULT_MESSAGE);
            //Bid content = new Bid();
            //create a random bid
            Bid bid = new Bid();
            Random number = new Random();
            bid.id = 1;

            Product product = new Product();
            int randomIdOfProduct = 1 + number.nextInt(2);
            product.id = randomIdOfProduct;

            User user = new User();
            user.name = "Cristina";
            int randomUserId = 1 + number.nextInt(4);
            user.id = randomUserId;
            int randomNotifications = number.nextInt(2); //0 or 1
            user.getOverbidNotifications = (randomNotifications == 0) ? false : true;

            bid.product = product;
            bid.user = user;

            BigDecimal randomAmount = BigDecimal.valueOf(100000*number.nextDouble());
            bid.amount = randomAmount;

            try (JMSContext context = connectionFactory.createContext(userName, password)) {
                //log.info("Sending " + count + " messages with content: " + content);
                log.info("Sending " + count + " bids");
                // Send the specified number of messages

                JMSProducer producer = context.createProducer();
                ObjectMessage objMsg = context.createObjectMessage(bid);
                for (int i = 0; i < count; i++) {
      //              producer.send(destination, content);
                    producer.send(destination, objMsg);
                }


                // Create the JMS consumer
                JMSConsumer consumer = context.createConsumer(destination);
                // Then receive the same number of messages that were sent
                for (int i = 0; i < count; i++) {
  //                  String text = consumer.receiveBody(String.class, 5000);
                    Bid myBid = consumer.receiveBody(Bid.class);
                    log.info("Received bid from " + myBid.user.name);
                }

            }

        } catch (NamingException e) {
            log.severe(e.getMessage());
        } finally {
            if (namingContext != null) {
                try {
                    namingContext.close();
                } catch (NamingException e) {
                    log.severe(e.getMessage());
                }
            }
        }
    }
}

