package com.tps.tpi.controller
{
	import com.tps.tpi.controller.delegates.ProfileDelegate;
	import com.tps.tpi.events.FetchProfileEvent;
	import com.tps.tpi.model.constants.MainViewIndex;
	import com.tps.tpi.model.objects.dto.BiographyDto;
	import com.tps.tpi.model.objects.dto.EducationDto;
	import com.tps.tpi.model.objects.dto.HistoryDto;
	import com.tps.tpi.model.objects.dto.PersonDto;
	import com.tps.tpi.model.presentation.ApplicationModel;
	import com.tps.tpi.model.presentation.ProfileModel;
	
	import flash.events.IEventDispatcher;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class ProfileController extends BaseController
	{
		public function ProfileController()
		{
			super();
		}
		
		[Autowire(bean="personalProfileModel")]
		[Bindable]
		public var currentUsersProfile:ProfileModel;

		[Autowire(bean="readOnlyProfileModel")]
		[Bindable]
		public var readOnlyProfile:ProfileModel;
		
		[Autowire(bean="applicationModel")]
		public var applicationModel:ApplicationModel;
		
		[Autowire]
		public var delegate:ProfileDelegate;
		
		[Mediate(event="FetchProfileEvent.FETCH_PROFESSIONALBIO")]
		public function getProfessionalProfileData( event:FetchProfileEvent  ):void
		{
			var res:Responder = new Responder( onProfessionalProfileReturn,  faultHandler );
			var token:AsyncToken = delegate.getProfessionalBio( event.data.toString() );
			token.isProfileOwner = event.isProfileOwner;
			token.addResponder( res );
		}
		
		public function onProfessionalProfileReturn( event:ResultEvent ):void
		{
			if( event.token.isProfileOwner )
				currentUsersProfile.professionalBioModel.professionalBio = event.result as BiographyDto;
			else
				readOnlyProfile.professionalBioModel.professionalBio = event.result as BiographyDto;
		}
		
		[Mediate(event="FetchProfileEvent.FETCH_POSITIONS")]
		public function getProfileHistory( event:FetchProfileEvent  ):void
		{
			var res:Responder = new Responder( onProfileHistoryReturn,  faultHandler );
			var token:AsyncToken = delegate.getHistory( event.data.toString() );
			token.isProfileOwner = event.isProfileOwner;
			token.addResponder( res );
		}
		
		public function onProfileHistoryReturn( event:ResultEvent ):void
		{
			if( event.token.isProfileOwner )
				currentUsersProfile.historyModel.positionsAndRoles = event.result as HistoryDto;
			else
				readOnlyProfile.historyModel.positionsAndRoles = event.result as HistoryDto;
		}
		
		[Mediate(event="FetchProfileEvent.FETCH_EDUCATION")]
		public function getProfileEducation( event:FetchProfileEvent  ):void
		{
			var res:Responder = new Responder( onProfileEducationReturn,  faultHandler );
			var token:AsyncToken = delegate.getProfileEducation( event.data.toString() );
			token.isProfileOwner = event.isProfileOwner;
			token.addResponder( res );
		}
		
		public function onProfileEducationReturn( event:ResultEvent ):void
		{
			if( event.token.isProfileOwner )
				currentUsersProfile.educationModel.education = event.result as EducationDto;
			else
				readOnlyProfile.educationModel.education = event.result as EducationDto;
		}
		
		[Mediate(event="FetchProfileEvent.FETCH_PERSON_BY_ID")]
		public function getPerson( event:FetchProfileEvent  ):void
		{
			var res:Responder = new Responder( onGetPersonReturn,  faultHandler );
			var token:AsyncToken = delegate.getPersonDto( event.data.toString() );
			token.isProfileOwner = event.isProfileOwner;
			token.addResponder( res );
		}
		
		public function onGetPersonReturn( event:ResultEvent ):void
		{
			if( event.token.isProfileOwner )
				currentUsersProfile.contactPersonalModel.person = event.result as PersonDto;
			else
			{
				//in this case currently, we always are seeing a new profile
				//and only one read-only version exists
				//so, reset it here...yuk...I got a bad feeling about this
				readOnlyProfile.reset();
				
				readOnlyProfile.contactPersonalModel.person = event.result as PersonDto;
				readOnlyProfile.userId = readOnlyProfile.contactPersonalModel.person.id;
				applicationModel.mainViewState = MainViewIndex.PROFILE_READ;
			}
			
		}
	}
}