/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (HistoryDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;
    import mx.collections.ListCollectionView;

    [Bindable]
    public class HistoryDtoBase extends AbstractDto {

        private var _affiliations:ListCollectionView;
        private var _coverages:ListCollectionView;
        private var _person:String;
        private var _positions:ListCollectionView;
        private var _projects:ListCollectionView;

        public function set affiliations(value:ListCollectionView):void {
            _affiliations = value;
        }
        public function get affiliations():ListCollectionView {
            return _affiliations;
        }

        public function set coverages(value:ListCollectionView):void {
            _coverages = value;
        }
        public function get coverages():ListCollectionView {
            return _coverages;
        }

        public function set person(value:String):void {
            _person = value;
        }
        public function get person():String {
            return _person;
        }

        public function set positions(value:ListCollectionView):void {
            _positions = value;
        }
        public function get positions():ListCollectionView {
            return _positions;
        }

        public function set projects(value:ListCollectionView):void {
            _projects = value;
        }
        public function get projects():ListCollectionView {
            return _projects;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _affiliations = input.readObject() as ListCollectionView;
            _coverages = input.readObject() as ListCollectionView;
            _person = input.readObject() as String;
            _positions = input.readObject() as ListCollectionView;
            _projects = input.readObject() as ListCollectionView;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_affiliations);
            output.writeObject(_coverages);
            output.writeObject(_person);
            output.writeObject(_positions);
            output.writeObject(_projects);
        }
    }
}