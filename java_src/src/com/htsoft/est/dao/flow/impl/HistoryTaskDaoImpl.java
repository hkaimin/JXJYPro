package com.htsoft.est.dao.flow.impl;
import java.util.List;

import org.jbpm.pvm.internal.history.model.HistoryTaskImpl;
import org.jbpm.pvm.internal.history.model.HistoryTaskInstanceImpl;

import com.htsoft.core.dao.impl.BaseDaoImpl;
import com.htsoft.est.dao.flow.HistoryTaskDao;

public class HistoryTaskDaoImpl extends BaseDaoImpl<HistoryTaskInstanceImpl> implements HistoryTaskDao {
	
	public HistoryTaskDaoImpl() {
		super(HistoryTaskImpl.class);
	}
	
	public List<HistoryTaskInstanceImpl> getByPiIdAssigneeOutcome(String piId,String assignee,String activityName,String outcome){
		String hql="from HistoryTaskInstanceImpl hti where hti.executionId=? and hti.historyTask.assignee=? and hti.activityName=? and hti.transitionName=?";
		return findByHql(hql, new Object[]{piId,assignee,activityName,outcome});
	}

}
