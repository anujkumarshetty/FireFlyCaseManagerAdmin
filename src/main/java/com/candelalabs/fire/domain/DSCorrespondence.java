package com.candelalabs.fire.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DSCorrespondence.
 */
@Entity
@Table(name = "dscorrespondence")
public class DSCorrespondence implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    @Column(name = "templateid")
    private Integer templateid;

    @NotNull
    @Column(name = "lettertype", nullable = false)
    private String lettertype;

    @NotNull
    @Column(name = "category", nullable = false)
    private String category;

    @NotNull
    @Column(name = "subcategory", nullable = false)
    private String subcategory;

    
    @Lob
    @Column(name = "lettertemplate", nullable = false)
    private byte[] lettertemplate;

    @Column(name = "lettertemplate_content_type", nullable = false)
    private String lettertemplateContentType;

    @Column(name = "isactive")
    private Integer isactive;

    @Column(name = "parentid")
    private Integer parentid;

    @NotNull
    @Column(name = "templatetype", nullable = false)
    private String templatetype;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getTemplateid() {
        return templateid;
    }

    public DSCorrespondence templateid(Integer templateid) {
        this.templateid = templateid;
        return this;
    }

    public void setTemplateid(Integer templateid) {
        this.templateid = templateid;
    }

    public String getLettertype() {
        return lettertype;
    }

    public DSCorrespondence lettertype(String lettertype) {
        this.lettertype = lettertype;
        return this;
    }

    public void setLettertype(String lettertype) {
        this.lettertype = lettertype;
    }

    public String getCategory() {
        return category;
    }

    public DSCorrespondence category(String category) {
        this.category = category;
        return this;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSubcategory() {
        return subcategory;
    }

    public DSCorrespondence subcategory(String subcategory) {
        this.subcategory = subcategory;
        return this;
    }

    public void setSubcategory(String subcategory) {
        this.subcategory = subcategory;
    }

    public byte[] getLettertemplate() {
        return lettertemplate;
    }

    public DSCorrespondence lettertemplate(byte[] lettertemplate) {
        this.lettertemplate = lettertemplate;
        return this;
    }

    public void setLettertemplate(byte[] lettertemplate) {
        this.lettertemplate = lettertemplate;
    }

    public String getLettertemplateContentType() {
        return lettertemplateContentType;
    }

    public DSCorrespondence lettertemplateContentType(String lettertemplateContentType) {
        this.lettertemplateContentType = lettertemplateContentType;
        return this;
    }

    public void setLettertemplateContentType(String lettertemplateContentType) {
        this.lettertemplateContentType = lettertemplateContentType;
    }

    public Integer getIsactive() {
        return isactive;
    }

    public DSCorrespondence isactive(Integer isactive) {
        this.isactive = isactive;
        return this;
    }

    public void setIsactive(Integer isactive) {
        this.isactive = isactive;
    }

    public Integer getParentid() {
        return parentid;
    }

    public DSCorrespondence parentid(Integer parentid) {
        this.parentid = parentid;
        return this;
    }

    public void setParentid(Integer parentid) {
        this.parentid = parentid;
    }

    public String getTemplatetype() {
        return templatetype;
    }

    public DSCorrespondence templatetype(String templatetype) {
        this.templatetype = templatetype;
        return this;
    }

    public void setTemplatetype(String templatetype) {
        this.templatetype = templatetype;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DSCorrespondence dSCorrespondence = (DSCorrespondence) o;
        if (dSCorrespondence.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dSCorrespondence.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DSCorrespondence{" +
            "id=" + getId() +
            ", templateid=" + getTemplateid() +
            ", lettertype='" + getLettertype() + "'" +
            ", category='" + getCategory() + "'" +
            ", subcategory='" + getSubcategory() + "'" +
            ", lettertemplate='" + getLettertemplate() + "'" +
            ", lettertemplateContentType='" + getLettertemplateContentType() + "'" +
            ", isactive=" + getIsactive() +
            ", parentid=" + getParentid() +
            ", templatetype='" + getTemplatetype() + "'" +
            "}";
    }
}
