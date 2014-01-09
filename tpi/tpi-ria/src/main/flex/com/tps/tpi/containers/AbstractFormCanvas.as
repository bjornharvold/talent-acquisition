package com.tps.tpi.containers
{
	import flash.events.Event;
	
	import mx.containers.Canvas;
	import mx.core.EventPriority;
	import mx.core.UIComponent;
	import mx.events.FlexEvent;
	import mx.events.ValidationResultEvent;
	import mx.validators.Validator;
	
	/**
	 * Dispatched when the form valid state changes.
	 * 
	 * @eventType validChange
	 */
	[Event(name="validChange", type="flash.events.Event")]

	/**
	 * An <code>AbstractFormCanvas</code> is a form that includes some helper
	 * methods and proeprties to define a standard way of valdiating
	 * form input values.
	 * 
	 * This class should not be instantiated directly, but it meant to be
	 * used as a base class for any form that needs validation.
	 */
	public class AbstractFormCanvas extends Canvas implements IValidateableForm
	{
		/** Flag indicating if the form is valid or not. */
		protected var formIsValid:Boolean = false;
		
		/** Holds a reference to the currently focussed control on the form. */
		protected var focusedFormControl:UIComponent;
	

		public function AbstractFormCanvas()
		{
			super();
			addEventListener( FlexEvent.CREATION_COMPLETE, resetFocusOnCreationComplete, false, EventPriority.DEFAULT, true );
		}
		
		/** Helper function to auto-reset the form focus once creation complete fires. */
		private function resetFocusOnCreationComplete( event:FlexEvent ):void
		{
			resetFocus();
			removeEventListener( FlexEvent.CREATION_COMPLETE, resetFocusOnCreationComplete );
		}
	

		
		/** 
		 * Public accessor to determine if form is valid or not
		 * 
		 * @param forceValidation True if validateForm should be called to force
		 * 	valdiation before checking the valid state.
		 */
		[Bindable( "validChange" )]
		public function isValid( forceValidation:Boolean = false ):Boolean
		{
			if ( forceValidation )
			{
				validateForm( null );
			}
			
			return formIsValid;
		}
	
		/**
		 * 
		 */		
		protected function focusControl( control:UIComponent ):void
		{
			// Remove the old focus if it exists
			if ( focusedFormControl )
			{
				focusedFormControl.drawFocus( false );
			}
			// Capture the new focus
			control.setFocus();
			control.drawFocus( true );
			
			// Clear the focused form control - it will be reset through
			// the valiation process
			focusedFormControl = null;
		}
		
		/** 
		 * Validate the form.
		 * 
		 * @param event The event that started the validation process, typically
		 * 	a "change" event.  In special cases, when you want to programmatically
		 *  kick start validation, you may pass "null" as the event.  In that case,
		 *  the focusFormControl will not be changed, so make sure the value is set
		 *  a head of time to the currently focused component.
		 */
		protected function validateForm( event:Event ):void 
		{                    
			// Save a reference to the currently focussed form control
			// so that the isValid() helper method can notify only
			// the currently focussed form control and not affect
			// any of the other form controls.
			if ( event != null )
			{
				focusedFormControl = event.target as UIComponent;
			}
					
			// Mark the form as valid to start with                
			formIsValid = true;
			
			// At this point, the subclasses must override and write
			// the rest of the validation code.  When the validation
			// code is complete, they must call:
			//
			// 		dispatchEvent( new Event( "validChanged" ) );
			//
			// It is important to dispatch the event so that others
			// who bound to isValid() get updated correctly
		}
		
		/**
		 * Helper method. Performs validation on a passed Validator instance.
		 * Validator is the base class of all Flex validation classes so 
		 * you can pass any validation class to this method.
		 * 
		 * @return true if the validator is valid, false otherwise.
		 */
		protected function validate( validator:Validator, suppressErrorTip:* = null ):Boolean
		{                
			// Get a reference to the component that is the source of the validator.
			var validatorSource:UIComponent = validator.source as UIComponent;
			
			// Suppress events if the current control being validated is not
			// the currently focused control on the form. This stops the user
			// from receiving visual validation cues on other form controls.
			var suppressEvents:Boolean = validatorSource != focusedFormControl;
			// Check for the manual override for the error tip display
			if ( suppressErrorTip != null && suppressErrorTip is Boolean)
			{
				suppressEvents = suppressErrorTip;
			}
			
			// Carry out validation. Returns a ValidationResultEvent.
			// Passing null for the first parameter makes the validator 
			// use the property defined in the property tag of the
			// <mx:Validator> tag.
			var event:ValidationResultEvent = validator.validate( null, suppressEvents );
			
			// Check if validation passed and return a boolean value accordingly
			var currentControlIsValid:Boolean = event.type == ValidationResultEvent.VALID;
			
			// Update the formIsValid flag to incorporate results of validation
			formIsValid = formIsValid && currentControlIsValid;
			
			// This isn't necessary anymore (and actually produces undesired side effect)
			// since we fixed the issue in the focusControl method by drawFocus (false) on the
			// preivous item that had focus
			//Fix a focus issue where the red border is still drawn even though
			//it shoudln't be anymore.
			//validatorSource.drawFocus( focusedFormControl == validatorSource );
			 
			return currentControlIsValid;
		}
		
		/**
		 * Helper method, sets the focus to the first form field so that
		 * the user does not have to mouse to it.
		 */
		protected function resetFocus():void
		{
			trace( "Subclasses should override AbstractForm::resetFocus().  Called from: " + this );
		}
		
		/**
		 * Reset the form input fields.
		 */
		public function clear():void
		{
			// Adjust the form focus
			resetFocus();
					
			// Force validation to make sure isValid reports correctly.  Use
			// a null event so that error strings are suppressed.
			validateForm( null );
		}
		
	}
}