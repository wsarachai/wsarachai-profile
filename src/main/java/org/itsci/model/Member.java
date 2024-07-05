package org.itsci.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
public class Member extends User {
    @Temporal(TemporalType.DATE)
    @Column(name="valid_from")
    private Date validFrom;
    @Temporal(TemporalType.DATE)
    @Column(name="expired_date")
    private Date expiredDate;

    public Date getValidFrom() {
        return validFrom;
    }

    public void setValidFrom(Date validFrom) {
        this.validFrom = validFrom;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }
}
