package io.redspark.email.overview.json;

import java.io.IOException;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;
import org.joda.time.LocalDateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author marcelofelix
 * 
 */
public class DateTimeJsonDeserializer extends JsonDeserializer<LocalDateTime> {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * (non-Javadoc) {@inheritDoc}
	 * @throws IOException 
	 * @throws JsonParseException 
	 * 
	 * @see org.codehaus.jackson.map.JsonDeserializer#deserialize(org.codehaus.jackson.JsonParser,
	 *      org.codehaus.jackson.map.DeserializationContext)
	 */
	@Override
	public LocalDateTime deserialize(JsonParser jp, DeserializationContext ctxt) throws JsonParseException, IOException {
		DateTimeFormatter format;
		LocalDateTime value = null;
		if(jp.getText().length() == 10){
			format = DateTimeFormat.forPattern("dd/MM/yyyy");
		} else {
			format = DateTimeFormat.forPattern("dd/MM/yyyy HH:mm");
		}
		try {
			value = format.parseLocalDateTime(jp.getText());
		} catch (Exception e) {
			log.error("Deserializer DateTime", e);
		}
		return value;
	}

}