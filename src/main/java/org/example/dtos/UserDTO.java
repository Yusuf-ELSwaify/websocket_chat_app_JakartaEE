package org.example.dtos;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

public class UserDTO {
	public String id;
	public String name;
	public String aviURL;
	public boolean online;
	public String toJson() {
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("id", id)
				.add("name", name)
				.add("aviURL", aviURL)
				.add("online", online);

		String message = jsonBuilder.build().toString();
		return Json.createObjectBuilder().add("user", message).build().toString();
	}
}
