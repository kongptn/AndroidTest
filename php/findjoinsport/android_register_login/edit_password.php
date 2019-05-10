<?php
   include_once 'connect.php';

   
    $password = isset($_POST['password']) ? $_POST['password'] : '';
    $passwordNew = isset($_POST['passwordNew']) ? $_POST['passwordNew'] : '';
    $user_id = isset($_POST['user_id']) ? $_POST['user_id'] : '';
   

  $sql="SELECT * FROM users  WHERE user_id='$user_id' AND password = '$password'";

if ($result=mysqli_query($conn,$sql))
  {
  // Return the number of rows in result set
  $rowcount=mysqli_num_rows($result);
 
  if($rowcount === 0){
    echo "no"; 
  }else{
  $sql = "UPDATE users SET password='$passwordNew' WHERE user_id='$user_id'";

 
   if ($conn->query($sql) === TRUE) {
       echo "true";
   } else {
       echo "false";
   }
    }
  // Free result set
  mysqli_free_result($result);
  }

mysqli_close($conn);
?>






