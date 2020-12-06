<?php
session_start();
require "../vendor/autoload.php";

if (!isset($_SESSION['logged'])) {
    header('Location: index.php');
    exit();
}

$_SESSION['page'] = "users";
?>

<!DOCTYPE HTML>
<html lang="pl">
<head>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>Shooting Range</title>

    <link href="../css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="../css/dashboard.css?<?php echo "" ?>" type="text/css">


    <script src="../dist/jquery-3.2.1.js"></script>
    <script src="../dist/bootstrap-3.3.7.js"></script>
    <script src="../dist/bootstrap-confirmation.js"></script>
</head>
<body>

<?php include('shared/navbar.php'); ?>

<?php include('shared/sidebar.php'); ?>

<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Users table</h1>

            <?php include('shared/alerts.php') ?>

            <div class="table-responsive">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>#</th>
                        <th class="col-md-1">Username</th>
                        <th class="col-md-1">Club</th>
                        <th class="col-md-1">Name</th>
                        <th class="col-md-1">Surname</th>
                        <th class="col-md-1">Legitimation number</th>
                        <th class="col-md-1">Phone number</th>
                        <th class="col-md-1">City</th>
                        <th class="col-md-1">Address</th>

                        <th>Action</th>
                    </tr>
                    </thead>
                    <tbody>

                    <?php

                    use src\models\DbConnect;
                    use src\models\User;

                    try {
                        $db_connect = new DbConnect();
                        $db_connect->connect();
                        $allEvents = $db_connect->getUserList();

                        while ($row = mysqli_fetch_array($allEvents)) {
                            $user = new User();

                            $user->setIdClubMember($row['idClubMember']);
                            $user->setUsername($row['username']);
                            $user->setIdClub($row['idClub']);
                            $user->setName($row['name']);
                            $user->setSurname($row['surname']);
                            $user->setLegitimationNumber($row['legitimationNumber']);
                            $user->setPhoneNumber($row['phoneNumber']);
                            $user->setCity($row['city']);
                            $user->setAddress($row['address']);
                            $user->setIsAdmin($row['isAdmin']);

                            //$result = mysqli_fetch_array($db_connect->getNumberOfMembers($row['idClub']));
                            //echo $result['number'];

                            echo "<tr>";
                            if ($user->isAdmin()){
                                echo "<td><span class=\"glyphicon glyphicon-user\"></span></td>";
                            }
                            else {
                                echo "<td>" . $user->getIdClubMember() . "</td>";
                            }
                            echo "<td>" . $user->getUsername() . "</td>";
                            echo "<td>" . $user->getIdClub() . "</td>";
                            echo "<td>" . $user->getName() . "</td>";
                            echo "<td>" . $user->getSurname() . "</td>";
                            echo "<td>" . $user->getLegitimationNumber() . "</td>";
                            echo "<td>" . $user->getPhoneNumber() . "</td>";
                            echo "<td>" . $user->getCity() . "</td>";
                            echo "<td>" . $user->getAddress() . "</td>";


                            //  echo "<td>" . '<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#message' . $user->getIdClubMember() . ' "><span class="glyphicon glyphicon-pencil"></span> Edit</button>';
                            echo "<td>" . '<a href="user/edit.php?id=' . $user->getIdClubMember() . '" class="btn btn-primary" role="button"><span class="glyphicon glyphicon-pencil"></span> Edit</a>';
                            echo ' <a href="user/delete.php?id=' . $user->getIdClubMember() . '" class="btn btn-danger" role="button"><span class="glyphicon glyphicon-trash"></span> Delete</a></td>';
                            echo "</tr>";

                            echo '<div id="message' . $user->getIdClubMember(). '" class="modal fade" role="dialog">
                                    <div class="modal-dialog">
                                        <!-- Modal content-->
                                        <div class="modal-content">
                                            <div class="modal-header">
                                                <button type="button" class="close" data-dismiss="modal">&times;</button>
                                                <h4 class="modal-title">Modal Header</h4>
                                            </div>
                                            <div class="modal-body">
                                                <p>Some text in the modal.</p>' . $user->getIdClubMember() . '
                                                <?php echo $row[\'id\'];?>
                                            </div>
                                            
                                            
                                            <div class="modal-footer">
                                                <button type="button" class="btn btn-default" data-dismiss="modal">Close</button>
                                            </div>
                                        </div>
                                    </div>
                                </div>';
                        }

                    } catch (Exception $e) {
                        $_SESSION['error'] = $e->getMessage();
                    }
                    ?>
                    </tbody>
                </table>
            </div>


        </div>
    </div>
</div>

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
</script>


</body>
</html>