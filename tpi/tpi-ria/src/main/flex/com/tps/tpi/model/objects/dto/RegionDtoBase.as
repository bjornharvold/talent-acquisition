/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (RegionDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class RegionDtoBase extends AbstractReferenceDataDto {

        private var _children:ListCollectionView;
        private var _countries:ListCollectionView;
        private var _parent:String;
        private var _parentName:String;

        public function set children(value:ListCollectionView):void {
            _children = value;
        }
        public function get children():ListCollectionView {
            return _children;
        }

        public function set countries(value:ListCollectionView):void {
            _countries = value;
        }
        public function get countries():ListCollectionView {
            return _countries;
        }

        public function set parent(value:String):void {
            _parent = value;
        }
        public function get parent():String {
            return _parent;
        }

        public function set parentName(value:String):void {
            _parentName = value;
        }
        public function get parentName():String {
            return _parentName;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _children = input.readObject() as ListCollectionView;
            _countries = input.readObject() as ListCollectionView;
            _parent = input.readObject() as String;
            _parentName = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_children);
            output.writeObject(_countries);
            output.writeObject(_parent);
            output.writeObject(_parentName);
        }
    }
}