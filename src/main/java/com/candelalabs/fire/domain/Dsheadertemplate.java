package com.candelalabs.fire.domain;


import javax.persistence.*;
import javax.validation.constraints.*;

import java.io.Serializable;
import java.util.Objects;

/**
 * A Dsheadertemplate.
 */
@Entity
@Table(name = "dsheadertemplate")
public class Dsheadertemplate implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequenceGenerator")
    @SequenceGenerator(name = "sequenceGenerator")
    private Long id;

    
    @Lob
    @Column(name = "headercontent", nullable = false)
    private String headercontent;

    @NotNull
    @Column(name = "headertemplatename", nullable = false)
    private String headertemplatename;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHeadercontent() {
        return headercontent;
    }

    public Dsheadertemplate headercontent(String headercontent) {
        this.headercontent = headercontent;
        return this;
    }

    public void setHeadercontent(String headercontent) {
        this.headercontent = headercontent;
    }

    public String getHeadertemplatename() {
        return headertemplatename;
    }

    public Dsheadertemplate headertemplatename(String headertemplatename) {
        this.headertemplatename = headertemplatename;
        return this;
    }

    public void setHeadertemplatename(String headertemplatename) {
        this.headertemplatename = headertemplatename;
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
        Dsheadertemplate dsheadertemplate = (Dsheadertemplate) o;
        if (dsheadertemplate.getId() == null || getId() == null) {
            return false;
        }
        return Objects.equals(getId(), dsheadertemplate.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId());
    }

    @Override
    public String toString() {
        return "Dsheadertemplate{" +
            "id=" + getId() +
            ", headercontent='" + getHeadercontent() + "'" +
            ", headertemplatename='" + getHeadertemplatename() + "'" +
            "}";
    }
}
