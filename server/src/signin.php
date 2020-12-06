<?php
    use src\models\DbConnect;

    require "../vendor/autoload.php";
    // array for JSON response
    $response = array();

	if(!isset($_POST['username']))
	{
        $response['error'] = true;
        $response["message"] = "Server: Username field is not set.";
        echo json_encode($response);
	}
    else {
        $db_connect = new DbConnect();
        $db_connect->connect();

        $username = $_POST["username"];
        $username = htmlentities($username, ENT_QUOTES, "UTF-8");

        $user = $db_connect->userLogin($username);

        if ($user != null){
            $response['error'] = false;
            $response['user'] = $user;
        }
        else {
            $response['error'] = true;
            $response['message'] = "Server: Invalid username.";
        }

        echo json_encode($response);
    }
?>