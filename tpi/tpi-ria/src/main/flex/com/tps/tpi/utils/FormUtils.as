package com.tps.tpi.utils
{
	import flash.display.DisplayObject;
	
	import mx.containers.Form;
	import mx.containers.FormItem;
	import mx.controls.CheckBox;
	import mx.controls.DateField;
	import mx.controls.RadioButton;
	import mx.controls.RadioButtonGroup;
	import mx.controls.TextArea;
	import mx.controls.TextInput;
	import mx.events.ValidationResultEvent;
	import mx.validators.Validator;
	
	/**
	 * Helper class that contains methods for working with forms
	 */
	public final class FormUtils
	{
		/**
		 * Used to reset any form back to an empty state, you may need to add more form types as needed
		 */
		public static function resetForm( form:Form ):void
		{
			var formElements:Array = form.getChildren();
			for each ( var obj:DisplayObject in formElements )
			{
				if ( obj is FormItem )
				{
					var formFields:Array = FormItem( obj ).getChildren();
					for each ( var formItem:DisplayObject in formFields )
					{
						if ( formItem is TextInput )
						{
							TextInput( formItem ).text = "";
						}
						else if ( formItem is TextArea )
						{
							TextArea( formItem ).text = "";
						}
						else if ( formItem is DateField )
						{
							DateField( formItem ).selectedDate = null;
						}
						else if ( formItem is CheckBox )
						{
							CheckBox( formItem ).selected = false;
						}
					}
				}
			}
		}
		
		/**
		 * Selects a radio button by object id
		 */
		public static function setRadioButtonById( group:RadioButtonGroup, id:String, value:* ):void
		{
			for ( var x:int = 0; x < group.numRadioButtons; x++ )
			{
				var btn:RadioButton = group.getRadioButtonAt( x );
				if ( btn.value[ id ] == value )
				{
					group.selection = btn;
				}
			}
		}
		
		/**
		 * Used to validate an array of validators to deteremine if all are valid
		 */
		public static function validateForm( validators:Array, suppress:Boolean = true ):Boolean
		{
			var isValid:Boolean = true;
			// Loop over all the validators
			for each ( var validator:Validator in validators )
			{
				isValid = validateElement( validator, suppress );
				if ( isValid == false )
					break;
			}
			return isValid;
		}
		
		/**
		 * Used to simplify validating validators
		 */
		public static function validateElement( validator:Validator, suppress:Boolean = true ):Boolean
		{
			// Validate each of the validators but we will suppress the validation events
			var vResult:ValidationResultEvent = validator.validate( null, suppress );
			if ( vResult.type == ValidationResultEvent.INVALID )
			{
				return false;
			}	
			else
			{
				return true;
			}
		}
	}
}