package com.tps.tpi.controller
{
	import com.tps.tpi.controller.delegates.BiographyDelegate;
	import com.tps.tpi.events.UpdateBiographyEvent;
	import com.tps.tpi.model.objects.dto.BiographyDto;
	import com.tps.tpi.model.presentation.IProfessionalBioModel;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	public class BiographyController extends BaseController
	{
		
		[Autowire]
		public var delegate:BiographyDelegate;
		
		public var profileBioModel:IProfessionalBioModel;
		
		public function BiographyController()
		{
			super();
		}
		
		[Mediate(event="UpdateBiographyEvent.EVENT_TYPE")]
		public function updateBiography( event:UpdateBiographyEvent  ):void
		{
			var res:Responder = new Responder( onBiographyReturn,  faultHandler );
			var token:AsyncToken = delegate.updateBiography( event.biography );
			token.addResponder( res );
		}
		
		public function onBiographyReturn( event:ResultEvent ):void
		{
			profileBioModel.professionalBio = event.result as BiographyDto;
		}
		
	}
}