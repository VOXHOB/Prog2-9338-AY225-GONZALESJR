function calculate() {

    // INPUTS
    let attendance = parseFloat(prompt("Enter Attendance Score:"));
    let lab1 = parseFloat(prompt("Enter Lab Work 1 Grade:"));
    let lab2 = parseFloat(prompt("Enter Lab Work 2 Grade:"));
    let lab3 = parseFloat(prompt("Enter Lab Work 3 Grade:"));

    // COMPUTATIONS
    let labWorkAverage = (lab1 + lab2 + lab3) / 3;
    let classStanding = (attendance * 0.40) + (labWorkAverage * 0.60);

    let requiredPrelimPass =
        (75 - (classStanding * 0.70)) / 0.30;

    let requiredPrelimExcellent =
        (100 - (classStanding * 0.70)) / 0.30;

    // OUTPUT
    let output =
        "===== PRELIM GRADE COMPUTATION =====\n\n" +
        "Attendance Score: " + attendance + "\n" +
        "Lab Work 1: " + lab1 + "\n" +
        "Lab Work 2: " + lab2 + "\n" +
        "Lab Work 3: " + lab3 + "\n" +
        "Lab Work Average: " + labWorkAverage.toFixed(2) + "\n" +
        "Class Standing: " + classStanding.toFixed(2) + "\n\n" +
        "Required Prelim Exam Score:\n" +
        "To PASS (75): " + requiredPrelimPass.toFixed(2) + "\n" +
        "To be EXCELLENT (100): " + requiredPrelimExcellent.toFixed(2);

    if (requiredPrelimPass <= 100) {
        output += "\n\nRemark: You still have a chance to pass.";
    } else {
        output += "\n\nRemark: Passing the prelim is no longer possible.";
    }

    alert(output);
}
