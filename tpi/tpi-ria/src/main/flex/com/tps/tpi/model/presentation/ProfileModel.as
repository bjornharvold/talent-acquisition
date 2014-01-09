package com.tps.tpi.model.presentation
{
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	
	
	[Bindable]
	public class ProfileModel extends EventDispatcher implements IProfileModel
	{
		
		// dispatcher that will be set from IoC action goodness
		// allowing this non-view class to dispatch bubbling events
		private var dispatcher:IEventDispatcher;
		
		/**
		 * the current professionalBio for the user
		 */
		public var professionalBioModel:ProfessionalBioModel;
		
		public var historyModel:PositionsAndRolesModel;
		
		public var educationModel:EducationModel;
		
		public var contactPersonalModel:ContactPersonalModel
		
		public var userId:String;
		
		/**
		 * 
		 * constructor
		 * 
		 */
		public function ProfileModel( dispatcher:IEventDispatcher )
		{
			this.dispatcher = dispatcher;
			reset();
		}
		
		/** reset this entity...hey, its a singleton currently....gott to refactor that out for
		 * next revision
		 */
		public function reset():void
		{
			professionalBioModel = new ProfessionalBioModel( dispatcher );
			historyModel = new PositionsAndRolesModel(  );
			educationModel = new EducationModel();
			contactPersonalModel = new ContactPersonalModel();
		}

	}
}