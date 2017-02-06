package org.titantech.titantools.generator.frontend;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;

public class SearchPageStrutsFormGenerator extends GeneratorBase {

    public String generateStrutsFormClass(String strutsFormName,
                                          String daoVOName, String formBeanVOName, String filterVOName, List inputBeans) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(strutsFormName, daoVOName, formBeanVOName, filterVOName));
        contents.append(generateFields(inputBeans, strutsFormName));
        contents.append(generateOrderFields(inputBeans, filterVOName));
        contents.append(generateSimpleResetMethod(inputBeans));
        contents.append(generateStrutsValidateMethod(inputBeans));
        contents.append(generateMapListMethod(inputBeans, daoVOName, formBeanVOName));
        contents.append(generateLoadFiltersMethod(inputBeans));
        contents.append(generateStrutsFormConstantMethods());
        contents.append(generateVOGettersSetters(inputBeans, filterVOName));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateClassFileHeader(String strutsFormName,
                                                 String daoVOName, String formBeanVOName, String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                STRUTS_FORM_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = STRUTS_FORM_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_CONSTANTS).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_EXCEPTION).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(FILTER_BEAN_JAVA_PACKAGE_NAME).append(PERIOD).append(filterVOName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(VO_JAVA_PACKAGE_NAME).append(PERIOD).append(daoVOName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(WEB_BEAN_JAVA_PACKAGE_NAME).append(PERIOD).append(formBeanVOName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(WEB_TAG_JAVA_PACKAGE_NAME).append(PERIOD).append(APP_ACTION_CONSTANTS).append(SEMICOLON).append(NEW_LINE);


        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(strutsFormName)
                .append(SPACE);

        if (!STRUTS_FORM_CLASS_EXTENDS.isEmpty()) {
            header.append(JAVA_KEYWORD_EXTENDS).append(SPACE);
            iter = STRUTS_FORM_CLASS_EXTENDS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        } else {
            header.append(OPEN_BRACE).append(DBL_NEW_LINE);
        }
        header.append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);

        return header;
    }

//	private StringBuffer generateFields(List inputBeans) {
//		StringBuffer sb = new StringBuffer();
//		sb.append(NEW_LINE);
//		Iterator iter = inputBeans.iterator();
//		while (iter.hasNext()) {
//			SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
//			sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
//				.append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
//				.append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
//				.append(NEW_LINE);
//		}
//		return sb;
//	}

    private StringBuffer generateFields(List inputBeans, String strutsFormName) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(JAVA_LIB_CLASS).append(SPACE)
                .append(FIELD_NAME_CLAZZ).append(SPACE).append(EQUAL).append(SPACE)
                .append(strutsFormName).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                .append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(APACHE_CLASS_LOGGER).append(SPACE)
                .append(FIELD_NAME_LOGGER).append(SPACE).append(EQUAL).append(SPACE)
                .append(APACHE_CLASS_LOGGER).append(PERIOD).append(METHOD_NAME_GETLOGGER)
                .append(OPEN_BRACKET).append(FIELD_NAME_CLAZZ).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE).append(JAVA_LIB_STRING)
                .append(SPACE).append(FIELD_NAME_ACTION_NAME).append(SPACE).append(EQUAL).append(SPACE).append(NULL)
                .append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE).append(JAVA_LIB_UTIL_LIST)
                .append(SPACE).append(FIELD_NAME_LIST).append(SPACE).append(EQUAL).append(SPACE).append(NULL)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

