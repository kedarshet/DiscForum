"use strict";

function renderQuestionAndAnswersBodies() {
    convertQuestionBodyToHTML();
    convertAnswersBodiesToHTML();
    highlightCodeInQuestion();
    highlightCodeInAnswers();
}

function convertQuestionBodyToHTML() {
	let questionBody = document.getElementById("questionBody");
    questionBody.innerHTML = replaceHTMLEntitiesWithRealCharacters(questionBody.innerHTML);

    // Add support for HTML tags inside Markdown code
    // that comes from the server.
    for (let e of questionBody.getElementsByTagName("*"))
        if (e.tagName !== "CODE" && e.tagName !== "PRE")
            e.innerHTML = replaceHTMLEntitiesWithRealCharacters(e.innerHTML);
}

function convertAnswersBodiesToHTML() {
	let answersBodies = document.getElementsByClassName("answerBody");

	for (let a of answersBodies) {
        a.innerHTML = replaceHTMLEntitiesWithRealCharacters(a.innerHTML);

        // Add support for HTML tags inside Markdown code
        // that comes from the server.
        for (let e of a.getElementsByTagName("*"))
            if (e.tagName !== "CODE")
                e.innerHTML = replaceHTMLEntitiesWithRealCharacters(e.innerHTML);
    }
}

function replaceHTMLEntitiesWithRealCharacters(string) {
    function replaceAll(string, search, replace) {
      return string.split(search).join(replace);
    }

    string = replaceAll(string,  "&lt;", "<");
    string = replaceAll(string,  "&gt;", ">");

    // This HTML entity should be the last since
    // it can affect on the other entities.
    string = replaceAll(string, "&amp;", "&");

    return string;
}

function highlightCodeInQuestion() {
	let questionBody = document.getElementById("questionBody");
	highlightCodeInsideElement(questionBody);
}

function highlightCodeInAnswers() {
	let answersBodies = document.getElementsByClassName("answerBody");

	for (let a of answersBodies)
	    highlightCodeInsideElement(a);
}

function highlightCodeInsideElement(element) {
	let children = element.getElementsByTagName("*");

	for (let c of children)
		if (c.tagName === "CODE" && c.parentElement.tagName === "PRE")
			hljs.highlightBlock(c);
}