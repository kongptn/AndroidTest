<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";
    $stadium_name = $_POST["stadium_name"];
    $description = $_POST["description"];

    $query = "INSERT INTO football_activity (stadium_name, description) values ('$stadium_name', '$description');";
    mysqli_query($con,$query) or die (mysqli_error($con));
    mysqli_close($con);
}