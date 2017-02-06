var sortElements=-1;
var sortElementsMap = null;
var sortAnchorMap = null;
var filterElementNameMap = null;

var ORDER_DIV_ELEMENT_ID="ORDER_DIV";

var sortedOrderElements = null;

function getNextAvailableArraySlot(ar) {
	for(i=0;i<ar.length;i++) {
		if (ar[i]==null) {
			return i;
		}
	}
	return -1;
}

function removeElementFromArray(val, ar) {
	for(i=0;i<ar.length;i++) {
		if (ar[i]==val) {
			ar[i]=null;
		}		
	}
}

function compressSortedOrderElements(ar) {
	var temp = new Array(sortElements);
	for(i=0;i<sortElements;i++) {
		temp[i] = ar[i];
		ar[i]=null;
	}
	var j=0;
	for(i=0;i<sortElements;i++) {
		if (temp[i]!=null) {
			ar[j] = temp[i];
			j++;
		}
	}
}

function clearArray(ar) {
	for(i=0;i<ar.length;i++) {
		ar[i]=null;
	}
}

function updateOrdering(anchor, orderElement) {
	if (orderElement.value==NOT_ORDERED_STR) {
		orderElement.value=ASCENDING_STR;
		anchor.firstChild.nodeValue=ASCENDING_STR;
		var i = getNextAvailableArraySlot(sortedOrderElements);
		sortedOrderElements[i]=orderElement.name;		
	} else if (orderElement.value==ASCENDING_STR) {
		orderElement.value=DESCENDING_STR;
		anchor.firstChild.nodeValue=DESCENDING_STR;
	} else if (orderElement.value==DESCENDING_STR) {
		orderElement.value=NOT_ORDERED_STR;
		anchor.firstChild.nodeValue=NOT_ORDERED_STR;
		removeElementFromArray(orderElement.name, sortedOrderElements);
		compressSortedOrderElements(sortedOrderElements);
	}
	var oe = document.forms[0].orderingOfOrderElements;
	oe.value="";
	var orderDiv = document.getElementById(ORDER_DIV_ELEMENT_ID);
	if (orderDiv.firstChild) {
		orderDiv.removeChild(orderDiv.firstChild);
	}
	if (sortedOrderElements[0] != null) {
		var OL =document.createElement('OL');
		orderDiv.appendChild(OL);
		for(i=0; i<sortElements && sortedOrderElements[i] != null; i++) {
			var LI =document.createElement('LI');
			LI.appendChild(document.createTextNode(getOrderElementDisplayName(sortedOrderElements[i])));
			OL.appendChild(LI);
			oe.value+=sortedOrderElements[i] + ",";
		}
		if (oe.value.length > 0) {
			oe.value = oe.value.substr(0, oe.value.length-1);
		}
	}
}

function getOrderElementDisplayName(n) {
	var displayName = sortElementsMap[n];
	if (displayName==null) return "undefined field";
	var key = "document.forms[0]." + n + ".value";
	return displayName + " (" + eval(key) + ")";
}

function loadOrderingElementsOrder() {	
	sortedOrderElements = new Array(sortElements);

	var val = document.forms[0].orderingOfOrderElements.value;
	if (val != null && val.length > 0){
		o = val.split(",");
		for(i=0;i<o.length;i++) {
			sortedOrderElements[i]=o[i];
		}
		var orderDiv = document.getElementById(ORDER_DIV_ELEMENT_ID);
		if (orderDiv.firstChild) {
			orderDiv.removeChild(orderDiv.firstChild);
		}
		var OL =document.createElement('OL');
		orderDiv.appendChild(OL);
		for(i=0; i < sortElements && sortedOrderElements[i] != null; i++) {
			var LI =document.createElement('LI');
			LI.appendChild(document.createTextNode(getOrderElementDisplayName(sortedOrderElements[i])));
			OL.appendChild(LI);
		}
	}
}

function clearFiltersAndOrder(){
	var f = document.forms[0];
	for (var i = 0;i<filterElementNameMap.length;i++) {
		var key = "f." + filterElementNameMap[i];
		var el = eval(key);
		if (el.type=="text") {
			el.value="";
		} else if (el.type=="select-one") {
			el.value="";
		} else if (el.type=="checkbox") {
			el.checked=false;
		} else if (el.type=="undefined") {
			alert('undefined, ha?');
		}
	}

	for (var i in sortElementsMap) {
		var key = "f." + i;
		var el = eval(key);
		el.value=NOT_ORDERED_STR;
	}

	var or = getOrderElements();
	for (i=0;i<sortElements;i++) {
		or[i].firstChild.nodeValue=NOT_ORDERED_STR;
	}
	clearArray(sortedOrderElements);
	var orderDiv = document.getElementById(ORDER_DIV_ELEMENT_ID);
	if (orderDiv.firstChild) {
		orderDiv.removeChild(orderDiv.firstChild);
	}
	document.forms[0].orderingOfOrderElements.value="";
}

function getOrderElements() {
	var or = new Array(sortElements);
	for (i=0;i<sortElements;i++) {
		or[i] = document.getElementById(sortAnchorMap[i]);
	}
	return or;
}
