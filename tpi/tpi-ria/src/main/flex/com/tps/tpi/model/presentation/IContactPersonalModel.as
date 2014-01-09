package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.PersonDto;
	
	public interface IContactPersonalModel
	{

		[Bindable(event="personChanged")]
		function set person( value:PersonDto ):void;
		function get person():PersonDto;
	}
}