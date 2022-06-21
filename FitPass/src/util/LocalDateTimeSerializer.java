package util;


import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class LocalDateTimeSerializer extends JsonSerializer<LocalDateTime> {

	  private final DateTimeFormatter format = DateTimeFormatter.ISO_DATE_TIME;

	  @Override
	  public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
	    gen.writeString(value.format(format));
	  }
	  
	}