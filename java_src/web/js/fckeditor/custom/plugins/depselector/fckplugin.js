// Register the related command.
FCKCommands.RegisterCommand( 'depselector', new FCKDialogCommand( 'depselector', FCKLang.DepSelectorDlgTitle, FCKPlugins.Items['depselector'].Path + 'fck_depselector.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'depselector', FCKLang.DepSelectorBtn ) ;
oSItem.IconPath = FCKPlugins.Items['depselector'].Path + 'department.png' ;
FCKToolbarItems.RegisterItem( 'depselector', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('isdepsel')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('depselector', FCKLang.DepSelectorDlgTitle,FCKToolbarItems.GetItem('depselector').IconPath);
		}
	}
});