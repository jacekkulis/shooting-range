<?php
use src\models\DbConnect;
use src\models\User;

require "../vendor/autoload.php";

$response = array();
$response['error'] = false;
$response['message'] = "Server: Default message";

// check if fields are not empty
if (!isset($_POST['username'])){
    $response['error'] = true;
    $response['message'] = "Server: Username field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['name'])){
    $response['error'] = true;
    $response['message'] = "Server: Name field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['surname'])){
    $response['error'] = true;
    $response['message'] = "Server: Surname field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['phoneNumber'])){
    $response['error'] = true;
    $response['message'] = "Server: phoneNumber field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['legitimationNumber'])){
    $response['error'] = true;
    $response['message'] = "Server: legitimationNumber field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['city'])){
    $response['error'] = true;
    $response['message'] = "Server: city field is empty";
    echo json_encode($response);
    exit();
} else if (!isset($_POST['address'])){
    $response['error'] = true;
    $response['message'] = "Server: address field is empty";
    echo json_encode($response);
    exit();
}

$username = $_POST['username'];
$name = $_POST['name'];
$surname = $_POST['surname'];
$idClub = $_POST['idClub'];
$phoneNumber = $_POST['phoneNumber'];
$legitimationNumber = $_POST['legitimationNumber'];
$city = $_POST['city'];
$address = $_POST['address'];

$db_connect = new DbConnect();
$db_connect->connect();

try
{
    // create user
    $user = new User();
    $user->setUsername($username);
    $user->setName($name);
    $user->setSurname($surname);
    $user->setIdClub($idClub);
    $user->setPhoneNumber($phoneNumber);
    $user->setLegitimationNumber($legitimationNumber);
    $user->setCity($city);
    $user->setAddress($address);

    $db_connect->updateUser($user);

    $response['error'] = false;
    $response['message'] = "Server: User update is successful.";

}
catch (Exception $err){
    $response['error'] = true;
    $response['message'] = "Server: Unknown error. ".$err;
}

echo json_encode($response);
?>