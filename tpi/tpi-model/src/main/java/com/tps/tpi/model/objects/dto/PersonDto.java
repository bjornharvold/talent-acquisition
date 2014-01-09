package com.tps.tpi.model.objects.dto;

import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.io.Serializable;

/**
 * User: Bjorn Harvold
 * Date: Oct 19, 2009
 * Time: 11:06:13 AM
 * Responsibility:
 */
public class PersonDto extends AbstractDto implements Serializable {
    private final static long serialVersionUID = 1027L;
    private String user;
    private String firstName;
    private String lastName;
    private String middleName;
    private String preferredPhone;
    private CityDto currentLocation;
    private String timezone;
    private String preferredEmail;
    private Date availability;
    private String workEmail;
    private String homeEmail;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
    private String homePhoneCountryCode;
    private String homePhoneExtension;
    private String workPhoneCountryCode;
    private String workPhoneExtension;
    private String mobilePhoneCountryCode;
    private String mobilePhoneExtension;
    private String preferredPhoneCountryCode;
    private String preferredPhoneExtension;
    private String faxCountryCode;
    private String fax;
    private String twitter;
    private String facebook;
    private String gtalk;
    private String aim;
    private String skype;
    private String msn;
    private String yahoo;
    private String profileImageUrl;
    private String profileImageUrlSmall;
    private String profileImageUrlLarge;
    private List<PersonAddressDto> personAddresses;
    private List<InterestDto> interests;
    private Float preferredSalary;
    private Float currentSalary;
    private String status;
    private String maritalStatus;
    private String disability;
    private String race;
    private BiographySkilledRoleDto currentBiographySkilledRole;
    private DepartmentDto currentDepartment;
    private PersonAddressDto preferredPersonAddress;
    private Date lastRecordUpdate = new Date();
    private Date lastLogin;
    private String currentEmploymentType;

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

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

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getPreferredPhone() {
        return preferredPhone;
    }

    public void setPreferredPhone(String preferredPhone) {
        this.preferredPhone = preferredPhone;
    }

    public CityDto getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(CityDto currentLocation) {
        this.currentLocation = currentLocation;
    }

    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    public String getPreferredEmail() {
        return preferredEmail;
    }

    public void setPreferredEmail(String preferredEmail) {
        this.preferredEmail = preferredEmail;
    }

    public Date getAvailability() {
        return availability;
    }

    public void setAvailability(Date availability) {
        this.availability = availability;
    }

    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public String getHomePhoneCountryCode() {
        return homePhoneCountryCode;
    }

    public void setHomePhoneCountryCode(String homePhoneCountryCode) {
        this.homePhoneCountryCode = homePhoneCountryCode;
    }

    public String getHomePhoneExtension() {
        return homePhoneExtension;
    }

    public void setHomePhoneExtension(String homePhoneExtension) {
        this.homePhoneExtension = homePhoneExtension;
    }

    public String getWorkPhoneCountryCode() {
        return workPhoneCountryCode;
    }

    public void setWorkPhoneCountryCode(String workPhoneCountryCode) {
        this.workPhoneCountryCode = workPhoneCountryCode;
    }

    public String getWorkPhoneExtension() {
        return workPhoneExtension;
    }

    public void setWorkPhoneExtension(String workPhoneExtension) {
        this.workPhoneExtension = workPhoneExtension;
    }

    public String getMobilePhoneCountryCode() {
        return mobilePhoneCountryCode;
    }

    public void setMobilePhoneCountryCode(String mobilePhoneCountryCode) {
        this.mobilePhoneCountryCode = mobilePhoneCountryCode;
    }

    public String getMobilePhoneExtension() {
        return mobilePhoneExtension;
    }

    public void setMobilePhoneExtension(String mobilePhoneExtension) {
        this.mobilePhoneExtension = mobilePhoneExtension;
    }

    public String getPreferredPhoneCountryCode() {
        return preferredPhoneCountryCode;
    }

    public void setPreferredPhoneCountryCode(String preferredPhoneCountryCode) {
        this.preferredPhoneCountryCode = preferredPhoneCountryCode;
    }

    public String getPreferredPhoneExtension() {
        return preferredPhoneExtension;
    }

    public void setPreferredPhoneExtension(String preferredPhoneExtension) {
        this.preferredPhoneExtension = preferredPhoneExtension;
    }

    public String getFaxCountryCode() {
        return faxCountryCode;
    }

    public void setFaxCountryCode(String faxCountryCode) {
        this.faxCountryCode = faxCountryCode;
    }

    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    public String getGtalk() {
        return gtalk;
    }

    public void setGtalk(String gtalk) {
        this.gtalk = gtalk;
    }

    public String getAim() {
        return aim;
    }

    public void setAim(String aim) {
        this.aim = aim;
    }

    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getMsn() {
        return msn;
    }

    public void setMsn(String msn) {
        this.msn = msn;
    }

    public String getYahoo() {
        return yahoo;
    }

