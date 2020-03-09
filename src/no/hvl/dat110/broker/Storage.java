package no.hvl.dat110.broker;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import no.hvl.dat110.common.TODO;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;

public class Storage {

	// data structure for managing subscriptions
	// maps from a topic to set of subscribed users
	protected ConcurrentHashMap<String, Set<String>> subscriptions;
	
	// data structure for managing currently connected clients
	// maps from user to corresponding client session object
	
	protected ConcurrentHashMap<String, ClientSession> clients;
	
	protected ConcurrentHashMap<String, Set<Message>> bufferedMessages;

	public Storage() {
		subscriptions = new ConcurrentHashMap<String, Set<String>>();
		clients = new ConcurrentHashMap<String, ClientSession>();
		bufferedMessages = new ConcurrentHashMap<>();
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {

		return subscriptions.keySet();

	}

	// get the session object for a given user
	// session object can be used to send a message to the user
	
	public ClientSession getSession(String user) {

		ClientSession session = clients.get(user);

		return session;
	}

	public Set<String> getSubscribers(String topic) {

		return (subscriptions.get(topic));

	}
	
	public Set<Message> getBufferedMessages(String user){
		return bufferedMessages.get(user);
	}

	public void addBufferedMessage(String user, Message message){
		Set<Message> msg;
		if(!bufferedMessages.containsKey(user)) {
			msg = new HashSet<>();
		}else {
			msg = bufferedMessages.get(user);
		}
		msg.add(message);
		bufferedMessages.put(user, msg);

	}

	public void clearBufferedMessages(String user){
		bufferedMessages.remove(user);
	}

	public void addClientSession(String user, Connection connection) {

		// TODO: add corresponding client session to the storage
		
		// Creating a new client session:
		ClientSession session = new ClientSession(user, connection);
		
		// Adding the clientsession to the storage.
		clients.put(user, session);
		
		
		
	}

	public void removeClientSession(String user) {

		// TODO: remove client session for user from the storage
		
		
		clients.remove(user);

		
	}

	public void createTopic(String topic) {

		// TODO: create topic in the storage
		
		
		Set<String> topics = new HashSet<String>();
		
		topics.add(topic);
		
		subscriptions.put(topic, topics);
		

		
	
	}

	public void deleteTopic(String topic) {

		// TODO: delete topic from the storage

		subscriptions.remove(topic);
		
	}

	public void addSubscriber(String user, String topic) {

		// TODO: add the user as subscriber to the topic
		
		Set<String> users = new HashSet<String>();
		users.add(user);
		
		subscriptions.put(topic, users);
	}

	public void removeSubscriber(String user, String topic) {
		
		// TODO: remove the user as subscriber to the topic
		//Set<String> users = new HashSet<String>();
		//users.add(user);
		Set<String> users = (Set<String>)getSubscribers(topic);
		users.remove(user);
		
		//subscriptions.remove(topic, users);
		subscriptions.put(topic, users);
		
		
		
		
	}
}
