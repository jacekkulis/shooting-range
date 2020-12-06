<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 31.10.2017
 * Time: 09:09
 */

namespace src\models;


class User implements \JsonSerializable
{
    /** @var int */
    private $idClubMember;
    /** @var string */
    private $username;
    /** @var string */
    private $password;
    /** @var string */
    private $name;
    /** @var string */
    private $surname;
    /** @var int */
    private $idClub;
    /** @var string */
    private $phoneNumber;
    /** @var int */
    private $legitimationNumber;
    /** @var string */
    private $city;
    /** @var string */
    private $address;
    /** @var int true=1*/
    private $isAdmin;

    /**
     * User constructor.
     */
    public function __construct()
    {
//
    }

    /**
     * @return int
     */
    public function getIdClubMember()
    {
        return $this->idClubMember;
    }

    /**
     * @param int $idClubMember
     */
    public function setIdClubMember($idClubMember)
    {
        $this->idClubMember = $idClubMember;
    }

    /**
     * @return string
     */
    public function getUsername()
    {
        return $this->username;
    }

    /**
     * @param string $username
     */
    public function setUsername($username)
    {
        $this->username = $username;
    }

    /**
     * @return string
     */
    public function getName()
    {
        return $this->name;
    }

    /**
     * @param string $name
     */
    public function setName($name)
    {
        $this->name = $name;
    }

    /**
     * @return string
     */
    public function getSurname()
    {
        return $this->surname;
    }

    /**
     * @param string $surname
     */
    public function setSurname($surname)
    {
        $this->surname = $surname;
    }

    /**
     * @return int
     */
    public function getIdClub()
    {
        return $this->idClub;
    }

    /**
     * @param int $idClub
     */
    public function setIdClub($idClub)
    {
        $this->idClub = $idClub;
    }

    /**
     * @return string
     */
    public function getPhoneNumber()
    {
        return $this->phoneNumber;
    }

    /**
     * @param string $phoneNumber
     */
    public function setPhoneNumber($phoneNumber)
    {
        $this->phoneNumber = $phoneNumber;
    }

    /**
     * @return int
     */
    public function getLegitimationNumber()
    {
        return $this->legitimationNumber;
    }

    /**
     * @param int $legitimationNumber
     */
    public function setLegitimationNumber($legitimationNumber)
    {
        $this->legitimationNumber = $legitimationNumber;
    }

    /**
     * @return string
     */
    public function getCity()
    {
        return $this->city;
    }

    /**
     * @param string $city
     */
    public function setCity($city)
    {
        $this->city = $city;
    }

    /**
     * @return string
     */
    public function getAddress()
    {
        return $this->address;
    }

    /**
     * @param string $address
     */
    public function setAddress($address)
    {
        $this->address = $address;
    }

    /**
     * @return int
     */
    public function isAdmin()
    {
        return $this->isAdmin;
    }

    /**
     * @param int $isAdmin
     */
    public function setIsAdmin($isAdmin)
    {
        $this->isAdmin = $isAdmin;
    }

    /**
     * @return string
     */
    public function getPassword()
    {
        return $this->password;
    }

    /**
     * @param string $password
     */
    public function setPassword($password)
    {
        $this->password = $password;
    }





    public function jsonSerialize() {
        return [
            'idUser' => $this->idClubMember,
            'username' => $this->username,
            'name' => $this->name,
            'surname' => $this->surname,
            'idClub' => $this->idClub,
            'phoneNumber' => $this->phoneNumber,
            'legitimationNumber' => $this->legitimationNumber,
            'city' => $this->city,
            'address' => $this->address,
            'isAdmin' => $this->isAdmin
        ];
    }
}