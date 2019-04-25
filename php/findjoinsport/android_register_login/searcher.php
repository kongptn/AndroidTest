<?php

require_once 'connect.php';
$name = isset($_POST['Query']);


$sql="SELECT * FROM users WHERE name LIKE '%$name%'";

$query = mysqli_query($conn, $sql);

if($query)
{
while($row = mysqli_fetch_array($query))
{
    $data[]=$row;
}
print(json_encode($data));

}else
{
echo('Not Found');
}
mysqli_close($conn);

?>