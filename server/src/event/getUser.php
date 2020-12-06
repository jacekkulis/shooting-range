<?php
use src\models\DbConnect;
use src\models\Event;
use src\models\MainReferee;
use src\models\Rater;
use src\models\TypeOfCompetition;

require "../../vendor/autoload.php";

// array for JSON response
$response = array();

$db_connect = new DbConnect();
$db_connect->connect();

if (isset($_GET['username'])){
    $username = $_GET['username'];

    $userId = $db_connect->getUserIdByUsername($username);

    //Counting the total item available in the database
    $total = $db_connect->getTotalNumberOfUserEvents($userId);

    //Getting result
    $result = $db_connect->getUserEvents($userId);

    while($row = mysqli_fetch_array($result)) {
        $event = new Event();

        $event->setCompetitionId($row['idCompetition']);
        $event->setCompetitionName($row['competitionName']);
        $event->setCompetitionDescription($row['competitionDescription']);
        $event->setCompetitionDate($row['competitionDate']);
        $event->setThumbnailUrl($row['thumbnailUrl']);
        $event->setPrice($row['price']);

        // Main Referee
        $mainRefereeResult = $db_connect->getMainRefereeById($row['idMainReferee']);
        $mainRefereeArray = mysqli_fetch_array($mainRefereeResult);

        $mainRefereeObject = new MainReferee();
        $mainRefereeObject->setMainRefereeId($mainRefereeArray['idMainReferee']);
        $mainRefereeObject->setMainRefereeName($mainRefereeArray['name']);
        $mainRefereeObject->setMainRefereeSurname($mainRefereeArray['surname']);
        $mainRefereeObject->setMainRefereeLegitimationNumber($mainRefereeArray['legitimationNumber']);
        $mainRefereeObject->setMainRefereeTitle($mainRefereeArray['title']);

        $event->setMainReferee($mainRefereeObject);

        // Rater
        $raterResult = $db_connect->getRaterById($row['idRater']);
        $raterArray = mysqli_fetch_array($raterResult);

        $raterObject = new Rater();
        $raterObject->setRaterId($raterArray['idRater']);
        $raterObject->setRaterName($raterArray['name']);
        $raterObject->setRaterSurname($raterArray['surname']);
        $raterObject->setRaterLegitimationNumber($raterArray['legitimationNumber']);
        $raterObject->setRaterTitle($raterArray['title']);

        $event->setRater($raterObject);

        // Type of competition
        $typeOfCompetitionResult = $db_connect->getTypeOfCompetitionById($row['idTypeOfCompetition']);
        $typeOfCompetitionArray = mysqli_fetch_array($typeOfCompetitionResult);

        $typeOfCompetitionObject = new TypeOfCompetition();
        $typeOfCompetitionObject->setTypeOfCompetitionId($typeOfCompetitionArray['idTypeOfCompetition']);
        $typeOfCompetitionObject->setTypeOfCompetitionNameOfType($typeOfCompetitionArray['nameOfType']);
        $typeOfCompetitionObject->setTypeOfCompetitionFullName($typeOfCompetitionArray['fullName']);

        $event->setTypeOfCompetition($typeOfCompetitionObject);

        array_push($response, $event);
    }

    //Displaying the array in json format
    echo json_encode($response);
}

?>