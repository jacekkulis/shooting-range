<?php
session_start();

use src\models\DbConnect;
use src\models\User;

if (!isset($_SESSION['logged'])) {
    header('Location: index.php');
    exit();
}

require "../../vendor/autoload.php";

$_SESSION['page'] = "edit";
?>

<!DOCTYPE HTML>
<html lang="pl">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shooting range</title>
    <link href="../../css/bootstrap.min.css" rel="stylesheet">
    <link href="../../css/bootstrap-toggle.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../../css/dashboard.css?<?php echo "" ?>" type="text/css">


    <script src="../../dist/jquery-3.2.1.js"></script>
    <script src="../../dist/bootstrap-3.3.7.js"></script>
    <script src="../../dist/bootstrap-toggle.min.js"></script>
    <script src="../../dist/bootstrap-confirmation.js"></script>

</head>
<body>

<?php include('../shared/navbar.php') ?>

<?php
$db_connect = new DbConnect();
try {
    $db_connect->connect();
    $clubList = $db_connect->getClubListForDropDownList();

    if (isset($_GET['id'])){
        $userId = $_GET['id'];
        $user = mysqli_fetch_array($db_connect->getUserById($userId));

        $_SESSION['username'] = $user['username'];
        $_SESSION['name'] = $user['name'];
        $_SESSION['surname'] = $user['surname'];
        $_SESSION['clubSelectBox'] = $user['idClub'];
        $_SESSION['legitimationNumber'] = $user['legitimationNumber'];
        $_SESSION['phoneNumber'] = $user['phoneNumber'];
        $_SESSION['city'] = $user['city'];
        $_SESSION['address'] = $user['address'];
    }
    else {
        $_SESSION['error'] = "Variable id is not set.";
        echo '<div class="alert alert-danger col-md-8 col-md-offset-2 placeholder"><strong>' . "Error! " . '</strong>' . $_SESSION['error'] . '</div>';
        unset($_SESSION['error']);
        exit;
    }

} catch (Exception $e) {
    $_SESSION['error'] = "Thrown error: " . $e->getMessage();
}
?>

<div class="col-sm-2 col-md-2 sidebar">
    <div class="text-center">
        <a href="../users.php" class="btn btn-primary">
            <span class="glyphicon glyphicon-backward" aria-hidden="true"></span> Back
        </a>
    </div>
</div>

