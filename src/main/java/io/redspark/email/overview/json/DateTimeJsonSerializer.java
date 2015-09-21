package io.redspark.email.overview.json;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * @author marcelofelix
 * 
 */
public class DateTimeJsonSerializer extends JsonSerializer<LocalDateTime> {

	/**
	 * (non-Javadoc) {@inheritDoc}
	 * 
	 * @see org.codehaus.jackson.map.JsonSerializer#serialize(java.lang.Object,
	 *      org.codehaus.jackson.JsonGenerator,
	 *      org.codehaus.jackson.map.SerializerProvider)
	 */
	@Override
	public void serialize(final LocalDateTime value, final JsonGenerator jgen, final SerializerProvider provider)
			throws IOException {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		jgen.writeString(value.format(format));

	}
}