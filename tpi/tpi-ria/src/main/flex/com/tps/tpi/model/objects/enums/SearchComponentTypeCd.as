/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR.
 */

package com.tps.tpi.model.objects.enums {

    import org.granite.util.Enum;

    [Bindable]
    [RemoteClass(alias="com.tps.tpi.model.objects.enums.SearchComponentTypeCd")]
    public class SearchComponentTypeCd extends Enum {

        public static const NAME:SearchComponentTypeCd = new SearchComponentTypeCd("NAME", _);
        public static const EMAIL:SearchComponentTypeCd = new SearchComponentTypeCd("EMAIL", _);
        public static const PHONE:SearchComponentTypeCd = new SearchComponentTypeCd("PHONE", _);
        public static const LOCATION:SearchComponentTypeCd = new SearchComponentTypeCd("LOCATION", _);
        public static const SKILLS_EXPERTISE:SearchComponentTypeCd = new SearchComponentTypeCd("SKILLS_EXPERTISE", _);
        public static const COMPANY_INDUSTRY:SearchComponentTypeCd = new SearchComponentTypeCd("COMPANY_INDUSTRY", _);
        public static const POSITION_ROLES:SearchComponentTypeCd = new SearchComponentTypeCd("POSITION_ROLES", _);
        public static const PROJECTS:SearchComponentTypeCd = new SearchComponentTypeCd("PROJECTS", _);
        public static const COVERAGE:SearchComponentTypeCd = new SearchComponentTypeCd("COVERAGE", _);
        public static const AVAILABILITY:SearchComponentTypeCd = new SearchComponentTypeCd("AVAILABILITY", _);
        public static const EMPLOYMENT_TYPE:SearchComponentTypeCd = new SearchComponentTypeCd("EMPLOYMENT_TYPE", _);
        public static const EDUCATION:SearchComponentTypeCd = new SearchComponentTypeCd("EDUCATION", _);
        public static const CERTIFICATION:SearchComponentTypeCd = new SearchComponentTypeCd("CERTIFICATION", _);
        public static const LANGUAGE:SearchComponentTypeCd = new SearchComponentTypeCd("LANGUAGE", _);
        public static const RATE:SearchComponentTypeCd = new SearchComponentTypeCd("RATE", _);
        public static const ORGANIZATION:SearchComponentTypeCd = new SearchComponentTypeCd("ORGANIZATION", _);
        public static const INTEREST:SearchComponentTypeCd = new SearchComponentTypeCd("INTEREST", _);
        public static const AWARDS:SearchComponentTypeCd = new SearchComponentTypeCd("AWARDS", _);
        public static const PUBLICATIONS:SearchComponentTypeCd = new SearchComponentTypeCd("PUBLICATIONS", _);
        public static const PATENTS:SearchComponentTypeCd = new SearchComponentTypeCd("PATENTS", _);
        public static const DEMOGRAPHIC:SearchComponentTypeCd = new SearchComponentTypeCd("DEMOGRAPHIC", _);

        function SearchComponentTypeCd(value:String = null, restrictor:* = null) {
            super((value || NAME.name), restrictor);
        }

        override protected function getConstants():Array {
            return constants;
        }

        public static function get constants():Array {
            return [NAME, EMAIL, PHONE, LOCATION, SKILLS_EXPERTISE, COMPANY_INDUSTRY, POSITION_ROLES, PROJECTS, COVERAGE, AVAILABILITY, EMPLOYMENT_TYPE, EDUCATION, CERTIFICATION, LANGUAGE, RATE, ORGANIZATION, INTEREST, AWARDS, PUBLICATIONS, PATENTS, DEMOGRAPHIC];
        }

        public static function valueOf(name:String):SearchComponentTypeCd {
            return SearchComponentTypeCd(NAME.constantOf(name));
        }
    }
}