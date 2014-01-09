

package com.tps.tpi.utils
{

import mx.formatters.DateFormatter;
import mx.resources.ResourceManager;	

/**
 * Provides re-usable utility functions for dealing with
 * date objects.
 */
public final class DateUtils
{
	/** A reusable date formatter */
	private static var dateFormatter:DateFormatter = new DateFormatter();
	
	/**
	 * Formats a date object into a long string format, such as
	 * 	<code>11/19/06 12:31 pm</code>.
	 */
	public static function formatDateTime( date:Date ):String
	{
		return format( date, ResourceManager.getInstance().getString( "resources","DATE_TIME_FORMAT") );
	}
	
	public static function formatDateYear( date:Date ):String
	{
		return format( date, ResourceManager.getInstance().getString( "resources","DATE_YEAR_FORMAT") );
	}
	
	/**
	 * Formats a date object into a short day string format, such as
	 */
	public static function formatDate( date:Date ):String
	{
		return format( date, ResourceManager.getInstance().getString( "resources","DATE_FORMAT") );
	}
	/**
	 * Formats a date object into a short day string format, such as
	 */
	public static function formatDateMonthYear( date:Date ):String
	{
		return format( date, ResourceManager.getInstance().getString( "resources","DATE_MONTH_YEAR_FORMAT") );
	}
	
	/**
	 * Formats a date object using an arbitrary format string.
	 */
	public static function format( date:Date, formatString:String ):String
	{
		dateFormatter.formatString = formatString;
		return dateFormatter.format( date );
	}
	
	/**
	 * Formats an int value representing seconds into a string format
	 * of <code>H:NN:SS</code>.
	 */
	public static function formatSeconds( seconds:int, showHours:Boolean = true ):String
	{
		var hours:int = seconds / ( 60 * 60 );
		seconds -= hours * ( 60 * 60 );
		
		var minutes:int = seconds / 60;
		seconds -= minutes * 60;
		
		var prefix:String = "";
		
		if ( showHours )
		{
			prefix = hours.toString() + ":";
		}
		
		return prefix
			+ ( ( minutes < 10 ) // leading 0?
				? "0" + minutes.toString()
				: minutes.toString() )
			+ ":"
			+ ( ( seconds < 10 ) // leading 0?
				? "0" + seconds.toString()
				: seconds.toString() );
	}
	
	
	// ----------------------------------------------------------------------
	// From Jeff Tapper

	/**
	 * Determines the number of units by which <i>code1</i> is less than <i>date2</i>.<br/>
	 * 
	 * Valid dateParts:<br/>
	 * s: Seconds<br/>
	 * n: Minutes<br/>
	 * h: Hours<br/>
	 * d: Days<br/>
	 * m: Months<br/>
	 * y: Years<br/>
	 */
	public static function dateDiff( datePart:String, date1:Date, date2:Date ):Number
	{
		return getDateDiffPartHashMap()[ datePart.toLowerCase() ]( date1, date2 );
	}
	
	/**
	 * Returns a new date object with the appropriate date/time settings<br/>
	 */
	public static function dateAdd( datePart:String, date:Date, num:Number ):Date
	{
		// get date part object;
		var dpo : Object = getDateAddPartHashMap()[datePart.toLowerCase()];
		// create new date as a copy of date passed in
		var newDate : Date = new Date(date.getFullYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes(),date.getSeconds(),date.getMilliseconds());
		// set the appropriate date part of the new date
		newDate[dpo.set](date[dpo.get]()+num);
		// return the new date
		return newDate;
	}
	
	private static var dapHashMap:Object;
	
	private static function getDateAddPartHashMap():Object
	{
		if ( dapHashMap != null ) return dapHashMap;
		
		dapHashMap = new Object();
		dapHashMap["s"] = new Object();
		dapHashMap["s"].get = "getSeconds";
		dapHashMap["s"].set = "setSeconds";
		dapHashMap["n"] = new Object();
		dapHashMap["n"].get = "getMinutes";
		dapHashMap["n"].set = "setMinutes";
		dapHashMap["h"] = new Object();
		dapHashMap["h"].get = "getHours";
		dapHashMap["h"].set = "setHours";
		dapHashMap["d"] = new Object();
		dapHashMap["d"].get = "getDate";
		dapHashMap["d"].set = "setDate";
		dapHashMap["m"] = new Object();
		dapHashMap["m"].get = "getMonth";
		dapHashMap["m"].set = "setMonth";
		dapHashMap["y"] = new Object();
		dapHashMap["y"].get = "getFullYear";
		dapHashMap["y"].set = "setFullYear";
		return dapHashMap;
	}
	
	private static var dpHashMap:Object;
	
	private static function getDateDiffPartHashMap():Object
	{
		if ( dpHashMap != null ) return dpHashMap;
		
		dpHashMap = new Object();
		dpHashMap["s"] = getSeconds;
		dpHashMap["n"] = getMinutes;
		dpHashMap["h"] = getHours;
		dpHashMap["d"] = getDays;
		dpHashMap["m"] = getMonths;
		dpHashMap["y"] = getYears;
		return dpHashMap;
	}

	/**
	 * Calculates the difference between two dates.  Returns an object with the following 
	 * properties: time, days, hours, minutes, seconds.
	 */
	public static function getDifference( date1:Date, date2:Date ):Object
	{
		var difference:Number = date2.time - date1.time;
		
		var o:Object = new Object();
		o.time = difference; 
		o.day = Math.round( difference / 86400000 );  // use round instead of floor here to fix DST boundary crossing
		var daysOffset:Number = o.days * 86400000;
		o.hours = Math.floor( ( difference - daysOffset ) / 3600000 );
		var hoursOffset:Number = o.hours * 3600000;
		o.minutes = Math.floor( ( difference - daysOffset - hoursOffset ) / 60000 );
		o.seconds = Math.floor( ( difference - daysOffset - hoursOffset - ( o.minutes * 60000 ) ) / 1000 );
		
		return o;   
	}

	private static function compareDates( date1:Date, date2:Date ):Number
	{
		// The number of milliseconds between the dates
		var difference:Number = date1.getTime() - date2.getTime();
			
		// Check to see if crossing a daylight savings time boundary
		if ( date1.timezoneOffset != date2.timezoneOffset )
		{
			// The number of minutes difference between the two different time zone
			// offset values 
			var timeZoneOffset:Number = date1.timezoneOffset - date2.timezoneOffset;
			
			// Minutes difference * 60 seconds/min * 1000 millis/second
			var millisOffset:Number = timeZoneOffset * 60 * 1000;
			
			return difference - millisOffset;
		}
		else // Not crossing DST boundary
		{
			return difference;	
		}
	}
	
	private static function getSeconds( date1:Date, date2:Date ):Number
	{
		return Math.floor( compareDates( date1, date2 ) / 1000 );
	}
	
	private static function getMinutes( date1:Date, date2:Date ):Number
	{
		return Math.floor( getSeconds( date1, date2 ) / 60 );
	}
	
	private static function getHours( date1:Date, date2:Date ):Number
	{
		return Math.floor( getMinutes( date1, date2 ) / 60 );
	}
	
	private static function getDays( date1:Date, date2:Date ):Number
	{
		return Math.floor( getHours( date1, date2 ) / 24 );
	}
	
	private static function getMonths( date1:Date, date2:Date ):Number{
		var yearDiff:Number = getYears(date1,date2);
		var monthDiff:Number = date1.getMonth() - date2.getMonth();
		if ( monthDiff < 0 )
		{
			monthDiff += 12;
		}
		if( date1.getDate() < date2.getDate() )
		{
			monthDiff -= 1;
		}
		return 12 *yearDiff + monthDiff;
	}
	
	private static function getYears(date1:Date,date2:Date):Number
	{
		return Math.floor( getDays( date1, date2 ) / 365 );
	}
	
	/**
	 * 
	 * @param dateIn
	 * @return returns a Date that is the first of the month for the incoming date
	 *  if no incoming date, use current date 
	 * 
	 */	
	public static function getFirstOfMonth( dateIn:Date = null):Date	{
		if( !dateIn )
			dateIn = new Date();
			
		return new Date(dateIn.fullYear, dateIn.month, 1, 0,0,0,0 );
		
	}
	
	
	public static function getLastOfMonth(dateIn:Date = null):Date	{
		if( !dateIn )
			dateIn = new Date();
			
		var date:Date = new Date(dateIn.fullYear, dateIn.month + 1, 1, 0,0,0,0 );
		return dateAdd('d',date,-1);
	}
	
	/**
	 * returns a Date only with all time zeroed out 
	 * @param date
	 * @return  <code>Date</code>
	 * 
	 */	
	public static function getDateOnly(date:Date):Date	{
		date.hours = 0;
		date.minutes = 0;
		date.seconds = 0;
		date.milliseconds = 0;
		return date;
	}

} // end class
} // end package