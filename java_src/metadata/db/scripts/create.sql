
/*==============================================================*/
/* Table: APP_FUNCTION                                          */
/*==============================================================*/
CREATE TABLE APP_FUNCTION  (
   FUNCTIONID           NUMBER(18)                      NOT NULL,
   FUNKEY               VARCHAR2(64)                    NOT NULL,
   FUNNAME              VARCHAR2(128)                   NOT NULL,
   CONSTRAINT PK_APP_FUNCTION PRIMARY KEY (FUNCTIONID),
   CONSTRAINT AK_UQ_RSKEY_APP_FUNC UNIQUE (FUNKEY)
);

COMMENT ON COLUMN APP_FUNCTION.FUNKEY IS
'权限Key';

COMMENT ON COLUMN APP_FUNCTION.FUNNAME IS
'权限名称';

/*==============================================================*/
/* Table: APP_ROLE                                              */
/*==============================================================*/
CREATE TABLE APP_ROLE  (
   ROLEID               NUMBER(18)                      NOT NULL,
   ROLENAME             VARCHAR2(128)                   NOT NULL,
   ROLEDESC             VARCHAR2(128),
   STATUS               SMALLINT                        NOT NULL,
   RIGHTS               CLOB,
   ISDEFAULTIN          SMALLINT                        NOT NULL,
   ORG_ID               NUMBER(18),
   ORG_PATH             VARCHAR2(256 BYTE),
   CONSTRAINT PK_APP_ROLE PRIMARY KEY (ROLEID)
);

COMMENT ON TABLE APP_ROLE IS
'角色表';

COMMENT ON COLUMN APP_ROLE.ROLENAME IS
'角色名称';

COMMENT ON COLUMN APP_ROLE.ROLEDESC IS
'角色描述';

COMMENT ON COLUMN APP_ROLE.STATUS IS
'状态';

/*==============================================================*/
/* Table: APP_TIPS                                              */
/*==============================================================*/
CREATE TABLE APP_TIPS  (
   TIPSID               NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18)                      NOT NULL,
   TIPSNAME             VARCHAR2(128),
   CONTENT              VARCHAR2(2048),
   DISHEIGHT            NUMBER(18),
   DISWIDTH             NUMBER(18),
   DISLEFT              NUMBER(18),
   DISTOP               NUMBER(18),
   DISLEVEL             NUMBER(18),
   CREATETIME           DATE                            NOT NULL,
   CONSTRAINT PK_APP_TIPS PRIMARY KEY (TIPSID)
);

COMMENT ON TABLE APP_TIPS IS
'用户便签';

/*==============================================================*/
/* Table: APP_USER                                              */
/*==============================================================*/
CREATE TABLE APP_USER  (
   USERID               NUMBER(18)                      NOT NULL,
   USERNAME             VARCHAR2(128)                   NOT NULL,
   TITLE                SMALLINT                        NOT NULL,
   DEPID                NUMBER(18),
   PASSWORD             VARCHAR2(128)                   NOT NULL,
   EMAIL                VARCHAR2(128)                   NOT NULL,
   JOBID                NUMBER(18),
   PHONE                VARCHAR2(32),
   MOBILE               VARCHAR2(32),
   FAX                  VARCHAR2(32),
   ADDRESS              VARCHAR2(64),
   ZIP                  VARCHAR2(32),
   PHOTO                VARCHAR2(128),
   ACCESSIONTIME        DATE                            NOT NULL,
   STATUS               SMALLINT                        NOT NULL,
   EDUCATION            VARCHAR2(64),
   FULLNAME             VARCHAR2(50)                    NOT NULL,
   DELFLAG              SMALLINT                        NOT NULL,
   ORG_ID               NUMBER(18),
   ORG_PATH             VARCHAR2(256 BYTE),
   CONSTRAINT PK_APP_USER PRIMARY KEY (USERID)
);

COMMENT ON TABLE APP_USER IS
'app_user
用户表';

COMMENT ON COLUMN APP_USER.USERID IS
'主键';

COMMENT ON COLUMN APP_USER.USERNAME IS
'用户名';

COMMENT ON COLUMN APP_USER.TITLE IS
'1=先生
0=女士
小姐';

COMMENT ON COLUMN APP_USER.PASSWORD IS
'密码';

COMMENT ON COLUMN APP_USER.EMAIL IS
'邮件';

COMMENT ON COLUMN APP_USER.JOBID IS
'职位';

COMMENT ON COLUMN APP_USER.PHONE IS
'电话';

COMMENT ON COLUMN APP_USER.MOBILE IS
'手机';

COMMENT ON COLUMN APP_USER.FAX IS
'传真';

COMMENT ON COLUMN APP_USER.ADDRESS IS
'地址';

COMMENT ON COLUMN APP_USER.ZIP IS
'邮编';

COMMENT ON COLUMN APP_USER.PHOTO IS
'相片';

COMMENT ON COLUMN APP_USER.ACCESSIONTIME IS
'入职时间';

COMMENT ON COLUMN APP_USER.STATUS IS
'状态
1=激活
0=禁用
2=离职
';

COMMENT ON COLUMN APP_USER.DELFLAG IS
'0=未删除
1=删除';

/*==============================================================*/
/* Table: COMPANY                                               */
/*==============================================================*/
CREATE TABLE COMPANY  (
   COMPANYID            NUMBER(18)                      NOT NULL,
   COMPANYNO            VARCHAR2(128),
   COMPANYNAME          VARCHAR2(128)                   NOT NULL,
   COMPANYDESC          VARCHAR2(4000),
   LEGALPERSON          VARCHAR2(32),
   SETUP                DATE,
   PHONE                VARCHAR2(32),
   FAX                  VARCHAR2(32),
   SITE                 VARCHAR2(128),
   LOGO                 VARCHAR2(128),
   CONSTRAINT PK_COMPANY PRIMARY KEY (COMPANYID)
);

COMMENT ON TABLE COMPANY IS
'公司信息';

/*==============================================================*/
/* Table: DEMENSION                                             */
/*==============================================================*/
CREATE TABLE DEMENSION  (
   DEM_ID               NUMBER(18)                      NOT NULL,
   DEM_NAME             VARCHAR2(128)                   NOT NULL,
   DEM_DESC             VARCHAR2(1024),
   DEM_TYPE             SMALLINT                        NOT NULL,
   CONSTRAINT PK_DEMENSION PRIMARY KEY (DEM_ID)
);

COMMENT ON COLUMN DEMENSION.DEM_NAME IS
'维度名称';

COMMENT ON COLUMN DEMENSION.DEM_DESC IS
'维度描述';

COMMENT ON COLUMN DEMENSION.DEM_TYPE IS
'类型
1=行政(为公司的整个组织结构，用户所属的部门即隶属于该维度)
2=其他
';

/*==============================================================*/
/* Table: DICTIONARY                                            */
/*==============================================================*/
CREATE TABLE DICTIONARY  (
   DICID                NUMBER(18)                      NOT NULL,
   PROTYPEID            NUMBER(18),
   ITEMNAME             VARCHAR2(64)                    NOT NULL,
   ITEMVALUE            VARCHAR2(128)                   NOT NULL,
   DESCP                VARCHAR2(256),
   SN                   NUMBER(18),
   CONSTRAINT PK_DICTIONARY PRIMARY KEY (DICID)
);

COMMENT ON TABLE DICTIONARY IS
'数据字典';

/*==============================================================*/
/* Table: DOC_FILE                                              */
/*==============================================================*/
CREATE TABLE DOC_FILE  (
   FILEID               NUMBER(18)                      NOT NULL,
   DOCID                NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_DOC_FILE PRIMARY KEY (FILEID, DOCID)
);

/*==============================================================*/
/* Table: DOC_FOLDER                                            */
/*==============================================================*/
CREATE TABLE DOC_FOLDER  (
   FOLDERID             NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   FOLDERNAME           VARCHAR2(128)                   NOT NULL,
   PARENTID             NUMBER(18),
   PATH                 VARCHAR2(128),
   ISSHARED             SMALLINT                        NOT NULL,
   DESCP                VARCHAR2(256),
   CONSTRAINT PK_DOC_FOLDER PRIMARY KEY (FOLDERID)
);

COMMENT ON COLUMN DOC_FOLDER.USERID IS
'主键';

COMMENT ON COLUMN DOC_FOLDER.FOLDERNAME IS
'目录名称';

COMMENT ON COLUMN DOC_FOLDER.PARENTID IS
'父目录';

COMMENT ON COLUMN DOC_FOLDER.PATH IS
'路径
为当前路径的＋上级路径
如当前ID为3，上级目录的路径为1.2，
则当前的路径为1.2.3.';

/*==============================================================*/
/* Table: DOC_PRIVILEGE                                         */
/*==============================================================*/
CREATE TABLE DOC_PRIVILEGE  (
   PRIVILEGEID          NUMBER(18)                      NOT NULL,
   FOLDERID             NUMBER(18),
   DOCID                NUMBER(18),
   RIGHTS               NUMBER(18)                      NOT NULL,
   UDRID                NUMBER(18),
   UDRNAME              VARCHAR2(128),
   FLAG                 SMALLINT                        NOT NULL,
   FDFLAG               SMALLINT                        NOT NULL,
   CONSTRAINT PK_DOC_PRIVILEGE PRIMARY KEY (PRIVILEGEID)
);

COMMENT ON TABLE DOC_PRIVILEGE IS
'文档或目录的权限，只要是针对公共目录下的文档，个人的文档则不需要在这里进行权限的设置

某个目录或文档若没有指定某部门或某个人，即在本表中没有记录，
则表示可以进行所有的操作';

COMMENT ON COLUMN DOC_PRIVILEGE.RIGHTS IS
'权限
文档或目录的读写修改权限
1=读
2=修改
4=删除

权限值可以为上面的值之和
如：3则代表进行读，修改的操作


';

COMMENT ON COLUMN DOC_PRIVILEGE.FLAG IS
'1=user
2=deparment
3=role';

COMMENT ON COLUMN DOC_PRIVILEGE.FDFLAG IS
'缺省为文件夹权限
1=文档权限
0=文件夹权限';

/*==============================================================*/
/* Table: DOCUMENT                                              */
/*==============================================================*/
CREATE TABLE DOCUMENT  (
   DOCID                NUMBER(18)                      NOT NULL,
   DOCNAME              VARCHAR2(100)                   NOT NULL,
   CONTENT              CLOB,
   CREATETIME           DATE                            NOT NULL,
   UPDATETIME           DATE,
   FOLDERID             NUMBER(18),
   USERID               NUMBER(18),
   FULLNAME             VARCHAR2(32)                    NOT NULL,
   HAVEATTACH           SMALLINT,
   SHAREDUSERIDS        VARCHAR2(1000),
   SHAREDUSERNAMES      VARCHAR2(1000),
   SHAREDDEPIDS         VARCHAR2(1000),
   SHAREDDEPNAMES       VARCHAR2(1000),
   SHAREDROLEIDS        VARCHAR2(1000),
   SHAREDROLENAMES      VARCHAR2(1000),
   ISSHARED             SMALLINT                        NOT NULL,
   AUTHOR               VARCHAR2(64),
   KEYWORDS             VARCHAR2(256),
   DOCTYPE              VARCHAR2(64),
   SWFPATH              VARCHAR2(256),
   CONSTRAINT PK_DOCUMENT PRIMARY KEY (DOCID)
);

