<?php

if($_SERVER['REQUEST_METHOD'] =='POST'){

    require_once 'connect.php';
    $email = $_POST['email'];
    $photo_user = $_POST['photo_user'];
    
    $sql = "SELECT  email FROM users WHERE email='$email'";
    $result = mysqli_query($conn,$sql);
    $check = mysqli_fetch_array($result);



    if(isset($check)){
        echo 'login oldAcc';
        }else if($check==null){
        echo'login New';
            $ppp = "INSERT INTO users (email,photo_user) VALUES ('$email','$photo_user')";
                $result = mysqli_query($conn,$ppp);
        }
        }else{
        echo 'error';
    }
?>