<?php
use src\models\DbConnect;
use src\models\User_Event;

require "../vendor/autoload.php";

$response = array();
$response['error'] = false;
$response['message'] = "Server: Default message";

// check if fields are not empty
if (!isset($_POST['userId'])){
    $response['error'] = true;
    $response['message'] = "Server: userId field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['competitionId'])){
    $response['error'] = true;
    $response['message'] = "Server: competitionId field is empty";
    echo json_encode($response);
    exit();
}

$userId = $_POST['userId'];
$competitionId = $_POST['competitionId'];

$db_connect = new DbConnect();
$db_connect->connect();

try
{
    // create user
    $user_event = new User_Event();
    $user_event->setUserId($userId);
    $user_event->setCompetitionId($competitionId);

    if ($db_connect->checkIfUserRegisteredToEvent($user_event)){
        $response['error'] = false;
        $response['registered'] = true;
        $response['message'] = "Server: user is registered in this event.";
    } else {
        $response['error'] = false;
        $response['registered'] = false;
        $response['message'] = "Server: user is not registered in this event..";
    }
}
catch (Exception $err){
    $response['error'] = true;
    $response['message'] = "Server: Unknown error. ".$err;
}

echo json_encode($response);

?>