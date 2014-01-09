package com.tps.tpi.controller
{
	import com.tps.tpi.events.LoginEvent;
	
	import flash.events.Event;
	
	import mx.controls.Alert;
	import mx.resources.ResourceManager;
	import mx.rpc.events.FaultEvent;
	import mx.utils.ObjectUtil;
	
	import org.swizframework.Swiz;
	import org.swizframework.controller.AbstractController;
	
	
	/**
	 * Base class for controllers. provides global fault_handlers and such
	 * 
	 */
	public class BaseController extends AbstractController
	{
		public function BaseController()
		{
			super();
		}
		
		/**
		 * A global fault handler
		 * TODO figure out what to do with errors
		 * 
		 * we will introspect for session timeout errors and redirect appropriately
		 */
		public function faultHandler( event:FaultEvent ):void
		{
			if( event.fault.faultString == "Access is denied" )
			{
				Alert.show( ResourceManager.getInstance().getString("resources","form.sessionTimeoutMessage"),
							ResourceManager.getInstance().getString("resources","form.sessionTimeoutTitle"),4,null, directToLogout );
				
			}
			else
			{
				trace( "A Service call failed" );
				trace( ObjectUtil.toString( event.fault) );
				trace( ObjectUtil.toString( event.fault) + "    TODO: Figure out what to do with errors!!!!" );
				Alert.show("There was an error.");
			}
		}
		
		protected function directToLogout( event:Event ):void
		{
			Swiz.dispatchEvent( new LoginEvent( LoginEvent.EVENT_LOGOUT ) );
		}

	}
}