<?php

if ($_SERVER['REQUEST_METHOD']=='POST') {

    $email = $_POST['email'];

    require_once 'connect.php';

    $sql = "SELECT * FROM users WHERE email='$email' ";

    $response = mysqli_query($conn, $sql);

    $result = array();
    $result['login'] = array();
    
    if ( mysqli_num_rows($response) === 1 ) {
        
        $row = mysqli_fetch_assoc($response);

        if (isset($_POST['email'])) {
            
            $index['name'] = $row['name'];
            $index['email'] = $row['email'];           
            $index['user_id'] = $row['user_id'];
            $index['user_firstname'] = $row['user_firstname'];
            $index['user_lastname'] = $row['user_lastname'];
            $index['password'] = $row['password'];
            $index['user_age']       = $row['user_age'] ;
             $index['user_tel']       = $row['user_tel'] ;
             $index['user_sex']       = $row['user_sex'] ;
             $index['security_code']       = $row['security_code'] ;
    
            array_push($result['login'], $index);

            $result['success'] = "1";
            $result['message'] = "success";
            echo json_encode($result);

            mysqli_close($conn);

        } else {

            $result['success'] = "0";
            $result['message'] = "error";
            echo json_encode($result);

            mysqli_close($conn);

        }

    }

}

?>