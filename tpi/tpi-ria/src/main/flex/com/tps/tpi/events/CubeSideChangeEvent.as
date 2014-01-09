package com.tps.tpi.events
{
	import flash.events.Event;

	public class CubeSideChangeEvent extends Event
	{
		public static const CHANGE_SELECTED:String = "cubeSideChangedEvent";
		/* use this event type to change ALL cube sides, not just selected one */
		public static const CHANGE_GLOBAL:String = "cubeSideChangedGloballyEvent";
		
		public var newIndex:int;
		
		public function CubeSideChangeEvent( type:String = CHANGE_SELECTED, newIndex:int = 0 )
		{
			super(type, true, false);
			this.newIndex = newIndex;
		}
		
		override public function clone():Event
		{
			return new CubeSideChangeEvent( type, newIndex );
		}
		
	}
}