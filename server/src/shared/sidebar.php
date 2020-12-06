<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 05.01.2018
 * Time: 10:18
 */

?>

<!-- Sidebar -->
<div class="col-sm-2 col-md-2 sidebar">
    <h5><i class="glyphicon glyphicon-user"></i>
        <small style="color: white"><b>HOME</b></small>
    </h5>
    <ul class="nav nav-sidebar">
        <?php
        if (isset($_SESSION['page']) && ($_SESSION['page'] == 'overview'))
        {
            echo '<li class="active"><a href="overview.php">Overview<span class="sr-only">(current)</span></a></li>';
        }
        else {
            echo '<li><a href="overview.php">Overview</a></li>';
        }
        ?>
        <li class="nav-divider"></li>
    </ul>

    <h5><i class="glyphicon glyphicon-user"></i>
        <small><b style="color: white" >USERS</b></small>
    </h5>
    <ul class="nav nav-sidebar">
        <?php
        if (isset($_SESSION['page']) && ($_SESSION['page'] == 'users'))
        {
            echo '<li class="active"><a href="users.php">User list<span class="sr-only">(current)</span></a></li>';
        }
        else {
            echo '<li><a href="users.php">User list</a></li>';
        }
        ?>

        <?php
        if (isset($_SESSION['page']) && ($_SESSION['page'] == 'createUser'))
        {
            echo '<li class="active"><a href="user/create.php">Create user<span class="sr-only">(current)</span></a></li>';
        }
        else {
            echo '<li><a href="user/create.php">Create user</a></li>';
        }
        ?>
        <li  class="nav-divider"></li>
    </ul>

    <h5><i style="color: white" class="glyphicon glyphicon-calendar"></i>
        <small style="color: white" ><b>EVENTS</b></small>
    </h5>
    <ul class="nav nav-sidebar">
        <?php
        if (isset($_SESSION['page']) && ($_SESSION['page'] == 'events'))
        {
            echo '<li class="active"><a href="events.php">Event list<span class="sr-only">(current)</span></a></li>';
        }
        else {
            echo '<li><a href="events.php">Event list</a></li>';
        }
        ?>

        <?php
        if (isset($_SESSION['page']) && ($_SESSION['page'] == 'createEvent'))
        {
            echo '<li class="active"><a href="event/create.php">Create event<span class="sr-only">(current)</span></a></li>';
        }
        else {
            echo '<li><a href="event/create.php">Create event</a></li>';
        }
        ?>
        <li class="nav-divider"></li>
    </ul>
</div>