//
//		private String actionName = null;
//		private List discountCards = null;


        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            } else {
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            }
        }
        return sb;
    }

    private StringBuffer generateOrderFields(List inputBeans, String filterVOName) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_LIB_STRING).append(TAB).append(PARAM_NAME_ORDERINGOFORDERELEMENTS)
                .append(SPACE).append(EQUAL).append(SPACE).append(DOUBLE_QUOTE)
                .append(DOUBLE_QUOTE).append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_SUFFIX_ORDER)
                        .append(SPACE).append(EQUAL).append(SPACE).append(DOUBLE_QUOTE)
                        .append("Not ordered").append(DOUBLE_QUOTE).append(SEMICOLON)
                        .append(NEW_LINE);
            }
        }
        sb.append(NEW_LINE);
        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(filterVOName).append(SPACE).append(FIELD_NAME_FILTER)
                .append(SPACE).append(EQUAL).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE)
                .append(filterVOName).append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);
        //	private ClientDiscountCardSearchFilter filter = new ClientDiscountCardSearchFilter();
        return sb;
    }

    private StringBuffer generateVOGettersSetters(List inputBeans, String filterVOName) {

        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);

        List otherFields = new ArrayList();
        otherFields.add(FIELD_NAME_ACTION_NAME);
        otherFields.add(FIELD_NAME_LIST);
        otherFields.add(FIELD_NAME_FILTER);
        otherFields.add(PARAM_NAME_ORDERINGOFORDERELEMENTS);

        Map typeMap = new HashMap();
        typeMap.put(FIELD_NAME_ACTION_NAME, JAVA_LIB_STRING);
        typeMap.put(FIELD_NAME_LIST, JAVA_LIB_UTIL_LIST);
        typeMap.put(FIELD_NAME_FILTER, filterVOName);
        typeMap.put(PARAM_NAME_ORDERINGOFORDERELEMENTS, JAVA_LIB_STRING);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.sortable) {
                String fName = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;
                otherFields.add(fName);
                typeMap.put(fName, JAVA_LIB_STRING);
            }
        }

        iter = otherFields.iterator();
        while (iter.hasNext()) {
            String fName = (String) iter.next();

            String typeName = (String) typeMap.get(fName);
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(typeName).append(SPACE)
                    .append(getJavaGetNameFromJavaFieldName(fName))
                    .append(OPEN_BRACKET)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB)
                    .append(JAVA_KEYWORD_RETURN).append(SPACE)
                    .append(fName)
                    .append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                    .append(getJavaSetNameFromJavaFieldName(fName))
                    .append(OPEN_BRACKET)
                    .append(typeName).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(fName))
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_THIS)
                    .append(PERIOD).append(fName)
                    .append(EQUAL)
                    .append(getJavaFieldNameFromDBFieldName(fName))
                    .append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }


        String typeName = JAVA_LIB_STRING;

        sb.append(NEW_LINE);
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            int rpt = 1;
            String sfx = "";
            if (in.range) {
                rpt = 2;
            }
            for (int i = 0; i < rpt; i++) {
                if (in.range) {
                    if (i == 0) {
                        sfx = FILTER_BEAN_RANGE_SUFFIX_FROM;
                    } else {
                        sfx = FILTER_BEAN_RANGE_SUFFIX_TO;
                    }
                }
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                        .append(SPACE).append(typeName).append(SPACE)
                        .append(getJavaGetNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(OPEN_BRACKET)
                        .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                        .append(NEW_LINE).append(TAB).append(TAB)
                        .append(JAVA_KEYWORD_RETURN).append(SPACE)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(SEMICOLON)
                        .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                        .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                        .append(getJavaSetNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(OPEN_BRACKET)
                        .append(typeName).append(SPACE)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                        .append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_THIS)
                        .append(PERIOD).append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(EQUAL)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(sfx)
                        .append(SEMICOLON)
                        .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
            }
        }
        return sb;
    }

    protected String generateStrutsFormConstantMethods() {
        StringBuffer sb = new StringBuffer();
        sb.append("	public void reset(ActionMapping mapping, HttpServletRequest request) {").append(NEW_LINE);
        sb.append("		try {").append(NEW_LINE);
        sb.append("			request.setCharacterEncoding(AppConstants.UTF8_CHARSET);").append(NEW_LINE);
        sb.append("		} catch (UnsupportedEncodingException e) {").append(NEW_LINE);
        sb.append("			e.printStackTrace();").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);

        sb.append("	private BigDecimal loadBigDecimal(String val) throws TCAppException {").append(NEW_LINE);
        sb.append("		if (val!=null && val.length()>0){").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				 return new BigDecimal(val);").append(NEW_LINE);
        sb.append("			} catch (NumberFormatException e) {").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private Integer loadInteger(String val) throws TCAppException {").append(NEW_LINE);
        sb.append("		if (val!=null && val.length()>0){").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				 return new Integer(val);").append(NEW_LINE);
        sb.append("			} catch (NumberFormatException e) {").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private Date loadDate(String value) throws TCAppException {").append(NEW_LINE);
        sb.append("		if (value != null && value.length() > 0) {").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				return AppConstants.TC_WEB_DATE_FORMAT.parse(value);").append(NEW_LINE);
        sb.append("			} catch (ParseException e) {").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private Long loadLong(String value) throws TCAppException {").append(NEW_LINE);
        sb.append("		if (value != null && value.length() > 0) {").append(NEW_LINE);
        sb.append("			try {").append(NEW_LINE);
        sb.append("				return new Long(value);").append(NEW_LINE);
        sb.append("			} catch (Exception e) {").append(NEW_LINE);
        sb.append("				throw new TCAppException(e.getMessage());").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private String parseDate(Date date) {").append(NEW_LINE);
        sb.append("		if(date != null) {").append(NEW_LINE);
        sb.append("			return AppConstants.TC_WEB_DATE_FORMAT.format(date);").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private String loadString(String value) {").append(NEW_LINE);
        sb.append("		if(value != null && value.trim().length() > 0 && !value.equals(\"All\")) {").append(NEW_LINE);
        sb.append("			return value.trim();").append(NEW_LINE);
        sb.append("		} else {").append(NEW_LINE);
        sb.append("			return null;").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private String loadFormString(String value) {").append(NEW_LINE);
        sb.append("		if(value == null || value.trim().length() == 0) {").append(NEW_LINE);
        sb.append("			return \"All\";").append(NEW_LINE);
        sb.append("		} else {").append(NEW_LINE);
        sb.append("			return value;").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private String loadOrder(String o) {").append(NEW_LINE);
        sb.append("		if (o == null) {").append(NEW_LINE);
        sb.append("			return null;").append(NEW_LINE);
        sb.append("		} else if (o.equals(\"Ascending\")) {").append(NEW_LINE);
        sb.append("			return \"ASC\";").append(NEW_LINE);
        sb.append("		} else if (o.equals(\"Descending\")) {").append(NEW_LINE);
        sb.append("			return \"DESC\";").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return null;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);

        return sb.toString();
    }

    protected String generateSimpleResetMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE).append("reset")
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(FIELD_NAME_LIST).append(SPACE).append(EQUAL).append(SPACE).append(NULL)
                .append(SEMICOLON).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            } else {
                sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            }
        }
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb.toString();
    }

    private StringBuffer generateStrutsValidateMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append("	public ActionErrors validate(ActionMapping mapping, HttpServletRequest request ) {").append(NEW_LINE);
        sb.append("		try {").append(NEW_LINE);
        sb.append("			request.setCharacterEncoding(AppConstants.UTF8_CHARSET);").append(NEW_LINE);
        sb.append("		} catch (UnsupportedEncodingException e) {").append(NEW_LINE);
        sb.append("			e.printStackTrace();").append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		ActionErrors errors = new ActionErrors();").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                String fName = null;
                for (int i = 0; i < 2; i++) {
                    if (i == 0) fName = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
                    else fName = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;
                    sb.append(generateEveryTypeOfValidationForAField(in, fName));
                }
            } else {
                sb.append(generateEveryTypeOfValidationForAField(in, getJavaFieldNameFromDBFieldName(in.dbFieldName)));
            }
        }

        sb.append(NEW_LINE);
        sb.append("		return errors;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateEveryTypeOfValidationForAField(SearchPageGeneratorInputBean in, String fName) {
        StringBuffer sb = new StringBuffer();
        //if (in.mandatory) {
        //	sb.append(generateValidationMandatoryCheck(in, fName));
        //}
        sb.append(generateValidationInputLengthCheck(in, fName));


        if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP ||
                in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.INTEGER ||
                in.dbFieldType == Types.DECIMAL) {
            sb.append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
                    .append(fName).append(LOGIC_NOTEQUAL).append(NULL).append(SPACE).append(LOGIC_AND).append(SPACE)
                    .append(fName).append(PERIOD).append("trim").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(PERIOD).append("length").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(GREATER).append(ZERO).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
            sb.append(generateValidationTryClause(in, fName));
        }
        return sb;
    }

    private StringBuffer generateValidationMandatoryCheck(SearchPageGeneratorInputBean in, String name) {
        StringBuffer sb = new StringBuffer();

        sb.append(NEW_LINE);
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
                .append(name).append(LOGIC_EQUAL).append(NULL).append(SPACE).append(LOGIC_OR).append(SPACE)
                .append(name).append(PERIOD).append("trim").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(PERIOD).append("length").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(LOGIC_EQUAL).append(ZERO).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        String errorType = null;
        if (in.dbFieldType == Types.VARCHAR || in.dbFieldType == Types.CHAR) {
            errorType = "error.parameter";
        } else if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP) {
            errorType = "error.date";
        } else if (in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.BIGINT ||
                in.dbFieldType == Types.INTEGER || in.dbFieldType == Types.DECIMAL) {
            errorType = "error.number";
        }
        sb.append(TAB).append(TAB).append(TAB)
                .append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                .append(COMMA).append(" new ActionError(").append(DOUBLE_QUOTE).append(errorType).append(DOUBLE_QUOTE).append(COMMA)
                .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(name)
                .append(" - mandatory parameter").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateValidationInputLengthCheck(SearchPageGeneratorInputBean in, String name) {
        StringBuffer sb = new StringBuffer();
        if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP || in.dbFieldLength == -1) {
            return sb;
        }

        sb.append(NEW_LINE);
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
                .append(name).append(LOGIC_NOTEQUAL).append(NULL).append(SPACE).append(LOGIC_AND).append(SPACE)
                .append(name).append(PERIOD).append("trim").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(PERIOD).append("length").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(GREATER).append(in.dbFieldLength).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        String errorType = null;
        if (in.dbFieldType == Types.VARCHAR || in.dbFieldType == Types.CHAR) {
            errorType = "error.parameter";
        } else if (in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.BIGINT ||
                in.dbFieldType == Types.INTEGER || in.dbFieldType == Types.DECIMAL) {
            errorType = "error.number";
        }
        sb.append(TAB).append(TAB).append(TAB)
                .append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                .append(COMMA).append(" new ActionError(").append(DOUBLE_QUOTE).append(errorType).append(DOUBLE_QUOTE).append(COMMA)
                .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(name)
                .append(" - too long").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;