COMMENT ON TABLE DOCUMENT IS
'文档';

COMMENT ON COLUMN DOCUMENT.CONTENT IS
'内容';

COMMENT ON COLUMN DOCUMENT.CREATETIME IS
'创建时间';

COMMENT ON COLUMN DOCUMENT.USERID IS
'主键';

COMMENT ON COLUMN DOCUMENT.SHAREDUSERIDS IS
'共享员工ID';

COMMENT ON COLUMN DOCUMENT.SHAREDDEPIDS IS
'共享部门ID';

COMMENT ON COLUMN DOCUMENT.SHAREDROLEIDS IS
'共享角色ID';

COMMENT ON COLUMN DOCUMENT.ISSHARED IS
'是否共享';

/*==============================================================*/
/* Table: DOWNLOAD_LOG                                          */
/*==============================================================*/
CREATE TABLE DOWNLOAD_LOG  (
   LOGID                NUMBER(18)                      NOT NULL,
   USERNAME             VARCHAR2(64)                    NOT NULL,
   USERID               NUMBER(18)                      NOT NULL,
   FILEID               NUMBER(18)                      NOT NULL,
   DOWNLOADTIME         DATE                            NOT NULL,
   NOTES                VARCHAR2(1024),
   CONSTRAINT PK_DOWNLOAD_LOG PRIMARY KEY (LOGID)
);

/*==============================================================*/
/* Table: FIELD_RIGHTS                                          */
/*==============================================================*/
CREATE TABLE FIELD_RIGHTS  (
   RIGHTID              NUMBER(18)                      NOT NULL,
   MAPPINGID            NUMBER(18)                      NOT NULL,
   FIELDID              NUMBER(18)                      NOT NULL,
   TASKNAME             VARCHAR2(128)                   NOT NULL,
   READWRITE            SMALLINT                       DEFAULT 0 NOT NULL,
   CONSTRAINT PK_FIELD_RIGHTS PRIMARY KEY (RIGHTID)
);

COMMENT ON COLUMN FIELD_RIGHTS.READWRITE IS
'隐藏读写权限
0=隐藏
1=读
2=写';

/*==============================================================*/
/* Table: FILE_ATTACH                                           */
/*==============================================================*/
CREATE TABLE FILE_ATTACH  (
   FILEID               NUMBER(18)                      NOT NULL,
   PROTYPEID            NUMBER(18),
   FILENAME             VARCHAR2(128)                   NOT NULL,
   FILEPATH             VARCHAR2(128)                   NOT NULL,
   CREATETIME           DATE                            NOT NULL,
   EXT                  VARCHAR2(32),
   FILETYPE             VARCHAR2(32)                    NOT NULL,
   NOTE                 VARCHAR2(1024),
   CREATORID            NUMBER(18),
   CREATOR              VARCHAR2(32)                    NOT NULL,
   TOTALBYTES           NUMBER(18)                     DEFAULT 0,
   DELFLAG              SMALLINT,
   CONSTRAINT PK_FILE_ATTACH PRIMARY KEY (FILEID)
);

COMMENT ON TABLE FILE_ATTACH IS
'附件';

COMMENT ON COLUMN FILE_ATTACH.FILENAME IS
'文件名';

COMMENT ON COLUMN FILE_ATTACH.FILEPATH IS
'文件路径';

COMMENT ON COLUMN FILE_ATTACH.CREATETIME IS
'创建时间';

COMMENT ON COLUMN FILE_ATTACH.EXT IS
'扩展名';

COMMENT ON COLUMN FILE_ATTACH.FILETYPE IS
'附件类型
如：邮件附件';

COMMENT ON COLUMN FILE_ATTACH.NOTE IS
'说明';

COMMENT ON COLUMN FILE_ATTACH.CREATOR IS
'上传者';

COMMENT ON COLUMN FILE_ATTACH.DELFLAG IS
'1=已删除
0=删除';

/*==============================================================*/
/* Table: FORM_DEF                                              */
/*==============================================================*/
CREATE TABLE FORM_DEF  (
   FORMDEFID            NUMBER(18)                      NOT NULL,
   FORMTITLE            VARCHAR2(128)                   NOT NULL,
   FORMDESP             CLOB,
   DEFHTML              CLOB,
   STATUS               SMALLINT                        NOT NULL,
   FORMTYPE             SMALLINT,
   ISDEFAULT            SMALLINT,
   ISGEN                SMALLINT                       DEFAULT 0,
   CONSTRAINT PK_FORM_DEF PRIMARY KEY (FORMDEFID),
   CONSTRAINT AK_FD_FORMNAME_FORM_DEF UNIQUE (FORMTITLE)
);

COMMENT ON COLUMN FORM_DEF.FORMDEFID IS
'表单ID';

COMMENT ON COLUMN FORM_DEF.FORMTITLE IS
'表单标题';

COMMENT ON COLUMN FORM_DEF.STATUS IS
'0=草稿状态
1=正式状态';

COMMENT ON COLUMN FORM_DEF.FORMTYPE IS
'表单类型
1=单表
2=主从表
3=多表';

COMMENT ON COLUMN FORM_DEF.ISDEFAULT IS
'是否缺省';

COMMENT ON COLUMN FORM_DEF.ISGEN IS
'1=已生成
0=未生成';

/*==============================================================*/
/* Table: FORM_DEF_MAPPING                                      */
/*==============================================================*/
CREATE TABLE FORM_DEF_MAPPING  (
   MAPPINGID            NUMBER(18)                      NOT NULL,
   FORMDEFID            NUMBER(18),
   DEFID                NUMBER(18),
   VERSIONNO            NUMBER(18)                     DEFAULT 0 NOT NULL,
   DEPLOYID             VARCHAR2(128)                   NOT NULL,
   USETEMPLATE          SMALLINT                       DEFAULT 0,
   CONSTRAINT PK_FORM_DEF_MAPPING PRIMARY KEY (MAPPINGID),
   CONSTRAINT AK_UK_DEPLOYID_FORM_DEF UNIQUE (DEPLOYID)
);

COMMENT ON COLUMN FORM_DEF_MAPPING.FORMDEFID IS
'表单ID';

COMMENT ON COLUMN FORM_DEF_MAPPING.DEPLOYID IS
'发布ID';

COMMENT ON COLUMN FORM_DEF_MAPPING.USETEMPLATE IS
'1=使用模板表单
0=使用普通在线表单';

/*==============================================================*/
/* Table: FORM_FIELD                                            */
/*==============================================================*/
CREATE TABLE FORM_FIELD  (
   FIELDID              NUMBER(18)                      NOT NULL,
   TABLEID              NUMBER(18),
   FIELDNAME            VARCHAR2(128)                   NOT NULL,
   FIELDLABEL           VARCHAR2(128),
   FIELDTYPE            VARCHAR2(128)                   NOT NULL,
   ISREQUIRED           SMALLINT,
   FIELDSIZE            NUMBER(18),
   FIELDDSCP            VARCHAR2(1024),
   ISPRIMARY            SMALLINT,
   FOREIGNKEY           VARCHAR2(64),
   FOREIGNTABLE         VARCHAR2(64),
   ISLIST               SMALLINT                       DEFAULT 1,
   ISQUERY              SMALLINT                       DEFAULT 1,
   SHOWFORMAT           VARCHAR2(256),
   ISFLOWTITLE          SMALLINT,
   ISDESIGNSHOW         SMALLINT,
   CONSTRAINT PK_FORM_FIELD PRIMARY KEY (FIELDID)
);

COMMENT ON COLUMN FORM_FIELD.SHOWFORMAT IS
'显示格式
如日期显示yyyy-MM-dd
数字 如 000.00';

COMMENT ON COLUMN FORM_FIELD.ISFLOWTITLE IS
'1=是
0=否';

COMMENT ON COLUMN FORM_FIELD.ISDESIGNSHOW IS
'1=设计的可视化
2=设计的不可视化
3=手工加上';

/*==============================================================*/
/* Table: FORM_TABLE                                            */
/*==============================================================*/
CREATE TABLE FORM_TABLE  (
   TABLEID              NUMBER(18)                      NOT NULL,
   FORMDEFID            NUMBER(18),
   TABLENAME            VARCHAR2(128)                   NOT NULL,
   TABLEKEY             VARCHAR2(128)                   NOT NULL,
   ISMAIN               SMALLINT,
   CONSTRAINT PK_FORM_TABLE PRIMARY KEY (TABLEID)
);

COMMENT ON COLUMN FORM_TABLE.FORMDEFID IS
'表单ID';

/*==============================================================*/
/* Table: FORM_TEMPLATE                                         */
/*==============================================================*/
CREATE TABLE FORM_TEMPLATE  (
   TEMPLATEID           NUMBER(18)                      NOT NULL,
   MAPPINGID            NUMBER(18),
   NODENAME             VARCHAR2(128)                   NOT NULL,
   TEMPCONTENT          CLOB,
   EXTDEF               CLOB,
   FORMURL              VARCHAR2(256),
   TEMPTYPE             SMALLINT,
   CONSTRAINT PK_FORM_TEMPLATE PRIMARY KEY (TEMPLATEID)
);

COMMENT ON COLUMN FORM_TEMPLATE.MAPPINGID IS
'映射ID';

COMMENT ON COLUMN FORM_TEMPLATE.NODENAME IS
'节点名称';

COMMENT ON COLUMN FORM_TEMPLATE.TEMPCONTENT IS
'模板内容';

COMMENT ON COLUMN FORM_TEMPLATE.TEMPTYPE IS
'1=EXT模板
2=URL模板';

/*==============================================================*/
/* Table: FUN_URL                                               */
/*==============================================================*/
CREATE TABLE FUN_URL  (
   URLID                NUMBER(18)                      NOT NULL,
   FUNCTIONID           NUMBER(18)                      NOT NULL,
   URLPATH              VARCHAR2(128)                   NOT NULL,
   CONSTRAINT PK_FUN_URL PRIMARY KEY (URLID)
);

/*==============================================================*/
/* Table: GLOBAL_TYPE                                           */
/*==============================================================*/
CREATE TABLE GLOBAL_TYPE  (
   PROTYPEID            NUMBER(18)                      NOT NULL,
   TYPENAME             VARCHAR2(128)                   NOT NULL,
   PATH                 VARCHAR2(64),
   DEPTH                NUMBER(18)                      NOT NULL,
   PARENTID             NUMBER(18),
   NODEKEY              VARCHAR2(64)                    NOT NULL,
   CATKEY               VARCHAR2(64)                    NOT NULL,
   SN                   NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   DEPID                NUMBER(18),
   CONSTRAINT PK_GLOBAL_TYPE PRIMARY KEY (PROTYPEID)
);

