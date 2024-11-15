'use strict';

function drawClock(ctx, radius) {
    drawFace(ctx, radius);
    drawNumbers(ctx, radius);
    drawTime(ctx, radius);
}

function drawFace(ctx, radius) {
    const grad = ctx.createRadialGradient(
        0,
        0,
        radius * 0.95,
        0,
        0,
        radius * 1.05
    );
    grad.addColorStop(0, "#26263F");
    grad.addColorStop(0.5, "white");
    grad.addColorStop(1, "#26263F");
    ctx.beginPath();
    ctx.arc(0, 0, radius, 0, 2 * Math.PI);
    ctx.fillStyle = "white";
    ctx.fill();
    ctx.strokeStyle = grad;
    ctx.lineWidth = radius * 0.05;
    ctx.stroke();
    ctx.beginPath();
    ctx.arc(0, 0, radius * 0.1, 0, 2 * Math.PI);
    ctx.fillStyle = "#26263F";
    ctx.fill();
}

function drawNumbers(ctx, radius) {
    ctx.font = radius * 0.25 + "px arial";
    ctx.textBaseline = "middle";
    ctx.textAlign = "center";
    for (let num = 1; num < 13; num++) {
        let ang = (num * Math.PI) / 6;
        ctx.rotate(ang);
        ctx.translate(0, -radius * 0.8);
        ctx.rotate(-ang);
        ctx.fillText(num.toString(), 0, 0);
        ctx.rotate(ang);
        ctx.translate(0, radius * 0.8);
        ctx.rotate(-ang);
    }
}

function drawTime(ctx, radius) {
    const now = new Date();
    let hour = now.getHours();
    let minute = now.getMinutes();
    let second = now.getSeconds();

    const currentDateTime = now.toLocaleDateString("th-TH");
    document.getElementById("current-date").innerHTML = currentDateTime + " " + hour + ":" + minute + ":" + second;

    //hour
    hour = hour % 12;
    hour =
        (hour * Math.PI) / 6 +
        (minute * Math.PI) / (6 * 60) +
        (second * Math.PI) / (360 * 60);
    drawHand(ctx, hour, radius * 0.5, radius * 0.07, "blue");
    //minute
    minute = (minute * Math.PI) / 30 + (second * Math.PI) / (30 * 60);
    drawHand(ctx, minute, radius * 0.8, radius * 0.07, "green");
    // second
    second = (second * Math.PI) / 30;
    drawHand(ctx, second, radius * 0.9, radius * 0.02, "red");
}

function drawHand(ctx, pos, length, width, color) {
    ctx.beginPath();
    ctx.lineWidth = width;
    ctx.lineCap = "round";
    ctx.moveTo(0, 0);
    ctx.rotate(pos);
    ctx.lineTo(0, -length);
    ctx.strokeStyle = color;
    ctx.stroke();
    ctx.rotate(-pos);
}

window.addEventListener("DOMContentLoaded", (event) => {
    const canvas = document.getElementById("clock");
    const ctx = canvas.getContext("2d");
    let radius = canvas.height / 2;
    ctx.translate(radius, radius);
    radius = radius * 0.9;
    setInterval(drawClock.bind(null, ctx, radius), 1000);
});
