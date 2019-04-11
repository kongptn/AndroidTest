<?php 
	//Creating a connection
    
    include_once 'connect.php';
    // รับค่าจากแอป
    $user_tel = (isset($_POST["user_tel"]))?$_POST["user_tel"]:"";
    $security_code = (isset($_POST["security_code"]))?$_POST["security_code"]:"";
    

 
    $sql ="SELECT * FROM users WHERE user_tel = '$user_tel' AND security_code = '$security_code'";
    
    $result = mysqli_query($conn ,$sql);
	
    
    if ($result=mysqli_query($conn,$sql)) {
        // Return the number of rows in result set
        // เช็คว่ามี่าซ้ำหรือป่าว
        $rowcount=mysqli_num_rows($result);
        if($rowcount == 0){
            //ถ้าซ้ำส้ง false
            echo "false";
        }else {
            //ถ้าไม่ก็ส่งข้อมลกลับไป
            while ($row = mysqli_fetch_assoc($result)) {
                $rows[] = $row;
                echo json_encode($row);
    } 
        }
// Free result set
    mysqli_free_result($result);
}  
 
    mysqli_close($conn);
?>