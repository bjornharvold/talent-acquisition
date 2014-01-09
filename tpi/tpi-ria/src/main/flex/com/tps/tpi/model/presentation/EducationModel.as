package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.EducationDto;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;

	[Bindable]
	public class EducationModel extends EventDispatcher implements IEducationModel
	{
		public function EducationModel(target:IEventDispatcher=null)
		{
			super(target);
		}
		
		/**
		 * the current professionalBio for the user
		 */
		private var _education:EducationDto;
		[Bindable(  event="educationChanged"  )]
		public function get education():EducationDto
		{
			return _education;
		}
		public function set education( value:EducationDto ):void
		{
			if( value )
			{
				_education = value;
				dispatchEvent( new Event( "educationChanged" ) );
			}
		}
		
	}
}