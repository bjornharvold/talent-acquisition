package com.tps.tpi.events
{
	import com.tps.tpi.model.objects.dto.SearchDto;
	
	import flash.events.Event;
	
	public class SearchEvent extends Event
	{
		public var search:SearchDto;
		
		public static const SEARCH_COUNT:String = "theBigSearchCountEvent";
		public static const SEARCH:String = "theBigSearchEvent";
		
		
		public function SearchEvent(type:String = SEARCH, search:SearchDto  = null)
		{
			super(type,true, true );
			this.search = search;
		}
	}
}