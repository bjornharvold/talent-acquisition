/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * NOTE: this file is only generated if it does not exist. You may safely put
 * your custom code here.
 */

package com.tps.tpi.model.objects.dto {
	import com.tps.tpi.model.constants.SearchResultsViewIndex;

    [Bindable]
    [RemoteClass(alias="com.tps.tpi.model.objects.dto.SearchDto")]
    public class SearchDto extends SearchDtoBase {
		
		/**
		 * keep track of the view type for this result
		 * table, tile, list....
		 */
		[Transient]
		public var currentResultsViewType:int = SearchResultsViewIndex.BLANK;
    }
}