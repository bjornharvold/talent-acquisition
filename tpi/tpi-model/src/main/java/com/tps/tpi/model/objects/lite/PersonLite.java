package com.tps.tpi.model.objects.lite;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang.StringUtils;

/**
 * User: Bjorn Harvold
 * Date: Oct 22, 2009
 * Time: 1:55:30 PM
 * Responsibility:
 */
public class PersonLite extends AbstractLite implements Serializable, Comparable {
    private final static long serialVersionUID = 10001L;
    private String firstName;
    private String lastName;
    private String employmentType;
    private String currentBiographySkilledRole;
    private String email;
    private String phone;
    private String phoneCountryCode;
    private String phoneExtension;
    private String timezone;
    private String city;
    private String country;
    private String state;
    private String profileImageUrlSmall;
    private List<LanguageLite> languages;
    private List<BiographySkillLite> biographySkills;
    private Float relevancy;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmploymentType() {
        return employmentType;
    }

    public void setEmploymentType(String employmentType) {
        this.employmentType = employmentType;
    }

    public String getCurrentBiographySkilledRole() {
        return currentBiographySkilledRole;
    }

    public void setCurrentBiographySkilledRole(String currentBiographySkilledRole) {
        this.currentBiographySkilledRole = currentBiographySkilledRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPhoneCountryCode() {
        return phoneCountryCode;
    }

    public void setPhoneCountryCode(String phoneCountryCode) {
        this.phoneCountryCode = phoneCountryCode;
    }

    public String getPhoneExtension() {
        return phoneExtension;
    }

    public void setPhoneExtension(String phoneExtension) {
        this.phoneExtension = phoneExtension;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getProfileImageUrlSmall() {
        return profileImageUrlSmall;
    }

    public void setProfileImageUrlSmall(String profileImageUrlSmall) {
        this.profileImageUrlSmall = profileImageUrlSmall;
    }

    public List<LanguageLite> getLanguages() {
        return languages;
    }

    public void setLanguages(List<LanguageLite> languages) {
        this.languages = languages;
    }

    public List<BiographySkillLite> getBiographySkills() {
        return biographySkills;
    }

    public void setBiographySkills(List<BiographySkillLite> biographySkills) {
        this.biographySkills = biographySkills;
    }

    public Float getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(Float relevancy) {
        this.relevancy = relevancy;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void addLanguage(LanguageLite language) {
        if (languages == null) {
            languages = new ArrayList<LanguageLite>();
        }

        boolean isNew = true;
        for (LanguageLite lite : languages) {
            if (StringUtils.equals(language.getId(), lite.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            languages.add(language);
        }
    }

    public void addBiographySkill(BiographySkillLite biographySkill) {
        if (biographySkills == null) {
            biographySkills = new ArrayList<BiographySkillLite>();
        }

        boolean isNew = true;
        for (BiographySkillLite lite : biographySkills) {
            if (StringUtils.equals(biographySkill.getId(), lite.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographySkills.add(biographySkill);
        }
    }

    @Override
    public int compareTo(Object o) {
        int result = 0;

        if (o instanceof PersonLite) {
            PersonLite compareTo = (PersonLite) o;

            if (this.getRelevancy() < compareTo.getRelevancy()) {
                result = 1;
            } else if (this.getRelevancy() == this.getRelevancy()) {
                result = 0;
            } else if (this.getRelevancy() > this.getRelevancy()) {
                result = -1;
            }
        }

        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PersonLite {");
        sb.append(super.toString());
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", employmentType='").append(employmentType).append('\'');
        sb.append(", currentBiographySkilledRole='").append(currentBiographySkilledRole).append('\'');
        sb.append(", email='").append(email).append('\'');
        sb.append(", phone='").append(phone).append('\'');
        sb.append(", timezone='").append(timezone).append('\'');
        sb.append(", city='").append(city).append('\'');
        sb.append(", country='").append(country).append('\'');
        sb.append(", state='").append(state).append('\'');
        sb.append(", profileImageUrlSmall='").append(profileImageUrlSmall).append('\'');
        sb.append(", relevancy=").append(relevancy);
        sb.append('}');
        return sb.toString();
    }
}
