/*
$RCSfile: RecordValueObject.java,v $

Change Summary
==============
*/

package org.titantech.titantools.dao.valuebean;

import java.util.ArrayList;
import java.util.Iterator;


/**
 */
public abstract class RecordValueObject implements IRecordValueObject {

    //ID of the plugin.
    private Integer pluginId = null;
    protected ArrayList fields = new ArrayList();
    private Iterator iter = null;

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID fields,
     * which is latter used by the BaseDAO in order to map database field values to the vo properties.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the record value object setter method for the vo property.
     * @param setterType  Type of the record value object setter method for the vo property.
     * @param getterName  Name of the record value object getter method for the vo property.
     */
    protected void addVOFieldPartOfComplexPK(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        //registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, true, null, null, true, null);
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, true, null, null, false, null);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID and not part of complex PK fields,
     * which is latter used by the BaseDAO in order to map database field values to the vo properties.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the case value object setter method for the vo property.
     * @param setterType  Type of the case value object setter method for the vo property.
     * @param getterName  Name of the case value object getter method for the vo property.
     */
    protected void addVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        //registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false, null, null, true, null);
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false, null, null, false, null);
    }

    /**
     * This is an implementation method used to register value object fields (either RECORD ID or not,)
     * which is latter used by the BaseDAO in order to map database field values to the vo properties.
     *
     * @param isIdentifiable     true if this field identified by an identifier query, false otherwise.
     * @param dbFieldName        Name of the database field that is being mapped.
     * @param dbFieldType        Type of the database field that is being mapped.
     * @param setterName         Name of the case value object setter method for the vo property.
     * @param setterType         Type of the case value object setter method for the vo property.
     * @param getterName         Name of the case value object getter method for the vo property.
     * @param isRecordId         Flag indicating whether this field is ORDER ID (true) or not (false).
     * @param modifierGetterName Name of the modifier getter method, which is used
     *                           to retrieve new value for this field.
     * @param modifiable         Flag indicating whether this field can be modified at all by auto or manual modifiers.
     * @param selectorGetterName Name of the selector getter method.
     * @return RecordValueObjectField    Record Value Object Field.
     */
    protected RecordValueObjectField registerVOField(boolean isIdentifiable, String dbFieldName,
                                                     int dbFieldType, String setterName, Class setterType, String getterName,
                                                     boolean isRecordId, boolean isPartOfComplexPK, String modifierGetterName, String modifierValidatorMethodName,
                                                     boolean modifiable, String selectorGetterName) {
        RecordValueObjectField field = new RecordValueObjectField(isRecordId);
        field.isPartOfComplexPK = isPartOfComplexPK;
        field.isIdentifiable = isIdentifiable;
        field.databaseFieldName = dbFieldName.toLowerCase();
        field.databaseFieldType = dbFieldType;
        field.setterMethodName = setterName;
        field.getterMethodName = getterName;
        field.setterType = setterType;
        field.modifierGetterName = modifierGetterName;
        field.modifierValidatorMethodName = modifierValidatorMethodName;
        field.modifiable = modifiable;
        field.selectorGetterName = selectorGetterName;
        fields.add(field);
        return field;
    }

    /**
     * Returns true if there is at least one more field to map from the result set to this vo.
     */
    public boolean hasNextField() {
        if (iter == null) {
            iter = fields.iterator();
        }
        return iter.hasNext();
    }

    /**
     * Returns RecordValueObjectField object, which contains vo to db field mapping parameters.
     */
    public RecordValueObjectField nextField() {
        if (iter == null) {
            iter = fields.iterator();
        }
        return (RecordValueObjectField) iter.next();
    }

    /**
     * Resets the vo field iterator, so it can be reiterated.
     */
    public void resetFieldIterator() {
        iter = fields.iterator();
    }

    /**
     * Sets plugin Id.
     */
    public void setPluginId(Integer pluginId) {
        this.pluginId = pluginId;
    }

    /**
     * Gets case type Id.
     */
    public Integer getPluginId() {
        return this.pluginId;
    }

    /**
     * Gets total number of registered (added) fields on this value object.
     */
    public int getNumberOfRegisteredFields() {
        return fields.size();
    }
}