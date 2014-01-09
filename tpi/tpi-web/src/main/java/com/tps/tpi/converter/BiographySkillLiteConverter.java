package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.BiographySkill;
import com.tps.tpi.model.objects.entity.BiographySkillEndorsement;
import com.tps.tpi.model.objects.lite.AbstractLite;
import com.tps.tpi.model.objects.lite.BiographySkillLite;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a BiographySkill entity to a BiographySkillLite object.
 */
public class BiographySkillLiteConverter extends AbstractLiteConverter {

    @Override
    protected AbstractLite toDto(AbstractLite lite, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof BiographySkill && lite instanceof BiographySkillLite) {
            BiographySkill entity = (BiographySkill) source;

            ((BiographySkillLite) lite).setProficiency(entity.getProficiency().name());

            if (entity.getSkill() != null) {
                ((BiographySkillLite) lite).setSkill(entity.getSkill().getShortName());
            }

            if (entity.getBiographySkillEndorsements() != null && !entity.getBiographySkillEndorsements().isEmpty()) {
                int size = entity.getBiographySkillEndorsements().size();
                int stars = 0;

                ((BiographySkillLite) lite).setEndorsements(size);

                for (BiographySkillEndorsement endorsement : entity.getBiographySkillEndorsements()) {
                    if (endorsement.getEndorsement().getStars() != null) {
                        stars += endorsement.getEndorsement().getStars();
                    }
                }

                ((BiographySkillLite) lite).setAverageStars(stars / size);
            } else {
                ((BiographySkillLite) lite).setEndorsements(0);
                ((BiographySkillLite) lite).setAverageStars(0);
            }
        }

        return lite;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractLite source, Class destClass, Class sourceClass) {

        if (source instanceof BiographySkillLite && entity instanceof BiographySkill) {
            BiographySkillLite lite = (BiographySkillLite) source;
        }

        return entity;
    }
}