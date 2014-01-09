package com.tps.tpi.model.vo
{
	[Bindable]
	public class FlashParams
	{
		
		public var destination:String = "" ;
		public var host:String = "localhost";
		public var port:int = 8080;
		public var contextRoot:String = "";

		public function FlashParams()
		{
				
		}
	}
}