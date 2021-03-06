package ws.tink.flex.managers
{
	
	
	import flash.display.DisplayObject;
	
	import mx.core.Application;
	import mx.core.UIComponent;
	import mx.events.EffectEvent;
	import mx.events.FlexEvent;
	
	import ws.tink.flex.pv3dEffects.effectClasses.CacheInstance;
	import ws.tink.flex.pv3dEffects.effectClasses.EffectInstance;


	public class EffectManager extends UIComponent
	{

		
		private static var instance			: EffectManager;
		private static var allowCreation	: Boolean;

		private var _cache			: Array;

		
		public static function get effectManager():EffectManager
		{			
			if( !instance )
 			{
 				allowCreation = true;
 				instance = new EffectManager();
 				allowCreation = false;
 				
 				Application.application.systemManager.addChild( instance );
			}
			
			return instance;
		}
		
		public function EffectManager()
		{
			super();
			
			if( !allowCreation ) throw new Error( "EffectManager is a singleton and can only be accessed through EffectManager.effectManager" );
		}
		
		public function addEffect( effect:EffectInstance ):void
		{
			effect.addEventListener( FlexEvent.CREATION_COMPLETE, onEffectCreationComplete, false, 0, true );
			effect.addEventListener( EffectEvent.EFFECT_START, onEffectStart, false, 0, true );
			effect.addEventListener( EffectEvent.EFFECT_END, onEffectEnd, false, 0, true );
		}
		
		public function removeEffect( effect:EffectInstance ):void
		{
			effect.removeEventListener( FlexEvent.CREATION_COMPLETE, onEffectCreationComplete, false );
			effect.removeEventListener( EffectEvent.EFFECT_START, onEffectStart, false );
			effect.removeEventListener( EffectEvent.EFFECT_END, onEffectEnd, false );
			if(effect.display != null){
				if(effect.display.parent == this){
					removeChild( effect.display );
				}
			}
			effect.destroy();
		}
		
		public function getPrevEffect( effect:EffectInstance ):CacheInstance
		{
			var cachedEffectIndex:int = getPrevEffectIndex( effect );
			
			if( cachedEffectIndex != -1 ) return CacheInstance( _cache[ cachedEffectIndex ] );
			
			return null;
		}
		
	
		
		override public function initialize():void
		{
			super.initialize();
			
			mouseEnabled = false;
			mouseChildren = false;
			
			_cache = new Array();
		}
		

		private function onEffectStart( event:EffectEvent ):void
		{
			var effect:EffectInstance = EffectInstance( event.target );			
			addChild( effect.display );
		}
		
		private function onEffectCreationComplete( event:FlexEvent ):void
		{
			removePrevCachedEffect( EffectInstance( event.currentTarget ) );
		}
		
		private function onEffectEnd( event:EffectEvent ):void
		{
			var effect:EffectInstance = EffectInstance( event.target );

			
			
			if( effect is CacheInstance )
			{
				if( CacheInstance( effect ).cache )
				{
					_cache.push( effect );
					return;
				}
			}
			
			removeEffect( effect );
		}
		
		
		private function getPrevEffectIndex( effect:EffectInstance ):int
		{
			var cache:EffectInstance;
			var numCached:int = _cache.length;
			
			for( var i:int = numCached-1; i >= 0; i-- )
			{
				cache = CacheInstance( _cache[ i ] );
				if( cache.target.parent == effect.target.parent ) return i;
			}

			return -1;
		}
		
		private function removePrevCachedEffect( effect:EffectInstance ):void
		{
			if(_cache.length > 1){
				var cachedEffectIndex:int = 0;//getPrevEffectIndex( effect );
				if( cachedEffectIndex != -1 ) removeEffect( CacheInstance( _cache.splice( cachedEffectIndex, 1 )[ 0 ] ) );
			}
			for(var i:Number=0;i<_cache.length;i++){
				if(CacheInstance( _cache[i]).display != null){
					var display:DisplayObject = CacheInstance( _cache[i]).display as DisplayObject;
					try{
						if ( getChildIndex( display ) != -1 )
							removeChild( display );
					}catch(e:Error){}
				}
			}
		}
	}
}