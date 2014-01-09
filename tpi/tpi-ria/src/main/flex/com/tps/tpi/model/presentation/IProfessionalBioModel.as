package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.BiographyDto;
	
	public interface IProfessionalBioModel
	{

		[Bindable(event="professionalBioChanged")]
		function set professionalBio( value:BiographyDto ):void;
		function get professionalBio():BiographyDto;
		
		/**
		 * entity interaction methods
		 */
		function updateSummary( value:String ):void;
	}
}