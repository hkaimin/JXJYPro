

// Register the related command.
FCKCommands.RegisterCommand( 'dgridcell', new FCKDialogCommand( 'dgridcell', FCKLang.TextareaProp, FCKPlugins.Items['dgridcell'].Path + 'fck_dgridcell.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dgridcell', FCKLang.TextareaProp,null,null,null,true,52 ) ;
//oSItem.IconPath = FCKPlugins.Items['dtextfield'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'dgridcell', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'TABLE' && tag.getAttribute('isgridcell')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dgridcell', FCKLang.TextareaProp, 52);
		}
	}
});