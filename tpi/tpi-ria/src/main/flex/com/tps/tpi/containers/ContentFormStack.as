package com.tps.tpi.containers
{
	import com.tps.tpi.view.components.EditContentButton;
	
	import flash.display.Graphics;
	import flash.display.Sprite;
	import flash.events.MouseEvent;
	import flash.filters.DropShadowFilter;
	
	import mx.containers.Canvas;
	import mx.controls.Label;
	import mx.core.UIComponent;
	import mx.states.AddChild;
	import mx.states.RemoveChild;
	import mx.states.State;
	
	
	public class ContentFormStack extends Canvas
	{
		/**
		 * we have editable or non editable views, same data
		 */
		public var editable:Boolean = false;
		
		protected var editButton:EditContentButton;
		protected var theLabel:Label
		
		//needed this for the drop shadow
		private var backGroundSprite:Sprite;
		
		//teh needed dropshadow filter
		private var ds:DropShadowFilter;
		
		private static const PADDING:int = 10;
		private static const CONTENT_BORDER_COLOR_EDIT:uint = 0xBCD1E4;
		private static const CONTENT_BORDER_COLOR_READ:uint = 0xD2C5AD;
		
		private static const CONTENT_BACKGROUND_COLOR_EDIT:uint = 0xE4EFF5;
		private static const CONTENT_BACKGROUND_COLOR_READ:uint = 0xFFFFFF;
		
		private static const HEADER_TITLE_HEIGHT:int = 15;
		private static const CONTENT_PADDING_BOTTOM:int = 0;
		private static const CONTENT_PADDING_RIGHT:int = 10;
		
		private static const EDIT_BUTTON_WIDTH:int = 41;
		
		/**
		 * constants to manage the state of this component
		 */
		public static const READ_STATE:String = "";//thus the default state
		public static const EDIT_STATE:String = "editState";

		private var _readUI:UIComponent;
		public function set readUI( value:UIComponent ):void
		{
			if( value )
			{
				_readUI = value;
				_readUI.setStyle( "left", 55 );
				_readUI.setStyle( "right", 20 );
				_readUI.setStyle( "bottom", 10 );
				_readUI.setStyle( "top", 25 );
			}
			
		}

		private var _editUI:UIComponent;
		public function set editUI( value:UIComponent ):void
		{
			if( value )
			{
				_editUI = value;
				_editUI.setStyle( "left", 55 );
				_editUI.setStyle( "right", 20 );
				_editUI.setStyle( "bottom", 10 );
				_editUI.setStyle( "top", 25 );
			}
			
		}
         
         
		public function ContentFormStack()
		{
			super();
			
			horizontalScrollPolicy = "off";
			verticalScrollPolicy = "off";
			//ensure no border shown
			this.setStyle( "borderWidth",0 );
			addEventListener( MouseEvent.MOUSE_OVER, onMouseOver );
			addEventListener( MouseEvent.MOUSE_OUT, onMouseOut );
		}
		
		
		override protected function createChildren():void
		{
			
			super.createChildren();
			
			//the background
			backGroundSprite = new Sprite();
			rawChildren.addChild( backGroundSprite );
			
			//The label
			theLabel = new Label();
			theLabel.text = this.label;
			theLabel.x = EDIT_BUTTON_WIDTH + 2;
			theLabel.y = 0;
			theLabel.height = HEADER_TITLE_HEIGHT;
			addChild( theLabel );	
			
			//The button
			editButton = new EditContentButton();
			editButton.x = 5;
			editButton.y = HEADER_TITLE_HEIGHT;
			editButton.width = EDIT_BUTTON_WIDTH;
			editButton.height = 25;
			editButton.visible = false;
			editButton.addEventListener( MouseEvent.CLICK, onEditButtonClick );
			editButton.addEventListener( MouseEvent.MOUSE_OVER, onMouseOver );
			editButton.addEventListener( MouseEvent.MOUSE_OUT, onMouseOut );
			addChild( editButton );
			
			//add teh read UI first now
            addChild( _readUI );
            
		}
		
		
		protected function onMouseOver( event:MouseEvent ):void
		{
			if( editable && currentState != EDIT_STATE )
				editButton.visible = true;
		}
            
		protected function onMouseOut( event:MouseEvent ):void
		{
			editButton.visible = false;
		}
            
        protected function onEditButtonClick( event:MouseEvent ):void
        {
        	currentState = EDIT_STATE;
        	editButton.visible = false;
        }
        
            
		override public function set currentState(value:String):void
		{
			super.currentState = value;
//			if( value == READ_STATE  )
//			{
//				styleName="readMode";
//			}
//			else
//			{
//				styleName="editMode";
//			}
		}
		
		override protected function updateDisplayList(unscaledWidth:Number, unscaledHeight:Number):void
	    {
	        
	        super.updateDisplayList(unscaledWidth, unscaledHeight );
	        var gr:Graphics = backGroundSprite.graphics;
	        
	        
	        if( currentState == EDIT_STATE )
	        {
	        	
	        	gr.clear();
		        gr.beginFill( CONTENT_BACKGROUND_COLOR_EDIT );
		        gr.lineStyle( 1, CONTENT_BORDER_COLOR_EDIT);
		        gr.drawRect( EDIT_BUTTON_WIDTH + 4, 
		        					HEADER_TITLE_HEIGHT , 
		        					unscaledWidth - ( EDIT_BUTTON_WIDTH + 4 ) - CONTENT_PADDING_RIGHT , 
		        					unscaledHeight - HEADER_TITLE_HEIGHT );
		        
		        ds = new DropShadowFilter();
		       	ds.color = 0x9D9D9D;
		       	backGroundSprite.filters = [ ds ];	
		        gr.endFill();
	        }
	        else
	        {
	        	gr.clear();
		        gr.beginFill( CONTENT_BACKGROUND_COLOR_READ );
		        gr.lineStyle( 1, CONTENT_BORDER_COLOR_READ);
		        gr.drawRect( EDIT_BUTTON_WIDTH + 4, 
		        					HEADER_TITLE_HEIGHT , 
		        					unscaledWidth - ( EDIT_BUTTON_WIDTH + 4 ) - CONTENT_PADDING_RIGHT, 
		        					unscaledHeight - HEADER_TITLE_HEIGHT );
		        
		       	ds = new DropShadowFilter();
		       	ds.color = 0xA99C85;
		       	backGroundSprite.filters = [ ds ];	
		        gr.endFill();
	        }
	        
            
	    }
	    
	    override protected function commitProperties():void
	    {
	    	super.commitProperties();
	    	if( states.length == 0)
	    	{
		    	//setup states
				var editState:State = new State();
				editState.name = EDIT_STATE;
				var addChild:AddChild = new AddChild();
				addChild.relativeTo = this;
				addChild.target = _editUI;
				var remChild:RemoveChild = new RemoveChild();
				remChild.target = _readUI;
				editState.overrides = [ addChild, remChild ];
				
				states = [ editState];
				
	    	}
	    	
	    }
	    
	    
	    /**
	    * trying to get this component to resize properly
	    */
	    override protected function measure():void
	    {
	    	super.measure();
	    	var minWidth:Number = 0;
            var minHeight:Number = 0;

            var preferredWidth:Number = 0;
            var preferredHeight:Number = 0;

            var n:int = numChildren;
            
            for (var i:int = 0; i < n; i++)
            {
                    var child:UIComponent = UIComponent(getChildAt(i));
                    //child.validateNow();
                    if (!child.includeInLayout)
                            continue;

                    var wPref:Number = child.getExplicitOrMeasuredWidth();
                    var hPref:Number = child.getExplicitOrMeasuredHeight();

                    minWidth = Math.max(minWidth, child.minWidth);
                    minHeight = Math.max(minHeight, hPref);
                    preferredWidth += wPref;
            }
            measuredHeight = minHeight + PADDING + PADDING + theLabel.height;
            measuredMinHeight = measuredHeight;
	    	
	    }
		
	}
}