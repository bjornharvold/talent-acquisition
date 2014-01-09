package com.tps.tpi.model.presentation
{
	import com.tps.tpi.model.constants.SearchResultsViewIndex;
	import com.tps.tpi.model.objects.dto.SearchDto;
	import com.tps.tpi.model.objects.lite.PersonLite;
	
	import flash.events.Event;
	import flash.events.EventDispatcher;
	import flash.utils.setTimeout;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ListCollectionView;
	
	[Bindable]
	public class SearchModel extends EventDispatcher implements ISearchModel
	{
		
		private var _quickSearchCount:int = 0;
		[Bindable(event="quickSearchCountChanged")]
		public function get quickSearchCount():int
		{
			return _quickSearchCount;
		}
		public function set quickSearchCount( value:int ):void
		{
			_quickSearchCount = value;
			dispatchEvent( new Event("quickSearchCountChanged") );
			
		}

		private var _searchResults:ListCollectionView;
		[Bindable(event="searchResultsChanged")]
		public function get searchResults():ListCollectionView
		{
			return _searchResults;
		}
		public function set searchResults( value:ListCollectionView ):void
		{
			_searchResults = value;
			dispatchEvent( new Event("searchResultsChanged") );
			
		}
		
		private var blankSearchCount:int = 0;
		
		
		public function SearchModel()
		{
			_searchResults = new ListCollectionView( new ArrayCollection() );
			addNewBlankSearch();
		}
		
		
		/**
		 * we need to determine how to add a new result.
		 * new results always get added to the currently shown search panel 
		 * in the UI.
		 * 
		 * note that future versions will maybe return a saved search here....
		 * 
		 */
		public function addSearchResult( value:SearchDto ):void
		{
			value.currentResultsViewType = SearchResultsViewIndex.TILE;
			if( value.name == null || value.name == "" )
				value.name = "Search " + ( currentSearchResultIndex + 1 ).toString();
			searchResults.setItemAt(value,  currentSearchResultIndex );
			currentSearchResult = value;
		}
		
		/** 
		 * when user requests to add a new blank search
		 * we use this method to set things up
		 */
		public function addNewBlankSearch( ):void
		{
			blankSearchCount++;
			var searchResult:SearchDto = new SearchDto();
			searchResult.name = "Search " + blankSearchCount;
			
			searchResults.addItem( searchResult );
			
			//lookout kludge alert!!!!
			//silly bug in SuperTabBar requires a slight pause here
			setTimeout( viewSearchResult, 25, searchResults.length - 1 );
			//after adding it, we switch to it
			//currentSearchResultIndex = searchResults.length;
		}
		
		
		/**
		 * this stores the current index of the currently 
		 * viewed <code>SearchResult</code>
		 */
		public var currentSearchResultIndex:int = 0;
		
		public var currentSearchResult:SearchDto;
		
		
		/////NEEDED ????????
		public function viewSearchResult( index:int ):void
		{
			currentSearchResultIndex = index;
			currentSearchResult = searchResults.getItemAt( index ) as SearchDto;
		}
		
		
		/**
		 * we need  a blank search result to use at startup
		 * in order to have the initial tab visible and ready
		 * we also add one for the ADD button...oh joy, how kludgy
		 * can we get?
		 */
		private function setupDefaultSearchResults():void
		{
			var sr:SearchDto = new SearchDto();
			sr.name = "Search 1";
			var srs:ArrayCollection = new ArrayCollection( [ sr ]  );
			_searchResults = new ListCollectionView( srs );
			
		}
		
		/**
		 * 
		 */
		 public function removeSearchResult( index:int ):void
		 {
		 	searchResults.removeItemAt( index );
		 	currentSearchResultIndex = 0;
		 }
		 
		 /**
		 * 
		 */
		 public function changeAllCubes( index:int ):void
		 {
			for each( var pl:PersonLite in currentSearchResult.persons )
			{
				pl.cubeIndex = index;
			}		 		
		 }

	}
}