package inputReaders;

import java.util.logging.Level;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import dataOrganizers.Record;
import dataOrganizers.Table;

/**
 * 
 * HTML Parser is responsible for receiving the the name of an input html file,
 * reading the content and extracting the content of a table named ""directory"
 * into a table.
 *
 */
public class HTMLParser extends InputParser {

	private Document doc;
	private Element table, header;
	private Elements dataRows;
	private final String TABLEID = "directory";

	public HTMLParser(String input) {
		super(input);
	}

	@Override
	public Table parse() {

		openFile();
		createDocument();
		extractTableInfo();
		if (table != null && header != null && dataRows != null)
			createOutputTable();
		return output;
	}

	private void createDocument() {
		/**
		 * Create a document object model (DOM) based on the input HTML file. The
		 * encoding for reading the input data is UTF-8.
		 */
		try {
			this.doc = Jsoup.parse(input, "UTF-8", "");
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	@Override
	protected void extractTableInfo() {
		/**
		 * Extracts the table element with id TABLEID from the DOM.
		 */
		table = this.doc.getElementById(TABLEID);
		if (table != null) {
			extractDataHeader();
			extractDataRows();
		} else {
			logger.log(Level.SEVERE,"File \"" + input + "\" does not have a table with id \"" + TABLEID + "\".");
		}

	}

	@Override
	protected void extractDataHeader() {
		/**
		 * Extracts the header of the data table from the DOM. The header is the first
		 * row of the table.
		 */
		Elements allRows = table.getElementsByTag("tr");
		if (!allRows.isEmpty()) {
			header = allRows.first();
		} else {
			logger.log(Level.WARNING,"File \"" + input + "\" contains no header and data rows.");
		}

	}

	@Override
	protected void extractDataRows() {
		/**
		 * Extracts the data rows of the DOM. All rows except the first row are data
		 * rows.
		 */
		Elements allRows = table.getElementsByTag("tr");

		if (allRows.size() > 1) {
			dataRows = allRows.first().siblingElements();
		} else {
			logger.log(Level.WARNING,"File \"" + input + "\" does not have data rows.");
		}
	}

	@Override
	protected void createOutputTable() {
		/**
		 * Creates a table based on the information extracted from the DOM.
		 */
		String[] fieldNames = getFieldNames();
		int idIndex = getIdColIndex(fieldNames);

		if (!checkId(idIndex)) {
			return;
		}

		for (Element row : dataRows) {

			String[] fieldValues = getFieldValues(row);

			if (fieldNames.length == fieldValues.length) {
				Record record = createRecord(fieldNames, fieldValues, idIndex);
				output.addRecord(record);
			} else {
				logger.log(Level.SEVERE,
						"In the file \"" + input + "\" there is a mismatch between header size and row size.");
			}
		}

	}

	private String[] getFieldNames() {
		/**
		 * Returns a list containing the names of the fields.
		 */
		String[] fieldNames = new String[header.children().size()];

		int i = 0;
		for (Element e : header.children()) {
			fieldNames[i++] = e.text().trim().toLowerCase();
		}

		return fieldNames;
	}

	public String[] getFieldValues(Element dataRow) {
		/**
		 * Returns field values of an html row as an array.
		 */
		String[] fieldValues = new String[dataRow.children().size()];

		int i = 0;
		for (Element data : dataRow.children()) {
			fieldValues[i++] = data.text().trim();
		}

		return fieldValues;
	}

}
