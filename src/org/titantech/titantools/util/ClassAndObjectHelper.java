/*
$RCSfile: ClassAndObjectHelper.java,v $

Change Summary
==============
$Log: ClassAndObjectHelper.java,v $
Revision 1.1.1.1  2009/07/09 01:17:07  rmironenko
no message

Revision 1.1.1.1  2008/09/19 23:01:54  rmironenko
no message

Revision 1.6  2008/04/18 21:11:23  jtam
Refactor application constants.

Revision 1.5  2008/04/17 19:34:44  jtam
recordId convertToString

Revision 1.4  2008/04/03 15:37:40  jtam
naming conventions

Revision 1.3  2008/03/31 00:07:13  rmironenko
Adding cleanup capability

Revision 1.2  2008/03/30 18:17:42  rmironenko
to DOS

Revision 1.1.1.1  2008/03/26 18:09:58  rmironenko
no message

Revision 1.15  2007/05/11 14:44:11  rmironenko
case type Id now exposed to mapper value object

Revision 1.14  2007/05/10 17:30:51  rmironenko
added group index

Revision 1.13  2007/05/04 16:46:47  rmironenko
fixed mapper value object population for manual corrector

Revision 1.12  2007/05/04 16:34:03  rmironenko
fixed mapper value object population for manual corrector

Revision 1.11  2006/11/07 00:12:35  rmironenko
removed redundant toLowerCase() function

Revision 1.10  2006/10/30 16:52:38  rmironenko
fixed convertToString object

Revision 1.9  2006/10/29 03:14:03  rmironenko
in progress

Revision 1.8  2006/10/25 23:07:48  rmironenko
in progress

Revision 1.7  2006/10/23 22:56:53  rmironenko
introduced multiple selectors per case type

Revision 1.6  2006/10/23 15:11:44  rmironenko
added CaseMapperValueObject instantiator

Revision 1.5  2006/10/20 14:40:54  rmironenko
in progress

Revision 1.4  2006/10/19 21:40:15  rmironenko
in progress

Revision 1.3  2006/10/13 17:02:19  rmironenko
changed name of auto corrector to corrector

Revision 1.2  2006/10/12 23:20:36  rmironenko
in progress

Revision 1.1  2006/10/11 23:16:50  rmironenko
new code

*/

package org.titantech.titantools.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;

import org.titantech.titantools.AppConstants;
import org.titantech.titantools.GESystemException;
import org.titantech.titantools.dao.valuebean.IRecordMapperValueObject;
import org.titantech.titantools.dao.valuebean.IRecordValueObject;
import org.titantech.titantools.dao.valuebean.RecordValueObjectField;

/**
 * @author roman.mironenko
 * @version 1.0
 * @created 11-Oct-2006
 */
public class ClassAndObjectHelper {

    /**
     * Get Class from provided class name.
     *
     * @param className
     * @return Class
     * @throws GESystemException
     */
    public static Class getClass(String className) throws GESystemException {
        try {
            return Class.forName(className);
        } catch (ClassNotFoundException e) {
            throw new GESystemException("Cannot find class " +
                    className + " : " + e.getMessage());
        }
    }

