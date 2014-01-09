package com.tps.tpi.containers
{

	/**
	 * A standard interface for providing validation to forms
	 */
	public interface IValidateableForm
	{
	
		function isValid( forceValidation:Boolean = false ):Boolean;
		
		function clear():void;
	
	}
}

