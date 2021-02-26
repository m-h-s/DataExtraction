cd "%~dp0"

java -cp cantest.jar;lib\* RecordMerger  first.html   second.csv %*

@pause

