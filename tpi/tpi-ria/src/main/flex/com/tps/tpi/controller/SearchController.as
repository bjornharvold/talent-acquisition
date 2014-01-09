package com.tps.tpi.controller
{
	import com.tps.tpi.controller.delegates.ProfileDelegate;
	import com.tps.tpi.controller.delegates.SearchDelegate;
	import com.tps.tpi.events.AddNewSearchEvent;
	import com.tps.tpi.events.CubeSideChangeEvent;
	import com.tps.tpi.events.DisplayProfileEvent;
	import com.tps.tpi.events.QuickSearchCountResults;
	import com.tps.tpi.events.QuickSearchEvent;
	import com.tps.tpi.events.RemoveSearchEvent;
	import com.tps.tpi.events.SearchEvent;
	import com.tps.tpi.events.ViewSearchResultEvent;
	import com.tps.tpi.model.constants.MainViewIndex;
	import com.tps.tpi.model.objects.dto.SearchComponentDto;
	import com.tps.tpi.model.objects.dto.SearchComponentGroupDto;
	import com.tps.tpi.model.objects.dto.SearchDto;
	import com.tps.tpi.model.objects.enums.SearchComponentFieldNameCd;
	import com.tps.tpi.model.objects.enums.SearchComponentGroupTypeCd;
	import com.tps.tpi.model.presentation.ApplicationModel;
	import com.tps.tpi.model.presentation.SearchModel;
	
	import flash.utils.getTimer;
	
	import mx.collections.ArrayCollection;
	import mx.collections.ListCollectionView;
	import mx.controls.Alert;
	import mx.rpc.AsyncToken;
	import mx.rpc.Responder;
	import mx.rpc.events.ResultEvent;
	
	import org.swizframework.Swiz;
	
	public class SearchController extends BaseController
	{
		/**
		 * the constructor
		 */
		public function SearchController()
		{
			super();
		}
		
		/**
		 * used for some timing tests
		 */
		private var timeDelay:int;
		
		//injected properties
		[Autowire]
		public var delegate:SearchDelegate;

		[Autowire]
		public var profileDelegate:ProfileDelegate;
				
		[Autowire]
		public var searchModel:SearchModel;
		
		[Autowire]
		public var applicationModel:ApplicationModel;
		
		/**
		 * get the count of possible records
		 * returned for a keyword search
		 * 
		 * @param event <code>QuickSearchEvent</code>
		 * @return void
		 */
		[Mediate( event="QuickSearchEvent.QUICK_SEARCH_COUNT" )]
		public function quickSearchCount( event:QuickSearchEvent ):void
		{
			var searchDto:SearchDto = new SearchDto();
			var searchGroup:SearchComponentGroupDto = new SearchComponentGroupDto();
			var searchComp:SearchComponentDto = new SearchComponentDto();
			searchComp.searchMap = new Object();
			searchComp.searchMap[ SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS ] = event.keywords;
			searchGroup.components = new ArrayCollection();
			searchGroup.type = SearchComponentGroupTypeCd.QUICK_SEARCH.name;
			searchGroup.components.addItem( searchComp );
			searchDto.groups = new ArrayCollection();
			searchDto.company = "Metrobank";//TBD!!!!!!   TODO
			searchDto.user = applicationModel.currentPerson.user;
			searchDto.name = "IDUNNO";
			searchDto.groups.addItem( searchGroup );
				
			var res:Responder = new Responder( onQuickSearchCountReturn,  faultHandler );
			var token:AsyncToken = delegate.performSearchCount( searchDto );
			token.addResponder( res );
			
		}

		/**
		 * handler for the quick search count results
		 * we stick the value into the entity, the view is bound to it
		 * note that future versions may need to do this differently
		 * if perchance there are > 1 UI locations for this sort of result
		 * 
		 * @param event <code>ResultEvent</code>
		 * @return void
		 * 
		 */
		private function onQuickSearchCountReturn( event:ResultEvent ):void
		{
			searchModel.quickSearchCount = event.result as int;
			Swiz.dispatchEvent( new QuickSearchCountResults() );
		}
		
		
		/**
		 * actual quick search call
		 * note that we pass the incoming keywords to the result event
		 * using the magic <code>AsynchToken</code>
		 * 
		 * @param event <code>QuickSearchEvent</code>
		 * @return void
		 */
		[Mediate( event="QuickSearchEvent.QUICK_SEARCH" )]
		public function quickSearch( event:QuickSearchEvent ):void
		{
			if( event.searchType == QuickSearchEvent.KEYWORD_SEARCH )
			{
				timeDelay = getTimer();
				
				var searchDto:SearchDto = new SearchDto();
				var searchGroup:SearchComponentGroupDto = new SearchComponentGroupDto();
				var searchComp:SearchComponentDto = new SearchComponentDto();
				searchComp.searchMap = new Object();
				searchComp.searchMap[ SearchComponentFieldNameCd.QUICKSEARCH_KEYWORDS ] = event.keywords;
				searchGroup.components = new ArrayCollection();
				searchGroup.type = SearchComponentGroupTypeCd.QUICK_SEARCH.name;
				searchGroup.components.addItem( searchComp );
				searchDto.groups = new ArrayCollection();
				searchDto.company = "Metrobank";//TBD!!!!!!   TODO
				searchDto.user = applicationModel.currentPerson.user;
				searchDto.name = "IDUNNO";
				searchDto.groups.addItem( searchGroup );
				
				
				var res:Responder = new Responder( onQuickSearchReturn,  faultHandler );
				var token:AsyncToken = delegate.performSearch( searchDto );
				token.keywords = event.keywords;
				token.addResponder( res );
				
				//we switch to the search results view now
				applicationModel.mainViewState = MainViewIndex.SEARCH;
				
			}
			else
			{
				Alert.show( "NEED TO IMPLEMENT");
			}
			
		}
		
		/**
		 * handle the quick search results call
		 * we pass the keywords and resutls to the entity method
		 * that does what is needed for adding new search results 
		 * 
		 * @param event <code>ResultEvent</code>
		 * @return void
		 */
		private function onQuickSearchReturn( event:ResultEvent ):void
		{
			//temporary....we are going to fake teh relevancy numbers here
//			var tmp:ListCollectionView = event.result as ListCollectionView;
//			var l:int = tmp.length;
//			var d:int;
//			for( var i:int = 0; i < l; i++ )
//			{
//				i % 2 == 0 ? d = i : d = i - 1;
//				//trace( ( ( l - d ) / l ) * 100 );
//				( tmp.getItemAt( i ) as PersonLite ).relevancy = ( l - d ) / l;
//			}

			//Alert.show( (getTimer() - timeDelay ).toString() );
			
			
			searchModel.addSearchResult( event.result as SearchDto );
			
			
		}

		/**
		 * event to capture user request to add a new blank search
		 * simply calls method in entity to do the work
		 * 
		 * @param event <code>AddNewSearchEvent</code>
		 * @return void
		 */
		[Mediate( event="AddNewSearchEvent.EVENT_TYPE" )]
		public function addNewBlankSearch( event:AddNewSearchEvent ):void
		{
			searchModel.addNewBlankSearch();
			
		}
		
		/**
		 * event to capture user request to add a new blank search
		 * simply calls method in entity to do the work
		 * 
		 * @param event <code>ViewSearchResultEvent</code>
		 * @return void
		 */
		[Mediate( event="ViewSearchResultEvent.EVENT_TYPE" )]
		public function viewSearchResult( event:ViewSearchResultEvent ):void
		{
			searchModel.viewSearchResult( event.searchResultIndex );
			
		}
		
		/**
		 * handle call to remove a <code>SearchResult</code> from 
		 * the list
		 * @param event <code>RemoveSearchEvent</code>
		 * @return void
		 */
		[Mediate( event="RemoveSearchEvent.EVENT_TYPE" )]
		public function removeSearchResult( event:RemoveSearchEvent ):void
		{
			searchModel.removeSearchResult( event.index );
		}

		/**
		 * responde to users request to change teh side on all cubes in results
		 * 
		 * @param event <code>CubeSideChangeEvent</code>
		 * @return void
		 */
		[Mediate( event="CubeSideChangeEvent.CHANGE_GLOBAL" )]
		public function globalCubeSideChange( event:CubeSideChangeEvent ):void
		{
			searchModel.changeAllCubes( event.newIndex );
		}
		
		/**
		 * respond to users request to view a particular 
		 * users profile
		 * 
		 * @param event <code>DisplayProfileEvent</code>
		 * @return void
		 * 
		 */
		[Mediate( event="DisplayProfileEvent.SHOW_PROFILE" )]
		public function loadAndDisplayAProfile( event:DisplayProfileEvent ):void
		{
			var res:Responder = new Responder( onLoadAndDisplayAProfile,  faultHandler );
			var token:AsyncToken = profileDelegate.getPersonDto( event.personId );
			token.addResponder( res );
		}
		
		public function onLoadAndDisplayAProfile( event:ResultEvent ):void
		{
			
		}
		
		
		/**
		 * advaced search call
		 * @param event a instance of <code>SearchEvent</code> 
		 * @returns void
		 * 
		 */
		[Mediate( event="SearchEvent.SEARCH" )]
		public function search( event:SearchEvent ):void
		{
				timeDelay = getTimer();
				//before sending on the way, massage the data some
				//decided to do here so the component throwing the search event
				//does not need to know it
				event.search.user = applicationModel.currentPerson.user;
				event.search.company = ""; //TBD!!!! TODO
				var res:Responder = new Responder( onSearchReturn,  faultHandler );
				var token:AsyncToken = delegate.performSearch( event.search );
				token.addResponder( res );
				
				//we switch to the search results view now
				applicationModel.mainViewState = MainViewIndex.SEARCH;
				
		}
		
		/**
		 * @private
		 * handle the advanced search results call
		 * 
		 * @param event <code>ResultEvent</code>
		 * @returns void
		 * 
		 */
		private function onSearchReturn( event:ResultEvent ):void
		{
			searchModel.addSearchResult( event.result as SearchDto );
		}
		
	}
}