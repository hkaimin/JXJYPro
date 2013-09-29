
var FckToolbarTemplateCombo=function(A,B){
    if (A === false)
		return;
    this.CommandName = 'dtemplates';
	this.Label = this.GetLabel();
	this.Tooltip = A ? A : this.Label;
    this.Style = B ? B : 2;
    this.DefaultLabel = '';
};
FckToolbarTemplateCombo.prototype=new FCKToolbarSpecialCombo;
FckToolbarTemplateCombo.prototype.GetLabel = function() {
	return FCKLang.Template;
};

FckToolbarTemplateCombo.prototype.getTemplates=function(){
   LoadTemplatesXml();
   return FCK._DTemplates;
};

FckToolbarTemplateCombo.prototype.CreateItems = function(A) {
	var B = A._Panel.Document;
	FCKTools.AppendStyleSheet(B, FCKConfig.ToolbarComboPreviewCSS);
	FCKTools.AppendStyleString(B, FCKConfig.EditorAreaStyles);
	B.body.className += ' ForceBaseFont';
	FCKConfig.ApplyBodyAttributes(B.body);
	var C = this.getTemplates();
	for (var D=0;D< C.length;D++) {
		var E = C[D];
		A.AddItem(D,E.Title,E.Title);
	};
	
};
FckToolbarTemplateCombo.prototype.RefreshActiveItems = function(A) {
//	var B = FCK.ToolbarSet.CurrentInstance.Selection
//			.GetBoundaryParentElement(true);
//	if (B) {
//		var C = new FCKElementPath(B);
//		var D = C.Elements;
//		for (var e = 0; e < D.length; e++) {
//			for (var i in A.Items) {
//				var E = A.Items[i];
//				var F = E.Style;
//				if (F.CheckElementRemovable(D[e], true)) {
//					A.SetLabel(F.Label || F.Name);
//					return;
//				}
//			}
//		}
//	};
	A.SetLabel(this.DefaultLabel);
};

function LoadTemplatesXml()
{
	var oTemplate ;

	if ( !FCK._DTemplates )
	{
		// Create the Templates array.
		FCK._DTemplates = new Array() ;

		// Load the XML file.
		var oXml = new FCKXml() ;
		oXml.LoadUrl(FCKConfig.DTemplatesXmlPath ) ;

		// Get the Images Base Path.
		var oAtt = oXml.SelectSingleNode( 'Templates/@imagesBasePath' ) ;
		var sImagesBasePath = oAtt ? oAtt.value : '' ;

		// Get the "Template" nodes defined in the XML file.
		var aTplNodes = oXml.SelectNodes( 'Templates/Template' ) ;

		for ( var i = 0 ; i < aTplNodes.length ; i++ )
		{
			var oNode = aTplNodes[i] ;

			oTemplate = new Object() ;

			var oPart ;

			// Get the Template Title.
			if ( (oPart = oNode.attributes.getNamedItem('title')) )
				oTemplate.Title = oPart.value ;
			else
				oTemplate.Title = 'Template ' + ( i + 1 ) ;

			// Get the Template Description.
			if ( (oPart = oXml.SelectSingleNode( 'Description', oNode )) )
				oTemplate.Description = oPart.text ? oPart.text : oPart.textContent ;

			// Get the Template Image.
			if ( (oPart = oNode.attributes.getNamedItem('image')) )
				oTemplate.Image = sImagesBasePath + oPart.value ;

			// Get the Template HTML.
			if ( (oPart = oXml.SelectSingleNode( 'Html', oNode )) )
				oTemplate.Html = oPart.text ? oPart.text : oPart.textContent ;
			else
			{
				alert( 'No HTML defined for template index ' + i + '. Please review the "' + FCKConfig.TemplatesXmlPath + '" file.' ) ;
				continue ;
			}

			FCK._DTemplates[ FCK._DTemplates.length ] = oTemplate ;
		}

	}

};


var FckTemplateCommand = function(){};
FckTemplateCommand.prototype = {
	Name : 'dtemplates',
	Execute : function(A, B) {
		FCKUndo.SaveUndoStep();
		FCK.SetData( FCK._DTemplates[A].Html ) ;
	},
	GetState : function() {
		if (FCK.EditMode != 0 || !FCK.EditorDocument)
			return -1;
		return 0;
	}
};



FCKCommands.RegisterCommand( 'dtemplates',new FckTemplateCommand());
FCKToolbarItems.RegisterItem( 'dtemplates',new FckToolbarTemplateCombo() );