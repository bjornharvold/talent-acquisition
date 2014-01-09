package com.tps.tpi.view.events
{
	import flash.events.Event;

	public class CancelEditEvent extends Event
	{
		public function CancelEditEvent( )
		{
			super("cancelEdit", false, false);
		}
		
	}
}