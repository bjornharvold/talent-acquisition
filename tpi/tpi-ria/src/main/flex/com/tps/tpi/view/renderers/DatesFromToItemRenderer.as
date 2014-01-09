package com.tps.tpi.view.renderers
{
	import com.tps.tpi.utils.DateUtils;
	
	import mx.controls.Label;

	public class DatesFromToItemRenderer extends Label
	{
		public var fromDateKey:String = "from";
		public var toDateKey:String = "to";
		private var dataIsDirty:Boolean;
		
		
		public function DatesFromToItemRenderer()
		{
			super();
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
				try
				{
					var txt:String;
					txt = DateUtils.formatDateMonthYear( data[fromDateKey] ) + " - ";
					if( data[toDateKey] == null )
						txt += "current";
					else
					 	txt += DateUtils.formatDateMonthYear( data[toDateKey] );
					 
					text = txt;
				}
				catch( error:Error )
				{
				}
			}
		}
		
	}
}