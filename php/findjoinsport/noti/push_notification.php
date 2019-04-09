<?php 

	function send_notification ($token, $message)
	{
		$url = 'https://fcm.googleapis.com/fcm/send';
		$fields = array(
			 'registration_ids' => $token,
			 'data' => $message
			);

		$headers = array(
			'Authorization:key = AAAAcsdLghE:APA91bEhdQRzhBBHojRhOaj38dcjv8uYH8fK-qJQizsWP293dHSY1nbDbgcsoxxwzZyy8qDP9BPJ4KiaFvrZMtr1-OEcvgyXAbx4Y0tn9w4OziXxJG8YN1iP5-g24qQ7U0odXKhYTgLx ',
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
	$conn = mysqli_connect("localhost","root","","findjoinsport");

    $id = isset($_POST['id']) ? $_POST['id'] : '';
	$sql = "SELECT * FROM devices WHERE id = '1'";

	$result = mysqli_query($conn,$sql);
	$token = array();

	if(mysqli_num_rows($result) > 0 ){

		while ($row = mysqli_fetch_assoc($result)) {
			$token[] = $row["token"];
		}
	}

	//mysqli_close($conn);

	$message = array("message" => " มีลูกค้าต้องการรับซ่อม");
    $message_status = send_notification($token, $message);

	echo $message_status;

 ?>
