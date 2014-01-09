package com.tps.tpi.view.components
{
	import flash.events.Event;
	import flash.events.KeyboardEvent;
	import flash.ui.Keyboard;
	import flash.utils.clearTimeout;
	import flash.utils.setTimeout;
	
	import mx.controls.TextInput;

	[Event(name="performSearch", type="flash.events.Event")]
	
	public class QuickSearchInput extends TextInput
	{
		
		private var interval:uint = 0;
		private var lastSearch:String = "";
		
		public function QuickSearchInput()
		{
			super();
			
		}
		
		override protected function keyUpHandler(event:KeyboardEvent):void
		{
			trace( event.keyCode );
			super.keyUpHandler( event );
			if( text != lastSearch && isLegitKeyStroke( event ) )
			{
				clearTimeout( interval );
				interval = setTimeout( executeSearch, 1500 );
			}
		}
		
		private function executeSearch(  ):void
		{
			dispatchEvent( new Event("performSearch") );
			lastSearch = text;
		}
		
		private function isLegitKeyStroke( event:KeyboardEvent ):Boolean
		{
			return 	!event.ctrlKey &&
					!event.shiftKey &&
					event.keyCode != Keyboard.CONTROL &&
					event.keyCode != Keyboard.SHIFT &&
					event.keyCode != Keyboard.SHIFT &&
					event.keyCode != Keyboard.DELETE &&
					event.keyCode != Keyboard.DOWN &&
					event.keyCode != Keyboard.UP &&
					event.keyCode != Keyboard.LEFT &&
					event.keyCode != Keyboard.RIGHT &&
					event.keyCode != Keyboard.CAPS_LOCK &&
					event.keyCode != Keyboard.SPACE; 
//					&&
//					event.keyCode != Keyboard.BACKSPACE;
		}
		
	}
}