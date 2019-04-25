<?php

if($_SERVER['REQUEST_METHOD'] =='POST'){

    $name = $_POST['name'];
    $email = $_POST['email'];
    $password = $_POST['password'];
    $user_firstname = $_POST['user_firstname'];
    $user_lastname = $_POST['user_lastname'];
    $user_age = $_POST['user_age'];
    $user_tel = $_POST['user_tel'];
    $user_sex = $_POST['user_sex'];
    $security_code = $_POST['security_code'];


   // $password = password_hash($password, PASSWORD_DEFAULT);

    require_once 'connect.php';

    $sql = "INSERT INTO users (name, email , password, user_firstname, user_lastname, user_age, user_tel, user_sex, security_code) VALUES ('$name', '$email', '$password', '$user_firstname', '$user_lastname', '$user_age', '$user_tel', '$user_sex', '$security_code')";

    if( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>