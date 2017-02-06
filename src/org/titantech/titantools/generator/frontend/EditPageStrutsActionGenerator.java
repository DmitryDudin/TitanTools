package org.titantech.titantools.generator.frontend;

import java.sql.Types;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.xml.soap.Detail;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;
import org.titantech.titantools.generator.GeneratorBase;

public class EditPageStrutsActionGenerator extends GeneratorBase {

    public String generateStrutsFormClass(String actionName, String strutsFormName, String delegateName, String daoVOName,
                                          String manageRecordForwardActionName, String resourcePermissionName, List<VOFieldToColumnMappingDetails> dbColumns) {
        StringBuffer contents = new StringBuffer();
        contents.append(generateClassFileHeader(actionName, strutsFormName, delegateName, daoVOName));
        contents.append(generateFields(actionName));
        contents.append(generateExecuteMethod(actionName, strutsFormName, delegateName, daoVOName, manageRecordForwardActionName, resourcePermissionName, dbColumns));
        contents.append(generateStrutsFormConstantMethods());
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

    private String convertStringIntoType(VOFieldToColumnMappingDetails detail) {
        String typeName = getTypeName(detail);
        typeName = getShortJavaTypeNameBeforeFirstPeriod(typeName);
        if (typeName.equalsIgnoreCase("String")) {
            return (detail.javaFieldName + "Str");
        }
        if (typeName.equalsIgnoreCase("Long")) {
            return ("Long.valueOf(" + detail.javaFieldName + "Str)");
        }
        if (typeName.equalsIgnoreCase("Date")) {
            //AppConstants.TC_WEB_DATE_TIME_FORMAT.parse(toDate);
            String str = APP_CONSTANTS + PERIOD + FIELD_NAME_TC_WEB_DATE_TIME_FORMAT + PERIOD + METHOD_NAME_PARSE
                    + OPEN_BRACKET + detail.javaFieldName + "Str" + CLOSE_BRACKET;
            return str;
        }
        return typeName;
    }

    protected StringBuffer generateExecuteMethod(String actionName, String formClassName,
                                                 String delegateName, String daoVOName, String manageRecordForwardActionName, String resourcePermissionName, List<VOFieldToColumnMappingDetails> dbColumns) {
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
        sb.append("		boolean actionTokenValid = isActionTokenValid(form.getActionToken(), session);").append(NEW_LINE);
        sb.append("		form.setActionToken(createAndSetNextValidActionToken(session));").append(NEW_LINE);
        sb.append("		String an = servletRequest.getParameter(ActionConstants.ACTION_NAME);").append(NEW_LINE);

        //Repeat over the private keys
        List<VOFieldToColumnMappingDetails> pkColumns = getPrimaryKeys(dbColumns);
        if (pkColumns.isEmpty()) {
            //if no private keys, use the first column
            sb.append("		String ").append(dbColumns.get(0).javaFieldName).append("Str = servletRequest.getParameter(ActionConstants.$PARAM_LABEL_ID$);").append(NEW_LINE);
        } else {
            Iterator<VOFieldToColumnMappingDetails> iter = pkColumns.iterator();
            while (iter.hasNext()) {
                VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
                sb.append("		String ").append(detail.javaFieldName).append("Str = servletRequest.getParameter(ActionConstants.$PARAM_LABEL_ID$);").append(NEW_LINE);
            }
        }

        sb.append("		ActionErrors actionErrors = form.validate(actionMapping, servletRequest);").append(NEW_LINE);
        sb.append("		if(!actionErrors.isEmpty()) {").append(NEW_LINE);
        sb.append("			this.saveErrors(servletRequest, actionErrors);").append(NEW_LINE);
        sb.append("			return actionMapping.findForward(\"business-error\");").append(NEW_LINE);
        sb.append("		} else {").append(NEW_LINE);

        sb.append("			try {").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_NEW.equals(an)) {").append(NEW_LINE);
        sb.append("					// do almost nothing.").append(NEW_LINE);
        sb.append("					form.reset();").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_OPEN.equals(an)) {").append(NEW_LINE);
        sb.append("					// do almost nothing.").append(NEW_LINE);
        sb.append("					form.setActionName(ActionConstants.ACTION_RETRIEVE);").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (ActionConstants.ACTION_RETRIEVE.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("					");
        sb.append(delegateName);
        sb.append(" delegate = new ");
        sb.append(delegateName);
        sb.append("();").append(NEW_LINE);
        sb.append("					try {").append(NEW_LINE);
        sb.append("						");

        sb.append(daoVOName);
        sb.append(" vo = delegate.get");
        sb.append(daoVOName).append(OPEN_BRACKET);

        ///EDITING HERE
        Iterator<VOFieldToColumnMappingDetails> iter = pkColumns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            sb.append(convertStringIntoType(detail)).append(COMMA);
        }

        sb.deleteCharAt(sb.length() - 1);
        sb.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);

        sb.append("						if (vo==null) {").append(NEW_LINE);
        sb.append("							logger.error(\"cannot retrieve vo, error: \" + ");

        iter = pkColumns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            sb.append(convertStringIntoType(detail)).append(" +");
        }
        sb.deleteCharAt(sb.length() - 1);

        sb.append(");").append(NEW_LINE);

