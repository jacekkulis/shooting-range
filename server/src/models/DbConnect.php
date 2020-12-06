<?php
namespace src\models;


class DbConnect
{
    private $db_host;
    private $db_user;
    private $db_password;
    private $db_name;

    /** @var \mysqli */
    private $connection;

    /**
     * A class that helps with connection with database
     */
    function __construct() {
        $this->db_host = "localhost";
        $this->db_user = "root";
        $this->db_password = "";
        $this->db_name = "rangedb";
    }

    function __destruct() {
        $this->close();
    }

    /**
     * Connects to database, returns true if connected,
     * returns false if some error happened.
     *
     * @param \mysqli_sql_exception $error Exception that was received
     * @throws \Exception
     * @return boolean
     */
    public function connect() {
        // Connecting to mysql database
        $this->connection = mysqli_connect($this->db_host, $this->db_user, $this->db_password, $this->db_name);
        @$this->connection->query("SET NAMES utf8 COLLATE utf8_polish_ci");
        mysqli_report(MYSQLI_REPORT_STRICT);

        if($this->connection->connect_errno != 0)
        {
            throw new \Exception(mysqli_connect_errno());
        }



        @$this->connection->query("SET NAMES utf8 COLLATE utf8_polish_ci");

        return true;
    }


    /**
     * Closes connection with database.
     */
    public function close() {
        $this->connection->close();
    }

    /**
     * ################################################################################################################# USER
     *
     */


    /**
     * Returns User object if user exist and credentials are valid or
     * returns null if something wrong.
     *
     * @param String  $username Account username.
     *
     * @return User
     */
    public function userLogin($username){
        $username = htmlentities($username, ENT_QUOTES, "UTF-8");
        $result = @$this->connection->query("SELECT idClubMember, username, name, surname, idClub, legitimationNumber, phoneNumber, city, address, isAdmin FROM clubmember WHERE username='$username'");

        $users = $result->num_rows;

        // check if user exist
        if($users > 0)
        {
            $userResult = $result->fetch_assoc();
            $user = new User();
            $user->setIdClubMember($userResult['idClubMember']);
            $user->setUsername($userResult['username']);
            $user->setName($userResult['name']);
            $user->setSurname($userResult['surname']);
            $user->setIdClub($userResult['idClub']);
            $user->setLegitimationNumber($userResult['legitimationNumber']);
            $user->setPhoneNumber($userResult['phoneNumber']);
            $user->setCity($userResult['city']);
            $user->setAddress($userResult['address']);
            $user->setIsAdmin($userResult['isAdmin']);
            $result->free_result();


            return $user;
        }
        else {
            return null;
        }
    }

    public function adminLogin($username, $password){
        $username = htmlentities($username, ENT_QUOTES, "UTF-8");
        $result = @$this->connection->query("SELECT idClubMember, username, name, surname, idClub, legitimationNumber, phoneNumber, city, address, isAdmin, password FROM clubmember WHERE username='$username'");

        $users = $result->num_rows;

        // check if user exist
        if($users > 0)
        {
            $userResult = $result->fetch_assoc();
            $result->free_result();

            $user = new User();
            $user->setIdClubMember($userResult['idClubMember']);
            $user->setUsername($userResult['username']);
            $user->setName($userResult['name']);
            $user->setSurname($userResult['surname']);
            $user->setIdClub($userResult['idClub']);
            $user->setLegitimationNumber($userResult['legitimationNumber']);
            $user->setPhoneNumber($userResult['phoneNumber']);
            $user->setCity($userResult['city']);
            $user->setAddress($userResult['address']);
            $user->setIsAdmin($userResult['isAdmin']);
            $user->setPassword($userResult['password']);

            if (password_verify($password, $user->getPassword()))
            {
                return $user;
            }
            else{
                return null;
            }
        }
        else {
            return null;
        }
    }

