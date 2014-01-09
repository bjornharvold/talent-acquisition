package com.tps.tpi.view.events
{
	import flash.events.Event;

	public class SaveEvent extends Event
	{
		public var data:*;
		public function SaveEvent( data:* )
		{
			super( "saveChangesEvent", false, false);
			this.data = data;
		}
		
	}
}