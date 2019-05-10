<?php
   include_once 'connect.php';

    $name = isset($_POST['name']) ? $_POST['name'] : '';
    $email = isset($_POST['email']) ? $_POST['email'] : '';
    $password = isset($_POST['password']) ? $_POST['password'] : '';
    $user_firstname = isset($_POST['user_firstname']) ? $_POST['user_firstname'] : '';
    $user_lastname = isset($_POST['user_lastname']) ? $_POST['user_lastname'] : '';
    $user_age = isset($_POST['user_age']) ? $_POST['user_age'] : '';
    $user_tel = isset($_POST['user_tel']) ? $_POST['user_tel'] : '';
    $user_sex = isset($_POST['user_sex']) ? $_POST['user_sex'] : '';
    $security_code =isset($_POST['security_code']) ? $_POST['security_code'] : '';

  $sql="SELECT * FROM users WHERE email = '$email '";

if ($result=mysqli_query($conn,$sql))
  {
  // Return the number of rows in result set
  $rowcount=mysqli_num_rows($result);
 
  if($rowcount == 0){
       $sql = "INSERT INTO users (name, email , password, user_firstname, user_lastname, user_age, user_tel, user_sex, security_code) VALUES ('$name', '$email', '$password', '$user_firstname', '$user_lastname', '$user_age', '$user_tel', '$user_sex', '$security_code')";
    
    if ($conn->query($sql) === TRUE) {
        echo "true";
    } else {
        echo "false";
    }
  }else{
  echo "ซ้ำ";
    }
  // Free result set
  mysqli_free_result($result);
  }

mysqli_close($conn);
?>