<%@ page contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1" %>

<%@taglib uri="/WEB-INF/struts-html.tld" prefix="html" %>
<%@taglib uri="/WEB-INF/struts-bean.tld" prefix="bean" %>
<%@taglib uri="/WEB-INF/struts-logic.tld" prefix="logic" %>

<jsp:useBean id="mainForm" scope="request" class="com.chatach.web.form.ForumThreadForm"/>

<html>

<link href="css/style.css" rel="stylesheet" type="text/css" media="screen"/>
<style>

    #mainouter {
        position: relative;
        z-index: 2;
        padding-top: 4px;
        padding-bottom: 4px;
    / / background: #ccc;
        height: 100%;
        width: 99%;
        margin: 0 auto;
        border-width: 1px;
        border-style: solid;
    }

    #topbar {
        position: relative;
        z-index: 3;
        padding-top: 4px;
        padding-bottom: 4px;
    / / background: #ccc;
        height: 20px;
        width: 99%;
        margin: 0 auto;
        border-width: 1px;
        border-style: solid;
    }

    .topbar-el {
        float: left;
        width: 10%;
    }

    #maininner {
        position: relative;
        z-index: 4;
        padding-top: 4px;
        padding-bottom: 4px;
    / / background: #ccc;
        height: 95%;
        width: 99%;
        margin: 0 auto;
        border-width: 1px;
        border-style: solid;
    }

    #leftnav {
        position: relative;
        z-index: 5;
        padding-top: 0px;
        float: left;
        border-width: 1px;
        border-style: solid;
        min-width: 100px;
        height: 100%;
        margin-left: 10px;
    }

    #topic {
        position: relative;
        z-index: 5;
        padding-top: 0px;
        margin-left: 10px;
        float: left;
        border-width: 1px;
        border-style: solid;
        min-width: 400px;
        height: 100%;
    }

    #rightnav {
        position: relative;
        z-index: 5;
        padding-top: 0px;
        float: right;
        border-width: 1px;
        border-style: solid;
        min-width: 100px;
        height: 100%
        margin-left: 10px;
    }

    .topic-summary {
        position: relative;
        z-index: 6;
        padding-top: 10px;
        border-width: 1px;
        border-style: solid;
    / / min-width: 100 px;
        min-height: 100px;
    }

    .topic-summary-header {
        position: relative;
        z-index: 7;
        border-width: 1px;
        border-style: solid;
    / / width: 100 %;
        height: 40px;
    }

    .topic-summary-header-subject {
        position: relative;
        z-index: 8;
        border-width: 1px;
        border-style: solid;
    / / width: 100 %;
    }

    .topic-summary-header-meta {
        position: relative;
        z-index: 8;
        padding-top: 4px;
        border-width: 1px;
        border-style: solid;
    / / width: 100 %;
    }

    .topic-summary-text {
        position: relative;
        z-index: 8;
        padding-top: 4px;
        border-width: 1px;
        border-style: solid;
    }

    .discussion {
        position: relative;
        z-index: 6;
        padding-top: 10px;
        border-width: 1px;
        border-style: dotted;

    / / min-width: 100 px;
        min-height: 100px;
    }

    .thread {
        padding-left: 10px;
        padding-top: 5px;
    }

    .comment {
        position: relative;
        z-index: 6;
        padding-top: 0px;
        border-width: 1px;
        border-style: solid;
    / / min-width: 100 px;
        min-height: 100px;
    }

    .comment-header {
        position: relative;
        z-index: 6;
        border-width: 1px;
        border-style: solid;
    }

    .comment-header-subject {
        position: relative;
        z-index: 6;
        border-width: 1px;
        border-style: solid;
    / / min-width: 100 px;
        height: 20px;
    }

    .comment-header-meta {
        position: relative;
        z-index: 6;
        border-width: 1px;
        border-style: solid;
        height: 20px;
    }
</style>

<body>

