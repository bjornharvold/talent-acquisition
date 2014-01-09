package com.tps.tpi.events
{
	import flash.events.Event;

	/**
	 * 
	 * This event is used for quick searching
	 * it probably could be removed and use <code>SearchEvent</code>
	 * but this modified search API in server land was chanegd after all the code
	 * was in place.  Ichose to keep the interface working as is and use the 
	 * controller, see <code>SearchController</code>, to handle the change
	 * and leave the UI events as they are
	 * 
	 */
	public class QuickSearchEvent extends Event
	{
		public var keywords:String;
		/**
		 * the search type is keyword or name 
		 * we default to keyword
		 */
		public var searchType:int = KEYWORD_SEARCH;
		
		
		public static const QUICK_SEARCH_COUNT:String = "quickSearchCountEvent";
		public static const QUICK_SEARCH:String = "quickSearchEvent";
		
		public static const KEYWORD_SEARCH:int = 0;
		public static const NAME_SEARCH:int = 1;
		
		public function QuickSearchEvent(type:String, keywords:String = null, searchType:int = KEYWORD_SEARCH )
		{
			super(type,true, true );
			this.keywords = keywords;
			this.searchType = searchType;
		}
		
		override public function clone():Event
		{
			return new QuickSearchEvent( type,keywords, searchType );
		}
		
	}
}