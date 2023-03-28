var checkboxes = document.getElementsByClassName('box-book');
var inputs = document.getElementsByClassName('input-book');
var select = document.getElementsByClassName('select-js');

for(let i = 0; i < checkboxes.length; ++i) {
    checkboxes[i].onchange = function() { 
        if(i < inputs.length) {  inputs[i].disabled = !this.checked; }
        else { select[i - inputs.length].disabled = !this.checked; }
    };
}
