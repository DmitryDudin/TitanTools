/*
$RCSfile: RecordMapperValueObject.java,v $

Change Summary
==============

*/

package org.titantech.titantools.dao.valuebean;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;

import org.titantech.titantools.GESystemException;


/**
 * This class provides common record value object functionality, which is
 * enforced by the IRecordValueObject interface.  The common functionality
 * includes a convenience addVOField method, which registers VO Fields.
 * Common field iterator functionality is also provided.
 *
 * @author roman.mironenko
 * @version 1.0
 * @created 4-Oct-2006
 */
public abstract class RecordMapperValueObject extends RecordValueObject implements IRecordMapperValueObject {
    //Indicates whether one of the fields was registered as an RECORD ID field.
    //One field must be RECORD ID, otherwise the record for which this value object
    //is created cannot be processed.  One and only one RECORD ID field can be registered.
    private boolean isRecordIDFieldSet = false;
    //Refers to RECORD ID field.
    private RecordValueObjectField recordIdField = null;
    //Map of voFields with selector getter method names as keys.
    private HashMap voFieldMapByFieldKey = null;
    //indicates whether this object has complex PK
    private boolean hasComplexPK = false;
    // set of complexPK fields
    private HashSet complexPKSet = null;
    // entity group index
    private Integer entityGroupId = null;
    // entity plugin Id
    private Integer entityPluginId = null;

    public RecordMapperValueObject() {
        super();
        loadVoFieldMap();
    }

