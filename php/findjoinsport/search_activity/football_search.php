<?php
$con=mysqli_connect("localhost","root","","findjoinsport");

$sql="SELECT * FROM activity fa inner join sport_type sp on fa.type_id = sp.type_id WHERE sp.type_id = 1;";
										



//WHERE sp.type_id = 3;
//select * from football_activity fa inner join sport_type sp on fa.type_id = sp.type_id;

$result=mysqli_query($con,$sql);

$data=array();
while($row=mysqli_fetch_assoc($result)){
$data["data"][]=$row;

}



	
	header('Content-Type:Application/json');
			
	echo json_encode($data);
	
	
?>