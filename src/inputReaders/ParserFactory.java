package inputReaders;

import java.util.Objects;

/**
 * 
 * ParserFactory is responsible for creating different types of parsers at
 * run-time.
 *
 */

public class ParserFactory {

	public InputParser createParser(String fileName) {

		/**
		 * New checks should be added in case of adding new types of parsers.
		 */
		
		if (fileName.matches(".*\\.html$|.*\\.htm$")) {
			return new HTMLParser(fileName);

		} else if (fileName.matches(".*\\.csv$")) {
			return new CSVParser(fileName);
		} else {
			System.out.println("The Input file type " + fileName + " is not supported yet.");
			return null;
		}
		
		
		
		
			
	}

}
