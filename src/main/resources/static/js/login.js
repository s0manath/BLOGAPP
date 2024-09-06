 function togglePasswordVisibility() {
    var passwordField = document.getElementById("password");
    var icon = document.querySelector(".toggle-password");

    // If the password field type is currently "password", change it to "text" to show the password
    if (passwordField.type === "password") {
        passwordField.type = "text"; // Show the password
        icon.classList.remove("fa-eye-slash"); // Remove the eye icon
        icon.classList.add("fa-eye"); // Add the eye-slash icon (indicates hide password)
    } else {
        // Otherwise, change it back to "password" to hide the password
        passwordField.type = "password"; // Hide the password
        icon.classList.remove("fa-eye"); // Remove the eye-slash icon
        icon.classList.add("fa-eye-slash"); // Add the eye icon (indicates show password)
    }
}