COMMENT ON TABLE GLOBAL_TYPE IS
'总分类表

用于显示树层次结构的分类
可以允许任何层次结构';

COMMENT ON COLUMN GLOBAL_TYPE.TYPENAME IS
'名称';

COMMENT ON COLUMN GLOBAL_TYPE.PATH IS
'路径';

COMMENT ON COLUMN GLOBAL_TYPE.DEPTH IS
'层次';

COMMENT ON COLUMN GLOBAL_TYPE.PARENTID IS
'父节点';

COMMENT ON COLUMN GLOBAL_TYPE.NODEKEY IS
'节点的分类Key';

COMMENT ON COLUMN GLOBAL_TYPE.CATKEY IS
'节点分类的Key，如产品分类Key为PT';

COMMENT ON COLUMN GLOBAL_TYPE.SN IS
'序号';

COMMENT ON COLUMN GLOBAL_TYPE.USERID IS
'所属用户
当为空则代表为公共分类';

/*==============================================================*/
/* Table: IN_MESSAGE                                            */
/*==============================================================*/
CREATE TABLE IN_MESSAGE  (
   RECEIVEID            NUMBER(18)                      NOT NULL,
   MESSAGEID            NUMBER(18),
   USERID               NUMBER(18),
   READFLAG             SMALLINT                        NOT NULL,
   DELFLAG              SMALLINT                        NOT NULL,
   USERFULLNAME         VARCHAR2(32)                    NOT NULL,
   CONSTRAINT PK_IN_MESSAGE PRIMARY KEY (RECEIVEID)
);

COMMENT ON COLUMN IN_MESSAGE.USERID IS
'主键';

COMMENT ON COLUMN IN_MESSAGE.READFLAG IS
'1=has red
0=unread';

/*==============================================================*/
/* Table: INDEX_DISPLAY                                         */
/*==============================================================*/
CREATE TABLE INDEX_DISPLAY  (
   INDEXID              NUMBER(18)                      NOT NULL,
   PORTALID             VARCHAR2(64)                    NOT NULL,
   USERID               NUMBER(18)                      NOT NULL,
   COLNUM               NUMBER(18)                      NOT NULL,
   ROWNO                NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_INDEX_DISPLAY PRIMARY KEY (INDEXID)
);

COMMENT ON TABLE INDEX_DISPLAY IS
'每个员工可以设置自己的显示方式';

COMMENT ON COLUMN INDEX_DISPLAY.PORTALID IS
'Portal ID';

COMMENT ON COLUMN INDEX_DISPLAY.USERID IS
'用户ID';

COMMENT ON COLUMN INDEX_DISPLAY.COLNUM IS
'列号';

COMMENT ON COLUMN INDEX_DISPLAY.ROWNO IS
'行号';

/*==============================================================*/
/* Table: MAIL                                                  */
/*==============================================================*/
CREATE TABLE MAIL  (
   MAILID               NUMBER(18)                      NOT NULL,
   SENDER               VARCHAR2(32)                    NOT NULL,
   SENDERID             NUMBER(18)                      NOT NULL,
   IMPORTANTFLAG        SMALLINT                        NOT NULL,
   SENDTIME             DATE                            NOT NULL,
   CONTENT              CLOB                            NOT NULL,
   SUBJECT              VARCHAR2(256)                   NOT NULL,
   COPYTONAMES          VARCHAR2(2000),
   COPYTOIDS            VARCHAR2(2000),
   RECIPIENTNAMES       VARCHAR2(2000)                  NOT NULL,
   RECIPIENTIDS         VARCHAR2(2000)                  NOT NULL,
   MAILSTATUS           SMALLINT                        NOT NULL,
   FILEIDS              VARCHAR2(500),
   FILENAMES            VARCHAR2(500),
   CONSTRAINT PK_MAIL PRIMARY KEY (MAILID)
);

COMMENT ON TABLE MAIL IS
'邮件';

COMMENT ON COLUMN MAIL.IMPORTANTFLAG IS
'1=一般
2=重要
3=非常重要';

COMMENT ON COLUMN MAIL.CONTENT IS
'邮件内容';

COMMENT ON COLUMN MAIL.SUBJECT IS
'邮件标题';

COMMENT ON COLUMN MAIL.COPYTONAMES IS
'抄送人姓名列表';

COMMENT ON COLUMN MAIL.COPYTOIDS IS
'抄送人ID列表
用'',''分开';

COMMENT ON COLUMN MAIL.RECIPIENTNAMES IS
'收件人姓名列表';

COMMENT ON COLUMN MAIL.RECIPIENTIDS IS
'收件人ID列表
用'',''分隔';

COMMENT ON COLUMN MAIL.MAILSTATUS IS
'邮件状态
1=正式邮件
0=草稿邮件';

COMMENT ON COLUMN MAIL.FILEIDS IS
'附件Ids，多个附件的ID，通过,分割';

COMMENT ON COLUMN MAIL.FILENAMES IS
'附件名称列表，通过,进行分割';

/*==============================================================*/
/* Table: MAIL_ATTACH                                           */
/*==============================================================*/
CREATE TABLE MAIL_ATTACH  (
   MAILID               NUMBER(18)                      NOT NULL,
   FILEID               NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_MAIL_ATTACH PRIMARY KEY (MAILID, FILEID)
);

/*==============================================================*/
/* Table: MAIL_BOX                                              */
/*==============================================================*/
CREATE TABLE MAIL_BOX  (
   BOXID                NUMBER(18)                      NOT NULL,
   MAILID               NUMBER(18)                      NOT NULL,
   FOLDERID             NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   SENDTIME             DATE                            NOT NULL,
   DELFLAG              SMALLINT                        NOT NULL,
   READFLAG             SMALLINT                        NOT NULL,
   REPLYFLAG            SMALLINT                        NOT NULL,
   NOTE                 VARCHAR2(256),
   CONSTRAINT PK_MAIL_BOX PRIMARY KEY (BOXID)
);

COMMENT ON TABLE MAIL_BOX IS
'收件箱';

COMMENT ON COLUMN MAIL_BOX.USERID IS
'主键';

COMMENT ON COLUMN MAIL_BOX.DELFLAG IS
'del=1则代表删除';

COMMENT ON COLUMN MAIL_BOX.NOTE IS
'note';

/*==============================================================*/
/* Table: MAIL_FOLDER                                           */
/*==============================================================*/
CREATE TABLE MAIL_FOLDER  (
   FOLDERID             NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   FOLDERNAME           VARCHAR2(128)                   NOT NULL,
   PARENTID             NUMBER(18),
   DEPLEVEL             NUMBER(18)                      NOT NULL,
   PATH                 VARCHAR2(256),
   ISPUBLIC             SMALLINT                        NOT NULL,
   FOLDERTYPE           SMALLINT                        NOT NULL,
   CONSTRAINT PK_MAIL_FOLDER PRIMARY KEY (FOLDERID)
);

COMMENT ON COLUMN MAIL_FOLDER.FOLDERID IS
'文件夹编号';

COMMENT ON COLUMN MAIL_FOLDER.USERID IS
'主键';

COMMENT ON COLUMN MAIL_FOLDER.FOLDERNAME IS
'文件夹名称';

COMMENT ON COLUMN MAIL_FOLDER.PARENTID IS
'父目录';

COMMENT ON COLUMN MAIL_FOLDER.DEPLEVEL IS
'目录层';

COMMENT ON COLUMN MAIL_FOLDER.ISPUBLIC IS
'1=表示共享，则所有的员工均可以使用该文件夹
0=私人文件夹';

COMMENT ON COLUMN MAIL_FOLDER.FOLDERTYPE IS
'文件夹类型
1=收信箱
2=发信箱
3=草稿箱
4=删除箱
10=其他';

/*==============================================================*/
/* Table: NEWS                                                  */
/*==============================================================*/
CREATE TABLE NEWS  (
   NEWSID               NUMBER(18)                      NOT NULL,
   SECTIONID            NUMBER(18),
   SUBJECTICON          VARCHAR2(128),
   SUBJECT              VARCHAR2(128)                   NOT NULL,
   AUTHOR               VARCHAR2(32)                    NOT NULL,
   CREATETIME           DATE                            NOT NULL,
   EXPTIME              DATE,
   REPLYCOUNTS          NUMBER(18),
   VIEWCOUNTS           NUMBER(18),
   ISSUER               VARCHAR2(32)                    NOT NULL,
   CONTENT              CLOB                            NOT NULL,
   UPDATETIME           DATE,
   STATUS               SMALLINT                        NOT NULL,
   ISDESKIMAGE          SMALLINT,
   ISNOTICE             SMALLINT,
   SN                   NUMBER(18),
   CONSTRAINT PK_NEWS PRIMARY KEY (NEWSID)
);

COMMENT ON TABLE NEWS IS
'新闻';

COMMENT ON COLUMN NEWS.NEWSID IS
'ID';

COMMENT ON COLUMN NEWS.SUBJECT IS
'新闻标题';

COMMENT ON COLUMN NEWS.AUTHOR IS
'作者';

COMMENT ON COLUMN NEWS.CREATETIME IS
'创建时间';

COMMENT ON COLUMN NEWS.VIEWCOUNTS IS
'浏览数';

COMMENT ON COLUMN NEWS.CONTENT IS
'内容';

COMMENT ON COLUMN NEWS.STATUS IS
'
0=待审核
1=审核通过';

COMMENT ON COLUMN NEWS.ISDESKIMAGE IS
'是否为桌面新闻';

/*==============================================================*/
/* Table: NEWS_COMMENT                                          */
/*==============================================================*/
CREATE TABLE NEWS_COMMENT  (
   COMMENTID            NUMBER(18)                      NOT NULL,
   CONTENT              VARCHAR2(500)                   NOT NULL,
   CREATETIME           DATE                            NOT NULL,
   FULLNAME             VARCHAR2(32)                    NOT NULL,
   USERID               NUMBER(18)                      NOT NULL,
   NEWSID               NUMBER(18),
   CONSTRAINT PK_NEWS_COMMENT PRIMARY KEY (COMMENTID)
);

COMMENT ON COLUMN NEWS_COMMENT.NEWSID IS
'ID';

/*==============================================================*/
/* Table: ORGANIZATION                                          */
/*==============================================================*/
CREATE TABLE ORGANIZATION  (
   ORG_ID               NUMBER(18)                      NOT NULL,
   DEM_ID               NUMBER(18),
   ORG_NAME             VARCHAR2(128)                   NOT NULL,
   ORG_DESC             VARCHAR2(500),
   ORG_SUP_ID           NUMBER(18),
   PATH                 VARCHAR2(128),
   DEPTH                INT,
   ORG_TYPE             SMALLINT,
   CREATOR_ID           NUMBER(18),
   CREATETIME           DATE,
   UPDATE_ID            NUMBER(18),
   UPDATETIME           DATE,
   CONSTRAINT PK_ORGANIZATION PRIMARY KEY (ORG_ID)
);

