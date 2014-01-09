package com.tps.tpi.model.vo
{
	import com.tps.tpi.model.constants.SearchResultsViewIndex;
	
	import mx.collections.ListCollectionView;
	
	
	/**
	 * 
	 * <code>SearchResult</code> is used to entity a individual result
	 * of searching returned by the server
	 * 
	 */
	[Bindable]
	public class SearchResult
	{
		/**
		 * List of <code>PersonLite</code> objects
		 */
		public var results:ListCollectionView;
		
		/**
		 * searches are to be saved and user can name them
		 */
		public var name:String;
		
		
		/**
		 * for now we track the keywords the search is based on
		 * future releases will have a data structure here to entity
		 * the advanced search features.  I'll assume this entity will
		 * represent basic quick searches as well.  Awaiting more server side
		 * work here to determine object structure, type, members, etc...
		 */
		public var keywords:String;
		
		/**
		 * keep track of the view type for this result
		 * table, tile, list....
		 */
		public var currentResultsViewType:int = SearchResultsViewIndex.BLANK;
		
		
		/**
		 * the constructor
		 */
		public function SearchResult()
		{
		}
		
		

	}
}