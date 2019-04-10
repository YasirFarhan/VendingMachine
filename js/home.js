$(document).ready(function () {
dipplayAllItem();
var moneyAdded = 0;
$('#amount').val(moneyAdded);
$('#dollarsIn').empty();
$('#itemNum').val(0);
/////// track number of deposited coins
var dollarsAdded=0;
var quartersAdded=0;
var dimesAdded=0;
var nickelsAdded=0;

$('#changeReturn').click(function(event){
$('#change').empty();
var change='';
if (dollarsAdded>0) {    quartersAdded=dollarsAdded*4;    }
if (quartersAdded>0){    change+=quartersAdded+' Quarters '; }
if (dimesAdded>0) {    change+=dimesAdded+' Dimes   ';  }
if (nickelsAdded>0) {    change+=nickelsAdded+' Nickels   ';  }
$('#change').append(change);
console.log(change);
$('#amount').val(0);
$('#dollarsIn').empty();
$('#message').empty();
$('#itemNum').val(0);
$('#item').empty();
moneyAdded = 0;
dollarsAdded=0;
quartersAdded=0;
dimesAdded=0;
nickelsAdded=0;
});
///////////////////// add money buttons/////////////
$('#addDollar').click(function(event){
moneyAdded=moneyAdded+1.00;
dollarsAdded++;
updateAmount(moneyAdded);
      });

$('#addQuarter').click(function(event){
moneyAdded=moneyAdded+0.25;
quartersAdded++;
updateAmount(moneyAdded);
});
$('#addDime').click(function(event){
moneyAdded=moneyAdded+0.10;
dimesAdded++;
updateAmount(moneyAdded);
});
$('#addNickel').click(function(event){
moneyAdded=moneyAdded+.05;
nickelsAdded++;
updateAmount(moneyAdded);
});


function updateAmount(money){
$('#dollarsIn').empty();
$('#amount').val(0);
$('#dollarsIn').append(moneyAdded.toFixed(2));
$('#amount').val(money);
}

///////////////////// purchase item///////////
$('#makePurcahse').click(function(event){
$('#message').empty();
if ($('#itemNum').val()==0 || $('#amount').val()==0) {
  $('#message').append('Add Money and select an item');
} else {
    $('#menuItems').empty();
    $('#message').empty();
    $.ajax({
  type : 'GET',
  url:'http://localhost:8080/money/'+$('#amount').val()+'/item/'+$('#itemNum').val(),
  dataType:'json',
  success: function(dataArray){
  $('#errorMessages').empty();
  dipplayAllItem();
  $('#itemNum').val(0);
  $('#item').empty();
  var quarters = dataArray.quarters;
  var dimes =  dataArray.dimes;
  var nickels =  dataArray.nickels;
  var pennies =  dataArray.pennies;
  var change='';
  if (quarters>0) {  change+=quarters+' Quarters ';}
  if (dimes>0) {  change+=dimes+' Dimes   ';}
  if (nickels>0) {  change+=nickels+' Nickels   ';}
  if (pennies>0) {  change+=pennies+' Pennies   ';}
$('#change').empty();
  $('#change').append(change);
  $('#amount').val(0);
  $('#dollarsIn').empty();
   $('#message').append('Thank You!!!');
// clear change
   moneyAdded = 0;
   dollarsAdded=0;
   quartersAdded=0;
   dimesAdded=0;
   nickelsAdded=0;
//success function ends here
  },
      error: function(data){
  dipplayAllItem();
  var errors = $.parseJSON(data.responseText).message
  if (errors.length>3) {
    $('#message').append(errors);
  }
  }});
 }
// makePurcahse on click ends here
});
// document.ready() ends here
});
 
///////////////////////////////////display selected items/////////////////////////////////////////////////////////////
function displaySelectedItem(itemId){
$('#itemNum').val(0);
$('#itemNum').val(itemId);
$('#item').empty();
$('#item').append(itemId);
}
////////////////////////////////Display all items////////////////////////////////////////
function dipplayAllItem(){
var menuItems= $('#menuItems');
  $.ajax({
  type : 'GET',
  url:'http://localhost:8080/items',
  dataType:'json',
  success: function(dataArray){
var i=0;
var j=1;
$.each(dataArray, function(index, list){
var row ='';
if (j%3==0) { row+='<div class="row" >';}
row+='<a onclick="displaySelectedItem('+dataArray[i].id+')"><div id="eachMenuItem" class="col-xs=6 col-sm-4 col-md-4" style="border: 1px solid;">'+ '<div class="menuNumber">'+dataArray[i].id+'</div>'  +'<br/>'+dataArray[i].name+'<br><br> $'+dataArray[i].price+'<br><br>Quantity Left: '+dataArray[i].quantity+'</div></a>';
if (j%3==0) { row+='</div> <br><br><br><br>';}
i++;
j++;
menuItems.append(row);
//$.each ends here
 });
//success function ends here
},
    error: function(){
      $('#errorMessages')
        .append($('<li>'))
        .attr({class: 'list-group-item list-group-item-danger'})
        .text('Error calling webservice');
    }
  });
  // fucntion displayAllItems ends here
  }
