function validateURLLive() {
    const url = document.getElementById("url").value;
    const error = document.getElementById("error");

    if (url.length === 0) {
        error.textContent = "";
        return;
    }

    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        error.textContent = "URL should start with http:// or https://";
        error.style.color = "red";
        return;
    }

    if (url.length < 10) {
        error.textContent = "URL is too short to be valid";
        error.style.color = "orange";
        return;
    }

    error.textContent = "URL format looks valid";
    error.style.color = "green";
}

function validateURLFinal() {
    const url = document.getElementById("url").value;

    if (!url.startsWith("http://") && !url.startsWith("https://")) {
        alert("Please enter a valid URL starting with http:// or https://");
        return false;
    }

    return true;
}
