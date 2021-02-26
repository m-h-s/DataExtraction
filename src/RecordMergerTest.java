

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import au.com.bytecode.opencsv.CSVReader;

/**
 * 
 * @author Mahsa Sadi
 * @since 2021-01-27
 * 
 * 
 *        This class contains some unit tests to check the proper function of
 *        RecordMerger.
 *
 */

class RecordMergerTest {

	private void callMain(String fileName1, String fileName2) {
		/**
		 * 
		 * This helper method calls the Main class with the required arguments.
		 */
		String[] fileNames;
		fileNames = new String[2];

		fileNames[0] = fileName1;
		fileNames[1] = fileName2;

		try {
			RecordMerger.main(fileNames);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	private List<String[]> readCSVFile(String filePath) {
		/**
		 * This helper method reads a CSV file.
		 */
		List<String[]> fileContent = new ArrayList<>();

		try {
			File file = new File(filePath);
			FileInputStream fileStream = new FileInputStream(file);
			InputStreamReader fileReader = new InputStreamReader(fileStream, StandardCharsets.UTF_8);
			CSVReader csvReader = new CSVReader(fileReader);
			fileContent = csvReader.readAll();
			csvReader.close();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		return fileContent;
	}

	private boolean compare(List<String[]> expectedOutput, List<String[]> actualOutput) {
		/**
		 * This helper function compares two lists of strings.
		 */
		boolean equal = actualOutput.size() == expectedOutput.size();

		for (int i = 0; i < actualOutput.size() && equal; i++) {

			String[] actualRow = actualOutput.get(i);
			String[] expectedRow = expectedOutput.get(i);
			Arrays.sort(actualRow);
			Arrays.sort(expectedRow);

			equal = Arrays.deepEquals(actualRow, expectedRow);

		}
		return equal;
	}

	@Test
	void testEmptyInput() {

		String filePath = Paths.get("data", "combined.csv").toString();

		callMain("test1-empty.csv", "test1-emptyBody.html");
		List<String[]> actualOutput = readCSVFile(filePath);

		assertTrue(actualOutput.isEmpty());

	}

	@Test
	void testNoData() {

		String filePath = Paths.get("data", "combined.csv").toString();

		callMain("test2-emptyDataRows.csv", "test2-emptyDataRows.html");
		List<String[]> actualOutput = readCSVFile(filePath);

		assertTrue(actualOutput.isEmpty());
	}

	@Test
	void testNoHeader() {

		String filePath = Paths.get("data", "combined.csv").toString();

		callMain("test3-noHeader.csv", "test3-noHeader.html");
		List<String[]> actualOutput = readCSVFile(filePath);

		assertTrue(actualOutput.isEmpty());
	}

	@Test
	void testNoID() {

		String filePath = Paths.get("data", "combined.csv").toString();

		callMain("test4-NoID.csv", "test4-NoID.html");
		List<String[]> actualOutput = readCSVFile(filePath);

		assertTrue(actualOutput.isEmpty());
	}

	@Test
	void testCorrectEmpty() {

		String filePath = Paths.get("data", "combined.csv").toString();
		String expectedfilePath = Paths.get("data", "test5-correct.csv").toString();

		callMain("test5-correct.csv", "test1-emptyBody.html");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(filePath);

		boolean equal = compare(expectedOutput, actualOutput);

		assertTrue(equal);
	}

	@Test
	void testCorrectNoData() {

		String expectedfilePath = Paths.get("data", "test5-correct.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test5-correct.csv", "test2-emptyDataRows.html");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

	@Test
	void testCorrectNoID() {

		String expectedfilePath = Paths.get("data", "test5-correct.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test5-correct.csv", "test4-NoID.html");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

	@Test
	void testsimpleMerge() {

		String expectedfilePath = Paths.get("data", "test6-out.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test6-simpleMerge.csv", "test6-simpleMerge.html");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

	@Test
	void testMergeAndExtend() {

		String expectedfilePath = Paths.get("data", "test1-out.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("first.html", "second.csv");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

	@Test
	void testExtend() {

		String expectedfilePath = Paths.get("data", "test7-out.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test7-extend.html", "test7-extend.csv");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

	@Test
	void testHeaderSizeMisMatch() {

		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test8-headerSizeDataSizeMismatch.csv", "test8-headerSizeDataSizeMismatch.html");
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(actualOutput.isEmpty());
	}

	@Test
	void testNonEnglish() {

		String expectedfilePath = Paths.get("data", "test9-out.csv").toString();
		String actualfilePath = Paths.get("data", "combined.csv").toString();

		callMain("test9-nonEnglish.html", "test9-nonEnglish.csv");
		List<String[]> expectedOutput = readCSVFile(expectedfilePath);
		List<String[]> actualOutput = readCSVFile(actualfilePath);

		assertTrue(compare(expectedOutput, actualOutput));
	}

}
