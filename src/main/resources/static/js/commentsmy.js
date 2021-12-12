const positionId = document.getElementById("positionId").value;

const csrfHeaderName = document.head.querySelector('[name="_csrf_header"]').content;
const csrfHeaderValue = document.head.querySelector('[name="_csrf"]').content;


const commentCtnr = document.getElementById("commentCtnr");

const commentForm = document.getElementById("commentForm");
commentForm.addEventListener("submit", handleCommentSubmit);

let elementWithError = document.getElementById("textContent");

const allComments = [];

const displayComments = (comments) => {
    commentCtnr.innerHTML = comments.map(c => createCommentDom(c)).join('')
}


async function handleCommentSubmit(event) {
    event.preventDefault();
    elementWithError.classList.remove("is-invalid");

    const form = event.currentTarget;
    const url = form.action;

    const formData = new FormData(form);
    console.log(formData);
    try {
        const responseData = await postCommentAsJson({url, formData});
        commentCtnr.insertAdjacentHTML("afterbegin", createCommentDom(responseData));
        form.reset();
    } catch (error) {
        let errorObj = JSON.parse(error.message);

        if (errorObj.fieldWithErrors) {
            errorObj.fieldWithErrors.forEach(
                e => {
                    elementWithError.classList.add("is-invalid");
                }
            )
        }
    }
}

async function postCommentAsJson({url, formData}) {

    const commentFormData = Object.fromEntries(formData.entries());
    const commentAsJsonString = JSON.stringify(commentFormData);

    const fetchOptions = {
        method: "POST",
        headers: {
            [csrfHeaderName]: csrfHeaderValue,
            "Content-Type": "application/json",
            "Accept": "application/json"
        },
        body: commentAsJsonString
    }

    const response = await fetch(url, fetchOptions);

    if (!response.ok) {
        const errorMessage = await response.text();
        throw new Error(errorMessage);
    }
    return response.json();
}


function createCommentDom(c) {
    let commentHtml = `<div id="commentCtnr-${c.id}">`
    commentHtml += `    <h4>    ${c.owner}  (${c.created})</h4>`
    commentHtml += `<p> ${c.textContent}</p> </div>`;

    return commentHtml;
}

fetch(`http://localhost:8080/api/${positionId}/comments`)
    .then(response => response.json())
    .then(
        data => {
            for (let comment of data) {
                allComments.push(comment)
            }
            displayComments(data)
        }
    );
