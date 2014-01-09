package com.tps.tpi.view.components
{
	import com.tps.tpi.model.objects.dto.AbstractDto;
	import com.tps.tpi.utils.DateUtils;

	public class VerifiedDataLabel extends LabelDottedUnderLine
	{
		private static const ENTERPRISE:String = "ENTERPRISE";
		
		private var baseDataIsDirty:Boolean = false;
		private var _baseData:AbstractDto;
		public function get baseData():AbstractDto
		{
			return _baseData;
		}
		public function set baseData( value:AbstractDto ):void
		{
			if( value )
			{	_baseData = value;
				baseDataIsDirty = true;
				invalidateProperties();
			}
		}
		
		
		/**
		 * constructor
		 */
		public function VerifiedDataLabel()
		{
			super();
			truncateToFit = true;
			
		}
		
		/**
		 * hardwored company name for now
		 */
		override protected function commitProperties():void
		{
			super.commitProperties();
			
			if( baseDataIsDirty )
			{
				baseDataIsDirty = false;
				if( baseData.recordCreatorType == ENTERPRISE )
				{
					showDottedLine = true;	
					toolTip = resourceManager.getString("resources", "source" ) + ": Metrobank, " + DateUtils.formatDate( baseData.createdDate );	
				}
				else
				{
					showDottedLine = false;
					toolTip = "";
				}
			}
			
		}
		
	}
}