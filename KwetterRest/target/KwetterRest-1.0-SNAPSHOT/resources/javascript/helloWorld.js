/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
function getHelloWorld(username) {
    $.ajax({
        type: "GET",
        url: "http://localhost:8080/KwetterRest/webresources/HelloWorld/" + username,
        contentType: "application/json; charset=utf-8",
        dataType: "json",
        success: function(data, status, jqXHR) {
            var replyMessage = data.toString();
            $("#helloWorld").append("<p>" +replyMessage + "</p>");
        },
        error: function(jqXHR, status) {
            $("#helloWorld").append("Errored");
        }
    });
}

