<?php
define('DB_HOST','localhost');
define('DB_USER','root');
define('DB_PASS','');
define('DB_NAME','findjoinsport');

$conn = new mysqli(DB_HOST, DB_USER, DB_PASS, DB_NAME);

if(mysqli_connect_errno()){
    echo "Fail connect MySQL: " . mysqli_connect_error();
    die();
}

$stmt = $conn->prepare("SELECT id, stadium_name, photo, date, time, name FROM activity;");

$stmt->execute();

$stmt->bind_result($id, $stadium_name, $photo, $date, $time, $name);

$gamers = array();

while($stmt->fetch()){
    $temp = array();
    $temp['id'] = $id;
    $temp['stadium_name'] = $stadium_name;
    $temp['photo'] = $photo;
    $temp['date'] = $date;
    $temp['time'] = $time;
    $temp['name'] = $name;
    array_push($gamers, $temp);
}

echo json_encode($gamers);