COMMENT ON COLUMN ORGANIZATION.ORG_TYPE IS
'1=公司
2=部门
3=其他组织';

/*==============================================================*/
/* Table: OUT_MAIL                                              */
/*==============================================================*/
CREATE TABLE OUT_MAIL  (
   MAILID               NUMBER(18)                      NOT NULL,
   UIDNO                VARCHAR2(512),
   USERID               NUMBER(18),
   FOLDERID             NUMBER(18),
   TITLE                VARCHAR2(512),
   CONTENT              CLOB,
   SENDERADDRESSES      VARCHAR2(128)                   NOT NULL,
   SENDERNAME           VARCHAR2(128),
   RECEIVERADDRESSES    CLOB                            NOT NULL,
   RECEIVERNAMES        CLOB,
   CCADDRESSES          CLOB,
   CCNAMES              CLOB,
   BCCADDRESSES         CLOB,
   BCCANAMES            CLOB,
   MAILDATE             DATE                            NOT NULL,
   FILEIDS              VARCHAR2(512),
   FILENAMES            VARCHAR2(512),
   READFLAG             SMALLINT                       DEFAULT 0 NOT NULL,
   REPLYFLAG            SMALLINT                       DEFAULT 0 NOT NULL,
   CONSTRAINT PK_OUT_MAIL PRIMARY KEY (MAILID)
);

COMMENT ON COLUMN OUT_MAIL.FOLDERID IS
'文件夹编号';

COMMENT ON COLUMN OUT_MAIL.TITLE IS
'主题';

COMMENT ON COLUMN OUT_MAIL.CONTENT IS
'内容';

COMMENT ON COLUMN OUT_MAIL.SENDERADDRESSES IS
'发件人地址';

COMMENT ON COLUMN OUT_MAIL.SENDERNAME IS
'发件人地址别名';

COMMENT ON COLUMN OUT_MAIL.RECEIVERADDRESSES IS
'收件人地址';

COMMENT ON COLUMN OUT_MAIL.RECEIVERNAMES IS
'收件人地址别名';

COMMENT ON COLUMN OUT_MAIL.CCADDRESSES IS
'抄送人地址';

COMMENT ON COLUMN OUT_MAIL.CCNAMES IS
'抄送人地址别名';

COMMENT ON COLUMN OUT_MAIL.BCCADDRESSES IS
'暗送人地址';

COMMENT ON COLUMN OUT_MAIL.BCCANAMES IS
'暗送人地址别名';

COMMENT ON COLUMN OUT_MAIL.READFLAG IS
'0:未阅
1:已阅';

COMMENT ON COLUMN OUT_MAIL.REPLYFLAG IS
'0:未回复
1;已回复';

/*==============================================================*/
/* Table: OUT_MAIL_FILE                                         */
/*==============================================================*/
CREATE TABLE OUT_MAIL_FILE  (
   MAILID               NUMBER(18)                      NOT NULL,
   FILEID               NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_OUT_MAIL_FILE PRIMARY KEY (MAILID, FILEID)
);

/*==============================================================*/
/* Table: OUT_MAIL_FOLDER                                       */
/*==============================================================*/
CREATE TABLE OUT_MAIL_FOLDER  (
   FOLDERID             NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   FOLDERNAME           VARCHAR2(128)                   NOT NULL,
   PARENTID             NUMBER(18),
   DEPLEVEL             NUMBER(18)                      NOT NULL,
   PATH                 VARCHAR2(256),
   FOLDERTYPE           SMALLINT                        NOT NULL,
   CONSTRAINT PK_OUT_MAIL_FOLDER PRIMARY KEY (FOLDERID)
);

COMMENT ON COLUMN OUT_MAIL_FOLDER.FOLDERID IS
'文件夹编号';

COMMENT ON COLUMN OUT_MAIL_FOLDER.USERID IS
'主键';

COMMENT ON COLUMN OUT_MAIL_FOLDER.FOLDERNAME IS
'文件夹名称';

COMMENT ON COLUMN OUT_MAIL_FOLDER.PARENTID IS
'父目录';

COMMENT ON COLUMN OUT_MAIL_FOLDER.DEPLEVEL IS
'目录层';

COMMENT ON COLUMN OUT_MAIL_FOLDER.FOLDERTYPE IS
'文件夹类型
1=收信箱
2=发信箱
3=草稿箱
4=删除箱
10=其他';

/*==============================================================*/
/* Table: OUT_MAIL_USER_SETING                                  */
/*==============================================================*/
CREATE TABLE OUT_MAIL_USER_SETING  (
   ID                   NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   USERNAME             VARCHAR2(128),
   MAILADDRESS          VARCHAR2(128)                   NOT NULL,
   MAILPASS             VARCHAR2(128)                   NOT NULL,
   SMTPHOST             VARCHAR2(128)                   NOT NULL,
   SMTPPORT             VARCHAR2(64)                    NOT NULL,
   POPHOST              VARCHAR2(128)                   NOT NULL,
   POPPORT              VARCHAR2(64)                    NOT NULL,
   CONSTRAINT PK_OUT_MAIL_USER_SETING PRIMARY KEY (ID)
);

COMMENT ON COLUMN OUT_MAIL_USER_SETING.USERID IS
'用户ID';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.USERNAME IS
'用户名称';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.MAILADDRESS IS
'外部邮件地址';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.MAILPASS IS
'外部邮件密码';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.SMTPHOST IS
'smt主机';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.SMTPPORT IS
'smt端口';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.POPHOST IS
'pop主机';

COMMENT ON COLUMN OUT_MAIL_USER_SETING.POPPORT IS
'pop端口';

/*==============================================================*/
/* Table: PAINT_TEMPLATE                                        */
/*==============================================================*/
CREATE TABLE PAINT_TEMPLATE  (
   PTEMPLATEID          NUMBER(18)                      NOT NULL,
   FILEID               NUMBER(18),
   TEMPLATEKEY          VARCHAR2(64),
   TEMPLATENAME         VARCHAR2(64)                    NOT NULL,
   PATH                 VARCHAR2(128),
   ISACTIVATE           SMALLINT                        NOT NULL,
   CONSTRAINT PK_PAINT_TEMPLATE PRIMARY KEY (PTEMPLATEID)
);

COMMENT ON COLUMN PAINT_TEMPLATE.PTEMPLATEID IS
'主键';

COMMENT ON COLUMN PAINT_TEMPLATE.TEMPLATENAME IS
'模板名称';

COMMENT ON COLUMN PAINT_TEMPLATE.PATH IS
'路径';

COMMENT ON COLUMN PAINT_TEMPLATE.ISACTIVATE IS
'是否激活
1=是
0=否';

/*==============================================================*/
/* Table: PHONE_BOOK                                            */
/*==============================================================*/
CREATE TABLE PHONE_BOOK  (
   PHONEID              NUMBER(18)                      NOT NULL,
   FULLNAME             VARCHAR2(128)                   NOT NULL,
   TITLE                VARCHAR2(32)                    NOT NULL,
   BIRTHDAY             DATE,
   NICKNAME             VARCHAR2(32),
   DUTY                 VARCHAR2(50),
   SPOUSE               VARCHAR2(32),
   CHILDS               VARCHAR2(40),
   COMPANYNAME          VARCHAR2(100),
   COMPANYADDRESS       VARCHAR2(128),
   COMPANYPHONE         VARCHAR2(32),
   COMPANYFAX           VARCHAR2(32),
   HOMEADDRESS          VARCHAR2(128),
   HOMEZIP              VARCHAR2(12),
   MOBILE               VARCHAR2(32),
   PHONE                VARCHAR2(32),
   EMAIL                VARCHAR2(32),
   QQ                   VARCHAR2(64),
   MSN                  VARCHAR2(128),
   NOTE                 VARCHAR2(500),
   USERID               NUMBER(18)                      NOT NULL,
   GROUPID              NUMBER(18),
   ISSHARED             SMALLINT                        NOT NULL,
   CONSTRAINT PK_PHONE_BOOK PRIMARY KEY (PHONEID)
);

COMMENT ON TABLE PHONE_BOOK IS
'通讯簿';

COMMENT ON COLUMN PHONE_BOOK.TITLE IS
'先生
女士
小姐';

/*==============================================================*/
/* Table: PHONE_GROUP                                           */
/*==============================================================*/
CREATE TABLE PHONE_GROUP  (
   GROUPID              NUMBER(18)                      NOT NULL,
   GROUPNAME            VARCHAR2(128)                   NOT NULL,
   ISSHARED             SMALLINT                        NOT NULL,
   SN                   NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18)                      NOT NULL,
   ISPUBLIC             SMALLINT                       DEFAULT 0,
   CONSTRAINT PK_PHONE_GROUP PRIMARY KEY (GROUPID)
);

COMMENT ON COLUMN PHONE_GROUP.GROUPNAME IS
'分组名称';

COMMENT ON COLUMN PHONE_GROUP.ISSHARED IS
'1=共享
0=私有';

COMMENT ON COLUMN PHONE_GROUP.ISPUBLIC IS
'是否公共
0=私有
1=公共';

/*==============================================================*/
/* Table: POSITION                                              */
/*==============================================================*/
CREATE TABLE POSITION  (
   POS_ID               NUMBER(18)                      NOT NULL,
   ORG_ID               NUMBER(18,0),
   POS_NAME             VARCHAR2(128)                   NOT NULL,
   POS_DESC             VARCHAR2(1024),
   POS_SUP_ID           NUMBER(18),
   PATH                 VARCHAR2(256),
   DEPTH                INTEGER,
   CONSTRAINT PK_POSITION PRIMARY KEY (POS_ID)
);

/*==============================================================*/
/* Table: POSITION_SUB                                          */
/*==============================================================*/
CREATE TABLE POSITION_SUB  (
   MAINPOSITIONID       NUMBER(18)                      NOT NULL,
   SUBPOSITIONID        NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_POSITION_SUB PRIMARY KEY (MAINPOSITIONID, SUBPOSITIONID)
);

COMMENT ON TABLE POSITION_SUB IS
'同级岗位是用于管理同级用户的下级，如一经理岗位同时有正与副，正副也同时管理下面的职位人员的话，
可以把这两职位在这里设置了映射关系，则清楚他们是同时管理下面的岗位';

/*==============================================================*/
/* Table: PRO_DEF_RIGHTS                                        */
/*==============================================================*/
CREATE TABLE PRO_DEF_RIGHTS  (
   RIGHTSID             NUMBER(18)                      NOT NULL,
   PROTYPEID            NUMBER(18),
   DEFID                NUMBER(18),
   ROLENAMES            VARCHAR2(2000),
   DEPNAMES             VARCHAR2(2000),
   USERNAMES            VARCHAR2(2000),
   USERIDS              VARCHAR2(2000),
   ROLEIDS              VARCHAR2(2000),
   DEPIDS               VARCHAR2(2000),
   CONSTRAINT PK_PRO_DEF_RIGHTS PRIMARY KEY (RIGHTSID)
);

