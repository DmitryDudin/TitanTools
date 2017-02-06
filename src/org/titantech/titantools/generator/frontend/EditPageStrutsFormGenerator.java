package org.titantech.titantools.generator.frontend;

import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;

public class EditPageStrutsFormGenerator extends GeneratorBase {

    public String generateStrutsFormClass(String strutsFormName, String daoVOName, List inputBeans) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(strutsFormName, daoVOName));
        contents.append(generateFields(inputBeans, strutsFormName));
        contents.append(generateSimpleResetMethod(inputBeans));
        contents.append(generateStrutsValidateMethod(inputBeans));
        contents.append(generateMapToFormMethod(inputBeans, daoVOName));
        contents.append(generateMapFromFormMethod(inputBeans, daoVOName));
        contents.append(generateStrutsFormConstantMethods());
        contents.append(generateVOGettersSetters(inputBeans));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateClassFileHeader(String strutsFormName, String daoVOName) {
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
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(VO_JAVA_PACKAGE_NAME).append(PERIOD).append(daoVOName).append(SEMICOLON).append(NEW_LINE);
        //	header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(WEB_BEAN_JAVA_PACKAGE_NAME).append(PERIOD).append(formBeanVOName).append(SEMICOLON).append(NEW_LINE);
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

    private StringBuffer generateFields(List inputBeans, String strutsFormName) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(JAVA_LIB_CLASS).append(SPACE)
                .append(FIELD_NAME_CLAZZ).append(SPACE).append(EQUAL).append(SPACE)
                .append(strutsFormName).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE).append(JAVA_LIB_STRING)
                .append(SPACE).append(FIELD_NAME_ACTION_NAME).append(SPACE).append(EQUAL).append(SPACE).append(NULL)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE).append(JAVA_LIB_STRING)
                .append(SPACE).append(FIELD_NAME_ACTION_TOKEN).append(SPACE).append(EQUAL).append(SPACE).append(NULL)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);


        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                    .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateVOGettersSetters(List inputBeans) {

        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);

        List otherFields = new ArrayList();
        otherFields.add(FIELD_NAME_ACTION_NAME);
        otherFields.add(FIELD_NAME_ACTION_TOKEN);

        Map typeMap = new HashMap();
        typeMap.put(FIELD_NAME_ACTION_NAME, JAVA_LIB_STRING);
        typeMap.put(FIELD_NAME_ACTION_TOKEN, JAVA_LIB_STRING);

        Iterator iter = otherFields.iterator();
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
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(typeName).append(SPACE)
                    .append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB)
                    .append(JAVA_KEYWORD_RETURN).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                    .append(getJavaSetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET)
                    .append(typeName).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_THIS)
                    .append(PERIOD).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(EQUAL)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
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

        return sb.toString();
    }

    protected String generateSimpleResetMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE).append("reset")
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                    .append(NEW_LINE);

