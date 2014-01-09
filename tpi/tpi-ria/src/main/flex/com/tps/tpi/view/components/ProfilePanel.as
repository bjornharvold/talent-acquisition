package com.tps.tpi.view.components
{
	import mx.containers.Box;
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.UIComponent;

	[DefaultProperty("content")]
	
	public class ProfilePanel extends Canvas
	{
		public function ProfilePanel()
		{
			super();
		}
		
		private var labelIsDirty:Boolean;
		private var labelText:Label;
		private var container:Box;
		private static const PADDING:int = 8;
		
		[ArrayElementType("mx.core.UIComponent")]
		public var content:Array;
		
		public var direction:String = "Vertical";

		override protected function createChildren():void
		{
			
			super.createChildren();
			
			
			labelText = new Label();
			addChild( labelText );
			labelText.setStyle( "paddingLeft",0);
			labelText.setStyle( "paddingBottom",0);
			labelText.setStyle( "fontSize",10);
			labelText.setStyle( "fontColor", 0x333333 );
			container = new Box();
			container.direction = this.direction;
			
			if( getStyle( "dropShadowEnabled" ) )
			{
				container.styleName = "dropShadowContainer";
			}
			else
			{
				container.styleName = "solidBorderContainer";
			}
			container.setStyle( "paddingLeft", PADDING );
			container.setStyle( "paddingRight", PADDING );
			container.setStyle( "paddingTop", PADDING );
			container.setStyle( "paddingBottom", PADDING );
			container.setStyle( "verticalGap", 0 );
			container.setStyle( "horizontalGap", 0 );
			container.horizontalScrollPolicy = "off";
			container.verticalScrollPolicy = "auto";
			
			addChild( container );	
			
			//play a reparetning game to make sure all things are added in their place
			for each( var u:UIComponent in getChildren() )
			{
				if( u != labelText && u != container )
					container.addChild( u );
			}
			
			
			
		}
		
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList( unscaledWidth, unscaledHeight );
			
			labelText.move( -3, 0 );
			container.move( 0, 18 -1 );
			container.width = this.unscaledWidth;
			container.height = this.unscaledHeight - 18;	
		}
		
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if( labelIsDirty )
			{
				labelIsDirty = false;
				labelText.text = label;
			}	
		}
		
		
		override public function set label(value:String):void
		{
			super.label = value;
			labelIsDirty = true;
			invalidateProperties();			
			
		}
		
	}
}