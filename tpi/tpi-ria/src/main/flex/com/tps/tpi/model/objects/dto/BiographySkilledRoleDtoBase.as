/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (BiographySkilledRoleDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class BiographySkilledRoleDtoBase extends AbstractDto {

        private var _biography:String;
        private var _skilledRole:SkilledRoleDto;
        private var _years:Number;

        public function set biography(value:String):void {
            _biography = value;
        }
        public function get biography():String {
            return _biography;
        }

        public function set skilledRole(value:SkilledRoleDto):void {
            _skilledRole = value;
        }
        public function get skilledRole():SkilledRoleDto {
            return _skilledRole;
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
            _skilledRole = input.readObject() as SkilledRoleDto;
            _years = function(o:*):Number { return (o is Number ? o as Number : Number.NaN) } (input.readObject());
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_biography);
            output.writeObject(_skilledRole);
            output.writeObject(_years);
        }
    }
}