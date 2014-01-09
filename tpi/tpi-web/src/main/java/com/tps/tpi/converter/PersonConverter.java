package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.*;
import com.tps.tpi.model.objects.dto.*;
import com.tps.tpi.model.objects.enums.MaritalStatusCd;
import com.tps.tpi.model.objects.enums.PersonStatusCd;
import com.tps.tpi.model.objects.enums.EmploymentTypeCd;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Person entity to a PersonDto dto. We are assuming the Person entity will ALWAYS
 * be created before the dto so when we convert back to the entity, we don't have to update every field because they will
 * always be there.
 */
public class PersonConverter extends AbstractConverter {

    @Override
    protected AbstractDto toDto(AbstractDto dto, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Person && dto instanceof PersonDto) {
            Person entity = (Person) source;

            ((PersonDto) dto).setAim(entity.getAim());
            ((PersonDto) dto).setAvailability(entity.getAvailability());
            ((PersonDto) dto).setDisability(entity.getDisability());
            ((PersonDto) dto).setFacebook(entity.getFacebook());
            ((PersonDto) dto).setFax(entity.getFax());
            ((PersonDto) dto).setFirstName(entity.getFirstName());
            ((PersonDto) dto).setGtalk(entity.getGtalk());
            ((PersonDto) dto).setHomeEmail(entity.getHomeEmail());
            ((PersonDto) dto).setHomePhone(entity.getHomePhone());
            ((PersonDto) dto).setHomePhoneExtension(entity.getHomePhoneExtension());
            ((PersonDto) dto).setHomePhoneCountryCode(entity.getHomePhoneCountryCode());
            ((PersonDto) dto).setMobilePhone(entity.getMobilePhone());
            ((PersonDto) dto).setMobilePhoneCountryCode(entity.getMobilePhoneCountryCode());
            ((PersonDto) dto).setMobilePhoneExtension(entity.getMobilePhoneExtension());
            ((PersonDto) dto).setLastName(entity.getLastName());
            ((PersonDto) dto).setMaritalStatus(entity.getMaritalStatus().name());
            ((PersonDto) dto).setMiddleName(entity.getMiddleName());
            ((PersonDto) dto).setMsn(entity.getMsn());
            ((PersonDto) dto).setPreferredEmail(entity.getPreferredEmail());
            ((PersonDto) dto).setPreferredPhone(entity.getPreferredPhone());
            ((PersonDto) dto).setPreferredPhoneCountryCode(entity.getPreferredPhoneCountryCode());
            ((PersonDto) dto).setPreferredPhoneExtension(entity.getPreferredPhoneExtension());
            ((PersonDto) dto).setPreferredSalary(entity.getPreferredSalary());
            ((PersonDto) dto).setCurrentSalary(entity.getCurrentSalary());
            ((PersonDto) dto).setProfileImageUrl(entity.getProfileImageUrl());
            ((PersonDto) dto).setProfileImageUrlSmall(entity.getProfileImageUrlSmall());
            ((PersonDto) dto).setProfileImageUrlLarge(entity.getProfileImageUrlLarge());
            ((PersonDto) dto).setRace(entity.getRace());
            ((PersonDto) dto).setSkype(entity.getSkype());
            ((PersonDto) dto).setStatus(entity.getStatus().name());
            ((PersonDto) dto).setTimezone(entity.getTimezone());
            ((PersonDto) dto).setTwitter(entity.getTwitter());
            ((PersonDto) dto).setUser(entity.getUser().getId());
            ((PersonDto) dto).setWorkEmail(entity.getWorkEmail());
            ((PersonDto) dto).setWorkPhone(entity.getWorkPhone());
            ((PersonDto) dto).setWorkPhoneCountryCode(entity.getWorkPhoneCountryCode());
            ((PersonDto) dto).setWorkPhoneExtension(entity.getWorkPhoneExtension());
            ((PersonDto) dto).setYahoo(entity.getYahoo());
            ((PersonDto) dto).setLastLogin(entity.getLastLogin());
            ((PersonDto) dto).setLastRecordUpdate(entity.getLastRecordUpdate());
            ((PersonDto) dto).setCurrentEmploymentType(entity.getCurrentEmploymentType().name());

            if (entity.getCurrentLocation() != null) {
                ((PersonDto) dto).setCurrentLocation(mapper.map(entity.getCurrentLocation(), CityDto.class));
            }

            if (entity.getCurrentDepartment() != null) {
                ((PersonDto) dto).setCurrentDepartment(mapper.map(entity.getCurrentDepartment(), DepartmentDto.class));
            }

            if (entity.getCurrentBiographySkilledRole() != null) {
                ((PersonDto) dto).setCurrentBiographySkilledRole(mapper.map(entity.getCurrentBiographySkilledRole(), BiographySkilledRoleDto.class));
            }

            if (entity.getPersonAddresses() != null) {
                for (PersonAddress pa : entity.getPersonAddresses()) {
                    ((PersonDto) dto).addPersonAddress(mapper.map(pa, PersonAddressDto.class));
                }
            }

            if (entity.getPreferredPersonAddress() != null) {
                ((PersonDto) dto).setPreferredPersonAddress(mapper.map(entity.getPreferredPersonAddress(), PersonAddressDto.class));
            }

            if (entity.getInterests() != null) {
                for (Interest interest : entity.getInterests()) {
                    ((PersonDto) dto).addInterest(mapper.map(interest, InterestDto.class));
                }
            }
        }

