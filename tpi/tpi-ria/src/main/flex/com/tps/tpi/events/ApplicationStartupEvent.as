package com.tps.tpi.events
{
	import com.tps.tpi.model.vo.FlashParams;
	
	import flash.events.Event;

	public class ApplicationStartupEvent extends Event
	{
		/**
		 * event used for app startup to gather any pre-config info
		 * such as flashvars needed to continue
		 */
		public static const START_UP:String = "com.tps.tpi.events.StartMeUp";
		
		public var flashParams:FlashParams
		
		public function ApplicationStartupEvent( type:String, flashParams:FlashParams )
		{
			super( type, true, true);
			this.flashParams = flashParams;
		}
		
		override public function clone():Event
		{
			return new ApplicationStartupEvent( type, flashParams  );
		}
		
	}
}