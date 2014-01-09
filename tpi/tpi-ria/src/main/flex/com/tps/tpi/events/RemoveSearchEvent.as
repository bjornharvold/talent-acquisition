package com.tps.tpi.events
{
	import flash.events.Event;

	public class RemoveSearchEvent extends Event
	{
		public static const EVENT_TYPE:String = "deleteASearchResultEvent";
		
		public var index:int;
		
		public function RemoveSearchEvent(type:String = EVENT_TYPE, index:int = -1 )
		{
			super(type, true, true);
			this.index = index;
		}
		
		override public function clone():Event
		{
			return new RemoveSearchEvent( type, index );
		}
		
	}
}