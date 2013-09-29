
// Register the related command.
FCKCommands.RegisterCommand( 'dcheckbox', new FCKDialogCommand( 'dcheckbox', FCKLang.CheckboxProp, FCKPlugins.Items['dcheckbox'].Path + 'fck_dcheckbox.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dcheckbox', FCKLang.CheckboxProp,null,null,null,true,49 ) ;
//oSItem.IconPath = FCKPlugins.Items['dtextfield'].Path + 'user.jpg' ;
FCKToolbarItems.RegisterItem( 'dcheckbox', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.type == 'checkbox'&& tag.getAttribute('cfckcheckbox')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dcheckbox', FCKLang.CheckboxProp, 49);
		}
	}
});