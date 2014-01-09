package com.tps.tpi.events
{
	import flash.events.Event;
	
	import mx.rpc.Responder;

	public class UserEvent extends CallBackEvent
	{
		
		public static const GET_BY_USERNAME:String = "getAUserFromSErverByUserNameEvent";
		public static const GET_BY_USERID:String = "getAUserFromSErverByUserIDEvent";
		
		/**
		 * use to pass username, userid, etc
		 */
		public var key:String;
		
		public function UserEvent(type:String = GET_BY_USERNAME, key:String = null, responder:Responder = null )
		{
			super(type, true, true, responder);
			this.key = key;
		}
		
		override public function clone():Event
		{
			return new UserEvent( type, key, responder );
		}
		
	}
}