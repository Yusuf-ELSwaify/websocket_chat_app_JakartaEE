package org.example;

import jakarta.websocket.OnClose;
import jakarta.websocket.OnMessage;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.ServerEndpoint;
import org.example.dtos.MessageDTO;
import org.example.dtos.UserDTO;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.Set;

@ServerEndpoint("/chat")
public class ChatServlet {

	public static final String GIRL_PNG = "/web_socket/images/female.png";
	public static final String MALE_PNG = "/web_socket/images/male.png";

	@OnOpen
	public void onOpen(Session session){
		Map<String, Object> userProperties = putParametersOnSession(session);
		Set<Session> openSessions = session.getOpenSessions();
		UserDTO userDTO = new UserDTO();
		userDTO.id = session.getId();
		userDTO.name = userProperties.get("name").toString();
		userDTO.aviURL = userProperties.get("gender").equals("male") ? MALE_PNG : GIRL_PNG;
		userDTO.online = true;
		for (Session openedsession : openSessions) {
			try {
				if (openedsession != session)
					openedsession.getBasicRemote().sendText(userDTO.toJson());
			} catch (IOException e) {
				System.out.println(e);
				throw new RuntimeException(e);
			}
		}
		System.out.println(session.getId() + " opened connection.");
	}

	private static Map<String, Object> putParametersOnSession(Session session) {
		Map<String, Object> userProperties = session.getUserProperties();
		String queryString = session.getQueryString();
		String name = null, gender = null;

		String[] params = queryString.split("&");
		for (String param : params) {
			String[] keyValue = param.split("=");
			if (keyValue.length == 2) {
				if (keyValue[0].equalsIgnoreCase("gender"))
					gender = keyValue[1];
				else if (keyValue[0].equalsIgnoreCase("name"))
					name = keyValue[1];
			}
		}
		if (name == null || gender == null)
			throw new RuntimeException("parameters not founded");
		userProperties.put("name", name);
		userProperties.put("gender", gender);
		return userProperties;
	}

	@OnClose
	public void onClose(Session session){

		Set<Session> openSessions = session.getOpenSessions();
		UserDTO userDTO = new UserDTO();
		userDTO.id = session.getId();
		Map<String, Object> userProperties = session.getUserProperties();
		userDTO.name = userProperties.get("name").toString();
		userDTO.aviURL = userProperties.get("gender").equals("male") ? MALE_PNG : GIRL_PNG;
		userDTO.online = false;
		for (Session openedsession : openSessions) {
			try {
				if (openedsession != session)
					openedsession.getBasicRemote().sendText(userDTO.toJson());
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		System.out.println(session.getId() + " closed connection.");
	}
	@OnMessage
	public void onMessage(String message, Session session){
		Set<Session> openSessions = session.getOpenSessions();
		Map<String, Object> userProperties = session.getUserProperties();
		MessageDTO messageDTO = new MessageDTO();
		messageDTO.me = false;
		messageDTO.text = message;
		messageDTO.sender = userProperties.get("name").toString();
		messageDTO.time = LocalDateTime.now();
		try {
			for (Session openedsession : openSessions) {
				if (openedsession == session)
				{
					messageDTO.me = true;
					openedsession.getBasicRemote().sendText(messageDTO.toJson());
					messageDTO.me = false;
				}
				else
					openedsession.getBasicRemote().sendText(messageDTO.toJson());
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