COMMENT ON COLUMN PRO_DEF_RIGHTS.USERIDS IS
'用户IDS
格式如下，以方便使用like操作
,1,2,';

COMMENT ON COLUMN PRO_DEF_RIGHTS.ROLEIDS IS
'角色IDS
格式如下，以方便使用like操作
,1,2,';

COMMENT ON COLUMN PRO_DEF_RIGHTS.DEPIDS IS
'部门IDS
格式如下，以方便使用like操作
,1,2,';

/*==============================================================*/
/* Table: PRO_DEFINITION                                        */
/*==============================================================*/
CREATE TABLE PRO_DEFINITION  (
   DEFID                NUMBER(18)                      NOT NULL,
   PROTYPEID            NUMBER(18),
   NAME                 VARCHAR2(256)                   NOT NULL,
   DESCRIPTION          VARCHAR2(1024),
   CREATETIME           DATE,
   DEPLOYID             VARCHAR2(64),
   DEFXML               CLOB,
   DRAWDEFXML           CLOB,
   ISDEFAULT            SMALLINT                       DEFAULT 0 NOT NULL,
   PROCESSNAME          VARCHAR2(128),
   NEWVERSION           NUMBER(18),
   STATUS               SMALLINT,
   CONSTRAINT PK_PRO_DEFINITION PRIMARY KEY (DEFID)
);

COMMENT ON TABLE PRO_DEFINITION IS
'流程定义';

COMMENT ON COLUMN PRO_DEFINITION.NAME IS
'流程的名称';

COMMENT ON COLUMN PRO_DEFINITION.DESCRIPTION IS
'描述';

COMMENT ON COLUMN PRO_DEFINITION.CREATETIME IS
'创建时间';

COMMENT ON COLUMN PRO_DEFINITION.DEPLOYID IS
'Jbpm 工作流id';

COMMENT ON COLUMN PRO_DEFINITION.DEFXML IS
'流程定义XML';

COMMENT ON COLUMN PRO_DEFINITION.ISDEFAULT IS
'是否缺省
1=是
0=否';

COMMENT ON COLUMN PRO_DEFINITION.PROCESSNAME IS
'来自jbpm的流程定义jpdl中的name值';

COMMENT ON COLUMN PRO_DEFINITION.STATUS IS
'1=激活
0=禁用';

/*==============================================================*/
/* Table: PRO_HANDLE_COMP                                       */
/*==============================================================*/
CREATE TABLE PRO_HANDLE_COMP  (
   HANDLEID             NUMBER(18)                      NOT NULL,
   DEPLOYID             VARCHAR2(128)                   NOT NULL,
   ACTIVITYNAME         VARCHAR2(128),
   TRANNAME             VARCHAR2(128),
   EVENTNAME            VARCHAR2(128),
   EVENTLEVEL           SMALLINT,
   EXECODE              VARCHAR2(4000),
   HANDLETYPE           SMALLINT,
   CONSTRAINT PK_PRO_HANDLE_COMP PRIMARY KEY (HANDLEID)
);

COMMENT ON COLUMN PRO_HANDLE_COMP.DEPLOYID IS
'JBPM流程DeployId';

COMMENT ON COLUMN PRO_HANDLE_COMP.ACTIVITYNAME IS
'节点名称';

COMMENT ON COLUMN PRO_HANDLE_COMP.TRANNAME IS
'若事件为某个transition中的事件的话，则该字段存储该值';

COMMENT ON COLUMN PRO_HANDLE_COMP.EVENTNAME IS
'事件名，有值为：
start
end';

COMMENT ON COLUMN PRO_HANDLE_COMP.EVENTLEVEL IS
'事件级别为三值：
1=process  代表为流程的事件
2=node     代表为流程节点的事件
3=transition 代表为跳转的事件';

COMMENT ON COLUMN PRO_HANDLE_COMP.EXECODE IS
'事件中动态执行的代码';

COMMENT ON COLUMN PRO_HANDLE_COMP.HANDLETYPE IS
'1=监听类 实现listener之类的接口
2=处理类  实现handler之类的接口';

/*==============================================================*/
/* Table: PRO_USER_ASSIGN                                       */
/*==============================================================*/
CREATE TABLE PRO_USER_ASSIGN  (
   ASSIGNID             NUMBER(18)                      NOT NULL,
   DEPLOYID             VARCHAR2(128)                   NOT NULL,
   ACTIVITYNAME         VARCHAR2(128)                   NOT NULL,
   ROLEID               VARCHAR2(128),
   ROLENAME             VARCHAR2(256),
   USERID               VARCHAR2(128),
   USERNAME             VARCHAR2(256),
   ISSIGNED             SMALLINT                       DEFAULT 0,
   JOBID                VARCHAR2(128),
   JOBNAME              VARCHAR2(128),
   REJOBID              VARCHAR2(128),
   REJOBNAME            VARCHAR2(128),
   DEPIDS               VARCHAR2(512),
   DEPNAMES             VARCHAR2(512),
   POSUSERFLAG          SMALLINT,
   CONSTRAINT PK_PRO_USER_ASSIGN PRIMARY KEY (ASSIGNID)
);

COMMENT ON TABLE PRO_USER_ASSIGN IS
'流程过程中各个任务节点及启动流程时的角色及用户';

COMMENT ON COLUMN PRO_USER_ASSIGN.ASSIGNID IS
'授权ID';

COMMENT ON COLUMN PRO_USER_ASSIGN.DEPLOYID IS
'jbpm流程定义的id';

COMMENT ON COLUMN PRO_USER_ASSIGN.ACTIVITYNAME IS
'流程节点名称';

COMMENT ON COLUMN PRO_USER_ASSIGN.ROLEID IS
'角色Id';

COMMENT ON COLUMN PRO_USER_ASSIGN.USERID IS
'用户ID';

COMMENT ON COLUMN PRO_USER_ASSIGN.ISSIGNED IS
'1=是会签任务
0=非会签任务

若为会签任务，则需要为该会签添加会签的决策方式的设置
';

/*==============================================================*/
/* Table: PROCESS_FORM                                          */
/*==============================================================*/
CREATE TABLE PROCESS_FORM  (
   FORMID               NUMBER(18)                      NOT NULL,
   RUNID                NUMBER(18)                      NOT NULL,
   ACTIVITYNAME         VARCHAR2(256)                   NOT NULL,
   CREATETIME           DATE                            NOT NULL,
   ENDTIME              DATE,
   DURTIMES             NUMBER(18),
   CREATORID            NUMBER(18),
   CREATORNAME          VARCHAR2(64),
   FROMTASKID           VARCHAR2(64),
   FROMTASK             VARCHAR2(256),
   TASKID               VARCHAR2(64),
   TRANSTO              VARCHAR2(256),
   STATUS               SMALLINT                       DEFAULT 0,
   PREFORMID            NUMBER(18),
   COMMENTS             VARCHAR2(2000),
   CONSTRAINT PK_PROCESS_FORM PRIMARY KEY (FORMID)
);

COMMENT ON TABLE PROCESS_FORM IS
'流程表单
存储保存在运行中的流程表单数据';

COMMENT ON COLUMN PROCESS_FORM.RUNID IS
'所属运行流程';

COMMENT ON COLUMN PROCESS_FORM.ACTIVITYNAME IS
'活动或任务名称';

COMMENT ON COLUMN PROCESS_FORM.FROMTASKID IS
'该任务来自由哪一任务跳转过来，目的是为了查到该任务的上一任务，方便任务驳回。存储Jbpm 的任务ID';

COMMENT ON COLUMN PROCESS_FORM.FROMTASK IS
'该任务来自由哪一任务跳转过来，目的是为了查到该任务的上一任务，方便任务驳回。';

COMMENT ON COLUMN PROCESS_FORM.TASKID IS
'当前任务ID';

COMMENT ON COLUMN PROCESS_FORM.TRANSTO IS
'跳转节点
跳转至下一任务';

COMMENT ON COLUMN PROCESS_FORM.STATUS IS
'0=进入任务
1=完成
2=取消';

/*==============================================================*/
/* Table: PROCESS_MODULE                                        */
/*==============================================================*/
CREATE TABLE PROCESS_MODULE  (
   MODULEID             NUMBER(18)                      NOT NULL,
   MODULENAME           VARCHAR2(256)                   NOT NULL,
   MODULEKEY            VARCHAR2(128)                   NOT NULL,
   DESCP                VARCHAR2(4000),
   DEFID                NUMBER(18),
   PROCESSKEY           VARCHAR2(256),
   CREATOR              VARCHAR2(64),
   CREATETIME           DATE,
   CONSTRAINT PK_PROCESS_MODULE PRIMARY KEY (MODULEID)
);

/*==============================================================*/
/* Table: PROCESS_RUN                                           */
/*==============================================================*/
CREATE TABLE PROCESS_RUN  (
   RUNID                NUMBER(18)                      NOT NULL,
   SUBJECT              VARCHAR2(256)                   NOT NULL,
   CREATOR              VARCHAR2(128),
   USERID               NUMBER(18)                      NOT NULL,
   DEFID                NUMBER(18)                      NOT NULL,
   PIID                 VARCHAR2(64),
   CREATETIME           DATE                            NOT NULL,
   RUNSTATUS            SMALLINT                        NOT NULL,
   BUSDESC              VARCHAR2(1024),
   ENTITYNAME           VARCHAR2(128),
   ENTITYID             NUMBER(18),
   FORMDEFID            NUMBER(18),
   CONSTRAINT PK_PROCESS_RUN PRIMARY KEY (RUNID)
);

COMMENT ON TABLE PROCESS_RUN IS
'运行中的流程';

COMMENT ON COLUMN PROCESS_RUN.SUBJECT IS
'标题
一般为流程名称＋格式化的时间';

COMMENT ON COLUMN PROCESS_RUN.CREATOR IS
'创建人';

COMMENT ON COLUMN PROCESS_RUN.USERID IS
'所属用户';

COMMENT ON COLUMN PROCESS_RUN.DEFID IS
'所属流程定义';

COMMENT ON COLUMN PROCESS_RUN.PIID IS
'流程实例ID';

COMMENT ON COLUMN PROCESS_RUN.CREATETIME IS
'创建时间';

COMMENT ON COLUMN PROCESS_RUN.RUNSTATUS IS
'0=尚未启动
1=已经启动流程
2=运行结束';

COMMENT ON COLUMN PROCESS_RUN.FORMDEFID IS
'存储正在运行的表单定义id';

/*==============================================================*/
/* Table: RELATIVE_JOB                                          */
/*==============================================================*/
CREATE TABLE RELATIVE_JOB  (
   REJOBID              NUMBER(18)                      NOT NULL,
   JOBNAME              VARCHAR2(128)                   NOT NULL,
   JOBCODE              VARCHAR2(256),
   PARENT               NUMBER(18),
   PATH                 VARCHAR2(128),
   DEPATH               NUMBER(18)                     DEFAULT 0,
   CONSTRAINT PK_RELATIVE_JOB PRIMARY KEY (REJOBID)
);

