<?php  
if($_SERVER['REQUEST_METHOD']=='GET'){    
     $insert_facebook_login = $_GET['idfacebook'];    
      require_once('connect.php');

     $sql = "SELECT  users FROM user_id WHERE email='$insert_facebook_login'";    
     $result = mysqli_query($conn,$sql);    
    $check = mysqli_fetch_array($result);    
         if(isset($check)){        
             echo 'login oldAcc';      
         }else if($check==null){        
             echo'login New';    
         }
     $plam = "INSERT INTO users(email) VALUES ('$insert_facebook_login')";   
     $result = mysqli_query($conn,$plam);      
                   
            }else{       
                echo 'error';
           } 
     ?>