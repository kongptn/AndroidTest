<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";
    $name = $_POST["name"];
    $lastname = $_POST["lastname"];

    $query = "INSERT INTO newstudent (name, lastname) VALUES ('$name', '$lastname')";
    mysqli_query($con,$query) or die (mysqli_error($con));
    mysqli_close($con);
}
?>