package com.tps.tpi.controller.delegates
{
	import com.tps.tpi.model.objects.dto.SearchDto;
	
	import mx.rpc.AsyncToken;
	import mx.rpc.remoting.mxml.RemoteObject;
	
	public class SearchDelegate
	{
		public function SearchDelegate()
		{
		}

		[Autowire( bean="searchService")]
		public var searchService:RemoteObject;
		
		
		public function performQuickSearch( keywords:String, index:int = 0, maxResults:int = 10000 ):AsyncToken
		{
			return searchService.quickSearch( keywords, index, maxResults);
		}

		public function performSearchCount( search:SearchDto ):AsyncToken
		{
			return searchService.searchCount( search );
		}

		public function performAdvancedSearch( searchParams:Array, index:int = 0, maxResults:int = 10000 ):AsyncToken
		{
			return searchService.advancedSearch( searchParams, index, maxResults );
		}
		
		public function performSearch( search:SearchDto, index:int = 0, maxResults:int = 10000 ):AsyncToken
		{
			return searchService.search( search, index, maxResults);
		}
	}
}