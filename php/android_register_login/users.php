<?php

    $host='localhost';
    $username='root';
    $pwd='';
    $db="findjoinsport";

    $con=mysqli_connect($host,$username,$pwd,$db) or die('Unable to connect');

    if(mysqli_connect_error($con))
    {
        echo "Failed to Connect to Database ".mysqli_connect_error();
    }

    $query=mysqli_query($con,"SELECT * FROM users");
    if($query)
    {
        while($row=mysqli_fetch_array($query))
        {
            $flag[]=$row;
        }

        print(json_encode($flag));
    }
    mysqli_close($con);
    ?>