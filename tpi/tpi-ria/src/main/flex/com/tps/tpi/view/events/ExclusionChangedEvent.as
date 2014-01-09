package com.tps.tpi.view.events
{
	import flash.events.Event;

	public class ExclusionChangedEvent extends Event
	{
		public static const EVENT_TYPE:String = "exclusionChanged";
		
		public var value:Boolean;
		
		public function ExclusionChangedEvent( value:Boolean )
		{
			super(EVENT_TYPE, false, false);
			this.value = value;
		}
		
	}
}