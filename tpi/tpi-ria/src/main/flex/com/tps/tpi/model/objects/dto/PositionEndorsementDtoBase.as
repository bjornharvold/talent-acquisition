/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (PositionEndorsementDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class PositionEndorsementDtoBase extends AbstractDto {

        private var _endorsement:EndorsementDto;
        private var _position:String;

        public function set endorsement(value:EndorsementDto):void {
            _endorsement = value;
        }
        public function get endorsement():EndorsementDto {
            return _endorsement;
        }

        public function set position(value:String):void {
            _position = value;
        }
        public function get position():String {
            return _position;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _endorsement = input.readObject() as EndorsementDto;
            _position = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_endorsement);
            output.writeObject(_position);
        }
    }
}