    public void setYahoo(String yahoo) {
        this.yahoo = yahoo;
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public void setProfileImageUrl(String profileImageUrl) {
        this.profileImageUrl = profileImageUrl;
    }

    public String getProfileImageUrlSmall() {
        return profileImageUrlSmall;
    }

    public void setProfileImageUrlSmall(String profileImageUrlSmall) {
        this.profileImageUrlSmall = profileImageUrlSmall;
    }

    public String getProfileImageUrlLarge() {
        return profileImageUrlLarge;
    }

    public void setProfileImageUrlLarge(String profileImageUrlLarge) {
        this.profileImageUrlLarge = profileImageUrlLarge;
    }

    public List<PersonAddressDto> getPersonAddresses() {
        return personAddresses;
    }

    public void setPersonAddresses(List<PersonAddressDto> personAddresses) {
        this.personAddresses = personAddresses;
    }

    public List<InterestDto> getInterests() {
        return interests;
    }

    public void setInterests(List<InterestDto> interests) {
        this.interests = interests;
    }

    public Float getPreferredSalary() {
        return preferredSalary;
    }

    public void setPreferredSalary(Float preferredSalary) {
        this.preferredSalary = preferredSalary;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(String maritalStatus) {
        this.maritalStatus = maritalStatus;
    }

    public String getDisability() {
        return disability;
    }

    public void setDisability(String disability) {
        this.disability = disability;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public DepartmentDto getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(DepartmentDto currentDepartment) {
        this.currentDepartment = currentDepartment;
    }

    public BiographySkilledRoleDto getCurrentBiographySkilledRole() {
        return currentBiographySkilledRole;
    }

    public void setCurrentBiographySkilledRole(BiographySkilledRoleDto currentBiographySkilledRole) {
        this.currentBiographySkilledRole = currentBiographySkilledRole;
    }

    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    public Float getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Float currentSalary) {
        this.currentSalary = currentSalary;
    }

    public PersonAddressDto getPreferredPersonAddress() {
        return preferredPersonAddress;
    }

    public void setPreferredPersonAddress(PersonAddressDto preferredPersonAddress) {
        this.preferredPersonAddress = preferredPersonAddress;
    }

    public Date getLastRecordUpdate() {
        return lastRecordUpdate;
    }

    public void setLastRecordUpdate(Date lastRecordUpdate) {
        this.lastRecordUpdate = lastRecordUpdate;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    public String getCurrentEmploymentType() {
        return currentEmploymentType;
    }

    public void setCurrentEmploymentType(String currentEmploymentType) {
        this.currentEmploymentType = currentEmploymentType;
    }

    public void addPersonAddress(PersonAddressDto dto) {
        if (personAddresses == null) {
            personAddresses = new ArrayList<PersonAddressDto>();
        }

        boolean isNew = true;
        for (PersonAddressDto aDto : personAddresses) {
            if (StringUtils.equals(aDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            personAddresses.add(dto);
        }
    }

    public void addInterest(InterestDto interestDto) {
        if (interests == null) {
            interests = new ArrayList<InterestDto>();
        }

        boolean isNew = true;
        for (InterestDto dto : interests) {
            if (StringUtils.equals(interestDto.getId(), dto.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            interests.add(interestDto);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("PersonDto {");
        sb.append(super.toString());
        sb.append(", user='").append(user).append('\'');
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", preferredPhone='").append(preferredPhone).append('\'');
        sb.append(", currentLocation=").append(currentLocation);
        sb.append(", timezone='").append(timezone).append('\'');
        sb.append(", preferredEmail='").append(preferredEmail).append('\'');
        sb.append(", availability=").append(availability);
        sb.append(", workEmail='").append(workEmail).append('\'');
        sb.append(", homeEmail='").append(homeEmail).append('\'');
        sb.append(", homePhone='").append(homePhone).append('\'');
        sb.append(", workPhone='").append(workPhone).append('\'');
        sb.append(", mobilePhone='").append(mobilePhone).append('\'');
        sb.append(", fax='").append(fax).append('\'');
        sb.append(", twitter='").append(twitter).append('\'');
        sb.append(", facebook='").append(facebook).append('\'');
        sb.append(", gtalk='").append(gtalk).append('\'');
        sb.append(", aim='").append(aim).append('\'');
        sb.append(", skype='").append(skype).append('\'');
        sb.append(", msn='").append(msn).append('\'');
        sb.append(", yahoo='").append(yahoo).append('\'');
        sb.append(", profileImageUrl='").append(profileImageUrl).append('\'');
        sb.append(", profileImageUrlSmall='").append(profileImageUrlSmall).append('\'');
        sb.append(", profileImageUrlLarge='").append(profileImageUrlLarge).append('\'');
        sb.append(", personAddresses=").append(personAddresses);
        sb.append(", interests=").append(interests);
        sb.append(", preferredSalary=").append(preferredSalary);
        sb.append(", currentSalary=").append(currentSalary);
        sb.append(", status='").append(status).append('\'');
        sb.append(", maritalStatus='").append(maritalStatus).append('\'');
        sb.append(", disability='").append(disability).append('\'');
        sb.append(", race='").append(race).append('\'');
        sb.append(", currentBiographySkilledRole=").append(currentBiographySkilledRole);
        sb.append(", currentDepartment=").append(currentDepartment);
        sb.append(", preferredPersonAddress=").append(preferredPersonAddress);
        sb.append(", lastRecordUpdate=").append(lastRecordUpdate);
        sb.append(", lastLogin=").append(lastLogin);
        sb.append(", currentEmploymentType='").append(currentEmploymentType).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
