var msgs = document.getElementsByClassName("msg");
var spans = document.getElementsByClassName("close");

for(let i = 0; i < spans.length; i++) {
    spans[i].onclick = function() { msgs[i].style.display = "none"; }
}
