<?php
define('HOST','http://203.158.131.67');
define('USER','Findjoinsport');
define('PASS','Prekong@123');
define('DATABASE','Findjoinsport');

$con = mysqli_connect(HOST,USER,PASS,DATABASE);
if($con->connect_error){
    die("Connection error: " . $con->connect_error);

}else{
    mysqli_set_charset($con, "utf8");
}
