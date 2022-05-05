"use strict";

function previewAnswerBody(answerBody) {
	let answerBodyPreviewContainer = document.getElementById("answerBodyPreview");
	answerBodyPreviewContainer.innerHTML = new showdown.Converter().makeHtml(answerBody.value);
	highlightCodeInsideElement(answerBodyPreviewContainer);
}

function highlightCodeInsideElement(element) {
	let children = element.getElementsByTagName("*");

	for (let c of children)
		if (c.tagName === "CODE" && c.parentElement.tagName === "PRE")
			hljs.highlightBlock(c);
}


