package com.tps.tpi.view.search.widgets
{
	import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
	import com.tps.tpi.model.objects.enums.SearchComponentTypeCd;
	
	import flash.errors.IllegalOperationError;
	import flash.events.Event;
	
	import mx.containers.TitleWindow;
	import mx.core.ContainerLayout;

	/**
	 * 
	 * base class for advanced search widgets
	 * 
	 */
	public class WidgetBase extends TitleWindow
	{
		
		/**
		 * the advanced search type
		 */
		public var type:SearchComponentTypeCd;
		
		/**
		 * searchComponent setup
		 * a setter is needed to handle
		 * injection from a saved search
		 */
		protected var searchComponentIsDirty:Boolean;
		protected var _searchComponentGroup:SearchComponentGroupDto;
		public function get searchComponentGroup():SearchComponentGroupDto
		{
			return _searchComponentGroup;
		}
		public function set searchComponentGroup( value:SearchComponentGroupDto ):void
		{
			if( value )
			{
				_searchComponentGroup = value;
				searchComponentIsDirty = true;
				invalidateProperties();
			}
		}
		
		
		/**
		 * the constructor
		 */
		public function WidgetBase()
		{
			super();
			showCloseButton = true;
			layout = ContainerLayout.ABSOLUTE;
			width = 635;
		}
		
		/**
		 * handler for reacting to user gesture of adding search params
		 * Subclasses must override.
		 */
		protected function onAddSearchParams( event:Event ):void 
		{
		}

		/**
		 * handler for reacting to user gesture of adding search params
		 * Subclasses must override.
		 */
		protected function onRemoveSearchParams( event:Event ):void 
		{
		}
		
		/**
		 * storage for params 
		 */
		protected var _params:SearchComponentGroupDto;
		/**
		 * @return Array array of Object maps
		 * public interface for returning the params gathered by the widget
		 */
		public function get params():SearchComponentGroupDto
		{
			return _params;
		}
	}
}