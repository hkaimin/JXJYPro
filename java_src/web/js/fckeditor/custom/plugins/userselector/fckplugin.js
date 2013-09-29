// Register the related command.
FCKCommands.RegisterCommand( 'userselector', new FCKDialogCommand( 'userselector', FCKLang.UserSelectorDlgTitle, FCKPlugins.Items['userselector'].Path + 'fck_userselector.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'userselector', FCKLang.UserSelectorBtn ) ;
oSItem.IconPath = FCKPlugins.Items['userselector'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'userselector', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('isusersel')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('userselector', FCKLang.UserSelectorDlgTitle,FCKToolbarItems.GetItem('userselector').IconPath);
		}
	}
});