package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.PrincipalData;
	
	public interface ILoginViewModel
	{
		function login( uid:String, pwd:String ):void;
		
		//these were/are needed for implementing the login in Flex
		//currently we are using FORM based HTML page though
//		function get loginErrorMessageIsVisible():Boolean;
//		function set loginErrorMessageIsVisible( value:Boolean):void;

		
	}
}