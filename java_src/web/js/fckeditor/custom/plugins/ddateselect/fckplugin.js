// Register the related command.
FCKCommands.RegisterCommand( 'ddateselect', new FCKDialogCommand( 'ddateselect', FCKLang.DateSelctorDlgTitle, FCKPlugins.Items['ddateselect'].Path + 'fck_ddateselect.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'ddateselect', FCKLang.DateSelectorBtn ) ;
oSItem.IconPath = FCKPlugins.Items['ddateselect'].Path + 'date.png' ;
FCKToolbarItems.RegisterItem( 'ddateselect', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('__cfckdate')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('ddateselect', FCKLang.DateSelctorDlgTitle,FCKToolbarItems.GetItem('ddateselect').IconPath);
		}
	}
});