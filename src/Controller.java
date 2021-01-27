import java.util.ArrayList;
import java.util.List;

import dataOrganizer.Table;
import inputReaders.InputParser;
import inputReaders.ParserFactory;
import outputWriters.OutputWriter;

public class Controller {

	private ParserFactory parserFactory;
	private List<Table> inputTables;
	private Table output;
	OutputWriter writer;

	public Controller() {
		parserFactory = new ParserFactory();
		inputTables = new ArrayList<>();
		output = new Table();
		writer = new OutputWriter();
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
		
		System.out.println(output);
		
		List<String[]> formattedTable = output.streamTable();
		writer.write(formattedTable);
	}
}
