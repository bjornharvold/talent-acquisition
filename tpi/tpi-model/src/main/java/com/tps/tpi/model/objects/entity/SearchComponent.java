package com.tps.tpi.model.objects.entity;

import com.tps.tpi.model.hibernate.EnumUserType;
import org.hibernate.annotations.*;
import org.hibernate.annotations.MapKey;

import javax.persistence.*;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Map;

/**
 * User: Bjorn Harvold
 * Date: Oct 8, 2009
 * Time: 3:51:10 PM
 * Responsibility: Entitlements for a search query. An admin can search and restrict searches to groups and users
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
                @TypeDef(name = "name",
                        typeClass = EnumUserType.class,
                        parameters = {@Parameter(name = "enumClassName", value = "com.tps.tpi.model.objects.enums.SearchComponentFieldNameCd")}
                )
        }
)
public class SearchComponent extends AbstractEntity implements Serializable {
    private final static long serialVersionUID = 42L;
    private Map<String, String> searchMap;

    @CollectionOfElements
    @JoinTable(name = "SearchValues",
            joinColumns = @JoinColumn(name = "searchValues_id"))
    @MapKey(columns={@Column(name="search_key")})
    @Column(name = "search_value")
    public Map<String, String> getSearchMap() {
        return searchMap;
    }

    public void setSearchMap(Map<String, String> searchMap) {
        this.searchMap = searchMap;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SearchComponent");
        sb.append("{");
        sb.append(super.toString());
        sb.append("}");
        return sb.toString();
    }
}
