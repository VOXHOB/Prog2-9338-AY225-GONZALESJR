function compute() {

    let attendance = parseFloat(prompt("Enter Attendance Grade:"));
    let lab1 = parseFloat(prompt("Enter Lab Work 1 Grade:"));
    let lab2 = parseFloat(prompt("Enter Lab Work 2 Grade:"));
    let lab3 = parseFloat(prompt("Enter Lab Work 3 Grade:"));

    let labAverage = (lab1 + lab2 + lab3) / 3;
    let classStanding = (attendance * 0.40) + (labAverage * 0.60);

    let requiredPass = (75 - (classStanding * 0.70)) / 0.30;
    let requiredExcellent = (100 - (classStanding * 0.70)) / 0.30;

    let remarks = "";

    if (requiredPass <= 100) {
        remarks += "You can still PASS the Prelim.\n";
    } else {
        remarks += "Passing is no longer possible.\n";
    }

    if (requiredExcellent <= 100) {
        remarks += "Excellent standing is achievable.";
    } else {
        remarks += "Excellent standing is not achievable.";
    }

    alert(
        "Attendance: " + attendance +
        "\nLab 1: " + lab1 +
        "\nLab 2: " + lab2 +
        "\nLab 3: " + lab3 +
        "\nLab Work Average: " + labAverage.toFixed(2) +
        "\nClass Standing: " + classStanding.toFixed(2) +
        "\n\nRequired Exam to PASS (75): " + requiredPass.toFixed(2) +
        "\nRequired Exam for EXCELLENT (100): " + requiredExcellent.toFixed(2) +
        "\n\nRemarks:\n" + remarks
    );
}
