package org.titantech.titantools.generator;

import java.util.Iterator;
import java.util.List;
import java.util.StringTokenizer;

import org.titantech.titantools.dao.bean.VODetails;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;


public class VOGenerator extends GeneratorBase {

    public String generateVO4DAO(List columns) {
        StringBuffer voContents = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            if (DB_PK_COLUMN_SET.contains(detail.dbColumnName)) {
                detail.isPK = true;
            }
            generateJavaBeanFieldsGettersSetters(detail);
            generateJavaBeanFieldTypeSQLType(detail);
        }
        voContents.append(generateJavaClassFileHeader(VO_JAVA_CLASS_NAME,
                VO_JAVA_PACKAGE_NAME, VO_CLASS_IMPORTS,
                VO_CLASS_EXTENDS, VO_CLASS_IMPLEMENTS));
        voContents.append(generateVOFileFields(columns, true));
        voContents.append(generateVOConstructor(columns));
        voContents.append(generateVOGettersSetters(columns, true));
        voContents.append(generateClassOrInterfaceFooter());
        String voStr = voContents.toString();
        return voStr;
    }

    public String generateVO4JIBX(String className, String packageName,
                                  List columns, List voClassImports,
                                  List voClassExtends, List voClassImplements) {
        StringBuffer voContents = new StringBuffer();
        voContents.append(generateJavaClassFileHeader(className, packageName, voClassImports,
                voClassExtends, voClassImplements));
        voContents.append(generateVOFileFields(columns, false));
        voContents.append(generateVOGettersSetters(columns, false));
        voContents.append(generateClassOrInterfaceFooter());
        String voStr = voContents.toString();
        return voStr;
    }

    private void generateJavaBeanFieldsGettersSetters(
            VOFieldToColumnMappingDetails detail) {
        StringBuffer javaFieldName = new StringBuffer();
        StringTokenizer st = new StringTokenizer(detail.dbColumnName
                .toLowerCase(), DB_FIELD_NAME_DELIMITER);
        while (st.hasMoreTokens()) {
            String str1 = st.nextToken();
            javaFieldName.append(str1.substring(0, 1).toUpperCase());
            javaFieldName.append(str1.substring(1));
        }
        detail.populateFormattedFieldSetterGetter(javaFieldName.toString());
    }

    private StringBuffer generateVOGettersSetters(List columns, boolean isVOForDAO) {
        StringBuffer gettersSetters = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VODetails detail = (VODetails) iter.next();
            String typeName = getTypeName(detail);
            if (isVOForDAO) typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
            gettersSetters.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(typeName).append(SPACE)
                    .append(detail.javaGetterMethod).append(OPEN_BRACKET)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB)
                    .append(JAVA_KEYWORD_RETURN).append(SPACE)
                    .append(detail.javaFieldName).append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);

            gettersSetters.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                    .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE)
                    .append(detail.javaSetterMethod).append(OPEN_BRACKET)
                    .append(typeName).append(SPACE).append(detail.javaFieldName)
                    .append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE)
                    .append(NEW_LINE).append(TAB).append(TAB).append(JAVA_KEYWORD_THIS)
                    .append(PERIOD).append(detail.javaFieldName).append(EQUAL)
                    .append(detail.javaFieldName).append(SEMICOLON)
                    .append(NEW_LINE).append(TAB).append(CLOSE_BRACE).append(NEW_LINE);
        }
        return gettersSetters;
    }

    private StringBuffer generateVOFileFields(List columns, boolean isVOForDAO) {
        StringBuffer fields = new StringBuffer();
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VODetails detail = (VODetails) iter.next();
            String typeName = getTypeName(detail);
            if (isVOForDAO) typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
            fields.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                    .append(typeName).append(SPACE).append(detail.javaFieldName)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        fields.append(NEW_LINE);
        return fields;
    }

    private StringBuffer generateVOConstructor(List columns) {
        StringBuffer constructor = new StringBuffer();
        constructor.append(TAB).append(JAVA_KEYWORD_PUBLIC).append(SPACE)
                .append(VO_JAVA_CLASS_NAME).append(OPEN_BRACKET).append(
                CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(
                NEW_LINE);
        Iterator iter = columns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter
                    .next();
            constructor.append(TAB).append(TAB).append(
                    VO_FIELD_MAPPING_METHOD_NAME).append(OPEN_BRACKET).append(
                    DOUBLE_QUOTE).append(detail.dbColumnName).append(
                    DOUBLE_QUOTE).append(COMMA).append(SPACE).append(
                    detail.dbColumnTypeName).append(COMMA).append(SPACE)
                    .append(DOUBLE_QUOTE).append(detail.javaSetterMethod)
                    .append(DOUBLE_QUOTE).append(COMMA).append(SPACE).append(
                    detail.javaFieldTypeName).append(COMMA).append(
                    SPACE).append(DOUBLE_QUOTE).append(
                    detail.javaGetterMethod).append(DOUBLE_QUOTE)
                    .append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);
        }
        constructor.append(TAB).append(CLOSE_BRACE).append(DBL_NEW_LINE);
        return constructor;
    }
}