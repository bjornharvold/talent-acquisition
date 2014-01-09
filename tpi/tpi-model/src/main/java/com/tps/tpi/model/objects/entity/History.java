package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.exception.ModelException;
import com.tps.tpi.model.hibernate.EnumUserType;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.*;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 2, 2009
 * Time: 12:56:40 PM
 * Responsibility: Past projects, positions etc that a person has held
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
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class History extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 22L;
    private Person person;
    private List<Position> positions;
    private List<Project> projects;
    private List<Coverage> coverages;
    private List<Affiliation> affiliations;

    @ManyToOne(optional = false, targetEntity = Person.class)
    @JoinColumn
    @ContainedIn
    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    @OneToMany(targetEntity = Position.class, mappedBy = "history")
    @IndexedEmbedded
    public List<Position> getPositions() {
        return positions;
    }

    public void setPositions(List<Position> positions) {
        this.positions = positions;
    }

    @OneToMany(targetEntity = Project.class, mappedBy = "history")
    @IndexedEmbedded
    public List<Project> getProjects() {
        return projects;
    }

    public void setProjects(List<Project> projects) {
        this.projects = projects;
    }

    @OneToMany(targetEntity = Coverage.class, mappedBy = "history")
    @IndexedEmbedded
    public List<Coverage> getCoverages() {
        return coverages;
    }

    public void setCoverages(List<Coverage> coverages) {
        this.coverages = coverages;
    }

    @OneToMany(targetEntity = Affiliation.class, mappedBy = "history")
    @IndexedEmbedded
    public List<Affiliation> getAffiliations() {
        return affiliations;
    }

    public void setAffiliations(List<Affiliation> affiliations) {
        this.affiliations = affiliations;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("History {");
        sb.append(super.toString());
        sb.append('}');
        return sb.toString();
    }

    public void addPosition(Position position) throws ModelException {
        if (positions == null) {
            positions = new ArrayList<Position>();
        }

        if (StringUtils.isBlank(position.getId())) {
            throw new ModelException("Position entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Position bs : positions) {
            if (StringUtils.equals(position.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            positions.add(position);
        }
    }

    public void addProject(Project project) throws ModelException {
        if (projects == null) {
            projects = new ArrayList<Project>();
        }

        if (StringUtils.isBlank(project.getId())) {
            throw new ModelException("Project entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Project bs : projects) {
            if (StringUtils.equals(project.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            projects.add(project);
        }
    }

    public void addCoverage(Coverage coverage) throws ModelException {
        if (coverages == null) {
            coverages = new ArrayList<Coverage>();
        }

        if (StringUtils.isBlank(coverage.getId())) {
            throw new ModelException("Coverage entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Coverage bs : coverages) {
            if (StringUtils.equals(coverage.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            coverages.add(coverage);
        }
    }

    public void addAffiliation(Affiliation affiliation) throws ModelException {
        if (affiliations == null) {
            affiliations = new ArrayList<Affiliation>();
        }

        if (StringUtils.isBlank(affiliation.getId())) {
            throw new ModelException("Affiliation entity needs to already be persisted");
        }

        boolean isNew = true;

        for (Affiliation bs : affiliations) {
            if (StringUtils.equals(affiliation.getId(), bs.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            affiliations.add(affiliation);
        }
    }
}
