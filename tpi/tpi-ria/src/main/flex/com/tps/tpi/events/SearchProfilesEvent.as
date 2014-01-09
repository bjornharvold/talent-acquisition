package com.tps.tpi.events
{
	import flash.events.Event;

	public class SearchProfilesEvent extends Event
	{
		public function SearchProfilesEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}