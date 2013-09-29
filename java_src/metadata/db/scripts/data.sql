insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (1,'goodsStockUser','用户帐号','当库存产生警报时，接收消息的人员','行政管理配置',1,'admin','adminConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (2,'codeConfig','验证码','登录时是否需要验证码','验证码配置',2,'1','codeConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (3,'smsMobile','手机短信','流程执行时是否需要短信提醒','手机短信提醒',2,'1','smsConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (4,'deviceName','设备名称','GMS设备名称','手机短信提醒',1,'COM4','smsConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (5,'baudRate','设备波特率','设备波特率','手机短信提醒',1,'9600','smsConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (6,'suggestId','意见接收人ID','意见接收人ID','意见箱配置',1,'1','suggestConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (7,'suggestName','意见接收人','意见接收人','意见箱配置',1,'管理员','suggestConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (8,'dynamicPwd','动态密码','动态密码','动态密码配置',2,'2','dynamicPwdConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (9,'dynamicUri','验证路径','验证路径','动态密码配置',1,'http://www.yoo-e.com/cbsite/authsys/api/','dynamicPwdConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (10,'smsPortUri','端口路径','商品路径','短信端口配置',1,'http://58.63.224.34:8000/smsweb/services/sms','smsPortConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (11,'smsPortUserID','用户ID','用户ID','短信端口配置',1,'','smsPortConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (12,'smsPortAccount','用户账号','用户账号','短信端口配置',1,'','smsPortConfig');

insert into sys_config (CONFIGID, CONFIGKEY, CONFIGNAME, CONFIGDESC, TYPENAME, DATATYPE, DATAVALUE,TYPEKEY)
values (13,'smsPortPwd','用户密码','用户密码','短信端口配置',1,'','smsPortConfig');

--insert into department (DEPID, DEPNAME, DEPDESC, DEPLEVEL, PARENTID, PATH, PHONE, FAX)
--values (1, '信息部门', '维护系统', 2, 0, '0.1.', '', '');

INSERT INTO app_user (USERID,USERNAME,TITLE,PASSWORD,EMAIL,DEPID,JOBID,PHONE,MOBILE,FAX,ADDRESS,ZIP,PHOTO,ACCESSIONTIME,STATUS,EDUCATION,FULLNAME,DELFLAG,ORG_ID,ORG_PATH)  
VALUES (1,'admin',1,'a4ayc/80/OGda4BO/1o/V0etpOqiLx1JwB5S3beHW0s=','csx@jee-soft.cn',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,sysdate,1,NULL,'超级管理员',0,null,null);

INSERT INTO app_user (USERID,USERNAME,TITLE,PASSWORD,EMAIL,DEPID,JOBID,PHONE,MOBILE,FAX,ADDRESS,ZIP,PHOTO,ACCESSIONTIME,STATUS,EDUCATION,FULLNAME,DELFLAG,ORG_ID,ORG_PATH) 
VALUES (-1,'system',1,'0','152@163.com',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,to_date('2009-12-18','yyyy-mm-dd'),0,NULL,'系统',1,null,null);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (1, null, '收件箱', 0, 1, '0.1.', 1, 1);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (2, null, '发件箱', 0, 1, '0.2.', 1, 2);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (3, null, '草稿箱', 0, 1, '0.3.', 1, 3);

insert into mail_folder (FOLDERID, USERID, FOLDERNAME, PARENTID, DEPLEVEL, PATH, ISPUBLIC, FOLDERTYPE)
values (4, null, '垃圾箱', 0, 1, '0.4.', 1, 4);


insert into app_role (ROLEID, ROLENAME, ROLEDESC, STATUS, RIGHTS, ISDEFAULTIN)
values (-1, '超级管理员', '超级管理员,具有所有权限', 1, '__ALL', 0);

insert into user_role (roleId,userId) values(-1,1);

insert into out_mail_folder (folderId,folderName,parentId,depLevel,path,folderType) values(1,'收件箱',0,1,'0.1',1);

insert into out_mail_folder (folderId,folderName,parentId,depLevel,path,folderType) values(2,'发件箱',0,1,'0.2',2);

insert into out_mail_folder (folderId,folderName,parentId,depLevel,path,folderType) values(3,'草稿箱',0,1,'0.3',3);

insert into out_mail_folder (folderId,folderName,parentId,depLevel,path,folderType) values(4,'垃圾箱',0,1,'0.4',4);

/*初始给流程设计的通用表单，方便流程若没有设置流程表单，也可以正常运行*/
insert into form_def (FORMDEFID, FORMTITLE, FORMDESP, DEFHTML, STATUS, FORMTYPE, ISDEFAULT, ISGEN) 
values (1, '通用流程表单', '通用流程表单', '<table class="form-info" cellspacing="1" cellpadding="0" style="width: 878px; height: 478px"><tbody><tr class="tr-info"><td class="td-info" colspan="4" style="text-align: center"><h2>申请事项</h2></td></tr><tr class="tr-info"><td class="td-info" width="20%"><strong>事项标题</strong></td><td class="td-info"><input class="x-form-text x-form-field" width="350" xtype="textfield" isnew="isnew" txtlabel="事项标题" txtvaluetype="varchar" txtisnotnull="1" txtisprimary="0" txtsize="256" style="width: 350px" name="itemSubject" type="text" /></td><td class="td-info" width="10%"><strong>创建时间</strong></td><td class="td-info" width="10%"><input xtype="datefield" txtlabel="创建时间" txtvaluetype="date" txtisnotnull="0" __cfckdate="__cfckdate" txtistoday="1" dateformat="yyyy-MM-dd" name="createtime" type="text" /></td></tr><tr class="tr-info"><td class="td-info"><strong>事项描述</strong></td><td class="td-info" colspan="3"><textarea class="x-form-textarea x-form-field x-column" xtype="fckeditor" txtlabel="事项描述" txtvaluetype="text" txtwidth="600" txtheight="350" isfck="true" style="width: 600px; height: 350px" name="itemDescp"></textarea></td></tr></tbody></table>', 1, 0, 1, 1);

insert into form_table (TABLEID, FORMDEFID, TABLENAME, TABLEKEY, ISMAIN) 
values (1, 1, '通用表单', 'general', 1);

insert into form_field (FIELDID, TABLEID, FIELDNAME, FIELDLABEL, FIELDTYPE, ISREQUIRED, FIELDSIZE, FIELDDSCP, ISPRIMARY, FOREIGNKEY, FOREIGNTABLE, ISLIST, ISQUERY, SHOWFORMAT, ISFLOWTITLE, ISDESIGNSHOW)
values (1, 1, 'entityId', 'ID', 'bigint', 0, null, '', 1, '', '', 1, 1, '', 0, 3);

insert into form_field (FIELDID, TABLEID, FIELDNAME, FIELDLABEL, FIELDTYPE, ISREQUIRED, FIELDSIZE, FIELDDSCP, ISPRIMARY, FOREIGNKEY, FOREIGNTABLE, ISLIST, ISQUERY, SHOWFORMAT, ISFLOWTITLE, ISDESIGNSHOW)
values (2, 1, 'itemDescp', '事项描述', 'text', 0, 4000, '', 0, '', '', 1, 1, '', 0, 1);

insert into form_field (FIELDID, TABLEID, FIELDNAME, FIELDLABEL, FIELDTYPE, ISREQUIRED, FIELDSIZE, FIELDDSCP, ISPRIMARY, FOREIGNKEY, FOREIGNTABLE, ISLIST, ISQUERY, SHOWFORMAT, ISFLOWTITLE, ISDESIGNSHOW)
values (3, 1, 'createtime', '创建时间', 'date', 0, null, '', 0, '', '', 1, 1, 'yyyy-MM-dd', 0, 1);

insert into form_field (FIELDID, TABLEID, FIELDNAME, FIELDLABEL, FIELDTYPE, ISREQUIRED, FIELDSIZE, FIELDDSCP, ISPRIMARY, FOREIGNKEY, FOREIGNTABLE, ISLIST, ISQUERY, SHOWFORMAT, ISFLOWTITLE, ISDESIGNSHOW)
values (4, 1, 'itemSubject', '事项标题', 'varchar', 1, 256, '', 0, '', '', 1, 1, '', 1, 1);

insert into type_key (typekeyId,typekey,typename,sn)values(1,'REGULATION','规章制度分类',1);
insert into type_key (typekeyId,typekey,typename,sn)values(4,'DIC','数据字典分类',2);
insert into type_key (typekeyId,typekey,typename,sn)values(6,'FLOW','流程分类',3);
insert into type_key (typekeyId,typekey,typename,sn)values(12,'ATTACHFILE_TYPE','附件分类',4);

-- 添加维度
insert into demension values(1,'行政维度','行政维度',1);
insert into demension values(2,'项目维度','项目维度',2);

-- 添加组织
Insert into ORGANIZATION (ORG_ID,DEM_ID,ORG_NAME,ORG_DESC,ORG_SUP_ID,PATH,DEPTH,ORG_TYPE,CREATOR_ID,CREATETIME,UPDATE_ID,UPDATETIME) values (1,1,'宏天软件','宏天软件',0,'0.1.',1,1,1,to_timestamp('29-8月 -11','DD-MON-RR HH.MI.SSXFF AM'),1,to_timestamp('29-8月 -11','DD-MON-RR HH.MI.SSXFF AM'));