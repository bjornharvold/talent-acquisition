package com.tps.tpi.events
{
	import flash.events.Event;

	public class MainViewIndexChangeEvent extends Event
	{
		public static const EVENT_TYPE:String = "mainViewIndexChangedEvent";
		
		public var newIndex:int;
		
		public function MainViewIndexChangeEvent( newIndex:int )
		{
			super(EVENT_TYPE, true, true);
			this.newIndex = newIndex;
		}
		
		
		override public function clone():Event
		{
			return new MainViewIndexChangeEvent( newIndex );
		}
	}
}