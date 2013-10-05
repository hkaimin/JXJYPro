/**
 * 系统导入的模块js，主要用于后加载方式，需要使用某些js时，需要在此指定加载哪一些。
 */
 Ext.ns("App");
App.importJs = {
	MenuView : [
	        __ctxPath + '/js/system/MenuView.js',
	        __ctxPath + '/js/system/MenuForm.js',
	        __ctxPath + '/js/system/MenuFunctionForm.js',
	        __ctxPath + '/js/system/MenuUrlForm.js',
	        __ctxPath + '/js/system/IconSelector.js'
	], 
	JxjyRyxfglView : [
    	__ctxPath+'/js/ryxf/JxjyRyxfglView.js',
    	__ctxPath+'/js/ryxf/JxjyRyxfglForm.js'
],JxjyXfshWstView : [
    	__ctxPath+'/js/ryxf/JxjyXfshWstView.js',
    	__ctxPath+'/js/ryxf/WSTXFSHForm.js'
],JxjyXfshRsjView : [
    	__ctxPath+'/js/ryxf/JxjyXfshRsjView.js',
    	__ctxPath+'/js/ryxf/RSJXFSHForm.js'
],


	AppRoleView : [
			__ctxPath + '/js/system/AppRoleView.js',
			__ctxPath+'/ext3/ux/CheckTreePanel.js',
			__ctxPath+'/js/system/RoleGrantRightView.js',
			__ctxPath + '/js/system/AppRoleForm.js'],

	DocumentSharedView : [
	        __ctxPath + '/js/document/DocumentSharedView.js',
	        __ctxPath + '/js/document/DocumentSharedDetail.js'],
	DocFolderSharedView :[
	        __ctxPath + '/js/document/FindPublicDocumentView.js',
	        __ctxPath + '/js/document/DocFolderView.js',
	        __ctxPath +'/js/document/DocFolderForm.js',
	        __ctxPath + '/js/document/DocFolderSharedView.js',
	        __ctxPath + '/js/document/DocFolderSharedForm.js',
	        __ctxPath + '/js/document/DocPrivilegeForm.js',
	        __ctxPath + '/js/document/DocPrivilegeView.js',
	        __ctxPath + '/ext3/ux/CheckColumn.js'],
	FindPublicDocumentView :[
	        __ctxPath + '/js/document/FindPublicDocumentView.js',
	        __ctxPath + '/js/document/PublicDocumentView.js',
	        __ctxPath + '/js/document/PublicDocumentDetail.js',
	        __ctxPath + '/js/document/NewPublicDocumentForm.js',
	        __ctxPath + '/js/document/DocFolderSelector.js'],
	NewPublicDocumentForm :[
	        __ctxPath + '/js/document/NewPublicDocumentForm.js',
	        __ctxPath + '/js/document/DocFolderSelector.js'],
	DocFolderMoveForm : [
	        __ctxPath + '/js/document/DocFolderMoveForm.js',
	        __ctxPath + '/js/document/PersonalDocFolderSelector.js'],
	NoticeView : [
			__ctxPath + '/js/info/NoticeView.js',
			__ctxPath + '/js/info/NoticeForm.js',
			__ctxPath + '/js/selector/SectionSelector.js'],
	ReportTemplateView : [
	          	        __ctxPath + '/js/system/ReportTemplateView.js',
	        			__ctxPath + '/js/system/ReportTemplateForm.js',
	        			__ctxPath + '/js/system/ReportParamForm.js',
	        			__ctxPath + '/js/system/ReportParamView.js',
	        			__ctxPath + '/js/system/ReportTemplatePreview.js',
	        			__ctxPath + '/ext3/ux/ext-basex.js'
	        			],       
	MessageView : [
	        __ctxPath + '/js/info/MessageView.js',
	        __ctxPath + '/js/info/MessageForm.js',
	        __ctxPath + '/js/info/MessageWin.js'],
	MessageManageView:[
	       __ctxPath + '/js/info/MessageManageView.js',
	       __ctxPath + '/js/info/MessageForm.js'
	],
	
    AppUserView : [
           __ctxPath+'/js/system/AppUserView.js',
           __ctxPath+'/js/system/AppUserForm.js',
           __ctxPath+'/ext3/ux/ItemSelector.js',
           __ctxPath+'/ext3/ux/MultiSelect.js',
           __ctxPath+'/js/system/DynamicPwdForm.js',
           __ctxPath+'/js/system/ResetPasswordForm.js',
           __ctxPath+'/js/system/setPasswordForm.js'],
    ProfileForm :[
    		__ctxPath+'/js/system/ProfileForm.js',
    		__ctxPath+'/js/system/ResetPasswordForm.js'],
    NewsView : [
            __ctxPath + '/js/info/NewsView.js',
            __ctxPath + '/js/info/NewsForm.js',
            __ctxPath + '/js/selector/SectionSelector.js'],
    CompanyView : [
            __ctxPath + '/js/system/CompanyView.js'],
    FileAttachView : [
    		__ctxPath + '/js/system/FileAttachView.js',
    		__ctxPath + '/js/system/FileAttachDetail.js'],
  
    PersonalMailBoxView : [
   		 	__ctxPath + '/ext3/ux/RowExpander.js',
    		__ctxPath + '/js/communicate/PersonalMailBoxView.js',
    		__ctxPath + '/js/communicate/MailView.js',
    		__ctxPath + '/js/communicate/MailForm.js',
    		__ctxPath + '/js/communicate/MailFolderForm.js'
    		],
    MailForm : [
    		__ctxPath + '/js/communicate/MailForm.js'],
   	PersonalPhoneBookView:[
   	        __ctxPath+'/js/communicate/PersonalPhoneBookView.js',
	        __ctxPath+'/js/communicate/PhoneBookView.js',
	        __ctxPath+'/js/communicate/PhoneGroupForm.js',
	        __ctxPath+'/js/communicate/PhoneBookForm.js'],
    SharedPhoneBookView:[
            __ctxPath+'/js/communicate/SharedPhoneBookView.js',
            __ctxPath+'/js/communicate/SharedPhoneBookWin.js'],
    FlowManagerView:[
    		__ctxPath+'/js/flow/ProTypeForm.js',
    		__ctxPath+'/js/selector/GlobalTypeSelector.js',
    		__ctxPath+'/js/system/GlobalTypeForm.js',
    		__ctxPath+'/js/flow/ProDefRightsForm.js',
    		__ctxPath+'/js/flow/ProDefinitionForm.js',
    		__ctxPath+'/js/flow/ProDefinitionView.js',
    		__ctxPath+'/js/flow/FlowManagerView.js',
    		__ctxPath+'/js/flow/ProDefinitionDetail.js',
			//__ctxPath+'/js/flow/ProcessRunStart.js',
    		__ctxPath+'/js/flow/ProDefinitionSetting.js',
    		__ctxPath+'/js/flow/MyTaskView.js',
    		__ctxPath+'/js/flow/ProcessNextForm.js',
    		__ctxPath+'/js/flow/FormDesignWindow.js',
    		__ctxPath+'/js/flow/FormEditorWindow.js',
    		__ctxPath+'/js/flowDesign/FlowDesignerWindow.js',
    		__ctxPath+'/js/selector/FormDefSelector.js',
    		__ctxPath+'/js/flow/FormDefForm.js',
    		__ctxPath+'/js/flow/FormDefDetailForm.js',
    		__ctxPath+'/js/selector/JobSelector.js',
    		__ctxPath+'/js/flow/FieldRightsForm.js',
    		__ctxPath+'/js/flow/TaskSignForm.js',
    		__ctxPath+'/js/selector/RoleSelector.js',
    		__ctxPath+'/js/selector/RelativeJobSelector.js',
    		__ctxPath+'/js/selector/UserDialog.js',
    		__ctxPath+'/js/selector/RoleDialog.js',
    		__ctxPath+'/js/selector/PositionDialog.js',
    		__ctxPath+'/js/selector/ReJobDialog.js',
    		__ctxPath+'/js/selector/DepSelector.js'
    ],
    TaskManager:[
       __ctxPath + '/js/flow/TaskDueDateWindow.js',
       __ctxPath + '/js/flow/TaskHandlerWindow.js',
 	   __ctxPath + '/js/flow/TaskManager.js',
 	   __ctxPath+'/js/flow/ProcessNextForm.js',
 	   __ctxPath + '/js/flow/PathChangeWindow.js'
 	],
    NewProcess:[
    	__ctxPath+'/js/flow/NewProcess.js',
    	__ctxPath+'/js/flow/ProDefinitionDetail.js',
    	__ctxPath+'/js/flow/ProDefinitionView.js'
    ],
    ProcessRunView:[
    	__ctxPath+'/js/flow/ProcessRunView.js',
    	__ctxPath+'/js/flow/ProcessRunDetail.js'
    ],
    ProcessHistoryView:[
    	__ctxPath+'/js/flow/ProcessHistoryView.js',
    	__ctxPath+'/js/flow/ProcessRunDetail.js'
    ],
    MyTaskView:[
    	__ctxPath+'/js/flow/MyTaskView.js',
    	__ctxPath+'/js/flow/ChangeTaskView.js',
    	__ctxPath+'/js/flow/ProcessNextForm.js'
    ],
   
   ProcessRunFinishView:[
    	__ctxPath+'/js/flow/ProcessRunFinishView.js',
    	__ctxPath+'/js/flow/ProcessRunDetail.js'
   ],
    SysConfigView:[
    	__ctxPath+'/js/system/SysConfigView.js',
    	__ctxPath+'/js/communicate/SmsMobileForm.js'
    ],
    //-------------personal moduels------------------------
    //-------------Home Message Detail moduels-------------
    NoticeDetail:[
    	__ctxPath+'/js/info/NoticeDetail.js'
    ],
    NewsDetail:[
    	__ctxPath+'/js/info/NewsDetail.js'
    ],
    PublicDocumentDetail:[
        __ctxPath+'/js/document/PublicDocumentDetail.js'                    
    ],
    MailDetail:[
        __ctxPath+'/js/communicate/MailDetail.js',
        __ctxPath+'/js/communicate/MailForm.js'
    ],
    CalendarPlanDetail:[
    	__ctxPath+'/js/task/CalendarPlanDetail.js'
    ],
    AppointmentDetail:[
    	__ctxPath+'/js/task/AppointmentDetail.js'
    ],
    //-------------Home Message Detail moduels-------------
    //-------------Search moduels--------------------------
    SearchNews:[
    	__ctxPath+'/js/search/SearchNews.js',
    	__ctxPath+'/js/info/NewsDetail.js'
    ],
    SearchMail:[
    	__ctxPath+'/ext3/ux/RowExpander.js',
    	__ctxPath+'/js/search/SearchMail.js',
    	__ctxPath+'/js/communicate/MailView.js',
    	__ctxPath+'/js/communicate/MailForm.js'
    ],
    SearchNotice:[
    	__ctxPath+'/js/search/SearchNotice.js'
    ],
    SearchDocument:[
        __ctxPath+'/js/search/SearchDocument.js',
        __ctxPath+'/js/document/PublicDocumentDetail.js'
    ],
   
    NewsCommentView:[
    	__ctxPath+'/js/info/NewsCommentView.js',
    	__ctxPath + '/ext3/ux/RowExpander.js'
    ],
    DictionaryView:[
    	__ctxPath+'/js/system/DictionaryView.js',
    	__ctxPath+'/js/system/DictionaryForm.js'
    ],
   
    SystemLogView:[
    	__ctxPath+'/js/system/SystemLogView.js'
    ],
    MyProcessRunView:[
    	__ctxPath+'/js/flow/MyProcessRunView.js',
    	__ctxPath+'/js/flow/ProcessRunDetail.js'
    ],
    MyRunningTaskView:[__ctxPath + '/js/flow/MyRunningTaskView.js',
   	      			__ctxPath + '/js/flow/ProcessRunDetail.js'],
    PersonalTipsView:[
        __ctxPath+'/js/info/PersonalTipsView.js'
    ],
    
    OutMailUserSetingForm:[
	   	__ctxPath+'/js/communicate/OutMailUserSetingForm.js'
	],
	OutMailBoxView : [
		__ctxPath + '/ext3/ux/RowExpander.js',
		__ctxPath + '/js/communicate/OutMailBoxView.js',
		__ctxPath + '/js/communicate/OutMailView.js',
		__ctxPath + '/js/communicate/OutMailForm.js',
		__ctxPath + '/js/communicate/OutMailFolderForm.js',
		__ctxPath + '/js/selector/EMailSelector.js'
    ],
    OutMailForm : [
		__ctxPath + '/js/communicate/OutMailForm.js',
		__ctxPath + '/js/selector/EMailSelector.js'	
    ],
    SmsMobileView : [
    	__ctxPath + '/js/communicate/SmsMobileView.js',
    	__ctxPath + '/js/communicate/SmsMobileForm.js'
    ],
    GlobalTypeManager:[
 	   __ctxPath + '/js/system/GlobalTypeManager.js',
       __ctxPath + '/js/system/GlobalTypeForm.js',
       __ctxPath + '/js/system/TypeKeyForm.js'
	 ],
	 PrivateDocumentView:[
	   __ctxPath + '/js/document/PrivateDocumentView.js',
	   __ctxPath + '/js/document/DocumentForm.js',
	   __ctxPath + '/js/document/DocFolderForm.js',
	   __ctxPath + '/js/document/DocumentSharedForm.js',
	   __ctxPath + '/js/document/FileDetailShowWin.js',
	   __ctxPath + '/js/selector/RoleSelector.js'
	 ],
	 KnowledgeManageView:[
	   __ctxPath + '/js/document/KnowledgeManageView.js',
	   __ctxPath + '/js/document/KnowledgeForm.js',
	   __ctxPath + '/js/document/DocFolderForm.js',
	   __ctxPath + '/js/document/DocFolderSelector.js',
	   __ctxPath + '/js/document/FileDetailShowWin.js',
	   __ctxPath + '/js/document/DocumentDetailWin.js',
	   __ctxPath + '/js/document/KnowledgePrivilegeWin.js',
	   __ctxPath + '/js/document/DocFolderSharedForm.js',
	   __ctxPath + '/ext3/ux/CheckColumn.js',
	   __ctxPath + '/js/selector/RoleSelector.js'
	 ],
	
	 PublicPhoneBookView:[
	    __ctxPath + '/js/communicate/PublicPhoneBookView.js',
        __ctxPath+'/js/communicate/PublicPhoneGroupForm.js',
        __ctxPath+'/js/communicate/PhoneBookForm.js'
	 ],
	
	 OnlineDocumentManageView:[
	    __ctxPath + '/js/document/OnlineDocumentManageView.js',
	    __ctxPath + '/js/document/OnlineDocumentForm.js',
	    __ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
	    __ctxPath + '/js/selector/SealSelector.js',
	    __ctxPath + '/js/selector/PaintTemplateSelector.js',
	    __ctxPath + '/js/document/DocFolderForm.js',
	    __ctxPath + '/ext3/ux/CheckColumn.js',
	    __ctxPath + '/js/document/DocFolderSharedForm.js',
	    __ctxPath + '/js/document/DocFolderSelector.js',
	    __ctxPath + '/js/document/FileDetailShowWin.js',
	    __ctxPath + '/js/document/KnowledgePrivilegeWin.js',
	    __ctxPath + '/js/document/OnlineDocumentDetail.js',
	    __ctxPath + '/js/selector/RoleSelector.js'
	 ],
	 PaintTemplateView:[
	    __ctxPath + '/js/document/PaintTemplateView.js',
	    __ctxPath + '/js/document/PaintTemplateForm.js',
	    __ctxPath + '/js/core/ntkoffice/NtkOfficePanel.js',
	    __ctxPath + '/js/document/DocumentTemplateForm.js',
	    __ctxPath + '/js/selector/SealSelector.js',
	    __ctxPath + '/js/selector/PaintTemplateSelector.js'
	 ],
	 SealView:[
	    __ctxPath + '/js/document/SealView.js',
	    __ctxPath + '/js/document/SealForm.js',
	    __ctxPath + '/js/core/ntkosign/NtkoSignPanel.js',
	    __ctxPath + '/js/document/MakeSealForm.js',
	    __ctxPath + '/js/document/SealShowPanel.js'
	 ],
	 SectionList:[
	 	__ctxPath + '/js/info/SectionList.js',
	 	__ctxPath + '/js/info/SectionForm.js',
        __ctxPath + '/js/selector/SectionSelector.js'
	 ],
	 SectionView:[
	 	__ctxPath + '/ext3/ux/Portal.js',
	 	__ctxPath + '/ext3/ux/PortalColumn.js',
	 	__ctxPath + '/ext3/ux/Portlet.js',
	 	__ctxPath + '/js/info/SectionView.js',
	 	__ctxPath + '/js/info/SectionForm.js',
	 	__ctxPath + '/js/selector/SectionSelector.js'
	 	
	 ],
	 FormDefView:[
	    __ctxPath + '/js/flow/FormDefView.js',
	    __ctxPath + '/js/flow/FormDefForm.js',
    	__ctxPath + '/js/fckdesign/Fckdesigner.js',
    	__ctxPath+'/js/fckdesign/FormDesignPanelForm.js',
    	__ctxPath + '/js/flow/FormDefDetailWin.js'
	 ],
	 FlowFormProsView:[
	    __ctxPath + '/js/flow/FlowFormProsView.js',
	    //__ctxPath + '/js/flow/FlowFormQueryView.js',
	    __ctxPath + '/js/flow/FlowFormQueryForms.js',
	    __ctxPath + '/js/flow/FlowFormQueryEntity.js',
	    __ctxPath + '/js/flow/FlowFormEntityView.js'
	 ],
	 OutMailSetView:[
	    __ctxPath + '/js/system/OutMailSetForm.js',
        __ctxPath + '/js/system/OutMailSetView.js'
	 ],
	 ProInstanceMgr:[
	    __ctxPath + '/js/flow/ProInstanceMgr.js',
	    __ctxPath + '/js/flow/ProInstanceView.js',
	    __ctxPath + '/js/flow/ProInstanceDetail.js',
 		__ctxPath + '/js/flow/PathChangeWindow.js',
 		__ctxPath+'/js/flow/ProcessNextForm.js',
 		__ctxPath + '/js/flow/TaskHandlerWindow.js',
 	    __ctxPath + '/js/flow/TaskDueDateWindow.js'
	 ],
	 JforumView:[
        __ctxPath + '/js/info/JforumView.js'
	 ],
	 MyFileAttachView:[
	 	__ctxPath + '/js/system/MyFileAttachView.js'
	 ],
	 ReportTemplateMenu:[
   	 	__ctxPath + '/js/system/ReportTemplateMenu.js',
   	 	__ctxPath + '/js/system/ReportTemplatePreview.js'
	 ],
	
	 ComIndexPage:[
	 	__ctxPath + '/ext3/ux/Portal.js',
	 	__ctxPath + '/ext3/ux/PortalColumn.js',
	 	__ctxPath + '/ext3/ux/Portlet.js',
	 	__ctxPath + '/js/info/ComIndexPage.js'
	 ],
	 AppHome:[
	 	__ctxPath + '/ext3/ux/Portal.js',
	 	__ctxPath + '/ext3/ux/PortalColumn.js',
	 	__ctxPath + '/ext3/ux/Portlet.js',
	 	__ctxPath + '/js/App.home.js'
	 ],
	 DicManager : [
		__ctxPath + '/js/system/GlobalTypeForm.js',
		__ctxPath + '/js/system/DicManager.js',
		__ctxPath + '/js/system/DicTypeChangeWin.js',
		__ctxPath + '/js/core/ux/TreeCombo.js',
		__ctxPath + '/js/system/DictionaryForm.js'
	],
	
ProcessModuleView : [
    	__ctxPath+'/js/flow/ProcessModuleView.js',
    	__ctxPath+'/js/flow/ProcessModuleForm.js',
    	__ctxPath+'/js/selector/FlowSelector.js'
]

//  end:  Generated for ProcessModule From Template: App.import.js.vm

,OrgSettingView: [
	   __ctxPath+'/js/system/OrgSettingView.js',
	   __ctxPath+'/js/system/DemensionForm.js',
	   __ctxPath+'/js/system/PositionForm.js',
	   __ctxPath+'/js/system/OrganizationForm.js',
	   __ctxPath+'/js/system/CompanyWin.js',
	   __ctxPath+'/js/system/DepartmentWin.js',
	   __ctxPath+'/js/selector/RoleDialog.js',
	   __ctxPath+'/js/selector/PositionDialog.js',
	   __ctxPath+'/js/selector/UserDialog.js'
],
PositionUserView:[
      	__ctxPath+'/js/system/PositionUserView.js',
      	__ctxPath+'/js/system/PositionForm.js',
      	__ctxPath+'/js/selector/PositionDialog.js'
],
DepUserView:[
     	__ctxPath+'/js/system/DepUserView.js',
     	__ctxPath+'/js/system/DepForm.js',
     	__ctxPath+'/js/selector/UserDialog.js',
     	__ctxPath + '/js/system/RelativeUserView.js',
        __ctxPath + '/js/system/RelativeUserForm.js',
        __ctxPath + '/js/system/RelativeJobView.js',
        __ctxPath + '/js/system/RelativeJobForm.js'
],
UserFormPanel:[
       	__ctxPath+'/js/system/UserFormPanel.js',
       	__ctxPath+'/js/system/setPasswordForm.js'
]
//  start:  Generated for PositionSub From Template: App.import.js.vm
,PositionSubView : [
    	__ctxPath+'/js/system/PositionSubView.js',
    	__ctxPath+'/js/system/PositionSubForm.js'
]
,ProjectView : [
    	__ctxPath+'/js/project/ProjectView.js',
    	__ctxPath+'/js/project/ProjectForm.js'
]
,CourseView : [
    	__ctxPath+'/js/project/CourseView.js',
    	__ctxPath+'/js/project/CourseForm.js'
]
,CreditSettingView : [
    	__ctxPath+'/js/project/CreditSettingView.js',
    	__ctxPath+'/js/project/CreditForm.js'
]
,StandardSettingView : [
    	__ctxPath+'/js/project/StandardSettingView.js',
    	__ctxPath+'/js/project/StandardForm.js',
    	__ctxPath+'/js/project/CreditSelector.js'
]
,CheckView : [
    	__ctxPath+'/js/project/CheckView.js',
]
//  end:  Generated for PositionSub From Template: App.import.js.vm
//  start:  Generated for JxjyRyxfgl From Template: App.import.js.vm


//  end:  Generated for JxjyRyxfgl From Template: App.import.js.vm
//  start:  Generated for JxjyXmgl From Template: App.import.js.vm
,JxjyXmglView : [
    	__ctxPath+'/js/ryxf/JxjyXmglView.js',
    	__ctxPath+'/js/ryxf/JxjyXmglForm.js'
]

//  end:  Generated for JxjyXmgl From Template: App.import.js.vm
//  start:  Generated for JxjySfbzsz From Template: App.import.js.vm
,JxjySfbzszView : [
    	__ctxPath+'/js/ryxf/JxjySfbzszView.js',
    	__ctxPath+'/js/ryxf/SFMXForm.js',
    	__ctxPath+'/js/ryxf/SFMXSAVEForm.js',
    	__ctxPath+'/js/ryxf/JxjySfbzszForm.js'
]

//  end:  Generated for JxjySfbzsz From Template: App.import.js.vm
//  start:  Generated for JxjySfbzmx From Template: App.import.js.vm
,JxjySfbzmxView : [
    	__ctxPath+'/js/ryxf/JxjySfbzmxView.js',
    	__ctxPath+'/js/ryxf/JxjySfbzmxForm.js'
]

//  end:  Generated for JxjySfbzmx From Template: App.import.js.vm
};