/*
$RCSfile: IRecordValueObject.java,v $

Change Summary
==============
*/

package org.titantech.titantools.dao.valuebean;

import java.io.Serializable;


/**
 * This interface must be implemented by all DAO value objects.
 */
public interface IRecordValueObject extends Serializable {
    public void setPluginId(Integer caseTypeId);

    public Integer getPluginId();

    public boolean hasNextField();

    public RecordValueObjectField nextField();

    public void resetFieldIterator();

    public int getNumberOfRegisteredFields();
}