"use strict";

let tagsList = [];
const MAX_TAGS_COUNT = 5;

function tagEditorInputOnInput() {
    var tagEditorInput = document.getElementById("tagEditorInput");

    function clearInput() {
        tagEditorInput.value = "";
    }

    let   value            = tagEditorInput.value;
    let   length           = value.length;
    const firstCharacter   = getStringFirstCharacter(value);
    const lastCharacter    = getStringLastCharacter(value);

    if (tagsList.length >= MAX_TAGS_COUNT) {
        clearInput();
    } else if (length < 2 && firstCharacter === " ") {
        clearInput();
    } else if (lastCharacter === " ") {
        const tagName = value.toLowerCase().trim();
        tagsList.push(tagName);
        clearInput();
        renderTags();
        updateTagInputs();
    }
}

function renderTags() {
    removeAllRenderedTags();

    let renderedTags = document.getElementById("renderedTags");

    for (let t of tagsList)
        renderedTags.appendChild(createRendererTagElement(t));
}

function createRendererTagElement(tagName) {
    let tag = document.createElement("span");
    addClass(tag, "renderedTag");

    tag.innerHTML  = '<span class="tagName">' + tagName + '</span>';
    tag.innerHTML += '<svg onmouseup="removeRenderedTag(this.parentElement.firstChild);" class="removeTagButton" width="14" height="14" viewBox="0 0 14 14"><path d="M12 3.41L10.59 2 7 5.59 3.41 2 2 3.41 5.59 7 2 10.59 3.41 12 7 8.41 10.59 12 12 10.59 8.41 7z"></path></svg>';

    return tag;
}

function removeAllRenderedTags() {
    let renderedTags = document.getElementById("renderedTags");
    renderedTags.innerHTML = "";
}

function removeRenderedTag(element) {
    const tagName  = getFirstWordInString(element.innerHTML);
    const tagIndex = tagsList.indexOf(tagName);

    removeItemFromArray(tagsList, tagIndex);
    renderTags();
}

function updateTagInputs() {
    for (let i = 0; i < 5; ++i) {
        let tag = document.getElementById("tag" + i);

        if (tagsList[i] === undefined)
            tag.name = "emptyTag";
        else
            tag.name = "tag";

        tag.value = tagsList[i];
    }
}

function removeLastCharacterInString(s) {
    return s.substring(0, s.length - 1);
}

function getStringLastCharacter(s) {
    return s.slice(s.length - 1);
}

function getStringFirstCharacter(s) {
    return s[0];
}

function getFirstWordInString(s) {
    const spaceIndex = s.indexOf(" ");

    if (spaceIndex === -1)
        return s;
    else
        return s.substr(0, spaceIndex);
};

function removeItemFromArray(array, index) {
    array.splice(index, 1);
}

function addClass(element, className) {
    element.classList.add(className);
}

function removeClass(element, className) {
    if (element.classList.contains(className))
        element.classList.remove(className);
}
