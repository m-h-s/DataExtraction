package outputWriters;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;

/**
 * 
 * OutputWriter is responsible for writing data to a CSV file. The name of the
 * output file is "combined.csv". The file address is "/data/combined.csv".
 *
 */

public class OutputWriter {

	final String fileName = "combined.csv";
	String filePath;
	FileOutputStream outputFile;
	OutputStreamWriter outputWriter;
	CSVWriter CSVWriter;

	public OutputWriter() {
		/**
		 * The file address is "/data/combined.csv", or "data\combined.csv".
		 */

		filePath = Paths.get("data", fileName).toString();
	}

	public void openFile() {

		/**
		 * Opens the output file and set the encoding for writing to the file to UTF-8.
		 */
		try {
			outputFile = new FileOutputStream(filePath);
			outputWriter = new OutputStreamWriter(outputFile, StandardCharsets.UTF_8);
			CSVWriter = new CSVWriter(outputWriter);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void write(List<String[]> dataStream) {

		/**
		 * Receives and writes the data all at once to the output file.
		 */
		openFile();

		try {
			CSVWriter.writeAll(dataStream);
			CSVWriter.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
