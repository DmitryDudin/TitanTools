
String.prototype.trim = function() {
// Strip leading and trailing white-space
return this.replace(/^\s*|\s*$/g, "");
}

String.prototype.normalize_space = function() {
var s;

// Replace repeated spaces, newlines and tabs with a single space
s = this.replace (/\r|\n|\r\n|\v|\f|\t/g, " ");
s = s.replace(/^\s*|\s(?=\s)|\s*$/g, "");

for (var nn=0; nn<s.length; nn++) {
  if (s.charCodeAt(nn) == 160) {
    s = s.substring(0,nn) + s.substring(nn+1,s.length);
  }
}
return s;

}

function toUpperCase(o){
  o.value=o.value.toUpperCase();
}

function restrictMaxLength(obj, maxLength){
  if (obj.value.length > maxLength) {
     alert('Max length of this area is ' + maxLength + ' chars');
     obj.value = obj.value.substring(0, maxLength);
  }
}

/*
  Use this to clear the English and French Descriptions common to many ECSM code pages
*/
function clearFields(field1, field2 ) {
  field1.value="";
  field2.value="";
}



function selectAll(target) {
  for (var i = 0; i < target.length; i++ ) {
    target.options[i].selected = true;
  }
    return true;
}

 function selectDropdownOptionByValue(dropdown, optionValue)
  {
    var requiredOption = optionValue.trim();

    // For an empty requiredOption (displayed as "-") choose the first option of the dropdown menu
    if (requiredOption == "-") {
      dropdown.options[0].selected = true;
      return true;
    }

    for (var jj = 0; jj < dropdown.length; jj++) {
      if (dropdown.options[jj].text == requiredOption) {
        dropdown.options[jj].selected = true;
       }
    }
  }


// These variables are for saving the original background colors
var savedStates=new Array();
var savedStateCount=0;

// This variable is to keep the reference to the executed row, so it can be highlighted with the right color when needed.
var executedRowReference=null;
// This variable keeps the executed row color
//var executedRowColor='#8fdfff';
var executedRowColor='#EEBB99';
// This should be set to normal row color (white)
var normalRowColor='#ffffff';

function isObjectEmpty(obj) {
	if (obj && obj!=null && obj!="") return false;
	return true;
}
/**
 *
 */
function initializeIndividualPage() {
	var highlightSelectedTableRow = document.getElementById("highlight-selectedRow");
	if (!isObjectEmpty(highlightSelectedTableRow)) {
		executeTableRow( highlightSelectedTableRow, executedRowColor);
		highlightSelectedTableRow.scrollIntoView(false);
	}
}

/**
 *
 */
function executeRowFunction(mEvent)
{
  if (!mEvent) { // if IE, event is from window.
    mEvent=window.event;
  }
  var sourceElement = null;
  if (mEvent.srcElement) {
    sourceElement = mEvent.srcElement;
  } // Netscape and Firefox
  else if (mEvent.target) {
    sourceElement = mEvent.target;
  }

  // verify that the source was not a 'delete' checkbox.
  if (sourceElement.id=="DeleteCheckboxIdentifier") {
    return;
  }

  setExecutedRowColor(executedRowReference, normalRowColor);
  executeTableRow( sourceElement, executedRowColor);
  return ;
}

function executeTableRow(myElement, executeHighlightColor)
{
  executedRowReference=null;
  while (myElement && ((myElement.tagName && myElement.tagName!="TR") || !myElement.tagName))
  {
    myElement=myElement.parentNode;
  }
  // If you don't want a particular row to be clickable, set it's id to "header"
  if (!myElement || (myElement && myElement.id && (myElement.id=="header" || myElement.id=="header-skip-highlight")) )
    return;

  // Read every cell on the row
  if (myElement)
  {
    executedRowReference=myElement;
    var tableRow=myElement;

    // myElement is a <TR>, find the first TD
    var tableCell=findNode(myElement, "TD");

    //var i=0;
    // Loop through every sibling (a sibling of a cell should be a cell)
    // We then highlight every siblings
    //var message="";
    while (tableCell)
    {
      // Make sure it's actually a cell (a TD)
      if (tableCell.tagName=="TD")
      {
        myceltext=tableCell.childNodes.item(0);
        tableCell.style["backgroundColor"]=executeHighlightColor;
        //message = message + myceltext.data + ", ";
        //i++;
      }
      // Go to the next cell in the row
      tableCell=tableCell.nextSibling;
    }
    //alert(message);
  }
}

// Just set highlight to a visible row in the table.
// This function to be used to set highlights to
// a row in the table onMouseOut (when table is exited.)
function setTableHighlight(myElement, executeHighlightColor) {
  //1. figure out the visible portion of the table
  //2. set highlight on some visible row
  //3. store highlighted row reference to allow setting row style back to normal.
  return;
}



