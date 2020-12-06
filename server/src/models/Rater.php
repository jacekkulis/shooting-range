<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 17.11.2017
 * Time: 12:03
 */

namespace src\models;


class Rater implements \JsonSerializable
{
    private $raterId;
    private $raterName;
    private $raterSurname;
    private $raterLegitimationNumber;
    private $raterTitle;

    /**
     * Rater constructor.
     */
    public function __construct()
    {
    }


    /**
     * @return mixed
     */
    public function getRaterId()
    {
        return $this->raterId;
    }

    /**
     * @param mixed $raterId
     */
    public function setRaterId($raterId)
    {
        $this->raterId = $raterId;
    }

    /**
     * @return mixed
     */
    public function getRaterName()
    {
        return $this->raterName;
    }

    /**
     * @param mixed $raterName
     */
    public function setRaterName($raterName)
    {
        $this->raterName = $raterName;
    }

    /**
     * @return mixed
     */
    public function getRaterSurname()
    {
        return $this->raterSurname;
    }

    /**
     * @param mixed $raterSurname
     */
    public function setRaterSurname($raterSurname)
    {
        $this->raterSurname = $raterSurname;
    }

    /**
     * @return mixed
     */
    public function getRaterLegitimationNumber()
    {
        return $this->raterLegitimationNumber;
    }

    /**
     * @param mixed $raterLegitimationNumber
     */
    public function setRaterLegitimationNumber($raterLegitimationNumber)
    {
        $this->raterLegitimationNumber = $raterLegitimationNumber;
    }

    /**
     * @return mixed
     */
    public function getRaterTitle()
    {
        return $this->raterTitle;
    }

    /**
     * @param mixed $raterTitle
     */
    public function setRaterTitle($raterTitle)
    {
        $this->raterTitle = $raterTitle;
    }

    /**
     * @return mixed
     */
    public function getRaterFullName()
    {
        return $this->raterName." ".$this->raterSurname;
    }

    public function jsonSerialize() {
        return [
            'raterId' => $this->raterId,
            'raterName' => $this->raterName,
            'raterSurname' => $this->raterSurname,
            'raterLegitimationNumber' => $this->raterLegitimationNumber,
            'raterTitle' => $this->raterTitle,
        ];
    }

}