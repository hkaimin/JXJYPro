

// Register the related command.
FCKCommands.RegisterCommand( 'dtextfield', new FCKDialogCommand( 'dtextfield', FCKLang.TextFieldProp, FCKPlugins.Items['dtextfield'].Path + 'fck_dtextfield.html', 400, 250 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dtextfield', FCKLang.TextFieldProp,null,null,null,true,51 ) ;
//oSItem.IconPath = FCKPlugins.Items['dtextfield'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'dtextfield', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.getAttribute('isNew')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dtextfield', FCKLang.TextFieldProp, 51);
		}
	}
});