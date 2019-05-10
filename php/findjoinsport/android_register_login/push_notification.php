<?php 

	function send_notification ($token, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $token,
			 'data' => $message
			
			);

		$headers = array(
			'Authorization:key = AAAA_0FoOwU:APA91bGzaBR_5V4Qpc-p_QxlIVX4AvMHjf3lP8vNgYn0es5RMtOuYfAXUV6RaginbWdCdFxmzOQWi6vpXaHZs5uTSK6ubFEQ0EgSSPCTxTeOfQjUtI9lN19VW7oj8AR74nKD-3ZAWNFm ',
			'Content-Type: application/json'
			);

	   $ch = curl_init();
       curl_setopt($ch, CURLOPT_URL, $url);
       curl_setopt($ch, CURLOPT_POST, true);
       curl_setopt($ch, CURLOPT_HTTPHEADER, $headers);
       curl_setopt($ch, CURLOPT_RETURNTRANSFER, true);
       curl_setopt ($ch, CURLOPT_SSL_VERIFYHOST, 0);  
       curl_setopt($ch, CURLOPT_SSL_VERIFYPEER, false);
       curl_setopt($ch, CURLOPT_POSTFIELDS, json_encode($fields));
       $result = curl_exec($ch);           
       if ($result === FALSE) {
           die('Curl failed: ' . curl_error($ch));
       }
       curl_close($ch);
       return $result;
	}
	$conn = mysqli_connect("localhost","Findjoinsport","Prekong@123","Findjoinsport");

	$user_create = isset($_POST['user_create']) ? $_POST['user_create'] : '';
	$Notification = isset($_POST['Notification']) ? $_POST['Notification'] : '';
//	$name = isset($_POST['name']) ? $_POST['name'] : '';
	//$name = isset($_POST['name']) ? $_POST['name'] : '';

	$sql = "SELECT * FROM users WHERE user_id = '$user_create'";

	$result = mysqli_query($conn,$sql);
	$token = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$token[] = $row["token"];
		}
	}

	//mysqli_close($conn);
//	$title = array("title" => $name);
	$message = array("message" => $Notification);
    $message_status = send_notification($token,$message);

	echo $message_status;

 ?>
