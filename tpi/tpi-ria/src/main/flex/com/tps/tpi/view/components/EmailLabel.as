package com.tps.tpi.view.components
{
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Label;

	public class EmailLabel extends Label
	{
		public function EmailLabel()
		{
			super();
			buttonMode = true;
			useHandCursor = true;
			
			addEventListener( MouseEvent.CLICK, onClick );
		}
		
		/**
		 * we shall just do our thing here
		 * simple thing to do, no need ofr event-controller
		 */
		private function onClick( event:MouseEvent):void
		{
			if( !event.shiftKey )
				navigateToURL( new URLRequest( 'mailto:' + text ) , "_self" );	
		}
		
	}
}