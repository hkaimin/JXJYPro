

// Register the related command.
FCKCommands.RegisterCommand( 's_users', new FCKDialogCommand( 's_users', FCKLang.SUsersDlgTitle, FCKPlugins.Items['s_users'].Path + 'fck_s_users.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 's_users', FCKLang.SUsersBtn ) ;
oSItem.IconPath = FCKPlugins.Items['s_users'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 's_users', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'BUTTON' && tag.getAttribute('users')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('s_users', FCKLang.SUsersDlgTitle,FCKToolbarItems.GetItem('s_users').IconPath);
		}
	}
});