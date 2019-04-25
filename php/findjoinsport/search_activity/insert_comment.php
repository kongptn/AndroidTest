<?php
if($_SERVER["REQUEST_METHOD"]=="POST"){
    include "Connect.php";

    $id = $_POST["id"];
    $user_id = $_POST["user_id"];
    $cm_data = $_POST['cm_data'];
   
   


    $query = "INSERT INTO comment_act (id, user_id, cm_data) VALUES ('$id', '$user_id','$cm_data')";
    if (mysqli_query($con,$query)) {
    } else {
        die (mysqli_error($con));
    }
    // or die (mysqli_error($con));
    mysqli_close($con);
}
?>