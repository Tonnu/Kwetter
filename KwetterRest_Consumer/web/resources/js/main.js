/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$('#userinput').keyup(function(){
       $.ajax({
        success: function(jqXHR, status, errorThrown) {
            $('#userinput').focus(); 
        }
    });
       
});