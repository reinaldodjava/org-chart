## OrgChart

It is an organization chart imager designed to meet the need for developers who need to generate orgChart for direct backend reporting.

## Example

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
*Output file:* **breaking-bad-organization.png**
![enter image description here](https://user-images.githubusercontent.com/33791554/71783331-19225f80-2fc4-11ea-8362-73c063085e4f.png)