COMMENT ON COLUMN RELATIVE_JOB.JOBNAME IS
'岗位名称';

COMMENT ON COLUMN RELATIVE_JOB.JOBCODE IS
'编码';

COMMENT ON COLUMN RELATIVE_JOB.PARENT IS
'父岗位';

COMMENT ON COLUMN RELATIVE_JOB.PATH IS
'路径';

COMMENT ON COLUMN RELATIVE_JOB.DEPATH IS
'深度';

/*==============================================================*/
/* Table: RELATIVE_USER                                         */
/*==============================================================*/
CREATE TABLE RELATIVE_USER  (
   RELATIVEUSERID       NUMBER(18)                      NOT NULL,
   REJOBID              NUMBER(18),
   USERID               NUMBER(18),
   JOBUSERID            NUMBER(18),
   ISSUPER              SMALLINT,
   CONSTRAINT PK_RELATIVE_USER PRIMARY KEY (RELATIVEUSERID)
);

COMMENT ON COLUMN RELATIVE_USER.RELATIVEUSERID IS
'ID';

COMMENT ON COLUMN RELATIVE_USER.REJOBID IS
'所属相对岗位';

COMMENT ON COLUMN RELATIVE_USER.USERID IS
'所属员工';

COMMENT ON COLUMN RELATIVE_USER.ISSUPER IS
'上下级标识
1=上级
0=下级';

/*==============================================================*/
/* Table: REPORT_PARAM                                          */
/*==============================================================*/
CREATE TABLE REPORT_PARAM  (
   PARAMID              NUMBER(18)                      NOT NULL,
   REPORTID             NUMBER(18)                      NOT NULL,
   PARAMNAME            VARCHAR2(64)                    NOT NULL,
   PARAMKEY             VARCHAR2(64)                    NOT NULL,
   DEFAULTVAL           VARCHAR2(128),
   PARAMTYPE            VARCHAR2(32)                    NOT NULL,
   SN                   NUMBER(18)                      NOT NULL,
   PARAMTYPESTR         VARCHAR2(1024),
   CONSTRAINT PK_REPORT_PARAM PRIMARY KEY (PARAMID)
);

COMMENT ON TABLE REPORT_PARAM IS
'报表参数';

COMMENT ON COLUMN REPORT_PARAM.REPORTID IS
'所属报表';

COMMENT ON COLUMN REPORT_PARAM.PARAMNAME IS
'参数名称';

COMMENT ON COLUMN REPORT_PARAM.PARAMKEY IS
'参数Key';

COMMENT ON COLUMN REPORT_PARAM.DEFAULTVAL IS
'缺省值';

COMMENT ON COLUMN REPORT_PARAM.PARAMTYPE IS
'类型
字符类型--varchar
整型--int
精度型--decimal
日期型--date
日期时间型--datetime
';

COMMENT ON COLUMN REPORT_PARAM.SN IS
'系列号';

/*==============================================================*/
/* Table: REPORT_TEMPLATE                                       */
/*==============================================================*/
CREATE TABLE REPORT_TEMPLATE  (
   REPORTID             NUMBER(18)                      NOT NULL,
   TITLE                VARCHAR2(128)                   NOT NULL,
   DESCP                VARCHAR2(500)                   NOT NULL,
   REPORTLOCATION       VARCHAR2(128)                   NOT NULL,
   CREATETIME           DATE                            NOT NULL,
   UPDATETIME           DATE                            NOT NULL,
   REPORTKEY            VARCHAR2(128),
   ISDEFAULTIN          SMALLINT,
   CONSTRAINT PK_REPORT_TEMPLATE PRIMARY KEY (REPORTID)
);

COMMENT ON TABLE REPORT_TEMPLATE IS
'报表模板
report_template';

COMMENT ON COLUMN REPORT_TEMPLATE.TITLE IS
'标题';

COMMENT ON COLUMN REPORT_TEMPLATE.DESCP IS
'描述';

COMMENT ON COLUMN REPORT_TEMPLATE.REPORTLOCATION IS
'报表模块的jasper文件的路径';

COMMENT ON COLUMN REPORT_TEMPLATE.CREATETIME IS
'创建时间';

COMMENT ON COLUMN REPORT_TEMPLATE.UPDATETIME IS
'修改时间';

COMMENT ON COLUMN REPORT_TEMPLATE.REPORTKEY IS
'标识key';

COMMENT ON COLUMN REPORT_TEMPLATE.ISDEFAULTIN IS
'是否缺省
1=缺省
0=非缺省';

/*==============================================================*/
/* Table: ROLE_FUN                                              */
/*==============================================================*/
CREATE TABLE ROLE_FUN  (
   ROLEID               NUMBER(18)                      NOT NULL,
   ORG_ID               NUMBER(18)                      NOT NULL,
   FUNCTIONID           NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_ROLE_FUN PRIMARY KEY (ROLEID, ORG_ID, FUNCTIONID)
);

/*==============================================================*/
/* Table: ROLE_POSITION                                         */
/*==============================================================*/
CREATE TABLE ROLE_POSITION  (
   POS_ID               NUMBER(18)                      NOT NULL,
   ROLEID               NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_ROLE_POSITION PRIMARY KEY (POS_ID, ROLEID)
);

/*==============================================================*/
/* Table: RUN_DATA                                              */
/*==============================================================*/
CREATE TABLE RUN_DATA  (
   DATAID               NUMBER(18)                      NOT NULL,
   RUNID                NUMBER(18),
   FIELDLABEL           VARCHAR2(128),
   FIELDNAME            VARCHAR2(64)                    NOT NULL,
   INTVALUE             NUMBER(18),
   LONGVALUE            NUMBER(18),
   DECVALUE             NUMBER(18,4),
   DATEVALUE            DATE,
   STRVALUE             VARCHAR2(4000),
   BOOLVALUE            SMALLINT,
   BLOBVALUE            BLOB,
   ISSHOWED             SMALLINT,
   TEXTVALUE            CLOB,
   FIELDTYPE            VARCHAR2(32),
   CONSTRAINT PK_RUN_DATA PRIMARY KEY (DATAID)
);

COMMENT ON COLUMN RUN_DATA.FIELDLABEL IS
'字段标签';

COMMENT ON COLUMN RUN_DATA.FIELDNAME IS
'字段名称';

COMMENT ON COLUMN RUN_DATA.INTVALUE IS
'整数值';

COMMENT ON COLUMN RUN_DATA.LONGVALUE IS
'长整值';

COMMENT ON COLUMN RUN_DATA.DECVALUE IS
'精度值';

COMMENT ON COLUMN RUN_DATA.DATEVALUE IS
'日期值';

COMMENT ON COLUMN RUN_DATA.STRVALUE IS
'字符值';

COMMENT ON COLUMN RUN_DATA.BOOLVALUE IS
'布尔值';

COMMENT ON COLUMN RUN_DATA.BLOBVALUE IS
'对象值';

COMMENT ON COLUMN RUN_DATA.ISSHOWED IS
'是否显示
1=显示
0=不显示';

/*==============================================================*/
/* Table: SEAL                                                  */
/*==============================================================*/
CREATE TABLE SEAL  (
   SEALID               NUMBER(18)                      NOT NULL,
   FILEID               NUMBER(18),
   SEALNAME             VARCHAR2(64)                    NOT NULL,
   SEALPATH             VARCHAR2(128),
   BELONGID             NUMBER(18)                      NOT NULL,
   BELONGNAME           VARCHAR2(64)                    NOT NULL,
   CONSTRAINT PK_SEAL PRIMARY KEY (SEALID)
);

COMMENT ON COLUMN SEAL.SEALNAME IS
'印章名称';

COMMENT ON COLUMN SEAL.SEALPATH IS
'印章文件路径';

COMMENT ON COLUMN SEAL.BELONGID IS
'所属人ID';

COMMENT ON COLUMN SEAL.BELONGNAME IS
'所属人';

/*==============================================================*/
/* Table: SECTION                                               */
/*==============================================================*/
CREATE TABLE SECTION  (
   SECTIONID            NUMBER(18)                      NOT NULL,
   SECTIONNAME          VARCHAR2(256)                   NOT NULL,
   SECTIONDESC          VARCHAR2(1024),
   CREATETIME           DATE                            NOT NULL,
   SECTIONTYPE          SMALLINT                        NOT NULL,
   USERNAME             VARCHAR2(256),
   USERID               NUMBER(18),
   COLNUMBER            NUMBER(18),
   ROWNUMBER            NUMBER(18),
   STATUS               SMALLINT                        NOT NULL,
   CONSTRAINT PK_SECTION PRIMARY KEY (SECTIONID)
);

/*==============================================================*/
/* Table: SHORT_MESSAGE                                         */
/*==============================================================*/
CREATE TABLE SHORT_MESSAGE  (
   MESSAGEID            NUMBER(18)                      NOT NULL,
   SENDERID             NUMBER(18),
   CONTENT              VARCHAR2(256)                   NOT NULL,
   SENDER               VARCHAR2(64)                    NOT NULL,
   MSGTYPE              SMALLINT                        NOT NULL,
   SENDTIME             DATE                            NOT NULL,
   CONSTRAINT PK_SHORT_MESSAGE PRIMARY KEY (MESSAGEID)
);

COMMENT ON TABLE SHORT_MESSAGE IS
'短信消息';

COMMENT ON COLUMN SHORT_MESSAGE.SENDERID IS
'主键';

COMMENT ON COLUMN SHORT_MESSAGE.MSGTYPE IS
'1=个人信息
2=日程安排
3=计划任务
';

/*==============================================================*/
/* Table: SMS_HISTORY                                           */
/*==============================================================*/
CREATE TABLE SMS_HISTORY  (
   SMSID                NUMBER(18)                      NOT NULL,
   SENDTIME             DATE                            NOT NULL,
   RECIPIENTS           VARCHAR2(128),
   PHONENUMBER          VARCHAR2(128)                   NOT NULL,
   USERID               NUMBER(18),
   USERNAME             VARCHAR2(128),
   SMSCONTENT           VARCHAR2(1024)                  NOT NULL,
   STATUS               SMALLINT                        NOT NULL,
   CONSTRAINT PK_SMS_HISTORY PRIMARY KEY (SMSID)
);

COMMENT ON COLUMN SMS_HISTORY.STATUS IS
'0=未发送
1=发送失败

发送成功后，该记录会直接存在另一张发送历史的表中
';