//			if (in.range) {
//				sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
//				.append(FILTER_BEAN_RANGE_SUFFIX_FROM)
//				.append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
//				.append(NEW_LINE);
//				sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
//				.append(FILTER_BEAN_RANGE_SUFFIX_TO)
//				.append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
//				.append(NEW_LINE);
//			} else {
//				sb.append(TAB).append(TAB).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
//				.append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
//				.append(NEW_LINE);
//			}
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

        sb.append("		String an = request.getParameter(ActionConstants.ACTION_NAME);").append(NEW_LINE);
        sb.append("		if (ActionConstants.ACTION_OPEN.equals(an)) {").append(NEW_LINE);
        sb.append("			return errors;").append(NEW_LINE);
        sb.append("		} else if(ActionConstants.ACTION_SAVE_AND_EXIT.equals(an) ||").append(NEW_LINE);
        sb.append("				ActionConstants.ACTION_SAVE_AND_ADD_ANOTHER_NEW.equals(an)) {").append(NEW_LINE);
        sb.append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(generateEveryTypeOfValidationForAField(in, getJavaFieldNameFromDBFieldName(in.dbFieldName)));
        }

        sb.append(NEW_LINE);
        sb.append("		}").append(NEW_LINE);
        sb.append("		return errors;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateEveryTypeOfValidationForAField(SearchPageGeneratorInputBean in, String fName) {
        StringBuffer sb = new StringBuffer();
        if (in.mandatory) {
            sb.append(generateValidationMandatoryCheck(in, fName));
        }
        sb.append(generateValidationInputLengthCheck(in, fName));


        if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP ||
                in.dbFieldType == Types.NUMERIC || in.dbFieldType == Types.INTEGER ||
                in.dbFieldType == Types.DECIMAL) {
            sb.append(NEW_LINE).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
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
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
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
        sb.append(TAB).append(TAB).append(TAB).append(TAB)
                .append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                .append(COMMA).append(" new ActionMessage(").append(DOUBLE_QUOTE).append(errorType).append(DOUBLE_QUOTE).append(COMMA)
                .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(name)
                .append(" - mandatory parameter").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateValidationInputLengthCheck(SearchPageGeneratorInputBean in, String name) {
        StringBuffer sb = new StringBuffer();
        if (in.dbFieldType == Types.DATE || in.dbFieldType == Types.TIMESTAMP || in.dbFieldLength == -1) {
            return sb;
        }

        sb.append(NEW_LINE);
        sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(OPEN_BRACKET)
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
        sb.append(TAB).append(TAB).append(TAB).append(TAB)
                .append("errors.add(").append(DOUBLE_QUOTE).append(name).append(DOUBLE_QUOTE)
                .append(COMMA).append(" new ActionMessage(").append(DOUBLE_QUOTE).append(errorType).append(DOUBLE_QUOTE).append(COMMA)
                .append(SPACE).append(DOUBLE_QUOTE).append(SPACE).append(OPEN_BRACKET).append(name)
                .append(" - too long").append(CLOSE_BRACKET).append(DOUBLE_QUOTE).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
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
        sb.append(TAB).append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE)
                .append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE);
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

    private StringBuffer generateMapToFormMethod(List inputBeans, String daoVOName) {
        StringBuffer sb = new StringBuffer();
        //	public void mapToForm(MasterLabel vo) {
        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_KEYWORD_VOID)
                .append(SPACE).append("mapToForm").append(OPEN_BRACKET).append(daoVOName).append(SPACE)
                .append("vo").append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
        //		if (vo==null) return;
        sb.append(TAB).append(TAB).append("if (vo==null) return;").append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(generateEveryTypeOfSetMethodForAField(in, getJavaFieldNameFromDBFieldName(in.dbFieldName)));
        }

        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE).append(NEW_LINE);
        return sb;
    }

    private StringBuffer generateEveryTypeOfSetMethodForAField(SearchPageGeneratorInputBean in, String fName) {
        StringBuffer sb = new StringBuffer();
        if (in.javaTypeName.equalsIgnoreCase("String")) {
            sb.append(TAB).append(TAB)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append(EQUAL)
                    .append(SPACE).append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(PERIOD).append(METHOD_NAME_TOSTRING)
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
//		if (vo.getUpdateDate()!=null) updateDate = AppConstants.TC_WEB_DATE_FORMAT.format(vo.getUpdateDate());
            sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                    .append(OPEN_BRACKET).append("vo").append(PERIOD)
                    .append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(LOGIC_NOTEQUAL).append(NULL)
                    .append(CLOSE_BRACKET).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append(EQUAL).append(SPACE)
                    .append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_FORMAT).append(PERIOD)
                    .append(METHOD_NAME_FORMAT).append(OPEN_BRACKET)
                    .append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                in.javaTypeName.equalsIgnoreCase("Integer") ||
                in.javaTypeName.equalsIgnoreCase("BigDecimal")) {

            sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                    .append(OPEN_BRACKET).append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(LOGIC_NOTEQUAL).append(NULL)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);


            sb.append(TAB).append(TAB).append(TAB)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append(EQUAL)
                    .append(SPACE).append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(PERIOD).append(METHOD_NAME_TOSTRING)
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

            sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateMapFromFormMethod(List inputBeans, String daoVOName) {
        StringBuffer sb = new StringBuffer();
        //	public MasterLabel getMasterLabel() throws TCAppException {
        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(daoVOName)
                .append(SPACE).append(GET_PREFIX).append(daoVOName).append(OPEN_BRACKET)
                .append(CLOSE_BRACKET).append(SPACE)
                .append(JAVA_KEYWORD_THROWS).append(SPACE).append(APP_EXCEPTION).append(SPACE)
                .append(OPEN_BRACE).append(NEW_LINE);

        //MasterLabel vo = new MasterLabel();
        sb.append(TAB).append(TAB).append(daoVOName).append(SPACE).append("vo").append(SPACE)
                .append(EQUAL).append(SPACE).append(JAVA_KEYWORD_NEW).append(SPACE).append(daoVOName)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(generateEveryTypeOfSetterForDAOVO(in, getJavaFieldNameFromDBFieldName(in.dbFieldName)));
        }

        //return vo;
        sb.append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE).append("vo").append(SEMICOLON)
                .append(NEW_LINE);
        // }
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE).append(NEW_LINE);
        return sb;
    }

    /*



    */
    private StringBuffer generateEveryTypeOfSetterForDAOVO(SearchPageGeneratorInputBean in, String javaFieldNameFromDBFieldName) {
        StringBuffer sb = new StringBuffer();
        if (in.javaTypeName.equalsIgnoreCase("String")) {
            sb.append(TAB).append(TAB)
                    .append("vo").append(PERIOD).append(getJavaSetNameFromDBFieldName(in.dbFieldName))
                    .append(OPEN_BRACKET)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(CLOSE_BRACKET)
                    .append(SEMICOLON).append(NEW_LINE);

        } else if (in.javaTypeName.equalsIgnoreCase("Date")) {
            //			if (labelId!=null && labelId.trim().length()>0) {

            sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                    .append(OPEN_BRACKET).append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(LOGIC_NOTEQUAL).append(NULL)
                    .append(SPACE).append(LOGIC_AND).append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(PERIOD).append("trim").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(PERIOD).append("length").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(GREATER).append(ZERO)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

//			try {
            sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
//				vo.setUpdateDate(AppConstants.TC_WEB_DATE_FORMAT.parse(updateDate.trim()));
            sb.append(TAB).append(TAB).append(TAB).append(TAB)
                    .append("vo").append(PERIOD).append(getJavaSetNameFromDBFieldName(in.dbFieldName)).append(OPEN_BRACKET)
                    .append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_FORMAT).append(PERIOD).append(METHOD_NAME_PARSE)
                    .append(OPEN_BRACKET).append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(PERIOD).append("trim")
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
//			} catch (ParseException e) {
            sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE)
                    .append(OPEN_BRACKET).append("ParseException").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE)
                    .append(OPEN_BRACE).append(NEW_LINE);
