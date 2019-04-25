<?php

if($_SERVER['REQUEST_METHOD'] =='POST'){

    $user_create = $_POST['user_create'];
    $userid_join = $_POST['userid_join'];
    $Notification = $_POST['Notification'];
    $status_noti = $_POST['status_noti'];
   

   // $password = password_hash($password, PASSWORD_DEFAULT);

    require_once 'connect.php';

    $sql = "INSERT INTO notification (user_send, user_get, noti_text, status_id) VALUES ('$userid_join', '$user_create', '$Notification', '$status_noti')";

    if( mysqli_query($conn, $sql) ) {
        $result["success"] = "1";
        $result["message"] = "success";

        echo json_encode($result);
        mysqli_close($conn);
    }
}

?>