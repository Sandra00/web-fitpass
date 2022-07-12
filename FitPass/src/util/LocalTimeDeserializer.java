package util;

import java.io.IOException;
import java.time.LocalTime;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class LocalTimeDeserializer extends StdDeserializer<LocalTime> {
	/*
	 * 
	*/
	private static final long serialVersionUID = 1L;
	
	protected LocalTimeDeserializer() {
		super(LocalTime.class);
	}
	
	@Override
	public LocalTime deserialize(JsonParser parser, DeserializationContext context) throws IOException {
		return LocalTime.parse(parser.readValueAs(String.class));
	}
}
