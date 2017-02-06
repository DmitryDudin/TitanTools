package org.titantech.titantools.generator;

import java.util.Iterator;

public class DelegateGenerator extends GeneratorBase {

    public String generateDelegateClass() {
        StringBuffer contents = new StringBuffer();
        contents.append(generateVOFileHeader());
        contents.append(generateClassOrInterfaceFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }

    private StringBuffer generateVOFileHeader() {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                VO_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = VO_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(VO_JAVA_CLASS_NAME)
                .append(SPACE);

        if (!VO_CLASS_EXTENDS.isEmpty()) {
            header.append(JAVA_KEYWORD_EXTENDS).append(SPACE);
            iter = VO_CLASS_EXTENDS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        }
        if (!VO_CLASS_IMPLEMENTS.isEmpty()) {
            header.append(SPACE).append(JAVA_KEYWORD_IMPLEMENTS).append(SPACE);
            iter = VO_CLASS_IMPLEMENTS.iterator();
            while (iter.hasNext()) {
                header.append((String) iter.next());
                if (iter.hasNext()) {
                    header.append(COMMA).append(SPACE);
                }
            }
        }
        header.append(SPACE).append(OPEN_BRACE).append(DBL_NEW_LINE);
        return header;
    }

}