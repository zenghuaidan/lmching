(function() {
	// trim polyfill : https://developer.mozilla.org/en-US/docs/Web/JavaScript/Reference/Global_Objects/String/Trim
	if (!String.prototype.trim) {
		(function() {
			// Make sure we trim BOM and NBSP
			var rtrim = /^[\s\uFEFF\xA0]+|[\s\uFEFF\xA0]+$/g;
			String.prototype.trim = function() {
				return this.replace(rtrim, '');
			};
		})();
	}
	
	[].slice.call( document.querySelectorAll( 'input.input__field' ) ).forEach( function( inputEl ) {
		// in case the input is already filled..
		if( inputEl.value.trim() !== '' ) {
			classie.add( inputEl.parentNode, 'input--filled' );
		}
		
		// events:
		inputEl.addEventListener( 'focus', onInputFocus );
		inputEl.addEventListener( 'blur', onInputBlur );
	} );
	
	function onInputFocus( ev ) {
		classie.add( ev.target.parentNode, 'input--filled' );
	}
	
	function onInputBlur( ev ) {
		if( ev.target.value.trim() === '' ) {
			classie.remove( ev.target.parentNode, 'input--filled' );
		}
	}
})();

function initSelect(x) {
	var i, j, selElmnt, a, b, c;
	/*for each element, create a new DIV that will act as the selected item:*/
	selElmnt = x.getElementsByTagName("select")[0];
	$(x).find("div").remove();
	a = document.createElement("DIV");
	a.setAttribute("class", "select-selected");
	a.innerHTML = $("<div>" + selElmnt.options[selElmnt.selectedIndex].innerText + "</div>").get(0).innerHTML;
	x.appendChild(a);
	/*for each element, create a new DIV that will contain the option list:*/
	b = document.createElement("DIV");
	b.setAttribute("class", "select-items select-hide");
	for (j = 0; j < selElmnt.length; j++) {
		/*for each option in the original select element, create a new DIV that will act as an option item:*/
		c = document.createElement("DIV");
		//c.innerHTML = selElmnt.options[j].innerHTML;
		c.innerHTML = $("<div>" + selElmnt.options[j].innerText + "</div>").get(0).innerHTML;
		c.addEventListener("click", function(e) {
			/*when an item is clicked, update the original select box, and the selected item:*/
			var y, i, k, s, h;
			s = this.parentNode.parentNode.getElementsByTagName("select")[0];
			h = this.parentNode.previousSibling;			
			
			s.selectedIndex = $(this).index();
			h.innerHTML = this.innerHTML;
			y = this.parentNode.getElementsByClassName("same-as-selected");
			for (k = 0; k < y.length; k++) {
				y[k].removeAttribute("class");
			}
			this.setAttribute("class", "same-as-selected");
			
			h.click();
			console.log("click");
			
			if(typeof customSelectChange != "undefined") {
				customSelectChange(s);
			}
		});
		b.appendChild(c);
	}
	x.appendChild(b);
	a.addEventListener("click", function(e) {
		/*when the select box is clicked, close any other select boxes, and open/close the current select box:*/
		if(this.nextSibling != null) {
			e.stopPropagation();
			closeAllSelect(this);
			this.nextSibling.classList.toggle("select-hide");
			this.classList.toggle("select-arrow-active");			
		}
	});
}

function closeAllSelect(elmnt) {
	/*a function that will close all select boxes in the document, except the current select box:*/
	var x, y, i, arrNo = [];
	x = document.getElementsByClassName("select-items");
	y = document.getElementsByClassName("select-selected");
	for (i = 0; i < y.length; i++) {
		if (elmnt == y[i]) {
			arrNo.push(i)
		} else {
			y[i].classList.remove("select-arrow-active");
		}
	}
	for (i = 0; i < x.length; i++) {
		if (arrNo.indexOf(i)) {
			x[i].classList.add("select-hide");
		}
	}
}
$(function() {
	/*look for any elements with the class "custom-select":*/
	x = document.getElementsByClassName("custom-select");
	for (i = 0; i < x.length; i++) {
		initSelect(x[i]);
	}

	/*if the user clicks anywhere outside the select box, then close all select boxes:*/
	document.addEventListener("click", closeAllSelect);
})