        return dto;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractDto source, Class destClass, Class sourceClass) {
        
        if (source instanceof PersonDto && entity instanceof Person) {
            PersonDto dto = (PersonDto) source;

            ((Person) entity).setAim(dto.getAim());
            ((Person) entity).setAvailability(dto.getAvailability());
            ((Person) entity).setDisability(dto.getDisability());
            ((Person) entity).setFacebook(dto.getFacebook());
            ((Person) entity).setFax(dto.getFax());
            ((Person) entity).setFirstName(dto.getFirstName());
            ((Person) entity).setGtalk(dto.getGtalk());
            ((Person) entity).setHomeEmail(dto.getHomeEmail());
            ((Person) entity).setHomePhone(dto.getHomePhone());
            ((Person) entity).setHomePhoneCountryCode(dto.getHomePhoneCountryCode());
            ((Person) entity).setHomePhoneExtension(dto.getHomePhoneExtension());
            ((Person) entity).setMobilePhone(dto.getMobilePhone());
            ((Person) entity).setMobilePhoneCountryCode(dto.getMobilePhoneCountryCode());
            ((Person) entity).setMobilePhoneExtension(dto.getMobilePhoneExtension());
            ((Person) entity).setLastName(dto.getLastName());
            ((Person) entity).setMaritalStatus(MaritalStatusCd.valueOf(dto.getMaritalStatus()));
            ((Person) entity).setMiddleName(dto.getMiddleName());
            ((Person) entity).setMsn(dto.getMsn());
            ((Person) entity).setPreferredEmail(dto.getPreferredEmail());
            ((Person) entity).setPreferredPhone(dto.getPreferredPhone());
            ((Person) entity).setPreferredPhoneCountryCode(dto.getPreferredPhoneCountryCode());
            ((Person) entity).setPreferredPhoneExtension(dto.getPreferredPhoneExtension());
            ((Person) entity).setPreferredSalary(dto.getPreferredSalary());
            ((Person) entity).setCurrentSalary(dto.getCurrentSalary());
            ((Person) entity).setProfileImageUrl(dto.getProfileImageUrl());
            ((Person) entity).setProfileImageUrlSmall(dto.getProfileImageUrlSmall());
            ((Person) entity).setProfileImageUrlLarge(dto.getProfileImageUrlLarge());
            ((Person) entity).setRace(dto.getRace());
            ((Person) entity).setSkype(dto.getSkype());
            ((Person) entity).setStatus(PersonStatusCd.valueOf(dto.getStatus()));
            ((Person) entity).setTimezone(dto.getTimezone());
            ((Person) entity).setTwitter(dto.getTwitter());
            ((Person) entity).setWorkEmail(dto.getWorkEmail());
            ((Person) entity).setWorkPhone(dto.getWorkPhone());
            ((Person) entity).setWorkPhoneExtension(dto.getWorkPhoneExtension());
            ((Person) entity).setWorkPhoneCountryCode(dto.getWorkPhoneCountryCode());
            ((Person) entity).setYahoo(dto.getYahoo());
            ((Person) entity).setLastLogin(dto.getLastLogin());
            ((Person) entity).setLastRecordUpdate(dto.getLastRecordUpdate());
            ((Person) entity).setCurrentEmploymentType(EmploymentTypeCd.valueOf(dto.getCurrentEmploymentType()));

            if (dto.getCurrentLocation() != null) {
                ((Person) entity).setCurrentLocation(mapper.map(dto.getCurrentLocation(), City.class));
            }
        }

        return entity;
    }
}
