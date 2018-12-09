# assetcontrolloader
Data transformation loader

Java project that helps to do data load and transform with mapping rules.

Program takes 3 input arguments: 
- /path/to/columnmapping.file 
- /path/to/rowmapping.file 
- /path/to/data.file

Algorythm of program:
1) Proceed column mapping and row mapping files to build mapping rules.
2) Go throught data-file and fill in he buffer with data rows and and when buffer takes N size,
launches proceed job with ThreadPoolExecutor, sizes of Processor Numbers (by default).
3) In the job method, do transformation and call output method with result data.

Usage:
1) Make changes in Constants.java file to change values:
    /**
     * Value separator
     */
    public final static String separator = "\t";
    /**
     * Records to proceed in one batch job
     */
    public static final int BUFFER_RECORD_COUNT = 3;
    /**
     * Number of threads
     */
    public static final int NUM_THREADS = Runtime.getRuntime().availableProcessors() + 2;
    /**
     * Character set of data
     */
    public static final String CharacterSet = "UTF-8";


2) Make a jar file with maven:
mvn package

3) Launch main class with arguments: 
Example:
java -cp assetcontrol-loader-1.0-SNAPSHOT.jar com.ac.loader.DataTranslator file-colmapping.csv file-rowmapping.csv file-data.csv

