package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import com.tps.tpi.model.objects.dto.SearchComponentDto;
import com.tps.tpi.model.objects.enums.SearchComponentGroupTypeCd;
import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.Parameter;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.hibernate.validator.NotNull;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 3:49:23 PM
 * Responsibility: A group with persons who are entitled to see a query
 */
@Entity
@TypeDefs(
        {
                @TypeDef(name = "recordCreatorType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordCreatorTypeCd")}
                ),
                @TypeDef(name = "recordStatusType",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.RecordStatusTypeCd")}
                ),
                @TypeDef(name = "type",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SearchComponentGroupTypeCd")}
                )
        }
)
public class SearchComponentGroup extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 43L;
    private SearchComponentGroupTypeCd type;
    private List<SearchComponent> components;

    @NotNull
    @Type(type = "type")
    @Column(nullable = false)
    public SearchComponentGroupTypeCd getType() {
        return type;
    }

    public void setType(SearchComponentGroupTypeCd type) {
        this.type = type;
    }

    @OneToMany(targetEntity = SearchComponent.class, cascade = CascadeType.ALL)
    public List<SearchComponent> getComponents() {
        return components;
    }

    public void setComponents(List<SearchComponent> components) {
        this.components = components;
    }

    public void addComponent(SearchComponent component) {
        if (components == null) {
            components = new ArrayList<SearchComponent>();
        }

        boolean isNew = true;
        for (SearchComponent entity : components) {
            if (StringUtils.equals(component.getId(), entity.getId())) {
                isNew = false;
            }
        }

        if (isNew) {
            components.add(component);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SearchComponentGroup {");
        sb.append(super.toString());
        sb.append(", type=").append(type);
        sb.append('}');
        return sb.toString();
    }
}
