package com.rlocatelli.orgchart;

import com.rlocatelli.orgchart.bean.OrgData;
import com.rlocatelli.orgchart.bean.TreeOrg;
import com.rlocatelli.orgchart.service.OrgChart;
import java.io.File;

/**
 *
 * @author reinaldo.locatelli
 */
public class MainOrgChart {

    private static OrgChart orgChart = new OrgChart();

    public static void main(String[] args) {

        TreeOrg treeOrg = new TreeOrg();

        treeOrg.setOrgData(new OrgData(1L, "Walter White", "CEO", null));
        treeOrg.add(new OrgData(2L, "Saul Goodman", "CFO", 1L));
        treeOrg.add(new OrgData(3L, "Mike Ehrmantraut", "COO", 1L));
        treeOrg.add(new OrgData(4L, "Jesse Pinkman", "CTO", 1L));

        treeOrg.add(new OrgData(5L, "Huell", "Strategic Operations", 2L));

        treeOrg.add(new OrgData(6L, "Skinny Pete", "Sale & QA", 4L));
        treeOrg.add(new OrgData(7L, "Badger", "Sale & QA", 4L));

        File file = new File("/home/reinaldo/Desktop/breaking-bad-organization.png");
        
        orgChart.getOrgParent(treeOrg, file);

    }

}
