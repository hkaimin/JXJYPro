
// Register the related command.
FCKCommands.RegisterCommand( 'dhiddenfield', new FCKDialogCommand( 'dhiddenfield', FCKLang.HiddenFieldProp, FCKPlugins.Items['dhiddenfield'].Path + 'fck_dhiddenfield.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dhiddenfield', FCKLang.HiddenFieldProp,null,null,null,true,56 ) ;
//oSItem.IconPath = FCKPlugins.Items['dtextfield'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'dhiddenfield', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'IMG' && tag.getAttribute('FCK__InputHidden')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dhiddenfield', FCKLang.HiddenFieldProp, 56);
		}
	}
});