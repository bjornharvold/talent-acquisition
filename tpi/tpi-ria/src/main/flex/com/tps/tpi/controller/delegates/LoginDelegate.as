package com.tps.tpi.controller.delegates
{
	
	import com.tps.tpi.model.vo.FlashParams;
	
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.http.mxml.HTTPService;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class LoginDelegate
	{
		
		[Autowire(bean="loginService")]
		public var service:HTTPService;

		[Autowire(bean="userService")]
		public var userService:RemoteObject;
		
		[Autowire]
		public var flashVars:FlashParams;
		
		public function LoginDelegate()
		{
		}

		public function validateLogin( username:String, password:String ):AsyncToken
		{
			var params:Object = new Object();
			params.j_username = username;
			params.j_password = password;
			service.method = "POST";
			service.showBusyCursor = true;
			service.url = "j_spring_security_check";
			return service.send( params );
		}
		
		public function getPrincipal():AsyncToken
		{
			return userService.getPrincipal();
			
		}
		
		public function logout( ):void
		{
			navigateToURL( new URLRequest( flashVars.contextRoot + '/j_spring_security_logout' ), '_self' );
			
		}
	}
}