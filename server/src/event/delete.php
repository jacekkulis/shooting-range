<?php
use src\models\DbConnect;
use src\models\Event;

session_start();

if (!isset($_SESSION['logged'])) {
    header('Location: index.php');
    exit();
}

require "../../vendor/autoload.php";

// array for JSON response

try {
    $response = array();
    $db_connect = new DbConnect();
    $db_connect->connect();

    $eventId = $_GET['id'];
    $event = mysqli_fetch_array($db_connect->getEventById($eventId));
    $thumbnailUrl = $_SERVER['DOCUMENT_ROOT'] . '/ShootingRange/src/eventImages/'. $event['thumbnailUrl'];

    if (file_exists($thumbnailUrl)) {
        unlink($thumbnailUrl);
        $_SESSION['info'] = 'File '.$thumbnailUrl.' has been deleted';
    } else {
        $_SESSION['error'] = 'Could not delete '.$thumbnailUrl.', file does not exist';
    }

    $result = $db_connect->deleteEvent($eventId);

    if ($result)
        $_SESSION["info"] = "Event has been deleted.";
    else
        $_SESSION['error'] = "There was error while deleting event.";
    header('Location: ../events.php');
} catch (Exception $e) {
    $_SESSION['error'] = "Unexpected error. " . $e->getMessage();
    header('Location: ../events.php');
}
?>