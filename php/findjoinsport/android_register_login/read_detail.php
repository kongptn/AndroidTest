<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {
    
    $user_id = $_POST['user_id'];

    require_once 'connect.php';

    $sql = "SELECT * FROM users WHERE user_id='$user_id' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['read'] = array();

    if( mysqli_num_rows($response) === 1 ) {
        
        if ($row = mysqli_fetch_assoc($response)) {
 
             $h['name']        = $row['name'] ;
             $h['email']       = $row['email'] ;
             $h['user_firstname']       = $row['user_firstname'] ;
             $h['user_lastname']       = $row['user_lastname'] ;
            //$h['password']       = $row['password'] ;
             $h['user_age']       = $row['user_age'] ;
             $h['user_tel']       = $row['user_tel'] ;
             $h['user_sex']       = $row['user_sex'] ;
             $h['photo_user']       = $row['photo_user'] ;
             
             array_push($result["read"], $h);
 
             $result["success"] = "1";
             echo json_encode($result);
        }
   }
 
 }else {
 
     $result["success"] = "0";
     $result["message"] = "Error!";
     echo json_encode($result);
 
    // mysqli_close($conn);
 }
 
 ?>