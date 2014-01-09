package com.tps.tpi.controller.delegates
{
	import com.tps.tpi.model.objects.dto.BiographyDto;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class BiographyDelegate
	{
		[Autowire(bean="profileService")]
		public var profileService:RemoteObject;
		
		public function BiographyDelegate()
		{
		}
		
		
		public function updateBiography( biography:BiographyDto ):AsyncToken
		{
			return profileService.updateBiographyDto( biography );
		}

	}
}