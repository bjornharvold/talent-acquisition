package com.tps.tpi.view.renderers
{
	import mx.controls.Label;

	public class DeepPropertyItemRenderer extends Label
	{
		public var dataKey:String = "shortName";
		public var dataProperty:String = "";
		public var useLookup:Boolean = false;
		public var resourceStringKey:String = "";
		
		private var dataIsDirty:Boolean;
		
		public function DeepPropertyItemRenderer()
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
				var str:String = "";
				
				if( dataProperty == "" )
					str = data[ dataKey ].toString();
				else
					str = data[ dataProperty ][ dataKey ].toString();
				
				if( useLookup )
				{
					if( resourceStringKey == "" )
						str = resourceManager.getString("resources", str );
					else
						str = resourceManager.getString("resources", resourceStringKey + "." + str );
				}
				
				text = str;
				
			}
		}
		
	}
}