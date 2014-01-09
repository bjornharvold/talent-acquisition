package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.objects.dto.HistoryDto;
	
	public interface IPositionsAndRolesModel
	{

		[Bindable(event="positionsAndRolesChanged")]
		function set positionsAndRoles( value:HistoryDto ):void;
		function get positionsAndRoles():HistoryDto;
	}
}