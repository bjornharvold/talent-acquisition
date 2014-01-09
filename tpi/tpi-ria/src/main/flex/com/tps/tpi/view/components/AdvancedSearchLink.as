package com.tps.tpi.view.components
{
	import com.tps.tpi.events.MainViewIndexChangeEvent;
	import com.tps.tpi.model.constants.MainViewIndex;
	
	import flash.events.MouseEvent;
	
	import mx.controls.LinkButton;

	public class AdvancedSearchLink extends LinkButton
	{
		public function AdvancedSearchLink()
		{
			super();
			//watch out for when we runtime load bundles, this will be failing
			label = resourceManager.getString('resources','form.more.options');
		}
		
		override protected function clickHandler(event:MouseEvent):void
		{
			super.clickHandler( event );
			
			dispatchEvent( new MainViewIndexChangeEvent( MainViewIndex.ADVANCED_SEARCH ) ) ;
		}
	}
}