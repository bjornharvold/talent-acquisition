/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package com.tps.tpi.model.objects.enums {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="com.tps.tpi.model.objects.enums.PhoneTypeCd")]
    public class PhoneTypeCd extends Enum {

        public static const HOME:PhoneTypeCd = new PhoneTypeCd("HOME", _);
        public static const WORK:PhoneTypeCd = new PhoneTypeCd("WORK", _);
        public static const MOBILE:PhoneTypeCd = new PhoneTypeCd("MOBILE", _);
        public static const FAX:PhoneTypeCd = new PhoneTypeCd("FAX", _);
        public static const SKYPE_OUT:PhoneTypeCd = new PhoneTypeCd("SKYPE_OUT", _);
        public static const GIZMO:PhoneTypeCd = new PhoneTypeCd("GIZMO", _);
        public static const SIP:PhoneTypeCd = new PhoneTypeCd("SIP", _);

        function PhoneTypeCd(value:String = null, restrictor:* = null) {
            super((value || HOME.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [HOME, WORK, MOBILE, FAX, SKYPE_OUT, GIZMO, SIP];
        }

        public static function valueOf(name:String):PhoneTypeCd {
            return PhoneTypeCd(HOME.constantOf(name));
        }
    }
}