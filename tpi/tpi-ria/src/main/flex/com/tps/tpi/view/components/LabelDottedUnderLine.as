package com.tps.tpi.view.components
{
	import flash.events.Event;
	
	import mx.controls.Label;

	public class LabelDottedUnderLine extends Label
	{
		//spacing between the dots
		private static const SPACING:int = 3;
		
		private var showDottedLineIsDirty:Boolean = false;
		private var _showDottedLine:Boolean = false;
		[Bindable("showDottedLineChanged")]
		public function get showDottedLine():Boolean
		{
			return _showDottedLine;
		}
		public function set showDottedLine( value:Boolean ):void
		{
			_showDottedLine = value;
			showDottedLineIsDirty = true;
			invalidateDisplayList();
			dispatchEvent( new Event( "showDottedLineChanged" ) );
		}
		
		
		/**
		 * teh constructor
		 */
		public function LabelDottedUnderLine()
		{
			super();
		}
		
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
		{
			super.updateDisplayList( unscaledWidth, unscaledHeight );
			if( showDottedLineIsDirty )
			{
				showDottedLineIsDirty = true;
				if( showDottedLine )
				{
					drawDottedLine( );
				}
				else
				{
					graphics.clear();
				}
			}
			
		}
		
		private function drawDottedLine( ):void
		{
			
			//each dot is 1px plus SPACING, how manydots?
			var numDots:int = textWidth / ( 1 + SPACING ) + 1;
			//how much space on each end? shift to make better looking
			var lead:int = ( width - textWidth ) / 2;
			lead = lead < 0 ? 0 : lead;
			//ok, seems this is 3px inside the Label code somewhere
			lead = 3;
			var pad:int = getStyle( "paddingLeft" );
			lead += pad;
			
			//place the dots just below the text...height of Label can excede height of text
			var bottom:int = textHeight - 1;
			 
			
			//ok, draw the dots
			graphics.clear();
			graphics.lineStyle(1, getStyle("color"), 0.5, true );
			for( var i:int = 0; i < numDots; i++ )
			{
				graphics.moveTo( lead + ( i * ( SPACING + 1 ) ), bottom );
				graphics.lineTo( lead + ( i * ( SPACING + 1 ) ) + 1, bottom );	
			}
			
			
		}
		
	}
}