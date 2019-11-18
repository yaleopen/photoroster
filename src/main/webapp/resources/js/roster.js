
function toggleNames(){
	
	var ele = document.getElementById("roster_form:roster_form_hide_names");
 
	if (ele.value=="Hide Names"){
		ele.value = "Show Names";
		$(".displayed_name").css('display', 'none');
	}
	else{
		ele.value="Hide Names";
		$(".displayed_name").css('display', 'block');
			
	}
	
	
}

function clear_picture(){
	$('.picture_search_field').val("");
}

function clear_overview(){
	$('.overview_search_field').val("");
}

function find_overview(){
	$('.overview_search_field').val("");
}

function makeReadOnly(){
	console.log(" see the readonly "+$('input.add_update_button').val());
	if ($('input.add_update_button').val()=="Update"){
		$('input.rowKey').prop("readonly", true);
		$('input.rowKey').css('background-color', '#FAFAD2');
	}
	else{
		$('input.rowKey').prop("readonly", false);

		$('input.rowKey').css('background-color', 'white');
	}
}

function changeSection(page){
	var loc = window.location;
	var paths = window.location.pathname.split('/');
	var context = window.location.protocol+"//"+window.location.host+"/"+paths[1]+"/"+page;
	window.location.href=context;
	submit();
}

$(document).ready(function(){
	
	$('.picture_search_field').keypress(function(e){
		if (e.which == 13){
			e.preventDefault();
			return false;
	   }
	});
	
	$('.overview_search_field').keypress(function(e){
		if (e.which == 13){
			e.preventDefault();
			return false;
	    }
	});
	
	$('#overview_table tr').mouseover(function(){
		this.className='rosterTableRowHighlight';
	});
	
	$('#overview_table tr').mouseout(function(){
		this.className='rosterTableRow';
	});
	
	$('.overview_search_field').bind('input', function(){
		  
		 

	}) ;
	
	
	 $("table.admin tr:even").css("background-color", "#ffffff");
	 $("table.admin tr:odd").css("background-color", "#f0ffff");
	 

	 $("table.user tr:even").css("background-color", "#ffffff");
	 $("table.user tr:odd").css("background-color", "#f0ffff");
	  
	 makeReadOnly(); 
});