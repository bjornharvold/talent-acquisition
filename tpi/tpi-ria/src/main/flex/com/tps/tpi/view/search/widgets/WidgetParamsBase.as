package com.tps.tpi.view.search.widgets
{
	import com.tps.tpi.model.objects.dto.SearchComponentDto;
	import com.tps.tpi.view.events.ExclusionChangedEvent;
	
	import flash.events.Event;
	
	import mx.containers.Box;
	import mx.containers.BoxDirection;
	
	[Event(name="addSearchParams",type="flash.events.Event")]
	[Event(name="removeSearchParams",type="flash.events.Event")]
	
	
	public class WidgetParamsBase extends Box
	{
		
		/** 
		 * storage for params for get params call
		 */
		protected var _params:SearchComponentDto
		
		/**
		 * 
		 * constructor 
		 * 
		 */
		public function WidgetParamsBase()
		{
			super();
			
			//default
			direction = BoxDirection.HORIZONTAL
			percentWidth = 100;
		}
		
		/**
		 * @private 
		 * 
		 * handle the user gesture to exclude
		 * the search param entered
		 * 
		 * to be overriden in subclasses that have exclusonary choice
		 * subclass would decide what to do in this case, notify UI or whatever
		 */
		protected function onExclusionChange( event:Event ):void
		{
			
		}
		
		/**
		 * @private
		 * 
		 * respond to user gesture to add another row of params by
		 * telling the parent
		 * 
		 * subclasses need not override
		 */
		protected function onAddSearchParams( event:Event ):void
		{
			dispatchEvent( new Event( "addSearchParams" ) );
			
		}
		
		/**
		 * @private
		 * 
		 * respond to user gesture to remove a row of params by
		 * telling the parent
		 * 
		 * subclasses need not override
		 */
		protected function onRemoveSearchParms( event:Event ):void
		{
			dispatchEvent( new Event( "removeSearchParams" ) );
		}
		
		/**
		 * @public
		 * expose the search params to parent for
		 * gathering them up.  
		 * 
		 * @return <code>Object</code> a map of search param data to be passed to server
		 * 
		 * subclasses override to build the params 
		 */
		public function get params():SearchComponentDto
		{
			
			return _params;
		}
	}
}