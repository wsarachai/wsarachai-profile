package org.itsci.model;

import javax.persistence.Entity;
import java.util.Date;

@Entity
public class Member extends User {
    private Date validFrom;
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
