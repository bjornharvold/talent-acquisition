/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (ProductDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class ProductDtoBase extends AbstractReferenceDataDto {

        private var _company:String;

        public function set company(value:String):void {
            _company = value;
        }
        public function get company():String {
            return _company;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _company = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_company);
        }
    }
}