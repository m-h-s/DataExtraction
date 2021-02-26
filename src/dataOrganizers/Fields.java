package dataOrganizers;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * 
 * Fields is a data structure for keeping the attributes of an entity.
 * Attributes are pairs of (Attribute Name, Attribute Values). Fields is a
 * HashMap containing attribute pairs.
 *
 */

public class Fields {

	private Map<String, String> fields;
	private Logger logger;

	public Fields() {
		fields = new HashMap<>();
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	}

	public Map<String, String> getFields() {
		/**
		 * Returns all pairs of (fieldName, fieldValue);
		 */
		return this.fields;
	}

	public Set<String> getNames() {
		/**
		 * Returns the name of fields.
		 */
		return this.fields.keySet();
	}

	public int size() {
		/**
		 * Returns the number of fields.
		 */
		return this.fields.size();
	}

	public String value(String fieldName) {
		/**
		 * Returns the value of a field name.
		 */
		return this.fields.get(fieldName);
	}

	public void addField(String fieldName, String value) {
		/**
		 * Adds a new pair of (fieldName, values) in case there is no existing fieldName
		 * in the existing fields or if the new value and old value of a field match.
		 * Otherwise it produces an error message.
		 */
		if (!fields.containsKey(fieldName)) {
			this.fields.put(fieldName, value);
		}

		else {
			String curValue = this.fields.get(fieldName);
			if (!Objects.equals(curValue.trim(), value.trim()))
				logger.log(Level.WARNING, "There is a conflict for the field " + fieldName + " " + curValue + " and "
						+ value + "mismatch.");
		}
	}

	public void merge(Fields newFields) {
		/**
		 * Merges given fields into existing fields.
		 */
		Map<String, String> newCols = newFields.getFields();

		for (String key : newCols.keySet())
			addField(key, newCols.get(key));
	}

	public String toString() {
		return this.fields.toString();
	}

}
