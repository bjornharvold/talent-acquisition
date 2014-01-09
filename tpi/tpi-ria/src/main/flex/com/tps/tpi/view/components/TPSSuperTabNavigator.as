package com.tps.tpi.view.components
{
	import flexlib.containers.SuperTabNavigator;
	
	public class TPSSuperTabNavigator extends SuperTabNavigator
	{
		public function TPSSuperTabNavigator()
		{
			super();
		}
		
		
		/**
		 * @private
		 * Our internal variable to keep track of the popupButton label
		 */
		private var _popUpButtonLabel:String;
		private var popUpButtonLabelIsDirty:Boolean = false;
		/**
		 * label for the popup button 
		 */
		public function get popUpButtonLabel():String 
		{
			return _popUpButtonLabel;	
		}
		
		public function set popUpButtonLabel( value:String ):void 
		{
			if( value )
			{
				_popUpButtonLabel = value;	
				popUpButtonLabelIsDirty = true;
				invalidateProperties();
			}
			
		}
		
		
		/**
	    * 
	    */
	    override protected function commitProperties() : void
	    {
	    	super.commitProperties();
	    	
	    	if( popUpButtonLabelIsDirty )
	    	{
	    		popUpButtonLabelIsDirty = false;
	    		popupButton.label = popUpButtonLabel;
	    		popupButton.height = 20;
	    	}
	    	
	    	
	    }
	}
}