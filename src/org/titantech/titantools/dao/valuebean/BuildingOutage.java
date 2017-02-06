package org.titantech.titantools.dao.valuebean;

import java.io.Serializable;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Date;

public class BuildingOutage extends RecordMapperValueObject implements Serializable {

    private static final long serialVersionUID = -4602604478181406507L;

    //	BUILDING_OUTAGE
//    BO_ID                INTEGER NOT NULL,
//    STATUS_CD            NUMBER(2) NULL,
//    BUILDING_ID          VARCHAR2(12) NOT NULL,
//    OUTAGE_START_TIME    DATE NULL,
//    INVESTIGATION_START_TIME DATE NULL,
//    OUTAGE_CONFIRMED_TIME DATE NULL,
//    INVESTIGATION_END_TIME DATE NULL,
//    DM_TICKET_ID         INTEGER NULL
//    CLOSURE_COMMENTS     VARCHAR2(1000)
    private Integer buildingOutageId;
    private Integer statusCode;
    private String buildingId;
    private Date outageStartTime;
    private Date investigationStartTime;
    private Date outageConfirmedTime;
    private Date investigationEndTime;
    private String dmTicketId;
    private String closureComments;
    private Integer userId;
    private Date lockedByUserDateTime;

    //	DM_TICKET
//    DM_TICKET_ID         INTEGER NOT NULL,
//    VENDOR_NAME          VARCHAR2(10) NULL,
//    TICKET_DISPATCHED_TIME DATE NULL,
//    ESTIMATED_REPAIR_TIME DATE NULL,
//    ACTUAL_REPAIR_TIME   DATE NULL
    private String vendorName;
    private Date ticketDispatchedTime;
    private Date estimatedRepairTime;
    private Date actualRepairTime;
    private String repairComments;

    // BUILDING_OUTAGE_STATUS
    private String statusDescription;

    private ArrayList saOrders;
    private int saOrdersCount;

    public BuildingOutage() {
        // building outage
        addVOField("BO_ID", Types.INTEGER, "setBuildingOutageId", Integer.class, "getBuildingOutageId");
        addVOField("STATUS_CD", Types.INTEGER, "setStatusCode", Integer.class, "getStatusCode");
        addVOField("BUILDING_ID", Types.VARCHAR, "setBuildingId", String.class, "getBuildingId");
        addVOField("OUTAGE_START_TIME", Types.DATE, "setOutageStartTime", Date.class, "getOutageStartTime");
        addVOField("INVESTIGATION_START_TIME", Types.DATE, "setInvestigationStartTime", Date.class, "getInvestigationStartTime");
        addVOField("OUTAGE_CONFIRMED_TIME", Types.DATE, "setOutageConfirmedTime", Date.class, "getOutageConfirmedTime");
        addVOField("INVESTIGATION_END_TIME", Types.DATE, "setInvestigationEndTime", Date.class, "getInvestigationEndTime");
        addVOField("DM_TICKET_ID", Types.VARCHAR, "setDmTicketId", String.class, "getDmTicketId");
        addVOField("CLOSURE_COMMENTS", Types.VARCHAR, "setClosureComments", String.class, "getClosureComments");
        addVOField("USER_ID", Types.NUMERIC, "setUserId", Integer.class, "getUserId");
        addVOField("LOCKED_BY_USER_DT", Types.DATE, "setLockedByUserDateTime", Date.class, "getLockedByUserDateTime");

        // dm ticket
        addVOField("VENDOR_NAME", Types.VARCHAR, "setVendorName", String.class, "getVendorName");
        addVOField("TICKET_DISPATCHED_TIME", Types.DATE, "setTicketDispatchedTime", Date.class, "getTicketDispatchedTime");
        addVOField("ESTIMATED_REPAIR_TIME", Types.DATE, "setEstimatedRepairTime", Date.class, "getEstimatedRepairTime");
        addVOField("ACTUAL_REPAIR_TIME", Types.DATE, "setActualRepairTime", Date.class, "getActualRepairTime");
        addVOField("REPAIR_COMMENTS", Types.VARCHAR, "setRepairComments", String.class, "getRepairComments");

        // building outage status
        addVOField("STATUS_DESCRIPTION", Types.VARCHAR, "setStatusDescription", String.class, "getStatusDescription");
    }

    public String getBuildingId() {
        return buildingId;
    }

    public void setBuildingId(String buildingId) {
        this.buildingId = buildingId;
    }

    public Integer getBuildingOutageId() {
        return buildingOutageId;
    }

    public void setBuildingOutageId(Integer buildingOutageId) {
        this.buildingOutageId = buildingOutageId;
    }

    public String getDmTicketId() {
        return dmTicketId;
    }

    public void setDmTicketId(String dmTicketId) {
        this.dmTicketId = dmTicketId;
    }

    public Date getInvestigationEndTime() {
        return investigationEndTime;
    }

    public void setInvestigationEndTime(Date investigationEndTime) {
        this.investigationEndTime = investigationEndTime;
    }

    public Date getInvestigationStartTime() {
        return investigationStartTime;
    }

    public void setInvestigationStartTime(Date investigationStartTime) {
        this.investigationStartTime = investigationStartTime;
    }

    public Date getOutageConfirmedTime() {
        return outageConfirmedTime;
    }

    public void setOutageConfirmedTime(Date outageConfirmedTime) {
        this.outageConfirmedTime = outageConfirmedTime;
    }

    public Date getOutageStartTime() {
        return outageStartTime;
    }

    public void setOutageStartTime(Date outageStartTime) {
        this.outageStartTime = outageStartTime;
    }

    public Integer getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
    }

    public Date getActualRepairTime() {
        return actualRepairTime;
    }

    public void setActualRepairTime(Date actualRepairTime) {
        this.actualRepairTime = actualRepairTime;
    }

    public Date getEstimatedRepairTime() {
        return estimatedRepairTime;
    }

    public void setEstimatedRepairTime(Date estimatedRepairTime) {
        this.estimatedRepairTime = estimatedRepairTime;
    }

    public Date getTicketDispatchedTime() {
        return ticketDispatchedTime;
    }

    public void setTicketDispatchedTime(Date ticketDispatchedTime) {
        this.ticketDispatchedTime = ticketDispatchedTime;
    }

    public String getVendorName() {
        return vendorName;
    }

    public void setVendorName(String vendorName) {
        this.vendorName = vendorName;
    }

    public ArrayList getSaOrders() {
        return saOrders;
    }

    public void setSaOrders(ArrayList saOrders) {
        this.saOrders = saOrders;
    }

    public int getSaOrdersCount() {
        if (this.saOrders == null) {
            return 0;
        }
        this.saOrdersCount = this.saOrders.size();
        return this.saOrdersCount;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }

    public String getClosureComments() {
        return closureComments;
    }

    public void setClosureComments(String closureComments) {
        this.closureComments = closureComments;
    }

    public String getRepairComments() {
        return repairComments;
    }

    public void setRepairComments(String repairComments) {
        this.repairComments = repairComments;
    }

    public Date getLockedByUserDateTime() {
        return lockedByUserDateTime;
    }

    public void setLockedByUserDateTime(Date lockedByUserDateTime) {
        this.lockedByUserDateTime = lockedByUserDateTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
}