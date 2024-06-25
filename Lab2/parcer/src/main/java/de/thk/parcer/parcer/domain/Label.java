package de.thk.parcer.parcer.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/**
 * Class that encapsulates a base64 encoded PDF label.
 */
@Entity
public class Label {
    @Id
    @GeneratedValue
    private long id;
    @Column(length = 1048576)
    private String base64EncodedLabel;

    public long getId() {
        return id;
    }

    /**
     * @return the base64EncodedLabel
     */
    public String getBase64EncodedLabel() {
        return base64EncodedLabel;
    }

    /**
     * @param base64EncodedLabel the base64EncodedLabel to set
     */
    public void setBase64EncodedLabel(String base64EncodedLabel) {
        this.base64EncodedLabel = base64EncodedLabel;
    }
}