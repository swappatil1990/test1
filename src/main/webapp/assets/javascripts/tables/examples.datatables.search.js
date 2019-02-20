function httpGet(theUrl)
{
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
	xmlHttp.send( null );
	return xmlHttp.responseText;
}

(function( $ ) {
	var selectedDataId="";
	'use strict';
	var objectId ="";
	var EditableTable = {
			 
		options: {
			addButton: '#addToTable',
			cancelAddExisting: '#cancelAddExisting',
			table: '#datatable-editable',
			dialog: {
				wrapper: '#dialog',
				cancelButton: '#dialogCancel',
				confirmButton: '#dialogConfirm',
			},
	
		},
		
		initialize: function() {
			this
				.setVars()
				.build()
				.events();
		},

		setVars: function() {
			this.$table				= $( this.options.table );
			this.$addButton			= $( this.options.addButton );
			this.$cancelAddExisting	= $( this.options.cancelAddExisting );

			// dialog
			this.dialog				= {};
			this.dialog.$wrapper	= $( this.options.dialog.wrapper );
			this.dialog.$cancel		= $( this.options.dialog.cancelButton );
			this.dialog.$confirm	= $( this.options.dialog.confirmButton );
			
			this.$addExistingButton			= $( this.options.addExistingButton );

			return this;
		},
		
		showDialog: function() {
			
		},
		
		build: function() {
			nullColumn[nullColumn.length]={  "visible": false,"bSortable": false };
			this.datatable = this.$table.DataTable({
				aoColumns: nullColumn
			});

			window.dt = this.datatable;

			return this;
		},

		events: function() {
			var _self = this;

			this.$table
				.on( 'click', 'tr', function () {
					$(this).toggleClass('selected');
					debugger;
					
					var cnt = $(this).closest( 'tbody' ).find('tr.selected').length;
					if(cnt>0)
						selectedDataId=$(this).closest( 'tbody' ).find('tr.selected')[0].childNodes[1].innerHTML;
					if((rowSelection=="single" && cnt==2))
					{
						$(this).toggleClass('selected');
						alert("Select only single row.")
					}
					
					 //alert(  $(this).closest( 'tbody' ).find('tr.selected').length +' row(s) selected' );
				} );

			this.$addButton.on( 'click', function(e) {
				e.preventDefault();

				_self.AddExisting();
			});
			
			this.$cancelAddExisting.on( 'click', function(e) {
				e.preventDefault();

				_self.CancelAddExisting();
			});

			this.dialog.$cancel.on( 'click', function( e ) {
				e.preventDefault();
				$.magnificPopup.close();
			});
			

			return this;
		},

		// ==========================================================================================
		// ROW FUNCTIONS
		// ==========================================================================================
		AddExisting: function() {
			var strUrl = document.location.href.split("searchTable.jsp")[0]+"API/addExistingTableRow.jsp?selectedDataId="+selectedDataId+"&objectTypeName="+objectType+"&parentDataId="+parentDataId;
			var result = httpGet(strUrl);
			parent.location.href = lastUrl;
		},
		
		CancelAddExisting: function() {
			parent.location.href = lastUrl;
		}

	};

	$(function() {
		EditableTable.initialize();
	});

}).apply( this, [ jQuery ]);