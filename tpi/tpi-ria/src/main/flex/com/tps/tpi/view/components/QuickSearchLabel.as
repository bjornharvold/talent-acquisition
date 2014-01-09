package com.tps.tpi.view.components
{
	import com.tps.tpi.events.QuickSearchEvent;
	
	import flash.events.MouseEvent;

	public class QuickSearchLabel extends VerifiedDataLabel
	{
		public function QuickSearchLabel()
		{
			super();
			buttonMode = true;
			useHandCursor = true;
			
			addEventListener( MouseEvent.CLICK, onClick );
		}
		
		protected function onClick( event:MouseEvent ):void
		{
			var quickSearchEvent:QuickSearchEvent = new QuickSearchEvent( QuickSearchEvent.QUICK_SEARCH, text );
			dispatchEvent( quickSearchEvent );
			
		}
		
	}
}