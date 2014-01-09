package com.tps.tpi.events
{
	import flash.events.Event;

	public class ViewSearchResultEvent extends Event
	{
		public var searchResultIndex:int;
		
		public static const EVENT_TYPE:String = "showAParticularSearchResultEvent";
		
		public function ViewSearchResultEvent(type:String = EVENT_TYPE, searchResultIndex:int = 0 )
		{
			super(type, true, true);
			this.searchResultIndex = searchResultIndex;
		}
		
		override public function clone():Event
		{
			return new ViewSearchResultEvent( type, searchResultIndex );
		}
		
	}
}