package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Person;
import com.tps.tpi.model.objects.entity.Language;
import com.tps.tpi.model.objects.entity.BiographySkill;
import com.tps.tpi.model.objects.lite.AbstractLite;
import com.tps.tpi.model.objects.lite.PersonLite;
import com.tps.tpi.model.objects.lite.LanguageLite;
import com.tps.tpi.model.objects.lite.BiographySkillLite;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Person entity to a PersonLite object.
 */
public class PersonLiteConverter extends AbstractLiteConverter {

    @Override
    protected AbstractLite toDto(AbstractLite lite, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Person && lite instanceof PersonLite) {
            Person entity = (Person) source;

            ((PersonLite) lite).setFirstName(entity.getFirstName());
            ((PersonLite) lite).setLastName(entity.getLastName());
            ((PersonLite) lite).setEmail(entity.getPreferredEmail());
            ((PersonLite) lite).setTimezone(entity.getTimezone());
            ((PersonLite) lite).setPhone(entity.getPreferredPhone());
            ((PersonLite) lite).setPhoneCountryCode(entity.getPreferredPhoneCountryCode());
            ((PersonLite) lite).setPhoneExtension(entity.getPreferredPhoneExtension());
            ((PersonLite) lite).setProfileImageUrlSmall(entity.getProfileImageUrlSmall());

            ((PersonLite) lite).setRelevancy(entity.getRelevancy());

            if (entity.getCurrentBiographySkilledRole() != null) {
                ((PersonLite) lite).setCurrentBiographySkilledRole(entity.getCurrentBiographySkilledRole().getSkilledRole().getShortName());
            }

            if (entity.getCurrentEmploymentType() != null) {
                ((PersonLite) lite).setEmploymentType(entity.getCurrentEmploymentType().name());
            }

            if (entity.getPreferredPersonAddress() != null) {
                ((PersonLite) lite).setCity(entity.getPreferredPersonAddress().getAddress().getCity().getShortName());
                ((PersonLite) lite).setCountry(entity.getPreferredPersonAddress().getAddress().getCity().getCountry().getShortName());
                ((PersonLite) lite).setState(entity.getPreferredPersonAddress().getAddress().getState());
            }

            if (entity.getEducations() != null && entity.getEducations().get(0).getLanguages() != null) {
                for (Language language : entity.getEducations().get(0).getLanguages()) {
                    ((PersonLite) lite).addLanguage(mapper.map(language, LanguageLite.class));
                }
            }

            if (entity.getBiographies() != null && entity.getBiographies().get(0).getBiographySkills() != null) {
                for (BiographySkill skill : entity.getBiographies().get(0).getBiographySkills()) {
                    ((PersonLite) lite).addBiographySkill(mapper.map(skill, BiographySkillLite.class));
                }
            }
        }

        return lite;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractLite source, Class destClass, Class sourceClass) {

        if (source instanceof PersonLite && entity instanceof Person) {
            PersonLite dto = (PersonLite) source;
        }

        return entity;
    }
}