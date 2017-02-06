/*
$RCSfile: IRecordMapperValueObject.java,v $

Change Summary
==============
*/

package org.titantech.titantools.dao.valuebean;

import java.util.HashSet;


/**
 */
public interface IRecordMapperValueObject extends IRecordValueObject {

    /**
     * Provides access to record Value Object Field, which is registered for the record Id.
     *
     * @return RecordValueObjectField
     */
    public RecordValueObjectField getRecordIDField();

    /**
     * Retrieves record Value Object Field by the provided field key.
     *
     * @param fieldKey Field Key.
     * @return RecordValueObjectField    Record Value Object Field
     */
    public RecordValueObjectField getObjectFieldByFieldKey(String fieldKey);

    /**
     * Retrieves set of record value object field objects that are part of primary complex key.
     *
     * @return Set    Set of Record Value Object Field objects that are part of primary complex key.
     */
    public HashSet getComplexPKSet();

    /**
     * Checks if the record value object has a complex PK.
     *
     * @return boolean    If the record mapper value object has complex primary key, returns true,
     * returns false otherwise.
     */
    public boolean hasComplexPK();

    /**
     * Retrieves group id for this mapper value object.
     *
     * @return Integer    group index.
     */
    public Integer getEntityGroupId();

    /**
     * Sets group id for this mapper value object.
     *
     * @param Integer Group id
     */
    public void setEntityGroupId(Integer groupId);

    /**
     * Retrieves entity plugin type Id.
     *
     * @return Integer    plugin type Id.
     */
    public Integer getEntityPluginId();

    /**
     * Sets entity plugin type Id.
     *
     * @param Integer Plugin Type Id.
     */
    public void setEntityPluginId(Integer pluginTypeId);

}