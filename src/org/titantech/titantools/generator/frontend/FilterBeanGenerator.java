package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;

public class FilterBeanGenerator extends GeneratorBase {

    public String generateFilterClass(String filterVOName, List inputBeans, List dbColumns) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(filterVOName));
        contents.append(generateFields(inputBeans));
        contents.append(generateOrderFields(inputBeans));
        contents.append(generateOrderingFields(inputBeans));
        contents.append(generateResetOrderingMethod(inputBeans));
        contents.append(generateIsOrderingRequiredMethod(inputBeans));
        contents.append(generateToStringMethod(inputBeans));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }


    private StringBuffer generateClassFileHeader(String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                FILTER_BEAN_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = FILTER_BEAN_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }

        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(filterVOName)
                .append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);
        return header;
    }

    private StringBuffer generateFields(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(in.javaTypeName)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(in.javaTypeName)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            } else {
                sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(in.javaTypeName)
                        .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                        .append(NEW_LINE);
            }
        }
        return sb;
    }

    private StringBuffer generateOrderFields(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                    .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(FILTER_BEAN_SUFFIX_ORDER)
                    .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateOrderingFields(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_KEYWORD_INT)
                    .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(FILTER_BEAN_SUFFIX_ORDERING)
                    .append(SPACE).append(EQUAL).append(SPACE).append(MINUS).append(1).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateResetOrderingMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE)
                .append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_VOID).append(SPACE).append(FILTER_BEAN_METHOD_RESET_ORDERING)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(TAB)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(FILTER_BEAN_SUFFIX_ORDER)
                    .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        sb.append(NEW_LINE);
        iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(TAB)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(FILTER_BEAN_SUFFIX_ORDERING)
                    .append(SPACE).append(EQUAL).append(SPACE).append(MINUS).append(1).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        sb.append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    protected StringBuffer generateIsOrderingRequiredMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE)
                .append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_KEYWORD_BOOLEAN).append(SPACE).append(FILTER_BEAN_METHOD_IS_ORDERING_REQUIRED)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE)
                .append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE)
                .append(OPEN_BRACKET).append(NEW_LINE);

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(TAB).append(TAB)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(FILTER_BEAN_SUFFIX_ORDER)
                    .append(SPACE).append(LOGIC_NOTEQUAL).append(SPACE).append(NULL);
            if (iter.hasNext()) {
                sb.append(LOGIC_OR);
            }
            sb.append(NEW_LINE);
        }
        sb.append(TAB).append(TAB).append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE)
                .append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    protected StringBuffer generateToStringMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE)
                .append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(JAVA_LIB_STRING).append(SPACE).append(METHOD_NAME_TOSTRING)
                .append(OPEN_BRACKET).append(CLOSE_BRACKET)
                .append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

        sb.append(TAB).append(TAB).append("StringBuffer sb = new StringBuffer();").append(NEW_LINE);

        //	if (purchaseDateFrom!=null) {
        //		sb.append("  purchaseDateFrom: ");
        //		sb.append(purchaseDateFrom);
        //	}

        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            if (in.range) {
                sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                        .append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(LOGIC_NOTEQUAL)
                        .append(NULL).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE)
                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(DOUBLE_QUOTE).append(SPACE).append(SPACE)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(COLON).append(SPACE).append(DOUBLE_QUOTE).append(CLOSE_BRACKET)
                        .append(SEMICOLON).append(NEW_LINE)
                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_FROM)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE)
                        .append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);


                sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                        .append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(LOGIC_NOTEQUAL)
                        .append(NULL).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE)
                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(DOUBLE_QUOTE).append(SPACE).append(SPACE)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(COLON).append(SPACE).append(DOUBLE_QUOTE).append(CLOSE_BRACKET)
                        .append(SEMICOLON).append(NEW_LINE)
                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(FILTER_BEAN_RANGE_SUFFIX_TO)
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE)
                        .append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

            } else {
                sb.append(TAB).append(TAB).append(JAVA_KEYWORD_IF).append(SPACE)
                        .append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(LOGIC_NOTEQUAL)
                        .append(NULL).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE)
                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(DOUBLE_QUOTE).append(SPACE).append(SPACE)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(COLON).append(SPACE).append(DOUBLE_QUOTE).append(CLOSE_BRACKET)
                        .append(SEMICOLON).append(NEW_LINE)

                        .append(TAB).append(TAB).append(TAB).append("sb.append").append(OPEN_BRACKET)
                        .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                        .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE)
                        .append(TAB).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
            }
        }


        sb.append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_RETURN).append(SPACE)
                .append("sb.toString();");

        sb.append(NEW_LINE)
                .append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        return sb;
    }

    protected String generateClassFooter() {
        return CLOSE_BRACE;
    }
}