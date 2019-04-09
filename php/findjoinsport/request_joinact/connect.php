<?php
define('HOST','localhost');
define('USER','root');
define('PASS','');
define('DATABASE','findjoinsport');

$con = mysqli_connect(HOST,USER,PASS,DATABASE);
if($con->connect_error){
    die("Connection error: " . $con->connect_error);

}else{
    mysqli_set_charset($con, "utf8");
}
?>