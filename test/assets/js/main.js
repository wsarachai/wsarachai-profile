navigator.geolocation.getCurrentPosition((position) => {
  const { latitude, longitude } = position.coords;

  console.log(latitude, longitude); // Log the latitude / longitude.
  // Show a map centered at latitude / longitude.
});