    /**
     * Returns true if user exist and credentials are valid or
     * returns false if user doesn't exist or credentials doesn't match.
     *
     * @param User  $user User with data.
     * @throws \Exception
     * @return boolean
     */
    public function registerUser($user){
        $username = $user->getUsername();
        $name = $user->getName();
        $surname = $user->getSurname();
        $idClub = $user->getIdClub();
        $phoneNumber = $user->getPhoneNumber();
        $legitimationNumber = $user->getLegitimationNumber();
        $city = $user->getCity();
        $address = $user->getAddress();
        $hashedPassword = $user->getPassword();

        if($this->connection->query("INSERT INTO clubmember(username, name, surname, idClub, legitimationNumber, phoneNumber, city, address, password) VALUES 
            ('$username', '$name', '$surname', '$idClub', '$legitimationNumber', '$phoneNumber', '$city', '$address', '$hashedPassword')"))
        {
            return true;
        }
        else{
            $response = array();
            $response['error'] = false;
            $response['message'] = "Server: ".Exception($this->connection->error);
            echo json_encode($response);
            throw new \Exception($this->connection->error);
        }
    }

    /**
     * Updates user information.
     *
     * @param User $user
     * @throws \Exception
     * @return boolean
     */
    public function updateUser(User $user){
        $username = $user->getUsername();
        $name = $user->getName();
        $surname = $user->getSurname();
        $idClub = $user->getIdClub();
        $phoneNumber = $user->getPhoneNumber();
        $legitimationNumber = $user->getLegitimationNumber();
        $city = $user->getCity();
        $address = $user->getAddress();

        if($this->connection->query(sprintf("UPDATE clubmember SET name='%s', surname='%s', legitimationNumber='%s', phoneNumber='%s', idClub='%s', city='%s', address='%s' WHERE username='%s'", $name, $surname, $legitimationNumber, $phoneNumber, $idClub, $city, $address, $username)))
        {
            return true;
        }
        else{
            $response = array();
            $response['error'] = false;
            $response['message'] = "Server: ".Exception($this->connection->error);
            echo json_encode($response);
            throw new \Exception($this->connection->error);
        }
    }

    /**
     * Checks if username exist, true if exist, false if not.
     *
     * @param String  $username Username (login).
     * @throws \Exception
     * @return boolean
     */
    public function userExist($username){
        $resultUsername = $this->connection->query("SELECT idClubMember FROM clubmember WHERE username='$username'");

        if (!$resultUsername)
            throw new \Exception($this->connection->error);

        if ($resultUsername->num_rows > 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public function getUserList(){
        $resultEvents = $this->connection->query("SELECT * FROM clubmember");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getUserIdByUsername($username){
        $result = @$this->connection->query("SELECT idClubMember FROM clubmember WHERE username='$username'");
        $row = mysqli_fetch_array($result);

        return $row['idClubMember'];
    }

    /**
     * @param $user_event
     * @return bool
     * @throws \Exception
     */
    public function addUserToEvent(User_Event $user_event)
    {
        $userId = $user_event->getUserId();
        $competitionId = $user_event->getCompetitionId();

        if($this->connection->query(sprintf("INSERT INTO competition_clubmember(idClubMember, idCompetition) VALUES ('$userId', '$competitionId')")))
        {
            return true;
        }
        else{
            $response = array();
            $response['error'] = false;
            $response['message'] = "Server: ".Exception($this->connection->error);
            echo json_encode($response);
            throw new \Exception($this->connection->error);
        }
    }

    /**
     * @param $user_event
     * @return bool
     * @throws \Exception
     */
    public function removeUserFromEvent(User_Event $user_event)
    {
        $userId = $user_event->getUserId();
        $competitionId = $user_event->getCompetitionId();

        if($this->connection->query(sprintf("DELETE FROM competition_clubmember WHERE idClubMember='$userId' AND idCompetition='$competitionId'")))
        {
            return true;
        }
        else{
            $response = array();
            $response['error'] = false;
            $response['message'] = "Server: ".Exception($this->connection->error);
            echo json_encode($response);
            throw new \Exception($this->connection->error);
        }
    }

    public function checkIfUserRegisteredToEvent($user_event)
    {
        $userId = $user_event->getUserId();
        $competitionId = $user_event->getCompetitionId();

        $resultUsername = $this->connection->query("SELECT * FROM competition_clubmember WHERE idClubMember='$userId' AND idCompetition='$competitionId'");

        if (!$resultUsername)
            throw new \Exception($this->connection->error);

        if ($resultUsername->num_rows > 0)
        {
            return true;
        }
        else {
            return false;
        }
    }

    public function deleteUser($idClubMember){
        if($this->connection->query(sprintf("DELETE FROM clubmember WHERE idClubMember='$idClubMember'")))
        {
            return true;
        }
        else{
            return false;
        }
    }

    public function getUserById($id){
        $resultUser = $this->connection->query("SELECT * FROM clubmember WHERE idClubMember='$id'");

        if (!$resultUser)
            throw new \Exception($this->connection->error);
        else
            return $resultUser;
    }

    /**
     * ################################################################################################################ EVENT
     *
     */

    public function getTotalNumberOfEvents(){
        return mysqli_num_rows(mysqli_query($this->connection, "SELECT idCompetition FROM competition"));
    }

    public function getTotalNumberOfAvailableEvents($userId){
        $number = @$this->connection->query("SELECT DISTINCT COUNT(competition.idCompetition) as number FROM competition LEFT JOIN competition_clubmember 
                                                ON competition.idCompetition=competition_clubmember.idCompetition
                                                WHERE competitionDate > CURRENT_TIMESTAMP()
                                                AND (competition_clubmember.idClubMember IS NULL 
                                                OR competition_clubmember.idClubMember!='$userId')");

        $row = mysqli_fetch_array($number);
        return $row['number'];
    }

    public function getTotalNumberOfOutdatedEvents(){
        return mysqli_num_rows(mysqli_query($this->connection, "SELECT idCompetition FROM competition WHERE competitionDate <= CURRENT_TIMESTAMP()"));
    }

    public function getTotalNumberOfUserEvents($id){
        $number = @$this->connection->query("SELECT DISTINCT COUNT(competition.idCompetition) as number FROM competition, competition_clubmember WHERE competition_clubmember.idCompetition=competition.idCompetition
                                                                        AND competition_clubmember.idClubMember='$id'");

        $row = mysqli_fetch_array($number);
        return $row['number'];
    }

    public function getIncomingEvents($userId){
        $resultEvents = @$this->connection->query("SELECT competition.* FROM competition LEFT JOIN competition_clubmember 
                                                ON competition.idCompetition=competition_clubmember.idCompetition
                                                WHERE competitionDate > CURRENT_TIMESTAMP()
                                                AND (competition_clubmember.idClubMember IS NULL 
                                                OR competition_clubmember.idClubMember!='$userId')
                                                ORDER BY competitionDate DESC");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getOutdatedEvents(){
        $resultEvents = $this->connection->query("SELECT * FROM competition WHERE competitionDate <= CURRENT_TIMESTAMP() ORDER BY competitionDate DESC");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getUserEvents($userId){
        $resultEvents = @$this->connection->query("SELECT * FROM competition, competition_clubmember 
                                                    WHERE competition_clubmember.idCompetition=competition.idCompetition
                                                    AND competition_clubmember.idClubMember='$userId'
                                                    ORDER BY competitionDate DESC");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getEventList(){
        $resultEvents = $this->connection->query("SELECT * FROM competition");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function deleteEvent($idCompetition){
        if($this->connection->query(sprintf("DELETE FROM competition WHERE idCompetition='$idCompetition'")))
        {
            return true;
        }
        else{
            return false;
        }
    }

    public function getEventById($id){
        $resultEvent = $this->connection->query("SELECT * FROM competition WHERE idCompetition='$id'");

        if (!$resultEvent)
            throw new \Exception($this->connection->error);
        else
            return $resultEvent;
    }

    public function createEvent($idMainReferee, $title, $description, $price, $idRater, $idTypeOfCompetition, $date, $thumbnailUrl){
        if($this->connection->query("INSERT INTO competition(idMainReferee, competitionName, competitionDescription, price, idRater, idTypeOfCompetition, competitionDate, thumbnailUrl) 
                                    VALUES ('$idMainReferee', '$title', '$description', '$price', '$idRater', '$idTypeOfCompetition', '$date', '$thumbnailUrl')"))
        {
            return true;
        }
        else{
            throw new \Exception($this->connection->error);
        }
    }

    public function updateEvent($id, $idMainReferee, $title, $description, $price, $idRater, $idTypeOfCompetition, $date, $thumbnailUrl){

        if($this->connection->query(sprintf("UPDATE competition SET idMainReferee='%s', competitionName='%s', competitionDescription='%s', price='%s', idRater='%s', idTypeOfCompetition='%s', competitionDate='%s', thumbnailUrl='%s' WHERE idCompetition='%s'", $idMainReferee, $title, $description, $price, $idRater, $idTypeOfCompetition, $date, $thumbnailUrl, $id))) {
            return true;
        }
        else{
            throw new \Exception($this->connection->error);
        }
    }

    public function updateEventWithoutThumbnail($id, $idMainReferee, $title, $description, $price, $idRater, $idTypeOfCompetition, $date){
        if($this->connection->query(sprintf("UPDATE competition SET idMainReferee='%s', competitionName='%s', competitionDescription='%s', price='%s', idRater='%s', idTypeOfCompetition='%s', competitionDate='%s' WHERE idCompetition='%s'", $idMainReferee, $title, $description, $price, $idRater, $idTypeOfCompetition, $date, $id))) {
            return true;
        }
        else{
            throw new \Exception($this->connection->error);
        }
    }

    /**
     * ################################################################################################################ CLUB
     *
     */

    /**
     * Returns club name list
     */
    public function getClubList(){
        $resultClubNames = @$this->connection->query("SELECT club.clubName FROM club");

        if (!$resultClubNames)
            throw new \Exception($this->connection->error);

        if ($resultClubNames->num_rows > 0)
        {
            $clubNames = array();
            while($club = mysqli_fetch_row($resultClubNames)){
                array_push($clubNames, $club[0]);
            }

            return $clubNames;
        }
        else {
            return null;
        }
    }

    public function getClubListForDropDownList(){
        $resultClubs = $this->connection->query("SELECT * FROM club");

        if (!$resultClubs)
            throw new \Exception($this->connection->error);
        else
            return $resultClubs;
    }

    public function getNumberOfMembers($clubId){
        $result = $this->connection->query("SELECT count(idClubMember) as number FROM clubmember
                                                    LEFT JOIN club ON club.idClub=clubmember.idClub
                                                    WHERE club.idClub='$clubId'");

        if (!$result)
            throw new \Exception($this->connection->error);
        else
            return $result;
    }


    /**
     * ################################################################################################################ MAIN REFEREE
     *
     */

    public function getMainRefereeList(){
        $resultEvents = $this->connection->query("SELECT * FROM mainReferee");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getMainRefereeById($id){
        $resultMainReferee = $this->connection->query("SELECT * FROM mainreferee WHERE idMainReferee='$id'");

        if (!$resultMainReferee)
            throw new \Exception($this->connection->error);
        else
            return $resultMainReferee;
    }


    /**
     * ################################################################################################################ RATER
     *
     */

    public function getRaterList(){
        $resultEvents = $this->connection->query("SELECT * FROM rater");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getRaterById($id){
        $resultRater = $this->connection->query("SELECT * FROM rater WHERE idRater='$id'");

        if (!$resultRater)
            throw new \Exception($this->connection->error);
        else
            return $resultRater;
    }

    /**
     * ################################################################################################################ TYPE OF COMPETITION
     *
     */

    public function getTypeOfCompetitionList(){
        $resultEvents = $this->connection->query("SELECT * FROM typeOfCompetition");

        if (!$resultEvents)
            throw new \Exception($this->connection->error);
        else
            return $resultEvents;
    }

    public function getTypeOfCompetitionById($id){
        $resultTypeOfCompetition = $this->connection->query("SELECT * FROM typeofcompetition WHERE idTypeOfCompetition='$id'");

        if (!$resultTypeOfCompetition)
            throw new \Exception($this->connection->error);
        else
            return $resultTypeOfCompetition;
    }
}