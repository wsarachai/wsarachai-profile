function copyToClipboard(text) {
  console.log("Copy function called with:", text);

  if (navigator.clipboard) {
    navigator.clipboard
      .writeText(text)
      .then(function () {
        showToast("Copied to clipboard!", "success");
      })
      .catch(function (err) {
        console.error("Failed to copy: ", err);
        fallbackCopyTextToClipboard(text);
      });
  } else {
    fallbackCopyTextToClipboard(text);
  }
}

function fallbackCopyTextToClipboard(text) {
  var textArea = document.createElement("textarea");
  textArea.value = text;
  textArea.style.top = "0";
  textArea.style.left = "0";
  textArea.style.position = "fixed";
  document.body.appendChild(textArea);
  textArea.focus();
  textArea.select();

  try {
    var successful = document.execCommand("copy");
    if (successful) {
      showToast("Copied to clipboard!", "success");
    } else {
      showToast("Failed to copy", "error");
    }
  } catch (err) {
    console.error("Fallback: Oops, unable to copy", err);
    showToast("Failed to copy", "error");
  }

  document.body.removeChild(textArea);
}

function showToast(message, type) {
  var toast = document.createElement("div");
  toast.className =
    "alert alert-" +
    (type === "success" ? "success" : "danger") +
    " position-fixed";
  toast.style.top = "20px";
  toast.style.right = "20px";
  toast.style.zIndex = "9999";
  toast.style.minWidth = "200px";
  toast.innerHTML = message;

  document.body.appendChild(toast);

  setTimeout(function () {
    if (toast.parentNode) {
      toast.parentNode.removeChild(toast);
    }
  }, 3000);
}
