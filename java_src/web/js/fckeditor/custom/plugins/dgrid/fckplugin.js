

// Register the related command.
FCKCommands.RegisterCommand( 'dgrid', new FCKDialogCommand( 'dgrid', FCKLang.DgridProp, FCKPlugins.Items['dgrid'].Path + 'fck_dgrid.html?Parent2', 440, 160 ) ) ;
// Create the "Plaholder" toolbar button.
var oSItem = new FCKToolbarButton( 'dgrid', FCKLang.DgridButton) ;
oSItem.IconPath = FCKPlugins.Items['dgrid'].Path + 'grid.png' ;
FCKToolbarItems.RegisterItem( 'dgrid', oSItem ) ;

FCKSelection.IsFormNode = function() {
	var B = this.GetSelectedElement();
	if (!B && FCK.EditorWindow) {
		try {
			B = this.GetSelection().getRangeAt(0).startContainer;
		} catch (e) {
		}
	}
	while (B) {
		if (B.nodeType == 1 && B.nodeName.IEquals('TABLE')) {
			if (B.getAttribute('isgrid') == 'false') {
				return true;
			}
			return false;
		}
		B = B.parentNode;
		if (!B) {
			break;
		}
	};
	return false;
};

FCKSelection.GetTableType = function() {
	var B = this.GetSelectedElement();
	if (!B && FCK.EditorWindow) {
		try {
			B = this.GetSelection().getRangeAt(0).startContainer;
		} catch (e) {
		}
	}
	while (B) {
		if (B.nodeType == 1 && B.nodeName.IEquals('TABLE')) {
			if (B.getAttribute('isgrid') == 'true') {
				return 'grid';
			}else if(B.getAttribute('isgrid') == 'false'){
			    return 'form';
			}else{
			   return 'table';
			}
		}
		B = B.parentNode;
		if (!B) {
			break;
		}
	};
	return 'table';
};

// 右键
FCK.ContextMenu.RegisterListener( {
	AddItems : function(menu, tag, tagName) {
        var bIsTable	= ( tagName == 'TABLE' ) ;
        var IsGridNode=FCKSelection.IsGridNode();
		var bIsCell		= ( !bIsTable && FCKSelection.HasAncestorNode( 'TABLE' )&&IsGridNode ) ;
		var type=FCKSelection.GetTableType();
		if ( bIsCell)
		{
			menu.AddSeparator() ;
			var oItem = menu.AddItem( 'Cell'	, FCKLang.CellCM ) ;
			if(type=='form'){
				oItem.AddItem( 'TableInsertCellBefore'	, FCKLang.InsertCellBefore, 69 ) ;
				oItem.AddItem( 'TableInsertCellAfter'	, FCKLang.InsertCellAfter, 58 ) ;
				oItem.AddItem( 'TableDeleteCells'	, FCKLang.DeleteCells, 59 ) ;
				if ( FCKBrowserInfo.IsGecko )
					oItem.AddItem( 'TableMergeCells'	, FCKLang.MergeCells, 60,
						FCKCommands.GetCommand( 'TableMergeCells' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				else
				{
					oItem.AddItem( 'TableMergeRight'	, FCKLang.MergeRight, 60,
						FCKCommands.GetCommand( 'TableMergeRight' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				}
				oItem.AddItem( 'TableHorizontalSplitCell'	, FCKLang.HorizontalSplitCell, 61,
					FCKCommands.GetCommand( 'TableHorizontalSplitCell' ).GetState() == FCK_TRISTATE_DISABLED ) ;
				oItem.AddSeparator() ;
			}
			oItem.AddItem( 'TableCellProp'		, FCKLang.CellProperties, 57,
				FCKCommands.GetCommand( 'TableCellProp' ).GetState() == FCK_TRISTATE_DISABLED ) ;
            if(type=='form'){
                    menu.AddSeparator() ;
					oItem = menu.AddItem( 'Row'			, FCKLang.RowCM ) ;
					oItem.AddItem( 'TableInsertRowBefore'		, FCKLang.InsertRowBefore, 70 ) ;
					oItem.AddItem( 'TableInsertRowAfter'		, FCKLang.InsertRowAfter, 62 ) ;
					oItem.AddItem( 'TableDeleteRows'	, FCKLang.DeleteRows, 63 ) ;
            }
			menu.AddSeparator() ;
			oItem = menu.AddItem( 'Column'		, FCKLang.ColumnCM ) ;
			oItem.AddItem( 'TableInsertColumnBefore', FCKLang.InsertColumnBefore, 71 ) ;
			oItem.AddItem( 'TableInsertColumnAfter'	, FCKLang.InsertColumnAfter, 64 ) ;
			oItem.AddItem( 'TableDeleteColumns'	, FCKLang.DeleteColumns, 65 ) ;
		}
		if ( bIsTable || bIsCell )
		{
			menu.AddSeparator() ;
			menu.AddItem( 'TableDelete'			, FCKLang.TableDelete ) ;
			menu.AddItem( 'dgrid'			, FCKLang.DgridProp,FCKToolbarItems.GetItem('dgrid').IconPath ) ;
		}
	}
});