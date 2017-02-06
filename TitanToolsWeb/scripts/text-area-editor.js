
// must add the following div to the page:
//<div id="divparent"></div>
// must add the following listener code to the iputboxes
//<input type="text" name="tester" value="test text" onclick="showPopupTextEditor(this)" onkeypress="showPopupTextEditor(this)" />


function toggle(div_id) {
	var el = document.getElementById(div_id);
	if ( el.style.display == 'none' ) {	el.style.display = 'block';}
	else {el.style.display = 'none';}
}
function blanket_size(popUpDivVar) {
	if (typeof window.innerWidth != 'undefined') {
		viewportheight = window.innerHeight;
	} else {
		viewportheight = document.documentElement.clientHeight;
	}
	if ((viewportheight > document.body.parentNode.scrollHeight) && (viewportheight > document.body.parentNode.clientHeight)) {
		blanket_height = viewportheight;
	} else {
		if (document.body.parentNode.clientHeight > document.body.parentNode.scrollHeight) {
			blanket_height = document.body.parentNode.clientHeight;
		} else {
			blanket_height = document.body.parentNode.scrollHeight;
		}
	}
	var blanket = document.getElementById('blanket');
	blanket.style.height = blanket_height + 'px';
	var popUpDiv = document.getElementById(popUpDivVar);
	popUpDiv_height=blanket_height/2-150;//150 is half popup's height
	popUpDiv.style.top = popUpDiv_height + 'px';
}

function popup(windowname) {
	blanket_size(windowname);
	//window_pos(windowname);
	toggle('blanket');
	//toggle(windowname);
}

function closePopupTextEditor(boxName, newValue) {
	//alert(boxName);
	//alert(newValue);
	var tb=document.getElementsByName(boxName)[0];
        tb.value=newValue;


	var divById = document.getElementById(boxName);
	if (divById!=null) {
        	var divParent = document.getElementById("divparent");
        	//var ta1 = document.getElementById("ta-" + boxName);
        	var  ta1 = divById.lastChild;
        	divById.removeChild(ta1);
        	var sel1 = divById.lastChild;
        	divById.removeChild(sel1);
        	divParent.removeChild(divById);
        }
        popup("divparent");
}

