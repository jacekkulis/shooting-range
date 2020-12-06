<?php
session_start();
require "../vendor/autoload.php";

if (!isset($_SESSION['logged'])) {
    header('Location: index.php');
    exit();
}

$_SESSION['page'] = "overview";
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

    <style>
        @import url("http://netdna.bootstrapcdn.com/font-awesome/4.0.3/css/font-awesome.min.css");

        body { margin-top:20px; }
        .fa { font-size: 50px;text-align: right;position: absolute;top: 7px;right: 27px;outline: none; }
        a { transition: all .3s ease;-webkit-transition: all .3s ease;-moz-transition: all .3s ease;-o-transition: all .3s ease; }
        /* Visitor */
        a.accounts i,.accounts h4.list-group-item-heading { color:#E48A07; }
        a.accounts:hover { background-color:#E48A07; }
        a.accounts:hover * { color:#FFF; }

        /* Google */
        a.clubs i,.clubs h4.list-group-item-heading { color:#dd4b39; }
        a.clubs:hover { background-color:#dd4b39; }
        a.clubs:hover * { color:#FFF; }


        /* loading */
        #loader {
            bottom: 0;
            height: 175px;
            left: 0;
            margin: auto;
            position: absolute;
            right: 0;
            top: 0;
            width: 175px;
        }
        #loader {
            bottom: 0;
            height: 175px;
            left: 0;
            margin: auto;
            position: absolute;
            right: 0;
            top: 0;
            width: 175px;
        }
        #loader .dot {
            bottom: 0;
            height: 100%;
            left: 0;
            margin: auto;
            position: absolute;
            right: 0;
            top: 0;
            width: 87.5px;
        }
        #loader .dot::before {
            border-radius: 100%;
            content: "";
            height: 87.5px;
            left: 0;
            position: absolute;
            right: 0;
            top: 0;
            transform: scale(0);
            width: 87.5px;
        }
        #loader .dot:nth-child(7n+1) {
            transform: rotate(45deg);
        }
        #loader .dot:nth-child(7n+1)::before {
            animation: 0.8s linear 0.1s normal none infinite running load;
            background: #00ff80 none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+2) {
            transform: rotate(90deg);
        }
        #loader .dot:nth-child(7n+2)::before {
            animation: 0.8s linear 0.2s normal none infinite running load;
            background: #00ffea none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+3) {
            transform: rotate(135deg);
        }
        #loader .dot:nth-child(7n+3)::before {
            animation: 0.8s linear 0.3s normal none infinite running load;
            background: #00aaff none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+4) {
            transform: rotate(180deg);
        }
        #loader .dot:nth-child(7n+4)::before {
            animation: 0.8s linear 0.4s normal none infinite running load;
            background: #0040ff none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+5) {
            transform: rotate(225deg);
        }
        #loader .dot:nth-child(7n+5)::before {
            animation: 0.8s linear 0.5s normal none infinite running load;
            background: #2a00ff none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+6) {
            transform: rotate(270deg);
        }
        #loader .dot:nth-child(7n+6)::before {
            animation: 0.8s linear 0.6s normal none infinite running load;
            background: #9500ff none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+7) {
            transform: rotate(315deg);
        }
        #loader .dot:nth-child(7n+7)::before {
            animation: 0.8s linear 0.7s normal none infinite running load;
            background: magenta none repeat scroll 0 0;
        }
        #loader .dot:nth-child(7n+8) {
            transform: rotate(360deg);
        }
        #loader .dot:nth-child(7n+8)::before {
            animation: 0.8s linear 0.8s normal none infinite running load;
            background: #ff0095 none repeat scroll 0 0;
        }

        @keyframes load {
            100% {
                opacity: 0;
                transform: scale(1);
            }
        }
        @keyframes load {
            100% {
                opacity: 0;
                transform: scale(1);
            }
        }
    </style>
</head>
<body>

<?php include('shared/navbar.php'); ?>

<?php include('shared/sidebar.php'); ?>


<div class="container-fluid">
    <div class="row">
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2 main">
            <h1 class="page-header">Overview</h1>

            <div class="col-md-12">
                <div class="center-block">
                    <img src="shared/logo.png" class="img-responsive center-block" alt="Logo" style="width:50%; height: 50%; margin-bottom: 30px;"/>
                </div>
            </div>



            <div class="container">
                <div class="row">
                    <div class="col-md-5">
                        <div class="list-group">
                            <a href="#" class="list-group-item accounts">
                                <h3 class="pull-right">
                                    <i class="fa fa-user"></i>
                                </h3>
                                <h4 class="list-group-item-heading count">
                                    37</h4>
                                <p class="list-group-item-text">Accounts</p>
                            </a>
                        </div>
                    </div>

                    <div class="col-md-5">
                        <div class="list-group">
                            <a href="#" class="list-group-item clubs">
                                <h3 class="pull-right">
                                    <i class="fa fa-group"></i>
                                </h3>
                                <h4 class="list-group-item-heading count">
                                    15</h4>
                                <p class="list-group-item-text">
                                    Clubs</p>
                            </a>
                        </div>
                    </div>

                </div>
            </div>

        </div>
    </div>
</div>

<script>
    // Animate the element's value from x to y:
    $({ someValue: 0 }).animate({ someValue: Math.floor(5) }, {
        duration: 3000,
        easing: 'swing', // can be anything
        step: function () { // called on every step
            // Update the element's text with rounded-up value:
            $('.').text(commaSeparateNumber(Math.round(this.someValue)));
        }
    });

    function commaSeparateNumber(val) {
        while (/(\d+)(\d{3})/.test(val.toString())) {
            val = val.toString().replace(/(\d)(?=(\d\d\d)+(?!\d))/g, "$1,");
        }
        return val;
    }
</script>

<?php include('shared/footer.php'); ?>

</body>
</html>