/**
 * This function takes an element as a parameter and
 *   returns an object which contain the saved state
 *   of the element's background color.
 */
function saveBackgroundStyle(myElement, restoreColor)
{
  saved=new Object();
  saved.element=myElement;
  saved.className=myElement.className;
  if (restoreColor) 
    saved.backgroundColor=restoreColor;
  else
    saved.backgroundColor=myElement.style["backgroundColor"];
  return saved;
}

/**
 * This function takes an element as a parameter and
 *   returns an object which contain the saved state
 *   of the element's background color.
 */
function restoreBackgroundStyle(savedState, restoreColor)
{
  if (restoreColor)
  	savedState.element.style["backgroundColor"]=restoreColor;
  else
  	savedState.element.style["backgroundColor"]=savedState.backgroundColor;
  if (savedState.className)
  {
    savedState.element.className=savedState.className;
  }
}

/**
 * This function is used by highlightTableRow() to find table cells (TD) node
 */
function findNode(startingNode, tagName)
{
  // on Firefox, the TD node might not be the firstChild node of the TR node
  myElement=startingNode;
  var i=0;
  while (myElement && (!myElement.tagName || (myElement.tagName && myElement.tagName!=tagName)))
  {
    myElement=startingNode.childNodes[i];
    i++;
  }
  if (myElement && myElement.tagName && myElement.tagName==tagName)
  {
    return myElement;
  }
  // on IE, the TD node might be the firstChild node of the TR node
  else if (startingNode.firstChild)
    return findNode(startingNode.firstChild, tagName);
  return 0;
}

/**
 * Highlight table row.
 * newElement could be any element nested inside the table
 * highlightColor is the color of the highlight
 */
function highlightTableRow(myElement, highlightColor, restoreColor)
{
  var i=0;
  // Restore color of the previously highlighted row
  for (i; i<savedStateCount; i++)
  {
    restoreBackgroundStyle(savedStates[i], restoreColor);
  }
  savedStateCount=0;
  setExecutedRowColor(executedRowReference, executedRowColor);

  // To get the node to the row (ie: the <TR> element),
  // we need to traverse the parent nodes until we get a row element (TR)
  // Netscape has a weird node (if the mouse is over a text object, then there's no tagName
  while (myElement && ((myElement.tagName && myElement.tagName!="TR") || !myElement.tagName))
  {
    myElement=myElement.parentNode;
  }

  // If you don't want a particular row to be highlighted, set it's id to "header" or "header-skip-highlight"
  if (!myElement || (myElement && myElement.id && (myElement.id=="header" || myElement.id=="header-skip-highlight")) )
    return;

  // Highlight every cell on the row
  if (myElement)
  {
    var tableRow=myElement;

    // Save the backgroundColor style OR the style class of the row (if defined)
    if (tableRow)
    {
	  savedStates[savedStateCount]=saveBackgroundStyle(tableRow, restoreColor);
      savedStateCount++;
    }

    // myElement is a <TR>, then find the first TD
    var tableCell=findNode(myElement, "TD");

    var i=0;
    // Loop through every sibling (a sibling of a cell should be a cell)
    // We then highlight every siblings
    while (tableCell)
    {
      // Make sure it's actually a cell (a TD)
      if (tableCell.tagName=="TD")
      {
        // If no style has been assigned, assign it, otherwise Netscape will
        // behave weird.
        if (!tableCell.style)
        {
          tableCell.style={};
        }
        else
        {
          savedStates[savedStateCount]=saveBackgroundStyle(tableCell);
          savedStateCount++;
        }
        // Assign the highlight color
        tableCell.style["backgroundColor"]=highlightColor;

        // Optional: alter cursor
        tableCell.style.cursor='default';
        i++;
      }
      // Go to the next cell in the row
      tableCell=tableCell.nextSibling;
    }
  }
}

function setExecutedRowColor(execRowRef, execRowCol) {
  if (execRowRef && execRowRef!=null)
  {
    var tableRow=execRowRef;
    // executedRowReference is a <TR>, then find the first TD
    var tableCell=findNode(execRowRef, "TD");
    var i=0;
    // Loop through every sibling (a sibling of a cell should be a cell)
    // We then highlight every siblings
    while (tableCell)
    {
      // Make sure it's actually a cell (a TD)
      if (tableCell.tagName=="TD")
      {
        // Assign the highlight color
        tableCell.style["backgroundColor"]=execRowCol;
        i++;
      }
      // Go to the next cell in the row
      tableCell=tableCell.nextSibling;
    }
  }
}

/**
 * this function is to be assigned to a <table> mouse event handler.
 * if the element that fired the event is within a table row,
 * this function will highlight the row.
 */
