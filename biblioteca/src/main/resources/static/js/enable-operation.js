var select = document.getElementById("selectEntity");
var modify = document.getElementById("divModify");

if(select.selectedIndex === 0) { modify.style.display = "none";  }

select.onchange = function() { 
    if(this.selectedIndex !== 0)
        modify.style.display = "block";
    else
        modify.style.display = "none";
};