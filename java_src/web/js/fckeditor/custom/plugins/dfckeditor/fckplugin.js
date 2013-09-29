// Register the related command.
FCKCommands.RegisterCommand( 'dfckeditor', new FCKDialogCommand( 'dfckeditor', FCKLang.FCKEditorDlgTitle, FCKPlugins.Items['dfckeditor'].Path + 'fck_dfckeditor.html', 380, 210 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem =new FCKToolbarButton( 'dfckeditor', FCKLang.FCKEditorBtn ) ;
oSItem.IconPath = FCKPlugins.Items['dfckeditor'].Path + 'fckeditor.png' ;
FCKToolbarItems.RegisterItem( 'dfckeditor', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'TEXTAREA' && tag.getAttribute('isFCK')) {
				menu.RemoveAllItems();
				menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				menu.AddItem('dfckeditor', FCKLang.FCKEditorDlgTitle,FCKToolbarItems.GetItem('dfckeditor').IconPath);
		}
	}
});