package com.rlocatelli.orgchart.bean;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author reinaldo.locatelli
 */
public class TreeOrg {

    private OrgData orgData;
    private List<TreeOrg> childrens;

    public TreeOrg() {
        this.childrens = new ArrayList<>();
    }

    public TreeOrg(OrgData orgData) {
        this();
        this.orgData = orgData;
    }

    public void add(OrgData orgData) {
        //Check if the parent exists
        TreeOrg father = getFather(orgData.getParentId(), this);
        TreeOrg treeOrg = new TreeOrg(orgData);

        //If it does not exist it will start
        if (Objects.isNull(father)) {
            childrens.add(treeOrg);
        } else {
            father.getChildrens().add(treeOrg);
        }

        //Check if any child is not a child of this element if moving from tree
        for (int i = childrens.size()-1; i >= 0; i--) {
            if (Objects.equals(childrens.get(i).getOrgData().getParentId(), orgData.getId())) {
                treeOrg.getChildrens().add(childrens.get(i));
                childrens.remove(childrens.get(i));
            }
        }

    }

    public TreeOrg getFather(Long idFather, TreeOrg treeOrg) {
        for (TreeOrg children : treeOrg.childrens) {
            if (Objects.equals(children.getOrgData().getId(), idFather)) {
                return children;
            }
            TreeOrg t = getFather(idFather, children);
            if (t != null) {
                return t;
            }
        }
        return null;
    }

    public OrgData getOrgData() {
        return orgData;
    }

    public void setOrgData(OrgData orgData) {
        this.orgData = orgData;
    }

    public List<TreeOrg> getChildrens() {
        return childrens;
    }

    public void setChildrens(List<TreeOrg> childrens) {
        this.childrens = childrens;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 31 * hash + Objects.hashCode(this.orgData);
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
        final TreeOrg other = (TreeOrg) obj;
        if (!Objects.equals(this.orgData, other.orgData)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "TREE -> " + orgData.getId() + " - " + orgData.getDescription();
    }

}
