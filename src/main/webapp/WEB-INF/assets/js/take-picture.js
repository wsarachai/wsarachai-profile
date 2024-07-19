"use strict";

let numOfPhoto = 0;
let isStuPic = true;
let video = {};
let canvas1 = {};
let canvas2 = {};
let inputImage1 = {};
let inputImage2 = {};
let buttonNewPhoto = {};
let attenButton = {};
let currentDate = {};
let currentTime = {};
let timeHandler = undefined;

var monthNamesThai = [
  "มกราคม",
  "กุมภาพันธ์",
  "มีนาคม",
  "เมษายน",
  "พฤษภาคม",
  "มิถุนายน",
  "กรกฎาคม",
  "สิงหาคม",
  "กันยายน",
  "ตุลาคม",
  "พฤษจิกายน",
  "ธันวาคม",
];

var dayNames = [
  "วันอาทิตย์ที่",
  "วันจันทร์ที่",
  "วันอังคารที่",
  "วันพุทธที่",
  "วันพฤหัสบดีที่",
  "วันศุกร์ที่",
  "วันเสาร์ที่",
];

function getLocation(location, latitudeInput, longitudeInput) {
  if (navigator.geolocation) {
    navigator.geolocation.getCurrentPosition(showPosition.bind(null, location, latitudeInput, longitudeInput));
  } else {
    location.innerHTML = "Geolocation is not supported by this browser.";
  }
}

function showPosition(location, latitudeInput, longitudeInput, position) {
  latitudeInput.value = position.coords.latitude;
  longitudeInput.value = position.coords.longitude;
  location.innerHTML = position.coords.latitude + ", " + position.coords.longitude;
}

const initElement = function () {
  buttonNewPhoto = document.getElementById("newphoto");
  attenButton = document.getElementById("attbutton");
  canvas1 = document.getElementById("canvas1");
  canvas2 = document.getElementById("canvas2");
  inputImage1 = document.getElementById("inputImage1");
  inputImage2 = document.getElementById("inputImage2");
  currentDate = document.getElementById("currentDate");
  currentTime = document.getElementById("currentTime");
  canvas1.width = 210;
  canvas2.width = 210;

  let ctx = canvas1.getContext("2d");
  ctx.fillStyle = "blue";
  ctx.fillRect(0, 0, canvas1.width, canvas1.height);

  ctx = canvas2.getContext("2d");
  ctx.fillStyle = "blue";
  ctx.fillRect(0, 0, canvas2.width, canvas2.height);

  video = document.querySelector("video");
  if (navigator.mediaDevices === undefined) {
    navigator.mediaDevices = {};
  }

  if (navigator.mediaDevices.getUserMedia === undefined) {
    navigator.mediaDevices.getUserMedia = function (constraints) {
      var getUserMedia =
        navigator.getUserMedia ||
        navigator.webkitGetUserMedia ||
        navigator.mozGetUserMedia ||
        navigator.msGetUserMedia;

      if (!getUserMedia) {
        return Promise.reject(
          new Error("getUserMedia is not implemented in this browser")
        );
      }

      return new Promise(function (resolve, reject) {
        getUserMedia.call(navigator, constraints, resolve, reject);
      });
    };
  }
};

const onLoadVideo = function () {
  video.width = 210;
  video.play();
};

const onMediaStream = function (stream) {
  if ("srcObject" in video) {
    video.srcObject = stream;
  } else {
    video.src = window.URL.createObjectURL(stream);
  }
  video.addEventListener("loadedmetadata", onLoadVideo);
  buttonNewPhoto.addEventListener("click", onTakeAPhoto);
};

function onTakeAPhoto() {
  let canvas = {};
  let inputImage = {};
  if (isStuPic) {
    canvas = canvas1;
    inputImage = inputImage1;
    isStuPic = false;
    buttonNewPhoto.innerHTML = buttonNewPhoto.getAttribute("state2");
  } else {
    canvas = canvas2;
    inputImage = inputImage2;
    isStuPic = true;
    buttonNewPhoto.innerHTML = buttonNewPhoto.getAttribute("state1");
  }
  const video_width = video.offsetWidth;
  const video_height = video.offsetHeight;
  const ratio = video_width / video_height;

  let target_width;
  let target_height;
  let y_of_video = 0;
  let x_of_video = 0;

  if (video_width > video_height) {
    target_width = canvas.width;
    target_height = canvas.width / ratio;
    y_of_video = (canvas.height - target_height) / 2;
  } else {
    target_width = canvas.height;
    target_height = canvas.height * ratio;
    x_of_video = (canvas.width - target_width) / 2;
  }

  let ctx = canvas.getContext("2d");
  ctx.drawImage(video, x_of_video, y_of_video, target_width, target_height);

  canvas.toBlob((blob) => {
    const file = new File([blob], "mycanvas" + numOfPhoto + ".png");
    const dataTransfer = new DataTransfer();
    dataTransfer.items.add(file);
    inputImage.files = dataTransfer.files;
  });
  numOfPhoto++;

  if (numOfPhoto >= 2) {
    // const link = document.createElement("a");
    // link.download = "photo.jpg";
    // link.setAttribute("href", canvas1.toDataURL("image/jpeg"));
    // link.dispatchEvent(new MouseEvent("click"));
    // numOfPhoto = 0;
    attenButton.removeAttribute("disabled");
  }
}

function onMediaError(err) {
  message.innerHTML = err.name + ": " + err.message;
}

const initEvent = function () {
  navigator.mediaDevices
    .getUserMedia({ video: true })
    .then(onMediaStream)
    .catch(onMediaError);
};

function zfill(num, len) {
  return (Array(len).join("0") + num).slice(-len);
}

const updateTime = function () {
  const date = new Date();
  currentDate.innerHTML =
    dayNames[date.getDay()] +
    " " +
    date.getDate() +
    " " +
    monthNamesThai[date.getMonth()] +
    "  " +
    date.getFullYear();
  currentTime.innerHTML =
    zfill(date.getHours(), 2) +
    ":" +
    zfill(date.getMinutes(), 2) +
    ":" +
    zfill(date.getSeconds(), 2);
};

const init = () => {
  const latitudeInput = document.getElementById("latitudeInput");
  const longitudeInput = document.getElementById("longitudeInput");
    const location = document.getElementById("location");
  getLocation(location, latitudeInput, longitudeInput);
  initElement();
  initEvent();
  timeHandler = setInterval(updateTime, 1000);
};

window.addEventListener("DOMContentLoaded", (event) => {
  init();
});
