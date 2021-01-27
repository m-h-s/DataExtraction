package inputReaders;

import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVReader;
import dataOrganizer.Record;
import dataOrganizer.Table;

/**
 * 
 * CVS Parser is responsible for receiving the the name of an input csv file,
 * reading the content and extracting the content into a table.
 *
 */

public class CSVParser extends InputParser {

	CSVReader csvReader;
	InputStreamReader fileReader;
	FileInputStream fileStream;
	List<String[]> table, dataRows;
	String[] header;

	public CSVParser(String input) {

		super(input);
		table = new ArrayList<>();
		dataRows = new ArrayList<>();
		header = new String[0];
	}

	@Override
	public Table parse() {

		openFile();
		extractTableInfo();
		createOutputTable();
		return output;
	}

	@Override
	protected void extractTableInfo() {

		/**
		 * Opens the CSV file and reads the data all at once.
		 */
		try {

			fileStream = new FileInputStream(input);
			fileReader = new InputStreamReader(fileStream, StandardCharsets.UTF_8);
			csvReader = new CSVReader(fileReader);

			table = csvReader.readAll();

			if (!table.isEmpty()) {
				extractDataHeader();
				extractDataRows();
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	protected void extractDataHeader() {
		/**
		 * Extract the header of the data table. The header is the first row of the
		 * table.
		 */

		header = new String[table.get(0).length];

		int i = 0;
		for (String title : table.get(0)) {
			header[i] = title.trim().toLowerCase();
			i++;
		}

	}

	@Override
	protected void extractDataRows() {

		/**
		 * Extracts the data rows of the table. All rows except the first row are data
		 * rows.
		 */

		dataRows = table.subList(1, table.size());

	}

	@Override
	protected void createOutputTable() {
		/**
		 * Creates a table based on the extracted data header and data rows.
		 */

		int idIndex = getIdColIndex(header);

		if (!(checkId(idIndex)))
			return;

		for (String[] row : dataRows) {
			if (header.length != row.length) {
				System.out.println(
						"Error: In file " + input + " there is a mismatch between data row and data header size.");
			} else {
				Record record = createRecord(header, row, idIndex);
				output.addRecord(record);
			}
		}

	}

}
