package com.rlocatelli.orgchart.bean;

import java.util.Objects;

/**
 *
 * @author reinaldo.locatelli
 */
public class OrgData {

    private Long id;
    private String name;
    private String description;
    private Long parentId;

    public OrgData() {
    }

    public OrgData(Long id, String name, String description, Long parentId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.parentId = parentId;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final OrgData other = (OrgData) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return  id + " - " + description;
    }
    
    

}
