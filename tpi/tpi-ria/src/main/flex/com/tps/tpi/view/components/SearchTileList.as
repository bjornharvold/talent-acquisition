package com.tps.tpi.view.components
{
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.filters.DropShadowFilter;
	import flash.filters.GlowFilter;
	
	import mx.controls.TileList;
	import mx.controls.listClasses.IListItemRenderer;

	public class SearchTileList extends TileList
	{
		public function SearchTileList()
		{
			super();
		}
		
		override protected function  drawSelectionIndicator(indicator:Sprite, x:Number, 
					y:Number, width:Number, height:Number, 
					color:uint, itemRenderer:IListItemRenderer):void
	    {
	    	var f:GlowFilter = new GlowFilter(0x006DB0,.75,11,11,3,1,false,true);
	    	//var f:DropShadowFilter = new DropShadowFilter( 5, 45,0x006DB0,.75,4,4,1,1,false,true,false );
	       	indicator.filters = [ f ];
	        var g:Graphics = indicator.graphics;
	        g.clear();
	        g.beginFill( 0xFFFFFF );
            g.lineStyle( 0 );
            g.drawRect( 5 , 7 , width - 10, height - 13  );
            //g.drawRect( 3 , 4 , width - 8, height - 9  );
            g.endFill(); 
	        
	        indicator.x = x;
	        indicator.y = y;
	    }

	}
}