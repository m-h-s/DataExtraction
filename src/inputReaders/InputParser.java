package inputReaders;

import java.io.File;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataOrganizers.Record;
import dataOrganizers.Table;

/**
 * 
 * Input parser is the super class for various parser that receive the name of
 * an input file, read the content and extract the related information into an
 * output file.
 * 
 * To add a new parser, this class should be extended.
 *
 */

public abstract class InputParser {

	protected File input;
	protected String filePath;
	protected Table output;
	protected Logger logger;

	public InputParser(String input) {
		filePath = Paths.get("data", input).toString();
		output = new Table();
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	}

	protected void openFile() {
		try {
			this.input = new File(filePath);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	protected int getIdColIndex(String[] fieldNames) {
		/**
		 * Gets the index of the ID column.
		 */
		int idIndex = -1;

		for (int i = 0; i < fieldNames.length; i++)
			if (Objects.equals(fieldNames[i].toLowerCase().trim(), "id")) {
				idIndex = i;
				break;
			}

		return idIndex;
	}

	protected boolean checkId(int idIndex) {
		/**
		 * Checks that the data has ID field.
		 */
		if (idIndex == -1) {
			logger.log(Level.SEVERE, "File \"" + input + "\" does not have an ID field. "
					+ "Indexing of the data is impossible for this file.");

			return false;
		}

		return true;
	}

	protected Record createRecord(String[] fieldNames, String[] fieldValues, int idIndex) {
		/**
		 * Creates a record, given field names and field values and the index of id
		 * column.
		 */
		Record record = new Record();

		int i = 0;
		for (String value : fieldValues) {
			if (i == idIndex) {
				record.setId(value);
			} else {
				record.addField(fieldNames[i], value);
			}
			i++;
		}

		return record;
	}

	public abstract Table parse();

	protected abstract void extractTableInfo();

	protected abstract void extractDataHeader();

	protected abstract void extractDataRows();

	protected abstract void createOutputTable();

}
