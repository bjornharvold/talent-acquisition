package com.tps.tpi.view.components
{
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Alert;
	
	public class ExternalLinkLabel extends VerifiedDataLabel
	{
		public var url:String;
		
		public function ExternalLinkLabel()
		{
			super();
			buttonMode = true;
			useHandCursor = true;
			
			addEventListener( MouseEvent.CLICK, onClick );
		}
		
		protected function onClick( event:MouseEvent ):void
		{
			if( url )
			{
				navigateToURL( new URLRequest( url ), "_blank" ) ;
			}
			
		}
	}
}