package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.EducationDto;
	
	public interface IEducationModel
	{

		[Bindable(event="educationChanged")]
		function set education( value:EducationDto ):void;
		function get education():EducationDto;
	}
}