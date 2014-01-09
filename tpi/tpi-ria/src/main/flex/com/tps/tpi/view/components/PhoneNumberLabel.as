package com.tps.tpi.view.components
{
	import mx.controls.Label;
	import mx.formatters.PhoneFormatter;

	public class PhoneNumberLabel extends Label
	{
		protected var formatter:PhoneFormatter;
		
		public function PhoneNumberLabel()
		{
			super();
			formatter = new PhoneFormatter();
			formatter.formatString = resourceManager.getString("resources", "PHONE_FORMAT_FULL");
		}
		
		override public function set text(value:String):void
		{
			value = formatter.format( value );
			super.text = value;
		}
		
	}
}