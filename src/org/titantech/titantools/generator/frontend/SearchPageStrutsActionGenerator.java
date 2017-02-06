package org.titantech.titantools.generator.frontend;

import java.util.Iterator;
import java.util.List;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.generator.GeneratorBase;

public class SearchPageStrutsActionGenerator extends GeneratorBase {

    public String generateStrutsFormClass(String actionName, String strutsFormName,
                                          String delegateName, String daoVOName, String searchRecordsForwardActionName,
                                          String editRecordForwardActionName, String resourcePermissionName) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(actionName, strutsFormName, delegateName, daoVOName));
        contents.append(generateFields(actionName));
        contents.append(generateExecuteMethod(actionName, strutsFormName, delegateName, daoVOName,
                searchRecordsForwardActionName, editRecordForwardActionName, resourcePermissionName));
        contents.append(generateClassFooter());
        String str = contents.toString();
        System.out.println(str);
        return str;
    }


    private StringBuffer generateClassFileHeader(String actionName,
                                                 String strutsFormName, String delegateName, String daoVOName) {
        StringBuffer header = new StringBuffer();
        header.append(JAVA_KEYWORD_PACKAGE).append(SPACE).append(
                STRUTS_ACTION_JAVA_PACKAGE_NAME).append(SEMICOLON).append(DBL_NEW_LINE);
        Iterator iter = STRUTS_ACTION_CLASS_IMPORTS.iterator();
        while (iter.hasNext()) {
            String imprt = (String) iter.next();
            header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(imprt)
                    .append(SEMICOLON).append(NEW_LINE);
        }
        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_CONSTANTS).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(PACKAGE_NAME_PREFIX).append(PERIOD).append(APP_EXCEPTION).append(SEMICOLON).append(NEW_LINE);

        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(DELEGATE_JAVA_PACKAGE_NAME).append(PERIOD).append(delegateName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(STRUTS_FORM_JAVA_PACKAGE_NAME).append(PERIOD).append(strutsFormName).append(SEMICOLON).append(NEW_LINE);
        header.append(JAVA_KEYWORD_IMPORT).append(SPACE).append(VO_JAVA_PACKAGE_NAME).append(PERIOD).append(daoVOName).append(SEMICOLON).append(NEW_LINE);

        header.append(NEW_LINE);
        header.append(JAVA_KEYWORD_PUBLIC).append(SPACE).append(
                JAVA_KEYWORD_CLASS).append(SPACE).append(actionName)
                .append(SPACE);

        if (!STRUTS_ACTION_CLASS_EXTENDS.isEmpty()) {
            header.append(JAVA_KEYWORD_EXTENDS).append(SPACE);
            iter = STRUTS_ACTION_CLASS_EXTENDS.iterator();
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

    private StringBuffer generateFields(String actionName) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(JAVA_LIB_CLASS).append(SPACE)
                .append(FIELD_NAME_CLAZZ).append(SPACE).append(EQUAL).append(SPACE)
                .append(actionName).append(PERIOD).append(JAVA_KEYWORD_CLASS)
                .append(SEMICOLON).append(NEW_LINE);

        sb.append(TAB).append(JAVA_KEYWORD_PRIVATE).append(SPACE)
                .append(JAVA_KEYWORD_STATIC).append(SPACE).append(APACHE_CLASS_LOGGER).append(SPACE)
                .append(FIELD_NAME_LOGGER).append(SPACE).append(EQUAL).append(SPACE)
                .append(APACHE_CLASS_LOGGER).append(PERIOD).append(METHOD_NAME_GETLOGGER)
                .append(OPEN_BRACKET).append(FIELD_NAME_CLAZZ).append(CLOSE_BRACKET)
                .append(SEMICOLON).append(NEW_LINE).append(NEW_LINE);

        return sb;
    }

    protected String generateSimpleResetMethod(List inputBeans) {
        StringBuffer sb = new StringBuffer();

        sb.append(TAB).append(JAVA_KEYWORD_PUBLIC)
                .append(SPACE).append(JAVA_KEYWORD_VOID).append(SPACE).append("reset")
                .append(OPEN_BRACKET).append(CLOSE_BRACKET).append(SPACE).append(OPEN_BRACE).append(NEW_LINE);

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

    protected String generateClassFooter() {
        return CLOSE_BRACE;
    }

    protected StringBuffer generateExecuteMethod(String actionName, String formClassName,
                                                 String delegateName, String daoVOName, String searchRecordsForwardActionName,
                                                 String editRecordForwardActionName, String resourcePermissionName) {
        StringBuffer sb = new StringBuffer();
        sb.append("	public ActionForward execute(ActionMapping actionMapping,").append(NEW_LINE);
        sb.append("			ActionForm actionForm, HttpServletRequest servletRequest,").append(NEW_LINE);
        sb.append("			HttpServletResponse servletResponse) {").append(NEW_LINE);

        sb.append("		// Security Check Starts").append(NEW_LINE);
        sb.append("		HttpSession session = servletRequest.getSession(false);").append(NEW_LINE);
        sb.append("		PermissionValidator pv = (PermissionValidator) session").append(NEW_LINE);
        sb.append("				.getAttribute(PermissionValidator.PERMISSION_VALIDATOR_CONSTANT);").append(NEW_LINE);
        sb.append("		if (!(pv!=null && pv.canRead(PermissionValidator.");
        sb.append(resourcePermissionName);
        sb.append("))) {").append(NEW_LINE);
        sb.append("			return actionMapping.findForward(\"accessDenied\");").append(NEW_LINE);
        sb.append("		} // Security Check Ends").append(NEW_LINE);
        sb.append("		String clientIp = servletRequest.getRemoteAddr();").append(NEW_LINE);
        sb.append("		String remoteUser = pv.getUser().getLoginName();").append(NEW_LINE);
        sb.append("		Long userId = pv.getUser().getUserId();").append(NEW_LINE);
        sb.append("		");
        sb.append(formClassName);
        sb.append(" form = (");
        sb.append(formClassName);
        sb.append(") actionForm;").append(NEW_LINE);
        sb.append("		String an = servletRequest.getParameter(ActionConstants.ACTION_NAME);").append(NEW_LINE);
        sb.append("		ActionErrors actionErrors = form.validate(actionMapping, servletRequest);").append(NEW_LINE);
        sb.append("		if(!actionErrors.isEmpty()) {").append(NEW_LINE);
        sb.append("			this.saveErrors(servletRequest, actionErrors);").append(NEW_LINE);
        sb.append("			form.setActionName(an);").append(NEW_LINE);
        sb.append("			form.reset();").append(NEW_LINE);
        sb.append("			return actionMapping.findForward(\"business-error\");").append(NEW_LINE);
        sb.append("		} else {").append(NEW_LINE);

        sb.append("			try {").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_OPEN.equals(an)) {").append(NEW_LINE);
        sb.append("					// do almost nothing.").append(NEW_LINE);
        sb.append("					form.loadFilters();").append(NEW_LINE);
        sb.append("					form.setActionName(ActionConstants.ACTION_RETRIEVE);").append(NEW_LINE);
        sb.append("				//	form.setDefaultFilters();").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);

        sb.append("				if (ActionConstants.ACTION_RETRIEVE.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("					");
        sb.append(delegateName);
        sb.append(" delegate = new ");
        sb.append(delegateName);
        sb.append("();").append(NEW_LINE);
        sb.append("					form.loadFilters();").append(NEW_LINE);
        sb.append("					try {").append(NEW_LINE);
        sb.append("						List list = delegate.get");
        sb.append(daoVOName);
        sb.append("s(form.getFilter(), clientIp, remoteUser, userId);").append(NEW_LINE);
        sb.append("						form.mapListRecords(list);").append(NEW_LINE);
        sb.append("						logger.info(\"User [\" + remoteUser + \" at \" + clientIp + \"] retrieves VOs: [\" + form.getFilter().toString() + \"]\");").append(NEW_LINE);
        sb.append("					} catch (NullPointerException e) {").append(NEW_LINE);
        sb.append("						logger.error(\"execute delegate: error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("						return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("					}").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_NEW.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("					// new page will be displayed with no values.").append(NEW_LINE);
        sb.append("					return actionMapping.findForward(\"");
        sb.append(editRecordForwardActionName);
        sb.append("\");").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("			} catch(TCAppException e) {").append(NEW_LINE);
        sb.append("				logger.error(\"");
        sb.append(actionName);
        sb.append(".execute error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("				ActionErrors errors = new ActionErrors();").append(NEW_LINE);
        sb.append("				errors.add(\"");
        sb.append(searchRecordsForwardActionName);
        sb.append("\", new ActionMessage(\"error.");
        sb.append(searchRecordsForwardActionName);
        sb.append("\"));").append(NEW_LINE);
        sb.append("				this.saveErrors(servletRequest, errors);").append(NEW_LINE);
        sb.append("				return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);

        sb.append("		}").append(NEW_LINE);
        sb.append("		return actionMapping.findForward(\"success\");").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }
}