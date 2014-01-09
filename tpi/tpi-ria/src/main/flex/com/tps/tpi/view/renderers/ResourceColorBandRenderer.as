package com.tps.tpi.view.renderers
{
	import mx.containers.Canvas;
	import mx.controls.Label;

	public class ResourceColorBandRenderer extends Canvas
	{
		private var dataIsDirty:Boolean;
		
		public function ResourceColorBandRenderer()
		{
			super();
		}
		
		override protected function createChildren():void
		{
			super.createChildren();
		}
		
		override public function set data(value:Object):void
		{
			super.data = value;
			dataIsDirty = true;
			invalidateProperties();
		}
		
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if( dataIsDirty )
			{
				dataIsDirty = false;
				switch( data.employmentType )
				{
					case "CONSULTANT": styleName = "resourceColorBandRendererConsultant"; break;
					case "CONTRACTOR": styleName = "resourceColorBandRendererConsultant"; break;
					case "GTE": styleName = "resourceColorBandRendererGTE"; break;
					case "VENDOR": styleName = "resourceColorBandRendererVendor"; break;
					default : styleName = "resourceColorBandRendererOther"; break;
				}
			}
		}
		
	}
}