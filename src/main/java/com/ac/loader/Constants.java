package com.ac.loader;

/**
 * Constants for the application
 */
public class Constants {

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
}
