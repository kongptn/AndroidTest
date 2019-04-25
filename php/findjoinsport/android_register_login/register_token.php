
<?php 
	//Creating a connection
	include_once 'connect.php';
    
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $token = isset($_POST['token']) ? $_POST['token'] : '';


    $sql = "UPDATE users
            SET token = '$token'
            WHERE email = '$email'";
    
    if ($conn->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
$conn->close();
?>
