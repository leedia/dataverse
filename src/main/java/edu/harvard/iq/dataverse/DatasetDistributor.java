/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package edu.harvard.iq.dataverse;

import javax.persistence.Version;

/**
 *
 * @author skraffmiller
 */
public class DatasetDistributor {
        
    /** Creates a new instance of DatasetDistributor */
    public DatasetDistributor() {
    }


    private int displayOrder;
    public int getDisplayOrder() {
        return this.displayOrder;
    }
    public void setDisplayOrder(int displayOrder) {
        this.displayOrder = displayOrder;
    }

    private DatasetFieldValue name;
    public DatasetFieldValue getName() {
        return this.name;
    }
    public void setName(DatasetFieldValue name) {
        this.name = name;
    }

    
    @Version
    private Long version;
    public Long getVersion() {
        return this.version;
    }
    public void setVersion(Long version) {
        this.version = version;
    }    

    private DatasetFieldValue url;
    public DatasetFieldValue getUrl() {
        return this.url;
    }
    public void setUrl(DatasetFieldValue url) {
        this.url = url;
    }
    
    private DatasetFieldValue logo;
    public DatasetFieldValue getLogo() {
        return this.logo;
    }
    public void setLogo(DatasetFieldValue logo) {
        this.logo = logo;
    }
    
    private DatasetFieldValue affiliation;
    public DatasetFieldValue getAffiliation() {
        return this.affiliation;
    }
    public void setAffiliation(DatasetFieldValue affiliation) {
        this.affiliation = affiliation;
    }

    private DatasetFieldValue abbreviation;
    public DatasetFieldValue getAbbreviation() {
        return this.abbreviation;
    }
    public void setAbbreviation(DatasetFieldValue abbreviation) {
        this.abbreviation = abbreviation;
    }
    
      public boolean isEmpty() {
        return ((abbreviation==null || abbreviation.getValue().trim().equals(""))
            && (affiliation==null || affiliation.getValue().trim().equals(""))
            && (logo==null || logo.getValue().trim().equals(""))
            && (name==null || name.getValue().trim().equals(""))
            && (url==null || url.getValue().trim().equals("")));
    }
      
    
}
