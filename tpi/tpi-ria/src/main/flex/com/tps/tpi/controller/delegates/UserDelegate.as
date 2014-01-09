package com.tps.tpi.controller.delegates
{
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class UserDelegate
	{
		
		[Autowire(bean="userService")]
		public var userService:RemoteObject;
		
		
		public function UserDelegate()
		{
		}
		
		
		public function getPersonByUsername( uid:String ):AsyncToken
		{
			return userService.getUserByUsername( uid );
		}
		

	}
}