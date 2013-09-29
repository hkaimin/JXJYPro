// Register the related command.
FCKCommands.RegisterCommand( 'dfileattach', new FCKDialogCommand( 'dfileattach', FCKLang.FileAttachDlgTitle, FCKPlugins.Items['dfileattach'].Path + 'fck_dfileattach.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'dfileattach', FCKLang.FileAttachBtn ) ;
oSItem.IconPath = FCKPlugins.Items['dfileattach'].Path + 'file.png' ;
FCKToolbarItems.RegisterItem( 'dfileattach', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('isfileattach')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('dfileattach', FCKLang.FileAttachDlgTitle,FCKToolbarItems.GetItem('dfileattach').IconPath);
		}
	}
});