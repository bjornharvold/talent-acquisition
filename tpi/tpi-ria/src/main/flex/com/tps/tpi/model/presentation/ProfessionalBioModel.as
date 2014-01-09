package com.tps.tpi.model.presentation
{
	import com.tps.tpi.events.UpdateBiographyEvent;
	import com.tps.tpi.model.objects.dto.BiographyDto;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.events.IEventDispatcher;
	
	import mx.utils.ObjectUtil;
	
	public class ProfessionalBioModel extends EventDispatcher implements IProfessionalBioModel
	{
		public function ProfessionalBioModel( dispatcher:IEventDispatcher )
		{
			super();
			this.dispatcher = dispatcher;
		}

		private var dispatcher:IEventDispatcher;
		
		/**
		 * we keep a unmodified copy here.  When user requests to 
		 * save, we modify this one, then save it.  If the server call
		 * fails, we can retain the original.
		 */
		 private var _professionalBioOriginal:BiographyDto;
		 		
		/**
		 * the current professionalBio for the user
		 */
		private var _professionalBio:BiographyDto;
		[Bindable(  event="professionalBioChanged"  )]
		public function get professionalBio():BiographyDto
		{
			return _professionalBio;
		}
		public function set professionalBio( value:BiographyDto ):void
		{
			if( value )
			{
				_professionalBio = value;
				dispatchEvent( new Event( "professionalBioChanged" ) );
				
				_professionalBioOriginal = ObjectUtil.copy( value ) as BiographyDto;
			}
		}
		
		
		
		/**
		 * entity interaction methods
		 */
		 
		 /**
		 * 
		 * save teh summary
		 * which ammounts to setting it in the copy of the entity, then saving the entity
		 */
		 public function updateSummary( value:String ):void
		 {
		 	_professionalBioOriginal.summary = value;
		 	dispatcher.dispatchEvent( new UpdateBiographyEvent( _professionalBioOriginal ) );
		 	
		 }
		 
		 
		 
		
	}
}