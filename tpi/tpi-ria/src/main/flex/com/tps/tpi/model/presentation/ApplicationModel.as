package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.constants.MainViewIndex;
	import com.tps.tpi.model.objects.dto.PersonDto;
	import com.tps.tpi.model.objects.dto.PrincipalData;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	
	
	/**
	 * 
	 * the application wide entity
	 * used only for things needing to be application wide
	 */
	[Bindable]
	public class ApplicationModel
	{
		 
		public function ApplicationModel()
		{
			super();
		}

		
		private var _mainViewState:int = MainViewIndex.DASHBOARD;
		public function get mainViewState():int
		{
			return _mainViewState;
		}
		public function set mainViewState( value:int ):void
		{
			_mainViewState = value;
		}

		/**
		 * the current prinicipal data
		 * this is retrieved from the session to get user anme and roles
		 */
		public var principalData:PrincipalData;

		/**
		 * the current authenticated, or not, user
		 */
		private var _currentPerson:PersonDto;
		public function get currentPerson():PersonDto
		{
			return _currentPerson;
		}
		public function set currentPerson( value:PersonDto ):void
		{
			if( value )
			{
				_currentPerson = value;
			}
		}
	}
}