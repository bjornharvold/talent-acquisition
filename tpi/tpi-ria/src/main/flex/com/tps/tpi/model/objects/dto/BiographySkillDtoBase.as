/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (BiographySkillDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class BiographySkillDtoBase extends AbstractDto {

        private var _biography:String;
        private var _biographySkillEndorsements:ListCollectionView;
        private var _proficiency:String;
        private var _skill:SkillDto;
        private var _years:Number;

        public function set biography(value:String):void {
            _biography = value;
        }
        public function get biography():String {
            return _biography;
        }

        public function set biographySkillEndorsements(value:ListCollectionView):void {
            _biographySkillEndorsements = value;
        }
        public function get biographySkillEndorsements():ListCollectionView {
            return _biographySkillEndorsements;
        }

        public function set proficiency(value:String):void {
            _proficiency = value;
        }
        public function get proficiency():String {
            return _proficiency;
        }

        public function set skill(value:SkillDto):void {
            _skill = value;
        }
        public function get skill():SkillDto {
            return _skill;
        }

        public function set years(value:Number):void {
            _years = value;
        }
        public function get years():Number {
            return _years;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _biography = input.readObject() as String;
            _biographySkillEndorsements = input.readObject() as ListCollectionView;
            _proficiency = input.readObject() as String;
            _skill = input.readObject() as SkillDto;
            _years = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_biography);
            output.writeObject(_biographySkillEndorsements);
            output.writeObject(_proficiency);
            output.writeObject(_skill);
            output.writeObject(_years);
        }
    }
}