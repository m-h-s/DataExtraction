package mergers;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import dataOrganizers.Table;
import inputReaders.InputParser;
import inputReaders.ParserFactory;
import outputWriters.OutputWriter;

/**
 * 
 * Controller is responsible for receiving the input files from the Main class,
 * and coordinating the involved classes to extract data from the input file, 
 * combine the extracted data into a single table and writing the table to an output file.
 *
 */
public class Controller {

	private ParserFactory parserFactory;
	private List<Table> inputTables;
	private Table output;
	private OutputWriter writer;
	private Logger logger;

	public Controller() {
		parserFactory = new ParserFactory();
		inputTables = new ArrayList<>();
		output = new Table();
		writer = new OutputWriter();
		logger = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME); 
	}

	public void parseAndCombineInput(String[] fileNames) {
		parseInputFiles(fileNames);
		combineInputTables();
	}

	private void parseInputFiles(String[] fileNames) {
		/**
		 * Receives an array of input files, creates a related parser for each file
		 * type, extracts data from the file, places the extracted data in a Table, adds
		 * the table to a list for further processing.
		 * 
		 * 
		 */
		for (String fileName : fileNames) {
			InputParser inputParser = parserFactory.createParser(fileName);
			Table dataTable = inputParser.parse();
			inputTables.add(dataTable);
		}
	}

	private void combineInputTables() {
		/**
		 * Combines the extracted tables into an output table, formats the output table
		 * to be written in a CSV file and writes the CSV file to the disk.
		 */
		output.combineTables(inputTables);
		logger.log(Level.INFO, "Combined Table - Sorted View of this Table is Available in \"" + Paths.get("data" , "combined.csv") 
				+ "\": \n" + output + "\n");

		List<String[]> formattedTable = new ArrayList<>();
		if (!output.isEmpty()) {
			formattedTable = output.streamTable();
		}

		writer.write(formattedTable);

	}
}
