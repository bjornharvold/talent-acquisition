/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (DepartmentDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class DepartmentDtoBase extends AbstractReferenceDataDto {

        private var _division:String;
        private var _parent:String;

        public function set division(value:String):void {
            _division = value;
        }
        public function get division():String {
            return _division;
        }

        public function set parent(value:String):void {
            _parent = value;
        }
        public function get parent():String {
            return _parent;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _division = input.readObject() as String;
            _parent = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_division);
            output.writeObject(_parent);
        }
    }
}