<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 17.11.2017
 * Time: 12:03
 */

namespace src\models;


class TypeOfCompetition implements \JsonSerializable
{
    private $typeOfCompetitionId;
    private $typeOfCompetitionNameOfType;
    private $typeOfCompetitionFullName;

    /**
     * TypeOfCompetition constructor.
     */
    public function __construct()
    {
    }


    /**
     * @return mixed
     */
    public function getTypeOfCompetitionId()
    {
        return $this->typeOfCompetitionId;
    }

    /**
     * @param mixed $typeOfCompetitionId
     */
    public function setTypeOfCompetitionId($typeOfCompetitionId)
    {
        $this->typeOfCompetitionId = $typeOfCompetitionId;
    }

    /**
     * @return mixed
     */
    public function getTypeOfCompetitionNameOfType()
    {
        return $this->typeOfCompetitionNameOfType;
    }

    /**
     * @param mixed $typeOfCompetitionNameOfType
     */
    public function setTypeOfCompetitionNameOfType($typeOfCompetitionNameOfType)
    {
        $this->typeOfCompetitionNameOfType = $typeOfCompetitionNameOfType;
    }

    /**
     * @return mixed
     */
    public function getTypeOfCompetitionFullName()
    {
        return $this->typeOfCompetitionFullName;
    }

    /**
     * @param mixed $typeOfCompetitionFullName
     */
    public function setTypeOfCompetitionFullName($typeOfCompetitionFullName)
    {
        $this->typeOfCompetitionFullName = $typeOfCompetitionFullName;
    }


    public function jsonSerialize() {
        return [
            'typeOfCompetitionId' => $this->typeOfCompetitionId,
            'typeOfCompetitionNameOfType' => $this->typeOfCompetitionNameOfType,
            'typeOfCompetitionFullName' => $this->typeOfCompetitionFullName
        ];
    }

}