<div class="col-sm-10 col-sm-offset-1 col-md-10 col-md-offset-2 main">
    <h1 class="page-header">Edit competition</h1>
    <div class="container-fluid">
        <form method="post" action="<?php echo $_SERVER['PHP_SELF'] . '?id=' .$userId; ?>" class="form-horizontal">

            <?php include('../shared/alerts.php'); ?>

            <?php include('../shared/alerts.php') ?>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="username">Username</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" class="form-control input-lg" name="username" placeholder="Username"
                           required="required"
                           value="<?php if (isset($_POST['username'])) {
                               echo $_POST['username'];
                           } else if (isset($_SESSION['username'])) {
                               echo $_SESSION['username'];
                               unset($_SESSION['username']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="password">Password</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-lock"></i></span>
                    <input type="password" class="form-control input-lg" name="password" placeholder="Password"
                           required="required"
                           value="<?php if (isset($_POST['password'])) {
                               echo $_POST['password'];
                           } else if (isset($_SESSION['password'])) {
                               echo $_SESSION['password'];
                               unset($_SESSION['password']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="clubSelectBox">Club</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-tags"></i></span>
                    <select required class="form-control input-lg" name="clubSelectBox" id="clubSelectBox">
                        <?php

                        while ($row = $clubList->fetch_assoc()):;
                            if ($row['idClub'] == $_POST['clubSelectBox'] || $row['idClub'] == $_SESSION['clubSelectBox']){
                                echo '<option selected value="' . $row['idClub'] . '">' . $row['clubName'] . '</option>';
                            } else
                                echo '<option value="' . $row['idClub'] . '">' . $row['clubName'] . '</option>';
                        endwhile;
                        $clubList->free_result();
                        ?>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="name">Name</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" class="form-control input-lg" name="name" placeholder="Name"
                           required="required"
                           value="<?php if (isset($_POST['name'])) {
                               echo $_POST['name'];
                           } else if (isset($_SESSION['name'])) {
                               echo $_SESSION['name'];
                               unset($_SESSION['name']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="surname">Surname</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <input type="text" class="form-control input-lg" name="surname" placeholder="Surname"
                           required="required"
                           value="<?php if (isset($_POST['surname'])) {
                               echo $_POST['surname'];
                           } else if (isset($_SESSION['surname'])) {
                               echo $_SESSION['surname'];
                               unset($_SESSION['surname']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="legitimationNumber">Legitimation number</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-credit-card"></i></span>
                    <input type="text" class="form-control input-lg" name="legitimationNumber" placeholder="Legitimation number"
                           required="required"
                           value="<?php if (isset($_POST['legitimationNumber'])) {
                               echo $_POST['legitimationNumber'];
                           } else if (isset($_SESSION['legitimationNumber'])) {
                               echo $_SESSION['legitimationNumber'];
                               unset($_SESSION['legitimationNumber']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="phoneNumber">Phone number</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-phone"></i></span>
                    <input type="text" class="form-control input-lg bfh-phone" data-country="PL" name="phoneNumber" placeholder="Phone number"
                           required="required"
                           value="<?php if (isset($_POST['phoneNumber'])) {
                               echo $_POST['phoneNumber'];
                           } else if (isset($_SESSION['phoneNumber'])) {
                               echo $_SESSION['phoneNumber'];
                               unset($_SESSION['phoneNumber']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="name">City</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                    <input type="text" class="form-control input-lg" name="city" placeholder="City"
                           required="required"
                           value="<?php if (isset($_POST['city'])) {
                               echo $_POST['city'];
                           } else if (isset($_SESSION['city'])) {
                               echo $_SESSION['city'];
                               unset($_SESSION['city']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-2 input-lg" for="address">Address</label>
                <div class="col-md-6 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-globe"></i></span>
                    <input type="text" class="form-control input-lg" name="address" placeholder="Address"
                           required="required"
                           value="<?php if (isset($_POST['address'])) {
                               echo $_POST['address'];
                           } else if (isset($_SESSION['address'])) {
                               echo $_SESSION['address'];
                               unset($_SESSION['address']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <div class="col-md-offset-1 col-md-11">
                    <button type="submit" name="create" class="form-group placeholder btn btn-large btn-primary col-md-12"
                            data-toggle="confirmation"
                            data-btn-ok-label="Yes"
                            data-btn-ok-class="btn-success"
                            data-btn-cancel-label="No"
                            data-btn-cancel-class="btn-danger"
                            data-title="Are you sure?"
                            data-placement="bottom"
                            data-singleton="true">
                        <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span> Edit</button>
                </div>
            </div>
        </form>
    </div>
</div>


<?php
if (isset($_POST['create'])) {
    if (!isset($_POST['username'])) {
        $_SESSION['error'] = "Field username is not set.";
    } else if (!isset($_POST['password'])) {
        $_SESSION['error'] = "Field password is not set.";
    } else if (!isset($_POST['clubBox'])) {
        $_SESSION['error'] = "Field clubBox is not set.";
    } else if (!isset($_POST['name'])) {
        $_SESSION['error'] = "Field name is not set.";
    } else if (!isset($_POST['surname'])) {
        $_SESSION['error'] = "Field surname is not set.";
    } else if (!isset($_POST['legitimationNumber'])) {
        $_SESSION['error'] = "Field legitimationNumber is not set.";
    } else if (!isset($_POST['phoneNumber'])) {
        $_SESSION['error'] = "Field phoneNumber is not set.";
    } else if (!isset($_POST['city'])) {
        $_SESSION['error'] = "Field city is not set.";
    } else if (!isset($_POST['address'])) {
        $_SESSION['error'] = "Field address is not set.";
    }

    $_SESSION['username'] = $_POST['username'];
    $_SESSION['password'] = $_POST['password'];
    $_SESSION['clubBox'] = $_POST['clubBox'];
    $_SESSION['name'] = $_POST['name'];
    $_SESSION['surname'] = $_POST['surname'];
    $_SESSION['legitimationNumber'] = $_POST['legitimationNumber'];
    $_SESSION['phoneNumber'] = $_POST['phoneNumber'];
    $_SESSION['city'] = $_POST['city'];
    $_SESSION['address'] = $_POST['address'];


    $username = $_POST['username'];
    $password = $_POST['password'];
    $hashedPassword = password_hash($password, PASSWORD_DEFAULT);
    $clubId = $_POST['clubBox'];
    $name = $_POST['name'];
    $surname = $_POST['surname'];
    $legitimationNumber = $_POST['legitimationNumber'];
    $phoneNumber= $_POST['phoneNumber'];
    $city = $_POST['city'];
    $address = $_POST['address'];

    // create user
    $user = new User();
    $user->setUsername($username);
    $user->setName($name);
    $user->setSurname($surname);
    $user->setIdClub($clubId);
    $user->setPhoneNumber($phoneNumber);
    $user->setLegitimationNumber($legitimationNumber);
    $user->setCity($city);
    $user->setAddress($address);
    $user->setPassword($hashedPassword);

    $_SESSION['info'] = json_encode($user);

    try {
        //$db_connect->updateEvent($eventId, $mainRefereeId, $title, $description, $price, $raterId, $typeOfCompetitionId, $competitionDate, $thumbnailUrl);
        $_SESSION['success'] = "User has been updated successfully!";
        echo "<meta http-equiv='refresh' content='0'>";
    } catch (Exception $e) {
        $_SESSION['error'] = "Some error happened. " . $e->getMessage();
        echo "<meta http-equiv='refresh' content='0'>";
    }
}
?>

<?php include('../shared/footer.php') ?>

<script>
    $('document').ready(function(){
        $('#image-event').hide();

        document.getElementById("changeThumbnail").value = "false";

        $('#thumbnail').attr('required', false)
    });

    $(function() {
        $('#toggle-event').change(function() {
            if ($(this).prop('checked')) {
                $('#image-event').show();
                $('#thumbnail').removeAttr('required');
                document.getElementById("changeThumbnail").value = "true";
            } else {
                $('#image-event').hide();

                $('#thumbnail').removeAttr('required');
                document.getElementById("changeThumbnail").value = "false";
            }
        })
    })
</script>

<script>
    console.log('Bootstrap ' + $.fn.tooltip.Constructor.VERSION);
    console.log('Bootstrap Confirmation ' + $.fn.confirmation.Constructor.VERSION);

    $('[data-toggle=confirmation]').confirmation({
        rootSelector: '[data-toggle=confirmation]',
        container: 'body'
    });
    $('[data-toggle=confirmation-singleton]').confirmation({
        rootSelector: '[data-toggle=confirmation-singleton]',
        container: 'body'
    });
    $('[data-toggle=confirmation-popout]').confirmation({
        rootSelector: '[data-toggle=confirmation-popout]',
        container: 'body'
    });

    $('#confirmation-delegate').confirmation({
        selector: 'button'
    });


    $(document).on('click', '#close-preview', function () {
        $('.image-preview').popover('hide');
        // Hover before close the preview
        $('.image-preview').hover(
            function () {
                $('.image-preview').popover('show');
            },
            function () {
                $('.image-preview').popover('hide');
            }
        );
    });

    $(function () {
        // Create the close button
        var closebtn = $('<button/>', {
            type: "button",
            text: 'x',
            id: 'close-preview',
            style: 'font-size: initial;',
        });
        closebtn.attr("class", "close pull-right");
        // Set the popover default content
        $('.image-preview').popover({
            trigger: 'manual',
            html: true,
            title: "<strong>Preview</strong>" + $(closebtn)[0].outerHTML,
            content: "There's no image",
            placement: 'right'
        });

        // Clear event
        $('.image-preview-clear').click(function () {
            $('.image-preview').attr("data-content", "").popover('hide');
            $('.image-preview-filename').val("");
            $('.image-preview-clear').hide();
            $('.image-preview-input input:file').val("");
            $(".image-preview-input-title").text("Browse");
        });
        // Create the preview image
        $(".image-preview-input input:file").change(function () {
            var img = $('<img/>', {
                id: 'dynamic',
                width: 150,
                height: 100
            });
            var file = this.files[0];
            var reader = new FileReader();
            // Set preview image into the popover data-content
            reader.onload = function (e) {
                $(".image-preview-input-title").text("Change");
                $(".image-preview-clear").show();
                $(".image-preview-filename").val(file.name);
                img.attr('src', e.target.result);
                $(".image-preview").attr("data-content", $(img)[0].outerHTML).popover("show");
            }
            reader.readAsDataURL(file);
        });
    });
</script>


</body>
</html>

