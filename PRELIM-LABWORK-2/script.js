const correctUsername = "admin";
const correctPassword = "1234";

document.getElementById("loginForm").addEventListener("submit", function (e) {
    e.preventDefault();

    const username = document.getElementById("username").value;
    const password = document.getElementById("password").value;

    if (username === correctUsername && password === correctPassword) {
        const now = new Date();
        const timestamp = now.toLocaleString();

        document.getElementById("message").textContent =
            "Welcome, " + username + "!";
        document.getElementById("timestamp").textContent =
            "Login Time: " + timestamp;

        generateAttendanceFile(username, timestamp);
    } else {
        document.getElementById("message").textContent =
            "Incorrect username or password!";
        document.getElementById("timestamp").textContent = "";
        playBeep();
    }
});

function playBeep() {
    const audioCtx = new (window.AudioContext || window.webkitAudioContext)();
    const oscillator = audioCtx.createOscillator();
    oscillator.type = "sine";
    oscillator.frequency.setValueAtTime(1000, audioCtx.currentTime);
    oscillator.connect(audioCtx.destination);
    oscillator.start();
    setTimeout(() => oscillator.stop(), 200);
}

function generateAttendanceFile(username, timestamp) {
    const content =
        "Attendance Summary\n" +
        "-------------------\n" +
        "Username: " + username + "\n" +
        "Timestamp: " + timestamp + "\n";

    const blob = new Blob([content], { type: "text/plain" });
    const link = document.createElement("a");
    link.href = URL.createObjectURL(blob);
    link.download = "attendance_summary.txt";
    link.click();
}
