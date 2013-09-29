// Register the related command.
FCKCommands.RegisterCommand( 'diccombo', new FCKDialogCommand( 'diccombo', FCKLang.DicComboDlgTitle, FCKPlugins.Items['diccombo'].Path + 'fck_diccombo.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'diccombo', FCKLang.DicComboBtn ) ;
oSItem.IconPath = FCKPlugins.Items['diccombo'].Path + 'diccombo.png' ;
FCKToolbarItems.RegisterItem( 'diccombo', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('isdic')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('diccombo', FCKLang.DicComboDlgTitle,FCKToolbarItems.GetItem('diccombo').IconPath);
		}
	}
});