    /**
     * Get instance of provided Class.
     *
     * @param c Class
     * @return Object    Instance
     */
    public static Object getObjectInstance(Class c) {
        try {
            return c.newInstance();
        } catch (InstantiationException e) {
            throw new GESystemException("Cannot instantiate object " +
                    c.getName() + " : " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new GESystemException("Cannot instantiate object " +
                    c.getName() + " : " + e.getMessage());
        }
    }

    /**
     * Get instance of Record Value Object from the provided Class.
     *
     * @param recordValueObjectClass Record Value Object Class.
     * @return IRecordValueObject    Instance of Record Value Object.
     */
    public static IRecordValueObject getRecordValueObjectInstance(Class recordValueObjectClass) {
        Object object = ClassAndObjectHelper.getObjectInstance(recordValueObjectClass);
        if (!(object instanceof IRecordValueObject)) {
            throw new GESystemException("Record Value Object " +
                    recordValueObjectClass.getName() + " must implement IRecordValueObject interface.");
        }
        return (IRecordValueObject) object;
    }

    /**
     * Get instance of Record Mapper Value Object from the provided Class.
     *
     * @param recordMapperValueObjectClass Record Mapper Value Object Class.
     * @return IRecordMapperValueObject    Instance of Record Mapper Value Object.
     */
    public static IRecordMapperValueObject getRecordMapperValueObjectInstance(Class recordMapperValueObjectClass) {
        Object object = ClassAndObjectHelper.getObjectInstance(recordMapperValueObjectClass);
        if (!(object instanceof IRecordMapperValueObject)) {
            throw new GESystemException("Record Mapper Value Object " +
                    recordMapperValueObjectClass.getName() + " must implement IRecordMapperValueObject interface.");
        }
        return (IRecordMapperValueObject) object;
    }

    /**
     * Get instance of Modifier Object from the provided class name.
     * @param modifierClassName    Modifier class name.
     * @return IRecordAutoModifier    Instance of modifier.
     */
    //public static IRecordModifier getRecordAutoModifierInstance(String modifierClassName) {
    //	Class modifierClass = getClass(modifierClassName);
    //	Object object = ClassAndObjectHelper.getObjectInstance(modifierClass);
    //	if (!(object instanceof IRecordModifier)) {
    //		throw new BOSystemException("Modifier object " +
    //				modifierClassName + " must implement IRecordAutoModifier interface.");
    //	}
    //	return (IRecordModifier)object;
    //}

    /**
     * Converts provided object value into a String value.
     *
     * @param objValue
     * @return
     */
    public static String convertToString(Object objValue) {
        String result = null;
        if (objValue == null) {
            // return null then
        } else if (objValue instanceof String) {
            result = (String) objValue;
        } else if (objValue instanceof Integer) {
            return ((Integer) objValue).toString();
        } else if (objValue instanceof Long) {
            return ((Long) objValue).toString();
        } else if (objValue instanceof java.util.Date) {
            return ((java.util.Date) objValue).toString();
        } else if (objValue instanceof Character) {
            return ((Character) objValue).toString();
        } else if (objValue instanceof Double) {
            return ((Double) objValue).toString();
        } else if (objValue instanceof Float) {
            return ((Float) objValue).toString();
        } else if (objValue instanceof Short) {
            return ((Short) objValue).toString();
        } else if (objValue instanceof Byte) {
            return ((Byte) objValue).toString();
        } else if (objValue instanceof Number) {
            return ((Number) objValue).toString();
        } else {
            throw new GESystemException("Cannot convert object to String : [" +
                    objValue.getClass().getName() + " : " + objValue + "]");
        }
        return result;
    }

    /**
     * Converts provided String value into the provided specific type.
     *
     * @param stringValue
     * @param class       Class type
     * @return Object    Provided String value converted into provided type.
     */
    public static Object convertFromString(String stringValue, Class type) {
        if (stringValue == null) {
            return null;
        }
        Object result = null;
        if (type == String.class) {
            result = stringValue;
        } else if (type == Integer.class) {
            result = Integer.valueOf(stringValue);
        } else if (type == Long.class) {
            result = Long.valueOf(stringValue);
        } else if (type == java.util.Date.class) {
            try {
                result = AppConstants.GE_formatter.parse(stringValue);
            } catch (ParseException e) {
                throw new GESystemException("Cannot convert String to java.util.Date : [" +
                        type.getClass().getName() + " : " + stringValue + "]");
            }
        } else if (type == Character.class) {
            if (stringValue.length() > 0) {
                result = new Character(stringValue.charAt(0));
            } else {
                result = null;
            }
        } else if (type == Double.class) {
            result = Double.valueOf(stringValue);
        } else if (type == Float.class) {
            result = Float.valueOf(stringValue);
        } else if (type == Short.class) {
            result = Short.valueOf(stringValue);
        } else if (type == Byte.class) {
            result = Byte.valueOf(stringValue);
        } else if (type == Number.class) {
            result = Long.valueOf(stringValue); // uncertain about this
        } else {
            throw new GESystemException("Cannot convert String to object : [" +
                    type.getClass().getName() + " : " + stringValue + "]");
        }
        return result;
    }

    /**
     * Retrieves value of RECORD ID field from record value object.
     *
     * @param valueObject Record Value Object.
     * @return RecordId    Record Id string.
     */
    public static String getRecordIdFromRecordValueObject(IRecordMapperValueObject valueObject) {
        RecordValueObjectField recordIdField = valueObject.getRecordIDField();
        String recordId = null;
        try {
            Method method = valueObject.getClass().getMethod(recordIdField.getterMethodName, (Class<?>[]) null);
            recordId = convertToString(method.invoke(valueObject, (Object[]) null));
        } catch (SecurityException e) {
            throw new GESystemException("Cannot access value object Record Id getter method for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new GESystemException("No record ID getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new GESystemException("No legal argument for record ID getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new GESystemException("No access to record ID getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new GESystemException("Cannot execute record ID getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        }
        return recordId;
    }

    /**
     * Retrieves value by specified getter name from record value object.
     *
     * @param valueObject Record Value Object.
     * @return RecordId    Record Id string.
     */
    public static Object getGetterMethodValueFromRecordValueObject(IRecordValueObject valueObject, String getterName) {
        Object value = null;
        try {
            Method method = valueObject.getClass().getMethod(getterName, (Class<?>[]) null);
            value = method.invoke(valueObject, (Object[]) null);
        } catch (SecurityException e) {
            throw new GESystemException("Cannot access value object getter method " + getterName + " for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (NoSuchMethodException e) {
            throw new GESystemException("No " + getterName + " getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (IllegalArgumentException e) {
            throw new GESystemException("No legal argument for " + getterName + " getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (IllegalAccessException e) {
            throw new GESystemException("No access to " + getterName + " getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        } catch (InvocationTargetException e) {
            throw new GESystemException("Cannot execute " + getterName + " getter method on value object for record type " +
                    valueObject.getPluginId() + " : " + e.getMessage());
        }
        return value;
    }

    /**
     * Find if there is a specified method with specified parameters
     * in the specified class, if there is return true.
     *
     * @param class          Class to verify for specified method existence.
     * @param methodName     Find this method name is specified class.
     * @param parameterTypes Method parameter types.
     * @return boolean    true if specified method with specified parameters
     * exists within specified class, false otherwise.
     */
    public static boolean checkClassForMethod(Class clazz, String methodName,
                                              Class[] parameterTypes) {
        boolean result = true;
        try {
            Method method = clazz.getMethod(methodName, parameterTypes);
            if (method == null) {
                result = false;
            }
        } catch (SecurityException e) {
            result = false;
        } catch (NoSuchMethodException e) {
            result = false;
        } catch (IllegalArgumentException e) {
            result = false;
        }
        return result;
    }
}