function trackTableHighlight(mEvent, highlightColor, restoreColor)
{
  if (!mEvent)
    mEvent=window.event;

  // Internet Explorer
  if (mEvent.srcElement)
  {
    highlightTableRow( mEvent.srcElement, highlightColor, restoreColor);
  }
  // Netscape and Firefox
  else if (mEvent.target)
  {
    highlightTableRow( mEvent.target, highlightColor, restoreColor);
  }
}

/**
 * Highlight table row.
 * newElement could be any element nested inside the table
 * highlightColor is the color of the highlight
 */
function highlightTableRowVersionA(myElement, highlightColor)
{
  var i=0;
  // Restore color of the previously highlighted row
  for (i; i<savedStateCount; i++)
  {
    restoreBackgroundStyle(savedStates[i]);
  }
  savedStateCount=0;

  // If you don't want a particular row to be highlighted, set it's id to "header"
  if (!myElement || (myElement && myElement.id && myElement.id=="header") )
    return;

  // Highlight every cell on the row
  if (myElement)
  {
    var tableRow=myElement;

    // Save the backgroundColor style OR the style class of the row (if defined)
    if (tableRow)
    {
	  savedStates[savedStateCount]=saveBackgroundStyle(tableRow);
      savedStateCount++;
    }

    // myElement is a <TR>, then find the first TD
    var tableCell=findNode(myElement, "TD");

    var i=0;
    // Loop through every sibling (a sibling of a cell should be a cell)
    // We then highlight every siblings
    while (tableCell)
    {
      // Make sure it's actually a cell (a TD)
      if (tableCell.tagName=="TD")
      {
        // If no style has been assigned, assign it, otherwise Netscape will
        // behave weird.
        if (!tableCell.style)
        {
          tableCell.style={};
        }
        else
        {
          savedStates[savedStateCount]=saveBackgroundStyle(tableCell);
          savedStateCount++;
        }
        // Assign the highlight color
        tableCell.style["backgroundColor"]=highlightColor;

        // Optional: alter cursor
        tableCell.style.cursor='default';
        i++;
      }
      // Go to the next cell in the row
      tableCell=tableCell.nextSibling;
    }
  }
}

function Left(str, n){
      if (n <= 0)
          return "";
      else if (n > String(str).length)
          return str;
      else
          return String(str).substring(0,n);
}

function Right(str, n){
  if (n <= 0)
     return "";
  else if (n > String(str).length)
     return str;
  else {
     var iLen = String(str).length;
     return String(str).substring(iLen, iLen - n);
  }
}

function copyYesNoValueToCheckbox(yesNoValue, checkbox) {
    if ("YES" == yesNoValue.trim().toUpperCase()) {
      checkbox.checked = true;
    } else {
      checkbox.checked = false;
    }
}

/*
Call this method if any changes are made on the page.
A dialog box will pop up for the user to confirm before the page is
unloaded.
*/

function setModified() {
  modified=true;
}

/*
Call this method to prevent the popup confirmation on unloading the page
*/
function resetModified() {
  modified=false;
}

function testPageModified() {
    proceed = true;
    if (modified) {
      proceed = confirm("Any changes made will be lost. Press OK to continue, or Cancel to stay on the current selection");
    }
    if (proceed) {
      resetModified();
    }
    return proceed;
}

function isNumeric(input) {
   var validNumerical = "0123456789";
   var digit;

   for (i = 0; i < input.length; i++) {
      digit = input.charAt(i);
      if (validNumerical.indexOf(digit) == -1) {
        return false;
      }
   }
   return true;
}

function unselectRadio(obj) {
  for (i = 0; i < obj.length; i++) {
    if ( obj[i].checked = true )
      obj[i].checked = false;
  }
}

var dateRe = new RegExp(/[0-9][0-9]\-(A|D|F|J|M|N|O|S)(a|c|e|o|p|u)(b|c|g|l|n|p|r|t|v|y)\-[0-9][0-9][0-9][0-9]/);
var months = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"];

function testDate(element) {
	if (element.value=="") {
		
		return true;
	}
	if (!element.value.match(dateRe)) {
		alert("Date time values must maintain the following sample format: 01-Jan-1970");
		element.value="";
		return false;
	}	
	var day = element.value.substr(0,2);
	var dayInt = parseInt(day);
	if (dayInt>31) {
		alert("Invalid day " + dayInt);
		element.value="";
		return false;
	}
	var mo = element.value.substr(3,3);
	var monthInt = -1;
	for(i = 0; i < months.length; i++) {	
		if(mo == months[i]) {
			monthInt = i;
			break;
		}
	}	
	if(monthInt == -1) {
		alert("Invalid month name " + mo);
		element.value="";
		return false;
	}	
	var year = element.value.substr(7, 4);

	var maxDay = Calendar.get_daysofmonth(monthInt, year);
	
	if(dayInt > maxDay) {
		alert("Invalid day " + dayInt);
		element.value="";
		return false;
	} 

	return true;
}



