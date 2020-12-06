<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 02.11.2017
 * Time: 14:26
 */

use src\models\DbConnect;

require "../vendor/autoload.php";
// array for JSON response
$response = array();

$db_connect = new DbConnect();
$db_connect->connect();

$clubList = array();
$clubList = $db_connect->getClubList();


if(!empty($clubList)){
    $response['error'] = false;
    $response['message'] = "Server: Send successfully";
    $response['clubList'] = $clubList;
}
else {
    $response['error'] = true;
    $response['message'] = "Server: Could not fetch data!";
}

echo json_encode($response);

?>