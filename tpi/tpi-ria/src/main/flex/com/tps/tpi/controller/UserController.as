package com.tps.tpi.controller
{
	import com.tps.tpi.controller.delegates.UserDelegate;
	import com.tps.tpi.events.UserEvent;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class UserController extends BaseController
	{
		
		[Autowire]
		public var delegate:UserDelegate;
		
		
		
		public function UserController()
		{
			super();
		}
		
		
		
		/**
		 * 
		 * we need two steps here....go go gadget chaincommand..NOT!, grr
		 * 
		 */
		[Mediate(event="UserEvent.GET_BY_USERNAME" )]
		public function getByUserName( event:UserEvent ):void
		{
			var res:Responder = new Responder( onGetUser,  faultHandler );
			var token:AsyncToken = delegate.getPersonByUsername( event.key );
			token.addResponder( res );
		}
		
		public function onGetUser( event:ResultEvent ):void
		{
			
		}
		
	}
}