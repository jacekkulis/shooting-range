<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 17.11.2017
 * Time: 12:03
 */

namespace src\models;


class User_Event implements \JsonSerializable
{
    private $competitionId;
    private $userId;


    /**
     * Event constructor.
     */
    public function __construct()
    {
    }

    /**
     * @return mixed
     */
    public function getCompetitionId()
    {
        return $this->competitionId;
    }

    /**
     * @param mixed $competitionId
     */
    public function setCompetitionId($competitionId)
    {
        $this->competitionId = $competitionId;
    }

    /**
     * @return mixed
     */
    public function getUserId()
    {
        return $this->userId;
    }

    /**
     * @param mixed $userId
     */
    public function setUserId($userId)
    {
        $this->userId = $userId;
    }

    public function jsonSerialize() {
        return [
            'competitionId' => $this->competitionId,
            'userId' => $this->userId,
        ];
    }


}