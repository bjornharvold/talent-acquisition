package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.HistoryDto;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	[Bindable]
	public class PositionsAndRolesModel extends EventDispatcher implements IPositionsAndRolesModel
	{
		public function PositionsAndRolesModel()
		{
		}
		
		
		/**
		 * the current professionalBio for the user
		 */
		private var _positionsAndRoles:HistoryDto;
		[Bindable(  event="positionsAndRolesChanged"  )]
		public function get positionsAndRoles():HistoryDto
		{
			return _positionsAndRoles;
		}
		public function set positionsAndRoles( value:HistoryDto ):void
		{
			if( value )
			{
				_positionsAndRoles = value;
				dispatchEvent( new Event( "positionsAndRolesChanged" ) );
			}
		}

	}
}