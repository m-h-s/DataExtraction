package dataOrganizers;

/**
 * 
 * Record is a data structure for keeping the information about an entity.
 * Record consists of an id which is the unique identifier of an entity and
 * Fields which is a map of (Attribute nName, Attribute Value) pairs.
 *
 */
public class Record {

	private String id;
	private Fields fields;

	public Record() {
		/**
		 * Creates an empty record.
		 */
		id = "";
		fields = new Fields();
	}

	public void addField(String name, String value) {
		/**
		 * Adds new fields to a record.
		 */
		fields.addField(name, value);
	}

	public Fields getFields() {
		return fields;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String toString() {
		return this.id + ": " + this.fields;
	}

}
