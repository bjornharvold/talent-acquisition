package com.tps.tpi.events
{
	import flash.events.Event;

	public class DisplayProfileEvent extends Event
	{
		public static const SHOW_PROFILE:String = "show";
		
		public var personId:String;
		
		public function DisplayProfileEvent(type:String = SHOW_PROFILE, personId:String = null )
		{
			super(type, true, true);
			this.personId = personId;
		}
		
		override public function clone():Event
		{
			return new DisplayProfileEvent( type, personId );
		}
	}
}