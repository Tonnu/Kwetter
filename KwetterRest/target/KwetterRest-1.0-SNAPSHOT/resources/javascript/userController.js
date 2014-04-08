/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var rootUrl = "http://localhost:8080/KwetterRest/webresources/user" ;

$('#btnSearch').click(function(){
    getUser($('#searchUser').val());
    return false;
});

$("#btnSearchAll").click(function(){
    getAll();
    return false;
});

$("#btnAddUser").click(function(){
    addUser();
    return false;
});

$("#btnUpdateUser").click(function(){
    updateUser();
    return false;
});


function getUser(username) {
    $.ajax({
        type: "GET",
        url: rootUrl + "/" + username,
        dataType: "json",
        success: renderList,
        error: function(jqXHR, status, errorThrown) {
            if (errorThrown === 'Not Found'){
                alert("User " + errorThrown);
            }
        }
    });
}

function getAll(){
    $.ajax({
        type: "GET",
        url: rootUrl,
        dataType: "json",
        success: renderList,
        error: function(jqXHR, status, errorThrown) {
            if (errorThrown === 'No Content'){
                alert("No Users Found");
            } else if (errorThrown === 'Internal Server Error'){
                alert("Problem finding users")
            }
        }
    });
}

function addUser() {
    data = formToJSON();
    $.ajax({
        type: "POST",
        url: rootUrl,
        contentType: 'application/json',
        dataType: "json",
        data: data,
        success: getAll(),
        error: function(jqXHR, textStatus, errorThrown) {
            if(errorThrown === 'Conflict'){
                alert("User bestaat al");
            }
        }
    });
}

function updateUser() {
    data = formToJSON();
    $.ajax({
        type: "PUT",
        url: rootUrl,
        contentType: 'application/json',
        dataType: "json",
        data: data,
        success: getAll(),
        error: function(jqXHR, textStatus, errorThrown) {
            if (errorThrown === 'Not Found'){
                alert("Ongeldige user");
            }
        }
    });
}

function renderList(data){
    var list = data === null ? [] : (data instanceof Array ? data : [data]);
    $("#selectedUser li").remove();
    $.each(list, function(index, user) {
        $("#selectedUser").append('<li> user: ' + user.userName + '<br /> first name: ' + user.firstName + ' || last name: ' + user.lastName + '<li>');
    });
}

function formToJSON(){
    var username = $("#username").val();
    return JSON.stringify({
       "userName": username === "" ? null : username,
       "firstName": $("#firstname").val(), 
       "lastName": $("#lastname").val() 
    });
}

// function renderList(data) {
//	// JAX-RS serializes an empty list as null, and a 'collection of one' as an object (not an 'array of one')
//	var list = data == null ? [] : (data.wine instanceof Array ? data.wine : [data.wine]);
//
//	$('#wineList li').remove();
//	$.each(list, function(index, wine) {
//		$('#wineList').append('<li><a href="#" data-identity="' + wine.id + '">'+wine.name+'</a></li>');
//	});
//}


