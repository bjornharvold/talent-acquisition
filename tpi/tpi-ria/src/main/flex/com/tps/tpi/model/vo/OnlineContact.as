package com.tps.tpi.model.vo
{
	[Bindable]
	public class OnlineContact
	{
		
		public var onlineType:String;
		public var value:String;
		public var isPrefered:Boolean;
		
		public function OnlineContact( onlineType:String = null, value:String = null, isPrefered:Boolean = false )
		{
			this.onlineType = onlineType;
			this.value = value;
			this.isPrefered = isPrefered;
		}

	}
}