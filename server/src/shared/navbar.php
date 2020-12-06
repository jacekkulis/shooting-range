<?php
/**
 * Created by PhpStorm.
 * User: Jacek
 * Date: 05.01.2018
 * Time: 10:18
 */

?>

<!-- Navbar -->
<nav class="navbar navbar-fixed-top navbar-inverse">
    <div class="container">
        <div class="navbar-header">
            <?php
            if (isset($_SESSION['logged']) && ($_SESSION['logged'] == true))
            {
                echo ' <div class="navbar-brand" href="#">Hello, ' . $_SESSION['user'] . '</div>';
            }
            else {
                echo '<a class="navbar-brand" href="src/index.php">Home</a>';
            }
            ?>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav navbar-right">
                <?php

                if (isset($_SESSION['logged']) && ($_SESSION['logged'] == true))
                {
                    echo '<li><a href="../logout.php">Logout</a></li>';
                }
                else {
                    echo '<li><a href="#">About</a></li>';
                }
                ?>
            </ul>
        </div>
    </div>
</nav>

