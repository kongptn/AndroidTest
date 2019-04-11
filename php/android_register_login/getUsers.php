<?php

require_once 'connect.php';

if (isset($_GET['key'])) {
    $key = $_GET['key'];
    $query = "SELECT * FROM users WHERE name LIKE '%$key%'";
    $result = mysqli_query($conn, $query);
        $response = array();
        while($row = mysqli_fetch_assoc($result)) {
            array_push($response,
            array(
                 'user_id' =>$row['user_id'],
                 'name' =>$row['name'],
                 'user_firstname' =>$row['user_firstname'],
                 'user_lastname' =>$row['user_lastname'],
                 'user_tel' =>$row['user_tel'],
                 'user_age' =>$row['user_age'],
                 'user_sex' =>$row['user_sex'],
                 'email' =>$row['email'])

            );    
        }
        echo json_encode($response);
}else{

    $query = "SELECT * FROM users";
        $result = mysqli_query($conn, $query);
        $response = array();
        while ($row = mysqli_fetch_assoc($result)){
            array_push($response,
            array(
                'user_id' =>$row['user_id'],
                'name' =>$row['name'],
                'user_firstname' =>$row['user_firstname'],
                'user_lastname' =>$row['user_lastname'],
                'user_tel' =>$row['user_tel'],
                'user_age' =>$row['user_age'],
                'user_sex' =>$row['user_sex'],
                'email' =>$row['email'])
            );
        }
        echo json_encode($response);
}

mysqli_close($conn);
?>