package com.tps.tpi.utils;

/**
 * Provides time keeping for code analysis.  Use this class to time the duration
 * between points in the code execution.  The duration is expressed in nanoseconds, although
 * the actual accuracy is variable between different platforms.
 * 
 * @author Bjorn Harvold
 *
 */
public class Stopwatch {
	
	private long startTime = 0L;
	private long mostRecentSpan = 0L;
	
	/**
	 * Instantiates a new Stopwatch object, with its start time set to the 
	 * current time in nanoseconds.
	 */
	public Stopwatch () {
		reset();
	}
	
	/**
	 * Resets the start time of the Stopwatch object to the current instant.
	 */
	public void reset () {
		startTime = System.nanoTime();	// Current timestamp in nanoseconds
	}
	
	/**
	 * Returns the time span, in nanoseconds, between the instant of calling this function and the
	 * last reset() call (or the instant the stopwatch was instantiated).
	 * @return The number of nanoseconds between the current time and the most recent
	 * reset() call or the time the stopwatch was instantiated.
	 */
	public long getCurrentTimespan () {
		long thisTime = System.nanoTime();
		mostRecentSpan = thisTime - startTime;
		return mostRecentSpan;
	}
	
	/**
	 * Combines the getCurrentTimespan() and reset() operations into one call, for convenience.
	 * This function takes the current time span and then resets the stopwatch.
	 * @return The current time span value.
	 */
	public long getCurrentTimespanAndReset() {
		long span = getCurrentTimespan();
		reset();
		return span;
	}
	
	/**
	 * Returns a more readable representation of the current time span, given in
	 * both nanoseconds and milliseconds.
	 * @return The current time span value, formatted.
	 */
	public String getFormattedTimespan() {
		getCurrentTimespan();
		String str = Long.toString(mostRecentSpan) + " ns (" + 
				Long.toString((mostRecentSpan / 1000000)) + " ms)";
		return str;
	}
	public String getTimeSpanNs() {
		getCurrentTimespan();
		return Long.toString(mostRecentSpan);
	}
	
	public String getTimeSpanMs() {
		getCurrentTimespan();
		return Long.toString((mostRecentSpan / 1000000));
	}

}
