package org.example.dtos;

import jakarta.json.Json;
import jakarta.json.JsonObjectBuilder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;

public class MessageDTO {
	public boolean me;
	public String sender;
	public LocalDateTime time;

	public String text;
	public String toJson() {
		String pattern = "yyyy-MM-dd hh:mm";
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern(pattern);
		JsonObjectBuilder jsonBuilder = Json.createObjectBuilder();
		jsonBuilder.add("me", me)
				.add("sender", sender)
				.add("time", time.format(myFormatObj))
				.add("text", text);

		String message = jsonBuilder.build().toString();
		return Json.createObjectBuilder().add("message", message).build().toString();
	}
}
