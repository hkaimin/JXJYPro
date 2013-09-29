package com.htsoft.est.action.document;
/*
 *  广州宏天软件有限公司 EST-BPM管理平台   --  http://www.jee-soft.cn
 *  Copyright (C) 2008-2011 GuangZhou HongTian Software Company
*/
import javax.annotation.Resource;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.FileAttach;
import com.htsoft.est.service.system.FileAttachService;

public class FileDetailAction extends BaseAction{
	@Resource
	private FileAttachService fileAttachService;
	
	private FileAttach fileAttach;
	private Long fileId;

	public Long getFileId() {
		return fileId;
	}

	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}

	public FileAttach getFileAttach() {
		return fileAttach;
	}

	public void setFileAttach(FileAttach fileAttach) {
		this.fileAttach = fileAttach;
	}

	@Override
	public String execute() throws Exception {
		fileAttach=fileAttachService.get(fileId);
		return SUCCESS;
	}
}
