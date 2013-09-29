// Register the related command.
FCKCommands.RegisterCommand( 'dradiobutton', new FCKDialogCommand( 'dradiobutton', FCKLang.RadioButtonProp, FCKPlugins.Items['dradiobutton'].Path + 'fck_dradiobutton.html', 340, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dradiobutton', FCKLang.RadioButtonProp,null,null,null,true,50 ) ;
FCKToolbarItems.RegisterItem( 'dradiobutton', oSItem ) ;


// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
		if (tagName == 'INPUT' && tag.type == 'radio'&& tag.getAttribute('cfckradio')) {
			menu.RemoveAllItems();
			menu.AddItem( 'Cut'		, FCKLang.Cut	, 7, FCKCommands.GetCommand( 'Cut' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Copy'	, FCKLang.Copy	, 8, FCKCommands.GetCommand( 'Copy' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem( 'Paste'	, FCKLang.Paste	, 9, FCKCommands.GetCommand( 'Paste' ).GetState() == FCK_TRISTATE_DISABLED ) ;
			menu.AddItem('dradiobutton', FCKLang.RadioButtonProp, 50);
		}
	}
});