/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (LanguageDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class LanguageDtoBase extends AbstractDto {

        private var _education:String;
        private var _languageEndorsements:ListCollectionView;
        private var _readwrite:String;
        private var _type:String;
        private var _verbal:String;

        public function set education(value:String):void {
            _education = value;
        }
        public function get education():String {
            return _education;
        }

        public function set languageEndorsements(value:ListCollectionView):void {
            _languageEndorsements = value;
        }
        public function get languageEndorsements():ListCollectionView {
            return _languageEndorsements;
        }

        public function set readwrite(value:String):void {
            _readwrite = value;
        }
        public function get readwrite():String {
            return _readwrite;
        }

        public function set type(value:String):void {
            _type = value;
        }
        public function get type():String {
            return _type;
        }

        public function set verbal(value:String):void {
            _verbal = value;
        }
        public function get verbal():String {
            return _verbal;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _education = input.readObject() as String;
            _languageEndorsements = input.readObject() as ListCollectionView;
            _readwrite = input.readObject() as String;
            _type = input.readObject() as String;
            _verbal = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_education);
            output.writeObject(_languageEndorsements);
            output.writeObject(_readwrite);
            output.writeObject(_type);
            output.writeObject(_verbal);
        }
    }
}