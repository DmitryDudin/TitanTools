package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;

public class SearchPageFormBeanVOGenerator extends GeneratorBase {

    public String generateFormBeanVOClass(String formBeanVOName, List inputBeans, List dbColumns) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(formBeanVOName));
        contents.append(generateFields(inputBeans));
        contents.append(generateVOGettersSetters(inputBeans));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateClassFileHeader(String filterVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                WEB_BEAN_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = WEB_BEAN_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }

        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(filterVOName)
                .append(SPACE);

        if (!WEB_BEAN_CLASS_IMPLEMENTS.isEmpty()) {
            header.append(JAVA_KEYWORD_IMPLEMENTS).append(SPACE);
            iter = WEB_BEAN_CLASS_IMPLEMENTS.iterator();
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

    private StringBuffer generateFields(List inputBeans) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        Iterator iter = inputBeans.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(JAVA_LIB_STRING)
                    .append(SPACE).append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(SPACE).append(EQUAL).append(SPACE).append(NULL).append(SEMICOLON)
                    .append(NEW_LINE);
        }
        return sb;
    }

    private StringBuffer generateVOGettersSetters(List columns) {
        StringBuffer sb = new StringBuffer();
        sb.append(NEW_LINE);
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            SearchPageGeneratorInputBean in = (SearchPageGeneratorInputBean) iter.next();
            String typeName = JAVA_LIB_STRING;
            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(typeName).append(SPACE)
                    .append(getJavaGetNameFromDBFieldName(in.dbFieldName)).append(OPEN_BRACKET)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB)
                    .append(JAVA_KEYWORD_RETURN).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

            sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                    .append(getJavaSetNameFromDBFieldName(in.dbFieldName)).append(OPEN_BRACKET)
                    .append(typeName).append(SPACE)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName))
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_THIS)
                    .append(PERIOD).append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(EQUAL)
                    .append(getJavaFieldNameFromDBFieldName(in.dbFieldName)).append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }
        return sb;
    }

    protected String generateClassFooter() {
        return CLOSE_BRACE;
    }
}