package com.tps.tpi.events
{
	import flash.events.Event;
	
	import mx.rpc.Responder;

	public class CallBackEvent extends Event
	{
		
		/**
		 * This method will be used by various views, thus we need to
		 * implement some callback mechanism
		 */
		public var responder:Responder;
		
		public function CallBackEvent(type:String, bubbles:Boolean=false, cancelable:Boolean=false, responder:Responder = null )
		{
			super(type, bubbles, cancelable);
			this.responder = responder;
		}
		
		override public function clone():Event
		{
			return new CallBackEvent( type, bubbles, cancelable, responder );
		}
		
	}
}