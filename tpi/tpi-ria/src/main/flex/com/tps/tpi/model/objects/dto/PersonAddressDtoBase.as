/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (PersonAddressDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class PersonAddressDtoBase extends AbstractDto {

        private var _address:AddressDto;
        private var _person:String;

        public function set address(value:AddressDto):void {
            _address = value;
        }
        public function get address():AddressDto {
            return _address;
        }

        public function set person(value:String):void {
            _person = value;
        }
        public function get person():String {
            return _person;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _address = input.readObject() as AddressDto;
            _person = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_address);
            output.writeObject(_person);
        }
    }
}