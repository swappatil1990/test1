function httpGet(theUrl)
{
	
	var xmlHttp = new XMLHttpRequest();
	xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
	xmlHttp.send( null );
	return xmlHttp.responseText;
}

(function( $ ) {

	'use strict';
	var objectId ="";
	var EditableTable = {
			 
		options: {
			addButton: '#addToTable',
			addExistingButton: '#addExistingToTable',
			table: '#datatable-editable',
			dialog: {
				wrapper: '#dialog',
				cancelButton: '#dialogCancel',
				confirmButton: '#dialogConfirm',
			},
			dialogRemove: {
				wrapperRemove: '#dialogRemove',
				cancelButtonRemove: '#dialogRemoveCancel',
				confirmButtonRemove: '#dialogRemoveConfirm',
			}
	
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

			// dialog
			this.dialog				= {};
			this.dialog.$wrapper	= $( this.options.dialog.wrapper );
			this.dialog.$cancel		= $( this.options.dialog.cancelButton );
			this.dialog.$confirm	= $( this.options.dialog.confirmButton );
			
			this.$addExistingButton			= $( this.options.addExistingButton );

			// dialogRemove
			this.dialogRemove				= {};
			this.dialogRemove.$wrapperRemove	= $( this.options.dialogRemove.wrapperRemove );
			this.dialogRemove.$cancelRemove		= $( this.options.dialogRemove.cancelButtonRemove );
			this.dialogRemove.$confirmRemove	= $( this.options.dialogRemove.confirmButtonRemove );

			return this;
		},
		
		showDialog: function() {
			
		},
		
		build: function() {
			nullColumn[nullColumn.length]={ "bSortable": false };
			this.datatable = this.$table.DataTable({
				aoColumns: nullColumn
			});

			window.dt = this.datatable;

			return this;
		},

		events: function() {
			var _self = this;

			this.$table
				.on('click', 'a.save-row', function( e ) {
					e.preventDefault();
					
					var columnData = "";
					var rowData = $(this).closest( 'tr' ).find('td');
					var i=0;
					debugger;
					for(i=0; i<rowData.length;i++)
					{
						if(rowData[i].childNodes.length!=0 && rowData[i].childNodes[0].nodeName=="A")
						{
							columnData=columnData+rowData[i].childNodes[0].innerHTML+",";
						}
						else if(rowData[i].childNodes[0]==undefined)
						{
							columnData=columnData+",";
						}
						else if(rowData[i].childNodes[0].value==undefined)
						{
							columnData=columnData+rowData[i].childNodes[0].data+",";
						}
						else
						{
							columnData=columnData+rowData[i].childNodes[0].value+",";
						}
					};
					var fullURL=document.location.href;
					var strUrl = "";
					if(fullURL.includes("table.jsp"))
					{
						strUrl = document.location.href.split("table.jsp")[0]+"API/saveTableRow.jsp?parentDataId="+parentDataId+"&columns="+tableColumns+"&columnData="+columnData+"&objectType="+objectType;
					}
					else
					{
						strUrl = document.location.href.split("relatedTable.jsp")[0]+"API/saveTableRow.jsp?parentDataId="+parentDataId+"&columns="+tableColumns+"&columnData="+columnData+"&objectType="+objectType;
					}
					
					var result = httpGet(encodeURI(strUrl));
					objectId = result.replace("true", "");
					if(result.indexOf("true")!=-1)
					{
						_self.rowSave( $(this).closest( 'tr' ) );
					}
					else
					{
						_self.rowCancel( $(this).closest( 'tr' ) );
					}
				})
				.on('click', 'a.cancel-row', function( e ) {
					e.preventDefault();

					_self.rowCancel( $(this).closest( 'tr' ) );
				})
				.on('click', 'a.edit-row', function( e ) {
					e.preventDefault();

					_self.rowEdit( $(this).closest( 'tr' ) );
				})
				.on( 'click', 'a.remove-row', function( e ) {
					e.preventDefault();

					var $row = $(this).closest( 'tr' );

					$.magnificPopup.open({
						items: {
							src: '#dialogRemove',
							type: 'inline'
						},
						preloader: false,
						modal: true,
						callbacks: {
							change: function() {
								_self.dialogRemove.$confirmRemove.on( 'click', function( e ) {
									e.preventDefault();
									debugger;
									var objectDataId="";
									if($row[0].cells[0].childNodes[0].nodeName=="A")
										objectDataId=$row[0].cells[0].childNodes[0].innerHTML;
									else
										objectDataId=$row[0].cells[0].childNodes[0].data;
									
									var fullURL=document.location.href;
									var strUrl = "";
									if(fullURL.includes("table.jsp"))
									{
										strUrl = document.location.href.split("table.jsp")[0]+"API/removeTableRow.jsp?objectDataId="+objectDataId+"&objectTypeName="+objectType+"&parentDataId="+parentDataId;
									}
									else
									{
										strUrl = document.location.href.split("relatedTable.jsp")[0]+"API/removeTableRow.jsp?objectDataId="+objectDataId+"&objectTypeName="+objectType+"&parentDataId="+parentDataId;
									}
									
									var result = httpGet(encodeURI(strUrl));
									
									_self.rowRemove( $row );
									
									$.magnificPopup.close();
								});
							},
							close: function() {
								_self.dialogRemove.$confirmRemove.off( 'click' );
							}
						}
					});
				})
				.on( 'click', 'a.delete-row', function( e ) {
					e.preventDefault();

					var $row = $(this).closest( 'tr' );

					$.magnificPopup.open({
						items: {
							src: '#dialog',
							type: 'inline'
						},
						preloader: false,
						modal: true,
						callbacks: {
							change: function() {
								_self.dialog.$confirm.on( 'click', function( e ) {
									e.preventDefault();
									debugger;
									var objectDataId="";
									if($row[0].cells[0].childNodes[0].nodeName=="A")
										objectDataId=$row[0].cells[0].childNodes[0].innerHTML;
									else
										objectDataId=$row[0].cells[0].childNodes[0].data;
									
									var fullURL=document.location.href;
									var strUrl = "";
									if(fullURL.includes("table.jsp"))
									{
										strUrl = document.location.href.split("table.jsp")[0]+"API/deleteTableRow.jsp?objectDataId="+objectDataId+"&objectTypeName="+objectType;
									}
									else
									{
										strUrl = document.location.href.split("relatedTable.jsp")[0]+"API/deleteTableRow.jsp?objectDataId="+objectDataId+"&objectTypeName="+objectType;
									}
									
									var result = httpGet(encodeURI(strUrl));
									
									_self.rowRemove( $row );
									$.magnificPopup.close();
								});
							},
							close: function() {
								_self.dialog.$confirm.off( 'click' );
							}
						}
					});
				});

			this.$addButton.on( 'click', function(e) {
				e.preventDefault();

				_self.rowAdd();
			});

			this.dialog.$cancel.on( 'click', function( e ) {
				e.preventDefault();
				$.magnificPopup.close();
			});
			
			this.$addExistingButton.on( 'click', function(e) {
				e.preventDefault();
				
				$.magnificPopup.open({
					items: {
						src: '#dialogRemove',
						type: 'inline'
					},
					preloader: false,
					modal: true,
					callbacks: {
						change: function() {
							_self.dialogRemove.$confirmRemove.on( 'click', function( e ) {
								e.preventDefault();
								
								_self.rowRemove( $row );
								$.magnificPopup.close();
							});
						},
						close: function() {
							_self.dialogRemove.$confirm.off( 'click' );
						}
					}
				});
			});

			this.dialogRemove.$cancelRemove.on( 'click', function( e ) {
				e.preventDefault();
				debugger;
				$.magnificPopup.close();
			});

			return this;
		},

		// ==========================================================================================
		// ROW FUNCTIONS
		// ==========================================================================================
		rowAdd: function() {
			this.$addButton.attr({ 'disabled': 'disabled' });
			var actions,
				data,
				$row;

			actions = [
				'<a href="#" class="hidden on-editing save-row"><i class="fa fa-save"></i></a>',
				'<a href="#" class="hidden on-editing cancel-row"><i class="fa fa-times"></i></a>',
				'<a href="#" class="on-default edit-row"><i class="fa fa-pencil"></i></a>',
				'<a href="#" class="on-default delete-row"><i class="fa fa-trash-o"></i></a>',
				'<a href="#" class="on-default remove-row"><i class="fa fa-unlink"></i></a>'
			].join(' ');
			
			var cnt=0;
			var emptyColumns=[];
			for(cnt=0;cnt<blankColumn.length;cnt++)
				emptyColumns[cnt]='';
			
			emptyColumns[blankColumn.length]=actions;
			data = this.datatable.row.add(emptyColumns);
			$row = this.datatable.row( data[0] ).nodes().to$();
			
			for(cnt=0;cnt<blankColumn.length;cnt++)
				$row[0].cells[cnt].className=blankColumn[cnt];
			
			
			$row
				.addClass( 'adding' )
				.find( 'td:last' )
				.addClass( 'actions' );

			this.rowEdit( $row );

			this.datatable.order([0,'asc']).draw(); // always show fields
		},
		
		rowCancel: function( $row ) {
			var _self = this,
				$actions,
				i,
				data;

			if ( $row.hasClass('adding') ) {
				this.rowRemove( $row );
			} else {

				data = this.datatable.row( $row.get(0) ).data();
				this.datatable.row( $row.get(0) ).data( data );

				$actions = $row.find('td.actions');
				if ( $actions.get(0) ) {
					this.rowSetActionsDefault( $row );
				}
				
				this.datatable.draw();
			}
		},

		rowEdit: function( $row ) {
			var _self = this,
				data;
			
			data = this.datatable.row( $row.get(0) ).data();

			$row.children( 'td' ).each(function( i ) {
				var $this = $( this );

				if ( $this.hasClass('actions') || $this.hasClass('noEdit')) {
					_self.rowSetActionsEditing( $row );
				}
				else if ($this.hasClass('passwordField')) {
					$this.html( '<input type="password" class="form-control input-block" value="' + data[i] + '"/>' );
				}
				else {
					$this.html( '<input type="text" class="form-control input-block" value="' + data[i] + '"/>' );
				}
			});
		},

		rowSave: function( $row ) {
			debugger;
			var objectIdNode = document.createTextNode(objectId);    
			//$row[0].cell[0].innerHTML = objectId;
			var _self     = this,
				$actions,
				values    = [];

			if ( $row.hasClass( 'adding' ) ) {
				this.$addButton.removeAttr( 'disabled' );
				$row.removeClass( 'adding' );
			}

			values = $row.find('td').map(function() {
				var $this = $(this);

				if ( $this.hasClass('actions') ) {
					_self.rowSetActionsDefault( $row );
					return _self.datatable.cell( this ).data();
				} else {
					return $.trim( $this.find('input').val() );
				}
			});

			this.datatable.row( $row.get(0) ).data( values );

			$row[0].cells[0].appendChild(objectIdNode);
			$actions = $row.find('td.actions');
			if ( $actions.get(0) ) {
				this.rowSetActionsDefault( $row );
			}

			this.datatable.draw();
		},

		rowRemove: function( $row ) {
			if ( $row.hasClass('adding') ) {
				this.$addButton.removeAttr( 'disabled' );
			}

			this.datatable.row( $row.get(0) ).remove().draw();
		},

		rowSetActionsEditing: function( $row ) {
			$row.find( '.on-editing' ).removeClass( 'hidden' );
			$row.find( '.on-default' ).addClass( 'hidden' );
		},

		rowSetActionsDefault: function( $row ) {
			$row.find( '.on-editing' ).addClass( 'hidden' );
			$row.find( '.on-default' ).removeClass( 'hidden' );
		}

	};

	$(function() {
		EditableTable.initialize();
	});

}).apply( this, [ jQuery ]);