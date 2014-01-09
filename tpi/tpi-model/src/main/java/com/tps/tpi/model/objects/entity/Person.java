package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.hibernate.PadNumberBridge;
import com.tps.tpi.model.objects.enums.*;
import com.tps.tpi.model.exception.ModelException;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Sep 28, 2009
 * Time: 9:07:02 AM
 * Responsibility: This is the key entity that connects everything else. This is an employ of a company or unemployed
 */
@Entity
@TypeDefs(
        {
                @TypeDef(name = "status",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.PersonStatusCd")}
                ),
                @TypeDef(name = "sex",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SexCd")}
                ),
                @TypeDef(name = "salutation",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SalutationCd")}
                ),
                @TypeDef(name = "employmentType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.EmploymentTypeCd")}
                ),
                @TypeDef(name = "availabilityType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.AvailabilityTypeCd")}
                ),
                @TypeDef(name = "maritalStatus",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.MaritalStatusCd")}
                ),
                @TypeDef(name = "recordCreatorType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordCreatorTypeCd")}
                ),
                @TypeDef(name = "recordStatusType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordStatusTypeCd")}
                )
        }
)
@Indexed
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Person extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 17L;
    private User user;
    private List<Biography> biographies;
    private List<Education> educations;
    private List<History> histories;
    private String preferredFirstName;
    private String firstName;
    private String lastName;
    private String middleName;
    private Department currentDepartment;
    private EmploymentTypeCd currentEmploymentType;
    private City currentLocation;
    private String timezone;
    private String preferredEmail;
    private Date availability;
    private AvailabilityTypeCd availabilityType;
    private PersonAddress preferredPersonAddress;

    // this is the latest title the person is holding / held
    private BiographySkilledRole currentBiographySkilledRole;
    private BiographyCompanyTitle currentBiographyCompanyTitle;
    private String workEmail;
    private String homeEmail;
    private String homePhoneCountryCode;
    private String homePhoneExtension;
    private String workPhoneCountryCode;
    private String workPhoneExtension;
    private String mobilePhoneCountryCode;
    private String mobilePhoneExtension;
    private String preferredPhoneCountryCode;
    private String preferredPhoneExtension;
    private String faxCountryCode;
    private String preferredPhone;
    private String homePhone;
    private String workPhone;
    private String mobilePhone;
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
    private List<PersonAddress> personAddresses;
    private List<Interest> interests;
    private Float preferredSalary;
    private Float currentSalary;
    private PersonStatusCd status;
    private MaritalStatusCd maritalStatus;
    private String disability;
    private String race;
    private Integer availableEndorsements;
    private SexCd sex;
    private SalutationCd salutation;
    private Date dob;
    private Float relevancy;
    private Date lastRecordUpdate = new Date();
    private Date lastLogin;

    @ManyToOne(optional = false, targetEntity = User.class, fetch = FetchType.LAZY)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @OneToMany(targetEntity = Biography.class, mappedBy = "person")
    @IndexedEmbedded
    public List<Biography> getBiographies() {
        return biographies;
    }

    public void setBiographies(List<Biography> biographies) {
        this.biographies = biographies;
    }

    @OneToMany(targetEntity = Education.class, mappedBy = "person")
    @IndexedEmbedded
    public List<Education> getEducations() {
        return educations;
    }

    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }

    @OneToMany(targetEntity = History.class, mappedBy = "person")
    @IndexedEmbedded
    public List<History> getHistories() {
        return histories;
    }

    public void setHistories(List<History> histories) {
        this.histories = histories;
    }

    @Field
    public String getPreferredFirstName() {
        return preferredFirstName;
    }

    public void setPreferredFirstName(String preferredFirstName) {
        this.preferredFirstName = preferredFirstName;
    }

    @Field
    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    @Field
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Field
    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    @ManyToOne(optional = true, targetEntity = Department.class, fetch = FetchType.LAZY)
    @JoinColumn
    public Department getCurrentDepartment() {
        return currentDepartment;
    }

    public void setCurrentDepartment(Department currentDepartment) {
        this.currentDepartment = currentDepartment;
    }

    @ManyToOne(optional = true, targetEntity = City.class, fetch = FetchType.LAZY)
    @JoinColumn
    @IndexedEmbedded
    public City getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(City currentLocation) {
        this.currentLocation = currentLocation;
    }

    @Field
    public String getTimezone() {
        return timezone;
    }

    public void setTimezone(String timezone) {
        this.timezone = timezone;
    }

    @Field
    public String getPreferredEmail() {
        return preferredEmail;
    }

    public void setPreferredEmail(String preferredEmail) {
        this.preferredEmail = preferredEmail;
    }

    @ManyToOne(optional = true, targetEntity = BiographySkilledRole.class)
    @JoinColumn
    public BiographySkilledRole getCurrentBiographySkilledRole() {
        return currentBiographySkilledRole;
    }

    public void setCurrentBiographySkilledRole(BiographySkilledRole currentBiographySkilledRole) {
        this.currentBiographySkilledRole = currentBiographySkilledRole;
    }

    @ManyToOne(optional = true, targetEntity = BiographyCompanyTitle.class)
    @JoinColumn
    public BiographyCompanyTitle getCurrentBiographyCompanyTitle() {
        return currentBiographyCompanyTitle;
    }

    public void setCurrentBiographyCompanyTitle(BiographyCompanyTitle currentBiographyCompanyTitle) {
        this.currentBiographyCompanyTitle = currentBiographyCompanyTitle;
    }

    @Temporal(value = TemporalType.DATE)
    @Field
    @DateBridge(resolution = Resolution.DAY)
    public Date getAvailability() {
        return availability;
    }

    public void setAvailability(Date availability) {
        this.availability = availability;
    }

    @Type(type = "availabilityType")
    @Field(index = Index.UN_TOKENIZED)
    public AvailabilityTypeCd getAvailabilityType() {
        return availabilityType;
    }

    public void setAvailabilityType(AvailabilityTypeCd availabilityType) {
        this.availabilityType = availabilityType;
    }

    @Field
    public String getWorkEmail() {
        return workEmail;
    }

    public void setWorkEmail(String workEmail) {
        this.workEmail = workEmail;
    }

    public String getHomeEmail() {
        return homeEmail;
    }

    @Field
    public void setHomeEmail(String homeEmail) {
        this.homeEmail = homeEmail;
    }

    @Field
    public String getPreferredPhone() {
        return preferredPhone;
    }

    public void setPreferredPhone(String preferredPhone) {
        this.preferredPhone = preferredPhone;
    }

    @Field
    public String getHomePhone() {
        return homePhone;
    }

    public void setHomePhone(String homePhone) {
        this.homePhone = homePhone;
    }

    @Field
    public String getWorkPhone() {
        return workPhone;
    }

    public void setWorkPhone(String workPhone) {
        this.workPhone = workPhone;
    }

    @Field
    public String getMobilePhone() {
        return mobilePhone;
    }

    public void setMobilePhone(String mobilePhone) {
        this.mobilePhone = mobilePhone;
    }

    @Field
    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    @Field
    public String getHomePhoneCountryCode() {
        return homePhoneCountryCode;
    }

    public void setHomePhoneCountryCode(String homePhoneCountryCode) {
        this.homePhoneCountryCode = homePhoneCountryCode;
    }

    @Field
    public String getHomePhoneExtension() {
        return homePhoneExtension;
    }

    public void setHomePhoneExtension(String homePhoneExtension) {
        this.homePhoneExtension = homePhoneExtension;
    }

    @Field
    public String getWorkPhoneCountryCode() {
        return workPhoneCountryCode;
    }

    public void setWorkPhoneCountryCode(String workPhoneCountryCode) {
        this.workPhoneCountryCode = workPhoneCountryCode;
    }

    @Field
    public String getWorkPhoneExtension() {
        return workPhoneExtension;
    }

    public void setWorkPhoneExtension(String workPhoneExtension) {
        this.workPhoneExtension = workPhoneExtension;
    }

    @Field
    public String getMobilePhoneCountryCode() {
        return mobilePhoneCountryCode;
    }

    public void setMobilePhoneCountryCode(String mobilePhoneCountryCode) {
        this.mobilePhoneCountryCode = mobilePhoneCountryCode;
    }

    @Field
    public String getMobilePhoneExtension() {
        return mobilePhoneExtension;
    }

    public void setMobilePhoneExtension(String mobilePhoneExtension) {
        this.mobilePhoneExtension = mobilePhoneExtension;
    }

    @Field
    public String getPreferredPhoneCountryCode() {
        return preferredPhoneCountryCode;
    }

    public void setPreferredPhoneCountryCode(String preferredPhoneCountryCode) {
        this.preferredPhoneCountryCode = preferredPhoneCountryCode;
    }

    @Field
    public String getPreferredPhoneExtension() {
        return preferredPhoneExtension;
    }

    public void setPreferredPhoneExtension(String preferredPhoneExtension) {
        this.preferredPhoneExtension = preferredPhoneExtension;
    }

    @Field
    public String getFaxCountryCode() {
        return faxCountryCode;
    }

    public void setFaxCountryCode(String faxCountryCode) {
        this.faxCountryCode = faxCountryCode;
    }

    @Field(index = Index.UN_TOKENIZED)
    public String getTwitter() {
        return twitter;
    }

    public void setTwitter(String twitter) {
        this.twitter = twitter;
    }

    @Field(index = Index.UN_TOKENIZED)
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @Field(index = Index.UN_TOKENIZED)
    public String getGtalk() {
        return gtalk;
    }

    public void setGtalk(String gtalk) {
        this.gtalk = gtalk;
    }

    public String getAim() {
        return aim;
    }

    @Field(index = Index.UN_TOKENIZED)
    public void setAim(String aim) {
        this.aim = aim;
    }

    @Field(index = Index.UN_TOKENIZED)
    public String getSkype() {
        return skype;
    }

    public void setSkype(String skype) {
        this.skype = skype;
    }

    public String getMsn() {
        return msn;
    }

    @Field(index = Index.UN_TOKENIZED)
    public void setMsn(String msn) {
        this.msn = msn;
    }

    @Field(index = Index.UN_TOKENIZED)
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

    @Field
    @FieldBridge(impl = PadNumberBridge.class)
    public Float getPreferredSalary() {
        return preferredSalary;
    }

    public void setPreferredSalary(Float preferredSalary) {
        this.preferredSalary = preferredSalary;
    }

    @Type(type = "status")
    public PersonStatusCd getStatus() {
        return status;
    }

    public void setStatus(PersonStatusCd status) {
        this.status = status;
    }

    @OneToMany(mappedBy = "person", targetEntity = PersonAddress.class)
    @IndexedEmbedded
    public List<PersonAddress> getPersonAddresses() {
        return personAddresses;
    }

    public void setPersonAddresses(List<PersonAddress> addresses) {
        this.personAddresses = addresses;
    }

    @OneToMany(mappedBy = "person", targetEntity = Interest.class)
    @IndexedEmbedded
    public List<Interest> getInterests() {
        return interests;
    }

    public void setInterests(List<Interest> interests) {
        this.interests = interests;
    }

    @Type(type = "maritalStatus")
    public MaritalStatusCd getMaritalStatus() {
        return maritalStatus;
    }

    public void setMaritalStatus(MaritalStatusCd maritalStatus) {
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

    public Integer getAvailableEndorsements() {
        return availableEndorsements;
    }

    public void setAvailableEndorsements(Integer availableEndorsements) {
        this.availableEndorsements = availableEndorsements;
    }

    @Type(type = "employmentType")
    @Field(index = Index.UN_TOKENIZED)
    public EmploymentTypeCd getCurrentEmploymentType() {
        return currentEmploymentType;
    }

    public void setCurrentEmploymentType(EmploymentTypeCd currentEmploymentType) {
        this.currentEmploymentType = currentEmploymentType;
    }

    @Field
    @FieldBridge(impl = PadNumberBridge.class)
    public Float getCurrentSalary() {
        return currentSalary;
    }

    public void setCurrentSalary(Float currentSalary) {
        this.currentSalary = currentSalary;
    }

    @Type(type = "sex")
    public SexCd getSex() {
        return sex;
    }

    public void setSex(SexCd sex) {
        this.sex = sex;
    }

    @Temporal(value = TemporalType.DATE)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Type(type = "salutation")
    public SalutationCd getSalutation() {
        return salutation;
    }

    public void setSalutation(SalutationCd salutation) {
        this.salutation = salutation;
    }

    @ManyToOne(targetEntity = PersonAddress.class, optional = true, fetch = FetchType.LAZY)
    @JoinColumn
    @IndexedEmbedded
    public PersonAddress getPreferredPersonAddress() {
        return preferredPersonAddress;
    }

    public void setPreferredPersonAddress(PersonAddress preferredPersonAddress) {
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

    @Transient
    public Float getRelevancy() {
        return relevancy;
    }

    public void setRelevancy(Float relevancy) {
        this.relevancy = relevancy;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Person {");
        sb.append(super.toString());
        sb.append(", firstName='").append(firstName).append('\'');
        sb.append(", lastName='").append(lastName).append('\'');
        sb.append(", middleName='").append(middleName).append('\'');
        sb.append(", preferredPhone='").append(preferredPhone).append('\'');
        sb.append(", currentEmploymentType=").append(currentEmploymentType);
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
        sb.append(", preferredSalary=").append(preferredSalary);
        sb.append(", currentSalary=").append(currentSalary);
        sb.append(", status=").append(status);
        sb.append(", maritalStatus=").append(maritalStatus);
        sb.append(", dob=").append(dob);
        sb.append(", disability='").append(disability).append('\'');
        sb.append(", race='").append(race).append('\'');
        sb.append(", sex='").append(sex).append('\'');
        sb.append(", availableEndorsements=").append(availableEndorsements);
        sb.append('}');
        return sb.toString();
    }

    /**
     * Utility method
     * @param bio
     */
    public void addBiography(Biography bio) throws ModelException {
        if (biographies == null) {
            biographies = new ArrayList<Biography>();
        }

        if (StringUtils.isBlank(bio.getId())) {
            throw new ModelException("biography entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Biography biography : biographies) {
            if (StringUtils.equals(bio.getId(), biography.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            biographies.add(bio);
        }
    }

    public void addEducation(Education education) throws ModelException {
        if (educations == null) {
            educations = new ArrayList<Education>();
        }

        if (StringUtils.isBlank(education.getId())) {
            throw new ModelException("Education entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Education biography : educations) {
            if (StringUtils.equals(education.getId(), biography.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            educations.add(education);
        }
    }

    public void addHistory(History history) throws ModelException {
        if (histories == null) {
            histories = new ArrayList<History>();
        }

        if (StringUtils.isBlank(history.getId())) {
            throw new ModelException("History entity needs to already be persisted");
        }

        boolean isNew = true;

        for (History hist : histories) {
            if (StringUtils.equals(hist.getId(), history.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            histories.add(history);
        }
    }
}
