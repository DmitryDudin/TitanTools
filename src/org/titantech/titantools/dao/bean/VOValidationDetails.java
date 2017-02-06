package org.titantech.titantools.dao.bean;

public class VOValidationDetails extends VODetails {

    public int minOccurs = 1; // default=1
    public int maxOccurs = 1; // default=1  -1 = unbounded

    public String xmlType = null;
}