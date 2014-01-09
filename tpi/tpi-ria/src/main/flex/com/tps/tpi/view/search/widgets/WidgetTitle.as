package com.tps.tpi.view.search.widgets
{
	import com.tps.tpi.model.objects.enums.SearchComponentTypeCd;
	
	import mx.containers.Canvas;
	import mx.controls.Image;
	import mx.controls.Label;

	public class WidgetTitle extends Canvas
	{
		
		public var type:SearchComponentTypeCd;
		
		private var titleIsDirty:Boolean;
		private var _title:String;
		public function set title( value:String ):void
		{
			_title = value;
			titleIsDirty = true;
			invalidateProperties();
		}
		
		private var theLabel:Label;
		private var theCloser:Image;
		
		public function WidgetTitle()
		{
			super();
			mouseChildren=false;
			
			
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
			
			theLabel = new Label();
			theLabel.setStyle( "left", 10 );
			theLabel.setStyle( "verticalCenter", 2 );
			theLabel.setStyle( "fontSize", 14 );
			addChild( theLabel );
			
			
//			theCloser = new Image();
//			theCloser.source = EmbeddedAssets.ICON_CLOSEX_WHITE;
//			theCloser.setStyle( "right","10");	
//			theCloser.setStyle( "verticalCenter","0");	
//			addChild( theCloser );
		}
		
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if( titleIsDirty )
			{
				titleIsDirty;
				theLabel.text = _title;
			}
		} 
		
	}
}