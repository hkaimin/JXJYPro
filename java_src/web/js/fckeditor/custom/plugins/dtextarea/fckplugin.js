

// Register the related command.
FCKCommands.RegisterCommand( 'dtextarea', new FCKDialogCommand( 'dtextarea', FCKLang.TextareaProp, FCKPlugins.Items['dtextarea'].Path + 'fck_dtextarea.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dtextarea', FCKLang.TextareaProp,null,null,null,true,52 ) ;
//oSItem.IconPath = FCKPlugins.Items['dtextfield'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'dtextarea', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'TEXTAREA' && tag.getAttribute('isNew')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dtextarea', FCKLang.TextareaProp, 52);
		}
	}
});