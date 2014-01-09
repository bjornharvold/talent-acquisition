package com.tps.tpi.events
{
	import flash.events.Event;

	public class AddNewSearchEvent extends Event
	{
		public static const EVENT_TYPE:String = "addANewBlankSearchEvent";
		
		public function AddNewSearchEvent(type:String = EVENT_TYPE )
		{
			super(type, true, true);
		}
		
		override public function clone():Event
		{
			return new AddNewSearchEvent( type );
		}
	}
}