package com.tps.tpi.model.presentation
{
	import mx.collections.ListCollectionView;
	
	public interface ISearchModel
	{


		[Bindable(event="quickSearchCountChanged")]
		function set quickSearchCount( value:int ):void;
		function get quickSearchCount():int;

		function set searchResults( value:ListCollectionView ):void;
		function get searchResults():ListCollectionView;

	}
}