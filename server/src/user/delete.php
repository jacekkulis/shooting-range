<?php
use src\models\DbConnect;
use src\models\Event;
use src\models\MainReferee;
use src\models\Rater;
use src\models\TypeOfCompetition;

session_start();

if (!isset($_SESSION['logged'])) {
    header('Location: index.php');
    exit();
}

require "../../vendor/autoload.php";

// array for JSON response
$response = array();

$db_connect = new DbConnect();
try {
    $db_connect->connect();

    $userId = $_GET['id'];

    $result = $db_connect->deleteUser($userId);

    if ($result)
        $_SESSION["info"] = "User has been deleted.";
    else
        $_SESSION['error'] = "There was error while deleting user.";


    header('Location: ../users.php');
} catch (Exception $e) {
    $_SESSION['error'] = "Unexpected error. " . $e->getMessage();
    header('Location: ../users.php');
}
?>