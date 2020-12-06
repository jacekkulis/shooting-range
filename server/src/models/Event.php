<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 17.11.2017
 * Time: 12:03
 */

namespace src\models;


class Event implements \JsonSerializable
{
    private $competitionId;
    private $competitionName;
    private $competitionDescription;
    private $competitionDate;

    private $mainReferee;
    private $rater;
    private $typeOfCompetition;

    private $price;
    private $thumbnailUrl;

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
    public function getCompetitionName()
    {
        return $this->competitionName;
    }

    /**
     * @param mixed $competitionName
     */
    public function setCompetitionName($competitionName)
    {
        $this->competitionName = $competitionName;
    }

    /**
     * @return mixed
     */
    public function getCompetitionDescription()
    {
        return $this->competitionDescription;
    }

    /**
     * @param mixed $competitionDescription
     */
    public function setCompetitionDescription($competitionDescription)
    {
        $this->competitionDescription = $competitionDescription;
    }

    /**
     * @return mixed
     */
    public function getCompetitionDate()
    {
        return $this->competitionDate;
    }

    /**
     * @param mixed $competitionDate
     */
    public function setCompetitionDate($competitionDate)
    {
        $this->competitionDate = $competitionDate;
    }

    /**
     * @return mixed
     */
    public function getPrice()
    {
        return $this->price;
    }

    /**
     * @param mixed $price
     */
    public function setPrice($price)
    {
        $this->price = $price;
    }

    /**
     * @return mixed
     */
    public function getThumbnailUrl()
    {
        return $this->thumbnailUrl;
    }

    /**
     * @param mixed $thumbnailUrl
     */
    public function setThumbnailUrl($thumbnailUrl)
    {
        $this->thumbnailUrl = $thumbnailUrl;
    }


    /**
     * @return MainReferee
     */
    public function getMainReferee()
    {
        return $this->mainReferee;
    }

    /**
     * @param MainReferee $mainReferee
     */
    public function setMainReferee(MainReferee $mainReferee)
    {
        $this->mainReferee = $mainReferee;
    }

    /**
     * @return Rater
     */
    public function getRater()
    {
        return $this->rater;
    }

    /**
     * @param Rater $rater
     */
    public function setRater(Rater $rater)
    {
        $this->rater = $rater;
    }

    /**
     * @return TypeOfCompetition
     */
    public function getTypeOfCompetition()
    {
        return $this->typeOfCompetition;
    }

    /**
     * @param TypeOfCompetition $typeOfCompetition
     */
    public function setTypeOfCompetition(TypeOfCompetition $typeOfCompetition)
    {
        $this->typeOfCompetition = $typeOfCompetition;
    }

    public function jsonSerialize() {
        return [
            'idCompetition' => $this->competitionId,
            'competitionName' => $this->competitionName,
            'competitionDescription' => $this->competitionDescription,
            'competitionDate' => $this->competitionDate,
            'thumbnailUrl' => $this->thumbnailUrl,
            'price' => $this->price,

            'mainReferee' => $this->mainReferee,
            'rater' => $this->rater,
            'typeOfCompetition' => $this->typeOfCompetition
        ];
    }


}