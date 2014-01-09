package com.tps.tpi.view.events
{
	import flash.events.Event;

	/**
	 * This event is used to tell everyone we need to change modes
	 */
	public class ModeChangeEvent extends Event
	{
		
		public function ModeChangeEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false)
		{
			super(type, bubbles, cancelable);
		}
		
	}
}