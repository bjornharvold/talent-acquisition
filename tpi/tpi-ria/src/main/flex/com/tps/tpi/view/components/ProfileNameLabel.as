package com.tps.tpi.view.components
{
	import com.tps.tpi.events.FetchProfileEvent;
	
	import flash.events.MouseEvent;
	
	import mx.controls.Label;

	public class ProfileNameLabel extends Label
	{
		public var personId:String;
		
		public function ProfileNameLabel()
		{
			super();
			addEventListener( MouseEvent.CLICK, openProfile );
			truncateToFit = true;
		}
		
		protected function openProfile( event:MouseEvent ):void
		{
			dispatchEvent( new FetchProfileEvent( FetchProfileEvent.FETCH_PERSON_BY_ID, personId ) );
		}
		
	}
}