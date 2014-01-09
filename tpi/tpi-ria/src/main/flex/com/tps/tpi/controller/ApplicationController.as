package com.tps.tpi.controller
{
	import com.tps.tpi.events.LoginEvent;
	import com.tps.tpi.events.MainViewIndexChangeEvent;
	import com.tps.tpi.model.presentation.ApplicationModel;
	import com.tps.tpi.model.vo.FlashParams;
	
	import org.swizframework.Swiz;
	
	
	public class ApplicationController extends BaseController
	{
		public function ApplicationController()
		{
			super();
		}
		
		[Autowire( bean="applicationModel" )]
		public var appModel:ApplicationModel;
		
		[Autowire(bean="flashParams",twoWay="true")]
		public var params:FlashParams;
			
		[Mediate( event="ApplicationStartupEvent.START_UP", properties="flashParams" )]
		public function startUpApp( flashParams:FlashParams ):void
		{
			params.contextRoot = flashParams.contextRoot;
			params.destination = flashParams.destination;
			params.host = flashParams.host;
			params.port = flashParams.port;
			
			//now that we have the flash vars...we can kick it all off.
			//yes, this couples the two events together, but...
			Swiz.dispatchEvent( new LoginEvent( LoginEvent.EVENT_LOGIN ) );
			
		}
		
		
		[Mediate( event="MainViewIndexChangeEvent.EVENT_TYPE" )]
		public function onMainIndexChange( event:MainViewIndexChangeEvent ):void
		{
			appModel.mainViewState = event.newIndex;
		}
	}
}