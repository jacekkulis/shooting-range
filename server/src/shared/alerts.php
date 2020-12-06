<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 10.01.2018
 * Time: 17:35
 */

if (isset($_SESSION['error'])) {
    echo '<div class="form-group row"><div class="alert alert-danger alert-dismissable col-md-12 placeholder"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' . "Error! " . '</strong>' . $_SESSION['error'] . '</div></div>';
    unset($_SESSION['error']);
}

if (isset($_SESSION['success'])) {
    echo '<div class="form-group row"><div class="alert alert-success col-md-12 alert-dismissable placeholder"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' . "Success! " . '</strong>' . $_SESSION['success'] . '</div></div>';
    unset($_SESSION['success']);
}

if (isset($_SESSION['warning'])) {
    echo '<div class="form-group row"><div class="alert alert-warning col-md-12 alert-dismissable placeholder"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' . "Warning! " . '</strong>' . $_SESSION['warning'] . '</div></div>';
    unset($_SESSION['warning']);
}

if (isset($_SESSION['info'])) {
    echo '<div class="form-group row"><div class="alert alert-info col-md-12 alert-dismissable placeholder"><a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a><strong>' . "Information! " . '</strong>' . $_SESSION['info'] . '</div></div>';
    unset($_SESSION['info']);
}
?>