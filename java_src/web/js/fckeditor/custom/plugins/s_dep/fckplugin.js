

// Register the related command.
FCKCommands.RegisterCommand( 's_dep', new FCKDialogCommand( 's_dep', FCKLang.SDepDlgTitle, FCKPlugins.Items['s_dep'].Path + 'fck_s_department.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 's_dep', FCKLang.SDepBtn ) ;
oSItem.IconPath = FCKPlugins.Items['s_dep'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 's_dep', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'BUTTON' && tag.getAttribute('department')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('s_dep', FCKLang.SDepDlgTitle,FCKToolbarItems.GetItem('s_dep').IconPath);
		}
	}
});