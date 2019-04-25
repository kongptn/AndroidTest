<?php

$conn = mysqli_connect("localhost", "root", "", "findjoinsport");
if($conn->connect_error){
    die("Connection error: " . $conn->connect_error);

}else{
    mysqli_set_charset($conn, "utf8");
}
?>