        sb.append("							return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("						}").append(NEW_LINE);
        sb.append("						form.mapToForm(vo);").append(NEW_LINE);
        sb.append("					} catch (NullPointerException e) {").append(NEW_LINE);
        sb.append("						logger.error(\"execute delegate: error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("						return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("					}").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("				if (actionTokenValid) {").append(NEW_LINE);
        sb.append("					if (ActionConstants.ACTION_SAVE_AND_EXIT.equals(form.getActionName()) ||").append(NEW_LINE);
        sb.append("							ActionConstants.ACTION_SAVE_AND_ADD_ANOTHER_NEW.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("						");
        sb.append(delegateName);
        sb.append(" delegate = new ");
        sb.append(delegateName);
        sb.append("();").append(NEW_LINE);
        sb.append("						try {").append(NEW_LINE);
        sb.append("							");
        sb.append(daoVOName);
        sb.append(" vo = form.get");
        sb.append(daoVOName);
        sb.append("();").append(NEW_LINE);

        sb.append("							");
        sb.append(daoVOName);
        sb.append(" existingVO = null;").append(NEW_LINE);
        sb.append("							");

        sb.append("if (");
        //if (vo.getTypeName()!= null){
        iter = pkColumns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            sb.append("vo.").append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET).append("!=null &&");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.deleteCharAt(sb.length() - 1);
        sb.append("){").append(NEW_LINE);

        sb.append("								");
        sb.append(" existingVO = delegate.get");
        sb.append(daoVOName).append(OPEN_BRACKET);

        iter = pkColumns.iterator();
        while (iter.hasNext()) {
            VOFieldToColumnMappingDetails detail = (VOFieldToColumnMappingDetails) iter.next();
            sb.append("vo.").append(detail.javaGetterMethod).append(OPEN_BRACKET).append(CLOSE_BRACKET).append(",");
        }
        sb.deleteCharAt(sb.length() - 1);
        sb.append(CLOSE_BRACKET).append(SEMICOLON).append(NEW_LINE);


        sb.append("							");
        sb.append("}").append(NEW_LINE);
        sb.append("							if (existingVO==null) {").append(NEW_LINE);
        sb.append("								delegate.add");
        sb.append(daoVOName);
        sb.append("(vo, clientIp, remoteUser, userId);").append(NEW_LINE);
        sb.append("							} else {").append(NEW_LINE);
        sb.append("								delegate.update");
        sb.append(daoVOName);
        sb.append("(vo, clientIp, remoteUser, userId);").append(NEW_LINE);
        sb.append("							}").append(NEW_LINE);
        sb.append("							logger.info(\"User [\" + remoteUser + \" at \" + clientIp + \"] saves vo: [\" + vo.toString() + \"]\");").append(NEW_LINE);
        sb.append("							if (ActionConstants.ACTION_SAVE_AND_EXIT.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("								form.reset();").append(NEW_LINE);
        sb.append("								return actionMapping.findForward(\"");
        sb.append(manageRecordForwardActionName);
        sb.append("\");").append(NEW_LINE);
        sb.append("							} else if (ActionConstants.ACTION_SAVE_AND_ADD_ANOTHER_NEW.equals(form.getActionName())) {").append(NEW_LINE);
        sb.append("								form.reset();").append(NEW_LINE);
        sb.append("							}").append(NEW_LINE);
        sb.append("						} catch (Exception e) {").append(NEW_LINE);
        sb.append("							logger.error(\"execute delegate: error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("							return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("						}").append(NEW_LINE);
        sb.append("					}").append(NEW_LINE);
        sb.append("				} else {").append(NEW_LINE);
        sb.append("					// action token is invalid.").append(NEW_LINE);
        sb.append("				}").append(NEW_LINE);
        sb.append("			} catch(TCAppException e) {").append(NEW_LINE);
        sb.append("				logger.error(\"");
        sb.append(actionName);
        sb.append(".execute error: \" + e.getMessage());").append(NEW_LINE);
        sb.append("				ActionErrors errors = new ActionErrors();").append(NEW_LINE);
        sb.append("				errors.add(\"edit");
        sb.append(daoVOName);
        sb.append("\", new ActionMessage(\"error.edit");
        sb.append(daoVOName);
        sb.append("\", \"error.edit");
        sb.append(daoVOName);
        sb.append("\"));").append(NEW_LINE);
        sb.append("				this.saveErrors(servletRequest, errors);").append(NEW_LINE);
        sb.append("				return actionMapping.findForward(\"error\");").append(NEW_LINE);
        sb.append("			}").append(NEW_LINE);


        sb.append("		}").append(NEW_LINE);
        sb.append("		return actionMapping.findForward(\"success\");").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        return sb;
    }

    protected StringBuffer generateStrutsFormConstantMethods() {
        StringBuffer sb = new StringBuffer();

        sb.append("	private boolean isActionTokenValid(String actionToken, HttpSession session) {").append(NEW_LINE);
        sb.append("		if (actionToken==null) return false;").append(NEW_LINE);
        sb.append("		String currentValidActionToken = (String)session.getAttribute(ActionConstants.ACTION_TOKEN_ATTR_NAME);").append(NEW_LINE);
        sb.append("		if (currentValidActionToken == null) return false;").append(NEW_LINE);
        sb.append("		if (!actionToken.equals(currentValidActionToken))return false;").append(NEW_LINE);
        sb.append("		return true;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);
        sb.append("	private String createAndSetNextValidActionToken(HttpSession session) {").append(NEW_LINE);
        sb.append("		String nextActionToken = \"\" + (System.currentTimeMillis() + Thread.currentThread().getName().hashCode());").append(NEW_LINE);
        sb.append("		session.setAttribute(ActionConstants.ACTION_TOKEN_ATTR_NAME, nextActionToken);").append(NEW_LINE);
        sb.append("		return nextActionToken;").append(NEW_LINE);
        sb.append("	}").append(NEW_LINE);

        return sb;
    }

}