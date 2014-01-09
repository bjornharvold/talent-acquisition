package com.tps.tpi.controller
{
	import com.tps.tpi.controller.delegates.LoginDelegate;
	import com.tps.tpi.controller.delegates.ProfileDelegate;
	import com.tps.tpi.events.LoginEvent;
	import com.tps.tpi.model.objects.dto.PersonDto;
	import com.tps.tpi.model.objects.dto.PrincipalData;
	import com.tps.tpi.model.presentation.ApplicationModel;
	import com.tps.tpi.model.presentation.IContactPersonalModel;
	import com.tps.tpi.model.presentation.ProfileModel;
	import com.tps.tpi.model.vo.Login;
	
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class LoginController extends BaseController
	{
		
		//
		// instances
		[Autowire]
		public var appModel:ApplicationModel;
		
		[Autowire]
		public var delegate:LoginDelegate;

		[Autowire]
		public var profileDelegate:ProfileDelegate;
		
		[Autowire(bean="personalProfileModel")]
		public var currentUsersProfile:ProfileModel;
		
		public function LoginController()
		{
			super();
		}
		
		// again note this is for Flex based login
		[Mediate(event="LoginEvent.EVENT_AUTHENTICATE", properties="login" )]
		public function validateLogin( login:Login ):void
		{
			var res:Responder = new Responder( onValidateLogin,  faultHandler );
			var token:AsyncToken = delegate.validateLogin( login.username, login.password );
			token.addResponder( res );
		}
		
		public function onValidateLogin( event:ResultEvent ):void
		{
			//loginModel.loginErrorMessageIsVisible = false;
			//Alert.show('login good ' + event.message);
		}
		
		/**
		 * 
		 * we need two steps here....go go gadget chaincommand..NOT!, grr
		 * 
		 */
		[Mediate(event="LoginEvent.EVENT_LOGIN" )]
		public function login( event:LoginEvent ):void
		{
			var res:Responder = new Responder( onPrincipalResult,  faultHandler );
			var token:AsyncToken = delegate.getPrincipal();
			token.addResponder( res );
		}
		
		
		public function onPrincipalResult( event:ResultEvent ):void
		{
			if( event.result is PrincipalData)
			{
				trace( "getPrincipal success with userId = " + event.result.username );
				appModel.principalData = event.result as PrincipalData;
				var res:Responder = new Responder( onGetPersonResult,  faultHandler );
				var token:AsyncToken = profileDelegate.getPersonByUsername( appModel.principalData.username );
				token.addResponder( res );
			}
			else 
			{
				Alert.show("FIGURE OUT WHAT TO DO HERE!!!     User logged in but does not exist in DB");
			}
		}
		
		public function onGetPersonResult( event:ResultEvent ):void
		{
			trace( "getPersonByUsername success " );
			appModel.currentPerson = event.result as PersonDto;
			currentUsersProfile.contactPersonalModel.person = event.result as PersonDto;
		}
		
		
		[Mediate(event="LoginEvent.EVENT_LOGOUT")]
		public function logout():void
		{
			delegate.logout();
		}
		

	}
}