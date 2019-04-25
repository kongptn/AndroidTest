<?php

if($_SERVER['REQUEST_METHOD'] == 'POST') {

    $user_id = $_POST['user_id'];
    $photo_user = $_POST['photo_user'];

    $path = "profile_image/$user_id.jpeg";
  //  $finalPath = "http://10.13.4.64/android_register_login/".$path;

    require_once 'connect.php';

    $sql = "UPDATE users SET photo_user='$path' WHERE user_id='$user_id' ";

    if (mysqli_query($conn, $sql)) {
        
        if ( file_put_contents( $path, base64_decode($photo_user) ) ) {
            
            $result['success'] = "1";
            $result['message'] = "success";

            echo json_encode($result);
            mysqli_close($conn);

        }

    }

}

?>