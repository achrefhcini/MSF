											<html>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js">
</script>
<script>
function showHint()
{
    var input = document.getElementById("search").value;
    if (input.length != 0) {

        xhr = new XMLHttpRequest();
    if(window.XMLHttprequest)  //objet standard
    {
        xhr = new XMLHttpRequest();//firefox

    }
    else if(window.ActiveXObject)// internet explorer
    {
        xhr = new ActiveXObject("Microsoft.XMLHTTP");
    }

    xhr.onreadystatechange = function ()
    {
        if(xhr.readyState == 4 && xhr.status == 200)
        {
            document.getElementById("searchResultdiv").innerHTML = xhr.responseText;
        }
    }
    xhr.open('GET','/forum-ms-web/v0/Event/GetEventsForAjax/'+input,true);
    xhr.send();
}}
</script>
<body>
	<div id="addResultDiv" style="color: red"></div>
	<input type="text" value="Search Events" id="search" onkeyup="showHint()" />
	<div id="searchResultdiv">Search Result will Appear here!</div>


</body>
</html>