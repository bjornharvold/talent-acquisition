package com.tps.tpi.converter;

import com.tps.tpi.model.objects.entity.AbstractEntity;
import com.tps.tpi.model.objects.entity.Language;
import com.tps.tpi.model.objects.entity.LanguageEndorsement;
import com.tps.tpi.model.objects.lite.AbstractLite;
import com.tps.tpi.model.objects.lite.LanguageLite;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:11:50 AM
 * Responsibility: This class converts a Language entity to a LanguageLite object.
 */
public class LanguageLiteConverter extends AbstractLiteConverter {

    @Override
    protected AbstractLite toDto(AbstractLite lite, AbstractEntity source, Class destClass, Class sourceClass) {
        if (source instanceof Language && lite instanceof LanguageLite) {
            Language entity = (Language) source;

            ((LanguageLite) lite).setReadwrite(entity.getReadwrite().name());
            ((LanguageLite) lite).setType(entity.getType().name());
            ((LanguageLite) lite).setVerbal(entity.getVerbal().name());

            if (entity.getLanguageEndorsements() != null && !entity.getLanguageEndorsements().isEmpty()) {
                int size = entity.getLanguageEndorsements().size();
                int stars = 0;

                ((LanguageLite) lite).setEndorsements(size);

                for (LanguageEndorsement endorsement : entity.getLanguageEndorsements()) {
                    if (endorsement.getEndorsement().getStars() != null) {
                        stars += endorsement.getEndorsement().getStars();
                    }
                }

                ((LanguageLite) lite).setAverageStars(stars / size);
            } else {
                ((LanguageLite) lite).setEndorsements(0);
                ((LanguageLite) lite).setAverageStars(0);
            }
        }

        return lite;
    }

    @Override
    protected AbstractEntity toEntity(AbstractEntity entity, AbstractLite source, Class destClass, Class sourceClass) {

        if (source instanceof LanguageLite && entity instanceof Language) {
            LanguageLite lite = (LanguageLite) source;
        }

        return entity;
    }
}