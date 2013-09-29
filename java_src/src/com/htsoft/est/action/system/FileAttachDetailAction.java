package com.htsoft.est.action.system;

import javax.annotation.Resource;

import com.htsoft.core.web.action.BaseAction;
import com.htsoft.est.model.system.FileAttach;
import com.htsoft.est.service.system.FileAttachService;

/**
 * @description 图片详细信息展示
 * @author : YHZ
 * @company : www.jee-soft.cn
 * @datetime 2010-11-23AM
 */
public class FileAttachDetailAction extends BaseAction {

	@Resource
	private FileAttachService fileAttachService;

	private Long fileId;
	private FileAttach fileAttach;

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
		fileAttach = fileAttachService.get(fileId);
		return SUCCESS;
	}
}
