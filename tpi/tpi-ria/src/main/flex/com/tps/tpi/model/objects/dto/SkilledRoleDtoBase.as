/**
 * Generated by Gas3 v2.0.0 (Granite Data Services).
 *
 * WARNING: DO NOT CHANGE THIS FILE. IT MAY BE OVERWRITTEN EACH TIME YOU USE
 * THE GENERATOR. INSTEAD, EDIT THE INHERITED CLASS (SkilledRoleDto.as).
 */

package com.tps.tpi.model.objects.dto {

    import flash.utils.IDataInput;
    import flash.utils.IDataOutput;

    [Bindable]
    public class SkilledRoleDtoBase extends AbstractReferenceDataDto {

        private var _skilledRoleGroup:String;

        public function set skilledRoleGroup(value:String):void {
            _skilledRoleGroup = value;
        }
        public function get skilledRoleGroup():String {
            return _skilledRoleGroup;
        }

        override public function readExternal(input:IDataInput):void {
            super.readExternal(input);
            _skilledRoleGroup = input.readObject() as String;
        }

        override public function writeExternal(output:IDataOutput):void {
            super.writeExternal(output);
            output.writeObject(_skilledRoleGroup);
        }
    }
}