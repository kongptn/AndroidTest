<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";

    $stadium_name = $_POST["stadium_name"];
    $description = $_POST["description"];
    $name = $_POST['name'];

    $image = $_POST['image'];
    $upload_path = "uploads/$name.jpg";

    $query = "INSERT INTO football_activity (stadium_name, description, name) VALUES ('$stadium_name', '$description','$name')";
    if (mysqli_query($con,$query)) {
        file_put_contents($upload_path,base64_decode($image));
    } else {
        die (mysqli_error($con));
    }
    // or die (mysqli_error($con));
    mysqli_close($con);
}
?>