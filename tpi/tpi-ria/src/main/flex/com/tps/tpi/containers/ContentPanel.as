package com.tps.tpi.containers
{
	import mx.containers.Panel;
	import mx.states.State;

	/**
	 * A <code>ContentPanel</code> extends the <code>Panel</code>
	 * and is used in content areas within the UI
	 * note that the <code>Panel</code> it extends is a monkey patched version
	 * exposing the leftOffset variable
	 */
	public class ContentPanel extends Panel
	{
		/**
		 * constants to manage the state of this component
		 */
		public static const READ_STATE:String = "";//thus the default state
		public static const EDIT_STATE:String = "editState";
		
		public function ContentPanel()
		{
			super();
			this.leftOffset = -1;
			
		}
		
		override public function set currentState( value:String ):void
		{
			super.currentState = value;	
			if( value == READ_STATE )
				this.styleName = "readMode";
			else
				this.styleName = "editMode";
		}
		
	}
}