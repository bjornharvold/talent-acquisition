package com.tps.tpi.model.vo
{
	[Bindable]
	public class SearchResultItem
	{
		public function SearchResultItem()
		{
		}
		
		/**
		 * incoming data
		 */
		public var resourceXML:XML;
		
		/**
		 * we intend to use this to get all items 
		 * on the same view index
		 **/
		public var cubeIndex:int = 0;


	}
}