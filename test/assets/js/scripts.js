function getLocation() {
  var button = document.getElementById("locationButton");
  button.classList.add("loading");

  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition, showError);
  } else {
    alert("Geolocation is not supported by this browser.");
    button.classList.remove("loading");
  }
}

function showPosition(position) {
  alert(
    "Latitude: " +
      position.coords.latitude +
      "\nLongitude: " +
      position.coords.longitude
  );
  var button = document.getElementById("locationButton");
  button.classList.remove("loading");
}

function showError(error) {
  switch (error.code) {
    case error.PERMISSION_DENIED:
      alert("User denied the request for Geolocation.");
      break;
    case error.POSITION_UNAVAILABLE:
      alert("Location information is unavailable.");
      break;
    case error.TIMEOUT:
      alert("The request to get user location timed out.");
      break;
    case error.UNKNOWN_ERROR:
      alert("An unknown error occurred.");
      break;
  }
  var button = document.getElementById("locationButton");
  button.classList.remove("loading");
}