    /**
     * This method is used to register value object fields which are NOT identifiable,
     * so these fields are not retrieved by the identifier query, they are going to be used
     * for different purpose altogether.  One possible usage is to parameterize modification
     * SQL statements  with these kinds of fields.  Not Record Id, not part of complex PK.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the record value object setter method for the vo property.
     * @param setterType  Type of the record value object setter method for the vo property.
     * @param getterName  Name of the record value object getter method for the vo property.
     */
    protected void addNonIdentifiableVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        registerVOField(false, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false, null, null, false, null);
    }

    /**
     * This method is used to register value object fields which are NOT identifiable,
     * so these fields are not retrieved by the identifier query, they are going to be used
     * for different purpose altogether.  One possible usage is to parameterize modification
     * SQL statements  with these kinds of fields.  Not Record Id, not part of complex PK.  However
     * this record IS modifiable, which means that it will have a new value created for it by the modifier.
     *
     * @param dbFieldName        Name of the database field that is being mapped.
     * @param dbFieldType        Type of the database field that is being mapped.
     * @param setterName         Name of the record value object setter method for the vo property.
     * @param setterType         Type of the record value object setter method for the vo property.
     * @param getterName         Name of the record value object getter method for the vo property.
     * @param modifierGetterName Name of the modifier getter method, which is used
     *                           to retrieve new value for this field.
     */
    protected void addNonIdentifiableModifiableVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName, String modifierGetterName) {
        registerVOField(false, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false, modifierGetterName, null, true, null);
    }

    /**
     * This method is used to register reference value object fields which are NOT modifiable.
     * Not Record Id, not part of complex PK.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the record value object setter method for the vo property.
     * @param setterType  Type of the record value object setter method for the vo property.
     * @param getterName  Name of the record value object getter method for the vo property.
     */
    protected void addReferenceVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false, null, null, false, null);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID and not
     * part of complex PK fields, which is latter used by the BaseDAO in order to map database field
     * values to the vo properties.
     *
     * @param dbFieldName                 Name of the database field that is being mapped.
     * @param dbFieldType                 Type of the database field that is being mapped.
     * @param setterName                  Name of the record value object setter method for the vo property.
     * @param setterType                  Type of the record value object setter method for the vo property.
     * @param getterName                  Name of the record value object getter method for the vo property.
     * @param modifierValidatorMethodName Name of the corrector validator method, which is used
     *                                    to validate new value for this field (record field.)
     */
    protected void addVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName,
                              String modifierValidatorMethodName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false,
                null, modifierValidatorMethodName, true, null);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID and not
     * part of complex PK fields, which is latter used by the BaseDAO in order to map database field
     * values to the vo properties.
     *
     * @param dbFieldName        Name of the database field that is being mapped.
     * @param dbFieldType        Type of the database field that is being mapped.
     * @param setterName         Name of the record value object setter method for the vo property.
     * @param setterType         Type of the record value object setter method for the vo property.
     * @param getterName         Name of the record value object getter method for the vo property.
     * @param modifierGetterName Name of the modifier method.
     */
    protected void addVOFieldWithModifierNoValidator(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName,
                                                     String modifierGetterName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false,
                modifierGetterName, null, true, null);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID
     * and not part of complex PK fields, which is latter used by the BaseDAO in order to map
     * database field values to the vo properties.
     *
     * @param dbFieldName                 Name of the database field that is being mapped.
     * @param dbFieldType                 Type of the database field that is being mapped.
     * @param setterName                  Name of the record value object setter method for the vo property.
     * @param setterType                  Type of the record value object setter method for the vo property.
     * @param getterName                  Name of the record value object getter method for the vo property.
     * @param modifierGetterName          Name of the modifier getter method, which is used
     *                                    to retrieve new value for this field.
     * @param modifierValidatorMethodName Name of the corrector validator method, which is used
     *                                    to validate new value for this field.
     */
    protected void addVOField(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName,
                              String modifierGetterName, String modifierValidatorMethodName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName, false, false,
                modifierGetterName, modifierValidatorMethodName, true, null);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID
     * and not part of complex PK fields, which is latter used by the BaseDAO in order to map
     * database field values to the vo properties.
     *
     * @param selectorGetterName Name of the selector getter method.
     * @param dbFieldName        Name of the database field that is being mapped.
     * @param dbFieldType        Type of the database field that is being mapped.
     * @param setterName         Name of the record value object setter method for the vo property.
     * @param setterType         Type of the record value object setter method for the vo property.
     * @param getterName         Name of the record value object getter method for the vo property.
     */
    protected void addVOFieldWithSelector(String selectorGetterName, String dbFieldName, int dbFieldType,
                                          String setterName, Class setterType, String getterName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName,
                false, false, null, null, true, selectorGetterName);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID
     * and not part of complex PK fields, which is latter used by the BaseDAO in order to map
     * database field values to the vo properties.
     *
     * @param selectorGetterName          Name of the selector getter method.
     * @param dbFieldName                 Name of the database field that is being mapped.
     * @param dbFieldType                 Type of the database field that is being mapped.
     * @param setterName                  Name of the record value object setter method for the vo property.
     * @param setterType                  Type of the record value object setter method for the vo property.
     * @param getterName                  Name of the record value object getter method for the vo property.
     * @param modifierValidatorMethodName Name of the corrector validator method, which is used
     *                                    to validate new value for this field (record field.)
     */
    protected void addVOFieldWithSelector(String selectorGetterName, String dbFieldName, int dbFieldType,
                                          String setterName, Class setterType, String getterName, String modifierValidatorMethodName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName,
                false, false, null, modifierValidatorMethodName, true, selectorGetterName);
    }

    /**
     * This is a convenience method used to register value object fields that are not RECORD ID
     * and not part of complex PK fields, which is latter used by the BaseDAO in order to map
     * database field values to the vo properties.
     *
     * @param selectorGetterName          Name of the selector getter method.
     * @param dbFieldName                 Name of the database field that is being mapped.
     * @param dbFieldType                 Type of the database field that is being mapped.
     * @param setterName                  Name of the record value object setter method for the vo property.
     * @param setterType                  Type of the record value object setter method for the vo property.
     * @param getterName                  Name of the record value object getter method for the vo property.
     * @param modifierGetterName          Name of the corrector getter method, which is used
     *                                    to retrieve new value for this field (record field.)
     * @param modifierValidatorMethodName Name of the corrector validator method, which is used
     *                                    to validate new value for this field (record field.)
     */
    protected void addVOFieldWithSelector(String selectorGetterName, String dbFieldName, int dbFieldType,
                                          String setterName, Class setterType, String getterName,
                                          String modifierGetterName, String modifierValidatorMethodName) {
        registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName,
                false, false, modifierGetterName, modifierValidatorMethodName, true, selectorGetterName);
    }

    /**
     * This is a convenience method used to register RECORD ID value object fields, which
     * is latter used by the BaseDAO in order to map database field values to the vo properties.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the record value object setter method for the vo property.
     * @param setterType  Type of the record value object setter method for the vo property.
     * @param getterName  Name of the record value object getter method for the vo property.
     */
    protected void addVOFieldRecordID(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        if (isRecordIDFieldSet) {
            throw new GESystemException("RECORD ID can be registered only once per value object.");
        }
        isRecordIDFieldSet = true;
        this.recordIdField = registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName,
                true, false, null, null, false, null);
    }

    /**
     * This is a convenience method used to register not RECORD ID, but other parts of complex
     * PK value object fields, which is latter used by the BaseDAO in order to map database
     * field values to the vo properties.
     *
     * @param dbFieldName Name of the database field that is being mapped.
     * @param dbFieldType Type of the database field that is being mapped.
     * @param setterName  Name of the record value object setter method for the vo property.
     * @param setterType  Type of the record value object setter method for the vo property.
     * @param getterName  Name of the record value object getter method for the vo property.
     */
    protected void addVOFieldPartOfComplexPK(String dbFieldName, int dbFieldType, String setterName, Class setterType, String getterName) {
        hasComplexPK = true;
        if (complexPKSet == null) {
            complexPKSet = new HashSet();
        }
        this.complexPKSet.add(registerVOField(true, dbFieldName, dbFieldType, setterName, setterType, getterName,
                false, true, null, null, false, null));
    }

    /**
     * Returns record RECORD ID field.
     */
    public RecordValueObjectField getRecordIDField() {
        if (!isRecordIDFieldSet) {
            throw new GESystemException("RECORD ID was not registered on this value object.");
        }
        return this.recordIdField;
    }

    /**
     * Loads registered selector RecordValueObjectFields into
     * a map with field keys as map keys.
     */
    private void loadVoFieldMap() {
        if (!fields.isEmpty()) {
            voFieldMapByFieldKey = new HashMap();
            Iterator iter = fields.iterator();
            while (iter.hasNext()) {
                RecordValueObjectField voField = (RecordValueObjectField) iter.next();
                voFieldMapByFieldKey.put(voField.databaseFieldName, voField);
            }
        }
    }

    /**
     * Retrieves Record Value Object Field by the provided field key.
     *
     * @param fieldKey Field Key.
     * @return RecordValueObjectField    Record Value Object Field
     */
    public RecordValueObjectField getObjectFieldByFieldKey(String fieldKey) {
        if (voFieldMapByFieldKey == null) {
            // in case when a record mapper value object is instantiated via
            // reflection invocation, the vo field registration happens after
            // loadVoFieldMap() call, which leaves the voFieldMapByFieldKey map
            // null, so we need to reload it.
            loadVoFieldMap();
        }
        RecordValueObjectField voField = (RecordValueObjectField)
                voFieldMapByFieldKey.get(fieldKey);
        if (voField == null) {
            throw new GESystemException("Field key [" +
                    fieldKey + "] undefined for plugin ID " + getPluginId());
        }
        return voField;
    }

    public HashSet getComplexPKSet() {
        return complexPKSet;
    }

    public boolean hasComplexPK() {
        return hasComplexPK;
    }

    public Integer getEntityGroupId() {
        return this.entityGroupId;
    }

    public void setEntityGroupId(Integer entityGroupId) {
        this.entityGroupId = entityGroupId;
    }

    public Integer getEntityPluginId() {
        return this.entityPluginId;
    }

    public void setEntityPluginId(Integer caseTypeId) {
        this.entityPluginId = caseTypeId;
    }
}