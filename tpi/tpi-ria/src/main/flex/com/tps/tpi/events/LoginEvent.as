package com.tps.tpi.events
{
	import com.tps.tpi.model.vo.Login;
	import flash.events.Event;

	public class LoginEvent extends Event
	{
		
		public static const EVENT_AUTHENTICATE:String = "authenticateTheUserEvent";
		public static const EVENT_LOGOUT:String = "logOutTheUserEvent";
		/**
		 * event to fetch the user...seemed like a good idea to make this part
		 * of LOGIN.  EVENT_ATUTHENTICAT is used first if need be to auth,
		 * then this gets the user and does the consume the roles and such
		 */
		public static const EVENT_LOGIN:String = "logInTheUserEvent";
		
		
		public var login:Login;
		
		public function LoginEvent(eventType:String=EVENT_AUTHENTICATE, login:Login=null)
		{
			super(eventType, true, true  );
			this.login = login;
		}
		
		
		override public function clone():Event
		{
			
			return new LoginEvent( this.type, this.login );
		}
	}
}