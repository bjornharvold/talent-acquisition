/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package com.tps.tpi.model.objects.enums {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="com.tps.tpi.model.objects.enums.SexCd")]
    public class SexCd extends Enum {

        public static const M:SexCd = new SexCd("M", _);
        public static const F:SexCd = new SexCd("F", _);

        function SexCd(value:String = null, restrictor:* = null) {
            super((value || M.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [M, F];
        }

        public static function valueOf(name:String):SexCd {
            return SexCd(M.constantOf(name));
        }
    }
}