function showPopupTextEditor(inputBox) {
	//alert(inputBox.name);
	var divById = document.getElementById(inputBox.name);
	var ta = null;

	if (divById==null) {
		var div1 = document.createElement("div");
		div1.id=inputBox.name;
		div1.style.zIndex="100000";
		div1.style.width="640px";
		div1.style.height="245px";
		div1.style.opacity = "100";
		div1.style.filter = 'alpha(opacity=100)';
		div1.style.backgroundColor="blue";

		var ta1 = document.createElement("textarea");
		ta1.value=inputBox.value;
		ta1.style.width="630px";
		ta1.style.height="210px";
		ta1.id="ta-" + inputBox.name;
		ta1.style.opacity = "100";
		ta1.style.filter = 'alpha(opacity=100)';
		ta1.style.color="black";
		ta1.style.fontWeight = "600";

		var filterArray = eval(inputBox.name + "FilterData");

		var sel1 = document.createElement("select");
		sel1.id="sel-" + inputBox.name;
		sel1.style.width="630px";
		sel1.options[0]=new Option("", "", false, false);
		
		if (filterArray && filterArray!=null && filterArray.length>0) {
			for(x=0;x<filterArray.length;x++) {
				sel1.options[x+1]=new Option(filterArray[x], "", false, false);
			}
		}
		sel1.addEventListener("change",
			function(event) {
				if (!event) var event = window.event;
				var targ;
				if (event.target) targ = event.target;
				else if (event.srcElement) targ = event.srcElement;
				if (targ.nodeType == 3) // defeat Safari bug
					targ = targ.parentNode;
				var id = targ.id;
				var name = id.substring(4);
				var textArea = document.getElementById("ta-" + name);
				textArea.value = targ.options[targ.selectedIndex].text;
				textArea.focus();
			}, false);


		//ta1.addEventListener("keypress", function(event){key = event ? event.which : window.event.keyCode;if(key==13) return false;return true}, false);
		ta1.addEventListener("keypress",
			function(event){
				if (!event) var event = window.event;
				var kc = event.keyCode;
				if (kc==9 || kc==13 || kc==27) {
					var targ;
					if (event.target) targ = event.target;
					else if (event.srcElement) targ = event.srcElement;
					if (targ.nodeType == 3) // defeat Safari bug
						targ = targ.parentNode;
					closePopupTextEditor(targ.parentNode.id, targ.value);
//				} else if (kc==13) {
//					if (event.preventDefault) {
//						event.preventDefault();
//						event.stopPropagation();
//					} else {
//						event.keyCode = 0;
//						event.returnValue = false;
//					}
//					return false;
				}
				return true
			}, false);

//		var span1 = document.createElement("span");
//		span1.appendChild(document.createTextNode("Select Filter from History"));
//		span1.style.backgroundColor="yellow";
//		span1.style.color="red";
//		span1.style.width="200px";
//		span1.style.textAlign="center";
//		var span2 = document.createElement("span");
//		span2.style.backgroundColor="yellow";
//		span2.style.color="red";
//		span2.style.width="200px";
//		span2.style.textAlign="center";
//		span2.appendChild(document.createTextNode("Edit Filter"));
		

//		div1.appendChild(span1);
		div1.appendChild(sel1);
//		div1.appendChild(span2);
		div1.appendChild(ta1);
	

		div1.style.position="fixed";

		var pos = getPosition(inputBox);

		//var ot = getY(inputBox) + 20;
		var ot = pos.y + 20;
		div1.style.top=ot + "px";
		//alert(inputBox.offsetTop);
		var ol = pos.x + 20;
		//var ol = getX(inputBox) + 20;
		div1.style.left=ol + "px";
		//alert(inputBox.offsetLeft);
		ta = ta1;
		divById = div1;
	}
	var divParent = document.getElementById("divparent");

	//alert(divParent.firstChild);
	if (divParent.firstChild == null) {
		divParent.appendChild(divById);
		ta.focus();
	}
	popup("divparent");
	divParent.style.opacity = "100";
	divParent.style.filter = 'alpha(opacity=100)';
	ta.focus();
}

function findPosX(obj) {
	var curleft = 0;
	if(obj.offsetParent)
	while(1) {
		curleft += obj.offsetLeft;
		if(!obj.offsetParent) break;
		obj = obj.offsetParent;
	} else if(obj.x)
		curleft += obj.x;
	return curleft;
}
function findPosY(obj) {
	var curtop = 0;
	if(obj.offsetParent)
	while(1) {
		curtop += obj.offsetTop;
		if(!obj.offsetParent) break;
		obj = obj.offsetParent;
	} else if(obj.y)
		curtop += obj.y;
	return curtop;
}


function getX(obj){
	return obj.offsetLeft + (obj.offsetParent ? getX(obj.offsetParent) : obj.x ? obj.x : 0);
}
function getY(obj){
	return (obj.offsetParent ? obj.offsetTop + getY(obj.offsetParent) : obj.y ? obj.y : 0);
}


function getPosition(obj) {
var left = 0;
var top = 0;

if (obj != null)
{
    // Try because sometimes errors on offsetParent after DOM changes.
    try
    {
        while (obj.offsetParent)
        {
            // While we haven't got the top obj in the DOM hierarchy
            // Add the offsetLeft
            left += obj.offsetLeft;
            // If my parent scrolls, then subtract the left scroll position
            if (obj.offsetParent.scrollLeft) {left -= obj.offsetParent.scrollLeft; }

            // Add the offsetTop
            top += obj.offsetTop;
            // If my parent scrolls, then subtract the top scroll position
            if (obj.offsetParent.scrollTop) { top -= obj.offsetParent.scrollTop; }

            // Grab
            obj = obj.offsetParent;
        }
    }
    catch (e)
    {
        // Do nothing
    }

    // Add the top obj left offset and the windows left scroll and subtract the body's client left position.
    left += obj.offsetLeft + document.body.scrollLeft - document.body.clientLeft;

    // Add the top obj topoffset and the windows topscroll and subtract the body's client top position.
    top += obj.offsetTop + document.body.scrollTop - document.body.clientTop;
}
return {x:left, y:top};

}