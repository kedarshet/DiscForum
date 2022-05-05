"use strict";

function previewQuestionBody(questionBody) {
	let questionBodyPreviewContainer = document.getElementById("questionBodyPreview");
	questionBodyPreviewContainer.innerHTML = new showdown.Converter().makeHtml(questionBody.value);
	highlightCodeInsideElement(questionBodyPreviewContainer);
}

function highlightCodeInsideElement(element) {
	let children = element.getElementsByTagName("*");

	for (let c of children)
		if (c.tagName === "CODE" && c.parentElement.tagName === "PRE")
			hljs.highlightBlock(c);
}

