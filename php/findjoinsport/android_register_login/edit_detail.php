<?php

if($_SERVER['REQUEST_METHOD'] == 'POST'){

    $name = $_POST['name'];
    $email = $_POST['email'];
    $user_firstname = $_POST['user_firstname'];
    $user_lastname = $_POST['user_lastname'];
    $user_age = $_POST['user_age'];
    $user_tel = $_POST['user_tel'];
    $user_sex = $_POST['user_sex'];
    $user_id = $_POST['user_id'];


    require_once 'connect.php';

    $sql = "UPDATE users SET name='$name', email='$email', user_firstname='$user_firstname', user_lastname='$user_lastname', user_age='$user_age', user_tel='$user_tel', user_sex='$user_sex' WHERE user_id='$user_id' ";

    if(mysqli_query($conn, $sql)) {

          $result["success"] = "1";
          $result["message"] = "success";

          echo json_encode($result);
          mysqli_close($conn);
      }
  }

else{

   $result["success"] = "0";
   $result["message"] = "error!";
   echo json_encode($result);

   mysqli_close($conn);
}

?>


