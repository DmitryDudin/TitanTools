package org.titantech.titantools.dao;

import org.titantech.titantools.dao.bean.SearchPageGeneratorInputBean;
import org.titantech.titantools.dao.bean.VOFieldToColumnMappingDetails;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class PGVOGeneratorDAO extends BaseDAO implements VOGeneratorDAO {

    private PGDAOFactory pgDAOFactory = null;

    public PGVOGeneratorDAO(PGDAOFactory pgDAOFactory) {
        this.pgDAOFactory = pgDAOFactory;
    }

    public List getTableMetaData(String tableName) throws DAOAppException {
        List out = new ArrayList();
        String sql = "SELECT * FROM " + tableName + " ";
        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = pgDAOFactory.getConnection();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();
            int numberOfColumns = metaData.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                VOFieldToColumnMappingDetails details = new VOFieldToColumnMappingDetails();
                details.dbColumnName = metaData.getColumnName(i);
                details.dbColumnType = metaData.getColumnType(i);
                details.dbColumnTypeName = metaData.getColumnTypeName(i);
                out.add(details);
            }
        } catch (SQLException e) {
            throw new DAOSysException("Error loading case types : " + e.getMessage());
        } finally {
            finalize(rs, stmt);
        }
        return out;
    }


    public List getDatabaseTables(String databaseSchema) throws DAOAppException {
        List listOfTables = new ArrayList();
//        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='public' AND table_type='BASE TABLE'";
        String sql = "SELECT table_name FROM information_schema.tables WHERE table_schema='" + databaseSchema + "' AND table_type='BASE TABLE'";
        Statement stmt = null;
        ResultSet rs = null;
        Connection connection = pgDAOFactory.getConnection();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                listOfTables.add(rs.getString(1));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }


        //....

        // TODO: Get tables from db, get table info, create data structure describing it, return a list of these structures

        return listOfTables;
    }

    public List getListOfSPGInputBeans(String tableName, String databaseSchema) throws DAOAppException {
        List out = new ArrayList();
        String sql = "SELECT * FROM " + databaseSchema + "." + tableName + " ";
        ResultSet rs = null;
        Statement stmt = null;
        Connection connection = pgDAOFactory.getConnection();
        try {
            stmt = connection.createStatement();
            rs = stmt.executeQuery(sql);
            ResultSetMetaData metaData = rs.getMetaData();


            int numberOfColumns = metaData.getColumnCount();
            for (int i = 1; i <= numberOfColumns; i++) {
                SearchPageGeneratorInputBean inputBean = new SearchPageGeneratorInputBean();

                inputBean.dbFieldName = metaData.getColumnName(i);
                inputBean.dbFieldType = metaData.getColumnType(i); // ??
                inputBean.dbFieldTypeStr = getFieldTypeStr(metaData.getColumnType(i)); // ??
                // inputBean.javaTypeName = ????
                inputBean.serial = metaData.isAutoIncrement(i);

                out.add(inputBean);
            }

        } catch (SQLException e) {
            throw new DAOSysException("Error loading case types : " + e.getMessage());
        } finally {
            finalize(rs, stmt);
        }
        return out;
    }

    private String getFieldTypeStr(int columnType) {

        Map typeIntToString = new HashMap();

        typeIntToString.put(Types.LONGVARCHAR, "TEXT");
        typeIntToString.put(Types.NUMERIC, "NUMERIC");
        typeIntToString.put(Types.TIMESTAMP, "TIMESTAMP");
        typeIntToString.put(Types.VARCHAR, "VARCHAR");
        typeIntToString.put(Types.DATE, "DATE");
        typeIntToString.put(Types.INTEGER, "INTEGER");
        typeIntToString.put(Types.CHAR, "CHAR");
        typeIntToString.put(Types.TIME, "TIME");
        typeIntToString.put(Types.BIGINT, "BIGINT");
        typeIntToString.put(Types.DOUBLE, "DOUBLE");
        typeIntToString.put(Types.FLOAT, "FLOAT");
        typeIntToString.put(Types.DECIMAL, "DECIMAL");
        typeIntToString.put(Types.SMALLINT, "SMALLINT");
        typeIntToString.put(Types.TINYINT, "TINYINT");

        return (String) typeIntToString.get(columnType);
    }

