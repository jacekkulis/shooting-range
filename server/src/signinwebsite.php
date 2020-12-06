<?php
session_start();
use src\models\DbConnect;

require "../vendor/autoload.php";

if(!isset($_POST['login']) || !isset($_POST['password']))
{
    header('Location: index.php');
    exit();
}


$login = $_POST["login"];
$password = $_POST["password"];

$login = htmlentities($login, ENT_QUOTES, "UTF-8");

$db_connect = new DbConnect();
$db_connect->connect();

$username = $_POST["login"];
$username = htmlentities($username, ENT_QUOTES, "UTF-8");

$user = $db_connect->adminLogin($username, $password);

if ($user != null){
    $_SESSION['logged'] = true;
    $_SESSION['user'] = $user->getUsername();

    if ($user->isAdmin() == 1){
        $_SESSION['isAdmin'] = true;
    }
    else {
        $_SESSION['isAdmin'] = false;
    }
    unset($_SESSION['error']);

    header('Location: overview.php');
}
else {
    $_SESSION['error'] = "Invalid login or username!";
    header('Location: signinsite.php');
}
?>