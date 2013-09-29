// Register the related command.
FCKCommands.RegisterCommand( 'dselectfield', new FCKDialogCommand( 'dselectfield', FCKLang.SelectionFieldProp, FCKPlugins.Items['dselectfield'].Path + 'fck_dselectfield.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dselectfield', FCKLang.SelectionFieldProp,null,null,null,true,53 ) ;
FCKToolbarItems.RegisterItem( 'dselectfield', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'SELECT' && tag.getAttribute('isNew')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dselectfield', FCKLang.SelectionFieldProp, 53);
		}
	}
});