<div id="mainouter">
    <html:form action="/forumThread" method="post">

        <div id="topbar">
            <div class="topbar-el">FORUM LOGO</div>
            <div class="topbar-el">SEARCH</div>
            <div class="topbar-el" style="width:60%">&nbsp;</div>
            <div class="topbar-el">USER NAME</div>
            <div class="topbar-el">LOGOUT</div>
        </div>

        <div id="maininner">
            <table style="width:100%;border-width:1px;border-style:solid">
                <tr>
                    <td valign="top" style="border-width:1px;border-style:solid;width:220px">
                        <div id="leftnav">
                            <a href="">general discussions</a><BR><BR>
                            <a href="">shipping discussions</a><BR><BR>
                            <a href="">address discussions</a><BR><BR>
                            <a href="">consolidation discussions</a>
                        </div>
                    </td>
                    <td valign="top" style="border-width:1px;border-style:solid">

                        <div id="topic">
                            <div class="topic-summary">
                                <div class="topic-summary-header">
                                    <div class="topic-summary-header-subject">Header 1</div>
                                    <div class="topic-summary-header-meta">posted by, on a date</div>
                                </div>
                                <div class="topic-summary-text">
                                    This is the actual summary of the topic, blah blah, mahan gotovitsa blah blah some
                                    weird shit
                                </div>
                            </div>

                            <div class="discussion">
                                <div class="comment">
                                    <div class="comment-header">
                                        <div class="comment-header-subject">Comment 1</div>
                                        <div class="comment-header-meta">posted by, on a date</div>
                                    </div>
                                    <div class="comment-body">
                                        some comment text
                                    </div>
                                    <div class="thread">
                                        <div class="comment">
                                            <div class="comment-header">
                                                <div class="comment-header-subject">Comment 1</div>
                                                <div class="comment-header-meta">posted by, on a date</div>
                                            </div>
                                            <div class="comment-body">
                                                some comment text2 22
                                            </div>
                                            <div class="thread">
                                                <div class="comment">
                                                    <div class="comment-header">
                                                        <div class="comment-header-subject">Comment 1</div>
                                                        <div class="comment-header-meta">posted by, on a date</div>
                                                    </div>
                                                    <div class="comment-body">
                                                        some comment text2 22 2- 2-34 -02 30-49820 01240 12asoefu fasdyh
                                                        aiufh 29i387y4928f hwio38r y2938y2p89f y2p938ry 2p938fy w9i8fh
                                                        984rty934y7tergukhigfa 93w84y92847f 98w34f7 938w427f df sdf 90u2
                                                        3409ruw 0ed9fj 0293ur 029uf 029wuf 902u3f 092u3f
                                                    </div>
                                                    <div class="thread">
                                                        <div class="comment">
                                                            <div class="comment-header">
                                                                <div class="comment-header-subject">Comment 1</div>
                                                                <div class="comment-header-meta">posted by, on a date
                                                                </div>
                                                            </div>
                                                            <div class="comment-body">
                                                                some comment text2 22 2- 2-34 -02 30-49820 01240
                                                                12asoefu fasdyh aiufh 29i387y4928f hwio38r y2938y2p89f
                                                                y2p938ry 2p938fy w9i8fh 984rty934y7tergukhigfa
                                                                93w84y92847f 98w34f7 938w427f df sdf 90u2 3409ruw 0ed9fj
                                                                0293ur 029uf 0
                                                                29wuf 902u3f 092u3f
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div class="comment">
                                    <div class="comment-header">
                                        <div class="comment-header-subject">Comment 1</div>
                                        <div class="comment-header-meta">posted by, on a date</div>
                                    </div>
                                    <div class="comment-body">
                                        some comment text
                                    </div>
                                    <div class="thread">
                                        <div class="comment">
                                            <div class="comment-header">
                                                <div class="comment-header-subject">Comment 1</div>
                                                <div class="comment-header-meta">posted by, on a date</div>
                                            </div>
                                            <div class="comment-body">
                                                some comment text2 22
                                            </div>
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </div>

                    </td>
                    <td valign="top" style="border-width:1px;border-style:solid;width:180px">
                        <div id="rightnav">
                            latest comments...<BR><BR>
                            something else
                        </div>
                    </td>
                </tr>
            </table>
            &nbsp;
        </div>

        <html:errors/>
    </html:form>

</div>

<BR><BR>
<html:form action="/forumThread" method="post">
    <html:errors/>
    <bean:write name="forumThreadForm" property="text"/>
</html:form>

</body>
</html>