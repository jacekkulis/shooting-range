<?php
session_start();

use src\models\DbConnect;
use src\models\ImageUploader;

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
    $mainRefereeList = $db_connect->getMainRefereeList();
    $raterList = $db_connect->getRaterList();
    $typeOfCompetitionList = $db_connect->getTypeOFCompetitionList();

    if (isset($_GET['id'])){
        $eventId = $_GET['id'];
        $event = mysqli_fetch_array($db_connect->getEventById($_GET['id']));

        $_SESSION['title'] = $event['competitionName'];
        $_SESSION['description'] = $event['competitionDescription'];
        $_SESSION['mainRefereeSelectBox'] = $event['idMainReferee'];
        $_SESSION['ratersSelectBox'] = $event['idRater'];
        $_SESSION['typeOfCompetitionBox'] = $event['idTypeOfCompetition'];
        $_SESSION['date'] = date("Y-m-d\TH:i", strtotime($event['competitionDate']));
        $_SESSION['price'] = $event['price'];

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
        <a href="../events.php" class="btn btn-primary">
            <span class="glyphicon glyphicon-backward" aria-hidden="true"></span> Back
        </a>
    </div>
</div>

<div class="col-sm-10 col-sm-offset-1 col-md-10 col-md-offset-2 main">
    <h1 class="page-header">Edit competition</h1>
    <div class="container-fluid">
        <form method="post" action="<?php echo $_SERVER['PHP_SELF'] . '?id=' .$eventId; ?>" enctype="multipart/form-data"
              class="form-horizontal">

            <?php include('../shared/alerts.php'); ?>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="title" id="title">Competition title</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-pencil"></i></span>
                    <input type="text" class="form-control input-lg" name="title" placeholder="Competition title"
                           required="required"
                           value="<?php if (isset($_POST['title'])) {
                               echo $_POST['title'];
                           } else if (isset($_SESSION['title'])) {
                               echo $_SESSION['title'];
                               unset($_SESSION['title']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="description" id="description">Competition
                    description</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-list-alt"></i></span>
                    <textarea rows="3" style="overflow:auto;resize:none" maxlength="300" class="form-control input-lg"
                              name="description" placeholder="Competition description"
                              required="required"><?php if (isset($_POST['description'])) {
                            echo $_POST['description'];
                        } else if (isset($_SESSION['description'])) {
                            echo $_SESSION['description'];
                            unset($_SESSION['description']);
                        } ?></textarea>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="mainRefereeSelectBox">Main referee</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <select required class="form-control input-lg" id="mainRefereeSelectBox" name="mainRefereeSelectBox">
                        <?php

                        while ($row = $mainRefereeList->fetch_assoc()):;
                            if ($row['idMainReferee'] == $_POST['mainRefereeSelectBox'] || $row['idMainReferee'] == $_SESSION['mainRefereeSelectBox']){
                                echo '<option selected value="' . $row['idMainReferee'] . '">' . $row['name'] . ' ' . $row['surname'] . '</option>';
                            } else
                                echo '<option value="' . $row['idMainReferee'] . '">' . $row['name'] . ' ' . $row['surname'] . '</option>';
                        endwhile;
                        $mainRefereeList->free_result();
                        ?>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="ratersSelectBox">Rater</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-user"></i></span>
                    <select required class="form-control input-lg" id="ratersSelectBox" name="ratersSelectBox">
                        <?php
                        while ($row = $raterList->fetch_assoc()):;
                            if ($row['idRater'] == $_POST['ratersSelectBox'] || $row['idRater'] == $_SESSION['ratersSelectBox']){
                                echo '<option selected value="' . $row['idRater'] . '">' . $row['name'] . ' ' . $row['surname'] . '</option>';
                            } else
                                echo '<option value="' . $row['idRater'] . '">' . $row['name'] . " " . $row['surname'] . '</option>';
                        endwhile;
                        $raterList->free_result();
                        ?>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="typeOfCompetitionBox">Type of competition</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-menu-hamburger"></i></span>
                    <select required class="form-control input-lg" id="typeOfCompetitionBox" name="typeOfCompetitionBox">
                        <?php while ($row = $typeOfCompetitionList->fetch_assoc()):;
                            if ($row['idTypeOfCompetition'] == $_POST['typeOfCompetitionBox'] || $row['idTypeOfCompetition'] == $_SESSION['typeOfCompetitionBox']){
                                echo '<option selected value="' . $row['idTypeOfCompetition'] . '">' . $row['fullName'] . '</option>';
                            } else
                                echo '<option <option value="' . $row['idTypeOfCompetition'] . '">' . $row['fullName'] . '</option>';
                        endwhile;

                        $typeOfCompetitionList->free_result();
                        ?>
                    </select>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="date">Date</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-calendar"></i></span>
                    <input class="form-control input-lg" type="datetime-local" id="date" name="date" required="required"
                           value="<?php if (isset($_POST['date'])) {
                               echo $_POST['date'];
                               unset($_SESSION['date']);
                           } else if (isset($_SESSION['date'])) {
                               echo $_SESSION['date'];
                               unset($_SESSION['date']);
                           } else {
                               echo '2018-01-01T10:00:00';
                           }
                           ?>">
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="price" id="price">Price</label>
                <div class="col-md-8 placeholder input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-eur"></i></span>
                    <input type="number" class="form-control input-lg" name="price" placeholder="Price"
                           required="required"
                           value="<?php if (isset($_POST['price'])) {
                               echo $_POST['price'];
                           } else if (isset($_SESSION['price'])) {
                               echo $_SESSION['price'];
                               unset($_SESSION['price']);
                           }
                           ?>"/>
                </div>
            </div>

            <div class="form-group">
                <label class="control-label col-md-3 input-lg" for="toggle-event">Do you want to change thumbnail?</label>
                <div class="col-md-8 input-group">
                    <span class="input-group-addon"><i class="glyphicon glyphicon-picture"></i></span>
                    <input id="toggle-event" name="toggle-event" data-width="100%" data-height="" data-size="normal" data-on="Change" data-off="Keep" type="checkbox" data-onstyle="success" data-offstyle="danger" data-toggle="toggle">
                </div>

                <input type="hidden" class="form-control input-lg" name="changeThumbnail" id="changeThumbnail"/>
            </div>

            <div class="form-group" id="image-event">
                <label class="control-label col-md-3 input-lg" for="thumbnail" id="thumbnail">Thumbnail</label>
                <div class="col-md-8">
                    <!-- image-preview-filename input [CUT FROM HERE]-->
                    <div class="input-group image-preview">
                        <span class="input-group-addon"><i class="glyphicon glyphicon-file"></i></span>
                        <input type="text" class="form-control image-preview-filename" disabled="disabled">
                        <!-- don't give a name === doesn't send on POST/GET -->
                        <span class="input-group-btn">
                                <!-- image-preview-clear button -->
                                <button type="button" class="btn btn-default image-preview-clear" style="display:none;">
                                    <span class="glyphicon glyphicon-remove"></span> Clear
                                </button>
                                <!-- image-preview-input -->
                                <div class="btn btn-default image-preview-input">
                                    <span class="glyphicon glyphicon-folder-open"></span>
                                    <span class="image-preview-input-title">Browse</span>
                                    <input type="file" accept="image/png, image/jpeg, image/gif"
                                           name="thumbnail" id="thumbnail"/> <!-- rename it -->
                                </div>
                        </span>
                    </div>
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
    if (!isset($_POST['title'])) {
        $_SESSION['error'] = "Field title is not set.";
    } else if (!isset($_POST['description'])) {
        $_SESSION['error'] = "Field description is not set.";
    } else if (!isset($_POST['mainRefereeSelectBox'])) {
        $_SESSION['error'] = "Field mainRefereeSelectBox is not set.";
    } else if (!isset($_POST['ratersSelectBox'])) {
        $_SESSION['error'] = "Field ratersSelectBox is not set.";
    } else if (!isset($_POST['typeOfCompetitionBox'])) {
        $_SESSION['error'] = "Field typeOfCompetitionBox is not set.";
    } else if (!isset($_POST['date'])) {
        $_SESSION['error'] = "Field date is not set.";
    } else if (!isset($_POST['price'])) {
        $_SESSION['error'] = "Field price is not set.";
    } else if ($_FILES['thumbnail']['size'] == 0 && $_FILES['thumbnail']['error'] == 0) {
        $_SESSION['error'] = "Field thumbnail is not set.";
    }

    $_SESSION['title'] = $_POST['title'];
    $_SESSION['description'] = $_POST['description'];
    $_SESSION['mainRefereeSelectBox'] = $_POST['mainRefereeSelectBox'];
    $_SESSION['ratersSelectBox'] = $_POST['ratersSelectBox'];
    $_SESSION['typeOfCompetitionBox'] = $_POST['typeOfCompetitionBox'];
    $_SESSION['date'] = $_POST['date'];
    $_SESSION['price'] = $_POST['price'];

    $title = $_POST['title'];
    $description = $_POST['description'];

    $mainRefereeId = $_POST['mainRefereeSelectBox'];
    $raterId = $_POST['ratersSelectBox'];
    $typeOfCompetitionId = $_POST['typeOfCompetitionBox'];

    $competitionDate = date("Y-m-d  H:i", strtotime($_POST['date']));
    $price = $_POST['price'];

    try {
        if (isset($_POST['changeThumbnail'])){
            if ($_POST['changeThumbnail'] == "true"){
                $image_uploader = new ImageUploader($_FILES['thumbnail']);
                $thumbnailUrl = $image_uploader->getTargetFile();

                if (!$image_uploader->isUploaded()) {
                    $_SESSION['error'] = $image_uploader->result;
                    exit();
                }

                $db_connect->updateEvent($eventId, $mainRefereeId, $title, $description, $price, $raterId, $typeOfCompetitionId, $competitionDate, $thumbnailUrl);
                $_SESSION['success'] = "Event has been updated successfully!";
            }
            else if ($_POST['changeThumbnail'] == "false"){
                $db_connect->updateEventWithoutThumbnail($eventId, $mainRefereeId, $title, $description, $price, $raterId, $typeOfCompetitionId, $competitionDate);
                $_SESSION['success'] = "Event has been updated successfully!";
            }
            else {
                $_SESSION['error'] = "Change thumbnail error";
            }
        }
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

