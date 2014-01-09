/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package com.tps.tpi.model.objects.enums {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="com.tps.tpi.model.objects.enums.ProficiencyCd")]
    public class ProficiencyCd extends Enum {

        public static const EXPERT:ProficiencyCd = new ProficiencyCd("EXPERT", _);
        public static const COMPETENT:ProficiencyCd = new ProficiencyCd("COMPETENT", _);
        public static const BASIC:ProficiencyCd = new ProficiencyCd("BASIC", _);

        function ProficiencyCd(value:String = null, restrictor:* = null) {
            super((value || EXPERT.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [EXPERT, COMPETENT, BASIC];
        }

        public static function valueOf(name:String):ProficiencyCd {
            return ProficiencyCd(EXPERT.constantOf(name));
        }
    }
}