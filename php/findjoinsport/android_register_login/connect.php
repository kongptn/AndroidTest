<?php

$conn = mysqli_connect("localhost", "Findjoinsport", "Prekong@123", "Findjoinsport");
if($conn->connect_error){
    die("Connection error: " . $conn->connect_error);

}else{
    mysqli_set_charset($conn, "utf8");
}
?>