/*==============================================================*/
/* Table: SMS_MOBILE                                            */
/*==============================================================*/
CREATE TABLE SMS_MOBILE  (
   SMSID                NUMBER(18)                      NOT NULL,
   SENDTIME             DATE                            NOT NULL,
   RECIPIENTS           VARCHAR2(128),
   PHONENUMBER          VARCHAR2(128)                   NOT NULL,
   USERID               NUMBER(18),
   USERNAME             VARCHAR2(128),
   SMSCONTENT           VARCHAR2(1024)                  NOT NULL,
   STATUS               SMALLINT                        NOT NULL,
   CONSTRAINT PK_SMS_MOBILE PRIMARY KEY (SMSID)
);

COMMENT ON COLUMN SMS_MOBILE.STATUS IS
'0=未发送
1=发送失败

发送成功后，该记录会直接存在另一张发送历史的表中
';

/*==============================================================*/
/* Table: SYS_CONFIG                                            */
/*==============================================================*/
CREATE TABLE SYS_CONFIG  (
   CONFIGID             NUMBER(18)                      NOT NULL,
   CONFIGKEY            VARCHAR2(64)                    NOT NULL,
   CONFIGNAME           VARCHAR2(64)                    NOT NULL,
   CONFIGDESC           VARCHAR2(256),
   TYPENAME             VARCHAR2(32)                    NOT NULL,
   DATATYPE             SMALLINT                        NOT NULL,
   DATAVALUE            VARCHAR2(64),
   TYPEKEY              VARCHAR2(64),
   CONSTRAINT PK_SYS_CONFIG PRIMARY KEY (CONFIGID)
);

COMMENT ON TABLE SYS_CONFIG IS
'系统配置

用于系统的全局配置
如邮件服务器的配置';

COMMENT ON COLUMN SYS_CONFIG.CONFIGKEY IS
'Key';

COMMENT ON COLUMN SYS_CONFIG.CONFIGNAME IS
'配置名称';

COMMENT ON COLUMN SYS_CONFIG.CONFIGDESC IS
'配置描述';

COMMENT ON COLUMN SYS_CONFIG.TYPENAME IS
'所属分类名称';

COMMENT ON COLUMN SYS_CONFIG.DATATYPE IS
'数据类型
1=varchar
2=intger
3=decimal
4=datetime
5=time
';

/*==============================================================*/
/* Table: TASK_SIGN                                             */
/*==============================================================*/
CREATE TABLE TASK_SIGN  (
   SIGNID               NUMBER(18)                      NOT NULL,
   ASSIGNID             NUMBER(18)                      NOT NULL,
   VOTECOUNTS           NUMBER(18),
   VOTEPERCENTS         NUMBER(18),
   DECIDETYPE           SMALLINT                        NOT NULL,
   CONSTRAINT PK_TASK_SIGN PRIMARY KEY (SIGNID)
);

COMMENT ON COLUMN TASK_SIGN.ASSIGNID IS
'所属流程设置';

COMMENT ON COLUMN TASK_SIGN.VOTECOUNTS IS
'绝对票数';

COMMENT ON COLUMN TASK_SIGN.VOTEPERCENTS IS
'百分比票数';

COMMENT ON COLUMN TASK_SIGN.DECIDETYPE IS
'1=pass 通过
2=reject 拒绝';

/*==============================================================*/
/* Table: TASK_SIGN_DATA                                        */
/*==============================================================*/
CREATE TABLE TASK_SIGN_DATA  (
   DATAID               NUMBER(18)                      NOT NULL,
   VOTEID               NUMBER(18)                      NOT NULL,
   VOTENAME             VARCHAR2(64),
   VOTETIME             DATE                            NOT NULL,
   TASKID               VARCHAR2(64)                    NOT NULL,
   ISAGREE              SMALLINT                        NOT NULL,
   CONSTRAINT PK_TASK_SIGN_DATA PRIMARY KEY (DATAID)
);

COMMENT ON COLUMN TASK_SIGN_DATA.VOTEID IS
'投票人';

COMMENT ON COLUMN TASK_SIGN_DATA.VOTENAME IS
'投票人名';

COMMENT ON COLUMN TASK_SIGN_DATA.VOTETIME IS
'投票时间';

COMMENT ON COLUMN TASK_SIGN_DATA.TASKID IS
'任务Id';

COMMENT ON COLUMN TASK_SIGN_DATA.ISAGREE IS
'是否同意
1=同意
2=拒绝

跟task_sign中的decideType是一样';

/*==============================================================*/
/* Table: TYPE_KEY                                              */
/*==============================================================*/
CREATE TABLE TYPE_KEY  (
   TYPEKEYID            NUMBER(18)                      NOT NULL,
   TYPEKEY              VARCHAR2(64)                    NOT NULL,
   TYPENAME             VARCHAR2(64)                    NOT NULL,
   SN                   NUMBER(18),
   CONSTRAINT PK_TYPE_KEY PRIMARY KEY (TYPEKEYID)
);

/*==============================================================*/
/* Table: USER_ORG                                              */
/*==============================================================*/
CREATE TABLE USER_ORG  (
   USER_ORG_ID          NUMBER(18)                      NOT NULL,
   USERID               NUMBER(18),
   ORG_ID               NUMBER(18),
   IS_PRIMARY           SMALLINT                        NOT NULL,
   IS_CHARGE            NUMBER(18),
   CONSTRAINT PK_USER_ORG PRIMARY KEY (USER_ORG_ID)
);

COMMENT ON COLUMN USER_ORG.USERID IS
'主键';

COMMENT ON COLUMN USER_ORG.IS_PRIMARY IS
'1=主要
0=非主要';

/*==============================================================*/
/* Table: USER_POSITION                                         */
/*==============================================================*/
CREATE TABLE USER_POSITION  (
   USER_POS_ID          NUMBER(18)                      NOT NULL,
   POS_ID               NUMBER(18),
   USERID               NUMBER(18),
   ISPRIMARY            SMALLINT,
   CONSTRAINT PK_USER_POSITION PRIMARY KEY (USER_POS_ID)
);

COMMENT ON COLUMN USER_POSITION.USERID IS
'主键';

/*==============================================================*/
/* Table: USER_ROLE                                             */
/*==============================================================*/
CREATE TABLE USER_ROLE  (
   USERID               NUMBER(18)                      NOT NULL,
   ROLEID               NUMBER(18)                      NOT NULL,
   CONSTRAINT PK_USER_ROLE PRIMARY KEY (USERID, ROLEID)
);

COMMENT ON COLUMN USER_ROLE.USERID IS
'主键';

/*==============================================================*/
/* Table: WF_GENERAL                                            */
/*==============================================================*/
CREATE TABLE WF_GENERAL  (
   ENTITYID             NUMBER(18)                      NOT NULL,
   ITEMSUBJECT          VARCHAR2(128)                   NOT NULL,
   ITEMDESCP            CLOB                            NOT NULL,
   RUNID                NUMBER(18),
   CREATETIME           DATE,
   CONSTRAINT PK_WF_GENERAL PRIMARY KEY (ENTITYID)
);

COMMENT ON COLUMN WF_GENERAL.ENTITYID IS
'ID';

COMMENT ON COLUMN WF_GENERAL.ITEMDESCP IS
'申请描述';

COMMENT ON COLUMN WF_GENERAL.RUNID IS
'process_run表的主键，通过它可以取到相关的流程运行及审批信息';

ALTER TABLE APP_TIPS
   ADD CONSTRAINT FK_APP_TIPS_AT_R_AP_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE APP_USER
   ADD CONSTRAINT FK_APP_USER_AU_R_ORG_ORGANIZA FOREIGN KEY (ORG_ID)
      REFERENCES ORGANIZATION (ORG_ID);

ALTER TABLE DICTIONARY
   ADD CONSTRAINT FK_GT_R_DCY FOREIGN KEY (PROTYPEID)
      REFERENCES GLOBAL_TYPE (PROTYPEID)
      ON DELETE SET NULL;

ALTER TABLE DOC_FILE
   ADD CONSTRAINT FK_DOC_FILE_DF_F_DT_DOCUMENT FOREIGN KEY (DOCID)
      REFERENCES DOCUMENT (DOCID)
      ON DELETE CASCADE;

ALTER TABLE DOC_FILE
   ADD CONSTRAINT FK_DOC_R_FA FOREIGN KEY (FILEID)
      REFERENCES FILE_ATTACH (FILEID)
      ON DELETE CASCADE;

ALTER TABLE DOC_FOLDER
   ADD CONSTRAINT FK_DOC_FOLD_DF_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE DOC_PRIVILEGE
   ADD CONSTRAINT FK_DOC_PRIV_DP_R_DF_DOC_FOLD FOREIGN KEY (FOLDERID)
      REFERENCES DOC_FOLDER (FOLDERID)
      ON DELETE CASCADE;

ALTER TABLE DOC_PRIVILEGE
   ADD CONSTRAINT FK_DOC_PRIV_DP_R_DT_DOCUMENT FOREIGN KEY (DOCID)
      REFERENCES DOCUMENT (DOCID)
      ON DELETE CASCADE;

ALTER TABLE DOCUMENT
   ADD CONSTRAINT FK_DOCUMENT_DT_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE DOCUMENT
   ADD CONSTRAINT FK_DOCUMENT_DT_R_DF_DOC_FOLD FOREIGN KEY (FOLDERID)
      REFERENCES DOC_FOLDER (FOLDERID)
      ON DELETE SET NULL;

ALTER TABLE FIELD_RIGHTS
   ADD CONSTRAINT FK_FIELD_RI_FR_R_FDM_FORM_DEF FOREIGN KEY (MAPPINGID)
      REFERENCES FORM_DEF_MAPPING (MAPPINGID)
      ON DELETE CASCADE;

ALTER TABLE FIELD_RIGHTS
   ADD CONSTRAINT FK_FIELD_RI_FR_R_FF_FORM_FIE FOREIGN KEY (FIELDID)
      REFERENCES FORM_FIELD (FIELDID)
      ON DELETE CASCADE;

ALTER TABLE FILE_ATTACH
   ADD CONSTRAINT FK_GT_R_FA FOREIGN KEY (PROTYPEID)
      REFERENCES GLOBAL_TYPE (PROTYPEID)
      ON DELETE SET NULL;

ALTER TABLE FORM_DEF_MAPPING
   ADD CONSTRAINT FK_FORM_DEF_FDM_R_FD_FORM_DEF FOREIGN KEY (FORMDEFID)
      REFERENCES FORM_DEF (FORMDEFID)
      ON DELETE CASCADE;

ALTER TABLE FORM_DEF_MAPPING
   ADD CONSTRAINT FK_FORM_DEF_FDM_R_PD_PRO_DEFI FOREIGN KEY (DEFID)
      REFERENCES PRO_DEFINITION (DEFID)
      ON DELETE CASCADE;

ALTER TABLE FORM_FIELD
   ADD CONSTRAINT FK_FORM_FIE_FF_R_FD_FORM_TAB FOREIGN KEY (TABLEID)
      REFERENCES FORM_TABLE (TABLEID)
      ON DELETE CASCADE;

