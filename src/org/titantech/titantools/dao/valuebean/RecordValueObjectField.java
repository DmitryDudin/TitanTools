/*
$RCSfile: RecordValueObjectField.java,v $

Change Summary
==============
*/

package org.titantech.titantools.dao.valuebean;

import java.io.Serializable;


/**
 */
public class RecordValueObjectField implements Serializable {
    private static final long serialVersionUID = 8011194813842536031L;

    // the value of this property indicates whether this field is identified by
    // an identifier query or not (by default it is identified.)
    public boolean isIdentifiable = true;
    // the value of this property identifies this value object as an RECORD ID field.
    public boolean isRecordId = false;
    // the value of this property identifies this value object part of a complex primary
    public boolean isPartOfComplexPK = false;
    // the value of this property is the name of a database field.
    public String databaseFieldName = null;
    // the value of this property is one of the java.sql.Types specific type, which is always an integer.
    public int databaseFieldType = -1;
    // the value of this property is the setter method name used to set the value from the database field.
    public String setterMethodName;
    // the value of this property is the getter method name used to retrieve RECORD ID field value.
    public String getterMethodName;
    // the value of this property is the type of the parameter to be used to set the database value
    // to the value object field.
    public Class setterType;

    public RecordValueObjectField(boolean isRecordId) {
        this.isRecordId = isRecordId;
    }

    // the value of this property is the name of the modifier getter method, which is used to retrieve
    // modified value for this field.
    public String modifierGetterName = null;
    // modifierValidatorMethodName	Name of the modifier validator method, which is used
    // to validate new value for this field.
    public String modifierValidatorMethodName = null;
    // the value of this property indicates whether this field is correctable either automatically or manually.
    public boolean modifiable = false;
    // the value of this property is the name of the selector getter method, which is used to retrieve
    // map selection data field to a record value object data field, which is used in the manual modification to
    // allow user to select a possible new value for a field, where the data comes from record selectors.
    public String selectorGetterName;
}