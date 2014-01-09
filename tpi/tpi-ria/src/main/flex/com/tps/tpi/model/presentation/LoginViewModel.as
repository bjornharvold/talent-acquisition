package com.tps.tpi.model.presentation
{
	import com.tps.tpi.events.LoginEvent;
	import com.tps.tpi.model.vo.Login;
	import com.tps.tpi.model.objects.dto.PrincipalData;
	
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	public class LoginViewModel extends EventDispatcher implements ILoginViewModel
	{
		
		[Bindable]
		public var loginErrorMessageIsVisible:Boolean = true;
		
		
		// dispatcher that will be set from constructor (via Prototype tag in Beans.mxml),
		// allowing this non-view class to dispatch bubbling events
		private var dispatcher:IEventDispatcher;
		
		public function LoginViewModel( dispatcher:IEventDispatcher )
		{
			this.dispatcher = dispatcher;
		}
		
		public function login( uid:String, pwd:String ):void
		{
			var login:Login = new Login();
			login.username = uid;
			login.password = pwd;
			
			var le:LoginEvent = new LoginEvent();
			le.login = login;
			dispatcher.dispatchEvent( le );

		}
		
		
		

	}
}