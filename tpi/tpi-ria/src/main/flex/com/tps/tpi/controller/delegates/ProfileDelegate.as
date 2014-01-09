package com.tps.tpi.controller.delegates
{
	
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class ProfileDelegate
	{
		
		[Autowire(bean="profileService")]
		public var profileService:RemoteObject;
		
		/**
		 * constructor
		 */
		public function ProfileDelegate()
		{
		}
		
		public function getPersonByUsername( uid:String ):AsyncToken
		{
			return profileService.getPersonDtoByUsername( uid );
		}
		
		//pass this a personId, not a userId
		public function getPersonDto( uid:String ):AsyncToken
		{
			return profileService.getPersonDto( uid );
		}
		
		
		public function getProfessionalBio( personId:String ):AsyncToken
		{
			return profileService.getBiographyDto( personId );
		}

		public function getHistory( personId:String ):AsyncToken
		{
			return profileService.getHistoryDto( personId );
		}
		
		public function getProfileEducation( personId:String ):AsyncToken
		{
			return profileService.getEducationDto( personId );
		}
		
	}
}