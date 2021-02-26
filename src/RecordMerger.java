import mergers.Controller;

/**
 * 
 * @author Mahsa Sadi
 * @since 2021-01-27
 * 
 * 
 *        This program receives an array of file names containing data tables,
 *        reads the content of each file, merges all the data into one table,
 *        and write the output table to the file "data/combined.csv" or
 *        "data\combined.csv". The structure of data in the input file: The
 *        first row contains the data header. Subsequent rows contain the data
 *        rows.
 *
 */
public class RecordMerger {

	public static final String FILENAME_COMBINED = "combined.csv";

	/**
	 * Entry point of this test.
	 *
	 * @param args command line arguments: first.html and second.csv. args contains
	 *             the file names with suffix. File paths are assumed to be
	 *             "data/fileName" or data\fileName".
	 * 
	 * @throws Exception bad things had happened.
	 */

	public static void main(final String[] args) throws Exception {

		if (args.length == 0) {
			System.err.println("Usage: java RecordMerger file1 [ file2 [...] ]");
			System.exit(1);
		}

		// your code starts here.
		Controller controller = new Controller();
		controller.parseAndCombineInput(args);
	}

}
