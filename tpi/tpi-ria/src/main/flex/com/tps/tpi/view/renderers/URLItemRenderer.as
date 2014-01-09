package com.tps.tpi.view.renderers
{
	import flash.events.MouseEvent;
	import flash.net.URLRequest;
	import flash.net.navigateToURL;
	
	import mx.controls.Label;

	public class URLItemRenderer extends Label
	{
		public var urlField:String = "longName";
		
		public var dataField:String = "code";
		
		private var dataIsDirty:Boolean;
		
		public function URLItemRenderer()
		{
			super();
			useHandCursor = true;
			buttonMode = true;
			addEventListener(MouseEvent.CLICK, onClick );
			setStyle("color","0x0000ff");
			setStyle("paddingLeft",5);
		}
		
		private function onClick( event:MouseEvent ):void
		{
			try 
			{
				navigateToURL( new URLRequest( data[ urlField ] ), "_blank" );
				
			}
			catch( error:Error )
			{
				trace(" NO URL PROPERTY KNOWN ");
			}
			
		}
		
		override public function set data(value:Object):void
		{
			super.data = value;
			if( value )
			{
				dataIsDirty = true;
				invalidateProperties();
			}
		} 
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if( dataIsDirty )
			{
				dataIsDirty = false;
				text = data[ dataField ];
			}
		}
		
		
	}
}