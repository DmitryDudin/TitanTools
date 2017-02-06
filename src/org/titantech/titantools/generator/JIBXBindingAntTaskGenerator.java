package org.titantech.titantools.generator;

public class JIBXBindingAntTaskGenerator extends GeneratorBase {
/*

	<target depends="compile.java" name="generate.cp.jibx.binding.source">

		<bind verbose="false" load="true" binding="${csg-bean-retrieveitemlist-xml-bind-src-file}">
			<classpath>
				<pathelement path="${build.dir}/classes"/>
				<pathelement location="${jibx-lib}/jibx-run.jar"/>
			</classpath>
		</bind>

		<bind verbose="false" load="true" binding="${csg-bean-retrieveitemlistresponse-xml-bind-src-file}">
			<classpath>
				<pathelement path="${build.dir}/classes"/>
				<pathelement location="${jibx-lib}/jibx-run.jar"/>
			</classpath>
		</bind>

	</target>


JIBX-EDGE-JAR=JIBX-Edge.jar
jibx-build.dir=jibxclasses

cp-input-xml-bind-file=${eclipse.project.home}/src/com/belltv/edge/web/bean/cp/CPJIBXBinding.xml
csg-bean-dir=com/belltv/edge/io/csg/bean
csg-bean-retrieveitemlist-xml-bind-src-dir=${eclipse.project.home}/src/${csg-bean-dir}/retrieveitemlist
csg-bean-retrieveitemlist-xml-bind-src-file=${csg-bean-retrieveitemlist-xml-bind-src-dir}/RetrieveItemListJIBXBinding.xml
csg-bean-retrieveitemlist-xml-bind-bin-dir=${eclipse.project.bin}/${csg-bean-dir}/retrieveitemlist
csg-bean-retrieveitemlist-xml-bind-jibxbin-dir=${eclipse.project.home}/${jibx-build.dir}/${csg-bean-dir}/retrieveitemlist
csg-bean-retrieveitemlist-xml-bind-antbuild-dir=${build.dir.classes}/${csg-bean-dir}/retrieveitemlist


csg-bean-retrieveitemlistresponse-xml-bind-src-dir=${eclipse.project.home}/src/${csg-bean-dir}/retrieveitemlist/response
csg-bean-retrieveitemlistresponse-xml-bind-src-file=${csg-bean-retrieveitemlistresponse-xml-bind-src-dir}/RetrieveItemListResponseJIBXBinding.xml



		<bind verbose="false" load="true" binding="${csg-bean-retrieveitemlistresponse-xml-bind-src-file}">
			<classpath>
				<pathelement path="${build.dir}/classes"/>
				<pathelement location="${jibx-lib}/jibx-run.jar"/>
			</classpath>
		</bind>

*/

	
	/*			
    <bind verbose="false" load="true" binding="${csg-bean-retrieveitemlist-xml-bind-src-file}">
		<classpath>
			<pathelement path="${build.dir}/classes"/>
			<pathelement location="${jibx-lib}/jibx-run.jar"/>
			</classpath>
	</bind>
}
*/
    //csg-bean-retrieveitemlist-xml-bind-src-file
    //csg-bean-retrieveitemlistresponse-xml-bind-src-file

    private static final String PROJECT_HOME_ANT_PROPERTY = "project.home";

    private String schemaBusinessName = null;
    private String javaProjectDir = null;
    private String outDir = null;
    private String voJavaClassName = null;
    private String packageName = null;
    private String ioPackageStructure = null;
    private String srcDirName = null;

    public void initialize(String schemaBusinessName, String javaProjectDir, String outDir,
                           String voJavaClassName, String packageName, String ioPackageStructure, String srcDirName) {

        this.schemaBusinessName = schemaBusinessName;
        this.javaProjectDir = javaProjectDir;
        this.outDir = outDir;
        this.voJavaClassName = voJavaClassName;
        this.packageName = packageName;
        this.ioPackageStructure = ioPackageStructure;
        this.srcDirName = srcDirName;
    }

    public String generateJIBXBindingAntTask() {
        String xmlBindSrcFile = getJIBXBindTaskSrcFileName();
        StringBuffer sb = getJIBXBindTask(xmlBindSrcFile, "\t");
        return sb.toString();
    }

    public String generateJIBXBindingAntProperties() {
        StringBuffer sb = new StringBuffer();
        sb.append(getProjectHomeAntPropertyNameValue()).append(NEW_LINE);
        sb.append(getBusinessBeanDirName()).append(NEW_LINE);
        sb.append(getJIBXBindTaskSrcDirName()).append(EQUAL).append(getJIBXBindTaskSrcFileDirValue()).append(NEW_LINE);
        sb.append(getJIBXBindTaskSrcFileName()).append(EQUAL).append(getJIBXBindTaskSrcFileNameValue()).append(NEW_LINE);
        return sb.toString();
    }

    // generate this: project.home=C:/1
    private String getProjectHomeAntPropertyNameValue() {
        return PROJECT_HOME_ANT_PROPERTY + EQUAL + javaProjectDir;
    }


    // generate this: csg-bean-dir=com/belltv/edge/io/csg/bean       com/belltv/edge/io/
    private String getBusinessBeanDirName() {
        return schemaBusinessName.toLowerCase() + "-bean-dir=" + ioPackageStructure +
                schemaBusinessName.toLowerCase() + "/bean";
    }

    // generate this: ${eclipse.project.home}/src/${csg-bean-dir}/retrieveitemlist
    private String getJIBXBindTaskSrcFileDirValue() {
        return "${" + PROJECT_HOME_ANT_PROPERTY + "}" +
                "/" + srcDirName + "/" + "${" + schemaBusinessName.toLowerCase() +
                "-bean-dir}/" + voJavaClassName.toLowerCase();
    }

    // generate this: csg-bean-retrieveitemlist-xml-bind-src-dir
    private String getJIBXBindTaskSrcDirName() {
        return schemaBusinessName.toLowerCase() +
                "-bean-" + voJavaClassName.toLowerCase() + "-xml-bind-src-dir";
    }

    // generate this: ${csg-bean-retrieveitemlist-xml-bind-src-dir}/RetrieveItemListJIBXBinding.xml
    private String getJIBXBindTaskSrcFileNameValue() {
        return "${" + schemaBusinessName.toLowerCase() +
                "-bean-" + voJavaClassName.toLowerCase() +
                "-xml-bind-src-dir}/" + voJavaClassName +
                JIBX_MAP_FILE_SUFFIX + PERIOD + XML_FILE_EXTENSION;
    }

    // generate this: csg-bean-retrieveitemlist-xml-bind-src-file
    private String getJIBXBindTaskSrcFileName() {
        return schemaBusinessName.toLowerCase() +
                "-bean-" + voJavaClassName.toLowerCase() + "-xml-bind-src-file";
    }

    private StringBuffer getJIBXBindTask(String xmlBindSrcFile, String indent) {
        StringBuffer sb = new StringBuffer()
                .append("\n").append(indent)
                .append("<bind verbose=\"false\" load=\"true\" binding=\"${")
                .append(xmlBindSrcFile)
                .append("}\">")
                .append("\n").append(indent).append(TAB)
                .append("<classpath>")
                .append("\n").append(indent).append(TAB).append(TAB)
                .append("<pathelement path=\"${build.dir}/classes\"/>")
                .append("\n").append(indent).append(TAB).append(TAB)
                .append("<pathelement location=\"${jibx-lib}/jibx-run.jar\"/>")
                .append("\n").append(indent).append(TAB)
                .append("</classpath>")
                .append("\n").append(indent)
                .append("</bind>");
        return sb;
    }
}