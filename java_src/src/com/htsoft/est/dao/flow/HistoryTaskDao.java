package com.htsoft.est.dao.flow;

import java.util.List;

import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;
import com.htsoft.core.dao.BaseDao;

public interface HistoryTaskDao extends BaseDao<HistoryTaskInstanceImpl>{
	public List<HistoryTaskInstanceImpl> getByPiIdAssigneeOutcome(String piId,String assignee,String activityName,String outcome);
}
