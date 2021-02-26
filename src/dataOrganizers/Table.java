package dataOrganizers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

/**
 * 
 * Table is a data structure responsible for keeping and retrieving data rows.
 * It consists of two elements header and table. Header is a HashSet that keeps
 * the data header. table is a HashMap of Fields that keeps the data rows. Each
 * data record containing a unique ID and a set of (field,value) is kept in a
 * row of the HasMap. Record IDs become keys in HashMap and (field, value) pairs
 * of the records become Fields in HahsMap. Fields is a HashMap keeping pairs of
 * (fieldNames, fieldValues). In this table data structure, different rows can
 * have different columns.
 * 
 * 
 * 
 * ****************Note: Although there is a Table class in "com.google.common.collect.Table" in "guava" library,
 * I have designed this table class from scratch with some customized methods and with little programming effort.********************* 
 * 
 *
 */
public class Table {

	private Map<String, Fields> table;
	private Set<String> header;

	public Table() {
		table = new HashMap<>();
		header = new HashSet<>();
	}

	public Map<String, Fields> getTable() {
		return this.table;
	}

	public void addRow(String id, Fields fields) {
		/**
		 * Adds a new data row to the table.
		 */
		updateHeader(fields.getNames());

		if (!table.containsKey(id))
			this.table.put(id, fields);
		else
			addFields(id, fields);

	}

	public void addFields(String id, Fields newFields) {
		/**
		 * Adds new columns to an existing data row.
		 */
		Fields currentFields = table.get(id);
		currentFields.merge(newFields);

	}

	public void updateHeader(Set<String> newHeaderNames) {
		/**
		 * Adds new data headers to the table header.
		 */
		for (String s : newHeaderNames)
			this.header.add(s);
	}

	public void addRecord(Record record) {
		/**
		 * Adds a record to the table.
		 */
		addRow(record.getId(), record.getFields());
	}

	public void combineTables(List<Table> tables) {
		/**
		 * Merges a list of tables and stores it in this.table.
		 */
		for (Table t : tables) {
			for (String id : t.getTable().keySet())
				addRow(id, t.getTable().get(id));
		}

	}

	public Map<String, Fields> sortById() {
		/**
		 * Provides a sorted view of the table. The rows are ordered by ID.
		 * 
		 * 
		 * *****Note: Since there is no mention that ID field are only numbers, IDs are assumed to
		 * be strings. Therefore, in ordering the output, ID: "1" and "11" comes before
		 * "2" and "22".*****
		 */
		return new TreeMap<>(this.table);

	}

	public String toString() {
		return this.table.toString();
	}

	public List<String[]> streamTable() {
		/**
		 * Prepares the table to be written to an output file.
		 *
		 * ******Note: Since there is no mention that IDS are only numbers,IDs are assumed to
		 * be strings. Therefore, in ordering the output, ID: "1" and "11" comes before
		 * "2" and "22".*******
		 */
		Map<String, Fields> sortedMap = sortById();
		String[] dataHeader = createDataHeader();
		List<String[]> dataRows = createDataRows(sortedMap, dataHeader);
		List<String[]> dataStream = createTableStream(dataHeader, dataRows);
		return dataStream;

	}

	private List<String[]> createTableStream(String[] dataHeader, List<String[]> dataRows) {
		/**
		 * Creates a table of data ready to be written into an output file.
		 */
		List<String[]> data = new ArrayList<>(dataRows);
		data.add(0, dataHeader);
		return data;
	}

	private String[] createDataHeader() {
		/**
		 * Prepares table header to be written into a file.
		 */
		String[] dataHeader = new String[this.header.size() + 1];

		dataHeader[0] = "id";
		int i = 1;
		for (String title : header) {
			dataHeader[i] = title;
			i++;
		}

		return dataHeader;
	}

	private List<String[]> createDataRows(Map<String, Fields> sortedView, String[] dataHeader) {
		/**
		 * Prepares data rows to be written into a field.
		 */
		List<String[]> dataRows = new ArrayList<>();

		for (String id : sortedView.keySet()) {
			String[] row = new String[dataHeader.length];
			Fields dataFields = sortedView.get(id);

			row[0] = id;

			for (int j = 1; j < row.length; j++) {
				String title = dataHeader[j];
				row[j] = dataFields.value(title);
			}

			dataRows.add(row);
		}

		return dataRows;
	}

	public boolean isEmpty() {
		return this.table.isEmpty();
	}

}
