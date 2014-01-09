package com.tps.tpi.events
{
	import flash.events.Event;
	

	public class FetchProfileEvent extends Event
	{
		public static const FETCH_PROFESSIONALBIO:String = "fetchTheProfessionalBioProfileData";
		public static const FETCH_EDUCATION:String = "fetchTheEducationProfileData";
		public static const FETCH_POSITIONS:String = "fetchThePositionsProfileData";
		public static const FETCH_CONTACT_PERSONAL:String = "fetchTheContactProfileData";
		public static const FETCH_PERSON_BY_ID:String = "fetchThePersonProfileDataByIdEvent";
		
		/**
		 * use this to pass a payload
		 */
		public var data:Object;
		
		public var isProfileOwner:Boolean = false;
		
		public function FetchProfileEvent(type:String = FETCH_CONTACT_PERSONAL, data:Object = null, isProfileOwner:Boolean = false  )
		{
			super( type, true, true );
			this.isProfileOwner = isProfileOwner;
			this.data = data;
		}
		
		override public function clone():Event
		{
			return new FetchProfileEvent( this.type, isProfileOwner );
			
		}
		
	}
}