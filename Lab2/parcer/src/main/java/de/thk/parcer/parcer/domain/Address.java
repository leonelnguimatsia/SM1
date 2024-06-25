package de.thk.parcer.parcer.domain;

import javax.persistence.Embeddable;

/**
 * Class for generic international addresses. Due to the
 * variety of address formats, we keep the format as simple
 * as possible with just three lines that might contain
 * whatever the particular address format requires.
 */
// @Embeddable lets addresses to be persisted within other database tables.
@Embeddable
public class Address {
    private String line1;
    private String line2;
    private String line3;

    /**
     * @return the line1
     */
    public String getLine1() {
        return line1;
    }

    /**
     * @param line1 the line1 to set
     */
    public void setLine1(String line1) {
        this.line1 = line1;
    }

    /**
     * @return the line3
     */
    public String getLine3() {
        return line3;
    }

    /**
     * @param line3 the line3 to set
     */
    public void setLine3(String line3) {
        this.line3 = line3;
    }

    /**
     * @return the line2
     */
    public String getLine2() {
        return line2;
    }

    /**
     * @param line2 the line2 to set
     */
    public void setLine2(String line2) {
        this.line2 = line2;
    }
}