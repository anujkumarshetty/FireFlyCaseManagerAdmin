package com.candelalabs.fire.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A DSFooterTemplate.
 */
@Entity
@Table(name = "dsfooter_template")
public class DSFooterTemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "footercontent", nullable = false)
    private String footercontent;

    @NotNull
    @Column(name = "footertemplatename", nullable = false)
    private String footertemplatename;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFootercontent() {
        return footercontent;
    }

    public DSFooterTemplate footercontent(String footercontent) {
        this.footercontent = footercontent;
        return this;
    }

    public void setFootercontent(String footercontent) {
        this.footercontent = footercontent;
    }

    public String getFootertemplatename() {
        return footertemplatename;
    }

    public DSFooterTemplate footertemplatename(String footertemplatename) {
        this.footertemplatename = footertemplatename;
        return this;
    }

    public void setFootertemplatename(String footertemplatename) {
        this.footertemplatename = footertemplatename;
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
        DSFooterTemplate dSFooterTemplate = (DSFooterTemplate) o;
        if (dSFooterTemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dSFooterTemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "DSFooterTemplate{" +
            "id=" + getId() +
            ", footercontent='" + getFootercontent() + "'" +
            ", footertemplatename='" + getFootertemplatename() + "'" +
            "}";
    }
}