//				throw new TCAppException("updateDate cannot be parsed: [" + updateDate + "]");
            sb.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW)
                    .append(SPACE).append(APP_EXCEPTION).append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append("cannot be parsed: [\" + ")
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(" + \"]\");").append(NEW_LINE);
//			}
            sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);


            sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

//			sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
//				.append(OPEN_BRACKET).append("vo").append(PERIOD)
//				.append(getJavaGetNameFromDBFieldName(in.dbFieldName))
//				.append(OPEN_BRACKET).append(CLOSE_BRACKET).append(LOGIC_NOTEQUAL).append(NULL)
//				.append(CLOSE_BRACKET).append(SPACE)
//				.append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append(EQUAL).append(SPACE)
//				.append(APP_CONSTANTS).append(PERIOD).append(FIELD_NAME_TC_WEB_DATE_FORMAT).append(PERIOD)
//				.append(METHOD_NAME_FORMAT).append(OPEN_BRACKET)
//				.append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
//				.append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET)
//				.append(SEMICOLON).append(NEW_LINE);
        } else if (in.javaTypeName.equalsIgnoreCase("Long") ||
                in.javaTypeName.equalsIgnoreCase("Integer") ||
                in.javaTypeName.equalsIgnoreCase("BigDecimal")) {
            //		if (labelId!=null && labelId.trim().length()>0) {
            sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                    .append(OPEN_BRACKET).append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(LOGIC_NOTEQUAL).append(NULL)
                    .append(SPACE).append(LOGIC_AND).append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(PERIOD).append("trim").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(PERIOD).append("length").append(OPEN_BRACKET).append(CLOSE_BRACKET)
                    .append(GREATER).append(ZERO)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

//			try {
//				vo.setLabelId(new Long(labelId.trim()));
//			} catch (Exception e) {
//				throw new TCAppException("labelId cannot be parsed: [" + labelId + "]");
//			}

//			try {
            sb.append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_TRY).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);
            //	vo.setLabelId(new Long(labelId.trim()));
            //	vo.setUpdateDate(AppConstants.TC_WEB_DATE_FORMAT.parse(updateDate.trim()));
            sb.append(TAB).append(TAB).append(TAB).append(TAB)
                    .append("vo").append(PERIOD).append(getJavaSetNameFromDBFieldName(in.dbFieldName)).append(OPEN_BRACKET)
                    .append(JAVA_KEYWORD_NEW).append(SPACE).append("Long").append(OPEN_BRACKET)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(PERIOD).append("trim")
                    .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
//			} catch (Exception e) {
            sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(SPACE).append(JAVA_KEYWORD_CATCH).append(SPACE)
                    .append(OPEN_BRACKET).append("Exception").append(SPACE).append("e").append(CLOSE_BRACKET).append(SPACE)
                    .append(OPEN_BRACE).append(NEW_LINE);
//				throw new TCAppException("updateDate cannot be parsed: [" + updateDate + "]");
            sb.append(TAB).append(TAB).append(TAB).append(TAB).append(JAVA_KEYWORD_THROW).append(SPACE).append(JAVA_KEYWORD_NEW)
                    .append(SPACE).append(APP_EXCEPTION).append(OPEN_BRACKET).append(DOUBLE_QUOTE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append("cannot be parsed: [\" + ")
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(" + \"]\");").append(NEW_LINE);
//			}
            sb.append(TAB).append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);


            //	sb.append(TAB).append(TAB).append(TAB)
            //		.append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SPACE).append(EQUAL)
            //		.append(SPACE).append("vo").append(PERIOD).append(getJavaGetNameFromDBFieldName(in.dbFieldName))
            //		.append(OPEN_BRACKET).append(CLOSE_BRACKET).append(PERIOD).append(METHOD_NAME_TOSTRING)
            //		.append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

            sb.append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }
        return sb;
    }

}