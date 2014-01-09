package com.tps.tpi.events
{
	import com.tps.tpi.model.objects.dto.BiographyDto;
	
	import flash.events.Event;

	public class UpdateBiographyEvent extends Event
	{
		public var biography:BiographyDto;
		
		public static const EVENT_TYPE:String = "updateTheBiographyData";
		
		public function UpdateBiographyEvent( biography:BiographyDto = null )
		{
			super(EVENT_TYPE, true, true );
			this.biography = biography;
		}
		
	}
}