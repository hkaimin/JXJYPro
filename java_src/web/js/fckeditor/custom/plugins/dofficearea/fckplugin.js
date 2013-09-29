// Register the related command.
FCKCommands.RegisterCommand( 'dofficearea', new FCKDialogCommand( 'dofficearea', FCKLang.OfficeEditorPro, FCKPlugins.Items['dofficearea'].Path + 'fck_dofficearea.html', 400, 250 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dofficearea', FCKLang.OfficeEditorBtn) ;
oSItem.IconPath = FCKPlugins.Items['dofficearea'].Path + 'office.png' ;
FCKToolbarItems.RegisterItem( 'dofficearea', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'TEXTAREA' && tag.getAttribute('isofficepanel')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dofficearea', FCKLang.OfficeEditorPro, FCKToolbarItems.GetItem('dofficearea').IconPath);
		}
	}
});