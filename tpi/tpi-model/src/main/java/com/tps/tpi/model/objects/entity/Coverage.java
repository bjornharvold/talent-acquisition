package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Parameter;
import org.hibernate.search.annotations.*;
import org.hibernate.search.annotations.Index;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 1:14:45 PM
 * Responsibility: Coverage tells what kind of coverage the person had for a product
 */
@Entity
@Indexed
@TypeDefs(
        {
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Coverage extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 8L;
    private History history;
    private Company company;
    private Product product;
    private SkilledRole skilledRole;
    private Region region;
    private List<CoverageEndorsement> coverageEndorsements;
    private Boolean current;

    @ManyToOne(optional = false, targetEntity = History.class)
    @JoinColumn
    @ContainedIn
    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    @ManyToOne(optional = true, targetEntity = Company.class)
    @JoinColumn
    @IndexedEmbedded
    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @ManyToOne(optional = true, targetEntity = Product.class)
    @JoinColumn
    @IndexedEmbedded
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    @ManyToOne(optional = true, targetEntity = SkilledRole.class)
    @JoinColumn
    @IndexedEmbedded
    public SkilledRole getSkilledRole() {
        return skilledRole;
    }

    public void setSkilledRole(SkilledRole companyTitle) {
        this.skilledRole = companyTitle;
    }

    @ManyToOne(optional = true, targetEntity = Region.class)
    @JoinColumn
    @IndexedEmbedded
    public Region getRegion() {
        return region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

    @OneToMany
    public List<CoverageEndorsement> getCoverageEndorsements() {
        return coverageEndorsements;
    }

    public void setCoverageEndorsements(List<CoverageEndorsement> coverageEndorsements) {
        this.coverageEndorsements = coverageEndorsements;
    }

    @Field(index = Index.UN_TOKENIZED)
    public Boolean getCurrent() {
        return current;
    }

    public void setCurrent(Boolean current) {
        this.current = current;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("Coverage {");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
