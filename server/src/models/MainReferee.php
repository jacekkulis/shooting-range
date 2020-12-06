<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 17.11.2017
 * Time: 12:03
 */

namespace src\models;


class MainReferee implements \JsonSerializable
{
    private $mainRefereeId;
    private $mainRefereeName;
    private $mainRefereeSurname;
    private $mainRefereeLegitimationNumber;
    private $mainRefereeTitle;

    /**
     * MainReferee constructor.
     */
    public function __construct()
    {
    }


    /**
     * @return mixed
     */
    public function getMainRefereeId()
    {
        return $this->mainRefereeId;
    }

    /**
     * @param mixed $mainRefereeId
     */
    public function setMainRefereeId($mainRefereeId)
    {
        $this->mainRefereeId = $mainRefereeId;
    }

    /**
     * @return mixed
     */
    public function getMainRefereeName()
    {
        return $this->mainRefereeName;
    }

    /**
     * @param mixed $mainRefereeName
     */
    public function setMainRefereeName($mainRefereeName)
    {
        $this->mainRefereeName = $mainRefereeName;
    }

    /**
     * @return mixed
     */
    public function getMainRefereeSurname()
    {
        return $this->mainRefereeSurname;
    }

    /**
     * @param mixed $mainRefereeSurname
     */
    public function setMainRefereeSurname($mainRefereeSurname)
    {
        $this->mainRefereeSurname = $mainRefereeSurname;
    }

    /**
     * @return mixed
     */
    public function getMainRefereeLegitimationNumber()
    {
        return $this->mainRefereeLegitimationNumber;
    }

    /**
     * @param mixed $mainRefereeLegitimationNumber
     */
    public function setMainRefereeLegitimationNumber($mainRefereeLegitimationNumber)
    {
        $this->mainRefereeLegitimationNumber = $mainRefereeLegitimationNumber;
    }

    /**
     * @return mixed
     */
    public function getMainRefereeTitle()
    {
        return $this->mainRefereeTitle;
    }

    /**
     * @param mixed $mainRefereeTitle
     */
    public function setMainRefereeTitle($mainRefereeTitle)
    {
        $this->mainRefereeTitle = $mainRefereeTitle;
    }


    /**
     * @return mixed
     */
    public function getMainRefereeFullName()
    {
        return $this->mainRefereeName." ".$this->mainRefereeSurname;
    }

    public function jsonSerialize() {
        return [
            'mainRefereeId' => $this->mainRefereeId,
            'mainRefereeName' => $this->mainRefereeName,
            'mainRefereeSurname' => $this->mainRefereeSurname,
            'mainRefereeLegitimationNumber' => $this->mainRefereeLegitimationNumber,
            'mainRefereeTitle' => $this->mainRefereeTitle,
        ];
    }

}