/*
    public List getBuildingOutages(Integer statusCode, BuildingOutageFilter filter) throws DAOAppException {
		List list = new ArrayList();
		StringBuffer sb = new StringBuffer();

		sb.append("SELECT * FROM ( SELECT ROWNUM RNUM, A.* FROM ( ");
		sb.append("SELECT ");
		sb.append(" BO.BO_ID, BO.STATUS_CD, BO.BUILDING_ID, BO.OUTAGE_START_TIME,");
		sb.append(" BO.INVESTIGATION_START_TIME, BO.OUTAGE_CONFIRMED_TIME,");
		sb.append(" BO.INVESTIGATION_END_TIME, BO.DM_TICKET_ID, BO.CLOSURE_COMMENTS,");
		sb.append(" BO.USER_ID, BO.LOCKED_BY_USER_DT,");
		sb.append("DT.VENDOR_NAME, DT.TICKET_DISPATCHED_TIME, DT.ESTIMATED_REPAIR_TIME,");
		sb.append("DT.ACTUAL_REPAIR_TIME, DT.REPAIR_COMMENTS, BOS.STATUS_DESCRIPTION ");
		sb.append(" FROM ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("BUILDING_OUTAGE_STATUS BOS, ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("BUILDING_OUTAGE BO LEFT OUTER JOIN ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("DM_TICKET DT ON BO.DM_TICKET_ID=DT.DM_TICKET_ID ");
		sb.append("WHERE BOS.STATUS_CD=BO.STATUS_CD");
		if (statusCode!=null) {
			sb.append(" AND BO.STATUS_CD=?");
		}
		if (filter!=null) {
			if (filter.investigationStartTimeFrom!=null) {
				sb.append(" AND BO.INVESTIGATION_START_TIME>=?");
			}
			if (filter.investigationStartTimeTo!=null) {
				sb.append(" AND BO.INVESTIGATION_START_TIME<=?");
			}
			if (filter.outageStartTimeFrom!=null) {
				sb.append(" AND BO.OUTAGE_START_TIME>=?");
			}
			if (filter.outageStartTimeTo!=null) {
				sb.append(" AND BO.OUTAGE_START_TIME<=?");
			}
			if (filter.confirmedTimeFrom!=null) {
				sb.append(" AND BO.OUTAGE_CONFIRMED_TIME>=?");
			}
			if (filter.confirmedTimeTo!=null) {
				sb.append(" AND BO.OUTAGE_CONFIRMED_TIME<=?");
			}
			if (filter.dispatchedTimeFrom!=null) {
				sb.append(" AND DT.TICKET_DISPATCHED_TIME>=?");
			}
			if (filter.dispatchedTimeTo!=null) {
				sb.append(" AND DT.TICKET_DISPATCHED_TIME<=?");
			}
			if (filter.estimatedRepairTimeFrom!=null) {
				sb.append(" AND DT.ESTIMATED_REPAIR_TIME>=?");
			}
			if (filter.estimatedRepairTimeTo!=null) {
				sb.append(" AND DT.ESTIMATED_REPAIR_TIME<=?");
			}
			if (filter.actualRepairTimeFrom!=null) {
				sb.append(" AND DT.ACTUAL_REPAIR_TIME>=?");
			}
			if (filter.actualRepairTimeTo!=null) {
				sb.append(" AND DT.ACTUAL_REPAIR_TIME<=?");
			}
			if (filter.investigationEndTimeFrom!=null) {
				sb.append(" AND BO.INVESTIGATION_END_TIME>=?");
			}
			if (filter.investigationEndTimeTo!=null) {
				sb.append(" AND BO.INVESTIGATION_END_TIME<=?");
			}

			if (filter.investigationStartTimeOrder!=null ||
					filter.outageStartTimeOrder!=null ||
					filter.confirmedTimeOrder!=null ||
					filter.dispatchedTimeOrder!=null ||
					filter.estimatedRepairTimeOrder!=null ||
					filter.actualRepairTimeOrder!=null ||
					filter.investigationEndTimeOrder!=null ||
					filter.boIdOrder!=null ||
					filter.statusOrder!=null) {
				sb.append(" ORDER BY ");
			}
			for(int orderIndex=0;orderIndex<9;orderIndex++) {
				addOrderingClauses(sb, filter, orderIndex);
			}
		}
		String sql = sb.toString();
		if (sql.charAt(sql.length()-1)==',') {
			sql = sql.substring(0, sql.length()-1);
		}

		sql += ") A WHERE ROWNUM<=?) WHERE RNUM>?";

		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			int i=1;

			if (statusCode!=null) {
				ps.setInt(i++, statusCode.intValue());
			}
			if (filter!=null) {
				if (filter.investigationStartTimeFrom!=null) {

					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationStartTimeFrom.getTime()));
				}
				if (filter.investigationStartTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationStartTimeTo.getTime()));
				}
				if (filter.outageStartTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.outageStartTimeFrom.getTime()));
				}
				if (filter.outageStartTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.outageStartTimeTo.getTime()));
				}
				if (filter.confirmedTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.confirmedTimeFrom.getTime()));
				}
				if (filter.confirmedTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.confirmedTimeTo.getTime()));
				}
				if (filter.dispatchedTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.dispatchedTimeFrom.getTime()));
				}
				if (filter.dispatchedTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.dispatchedTimeTo.getTime()));
				}
				if (filter.estimatedRepairTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.estimatedRepairTimeFrom.getTime()));
				}
				if (filter.estimatedRepairTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.estimatedRepairTimeTo.getTime()));
				}
				if (filter.actualRepairTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.actualRepairTimeFrom.getTime()));
				}
				if (filter.actualRepairTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.actualRepairTimeTo.getTime()));
				}
				if (filter.investigationEndTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationEndTimeFrom.getTime()));
				}
				if (filter.investigationEndTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationEndTimeTo.getTime()));
				}
				ps.setInt(i++, filter.retrieveRecords);
				ps.setInt(i++, filter.fromRecordNumber);
			}
			rs = ps.executeQuery();
			while(rs.next()) {
				list.add(populateValueObject(rs, BuildingOutage.class, null));
			}
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
		return list;
	}

	private void addOrderingClauses(StringBuffer sb, BuildingOutageFilter filter, int i) {
		if (filter.investigationStartTimeOrder!=null && filter.investigationStartTimeOrdering==i) {
			sb.append(" BO.INVESTIGATION_START_TIME ");
			sb.append(filter.investigationStartTimeOrder);
			sb.append(",");
		}
		if (filter.outageStartTimeOrder!=null && filter.outageStartTimeOrdering==i) {
			sb.append(" BO.OUTAGE_START_TIME ");
			sb.append(filter.outageStartTimeOrder);
			sb.append(",");
		}
		if (filter.confirmedTimeOrder!=null && filter.confirmedTimeOrdering==i) {
			sb.append(" BO.OUTAGE_CONFIRMED_TIME ");
			sb.append(filter.confirmedTimeOrder);
			sb.append(",");
		}
		if (filter.dispatchedTimeOrder!=null && filter.dispatchedTimeOrdering==i) {
			sb.append(" DT.TICKET_DISPATCHED_TIME ");
			sb.append(filter.dispatchedTimeOrder);
			sb.append(",");
		}
		if (filter.estimatedRepairTimeOrder!=null && filter.estimatedRepairTimeOrdering==i) {
			sb.append(" DT.ESTIMATED_REPAIR_TIME ");
			sb.append(filter.estimatedRepairTimeOrder);
			sb.append(",");
		}
		if (filter.actualRepairTimeOrder!=null && filter.actualRepairTimeOrdering==i) {
			sb.append(" DT.ACTUAL_REPAIR_TIME ");
			sb.append(filter.actualRepairTimeOrder);
			sb.append(",");
		}
		if (filter.investigationEndTimeOrder!=null && filter.investigationEndTimeOrdering==i) {
			sb.append(" BO.INVESTIGATION_END_TIME ");
			sb.append(filter.investigationEndTimeOrder);
			sb.append(",");
		}
		if (filter.boIdOrder!=null && filter.boIdOrdering==i) {
			sb.append(" BO.BO_ID ");
			sb.append(filter.boIdOrder);
			sb.append(",");
		}
		if (filter.statusOrder!=null && filter.statusOrdering==i) {
			sb.append(" BO.STATUS_CD ");
			sb.append(filter.statusOrder);
			sb.append(",");
		}
	}
	
	public BuildingOutage getBuildingOutage(Integer boId) throws DAOAppException {
		BuildingOutage bo = null;
		String sql = "SELECT * FROM " +
			AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE BO LEFT OUTER JOIN " + 
			AppConstants.BO_SCHEMA_NAME + "DM_TICKET DT ON BO.DM_TICKET_ID=DT.DM_TICKET_ID WHERE BO.BO_ID=?";

		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, boId.intValue());
			rs = ps.executeQuery();
			while(rs.next()) {
				bo = (BuildingOutage)populateValueObject(rs, BuildingOutage.class, null);
			}
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
		return bo;
	}

	public void saveInvestigationStartTimeAndLock(BuildingOutage bo) throws DAOAppException {
		String sql = "UPDATE " + AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE SET INVESTIGATION_START_TIME=?, USER_ID=?, LOCKED_BY_USER_DT=SYSDATE WHERE BO_ID=?";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			int i = 1;
			if (bo.getInvestigationStartTime()!=null) {
				ps.setTimestamp(i++, new java.sql.Timestamp(bo.getInvestigationStartTime().getTime()));
			} else {
				ps.setNull(i++, Types.DATE);
			}
			ps.setInt(i++, bo.getUserId().intValue());
			ps.setInt(i, bo.getBuildingOutageId().intValue());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}

	public void save(BuildingOutage bo) throws DAOAppException {
		String sql = "UPDATE " + AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE SET STATUS_CD=?, OUTAGE_CONFIRMED_TIME=?, " +
			"INVESTIGATION_END_TIME=?, DM_TICKET_ID=?, CLOSURE_COMMENTS=? WHERE BO_ID=?";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			int i = 1;
			if (bo.getStatusCode()==null) {
				throw new GESystemException("Empty Building Outage status code.");
			}
			ps.setInt(i++, bo.getStatusCode().intValue());
			if (bo.getOutageConfirmedTime()!=null) {
				ps.setTimestamp(i++, new java.sql.Timestamp(bo.getOutageConfirmedTime().getTime()));
			} else {
				ps.setNull(i++, Types.DATE);
			}
			if (bo.getInvestigationEndTime()!=null) {
				ps.setTimestamp(i++, new java.sql.Timestamp(bo.getInvestigationEndTime().getTime()));
			} else {
				ps.setNull(i++, Types.DATE);
			}
			if (bo.getDmTicketId()!=null) {
				ps.setString(i++, bo.getDmTicketId());
			} else {
				ps.setNull(i++, Types.VARCHAR);
			}
			if (bo.getClosureComments()!=null) {
				ps.setString(i++, bo.getClosureComments());
			} else {
				ps.setNull(i++, Types.VARCHAR);
			}
			ps.setInt(i, bo.getBuildingOutageId().intValue());

			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}

	public int countBuildingOutages(Integer statusCode, BuildingOutageFilter filter) throws DAOAppException {
		int count = 0;
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT count(1) FROM ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("BUILDING_OUTAGE_STATUS BOS, ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("BUILDING_OUTAGE BO LEFT OUTER JOIN ");
		sb.append(AppConstants.BO_SCHEMA_NAME);
		sb.append("DM_TICKET DT ON BO.DM_TICKET_ID=DT.DM_TICKET_ID ");
		sb.append("WHERE BOS.STATUS_CD=BO.STATUS_CD");
		if (statusCode!=null) {
			sb.append(" AND BO.STATUS_CD=?");
		}
		if (filter!=null) {
			if (filter.investigationStartTimeFrom!=null) {
				sb.append(" AND BO.INVESTIGATION_START_TIME>=?");
			}
			if (filter.investigationStartTimeTo!=null) {
				sb.append(" AND BO.INVESTIGATION_START_TIME<=?");
			}
			if (filter.outageStartTimeFrom!=null) {
				sb.append(" AND BO.OUTAGE_START_TIME>=?");
			}
			if (filter.outageStartTimeTo!=null) {
				sb.append(" AND BO.OUTAGE_START_TIME<=?");
			}
			if (filter.confirmedTimeFrom!=null) {
				sb.append(" AND BO.OUTAGE_CONFIRMED_TIME>=?");
			}
			if (filter.confirmedTimeTo!=null) {
				sb.append(" AND BO.OUTAGE_CONFIRMED_TIME<=?");
			}
			if (filter.dispatchedTimeFrom!=null) {
				sb.append(" AND DT.TICKET_DISPATCHED_TIME>=?");
			}
			if (filter.dispatchedTimeTo!=null) {
				sb.append(" AND DT.TICKET_DISPATCHED_TIME<=?");
			}
			if (filter.estimatedRepairTimeFrom!=null) {
				sb.append(" AND DT.ESTIMATED_REPAIR_TIME>=?");
			}
			if (filter.estimatedRepairTimeTo!=null) {
				sb.append(" AND DT.ESTIMATED_REPAIR_TIME<=?");
			}
			if (filter.actualRepairTimeFrom!=null) {
				sb.append(" AND DT.ACTUAL_REPAIR_TIME>=?");
			}
			if (filter.actualRepairTimeTo!=null) {
				sb.append(" AND DT.ACTUAL_REPAIR_TIME<=?");
			}
			if (filter.investigationEndTimeFrom!=null) {
				sb.append(" AND BO.INVESTIGATION_END_TIME>=?");
			}
			if (filter.investigationEndTimeTo!=null) {
				sb.append(" AND BO.INVESTIGATION_END_TIME<=?");
			}
		}
		String sql = sb.toString();
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			int i=1;
			if (statusCode!=null) {
				ps.setInt(i++, statusCode.intValue());
			}
			if (filter!=null) {
				if (filter.investigationStartTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationStartTimeFrom.getTime()));
				}
				if (filter.investigationStartTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationStartTimeTo.getTime()));
				}
				if (filter.outageStartTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.outageStartTimeFrom.getTime()));
				}
				if (filter.outageStartTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.outageStartTimeTo.getTime()));
				}
				if (filter.confirmedTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.confirmedTimeFrom.getTime()));
				}
				if (filter.confirmedTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.confirmedTimeTo.getTime()));
				}
				if (filter.dispatchedTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.dispatchedTimeFrom.getTime()));
				}
				if (filter.dispatchedTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.dispatchedTimeTo.getTime()));
				}
				if (filter.estimatedRepairTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.estimatedRepairTimeFrom.getTime()));
				}
				if (filter.estimatedRepairTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.estimatedRepairTimeTo.getTime()));
				}
				if (filter.actualRepairTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.actualRepairTimeFrom.getTime()));
				}
				if (filter.actualRepairTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.actualRepairTimeTo.getTime()));
				}
				if (filter.investigationEndTimeFrom!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationEndTimeFrom.getTime()));
				}
				if (filter.investigationEndTimeTo!=null) {
					ps.setTimestamp(i++, new java.sql.Timestamp(filter.investigationEndTimeTo.getTime()));
				}
			}
			rs = ps.executeQuery();
			if(rs.next()) {
				count=rs.getInt(1);
			}
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
		return count;
	}
/*
	public void setLockByUser(BuildingOutage bo, User user) throws DAOAppException {
		String sql = "UPDATE " + AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE SET USER_ID=?, LOCKED_BY_USER_DT=SYSDATE WHERE BO_ID=?";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, user.getUserId().intValue());
			ps.setInt(2, bo.getBuildingOutageId().intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}
*/
    /*
    public void setLock(BuildingOutage bo) throws DAOAppException {
		String sql = "UPDATE " + AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE SET USER_ID=?, LOCKED_BY_USER_DT=SYSDATE WHERE BO_ID=?";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bo.getUserId().intValue());
			ps.setInt(2, bo.getBuildingOutageId().intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}
	public void removeLock(BuildingOutage bo) throws DAOAppException {
		String sql = "UPDATE " + AppConstants.BO_SCHEMA_NAME +
			"BUILDING_OUTAGE SET USER_ID=NULL, LOCKED_BY_USER_DT=NULL WHERE BO_ID=?";
		PreparedStatement ps = null;
		ResultSet rs=null;
		Connection connection = oraDAOFactory.getConnection();
		try {
			ps = connection.prepareStatement(sql);
			ps.setInt(1, bo.getBuildingOutageId().intValue());
			ps.executeUpdate();
		} catch (SQLException e) {
			throw new DAOSysException(e.getMessage());
		} finally {
			finalize(rs, ps);
		}
	}
	*/
}