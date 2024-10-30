package org.itsci.controller.bean;

public class SemesterBean {
    private String year;
    private String term;

    public SemesterBean(String year, String term) {
        this.year = year;
        this.term = term;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getTerm() {
        return term;
    }

    public void setTerm(String term) {
        this.term = term;
    }
}
