package io.redspark.email.overview.json;

import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * @author marcos.fernandes
 * 
 */
public class LocalDateJsonDeserializer extends JsonDeserializer<LocalDate> {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public LocalDate deserialize(JsonParser jp, DeserializationContext ctxt) {
		DateTimeFormatter format = DateTimeFormat.forPattern("dd/MM/yyyy");
		LocalDate value = null;
		try {
			value = format.parseLocalDate(jp.getText());
		} catch (Exception e) {
			log.error("Deserializer LocalDate", e);
		}
		return value;
	}
}