ALTER TABLE FORM_TABLE
   ADD CONSTRAINT FK_FORM_TAB_FT_R_FD_FORM_DEF FOREIGN KEY (FORMDEFID)
      REFERENCES FORM_DEF (FORMDEFID)
      ON DELETE CASCADE;

ALTER TABLE FORM_TEMPLATE
   ADD CONSTRAINT FK_FORM_TEM_FT_R_FDM_FORM_DEF FOREIGN KEY (MAPPINGID)
      REFERENCES FORM_DEF_MAPPING (MAPPINGID)
      ON DELETE SET NULL;

ALTER TABLE FUN_URL
   ADD CONSTRAINT FK_FUN_URL_FU_R_AFN_APP_FUNC FOREIGN KEY (FUNCTIONID)
      REFERENCES APP_FUNCTION (FUNCTIONID);

ALTER TABLE IN_MESSAGE
   ADD CONSTRAINT FK_IN_MESSA_IM_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE IN_MESSAGE
   ADD CONSTRAINT FK_IN_MESSA_IM_R_SM_SHORT_ME FOREIGN KEY (MESSAGEID)
      REFERENCES SHORT_MESSAGE (MESSAGEID);

ALTER TABLE INDEX_DISPLAY
   ADD CONSTRAINT FK_INDEX_DI_ID_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE MAIL
   ADD CONSTRAINT FK_MAIL_ML_R_AU_APP_USER FOREIGN KEY (SENDERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE MAIL_ATTACH
   ADD CONSTRAINT FK_MAIL_ATT_MA_R_ML_MAIL FOREIGN KEY (MAILID)
      REFERENCES MAIL (MAILID);

ALTER TABLE MAIL_ATTACH
   ADD CONSTRAINT FK_MAIL_ATT_MA_R_FA_FILE_ATT FOREIGN KEY (FILEID)
      REFERENCES FILE_ATTACH (FILEID)
      ON DELETE CASCADE;

ALTER TABLE MAIL_BOX
   ADD CONSTRAINT FK_MAIL_BOX_MB_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE MAIL_BOX
   ADD CONSTRAINT FK_MAIL_BOX_MB_R_FD_MAIL_FOL FOREIGN KEY (FOLDERID)
      REFERENCES MAIL_FOLDER (FOLDERID);

ALTER TABLE MAIL_BOX
   ADD CONSTRAINT FK_MAIL_BOX_MB_R_ML_MAIL FOREIGN KEY (MAILID)
      REFERENCES MAIL (MAILID);

ALTER TABLE MAIL_FOLDER
   ADD CONSTRAINT FK_MAIL_FOL_FD_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE NEWS
   ADD CONSTRAINT FK_NEWS_NEWS_R_SC_SECTION FOREIGN KEY (SECTIONID)
      REFERENCES SECTION (SECTIONID)
      ON DELETE SET NULL;

ALTER TABLE NEWS_COMMENT
   ADD CONSTRAINT FK_NEWS_COM_NC_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE NEWS_COMMENT
   ADD CONSTRAINT FK_NEWS_COM_NC_R_NS_NEWS FOREIGN KEY (NEWSID)
      REFERENCES NEWS (NEWSID);

ALTER TABLE ORGANIZATION
   ADD CONSTRAINT FK_ORG_R_DMS FOREIGN KEY (DEM_ID)
      REFERENCES DEMENSION (DEM_ID)
      ON DELETE CASCADE;

ALTER TABLE OUT_MAIL
   ADD CONSTRAINT FK_OUT_MAIL_OM_R_OMF_OUT_MAIL FOREIGN KEY (FOLDERID)
      REFERENCES OUT_MAIL_FOLDER (FOLDERID);

ALTER TABLE OUT_MAIL_FILE
   ADD CONSTRAINT FK_OUT_MAIL_OMF_R_OM_OUT_MAIL FOREIGN KEY (MAILID)
      REFERENCES OUT_MAIL (MAILID);

ALTER TABLE OUT_MAIL_FILE
   ADD CONSTRAINT FK_OUT_MAIL_REFERENCE_FILE_ATT FOREIGN KEY (FILEID)
      REFERENCES FILE_ATTACH (FILEID)
      ON DELETE CASCADE;

ALTER TABLE OUT_MAIL_FOLDER
   ADD CONSTRAINT FK_OUT_MAIL_OMF_RAU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE OUT_MAIL_USER_SETING
   ADD CONSTRAINT FK_OUT_MAIL_OMU_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID)
      ON DELETE SET NULL;

ALTER TABLE PAINT_TEMPLATE
   ADD CONSTRAINT FK_PT_R_FA FOREIGN KEY (FILEID)
      REFERENCES FILE_ATTACH (FILEID)
      ON DELETE CASCADE;

ALTER TABLE PHONE_BOOK
   ADD CONSTRAINT FK_PHONE_BO_PB_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE PHONE_BOOK
   ADD CONSTRAINT FK_PHONE_BO_PB_R_PG_PHONE_GR FOREIGN KEY (GROUPID)
      REFERENCES PHONE_GROUP (GROUPID);

ALTER TABLE PHONE_GROUP
   ADD CONSTRAINT FK_PHONE_GR_PG_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE POSITION_SUB
   ADD CONSTRAINT FK_POS_SUB_R_POS FOREIGN KEY (MAINPOSITIONID)
      REFERENCES POSITION (POS_ID)
      ON DELETE CASCADE;

ALTER TABLE POSITION_SUB
   ADD CONSTRAINT FK_POS_SUB_SUB_R_POS FOREIGN KEY (SUBPOSITIONID)
      REFERENCES POSITION (POS_ID)
      ON DELETE CASCADE;

ALTER TABLE PRO_DEF_RIGHTS
   ADD CONSTRAINT FK_PRO_DEF__PDR_R_GT_GLOBAL_T FOREIGN KEY (PROTYPEID)
      REFERENCES GLOBAL_TYPE (PROTYPEID);

ALTER TABLE PRO_DEF_RIGHTS
   ADD CONSTRAINT FK_PRO_DEF__PDR_R_PD_PRO_DEFI FOREIGN KEY (DEFID)
      REFERENCES PRO_DEFINITION (DEFID)
      ON DELETE CASCADE;

ALTER TABLE PRO_DEFINITION
   ADD CONSTRAINT FK_PRO_DEFI_PD_R_GT_GLOBAL_T FOREIGN KEY (PROTYPEID)
      REFERENCES GLOBAL_TYPE (PROTYPEID)
      ON DELETE SET NULL;

ALTER TABLE PROCESS_FORM
   ADD CONSTRAINT FK_PROCESS__PF_R_PR_PROCESS_ FOREIGN KEY (RUNID)
      REFERENCES PROCESS_RUN (RUNID);

ALTER TABLE PROCESS_MODULE
   ADD CONSTRAINT FK_PM_R_PDI FOREIGN KEY (DEFID)
      REFERENCES PRO_DEFINITION (DEFID)
      ON DELETE CASCADE;

ALTER TABLE PROCESS_RUN
   ADD CONSTRAINT FK_PRORN__R_FORM_DEF FOREIGN KEY (FORMDEFID)
      REFERENCES FORM_DEF (FORMDEFID)
      ON DELETE SET NULL;

ALTER TABLE PROCESS_RUN
   ADD CONSTRAINT FK_PROCESS__PR_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE PROCESS_RUN
   ADD CONSTRAINT FK_PROCESS__PR_R_PD_PRO_DEFI FOREIGN KEY (DEFID)
      REFERENCES PRO_DEFINITION (DEFID);

ALTER TABLE RELATIVE_USER
   ADD CONSTRAINT FK_RELATIVE_RU_R_AU_APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID)
      ON DELETE CASCADE;

ALTER TABLE RELATIVE_USER
   ADD CONSTRAINT FK_RELATIVE_RU_R_RJ_RELATIVE FOREIGN KEY (REJOBID)
      REFERENCES RELATIVE_JOB (REJOBID)
      ON DELETE CASCADE;

ALTER TABLE REPORT_PARAM
   ADD CONSTRAINT FK_REPORT_P_RP_R_RPT_REPORT_T FOREIGN KEY (REPORTID)
      REFERENCES REPORT_TEMPLATE (REPORTID);

ALTER TABLE ROLE_FUN
   ADD CONSTRAINT FK_ROLE_FUN_RF_R_AFN_APP_FUNC FOREIGN KEY (FUNCTIONID)
      REFERENCES APP_FUNCTION (FUNCTIONID);

ALTER TABLE ROLE_FUN
   ADD CONSTRAINT FK_ROLE_FUN_RF_R_AR_APP_ROLE FOREIGN KEY (ROLEID)
      REFERENCES APP_ROLE (ROLEID);

ALTER TABLE ROLE_POSITION
   ADD CONSTRAINT FK_ROL_POS_R_POS FOREIGN KEY (POS_ID)
      REFERENCES POSITION (POS_ID)
      ON DELETE CASCADE;

ALTER TABLE ROLE_POSITION
   ADD CONSTRAINT FK_ROLE_POS_RPOS_R_AP_APP_ROLE FOREIGN KEY (ROLEID)
      REFERENCES APP_ROLE (ROLEID);

ALTER TABLE RUN_DATA
   ADD CONSTRAINT FK_FD_R_PR FOREIGN KEY (RUNID)
      REFERENCES PROCESS_RUN (RUNID)
      ON DELETE CASCADE;

ALTER TABLE SEAL
   ADD CONSTRAINT FK_SEAL_R_FA FOREIGN KEY (FILEID)
      REFERENCES FILE_ATTACH (FILEID)
      ON DELETE CASCADE;

ALTER TABLE SHORT_MESSAGE
   ADD CONSTRAINT FK_SHORT_ME_SM_R_AU_APP_USER FOREIGN KEY (SENDERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE TASK_SIGN
   ADD CONSTRAINT FK_TASK_SIG_TS_R_PUA_PRO_USER FOREIGN KEY (ASSIGNID)
      REFERENCES PRO_USER_ASSIGN (ASSIGNID);

ALTER TABLE USER_ORG
   ADD CONSTRAINT FK_USER_ORG_USER_ORG__APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE USER_ORG
   ADD CONSTRAINT FK_USER_ORG_R_ORG FOREIGN KEY (ORG_ID)
      REFERENCES ORGANIZATION (ORG_ID)
      ON DELETE CASCADE;

ALTER TABLE USER_POSITION
   ADD CONSTRAINT FK_USER_POS_R_POSITION FOREIGN KEY (POS_ID)
      REFERENCES POSITION (POS_ID)
      ON DELETE CASCADE;

ALTER TABLE USER_POSITION
   ADD CONSTRAINT FK_USER_POS_USER_POS__APP_USER FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

ALTER TABLE USER_ROLE
   ADD CONSTRAINT FK_UR_R_AR FOREIGN KEY (ROLEID)
      REFERENCES APP_ROLE (ROLEID);

ALTER TABLE USER_ROLE
   ADD CONSTRAINT FK_UR_R_AU FOREIGN KEY (USERID)
      REFERENCES APP_USER (USERID);

