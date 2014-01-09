package com.tps.tpi.events
{
	import flash.events.Event;

	//TODO look at doing this better and removing this
	//its used with Swiz framework having registered a mediation in a view
	//I'd rather not do such a thing
	public class QuickSearchCountResults extends Event
	{
		public static const EVENT_TYPE:String = "quickSearchCountResultsAreIn";
		
		public function QuickSearchCountResults( )
		{
			super(EVENT_TYPE, true, true);
		}
		
	}
}