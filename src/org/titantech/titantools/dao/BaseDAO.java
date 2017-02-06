package org.titantech.titantools.dao;


import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.sql.Types;
import java.text.ParseException;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.titantech.titantools.AppConstants;
import org.titantech.titantools.dao.valuebean.IRecordValueObject;
import org.titantech.titantools.dao.valuebean.RecordValueObjectField;
import org.titantech.titantools.util.ClassAndObjectHelper;

public abstract class BaseDAO {
    /**
     * A convenience method for DAO implementations to finalize ResultSets and Statements.
     *
     * @param rs
     * @param stmt
     */
    protected void finalize(ResultSet rs, Statement stmt) {
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException ex) {
                throw new DAOSysException(ex.getMessage());
            }
        }
        if (stmt != null) {
            try {
                stmt.close();
            } catch (SQLException e) {
                throw new DAOSysException(e.getMessage());
            }
        }
    }

    /**
     * Populates record data from the provided result set into
     * a new instance of the provided value object class.
     *
     * @param rs               ResultSet record.
     * @param valueObjectClass Value Object Class.
     * @param caseTypeId       Case Type ID.
     * @return Value Object instance populated with result set data.
     * @throws DAOAppException thrown on value object instantiation errors and data population errors.
     */
    protected IRecordValueObject populateValueObject(ResultSet rs, Class valueObjectClass,
                                                     Integer caseTypeId) throws DAOAppException {
        IRecordValueObject caseValueObject =
                ClassAndObjectHelper.getRecordValueObjectInstance(valueObjectClass);
        caseValueObject.setPluginId(caseTypeId);
        return populateValueObject(rs, caseValueObject);
    }

    /**
     * Populates record data from the provided result set into
     * the provided value object.  The data is populated by reflectively
     * reading the value object fields and finding corresponding fields in the result set.
     *
     * @param rs Result Set data.
     * @param vo Value Object instance.
     * @return Populated Value Object.
     * @throws DAOAppException thrown on data population errors.
     */
    private IRecordValueObject populateValueObject(ResultSet rs, IRecordValueObject vo) throws DAOAppException {
        Set setColNames = new HashSet();
        try {
            ResultSetMetaData meta = rs.getMetaData();
            int numberOfColumns = meta.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                setColNames.add(meta.getColumnName(i).toLowerCase());
            }
        } catch (SQLException e) {
            throw new DAOAppException("Cannot retrieve result set meta data ");
        }
        Class c = vo.getClass();
        RecordValueObjectField voField = null;
        Method method = null;
        try {
            while (vo.hasNextField()) {
                voField = vo.nextField();
                if (!voField.isIdentifiable) {
                    continue;
                }
                Class[] parameterTypes = new Class[]{voField.setterType};
                method = c.getMethod(voField.setterMethodName, parameterTypes);
                // if this column is not selected by the query, don't try to retrieve it.
                if (!setColNames.contains(voField.databaseFieldName.toLowerCase())) {
                    continue;
                }
                if (voField.databaseFieldType == Types.VARCHAR) {
                    String value = rs.getString(voField.databaseFieldName);
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.DATE) {
                    Timestamp timestamp = rs.getTimestamp(voField.databaseFieldName);
                    java.util.Date value = null;
                    if (timestamp != null) {
                        value = new Date(timestamp.getTime());
                    }
                    Object[] arguments = null;
                    if (value != null) {
                        arguments = new Object[]{value};
                    } else {
                        arguments = new Object[]{null};
                    }
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.INTEGER) {
                    Integer value = new Integer(rs.getInt(voField.databaseFieldName));
                    if (rs.wasNull()) {
                        // TODO find out if this is going to cause problems for any plug-ins.
                        // Otherwise leave the field value null.
                    } else {
                        Object[] arguments = new Object[]{value};
                        method.invoke(vo, arguments);
                    }
                } else if (voField.databaseFieldType == Types.CHAR) {
                    String value = rs.getString(voField.databaseFieldName);
                    Object[] arguments = null;
                    if (value == null || value.length() == 0) {
                        arguments = new Object[]{null};
                    } else if (voField.setterType == String.class) {
                        arguments = new Object[]{value};
                    } else if (voField.setterType == Character.class) {
                        arguments = new Object[]{new Character(value.charAt(0))};
                    } else if (voField.setterType == Boolean.class) {
                        Boolean bool = null;
                        if (AppConstants.GE_BOOLEAN_CHAR_YES.equals(value)) {
                            bool = new Boolean(true);
                        } else {
                            bool = new Boolean(false);
                        }
                        arguments = new Object[]{bool};
                    }
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.TIMESTAMP) {
                    Timestamp timestamp = rs.getTimestamp(voField.databaseFieldName);
                    java.util.Date value = null;
                    if (timestamp != null) {
                        value = new Date(timestamp.getTime());
                    }
                    Object[] arguments = null;
                    if (value != null) {
                        arguments = new Object[]{value};
                    } else {
                        arguments = new Object[]{null};
                    }
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.TIME) {
                    java.sql.Time value = rs.getTime(voField.databaseFieldName);
                    Object[] arguments = null;
                    if (value != null) {
                        arguments = new Object[]{new java.util.Date(value.getTime())};
                    } else {
                        arguments = new Object[]{null};
                    }
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.NUMERIC) {
                    Long value = new Long(rs.getLong(voField.databaseFieldName));
                    Object[] arguments = null;
                    if (value == null) {
                        arguments = new Object[]{null};
                    } else if (voField.setterType == Long.class) {
                        arguments = new Object[]{value};
                    } else if (voField.setterType == Integer.class) {
                        arguments = new Object[]{new Integer(value.intValue())};
                    }
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.BIGINT) {
                    Long value = new Long(rs.getLong(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.DOUBLE) {
                    Double value = new Double(rs.getDouble(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.FLOAT) {
                    Float value = new Float(rs.getFloat(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.DECIMAL) {
                    Double value = new Double(rs.getDouble(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.SMALLINT) {
                    Short value = new Short(rs.getShort(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                } else if (voField.databaseFieldType == Types.TINYINT) {
                    Byte value = new Byte(rs.getByte(voField.databaseFieldName));
                    Object[] arguments = new Object[]{value};
                    method.invoke(vo, arguments);
                }
                /*
                Types.ARRAY; Types.BINARY; Types.BIT; Types.BLOB; Types.BOOLEAN; Types.CLOB; Types.DATALINK;
				Types.STRUCT; Types.VARBINARY; Types.DISTINCT; Types.JAVA_OBJECT; Types.LONGVARBINARY;
				Types.LONGVARCHAR; Types.OTHER; Types.REAL; Types.REF;
				*/
            }
        } catch (NoSuchMethodException e) {
            throw new DAOAppException("No such value object setter method " +
                    voField.setterMethodName + " in " + vo.getClass().getName());
        } catch (IllegalAccessException e) {
            throw new DAOAppException("Cannot access value object setter method " +
                    voField.setterMethodName + " in " + vo.getClass().getName());
        } catch (InvocationTargetException e) {
            throw new DAOAppException("Cannot invoke value object setter method " +
                    voField.setterMethodName + " in " + vo.getClass().getName());
        } catch (SQLException e) {
            throw new DAOAppException("Cannot retrieve database value from " +
                    voField.databaseFieldName + " field");
        }
        return vo;
    }

    /**
     * This method sets a prepared statement dynamic parameter
     * of the provided SQL type to the provided value.  If the provided
     * parameter value is null then the prepared statement parameter is
     * set to NULL with the provided SQL parameter type.
     *
     * @param ps            Prepared Statement.
     * @param i             Dynamic parameter index.
     * @param paramStrValue Parameter value as a String.
     * @param parameterType Parameter SQL type.
     * @return i    modified dynamic parameter index.
     * @throws SQLException
     * @throws ParseException
     */
    protected int setSQLParameter(PreparedStatement ps, int i, String paramStrValue,
                                  int parameterType) throws SQLException, ParseException {
        if (paramStrValue == null || paramStrValue.length() == 0) {
            ps.setNull(i, parameterType);
        } else {
            if (parameterType == Types.VARCHAR) {
                if (paramStrValue == null || paramStrValue.length() == 0) {
                    ps.setNull(i++, Types.VARCHAR);
                } else {
                    ps.setString(i, paramStrValue);
                }
            } else if (parameterType == Types.DATE) {
                Date date = AppConstants.GE_formatter.parse(paramStrValue);
                Timestamp value = new Timestamp(date.getTime());
                ps.setTimestamp(i, value);
            } else if (parameterType == Types.INTEGER) {
                Integer value = new Integer(paramStrValue);
                ps.setInt(i, value.intValue());
            } else if (parameterType == Types.CHAR) {
                ps.setString(i, paramStrValue);
            } else if (parameterType == Types.TIMESTAMP) {
                java.sql.Date value = new java.sql.Date(
                        (AppConstants.GE_formatter.parse(paramStrValue)).getTime());
                ps.setDate(i, value);
            } else if (parameterType == Types.TIME) {
                java.sql.Date value = new java.sql.Date(
                        (AppConstants.GE_formatter.parse(paramStrValue)).getTime());
                ps.setDate(i, value);
            } else if (parameterType == Types.NUMERIC) {
                Long value = new Long(paramStrValue);
                ps.setLong(i, value.longValue());
            } else if (parameterType == Types.BIGINT) {
                Long value = new Long(paramStrValue);
                ps.setLong(i, value.longValue());
            } else if (parameterType == Types.DOUBLE) {
                Double value = new Double(paramStrValue);
                ps.setDouble(i, value.doubleValue());
            } else if (parameterType == Types.FLOAT) {
                Float value = new Float(paramStrValue);
                ps.setFloat(i, value.floatValue());
            } else if (parameterType == Types.DECIMAL) {
                Double value = new Double(paramStrValue);
                ps.setDouble(i, value.doubleValue());
            } else if (parameterType == Types.SMALLINT) {
                Short value = new Short(paramStrValue);
                ps.setShort(i, value.shortValue());
            } else if (parameterType == Types.TINYINT) {
                Byte value = new Byte(paramStrValue);
                ps.setByte(i, value.byteValue());
            }
        }
        /*
		Types.ARRAY; Types.BINARY; Types.BIT; Types.BLOB; Types.BOOLEAN; Types.CLOB; Types.DATALINK;
		Types.STRUCT; Types.VARBINARY; Types.DISTINCT; Types.JAVA_OBJECT; Types.LONGVARBINARY;
		Types.LONGVARCHAR; Types.OTHER; Types.REAL; Types.REF;
		*/
        i++;
        return i;
    }
}
