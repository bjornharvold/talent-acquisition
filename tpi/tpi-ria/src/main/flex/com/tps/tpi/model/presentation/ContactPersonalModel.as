package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.PersonDto;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	
	public class ContactPersonalModel extends EventDispatcher implements IContactPersonalModel
	{
		public function ContactPersonalModel()
		{
		}
		
		/**
		 * the current professionalBio for the user
		 */
		private var _person:PersonDto;
		[Bindable(  event="personChanged"  )]
		public function get person():PersonDto
		{
			return _person;
		}
		public function set person( value:PersonDto ):void
		{
			if( value )
			{
				_person = value;
				dispatchEvent( new Event( "personChanged" ) );
			}
		}
		

	}
}