//		if (firstName!=null && firstName.trim().length()>25) {
//			errors.add("firstName", new ActionError("error.invalidName", " (too long)"));
//		}
    }

    private StringBuffer generateValidationTryClause(SearchPageGeneratorInputBean in, String fName) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE)
                .append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(TAB)
                .append(generateValidationTest(fName, in)).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE).append(OPEN_BRACKET)
                .append("Exception e").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(TAB)
                .append(generateValidationException(fName, in));
        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE)
                .append(TAB).append(TAB).append(CLOSE_BRACE);
        return sb;
    }

    private StringBuffer generateValidationException(String name, SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();
        if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP) {
            sb.append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                    .append(COMMA).append(" new ActionMessage(").append(DOUBLE_QUOTE).append("error.date").append(DOUBLE_QUOTE).append(COMMA)
                    .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(DOUBLE_QUOTE).append(SPACE).append(PLUS)
                    .append(SPACE).append(name).append(SPACE).append(PLUS).append(SPACE).append(DOUBLE_QUOTE)
                    .append(" - the date cannot be parsed").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        } else if (in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.BIGINT ||
                in.dbFieldType == Types.INTEGER || in.dbFieldType == Types.DECIMAL) {
            sb.append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                    .append(COMMA).append(" new ActionMessage(").append(DOUBLE_QUOTE).append("error.invalidNumber").append(DOUBLE_QUOTE).append(COMMA)
                    .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(DOUBLE_QUOTE).append(SPACE).append(PLUS)
                    .append(SPACE).append(name).append(SPACE).append(PLUS).append(SPACE).append(DOUBLE_QUOTE)
                    .append(" - not a number").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateValidationTest(String name, SearchPageGeneratorInputBean in) {
        StringBuffer sb = new StringBuffer();
        if (in.dbFieldType == Types.VARCHAR) {
            sb.append(" TODO: FIXME!");
        } else if (in.dbFieldType == Types.CHAR) {
            sb.append(" TODO: FIXME!");
        } else if (in.dbFieldType == Types.DATE) {
            sb.append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_FORMAT)
                    .append(PERIOD).append(METHOD_NAME_PARSE).append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON);
        } else if (in.dbFieldType == Types.TIMESTAMP) {
            sb.append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_TIME_FORMAT)
                    .append(PERIOD).append(METHOD_NAME_PARSE).append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
            //	sb.append("AppConstants.TC_WEB_DATE_TIME_FORMAT.parse(birthDateFrom);");
        } else if (in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.INTEGER || in.dbFieldType == Types.DECIMAL) {
            sb.append(JAVA_KEYWORD_NEW).append(SPACE).append(in.javaTypeName).append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET).append(SEMICOLON);
        }
        return sb;
    }

    protected String generateClassFooter() {
        return CLOSE_BRACE;
    }

    private StringBuffer generateLoadFiltersMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        sb.append(NEW_LINE).append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_KEYWORD_VOID)
                .append(SPACE).append("loadFilters").append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE)
                .append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.filterable) continue;
            if (in.range) {
                String fName = null;
                for (int i = 0; i < 2; i++) {
                    if (i == 0) fName = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_FROM;
                    else fName = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_RANGE_SUFFIX_TO;
                    sb.append(generateEveryTypeOfFilterLoadLineForAField(in, fName));
                }
            } else {
                sb.append(generateEveryTypeOfFilterLoadLineForAField(in, getJavaFieldNameFromDBFieldName(in.dbFieldName)));
            }
        }
        sb.append(NEW_LINE).append("		filter.resetOrdering();").append(NEW_LINE).append(NEW_LINE);

        sb.append("		if (orderingOfOrderElements!=null && orderingOfOrderElements.length()>0) {").append(NEW_LINE);
        sb.append("			StringTokenizer st = new StringTokenizer(orderingOfOrderElements, \",\");").append(NEW_LINE);
        sb.append("			int i=0;").append(NEW_LINE);
        sb.append("			while (st.hasMoreTokens()) {").append(NEW_LINE);
        sb.append("				String o = st.nextToken();").append(NEW_LINE);

        boolean firstField = true;
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.sortable) continue;
            String nameOrder = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;
            String nameOrdering = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDERING;
            if (firstField) {
                sb.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF);
            } else {
                sb.append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE)
                        .append(SPACE).append(JAVA_KEYWORD_ELSE).append(SPACE).append(JAVA_KEYWORD_IF);
            }
            sb.append(SPACE).append(OPEN_BRACKET)
                    .append("o.equals").append(OPEN_BRACKET).append(DOUBLE_QUOTE).append(nameOrder).append(DOUBLE_QUOTE)
                    .append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
            sb.append(TAB).append(TAB).append(TAB).append(TAB).append(TAB).append("filter").append(PERIOD).append(nameOrdering)
                    .append(SPACE).append(EQUAL).append(SPACE).append("i").append(SEMICOLON);
            firstField = false;
        }
        if (!firstField) {
            sb.append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }
        sb.append("				i++;").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (!in.sortable) continue;
            String nameOrder = getJavaFieldNameFromDBFieldName(in.dbFieldName) + FILTER_BEAN_SUFFIX_ORDER;

            sb.append(TAB).append(TAB).append(TAB).append("filter").append(PERIOD).append(nameOrder).append(SPACE)
                    .append(EQUAL).append(SPACE).append("loadOrder").append(OPEN_BRACKET).append(nameOrder)
                    .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        }
        sb.append("		}").append(NEW_LINE);


        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateEveryTypeOfFilterLoadLineForAField(SearchPageGeneratorInputBean in, String name) {
        StringBuffer sb = new StringBuffer();
        if (in.javaTypeName.equalsIgnoreCase("String")) {
            sb.append(TAB).append(TAB)
                    .append(JAVA_KEYWORD_IF).append(SPACE).append(OPEN_BRACKET).append(name).append(LOGIC_NOTEQUAL).append(NULL)
                    .append(SPACE).append(LOGIC_AND).append(SPACE).append(name).append(PERIOD).append("length")
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(GREATER)
                    .append(ZERO).append(CLOSE_BRACKET).append(SPACE)
                    .append("filter").append(PERIOD).append(name).append(SPACE)
                    .append(EQUAL).append(SPACE).append(name).append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
            sb.append(TAB).append(TAB).append("filter").append(PERIOD).append(name).append(SPACE)
                    .append(EQUAL).append(SPACE)
                    .append("loadDate").append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("Long")) {
            sb.append(TAB).append(TAB).append("filter").append(PERIOD).append(name).append(SPACE)
                    .append(EQUAL).append(SPACE)
                    .append("loadLong").append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("Integer")) {
            sb.append(TAB).append(TAB).append("filter").append(PERIOD).append(name).append(SPACE)
                    .append(EQUAL).append(SPACE)
                    .append("loadInteger").append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
            sb.append(TAB).append(TAB).append("filter").append(PERIOD).append(name).append(SPACE)
                    .append(EQUAL).append(SPACE)
                    .append("loadBigDecimal").append(OPEN_BRACKET).append(name).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateMapListMethod(List inputBeans, String daoVOName, String formBeanVOName) {
        StringBuffer sb = new StringBuffer();

        sb.append("	public void mapListRecords(List listIn)").append(SPACE).append(JAVA_KEYWORD_THROWS).append(SPACE)
                .append(APP_EXCEPTION).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(FIELD_NAME_LIST).append(SPACE).append(EQUAL)
                .append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(JAVA_LIB_UTIL_ARRAYLIST)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append("		if (listIn==null) return;").append(NEW_LINE);
        sb.append("		Iterator iter = listIn.iterator();").append(NEW_LINE);
        sb.append("		while (iter.hasNext()) {").append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(daoVOName).append(SPACE)
                .append("voIn").append(SPACE).append(EQUAL).append(SPACE).append(OPEN_BRACKET)
                .append(daoVOName).append(CLOSE_BRACKET).append("iter.next()").append(SEMICOLON).append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append(formBeanVOName).append(SPACE)
                .append("voOut").append(SPACE).append(EQUAL).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE)
                .append(formBeanVOName).append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);


        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            String name = getJavaFieldNameFromDBFieldName(in.dbFieldName);
            String getName = getJavaGetNameFromDBFieldName(in.dbFieldName);
            String setName = getJavaSetNameFromDBFieldName(in.dbFieldName);

            if (in.javaTypeName.equalsIgnoreCase("String")) {
                sb.append(TAB).append(TAB).append(TAB).append("voOut").append(PERIOD).append(setName)
                        .append(OPEN_BRACKET).append("voIn").append(PERIOD).append(getName)
                        .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
            } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
                sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                        .append(OPEN_BRACKET).append("voIn").append(PERIOD).append(getName)
                        .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(LOGIC_NOTEQUAL).append(NULL)
                        .append(CLOSE_BRACKET).append(OPEN_BRACE).append(NEW_LINE);
                sb.append(TAB).append(TAB).append(TAB).append(TAB).append("voOut").append(PERIOD).append(setName)
                        .append(OPEN_BRACKET)
                        .append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_FORMAT).append(PERIOD)
                        .append("format").append(OPEN_BRACKET)
                        .append("voIn").append(PERIOD).append(getName)
                        .append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(CLOSE_BRACKET)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
                sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
            } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                    in.javaTypeName.equalsIgnoreCase("Integer") ||
                    in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
                sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                        .append(OPEN_BRACKET).append("voIn").append(PERIOD).append(getName)
                        .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(LOGIC_NOTEQUAL).append(NULL)
                        .append(CLOSE_BRACKET).append(OPEN_BRACE).append(NEW_LINE);
                sb.append(TAB).append(TAB).append(TAB).append(TAB).append("voOut").append(PERIOD).append(setName)
                        .append(OPEN_BRACKET).append("voIn").append(PERIOD).append(getName)
                        .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(PERIOD)
                        .append("toString").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
                sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
            }

        }
        sb.append("			").append(FIELD_NAME_LIST).append(PERIOD).append("add")
                .append(OPEN_BRACKET).append("voOut").append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }
}