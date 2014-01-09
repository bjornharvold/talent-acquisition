package com.tps.tpi.view.components
{
	import com.tps.tpi.view.EmbeddedAssets;
	
	import flash.events.IOErrorEvent;

	public class ProfileImage extends SmoothImage
	{
		public function ProfileImage()
		{
			super();
			addEventListener( IOErrorEvent.IO_ERROR, onError );
		}
		
		private function onError( event:IOErrorEvent ):void
		{
			this.source = EmbeddedAssets.MISSING_PROFILE_IMAGE;
		}
		
	}
}